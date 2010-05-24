package pl.softwaremill.cdiext.security;

import pl.softwaremill.cdiext.navigation.NavBase;
import pl.softwaremill.cdiext.util.BeanInject;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.io.IOException;

/**
 * TODO: go back after a redirect to login
 *
 * TODO: secure the cache:
 * //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-cache");
//Directs caches not to store the page under any circumstance
response.setHeader("Cache-Control","no-store");
//Causes the proxy cache to see the page as "stale"
response.setDateHeader("Expires", 0);
//HTTP 1.0 backward compatibility
response.setHeader("Pragma","no-cache");  
 * @author Adam Warski (adam at warski dot org)
 */
public class SecurityPhaseListener implements PhaseListener {
    public void beforePhase(PhaseEvent event) {
        NavBase nav = BeanInject.lookup(NavBase.class);
        LoginBean login = BeanInject.lookup(LoginBean.class);

        if (nav.lookup(event.getFacesContext().getViewRoot().getViewId()).isRequiresLogin() && !login.isLoggedIn()) {
            FacesContext ctx = event.getFacesContext();
            String url = ctx.getApplication().getViewHandler().getActionURL(ctx, nav.getLogin().s());
            try {
                ctx.getExternalContext().redirect(ctx.getExternalContext().encodeActionURL(url));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ctx.responseComplete();
        }
    }

    public void afterPhase(PhaseEvent event) { }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
