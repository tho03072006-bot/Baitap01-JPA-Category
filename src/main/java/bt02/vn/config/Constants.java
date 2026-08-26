package bt02.vn.config;

/**
 * Constants: hang so dung chung cho project. Dat ten PascalCase dung chuan
 * Java (khac voi ban tutorial goc viet "constants" chu thuong - da sua lai
 * cho dung Java Naming Convention).
 */
public class Constants {

    private Constants() {
    }

    /**
     * Thu muc vat ly luu anh Category upload len, nam NGOAI thu muc webapp
     * (giong cach lam cua AppConfig.UPLOAD_DIR ben project bt2-servlet-jsp)
     * de anh khong bi mat moi khi build/deploy lai project.
     */
    public static final String DIR = "D:\\WEB\\uploads\\Baitap02\\categories";

    /**
     * Ten Cookie dung cho chuc nang "Ghi nho dang nhap" - giong het
     * AppConfig.COOKIE_REMEMBER_USERNAME ben bt2-servlet-jsp.
     */
    public static final String COOKIE_REMEMBER_USERNAME = "remember_username";

    /**
     * PHAN QUYEN (roleid) - dung cho bang "users" RIENG cua Baitap02 (doc
     * lap voi bang "AppUser" ben bt2-servlet-jsp). Gia tri (1 = admin,
     * 5 = user thuong) chi la QUY UOC giong ben bt2-servlet-jsp cho de
     * nho, khong con y nghia "dung chung du lieu" nua.
     */
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 5;
}
