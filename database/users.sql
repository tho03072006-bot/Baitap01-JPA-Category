/*
   Script du phong cho bang "users" (rieng cua Baitap02, KHONG dung chung
   bang "AppUser" ben bt2-servlet-jsp - Baitap02 la bai lam DOC LAP).
   hibernate.hbm2ddl.auto=update thuong tu tao duoc bang nay khi chay lan
   dau, nhung neu gap loi "Invalid object name 'users'" giong tung gap voi
   bang "categories" thi chay tay script nay trong SSMS (dung database
   "webst2" - vi persistence.xml dang tro toi database nay).

   (Bai tap 03) THEM 4 COT MOI phuc vu dang ky + kich hoat tai khoan bang
   OTP qua email, va quen mat khau bang OTP qua email: enabled, otp_code,
   otp_expiry, otp_purpose (xem giai thich chi tiet trong AppUser.java).
*/

USE webst2;
GO

IF OBJECT_ID('dbo.users', 'U') IS NULL
BEGIN
    CREATE TABLE users (
        id          INT IDENTITY(1,1) PRIMARY KEY,
        username    NVARCHAR(100)  NOT NULL UNIQUE,
        password    NVARCHAR(255)  NOT NULL,
        fullname    NVARCHAR(255)  NULL,
        email       NVARCHAR(255)  NULL,
        roleid      INT            NOT NULL DEFAULT 5,
        enabled     INT            NOT NULL DEFAULT 1,
        otp_code    NVARCHAR(10)   NULL,
        otp_expiry  DATETIME2      NULL,
        otp_purpose NVARCHAR(20)   NULL
    );
END
GO

-- Bang "users" da tung duoc tao TRUOC KHI co tinh nang OTP (Bai tap 03) ->
-- ALTER them tung cot con thieu (khong lam mat du lieu cac cot khac), y
-- het cach lam voi cot Quantity ben database/categories.sql.
IF COL_LENGTH('dbo.users', 'enabled') IS NULL
BEGIN
    -- DEFAULT 1: cac tai khoan CU (tao truoc khi co tinh nang nay, vd
    -- admin/123456 da dung tu Bai tap 01/02) duoc TU DONG coi la da kich
    -- hoat, khong bi khoa dang nhap oan sau khi ALTER TABLE nay chay.
    ALTER TABLE users ADD enabled INT NOT NULL DEFAULT 1;
END
GO
IF COL_LENGTH('dbo.users', 'otp_code') IS NULL
BEGIN
    ALTER TABLE users ADD otp_code NVARCHAR(10) NULL;
END
GO
IF COL_LENGTH('dbo.users', 'otp_expiry') IS NULL
BEGIN
    ALTER TABLE users ADD otp_expiry DATETIME2 NULL;
END
GO
IF COL_LENGTH('dbo.users', 'otp_purpose') IS NULL
BEGIN
    ALTER TABLE users ADD otp_purpose NVARCHAR(20) NULL;
END
GO

-- Tai khoan admin mac dinh de dang nhap demo ngay khi vua deploy xong
-- (roleid = 1 = admin, xem bt02.vn.config.Constants.ROLE_ADMIN). enabled = 1
-- de dang nhap duoc luon, khong can qua buoc xac thuc OTP.
IF NOT EXISTS (SELECT 1 FROM users WHERE username = N'admin')
BEGIN
    INSERT INTO users (username, password, fullname, email, roleid, enabled) VALUES
        (N'admin', N'123456', N'Quản trị viên', N'admin@baitap02.local', 1, 1);
END
GO
