package bt02.vn.controller;

import java.io.IOException;
import java.util.List;

import bt02.vn.entity.Product;
import bt02.vn.service.IProductService;
import bt02.vn.service.ProductServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * HomeServlet: TRANG CHU cong khai cho khach hang (khac hoan toan voi
 * DashboardServlet - la trang chu cua KHU VUC QUAN TRI "/admin/home").
 * Yeu cau 4.2 cua Bai tap 03: "Hien thi 10 san pham moi nhat len trang chu".
 *
 * index.jsp (welcome-file trong web.xml) forward request toi day, nen URL
 * nguoi dung thay tren trinh duyet van la "/" (context root), khong phai
 * "/home" - dung RequestDispatcher.forward (server-side), khong phai
 * sendRedirect, nen dia chi tren thanh trinh duyet KHONG doi.
 */
@WebServlet(urlPatterns = { "/home" })
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int LATEST_LIMIT = 10;

    private IProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> latestProducts = productService.findLatest(LATEST_LIMIT);
        req.setAttribute("latestProducts", latestProducts);
        req.getRequestDispatcher("/WEB-INF/views/public/home.jsp").forward(req, resp);
    }
}
