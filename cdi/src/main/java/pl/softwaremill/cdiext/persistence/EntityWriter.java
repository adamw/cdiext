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
        readOnlyEm.detach(entity);
        // Writing the changes
        T writtenEntity = writeableEm.merge(entity);
        writeableEm.flush();
        // Now looking up a fresh copy of the entity. We won't get a stale one because we removed it from the context
        // in the beginning
        return (T) readOnlyEm.find(writtenEntity.getClass(), writtenEntity.getId());
    }
}
