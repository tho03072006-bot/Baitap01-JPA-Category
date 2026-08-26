package bt02.vn.dao;

import bt02.vn.entity.AppUser;

/**
 * IUserDao: interface tang truy xuat du lieu cho AppUser, theo dung cau
 * truc interface-based DAO da dung cho ICategoryDao trong project nay.
 * Chi can 1 method duy nhat vi login/remember-me chi can tra ve AppUser
 * theo username (khong can insert/update/delete AppUser trong pham vi
 * bai tap nay - viec tao/sua tai khoan da co san o bt2-servlet-jsp).
 */
public interface IUserDao {

    /**
     * Tim 1 AppUser theo username (dung cho ca kiem tra dang nhap lan
     * doc lai Cookie "Ghi nho dang nhap").
     * @return AppUser tim thay, hoac null neu khong co username nay.
     */
    AppUser findByUsername(String username);
}
