package bt02.vn.controller;

import java.io.IOException;

import bt02.vn.service.CategoryServiceImpl;
import bt02.vn.service.ICategoryService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * DashboardServlet: trang chu "/admin/home", hien 1 vai so lieu tong quan
 * (dashboard) lay tu ICategoryService - dung theo dung cau truc dashboard
 * cua bt2-servlet-jsp (AdminHomeServlet) nhung co so lieu that lay qua JPA
 * (ICategoryService.count()) thay vi chi la trang chao mung tinh.
 */
@WebServlet(urlPatterns = { "/admin/home" })
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ICategoryService cateService;

    @Override
    public void init() throws ServletException {
        this.cateService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int totalCategory = cateService.count();
        req.setAttribute("totalCategory", totalCategory);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp");
        dispatcher.forward(req, resp);
    }
}
