<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="admin-header.jsp">
    <jsp:param name="pageTitle" value="Thêm danh mục"/>
    <jsp:param name="activeMenu" value="category"/>
</jsp:include>

<style>
    .panel { background:#fff; padding:28px 30px; border-radius:10px; box-shadow:0 4px 16px rgba(0,0,0,0.06); max-width:480px; }
    .panel-head { margin-bottom:18px; }
    .panel-head h1 { margin:0; font-size:20px; color:#1a1a2e; }
    .field { margin-bottom:16px; }
    label { display:block; margin-bottom:6px; font-size:14px; color:#333; font-weight:600; }
    input[type="text"], input[type="file"], input[type="number"] {
        width:100%; padding:10px 12px; border:1px solid #ccc; border-radius:6px; font-size:14px;
    }
    .hint { font-size:12px; color:#888; margin-top:6px; }
    .btn-row { display:flex; gap:10px; margin-top:6px; }
    button, a.btn-link {
        flex:1; padding:11px; border-radius:6px; font-size:14px; font-weight:600;
        text-align:center; text-decoration:none; cursor:pointer; border:none;
    }
    button { background:#0d6efd; color:#fff; }
    button:hover { background:#0b5ed7; }
    a.btn-link { background:#fff; color:#444; border:1px solid #ccc; }
    a.btn-link:hover { background:#f5f5f5; }
</style>

<div class="panel">
    <div class="panel-head"><h1>Thêm danh mục</h1></div>

    <%-- enctype="multipart/form-data" bat buoc phai co khi form chua input type="file" --%>
    <form action="${pageContext.request.contextPath}/admin/category/insert" method="post" enctype="multipart/form-data">
        <div class="field">
            <label for="categoryname">Category name</label>
            <input type="text" id="categoryname" name="categoryname" required placeholder="Ví dụ: Quần áo nam">
        </div>
        <div class="field">
            <label for="images">Link ảnh (nếu dùng ảnh từ 1 URL có sẵn)</label>
            <input type="text" id="images" name="images" placeholder="https://...">
        </div>
        <div class="field">
            <label for="images1">Hoặc upload ảnh từ máy</label>
            <input type="file" id="images1" name="images1" accept="image/*">
        </div>
        <div class="field">
            <label for="quantity">Số lượng</label>
            <input type="number" id="quantity" name="quantity" min="0" step="1" value="0">
            <div class="hint">Trạng thái tự động: còn hàng (≥ 1) → Hoạt động, hết hàng (0) → Khóa.</div>
        </div>
        <div class="btn-row">
            <button type="submit">Thêm</button>
            <a class="btn-link" href="${pageContext.request.contextPath}/admin/categories">Hủy</a>
        </div>
    </form>
</div>

<jsp:include page="admin-footer.jsp"/>
