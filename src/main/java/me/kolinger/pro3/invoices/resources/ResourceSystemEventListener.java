package me.kolinger.pro3.invoices.resources;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import java.util.List;
import java.util.Map;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class ResourceSystemEventListener implements SystemEventListener {

    private static final String LIBRARY_NAME = "_combined_";

    private UIComponent scriptsComponent;
    private UIComponent stylesComponent;

    private StringBuilder scripts = new StringBuilder();
    private StringBuilder styles = new StringBuilder();

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (event instanceof PreRenderViewEvent) {
            FacesContext context = FacesContext.getCurrentInstance();

            PreRenderViewEvent realEvent = (PreRenderViewEvent) event;
            UIViewRoot root = (UIViewRoot) realEvent.getSource();

            convertComponents(root.getComponentResources(context, "head"));
            convertComponents(root.getComponentResources(context, "body"));

            System.out.println(scriptsComponent);
            if (scripts.length() > 0 && scriptsComponent != null) {
                scriptsComponent.getAttributes().put("name", scripts.substring(0, scripts.length() - 1));
                scriptsComponent.getAttributes().put("library", LIBRARY_NAME);
                scriptsComponent.setRendered(true);
            }

            if (styles.length() > 0 && stylesComponent != null) {
                stylesComponent.getAttributes().put("name", styles.substring(0, styles.length() - 1));
                stylesComponent.getAttributes().put("library", LIBRARY_NAME);
                stylesComponent.setRendered(true);
            }
        }
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof UIViewRoot;
    }

    private void convertComponents(List<UIComponent> components) {
        Map<String, Object> attributes;
        for (UIComponent component : components) {
            String type = component.getRendererType();
            attributes = component.getAttributes();

            String library = (String) attributes.get("library");
            String name = (String) attributes.get("name");

//            if (name.contains("#exclude")) {
//                attributes.put("name", name.replace("#exclude", ""));
//                continue;
//            }

            if (type.equals("javax.faces.resource.Stylesheet")) {
                styles.append(library);
                styles.append("/");
                styles.append(attributes.get("name"));
                styles.append(",");
                if (stylesComponent == null) {
                    stylesComponent = component;
                }
            } else if (type.equals("javax.faces.resource.Script")) {
                scripts.append(library);
                scripts.append("/");
                scripts.append(name);
                scripts.append(",");
                if (scriptsComponent == null) {
                    scriptsComponent = component;
                }
            }

            component.setRendered(false);
        }
    }
}
