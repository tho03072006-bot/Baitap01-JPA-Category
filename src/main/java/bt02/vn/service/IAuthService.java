package bt02.vn.service;

import bt02.vn.entity.AppUser;

/**
 * IAuthService: nghiep vu xac thuc dang nhap + (Bai tap 03) dang ky tai
 * khoan kem kich hoat OTP qua email + quen mat khau bang OTP qua email.
 */
public interface IAuthService {

    /**
     * Kiem tra username/password co dung khong (so sanh truc tiep, du an
     * demo hoc tap nen chua ma hoa mat khau bang BCrypt/hash). LUU Y: method
     * nay CHUA kiem tra tai khoan da kich hoat (enabled) hay chua - viec do
     * do LoginServlet tu kiem tra rieng sau khi login() tra true, de con
     * phan biet ro 2 truong hop bao loi khac nhau ("sai mat khau" va "chua
     * kich hoat tai khoan") cho nguoi dung.
     */
    boolean login(String username, String password);

    /** Lay lai AppUser theo username. */
    AppUser findByUsername(String username);

    /** Lay lai AppUser theo email - dung cho dang ky (kiem tra trung) va quen mat khau. */
    AppUser findByEmail(String email);

    /**
     * Dang ky 1 tai khoan MOI (enabled = 0, tai khoan thuong ROLE_USER),
     * sinh ma OTP va gui qua email nguoi dung vua nhap.
     * @throws Exception neu username/email da ton tai, hoac du lieu nhap khong hop le
     */
    void register(String username, String password, String fullname, String email) throws Exception;

    /**
     * Xac nhan ma OTP dang ky de kich hoat tai khoan (enabled = 0 -> 1).
     * @return true neu OTP dung va con hieu luc (da kich hoat thanh cong)
     */
    boolean verifyRegisterOtp(String username, String otp);

    /** Sinh OTP moi va gui lai qua email (khi OTP cu het han hoac nguoi dung khong nhan duoc mail). */
    void resendOtp(String username) throws Exception;

    /**
     * Buoc 1 cua "Quen mat khau": tim tai khoan theo username HOAC email,
     * sinh OTP va gui qua email cua tai khoan do.
     * @throws Exception neu khong tim thay tai khoan, hoac tai khoan khong co email
     */
    void forgotPassword(String usernameOrEmail) throws Exception;

    /**
     * Buoc 2 cua "Quen mat khau": xac nhan OTP dung + con hieu luc thi
     * doi sang mat khau moi.
     * @return true neu doi mat khau thanh cong
     */
    boolean resetPassword(String usernameOrEmail, String otp, String newPassword);
}
