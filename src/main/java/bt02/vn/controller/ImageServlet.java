package bt02.vn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bt02.vn.config.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ImageServlet: "phat" lai anh da upload, qua URL dang
 * /image?fname=xxx.png (mac dinh - anh Category, GIU NGUYEN hanh vi cu de
 * khong pha vo cac cho da dung truoc do) hoac /image?fname=xxx.png&type=product
 * (anh Product, THEM MOI cho Bai tap 03). Can Servlet rieng vi anh duoc
 * luu o Constants.DIR/Constants.PRODUCT_DIR - nam NGOAI thu muc webapp
 * nen trinh duyet khong the truy cap truc tiep bang URL tinh duoc.
 */
@WebServlet(urlPatterns = { "/image" })
public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fileName = req.getParameter("fname");
        if (fileName == null || fileName.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // "type=product" -> doc trong thu muc anh Product; con lai (khong
        // truyen, hoac bat ky gia tri nao khac) -> giu hanh vi CU, doc trong
        // thu muc anh Category, de khong lam hong cac URL <img> da sinh ra
        // tu truoc (category-list.jsp, category-edit.jsp...).
        String type = req.getParameter("type");
        String baseDir = "product".equals(type) ? Constants.PRODUCT_DIR : Constants.DIR;

        File file = new File(baseDir + File.separator + fileName);
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType("image/*");

        try (InputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
