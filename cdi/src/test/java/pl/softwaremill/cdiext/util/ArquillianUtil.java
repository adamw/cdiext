package pl.softwaremill.cdiext.util;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.container.ManifestContainer;
import org.jboss.shrinkwrap.api.container.ResourceContainer;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class ArquillianUtil {
    public static <T extends Archive<T> & ManifestContainer<T>> T addEmptyBeansXml(T archive) {
        return archive.addManifestResource(
                        new ByteArrayAsset("<beans/>".getBytes()),
                        ArchivePaths.create("beans.xml"));
    }

    public static <T extends Archive<T> & ResourceContainer<T>> T addTestBeansXml(T archive) {
        // TODO: use the same as in the app?
        return archive.addResource("beans.xml");
    }

    public static <T extends Archive<T> & ResourceContainer<T>> T addExtensionsFromApp(T archive) {
        return archive.addResource("META-INF/services/javax.enterprise.inject.spi.Extension");
    }
}
