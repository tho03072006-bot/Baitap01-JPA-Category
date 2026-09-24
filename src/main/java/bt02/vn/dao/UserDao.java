package bt02.vn.dao;

import bt02.vn.config.JpaConfig;
import bt02.vn.entity.AppUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

/**
 * UserDao: cai dat IUserDao bang JPA/Hibernate (EntityManager + JPQL),
 * y het tinh than CategoryDao - KHONG dung JDBC/PreparedStatement nhu
 * UserRepository.java ben bt2-servlet-jsp.
 *
 * AppUser anh xa toi bang "users" - bang RIENG cua Baitap02 (xem
 * entity/AppUser.java va database/users.sql), khong con dung chung bang
 * "AppUser" ben bt2-servlet-jsp nua - Baitap02 la bai lam doc lap, chi
 * ke thua CAU TRUC/Y TUONG roi viet lai toan bo tang du lieu bang JPA that.
 */
public class UserDao implements IUserDao {

    @Override
    public AppUser findByUsername(String username) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            // JPQL: truy van tren TEN CLASS/FIELD Java (AppUser, u.username),
            // KHONG phai ten bang/cot SQL that - Hibernate se tu dich sang
            // "SELECT * FROM users WHERE username = ?" luc chay (ten bang
            // that la "users", xem @Table trong entity/AppUser.java).
            TypedQuery<AppUser> query = enma.createQuery(
                    "SELECT u FROM AppUser u WHERE u.username = :username", AppUser.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Khong tim thay username nay -> tra null (giong ResultSet.next() == false ben JDBC)
            return null;
        } finally {
            enma.close();
        }
    }

    @Override
    public AppUser findByEmail(String email) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<AppUser> query = enma.createQuery(
                    "SELECT u FROM AppUser u WHERE u.email = :email", AppUser.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            enma.close();
        }
    }

    @Override
    public void insert(AppUser user) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(user);
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
    public void update(AppUser user) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(user);
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
}
