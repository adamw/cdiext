package pl.softwaremill.cdiext.navigation;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewMetadata;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class ViewIdPage extends AbstractPage {
    private final String viewId;
    private final String securityEL;

    public ViewIdPage(String viewId, Map<String, String> params, boolean requiresLogin, String securityEL) {
        super(params, requiresLogin);
        this.viewId = viewId;
        this.securityEL = securityEL;
    }

    @Override
    protected String computeViewId() {
        return viewId;
    }

    public Page copy(Map<String, String> params, boolean requiresLogin) {
        return new ViewIdPage(viewId, params, requiresLogin, securityEL);
    }

    public String getSecurityEL() {
        return securityEL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewIdPage that = (ViewIdPage) o;

        if (!viewId.equals(that.viewId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return viewId.hashCode();
    }
}