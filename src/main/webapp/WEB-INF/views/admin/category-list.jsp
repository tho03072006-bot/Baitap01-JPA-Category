<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<jsp:include page="admin-header.jsp">
    <jsp:param name="pageTitle" value="Danh sách danh mục"/>
    <jsp:param name="activeMenu" value="category"/>
</jsp:include>

<style>
    .panel { background:#fff; padding:28px 30px; border-radius:10px; box-shadow:0 4px 16px rgba(0,0,0,0.06); }
    .panel-head { display:flex; justify-content:space-between; align-items:center; margin-bottom:18px; }
    .panel-head h1 { margin:0; font-size:20px; color:#1a1a2e; }
    a.btn-link { display:inline-block; padding:9px 18px; background:#0d6efd; color:#fff; text-decoration:none; border-radius:6px; font-size:14px; font-weight:600; }
    a.btn-link:hover { background:#0b5ed7; }
    table { width:100%; border-collapse:collapse; }
    th, td { text-align:left; padding:10px 12px; border-bottom:1px solid #eee; font-size:14px; }
    th { color:#666; font-weight:600; background:#f8f9fb; }
    img.icon { width:56px; height:56px; object-fit:cover; border-radius:6px; border:1px solid #eee; }
    .no-icon { color:#999; font-size:12px; }
    .badge { display:inline-block; padding:3px 10px; border-radius:20px; font-size:12px; font-weight:600; }
    .badge.on { background:#e7f6ec; color:#1e7e34; }
    .badge.off { background:#fdecea; color:#b91c1c; }
    .row-actions a { margin-right:10px; font-size:13px; text-decoration:none; font-weight:600; }
    .row-actions a.edit { color:#b8860b; }
    .row-actions a.delete { color:#b91c1c; }
    .empty { text-align:center; color:#999; padding:24px 0; }
    .search-row { display:flex; gap:10px; margin-bottom:18px; }
    .search-row input[type="text"] {
        flex:1; padding:9px 12px; border:1px solid #ccc; border-radius:6px; font-size:14px;
    }
    .search-row button {
        padding:9px 18px; border:none; border-radius:6px; background:#0d6efd; color:#fff;
        font-size:14px; font-weight:600; cursor:pointer;
    }
    .search-row button:hover { background:#0b5ed7; }
    .search-row a.clear {
        padding:9px 14px; border-radius:6px; background:#f0f2f5; color:#555;
        text-decoration:none; font-size:13px; font-weight:600; display:flex; align-items:center;
    }
    .qty-zero { color:#b91c1c; font-size:12px; font-weight:600; margin-left:6px; }
</style>

<div class="panel">
    <div class="panel-head">
        <h1>Danh sách danh mục (Category &ndash; JPA)</h1>
        <a class="btn-link" href="${pageContext.request.contextPath}/admin/category/add">+ Thêm danh mục</a>
    </div>

    <form class="search-row" action="${pageContext.request.contextPath}/admin/categories" method="get">
        <input type="text" name="keyword" placeholder="Tìm theo tên danh mục..." value="${keyword}">
        <button type="submit">Tìm kiếm</button>
        <c:if test="${not empty keyword}">
            <a class="clear" href="${pageContext.request.contextPath}/admin/categories">Xóa lọc</a>
        </c:if>
    </form>

    <table>
        <thead>
        <tr>
            <th style="width:50px;">STT</th>
            <th style="width:90px;">Ảnh</th>
            <th>Tên danh mục</th>
            <th style="width:100px;">Số lượng</th>
            <th style="width:110px;">Trạng thái</th>
            <th style="width:140px;">Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listcate}" var="cate" varStatus="stt">
            <tr>
                <td>${stt.index + 1}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty cate.images and fn:startsWith(cate.images, 'http')}">
                            <img class="icon" src="${cate.images}">
                        </c:when>
                        <c:when test="${not empty cate.images}">
                            <c:url value="/image" var="imgUrl">
                                <c:param name="fname" value="${cate.images}"/>
                            </c:url>
                            <img class="icon" src="${imgUrl}">
                        </c:when>
                        <c:otherwise>
                            <span class="no-icon">Chưa có ảnh</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${cate.categoryname}"/></td>
                <td>
                    <c:out value="${cate.quantity}"/>
                    <c:if test="${cate.quantity == 0}"><span class="qty-zero">Hết hàng</span></c:if>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${cate.status == 1}"><span class="badge on">Hoạt động</span></c:when>
                        <c:otherwise><span class="badge off">Khóa</span></c:otherwise>
                    </c:choose>
                </td>
                <td class="row-actions">
                    <a class="edit" href="${pageContext.request.contextPath}/admin/category/edit?id=${cate.categoryid}">Sửa</a>
                    <a class="delete" href="${pageContext.request.contextPath}/admin/category/delete?id=${cate.categoryid}"
                       onclick="return confirm('Xoá danh mục này?');">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty listcate}">
            <tr>
                <td colspan="6" class="empty">
                    <c:choose>
                        <c:when test="${not empty keyword}">Không tìm thấy danh mục nào khớp với "<c:out value="${keyword}"/>"</c:when>
                        <c:otherwise>Chưa có danh mục nào</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="admin-footer.jsp"/>
