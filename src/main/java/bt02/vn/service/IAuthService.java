package bt02.vn.service;

import bt02.vn.entity.AppUser;

/**
 * IAuthService: nghiep vu xac thuc dang nhap, dat tang giua Controller
 * (LoginServlet) va DAO (UserDao) - dung interface cho dong bo voi
 * ICategoryService da co trong project.
 */
public interface IAuthService {

    /**
     * Kiem tra username/password co dung khong (so sanh truc tiep, giong
     * AuthService.login() ben bt2-servlet-jsp - du an demo hoc tap nen
     * chua ma hoa mat khau bang BCrypt/hash).
     */
    boolean login(String username, String password);

    /**
     * Lay lai AppUser theo username - dung sau khi login() tra true de
     * lay roleid quyet dinh redirect, va dung khi doc Cookie "remember_username"
     * de tu tao lai Session ma khong can nhap lai mat khau.
     */
    AppUser findByUsername(String username);
}
