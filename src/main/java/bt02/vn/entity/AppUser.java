package bt02.vn.entity;

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
 * Ten cot van giu nguyen kieu snake/lowercase quen thuoc, nhung ten BANG
 * doi thanh "users" (chu thuong, giong tinh than dat ten "categories")
 * de KHONG bi trung/dung chung voi bang "AppUser" cua project khac.
 *
 * Neu Hibernate (hibernate.hbm2ddl.auto=update) khong tu tao bang nay khi
 * chay lan dau (da tung gap voi bang "categories"), chay tay script
 * database/users.sql (co san du lieu seed: tai khoan admin/123456).
 */
@Entity
@Table(name = "users")
public class AppUser {

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
}
