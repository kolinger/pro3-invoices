package me.kolinger.pro3.invoices.resources;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class CombinedResourcesServlet extends HttpServlet {

    private static final String URL_PATH = "/_static";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String fileName = uri.substring(uri.indexOf(URL_PATH) + 9);

        File file = new File(req.getServletContext().getRealPath("/_combined/") + "/" + fileName);
        if (!file.exists()) {
            resp.sendError(404, fileName + " not exists");
            return;
        }

        ServletOutputStream sos = resp.getOutputStream();
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int length;
        byte[] buffer = new byte[4096];
        while ((length = in.read(buffer)) != -1) {
            sos.write(buffer, 0, length);
        }
        in.close();
        sos.close();
    }
}
