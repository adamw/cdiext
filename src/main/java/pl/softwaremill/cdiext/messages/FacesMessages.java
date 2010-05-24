package pl.softwaremill.cdiext.messages;

import pl.softwaremill.cdiext.el.ELEvaluator;
import pl.softwaremill.cdiext.i18n.CurrentLocale;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Seam2 authors (http://seamframework.org)
 * @author Adam Warski (adam at warski dot org)
 */
@SessionScoped
// TODO: make @FlashScoped after flash is fixed
public class FacesMessages implements Serializable {
    @Inject
    private ELEvaluator elEvaluator;

    @Inject
    private CurrentLocale currentLocale;

    private static class MessageData {
        private final String controlId;
        private final FacesMessage fm;

        private MessageData(String controlId, FacesMessage fm) {
            this.controlId = controlId;
            this.fm = fm;
        }

        public String getControlId() {
            return controlId;
        }

        public FacesMessage getFm() {
            return fm;
        }
    }

    private static final FacesMessage.Severity DEFAULT_SEVERITY = FacesMessage.SEVERITY_INFO;
    private List<MessageData> messages = new ArrayList<MessageData>();

    public void add(String key, Object... params) {
        addToControl(null, key, params);
    }

    public void add(String key, FacesMessage.Severity severity, Object... params) {
        addToControl(null, key, severity, params);
    }

    public void addToControl(String controlId, String key, Object... params) {
        addToControl(controlId, key, DEFAULT_SEVERITY, params);
    }

    public void addToControl(String controlId, String key, FacesMessage.Severity severity, Object... params) {
        // TODO: remove el evaluator?
        key = elEvaluator.evaluate(key, String.class);
        key = formatMessage(key, params);
        FacesMessage fm = new FacesMessage(severity, key, key);
        messages.add(new MessageData(controlId, fm));
    }

    public void beforeRenderResponse() {
        for (MessageData message : messages) {
            FacesContext.getCurrentInstance().addMessage(getClientId(message.getControlId()), message.getFm());
        }

        messages.clear();
    }

    public String formatMessage(String key, Object... params) {
        // TODO: multiple bundles?
        ResourceBundle rb = ResourceBundle.getBundle("messages", currentLocale.getCurrentLocale());
        String value = rb.getString(key);
        return new MessageFormat(value).format(params);
    }

    private String getClientId(String id) {
        if (id == null) {
            return null;
        }
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return getClientId(facesContext.getViewRoot(), id, facesContext);
    }

    private static String getClientId(UIComponent component, String id, FacesContext facesContext) {
        String componentId = component.getId();
        if (componentId != null && componentId.equals(id)) {
            return component.getClientId(facesContext);
        } else {
            Iterator iter = component.getFacetsAndChildren();
            while (iter.hasNext()) {
                UIComponent child = (UIComponent) iter.next();
                String clientId = getClientId(child, id, facesContext);
                if (clientId != null) return clientId;
            }
            return null;
        }
    }
}