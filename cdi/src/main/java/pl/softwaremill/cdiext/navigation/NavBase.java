package pl.softwaremill.cdiext.navigation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public abstract class NavBase {
    private Map<String, Page> pagesByViewId= new HashMap<String, Page>();

    public void register(String viewId, Page page) {
        pagesByViewId.put(viewId, page);
    }

    public Page lookup(String viewId) {
        Page p = pagesByViewId.get(viewId);
        if (p == null) {
            throw new RuntimeException("Page with view id " + viewId + " not found.");
        }
        return p;
    }

    protected class ViewIdPageBuilder {
        private final String viewId;

        private boolean requiresLogin;

        public ViewIdPageBuilder(String viewId) {
            this.viewId = viewId;
            requiresLogin = false;
        }

        public ViewIdPageBuilder setRequiresLogin(boolean requiresLogin) {
            this.requiresLogin = requiresLogin;
            return this;
        }

        public Page b() {
            Page p = new ViewIdPage(viewId, new LinkedHashMap<String, String>(), requiresLogin);
            register(viewId, p);
            return p;
        }
    }
    
    private final Page currentPage = new CurrentPage();

    public Page getCurrentPage() {
        return currentPage;
    }

    public abstract Page getLogin();

    public abstract Page getError();
}
