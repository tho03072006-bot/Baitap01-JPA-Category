package bt02.vn.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import bt02.vn.config.Constants;
import bt02.vn.entity.Category;
import bt02.vn.entity.Product;
import bt02.vn.service.CategoryServiceImpl;
import bt02.vn.service.ICategoryService;
import bt02.vn.service.IProductService;
import bt02.vn.service.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * ProductController: CRUD cho Products (yeu cau 4.1 cua Bai tap 03), dung
 * MOT Servlet duy nhat xu ly toan bo (List - Add - Edit - Delete), phan
 * biet hanh dong qua URL - RAP KHUON HOAN TOAN theo cau truc/quy uoc da
 * dung cho CategoryController (cung du an, cung tang Service/Dao, cung
 * kieu upload anh qua Part + Constants.<X>_DIR).
 */
@WebServlet(urlPatterns = { "/admin/products", "/admin/product/add", "/admin/product/insert",
        "/admin/product/edit", "/admin/product/update", "/admin/product/delete" })
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // gioi han 5MB / anh
public class ProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IProductService productService;
    private ICategoryService categoryService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductServiceImpl();
        this.categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/admin/products")) {
            List<Product> list = productService.findAll();
            req.setAttribute("listproduct", list);
            req.getRequestDispatcher("/WEB-INF/views/admin/product-list.jsp").forward(req, resp);

        } else if (url.contains("/admin/product/add")) {
            req.setAttribute("listcate", categoryService.findAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/product-add.jsp").forward(req, resp);

        } else if (url.contains("/admin/product/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.findById(id);
            req.setAttribute("product", product);
            req.setAttribute("listcate", categoryService.findAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/product-edit.jsp").forward(req, resp);

        } else if (url.contains("/admin/product/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                productService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        if (url.contains("/admin/product/insert")) {
            handleInsert(req, resp);
        } else if (url.contains("/admin/product/update")) {
            handleUpdate(req, resp);
        }
    }

    private void handleInsert(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String productname = req.getParameter("productname");
        double price = parseDoubleSafe(req.getParameter("price"), 0);
        String description = req.getParameter("description");
        int quantity = parseIntSafe(req.getParameter("quantity"), 0);
        int categoryid = parseIntSafe(req.getParameter("categoryid"), 0);
        String images = req.getParameter("images");

        Category category = categoryService.findById(categoryid);
        if (category == null) {
            // Khong tim thay danh muc da chon (vd bi xoa giua chung luc dang
            // them) -> quay lai form them, KHONG luu san pham "mo coi".
            req.setAttribute("formError", "Danh mục đã chọn không tồn tại, vui lòng chọn lại.");
            req.setAttribute("listcate", categoryService.findAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/product-add.jsp").forward(req, resp);
            return;
        }

        Product product = new Product();
        product.setProductname(productname);
        product.setPrice(price);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setCategory(category);

        String uploadPath = Constants.PRODUCT_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            Part part = req.getPart("images1");
            if (part != null && part.getSize() > 0) {
                String fname = saveUploadedFile(part, uploadPath);
                product.setImages(fname);
            } else if (images != null && !images.isBlank()) {
                product.setImages(images);
            } else {
                product.setImages(null);
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        }

        productService.insert(product);
        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        int productid = Integer.parseInt(req.getParameter("productid"));
        String productname = req.getParameter("productname");
        double price = parseDoubleSafe(req.getParameter("price"), 0);
        String description = req.getParameter("description");
        int quantity = parseIntSafe(req.getParameter("quantity"), 0);
        int categoryid = parseIntSafe(req.getParameter("categoryid"), 0);
        String images = req.getParameter("images");

        Product product = productService.findById(productid);
        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }

        Category category = categoryService.findById(categoryid);
        if (category == null) {
            req.setAttribute("formError", "Danh mục đã chọn không tồn tại, vui lòng chọn lại.");
            req.setAttribute("product", product);
            req.setAttribute("listcate", categoryService.findAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/product-edit.jsp").forward(req, resp);
            return;
        }

        String fileold = product.getImages();
        product.setProductname(productname);
        product.setPrice(price);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setCategory(category);

        String uploadPath = Constants.PRODUCT_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            Part part = req.getPart("images1");
            if (part != null && part.getSize() > 0) {
                if (isLocalFile(fileold)) {
                    deleteFileQuietly(uploadPath + File.separator + fileold);
                }
                String fname = saveUploadedFile(part, uploadPath);
                product.setImages(fname);
            } else if (images != null && !images.isBlank()) {
                product.setImages(images);
            } else {
                product.setImages(fileold);
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        }

        productService.update(product);
        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }

    private boolean isLocalFile(String images) {
        if (images == null || images.isBlank()) {
            return false;
        }
        return !images.startsWith("http://") && !images.startsWith("https://");
    }

    private void deleteFileQuietly(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String saveUploadedFile(Part part, String uploadPath) throws IOException {
        String filename = Paths.get(extractFileName(part)).getFileName().toString();
        int index = filename.lastIndexOf(".");
        String ext = index >= 0 ? filename.substring(index + 1) : "";
        String fname = System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);

        part.write(uploadPath + File.separator + fname);
        return fname;
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "unknown";
    }

    private int parseIntSafe(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private double parseDoubleSafe(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
