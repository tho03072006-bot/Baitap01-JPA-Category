package bt02.vn.dao;

import bt02.vn.entity.AppUser;

/**
 * IUserDao: interface tang truy xuat du lieu cho AppUser, theo dung cau
 * truc interface-based DAO da dung cho ICategoryDao trong project nay.
 *
 * (Bai tap 03) THEM 3 method moi (insert/update/findByEmail) de phuc vu
 * dang ky tai khoan + kich hoat OTP + quen mat khau - truoc do (Bai tap
 * 01/02, chi co tinh nang dang nhap) chi can findByUsername() la du.
 */
public interface IUserDao {

    /**
     * Tim 1 AppUser theo username (dung cho ca kiem tra dang nhap lan
     * doc lai Cookie "Ghi nho dang nhap").
     * @return AppUser tim thay, hoac null neu khong co username nay.
     */
    AppUser findByUsername(String username);

    /** Tim 1 AppUser theo email - dung cho dang ky (kiem tra trung email) va quen mat khau. */
    AppUser findByEmail(String email);

    /** Them 1 tai khoan moi (dung khi dang ky). */
    void insert(AppUser user);

    /** Cap nhat 1 tai khoan da co (dung khi xac thuc OTP, doi mat khau, gui lai OTP...). */
    void update(AppUser user);
}
