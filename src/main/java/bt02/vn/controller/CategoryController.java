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
import bt02.vn.service.CategoryServiceImpl;
import bt02.vn.service.ICategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * CategoryController: dung 1 Servlet duy nhat xu ly toan bo CRUD Category
 * (List - Add - Edit - Delete), phan biet hanh dong qua URL, DUNG THEO
 * DUNG cau truc mau cua Bai tap 01. Toan bo thao tac database di qua
 * ICategoryService -> CategoryDao -> EntityManager (JPA/Hibernate that
 * su), KHONG dung JDBC thuan.
 *
 * SUA so voi de bai goc: nhanh xoa (delete) de bai dung "else" (ngam dinh
 * moi URL con lai deu la xoa) - de RO RANG va AN TOAN hon, o day kiem tra
 * tuong minh url.contains("/admin/category/delete") thay vi else mac dinh.
 */
@WebServlet(urlPatterns = { "/admin/categories", "/admin/category/add", "/admin/category/insert",
        "/admin/category/edit", "/admin/category/update", "/admin/category/delete" })
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // gioi han 5MB / anh
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ICategoryService cateService;

    @Override
    public void init() throws ServletException {
        this.cateService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/admin/categories")) {
            // Tinh nang tim kiem theo ten (them theo yeu cau SV, 2026-08-26):
            // ICategoryService.searchByName() da co san tu truoc (dung JPQL
            // "LIKE") nhung chua duoc noi vao Controller/giao dien - gio moi
            // dung toi. Neu khong nhap keyword (hoac chi toan khoang trang)
            // thi hien thi day du nhu cu (findAll()).
            String keyword = req.getParameter("keyword");
            List<Category> list;
            if (keyword != null && !keyword.isBlank()) {
                list = cateService.searchByName(keyword.trim());
            } else {
                list = cateService.findAll();
            }
            req.setAttribute("listcate", list);
            req.setAttribute("keyword", keyword); // giu lai gia tri da nhap tren o tim kiem
            req.getRequestDispatcher("/WEB-INF/views/admin/category-list.jsp").forward(req, resp);

        } else if (url.contains("/admin/category/add")) {
            req.getRequestDispatcher("/WEB-INF/views/admin/category-add.jsp").forward(req, resp);

        } else if (url.contains("/admin/category/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Category category = cateService.findById(id);
            req.setAttribute("cate", category);
            req.getRequestDispatcher("/WEB-INF/views/admin/category-edit.jsp").forward(req, resp);

        } else if (url.contains("/admin/category/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                cateService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        if (url.contains("/admin/category/insert")) {
            handleInsert(req, resp);
        } else if (url.contains("/admin/category/update")) {
            handleUpdate(req, resp);
        }
    }

    private void handleInsert(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        // lay du lieu tu form
        String categoryname = req.getParameter("categoryname");
        int quantity = parseIntSafe(req.getParameter("quantity"), 0);
        String images = req.getParameter("images");

        // dua du lieu vao model. LUU Y: KHONG con set status tay o day nua -
        // CategoryServiceImpl.insert() se TU DONG suy ra status tu quantity
        // (>=1 la Hoat dong, 0 la Khoa) truoc khi luu, theo dung yeu cau moi.
        Category category = new Category();
        category.setCategoryname(categoryname);
        category.setQuantity(quantity);

        String uploadPath = Constants.DIR; // upload vao thu muc cau hinh trong Constants
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // SUA: dung mkdirs() (tao ca thu muc cha con thieu) thay vi mkdir()
        }

        try {
            Part part = req.getPart("images1");
            if (part != null && part.getSize() > 0) {
                String fname = saveUploadedFile(part, uploadPath);
                category.setImages(fname);
            } else if (images != null && !images.isBlank()) {
                category.setImages(images);
            } else {
                category.setImages("avatar.png");
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        }

        // dua model vao phuong thuc insert
        cateService.insert(category);
        // chuyen trang
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        // lay du lieu tu form
        int categoryid = Integer.parseInt(req.getParameter("categoryid"));
        String categoryname = req.getParameter("categoryname");
        int quantity = parseIntSafe(req.getParameter("quantity"), 0);
        String images = req.getParameter("images");

        // dua du lieu vao model
        Category category = cateService.findById(categoryid);
        if (category == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
            return;
        }
        String fileold = category.getImages();
        category.setCategoryname(categoryname);
        // KHONG con set status tay o day - CategoryServiceImpl.update() se
        // TU DONG suy ra status tu quantity truoc khi luu.
        category.setQuantity(quantity);

        String uploadPath = Constants.DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            Part part = req.getPart("images1");
            if (part != null && part.getSize() > 0) {
                // xoa file cu tren thu muc, chi khi anh cu la file luu noi bo
                // (khong xoa nham neu anh cu la 1 link https ben ngoai)
                // SUA loi NullPointerException/StringIndexOutOfBounds cua de bai goc:
                // de bai goc goi truc tiep category.getImages().substring(0,5) - se
                // NEM LOI neu getImages() dang null hoac ngan hon 5 ky tu (vd "a.png").
                if (isLocalFile(fileold)) {
                    deleteFileQuietly(uploadPath + File.separator + fileold);
                }

                String fname = saveUploadedFile(part, uploadPath);
                category.setImages(fname);

            } else if (images != null && !images.isBlank()) {
                category.setImages(images);
            } else {
                category.setImages(fileold);
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        }

        // dua model vao phuong thuc update
        cateService.update(category);
        // chuyen trang
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    /** true neu chuoi anh la ten file luu noi bo (khong phai link http/https ben ngoai) */
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
            // Khong lam gian doan luong update chi vi xoa file cu that bai
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

    // Part khong co san getFileName() (ban cu cua Servlet API), phai tu boc
    // tach tu header Content-Disposition
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
}
