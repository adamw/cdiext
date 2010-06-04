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

    public ViewIdPage(String viewId, Map<String, String> params, boolean requiresLogin) {
        super(params, requiresLogin);
        this.viewId = viewId;
    }

    @Override
    protected String computeViewId() {
        return viewId;
    }

    public Page copy(Map<String, String> params, boolean requiresLogin) {
        return new ViewIdPage(viewId, params, requiresLogin);
    }    
}