package pl.softwaremill.cdiext.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class EntityWriter {
    @Inject @ReadOnly
    private EntityManager readOnlyEm;

    @Inject @Writeable
    private EntityManager writeableEm;

    @SuppressWarnings({"unchecked"})
    /**
     * Stores changes made to an entity. All entites must have {@code DETACH} cascade enabled on all associations!
     * @param entity The entity to be written.
     * @return The written entity.
     */
    public <T extends Identifiable<?>> T write(T entity) {
        // First detaching the entity from the RO context
        if (readOnlyEm.contains(entity)) {
            readOnlyEm.detach(entity);
        } else if (entity.getId() != null) {
            // If the entity is not in the RO EM, and is persistent, it is possible that the RO EM contains a different
            // copy of the entity. It must also be detached, hence first looking it up. It is possible that the find()
            // loads the entity into the EM, but it's not possible to check if an entity is loaded into an EM simply
            // by id.
            readOnlyEm.detach(readOnlyEm.find(entity.getClass(), entity.getId()));
        }
        // Writing the changes
        T writtenEntity = writeableEm.merge(entity);
        writeableEm.flush();
        // Now looking up a fresh copy of the entity. We won't get a stale one because we removed it from the context
        // in the beginning
        return (T) readOnlyEm.find(writtenEntity.getClass(), writtenEntity.getId());
    }

    /**
     * Executes an update query, created with the given creator, using the writeable entity manager.
     * The read only entity manager is cleared, so all entities with lazy values will have to be re-read.
     * @param queryCreator A creator to create the update query.
     * @return The number of affected rows.
     */
    public int executeUpdate(QueryCreator queryCreator) {
        int result = queryCreator.createQuery(writeableEm).executeUpdate();

        // Now we need to clear the read only entity manager, so that it doesn't contain stale entities.
        // This could result in some lazy initialization exception.
        readOnlyEm.clear();

        return result;
    }
}
