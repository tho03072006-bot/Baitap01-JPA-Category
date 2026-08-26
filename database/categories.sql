/*
   Script du phong cho bang "categories" (rieng cua Baitap02, tach biet
   voi bang "Category" cu ben bt2-servlet-jsp). hibernate.hbm2ddl.auto=update
   thuong tu tao/them cot duoc, nhung neu gap loi "Invalid object name
   'categories'" (bang chua ton tai) hoac cot "Quantity" chua co (bang da
   tao tu truoc khi them tinh nang so luong) thi chay tay script nay
   trong SSMS (dung database "webst2").
*/

USE webst2;
GO

IF OBJECT_ID('dbo.categories', 'U') IS NULL
BEGIN
    CREATE TABLE categories (
        CategoryId    INT IDENTITY(1,1) PRIMARY KEY,
        CategoryName  NVARCHAR(50)  NOT NULL,
        Images        NVARCHAR(500) NULL,
        status        INT           NULL,
        Quantity      INT           NOT NULL DEFAULT 0
    );
END
GO

-- Bang "categories" da tung duoc tao TRUOC KHI co tinh nang so luong ->
-- ALTER them cot Quantity neu chua co (khong lam mat du lieu cac cot khac)
IF COL_LENGTH('dbo.categories', 'Quantity') IS NULL
BEGIN
    ALTER TABLE categories ADD Quantity INT NOT NULL DEFAULT 0;
END
GO

-- Vai dong du lieu mau de test giao dien List ngay khi vua deploy xong
IF NOT EXISTS (SELECT 1 FROM categories)
BEGIN
    INSERT INTO categories (CategoryName, Images, status, Quantity) VALUES
        (N'Quần áo nam', NULL, 1, 25),
        (N'Quần áo nữ', NULL, 1, 40),
        (N'Giày dép', NULL, 0, 0);
END
GO
