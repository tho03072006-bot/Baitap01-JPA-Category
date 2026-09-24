package bt02.vn.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Entity JPA anh xa toi bang "users" - bang RIENG cua Baitap02, KHONG con
 * dung chung bang "AppUser" ben bt2-servlet-jsp nua (Baitap02 phai la 1
 * bai lam DOC LAP, chi "ke thua" CAU TRUC/Y TUONG tu ben bt2-servlet-jsp
 * (model User.java: username/password/fullName/email/roleid) roi VIET
 * LAI HOAN TOAN bang JPA, giong het cach lam voi Category/categories
 * truoc do (bang "categories" cung la bang moi, tach biet voi "Category").
 *
 * (Bai tap 03) THEM 4 FIELD MOI phuc vu dang ky + kich hoat tai khoan
 * bang OTP qua email, va quen mat khau bang OTP qua email:
 *   - enabled   : 0 = tai khoan MOI dang ky, CHUA xac thuc OTP (chua duoc
 *                 phep dang nhap) | 1 = da kich hoat (hoac la tai khoan
 *                 cu tao truoc khi co tinh nang nay, xem database/users.sql).
 *   - otpCode   : ma OTP 6 chu so dang cho xac nhan gan nhat (dung chung
 *                 cho ca dang ky lan quen mat khau, vi 1 nguoi dung khong
 *                 the lam 2 viec nay CUNG LUC).
 *   - otpExpiry : thoi diem ma OTP tren HET HAN (xem Constants.OTP_EXPIRY_MINUTES).
 *   - otpPurpose: OTP nay dung de lam gi - "REGISTER" (kich hoat tai khoan
 *                 moi) hay "RESET" (quen mat khau) - xem Constants.OTP_PURPOSE_*.
 */
@Entity
@Table(name = "users")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // @NotNull la annotation cua Jakarta Validation (goi trong pom.xml qua
    // hibernate-validator + jakarta.validation-api), dung dung nhu slide
    // 07_JPA_Servlet.pdf trang 27 minh hoa.
    @NotNull(message = "Ten dang nhap khong duoc de trong")
    @Column(name = "username", nullable = false, unique = true, columnDefinition = "nvarchar(100) not null")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "nvarchar(255) not null")
    private String password;

    @Column(name = "fullname", columnDefinition = "nvarchar(255) null")
    private String fullName;

    @Column(name = "email", columnDefinition = "nvarchar(255) null")
    private String email;

    // roleid: 1 = admin (vao duoc khu /admin/*), 5 = user thuong.
    // columnDefinition co "default 5" de neu Hibernate tu tao bang thi cot
    // nay co gia tri mac dinh, giong cach lam voi Category.status truoc do.
    @Column(name = "roleid", nullable = false, columnDefinition = "int not null default 5")
    private int roleid;

    // 0 = chua kich hoat (moi dang ky, dang cho xac thuc OTP), 1 = da kich hoat.
    // LUU Y: Java default cua "int" la 0 nhung DB dat DEFAULT 1 (de cac tai
    // khoan CU - tao truoc khi co tinh nang nay - tu dong duoc coi la da
    // kich hoat khi ALTER TABLE, xem database/users.sql). Tai khoan MOI dang
    // ky luon duoc code (AuthServiceImpl.register) SET TAY enabled = 0, khong
    // phu thuoc vao DEFAULT cua DB.
    @Column(name = "enabled", nullable = false, columnDefinition = "int not null default 1")
    private int enabled;

    @Column(name = "otp_code", columnDefinition = "nvarchar(10) null")
    private String otpCode;

    @Column(name = "otp_expiry", columnDefinition = "datetime2 null")
    private LocalDateTime otpExpiry;

    @Column(name = "otp_purpose", columnDefinition = "nvarchar(20) null")
    private String otpPurpose;

    public AppUser() {
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AppUser(String username, String password, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public LocalDateTime getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(LocalDateTime otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    public String getOtpPurpose() {
        return otpPurpose;
    }

    public void setOtpPurpose(String otpPurpose) {
        this.otpPurpose = otpPurpose;
    }
}
