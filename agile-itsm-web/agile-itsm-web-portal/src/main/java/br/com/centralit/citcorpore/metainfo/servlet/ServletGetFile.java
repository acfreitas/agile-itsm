package br.com.centralit.citcorpore.metainfo.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.citframework.util.UtilTratamentoArquivos;

public class ServletGetFile extends HttpServlet {

    private static final long serialVersionUID = -1620622864811251759L;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    /**
     * Processa as requisicoes.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String file = request.getParameter("file");
        if (file == null || file.trim().equalsIgnoreCase("")) {
            return;
        }
        String fileName = request.getParameter("fileName");
        if (fileName == null || fileName.trim().equalsIgnoreCase("")) {
            fileName = "export.txt";
        }
        String fileType = request.getParameter("type");
        if (fileType == null || fileType.trim().equalsIgnoreCase("")) {
            fileType = "txt";
        }
        try {
            final byte[] bytes = UtilTratamentoArquivos.getBytesFromFile(new File(file));
            final ServletOutputStream outputStream = response.getOutputStream();
            if (fileType.equalsIgnoreCase("txt")) {
                response.setContentType("text/plain");
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentLength(bytes.length);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
