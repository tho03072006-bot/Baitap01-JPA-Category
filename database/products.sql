/*
   Script du phong cho bang "products" (Bai tap 03 - them bang Products
   voi moi lien he 1-N voi bang "categories" da co truoc do: 1 categories
   co NHIEU products, moi products thuoc ve DUNG 1 categories qua khoa
   ngoai CategoryId).

   hibernate.hbm2ddl.auto=update thuong tu tao duoc bang nay khi chay lan
   dau, nhung neu gap loi "Invalid object name 'products'" (giong tung
   gap voi bang "categories") thi chay tay script nay trong SSMS (dung
   database "webst2").

   LUU Y: phai chay database/categories.sql TRUOC script nay (de bang
   "categories" da ton tai san) vi FOREIGN KEY ben duoi tham chieu toi no.
*/

USE webst2;
GO

IF OBJECT_ID('dbo.products', 'U') IS NULL
BEGIN
    CREATE TABLE products (
        ProductId    INT IDENTITY(1,1) PRIMARY KEY,
        ProductName  NVARCHAR(200)  NOT NULL,
        Price        FLOAT          NOT NULL DEFAULT 0,
        Description  NVARCHAR(1000) NULL,
        Images       NVARCHAR(500)  NULL,
        Quantity     INT            NOT NULL DEFAULT 0,
        CategoryId   INT            NOT NULL,
        CreatedDate  DATETIME2      NOT NULL DEFAULT GETDATE(),
        CONSTRAINT FK_products_categories FOREIGN KEY (CategoryId)
            REFERENCES categories(CategoryId)
    );
END
GO

-- Bảng products đã có từ bài trước có thể dùng CreatedAt thay vì CreatedDate.
-- Thêm cột mới và giữ nguyên thời điểm tạo của dữ liệu cũ.
IF COL_LENGTH('dbo.products', 'CreatedDate') IS NULL
BEGIN
    ALTER TABLE dbo.products ADD CreatedDate DATETIME2 NOT NULL
        CONSTRAINT DF_products_CreatedDate DEFAULT GETDATE();

    IF COL_LENGTH('dbo.products', 'CreatedAt') IS NOT NULL
    BEGIN
        EXEC(N'UPDATE dbo.products SET CreatedDate = CreatedAt');
    END
END
GO

-- Vai san pham mau de test ngay "10 san pham moi nhat" o trang chu va
-- phan trang o /product khi vua deploy xong (chi insert neu bang dang
-- rong VA da co it nhat 1 categories de gan khoa ngoai vao).
IF NOT EXISTS (SELECT 1 FROM products)
BEGIN
    DECLARE @cateId INT = (SELECT TOP 1 CategoryId FROM categories ORDER BY CategoryId);
    IF @cateId IS NOT NULL
    BEGIN
        INSERT INTO products (ProductName, Price, Description, Images, Quantity, CategoryId, CreatedDate) VALUES
            (N'Áo thun nam basic',   150000, N'Áo thun cotton 100%, thoáng mát, nhiều màu',            NULL, 50, @cateId, DATEADD(MINUTE, -50, GETDATE())),
            (N'Áo sơ mi nữ công sở', 220000, N'Sơ mi công sở form ôm, vải không nhăn',                 NULL, 30, @cateId, DATEADD(MINUTE, -45, GETDATE())),
            (N'Quần jean nam slim',  350000, N'Jean co giãn nhẹ, form slim fit',                       NULL, 20, @cateId, DATEADD(MINUTE, -40, GETDATE())),
            (N'Chân váy chữ A',      180000, N'Vải tuyết mưa, form chữ A tôn dáng',                    NULL, 15, @cateId, DATEADD(MINUTE, -35, GETDATE())),
            (N'Áo khoác gió',        280000, N'Chống nước nhẹ, có mũ trùm đầu',                        NULL, 25, @cateId, DATEADD(MINUTE, -30, GETDATE())),
            (N'Giày sneaker trắng',  450000, N'Đế cao su êm chân, phối được nhiều outfit',             NULL, 12, @cateId, DATEADD(MINUTE, -25, GETDATE())),
            (N'Dép quai ngang',       95000, N'Chất liệu EVA nhẹ, chống trơn trượt',                    NULL, 40, @cateId, DATEADD(MINUTE, -20, GETDATE())),
            (N'Túi đeo chéo mini',   210000, N'Da PU chống nước, ngăn chia gọn gàng',                  NULL, 18, @cateId, DATEADD(MINUTE, -15, GETDATE())),
            (N'Mũ lưỡi trai',         85000, N'Vải kaki bền màu, điều chỉnh size sau',                 NULL, 60, @cateId, DATEADD(MINUTE, -10, GETDATE())),
            (N'Thắt lưng da nam',    130000, N'Da bò thật, khóa kim loại chống gỉ',                     NULL, 22, @cateId, DATEADD(MINUTE, -8,  GETDATE())),
            (N'Vớ cổ ngắn (5 đôi)',   60000, N'Cotton co giãn, thấm hút mồ hôi',                        NULL, 70, @cateId, DATEADD(MINUTE, -6,  GETDATE())),
            (N'Kính mát thời trang', 175000, N'Tròng chống UV400, gọng nhẹ',                            NULL, 10, @cateId, DATEADD(MINUTE, -4,  GETDATE()));
    END
END
GO
