package bt02.vn.controller;

import bt02.vn.config.Constants;
import bt02.vn.entity.AppUser;
import bt02.vn.service.AuthServiceImpl;
import bt02.vn.service.IAuthService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * LoginServlet: ke thua CAU TRUC/Y TUONG tu LoginServlet.java ben
 * bt2-servlet-jsp (Session + Cookie remember-me + phan quyen roleid),
 * nhung viet lai HOAN TOAN tang duoi bang JPA that (AuthServiceImpl ->
 * UserDao -> EntityManager) thay vi JDBC, va dung bang "users" RIENG cua
 * Baitap02 (khong con dung chung bang "AppUser" nua) - dang nhap bang
 * tai khoan admin/123456 da duoc seed san trong database/users.sql.
 *
 * 2 ky thuat dang nhap:
 * 1) SESSION: session.setAttribute("username",...) - luu O PHIA SERVER,
 *    chi ton tai trong phien lam viec hien tai.
 * 2) COOKIE "Ghi nho dang nhap": neu tick checkbox, server gui 1 Cookie
 *    chua username ve trinh duyet, song toi 30 ngay. Lan sau vao lai
 *    "/login" du da dong han trinh duyet, server doc duoc Cookie nay
 *    (doGet ben duoi) va tu tao Session moi, khong can dang nhap lai.
 *    CHI luu username vao Cookie, KHONG BAO GIO luu mat khau vi ly do
 *    bao mat (Cookie co the bi doc trom qua DevTools/XSS).
 */
@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IAuthService authService;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Da dang nhap roi (con Session) -> khoi can hien lai form login
        HttpSession existingSession = req.getSession(false);
        if (existingSession != null && existingSession.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
            return;
        }

        // Chua co Session -> kiem tra Cookie "remember_username"
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.COOKIE_REMEMBER_USERNAME)) {
                    AppUser user = authService.findByUsername(cookie.getValue());
                    if (user != null) {
                        HttpSession session = req.getSession(true);
                        session.setAttribute("username", user.getUsername());
                        session.setAttribute("roleid", user.getRoleid());
                        resp.sendRedirect(req.getContextPath() + "/admin/home");
                        return;
                    }
                }
            }
        }

        showLoginForm(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember"); // "on" neu checkbox duoc tick

        boolean isValid = authService.login(username, password);

        if (isValid) {
            AppUser user = authService.findByUsername(username);

            HttpSession session = req.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("roleid", user.getRoleid());

            if ("on".equals(remember)) {
                saveRememberMeCookie(resp, username);
            }

            if (user.getRoleid() == Constants.ROLE_ADMIN) {
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else {
                // Bai nay khong co trang "/home" cho user thuong (chi tap trung
                // vao CRUD Category cho admin) nen bao loi ngay tai form login.
                session.invalidate();
                req.setAttribute("loginError", "Tai khoan nay khong co quyen quan tri!");
                showLoginForm(req, resp);
            }
        } else {
            req.setAttribute("loginError", "Sai ten dang nhap hoac mat khau!");
            showLoginForm(req, resp);
        }
    }

    private void showLoginForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // loginError co the da duoc AuthFilter set vao Session (vd khi bi da
        // ve tu "/admin/*" do khong du quyen) - chuyen no thanh request
        // attribute de login.jsp hien thi, roi xoa khoi Session luon.
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("loginError") != null) {
            req.setAttribute("loginError", session.getAttribute("loginError"));
            session.removeAttribute("loginError");
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        dispatcher.forward(req, resp);
    }

    private void saveRememberMeCookie(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(Constants.COOKIE_REMEMBER_USERNAME, username);
        int thirtyDaysInSeconds = 30 * 24 * 60 * 60;
        cookie.setMaxAge(thirtyDaysInSeconds);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
