package bt02.vn.service;

import java.util.List;

import bt02.vn.dao.CategoryDao;
import bt02.vn.dao.ICategoryDao;
import bt02.vn.entity.Category;

/**
 * CategoryServiceImpl: tang trung gian giua Controller va Dao - noi dat
 * cac "quy tac nghiep vu" (business rule), vi du: khong cho insert trung
 * ten danh muc, khong cho update 1 danh muc khong ton tai. Controller
 * KHONG duoc goi thang xuong Dao - luon di qua Service.
 */
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDao cateDao = new CategoryDao();

    @Override
    public List<Category> findAll() {
        return cateDao.findAll();
    }

    @Override
    public Category findById(int id) {
        return cateDao.findById(id);
    }

    @Override
    public List<Category> searchByName(String keyword) {
        return cateDao.searchByName(keyword);
    }

    @Override
    public void insert(Category category) {
        // Quy tac nghiep vu: chi insert neu CHUA co danh muc trung ten
        Category cate = this.findByCategoryname(category.getCategoryname());
        if (cate == null) {
            applyStatusFromQuantity(category);
            cateDao.insert(category);
        }
    }

    @Override
    public void update(Category category) {
        // Quy tac nghiep vu: chi update neu danh muc do THAT SU ton tai
        Category cate = this.findById(category.getCategoryid());
        if (cate != null) {
            applyStatusFromQuantity(category);
            cateDao.update(category);
        }
    }

    /**
     * Quy tac nghiep vu MOI (theo yeu cau SV, 2026-08-26): trang thai
     * (status) KHONG con do admin tu chon tay nua, ma TU DONG suy ra tu
     * so luong ton kho (quantity):
     *   - quantity >= 1  -> status = 1 (Hoat dong, con hang)
     *   - quantity == 0  -> status = 0 (Khoa, het hang)
     * Dat logic nay o tang Service (khong phai Controller/Dao) de ap dung
     * NHAT QUAN cho MOI duong goi insert/update, dung dung vai tro "noi
     * dat quy tac nghiep vu" da ghi trong javadoc class nay.
     */
    private void applyStatusFromQuantity(Category category) {
        category.setStatus(category.getQuantity() >= 1 ? 1 : 0);
    }

    @Override
    public void delete(int id) {
        try {
            cateDao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int count() {
        return cateDao.count();
    }

    @Override
    public List<Category> findAll(int page, int pagesize) {
        return cateDao.findAll(page, pagesize);
    }

    @Override
    public Category findByCategoryname(String name) {
        try {
            return cateDao.findByCategoryname(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
