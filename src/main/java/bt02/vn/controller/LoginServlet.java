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
 * (Bai tap 03) SUA 2 CHO so voi ban Bai tap 01/02 truoc do:
 * 1) Them kiem tra "user.getEnabled() == 0" - tai khoan MOI dang ky (qua
 *    RegisterServlet) phai xac thuc OTP qua email THI MOI duoc dang nhap,
 *    neu chua se bao loi va huong dan sang trang xac thuc OTP.
 * 2) Tai khoan thuong (ROLE_USER, vd khach hang tu dang ky) GIO DUOC PHEP
 *    dang nhap va chuyen huong ve trang chu "/" (truoc day - khi Baitap02
 *    chi co CRUD Category cho admin - moi tai khoan khong phai admin deu
 *    bi tu choi ngay tai day; nay Bai tap 03 them mang khach hang xem san
 *    pham nen phai mo lai cho ROLE_USER).
 *
 * 2 ky thuat dang nhap (giu nguyen tu ban truoc):
 * 1) SESSION: session.setAttribute("username",...) - luu O PHIA SERVER,
 *    chi ton tai trong phien lam viec hien tai.
 * 2) COOKIE "Ghi nho dang nhap": neu tick checkbox, server gui 1 Cookie
 *    chua username ve trinh duyet, song toi 30 ngay. CHI luu username,
 *    KHONG BAO GIO luu mat khau vi ly do bao mat.
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

        // Da dang nhap roi (con Session) -> khong can hien lai form login,
        // dua thang ve dung khu vuc theo vai tro.
        HttpSession existingSession = req.getSession(false);
        if (existingSession != null && existingSession.getAttribute("username") != null) {
            redirectByRole(req, resp, existingSession);
            return;
        }

        // Chua co Session -> kiem tra Cookie "remember_username"
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.COOKIE_REMEMBER_USERNAME)) {
                    AppUser user = authService.findByUsername(cookie.getValue());
                    if (user != null && user.getEnabled() == 1) {
                        HttpSession session = req.getSession(true);
                        session.setAttribute("username", user.getUsername());
                        session.setAttribute("roleid", user.getRoleid());
                        redirectByRole(req, resp, session);
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

        if (!isValid) {
            req.setAttribute("loginError", "Sai tên đăng nhập hoặc mật khẩu!");
            showLoginForm(req, resp);
            return;
        }

        AppUser user = authService.findByUsername(username);

        // (Bai tap 03) Tai khoan dang ky qua RegisterServlet bat dau o
        // enabled = 0, phai xac thuc OTP qua email truoc thi moi cho dang
        // nhap - neu chua, khong tao Session, dua sang trang xac thuc OTP.
        if (user.getEnabled() == 0) {
            req.setAttribute("loginError",
                    "Tài khoản chưa được kích hoạt. Vui lòng kiểm tra email để xác thực mã OTP trước khi đăng nhập.");
            req.setAttribute("unverifiedUsername", username);
            showLoginForm(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("username", user.getUsername());
        session.setAttribute("roleid", user.getRoleid());

        if ("on".equals(remember)) {
            saveRememberMeCookie(resp, username);
        }

        redirectByRole(req, resp, session);
    }

    /** Dua nguoi dung ve dung noi theo vai tro: Admin -> Dashboard quan tri, User thuong -> Trang chu. */
    private void redirectByRole(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws IOException {
        Object roleAttr = session.getAttribute("roleid");
        int roleid = (roleAttr instanceof Integer) ? (Integer) roleAttr : Constants.ROLE_USER;
        if (roleid == Constants.ROLE_ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
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
