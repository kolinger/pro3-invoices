package me.kolinger.pro3.invoices.resources;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class ResourceHandlerWrapper extends javax.faces.application.ResourceHandlerWrapper {

    private ResourceHandler wrapped;
    private String cache;

    public ResourceHandlerWrapper(ResourceHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ResourceHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public Resource createResource(String resourceName, String libraryName) {
        if (libraryName != null && libraryName.equals("_combined_")) {
            return new CombinedResource(resourceName);
        } else {
            return getWrapped().createResource(resourceName, libraryName);
        }
    }
}
