package bt02.vn.filter;

import bt02.vn.config.Constants;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * AuthFilter: chan TAT CA request vao "/admin/*" (Dashboard + Quan ly
 * Category), y het AuthFilter.java ben bt2-servlet-jsp:
 *   1) Chua dang nhap (chua co Session) -> da ve "/login".
 *   2) Da dang nhap nhung khong phai admin (roleid != Constants.ROLE_ADMIN)
 *      -> khong cho vao, da ve lai "/login" kem thong bao.
 */
@WebFilter(urlPatterns = { "/admin/*" })
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("username") != null;

        if (!isLoggedIn) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return; // QUAN TRONG: return ngay, khong goi chain.doFilter() nua
        }

        Object roleAttr = session.getAttribute("roleid");
        int roleid = (roleAttr instanceof Integer) ? (Integer) roleAttr : Constants.ROLE_USER;

        if (roleid != Constants.ROLE_ADMIN) {
            session.setAttribute("loginError", "Tai khoan nay khong co quyen quan tri!");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
