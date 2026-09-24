package bt02.vn.dao;

import java.util.List;

import bt02.vn.entity.Product;

/**
 * IProductDao: interface tang truy xuat du lieu cho Product, dung dung
 * cau truc interface-based DAO da dung cho ICategoryDao trong project.
 */
public interface IProductDao {

    void insert(Product product);

    void update(Product product);

    void delete(int productid) throws Exception;

    Product findById(int productid);

    List<Product> findAll();

    /** Lay ra "limit" san pham MOI NHAT (sap xep theo CreatedDate giam dan) - dung cho trang chu. */
    List<Product> findLatest(int limit);

    /** Lay 1 "trang" san pham (page bat dau tu 0) - dung cho phan trang tai "/product". */
    List<Product> findPage(int page, int pagesize);

    int count();
}
