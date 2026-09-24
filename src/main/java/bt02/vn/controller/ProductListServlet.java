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
 * ProductListServlet: hien thi TAT CA san pham, phan trang 6 san pham /
 * trang, tai DUNG URL "/product" - dung nguyen van theo yeu cau 4.3 cua
 * Bai tap 03 ("... hien thi tren trang co URL la /product").
 *
 * Tham so query "page" la SO TRANG NGUOI DUNG THAY (bat dau tu 1, than
 * thien voi nguoi dung); ben trong chuyen ve "page - 1" (bat dau tu 0)
 * truoc khi goi ProductService.findPage(), vi setFirstResult() cua JPA
 * tinh theo so THU TU BAN GHI (0-based).
 */
@WebServlet(urlPatterns = { "/product" })
public class ProductListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int PAGE_SIZE = 6;

    private IProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int pageNumber = parsePositiveIntOrDefault(req.getParameter("page"), 1);

        int totalPages = productService.countPages(PAGE_SIZE);
        if (totalPages < 1) {
            totalPages = 1; // luon co it nhat 1 "trang" (co the rong) de JSP hien thi nut trang de dang hon
        }
        if (pageNumber > totalPages) {
            pageNumber = totalPages;
        }

        List<Product> products = productService.findPage(pageNumber - 1, PAGE_SIZE);

        req.setAttribute("products", products);
        req.setAttribute("currentPage", pageNumber);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/WEB-INF/views/public/product-list.jsp").forward(req, resp);
    }

    private int parsePositiveIntOrDefault(String value, int defaultValue) {
        try {
            int parsed = Integer.parseInt(value);
            return parsed >= 1 ? parsed : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
