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
public interface Page {
    Page redirect();
    Page includeViewParams();
    Page includeViewParam(String name);
    Page includeParam(String name, String value);

    String s();
    String getS();
    
    boolean isRequiresLogin();
    String getSecurityEL();
}