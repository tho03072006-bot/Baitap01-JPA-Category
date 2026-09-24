package bt02.vn.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

/**
 * Entity JPA cho bang "products" - phan MOI cua Bai tap 03 (them bang
 * products vao database voi moi lien he 1-N voi bang category truoc do:
 * 1 Category co NHIEU Product, 1 Product chi thuoc VE 1 Category).
 *
 * Ve phia JPA, quan he 1-N nay duoc khai bao O PHIA "N" (Product) bang
 * @ManyToOne + @JoinColumn tro toi khoa chinh cua Category - KHONG can
 * khai bao nguoc lai @OneToMany ben Category.java vi bai nay khong can
 * di tu 1 Category liet ke ra danh sach Product cua no (Product tu truy
 * van bang CategoryId la du, xem ProductDao.findByCategory neu can mo
 * rong sau nay).
 *
 * KHONG dung Lombok, viet tay constructor/getter/setter giong Category.java.
 */
@Entity
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId")
    private int productid;

    @NotEmpty(message = "Không được phép rỗng")
    @Column(name = "ProductName", columnDefinition = "nvarchar(200) not null")
    private String productname;

    @Column(name = "Price", columnDefinition = "float not null default 0")
    private double price;

    @Column(name = "Description", columnDefinition = "nvarchar(1000) null")
    private String description;

    @Column(name = "Images", columnDefinition = "nvarchar(500) null")
    private String images;

    @Column(name = "Quantity", columnDefinition = "int not null default 0")
    private int quantity;

    @Column(name = "CreatedDate", columnDefinition = "datetime2 not null")
    private LocalDateTime createddate;

    // Quan he N-1: rat nhieu Product co the tro toi CUNG 1 Category.
    // fetch = EAGER de khi lay Product len (vd hien danh sach san pham),
    // ten Category di kem duoc nap san luon, JSP khong can query them lan
    // nua (phu hop voi quy mo nho cua bai tap nay).
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category category;

    public Product() {
    }

    public Product(String productname, double price, String description, String images,
            int quantity, Category category) {
        this.productname = productname;
        this.price = price;
        this.description = description;
        this.images = images;
        this.quantity = quantity;
        this.category = category;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreateddate() {
        return createddate;
    }

    public void setCreateddate(LocalDateTime createddate) {
        this.createddate = createddate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product [productid=" + productid + ", productname=" + productname + ", price=" + price
                + ", quantity=" + quantity + ", category=" + (category != null ? category.getCategoryname() : null)
                + "]";
    }
}
