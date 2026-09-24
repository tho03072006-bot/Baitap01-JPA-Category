package bt02.vn.service;

import java.time.LocalDateTime;

import bt02.vn.config.Constants;
import bt02.vn.dao.IUserDao;
import bt02.vn.dao.UserDao;
import bt02.vn.entity.AppUser;
import bt02.vn.util.MailService;
import bt02.vn.util.OtpUtil;

public class AuthServiceImpl implements IAuthService {

    private final IUserDao userDao;

    public AuthServiceImpl() {
        this.userDao = new UserDao();
    }

    @Override
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        AppUser user = userDao.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public AppUser findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public AppUser findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void register(String username, String password, String fullname, String email) throws Exception {
        if (username == null || username.isBlank()) {
            throw new Exception("Tên đăng nhập không được để trống");
        }
        if (password == null || password.isBlank()) {
            throw new Exception("Mật khẩu không được để trống");
        }
        if (email == null || email.isBlank()) {
            throw new Exception("Email không được để trống (bắt buộc để nhận mã OTP kích hoạt)");
        }
        // Quy tac nghiep vu: khong cho dang ky trung username hoac trung email.
        if (userDao.findByUsername(username) != null) {
            throw new Exception("Tên đăng nhập \"" + username + "\" đã được sử dụng");
        }
        if (userDao.findByEmail(email) != null) {
            throw new Exception("Email \"" + email + "\" đã được đăng ký cho tài khoản khác");
        }

        AppUser user = new AppUser(username, password, fullname, email);
        user.setRoleid(Constants.ROLE_USER);
        // Tai khoan MOI dang ky luon bat dau o trang thai CHUA kich hoat -
        // phai xac thuc OTP gui qua email thi moi dang nhap duoc (xem
        // LoginServlet.doPost()).
        user.setEnabled(0);
        applyNewOtp(user, Constants.OTP_PURPOSE_REGISTER);

        userDao.insert(user);
        MailService.sendOtpEmail(email, user.getOtpCode(), "kích hoạt tài khoản");
    }

    @Override
    public boolean verifyRegisterOtp(String username, String otp) {
        AppUser user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        if (user.getEnabled() == 1) {
            // Da kich hoat tu truoc roi (vd nguoi dung bam xac nhan 2 lan) ->
            // coi nhu thanh cong luon, khong bao loi gay kho hieu.
            return true;
        }
        if (!isOtpValid(user, otp, Constants.OTP_PURPOSE_REGISTER)) {
            return false;
        }
        user.setEnabled(1);
        clearOtp(user);
        userDao.update(user);
        return true;
    }

    @Override
    public void resendOtp(String username) throws Exception {
        AppUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new Exception("Không tìm thấy tài khoản này");
        }
        if (user.getEnabled() == 1) {
            throw new Exception("Tài khoản này đã được kích hoạt trước đó");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new Exception("Tài khoản này chưa có email để nhận lại OTP");
        }
        applyNewOtp(user, Constants.OTP_PURPOSE_REGISTER);
        userDao.update(user);
        MailService.sendOtpEmail(user.getEmail(), user.getOtpCode(), "kích hoạt tài khoản");
    }

    @Override
    public void forgotPassword(String usernameOrEmail) throws Exception {
        AppUser user = findByUsernameOrEmail(usernameOrEmail);
        if (user == null) {
            throw new Exception("Không tìm thấy tài khoản khớp với thông tin này");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new Exception("Tài khoản này chưa có email để gửi OTP đặt lại mật khẩu");
        }
        applyNewOtp(user, Constants.OTP_PURPOSE_RESET);
        userDao.update(user);
        MailService.sendOtpEmail(user.getEmail(), user.getOtpCode(), "đặt lại mật khẩu");
    }

    @Override
    public boolean resetPassword(String usernameOrEmail, String otp, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            return false;
        }
        AppUser user = findByUsernameOrEmail(usernameOrEmail);
        if (user == null) {
            return false;
        }
        if (!isOtpValid(user, otp, Constants.OTP_PURPOSE_RESET)) {
            return false;
        }
        user.setPassword(newPassword);
        clearOtp(user);
        userDao.update(user);
        return true;
    }

    /** Tim tai khoan theo username TRUOC, khong co thi thu tim theo email - dung cho quen mat khau. */
    private AppUser findByUsernameOrEmail(String usernameOrEmail) {
        if (usernameOrEmail == null || usernameOrEmail.isBlank()) {
            return null;
        }
        AppUser user = userDao.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userDao.findByEmail(usernameOrEmail);
        }
        return user;
    }

    /** Sinh 1 ma OTP moi + han su dung, gan truc tiep vao doi tuong AppUser (chua luu xuong DB). */
    private void applyNewOtp(AppUser user, String purpose) {
        user.setOtpCode(OtpUtil.generateOtp());
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(Constants.OTP_EXPIRY_MINUTES));
        user.setOtpPurpose(purpose);
    }

    /** Xoa ma OTP sau khi da dung xong (xac thuc thanh cong) de khong the dung lai lan 2. */
    private void clearOtp(AppUser user) {
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        user.setOtpPurpose(null);
    }

    /** Kiem tra 1 ma OTP nguoi dung nhap co KHOP, DUNG MUC DICH, va CHUA HET HAN hay khong. */
    private boolean isOtpValid(AppUser user, String otpInput, String expectedPurpose) {
        if (otpInput == null || otpInput.isBlank()) {
            return false;
        }
        if (user.getOtpCode() == null || !user.getOtpCode().equals(otpInput)) {
            return false;
        }
        if (!expectedPurpose.equals(user.getOtpPurpose())) {
            return false;
        }
        if (user.getOtpExpiry() == null || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }
}
