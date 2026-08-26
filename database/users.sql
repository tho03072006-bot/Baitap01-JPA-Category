/*
   Script du phong cho bang "users" (rieng cua Baitap02, KHONG dung chung
   bang "AppUser" ben bt2-servlet-jsp - Baitap02 la bai lam DOC LAP).
   hibernate.hbm2ddl.auto=update thuong tu tao duoc bang nay khi chay lan
   dau, nhung neu gap loi "Invalid object name 'users'" giong tung gap voi
   bang "categories" thi chay tay script nay trong SSMS (dung database
   "webst2" - vi persistence.xml dang tro toi database nay).
*/

USE webst2;
GO

IF OBJECT_ID('dbo.users', 'U') IS NULL
BEGIN
    CREATE TABLE users (
        id        INT IDENTITY(1,1) PRIMARY KEY,
        username  NVARCHAR(100) NOT NULL UNIQUE,
        password  NVARCHAR(255) NOT NULL,
        fullname  NVARCHAR(255) NULL,
        email     NVARCHAR(255) NULL,
        roleid    INT NOT NULL DEFAULT 5
    );
END
GO

-- Tai khoan admin mac dinh de dang nhap demo ngay khi vua deploy xong
-- (roleid = 1 = admin, xem bt02.vn.config.Constants.ROLE_ADMIN)
IF NOT EXISTS (SELECT 1 FROM users WHERE username = N'admin')
BEGIN
    INSERT INTO users (username, password, fullname, email, roleid) VALUES
        (N'admin', N'123456', N'Quản trị viên', N'admin@baitap02.local', 1);
END
GO
