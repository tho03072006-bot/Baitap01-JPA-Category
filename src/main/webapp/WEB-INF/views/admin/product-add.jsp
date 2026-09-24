<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="admin-header.jsp">
    <jsp:param name="pageTitle" value="Thêm sản phẩm"/>
    <jsp:param name="activeMenu" value="product"/>
</jsp:include>

<style>
    .panel { background:#fff; padding:28px 30px; border-radius:10px; box-shadow:0 4px 16px rgba(0,0,0,0.06); max-width:520px; }
    .panel-head { margin-bottom:18px; }
    .panel-head h1 { margin:0; font-size:20px; color:#1a1a2e; }
    .field { margin-bottom:16px; }
    label { display:block; margin-bottom:6px; font-size:14px; color:#333; font-weight:600; }
    input[type="text"], input[type="file"], input[type="number"], select, textarea {
        width:100%; padding:10px 12px; border:1px solid #ccc; border-radius:6px; font-size:14px; font-family:inherit;
    }
    textarea { resize:vertical; min-height:80px; }
    .hint { font-size:12px; color:#888; margin-top:6px; }
    .error { margin-bottom:16px; padding:10px; background:#fef2f2; border:1px solid #fecaca; color:#b91c1c; border-radius:6px; font-size:13px; }
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
    <div class="panel-head"><h1>Thêm sản phẩm</h1></div>

    <c:if test="${not empty formError}">
        <div class="error"><c:out value="${formError}"/></div>
    </c:if>

    <%-- enctype="multipart/form-data" bat buoc phai co khi form chua input type="file" --%>
    <form action="${pageContext.request.contextPath}/admin/product/insert" method="post" enctype="multipart/form-data">
        <div class="field">
            <label for="productname">Tên sản phẩm</label>
            <input type="text" id="productname" name="productname" required placeholder="Ví dụ: Áo thun nam basic">
        </div>
        <div class="field">
            <label for="categoryid">Danh mục</label>
            <select id="categoryid" name="categoryid" required>
                <option value="" disabled selected>-- Chọn danh mục --</option>
                <c:forEach items="${listcate}" var="cate">
                    <option value="${cate.categoryid}"><c:out value="${cate.categoryname}"/></option>
                </c:forEach>
            </select>
            <c:if test="${empty listcate}">
                <div class="hint">Chưa có danh mục nào - vui lòng <a href="${pageContext.request.contextPath}/admin/category/add">thêm danh mục</a> trước.</div>
            </c:if>
        </div>
        <div class="field">
            <label for="price">Giá bán (đ)</label>
            <input type="number" id="price" name="price" min="0" step="1000" value="0" required>
        </div>
        <div class="field">
            <label for="description">Mô tả</label>
            <textarea id="description" name="description" placeholder="Mô tả ngắn về sản phẩm..."></textarea>
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
            <label for="quantity">Số lượng tồn kho</label>
            <input type="number" id="quantity" name="quantity" min="0" step="1" value="0">
        </div>
        <div class="btn-row">
            <button type="submit">Thêm</button>
            <a class="btn-link" href="${pageContext.request.contextPath}/admin/products">Hủy</a>
        </div>
    </form>
</div>

<jsp:include page="admin-footer.jsp"/>
