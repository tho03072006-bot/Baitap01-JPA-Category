package bt02.vn.controller;

import java.io.IOException;

import bt02.vn.entity.Product;
import bt02.vn.service.IProductService;
import bt02.vn.service.ProductServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ProductDetailServlet: hien thi chi tiet DUNG 1 san pham, duoc goi khi
 * bam chuot vao 1 san pham tren trang chu ("/") hoac tren trang danh sach
 * ("/product") - yeu cau 4.4 cua Bai tap 03. De bai khong bat buoc URL cu
 * the cho trang chi tiet nen dung "/product-detail?id=..." (khac voi
 * "/product" - la URL BAT BUOC danh rieng cho trang danh sach).
 */
@WebServlet(urlPatterns = { "/product-detail" })
public class ProductDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Product product = productService.findById(id);
        if (product == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        req.setAttribute("product", product);
        req.getRequestDispatcher("/WEB-INF/views/public/product-detail.jsp").forward(req, resp);
    }
}
