package bt02.vn.dao;

import java.time.LocalDateTime;
import java.util.List;

import bt02.vn.config.JpaConfig;
import bt02.vn.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

/**
 * ProductDao: cai dat IProductDao bang JPA/Hibernate THAT SU (EntityManager
 * + JPQL), dung dung tinh than CategoryDao (persist/merge/find/remove,
 * moi method tu mo/dong 1 EntityManager rieng, co try/finally { enma.close() }
 * day du de tranh ri tai nguyen).
 */
public class ProductDao implements IProductDao {

    @Override
    public void insert(Product product) {
        // Dam bao luon co CreatedDate (dung de sap xep "moi nhat") ngay ca
        // khi tang Service quen gan - phong ngua, khong thay the cho viec
        // Service da tu gan gia tri nay truoc khi goi insert().
        if (product.getCreateddate() == null) {
            product.setCreateddate(LocalDateTime.now());
        }
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(product);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void update(Product product) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(product);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void delete(int productid) throws Exception {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Product product = enma.find(Product.class, productid);
            if (product != null) {
                enma.remove(product);
            } else {
                throw new Exception("Không tìm thấy sản phẩm");
            }
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public Product findById(int productid) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            return enma.find(Product.class, productid);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findAll() {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            String jpql = "SELECT p FROM Product p ORDER BY p.productid DESC";
            TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findLatest(int limit) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            // Sap xep theo CreatedDate giam dan (moi nhat truoc); them tieu
            // chi phu productid DESC de cac san pham cung 1 thoi diem tao
            // (vd du lieu seed) van co thu tu on dinh, khong bi dao lon moi
            // lan query.
            String jpql = "SELECT p FROM Product p ORDER BY p.createddate DESC, p.productid DESC";
            TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findPage(int page, int pagesize) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            String jpql = "SELECT p FROM Product p ORDER BY p.createddate DESC, p.productid DESC";
            TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
            query.setFirstResult(page * pagesize);
            query.setMaxResults(pagesize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public int count() {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            String jpql = "SELECT count(p) FROM Product p";
            Query query = enma.createQuery(jpql);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }
}
