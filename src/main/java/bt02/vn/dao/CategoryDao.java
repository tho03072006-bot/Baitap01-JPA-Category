package bt02.vn.dao;

import java.util.List;

import bt02.vn.config.JpaConfig;
import bt02.vn.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

/**
 * CategoryDao: cai dat ICategoryDao bang JPA/Hibernate THAT SU (dung
 * EntityManager.persist()/merge()/find()/remove() va JPQL), KHONG con
 * viet PreparedStatement/SQL thuan tay nhu ben bt2-servlet-jsp nua - dung
 * theo dung yeu cau cong nghe cua bai tap nay.
 *
 * So voi code mau trong de bai, ben duoi co SUA 3 LOI (giai thich ro trong
 * comment tung method) va THEM finally { enma.close(); } cho nhung method
 * de bai goc thieu (findAll, findById, count, findAll phan trang) - EntityManager
 * la tai nguyen (giong Connection cua JDBC), khong dong se ri tai nguyen
 * (memory leak) khi ung dung chay lau.
 */
public class CategoryDao implements ICategoryDao {

    @Override
    public void insert(Category category) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(category); // insert vao bang
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
    public void update(Category category) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(category); // update vao bang
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
    public void delete(int cateid) throws Exception {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Category category = enma.find(Category.class, cateid);
            if (category != null) {
                enma.remove(category);
            } else {
                throw new Exception("Không tìm thấy");
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
    public Category findById(int cateid) {
        // SUA so voi de bai: de bai goc KHONG dong enma o method nay -> them
        // finally { enma.close(); } de tranh ri EntityManager
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            return enma.find(Category.class, cateid);
        } finally {
            enma.close();
        }
    }

    @Override
    public Category findByCategoryname(String name) throws Exception {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT c FROM Category c WHERE c.categoryname = :catename";
        try {
            TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
            query.setParameter("catename", name);
            // SUA so voi de bai: getSingleResult() se NEM NoResultException
            // (khong tra ve null) khi khong tim thay dong nao khop - bat rieng
            // truong hop nay thanh "khong tim thay" thay vi de loi runtime lan ra ngoai
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category> findAll() {
        // SUA so voi de bai: them finally { enma.close(); }
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<Category> query = enma.createNamedQuery("Category.findAll", Category.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category> searchByName(String catname) {
        // SUA 2 LOI so voi de bai:
        // 1) de bai viet JPQL la "c.catename" - SAI ten field, Category KHONG
        //    co field ten "catename" (chi co "categoryname") -> se nem loi
        //    "could not resolve property: catename" luc chay.
        // 2) de bai dat ten tham so JPQL la ":catname" nhung lai
        //    query.setParameter("catename", ...) (ten khac nhau) -> se nem loi
        //    "Parameter with that name did not exist".
        // Da sua dong bo lai thanh ":catname" ca 2 cho, va dung dung field
        // "categoryname". Cung them finally { enma.close(); } bi thieu.
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            String jpql = "SELECT c FROM Category c WHERE c.categoryname LIKE :catname";
            TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
            query.setParameter("catname", "%" + catname + "%");
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category> findAll(int page, int pagesize) {
        // SUA so voi de bai: them finally { enma.close(); }
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<Category> query = enma.createNamedQuery("Category.findAll", Category.class);
            query.setFirstResult(page * pagesize);
            query.setMaxResults(pagesize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public int count() {
        // SUA so voi de bai: them finally { enma.close(); }
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            String jpql = "SELECT count(c) FROM Category c";
            Query query = enma.createQuery(jpql);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }
}
