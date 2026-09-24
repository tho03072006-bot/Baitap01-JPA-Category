package bt02.vn.service;

import java.time.LocalDateTime;
import java.util.List;

import bt02.vn.dao.IProductDao;
import bt02.vn.dao.ProductDao;
import bt02.vn.entity.Product;

/**
 * ProductServiceImpl: tang trung gian giua Controller va Dao cho Product,
 * dung dung tinh than CategoryServiceImpl - Controller KHONG duoc goi
 * thang xuong Dao, luon di qua Service.
 */
public class ProductServiceImpl implements IProductService {

    private final IProductDao productDao = new ProductDao();

    @Override
    public void insert(Product product) {
        // Quy tac nghiep vu: luon gan CreatedDate = thoi diem tao MOI (khong
        // cho phep form/nguoi dung tu truyen ngay tao) de dam bao thu tu
        // "moi nhat" o trang chu luon chinh xac, khong bi gia mao.
        product.setCreateddate(LocalDateTime.now());
        productDao.insert(product);
    }

    @Override
    public void update(Product product) {
        // Quy tac nghiep vu: giu nguyen CreatedDate goc khi sua san pham
        // (khong doi lai thanh "moi nhat" chi vi admin sua gia/so luong).
        Product existing = productDao.findById(product.getProductid());
        if (existing != null) {
            product.setCreateddate(existing.getCreateddate());
            productDao.update(product);
        }
    }

    @Override
    public void delete(int productid) throws Exception {
        productDao.delete(productid);
    }

    @Override
    public Product findById(int productid) {
        return productDao.findById(productid);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findLatest(int limit) {
        return productDao.findLatest(limit);
    }

    @Override
    public List<Product> findPage(int page, int pagesize) {
        return productDao.findPage(page, pagesize);
    }

    @Override
    public int count() {
        return productDao.count();
    }

    @Override
    public int countPages(int pagesize) {
        int total = productDao.count();
        // Cong thuc "chia lam tron len" (ceiling division) khong dung so
        // thuc: (total + pagesize - 1) / pagesize. Vi du total=13, pagesize=6
        // -> (13+5)/6 = 3 trang (6+6+1).
        return (total + pagesize - 1) / pagesize;
    }
}
