package bt02.vn.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * JpaConfig: noi nap persistence.xml va cap phat EntityManager - moi noi
 * trong tang Dao can thao tac database se goi JpaConfig.getEntityManager()
 * de lay 1 EntityManager MOI cho tung request (khong dung chung 1
 * EntityManager cho ca ung dung, vi EntityManager KHONG thread-safe).
 *
 * EntityManagerFactory (bien "factory") thi NGUOC LAI - chi can tao 1 LAN
 * DUY NHAT cho ca ung dung (rat "nang" de khoi tao, doc persistence.xml,
 * ket noi connection pool...), nen duoc gan trong static initializer block
 * (chay dung 1 lan, ngay lan dau class nay duoc JVM nap).
 *
 * "dataSource" phai khop CHINH XAC voi ten khai bao trong
 * <persistence-unit name="dataSource"> cua file persistence.xml.
 */
public class JpaConfig {

    private static final String PERSISTENCE_UNIT_NAME = "dataSource";

    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Constructor private - day la class chi chua static method, khong can
    // (va khong nen) tao instance
    private JpaConfig() {
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
