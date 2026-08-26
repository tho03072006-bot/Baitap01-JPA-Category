package bt02.vn.dao;

import java.util.List;

import bt02.vn.entity.Category;

/**
 * Interface tang Dao (Data Access Object) cho Category - dung dung theo
 * yeu cau Bai tap 01: "pull up thanh interface".
 * Viet interface truoc giup tang Service chi phu thuoc vao "hop dong"
 * (interface) thay vi phu thuoc truc tiep vao 1 cai lop cu the - de sau
 * nay doi cach cai dat (vd doi database) ma khong phai sua tang Service.
 */
public interface ICategoryDao {

    void insert(Category category);

    int count();

    List<Category> findAll(int page, int pagesize);

    List<Category> searchByName(String catname);

    List<Category> findAll();

    Category findById(int cateid);

    void delete(int cateid) throws Exception;

    void update(Category category);

    Category findByCategoryname(String name) throws Exception;
}
