package me.kolinger.pro3.invoices.resources;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.resource.ResourceInfo;
import com.sun.faces.application.resource.ResourceManager;

import javax.faces.application.Resource;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class CombinedResource extends Resource {

    private static final String URL_PATH = "/_static";
    private static final int MODE_CSS = 0;
    private static final int MODE_JS = 1;

    private int mode;
    private String requestPath;

    public CombinedResource(String input) {
        if (input.contains("css")) {
            mode = MODE_CSS;
        } else {
            mode = MODE_JS;
        }

        String[] files = input.split(",");
        ResourceBaseInfo[] resources = new ResourceBaseInfo[files.length];
        int index = 0;
        for (String file : files) {
            int end = file.indexOf('/');
            String library = file.substring(0, end);
            String name = file.substring(end + 1);
            resources[index++] = new ResourceBaseInfo(library, name);
        }
        process(resources);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public Map<String, String> getResponseHeaders() {
        return null;
    }

    @Override
    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public URL getURL() {
        return null;
    }

    @Override
    public boolean userAgentNeedsUpdate(FacesContext context) {
        return false;
    }

    private void process(ResourceBaseInfo[] resources) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        ApplicationAssociate applicationAssociate = ApplicationAssociate.getInstance(externalContext);
        ResourceManager resourceManager = applicationAssociate.getResourceManager();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        // servlet URL
        if (mode == MODE_JS) {
            requestPath = request.getContextPath() + URL_PATH + "/combined.js";
        } else {
            requestPath = request.getContextPath() + URL_PATH + "/combined.css";
        }

        // concatenate all files
        StringBuilder builder = new StringBuilder();
        for (ResourceBaseInfo resource : resources) {
            try {
                String mimeType = externalContext.getMimeType(resource.name);
                ResourceInfo resourceInfo = resourceManager.findResource(resource.library, resource.name, mimeType,
                        facesContext);

                if (resourceInfo == null) {
                    continue;
                }

                InputStream stream = resourceInfo.getHelper().getInputStream(resourceInfo, facesContext);
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // clean folder
        File directory = new File(externalContext.getRealPath("/_combined/"));
        if (!directory.exists()) {
            directory.mkdir();
        }

        // and result write to combined file
        // TODO: yui compiler?
        try {
            String fileName;
            if (mode == MODE_JS) {
                fileName = "/combined.js";
            } else {
                fileName = "/combined.css";
            }
            File combined = new File(directory + fileName);
            Writer writer = new FileWriter(combined);
            writer.append(builder);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ResourceBaseInfo {
        public String library;
        public String name;

        private ResourceBaseInfo(String library, String name) {
            this.library = library;
            this.name = name;
        }
    }
}