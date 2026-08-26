package bt02.vn.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

/**
 * Entity JPA cho bang "categories" - Bai tap 01 (24/06/2026) "CRUD voi JPA
 * 3.0 va Jakarta 6.0". Bang MOI hoan toan, tach biet voi bang "Category"
 * cu (cate_id/cate_name/icon) cua bt2-servlet-jsp. KHONG lam quan he Video
 * (bo theo yeu cau).
 *
 * KHONG dung Lombok (khong con @Data/@NoArgsConstructor/@AllArgsConstructor
 * nhu ban truoc) - tu viet tay constructor + getter/setter, giong dung
 * phong cach cac model/entity khac trong project (AppUser.java, cac model
 * ben bt2-servlet-jsp). Ly do bo Lombok: khong can cai them plugin vao
 * Eclipse, chac chan build/cham bai duoc tren bat ky may nao.
 */
@Entity
@Table(name = "categories")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private int categoryid;

    @Column(name = "CategoryName", columnDefinition = "nvarchar(50) not null")
    @NotEmpty(message = "Không được phép rỗng")
    private String categoryname;

    @Column(name = "Images", columnDefinition = "nvarchar(500) null")
    private String images;

    private int status;

    // So luong ton kho cua danh muc - them theo yeu cau SV (2026-08-26).
    // columnDefinition co "default 0" de neu Hibernate tu tao/them cot nay
    // thi co gia tri mac dinh, giong cach lam voi cac cot khac trong entity.
    @Column(name = "Quantity", columnDefinition = "int not null default 0")
    private int quantity;

    public Category() {
    }

    public Category(String categoryname, String images, int status) {
        this.categoryname = categoryname;
        this.images = images;
        this.status = status;
    }

    public Category(int categoryid, String categoryname, String images, int status) {
        this.categoryid = categoryid;
        this.categoryname = categoryname;
        this.images = images;
        this.status = status;
    }

    public Category(String categoryname, String images, int status, int quantity) {
        this.categoryname = categoryname;
        this.images = images;
        this.status = status;
        this.quantity = quantity;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Category [categoryid=" + categoryid + ", categoryname=" + categoryname
                + ", images=" + images + ", status=" + status + ", quantity=" + quantity + "]";
    }
}
