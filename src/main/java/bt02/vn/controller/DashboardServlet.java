package bt02.vn.controller;

import java.io.IOException;

import bt02.vn.service.CategoryServiceImpl;
import bt02.vn.service.ICategoryService;
import bt02.vn.service.IProductService;
import bt02.vn.service.ProductServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * DashboardServlet: trang chu "/admin/home", hien 1 vai so lieu tong quan
 * (dashboard) - dung theo dung cau truc dashboard cua bt2-servlet-jsp
 * (AdminHomeServlet) nhung co so lieu that lay qua JPA.
 *
 * (Bai tap 03) THEM so lieu tong so Products ben canh Category da co.
 */
@WebServlet(urlPatterns = { "/admin/home" })
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ICategoryService cateService;
    private IProductService productService;

    @Override
    public void init() throws ServletException {
        this.cateService = new CategoryServiceImpl();
        this.productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int totalCategory = cateService.count();
        int totalProduct = productService.count();
        req.setAttribute("totalCategory", totalCategory);
        req.setAttribute("totalProduct", totalProduct);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp");
        dispatcher.forward(req, resp);
    }
}
