package bt02.vn.service;

import java.util.List;

import bt02.vn.entity.Product;

public interface IProductService {

    void insert(Product product);

    void update(Product product);

    void delete(int productid) throws Exception;

    Product findById(int productid);

    List<Product> findAll();

    List<Product> findLatest(int limit);

    List<Product> findPage(int page, int pagesize);

    int count();

    /** Tong so trang can co de hien thi het tat ca san pham, voi "pagesize" san pham/trang. */
    int countPages(int pagesize);
}
