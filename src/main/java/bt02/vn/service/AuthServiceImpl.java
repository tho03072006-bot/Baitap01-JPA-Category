package bt02.vn.service;

import bt02.vn.dao.IUserDao;
import bt02.vn.dao.UserDao;
import bt02.vn.entity.AppUser;

public class AuthServiceImpl implements IAuthService {

    private final IUserDao userDao;

    public AuthServiceImpl() {
        this.userDao = new UserDao();
    }

    @Override
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        AppUser user = userDao.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public AppUser findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
