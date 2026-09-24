<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="admin-header.jsp">
    <jsp:param name="pageTitle" value="Danh sách sản phẩm"/>
    <jsp:param name="activeMenu" value="product"/>
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
    .row-actions a { margin-right:10px; font-size:13px; text-decoration:none; font-weight:600; }
    .row-actions a.edit { color:#b8860b; }
    .row-actions a.delete { color:#b91c1c; }
    .empty { text-align:center; color:#999; padding:24px 0; }
    .qty-zero { color:#b91c1c; font-size:12px; font-weight:600; margin-left:6px; }
    .cate-badge { display:inline-block; padding:3px 10px; border-radius:20px; font-size:12px; font-weight:600; background:#eef1f7; color:#444; }
</style>

<div class="panel">
    <div class="panel-head">
        <h1>Danh sách sản phẩm (Product &ndash; JPA)</h1>
        <a class="btn-link" href="${pageContext.request.contextPath}/admin/product/add">+ Thêm sản phẩm</a>
    </div>

    <table>
        <thead>
        <tr>
            <th style="width:50px;">STT</th>
            <th style="width:90px;">Ảnh</th>
            <th>Tên sản phẩm</th>
            <th style="width:130px;">Danh mục</th>
            <th style="width:110px;">Giá</th>
            <th style="width:100px;">Số lượng</th>
            <th style="width:140px;">Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listproduct}" var="p" varStatus="stt">
            <tr>
                <td>${stt.index + 1}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty p.images and fn:startsWith(p.images, 'http')}">
                            <img class="icon" src="${p.images}">
                        </c:when>
                        <c:when test="${not empty p.images}">
                            <c:url value="/image" var="imgUrl">
                                <c:param name="fname" value="${p.images}"/>
                                <c:param name="type" value="product"/>
                            </c:url>
                            <img class="icon" src="${imgUrl}">
                        </c:when>
                        <c:otherwise>
                            <span class="no-icon">Chưa có ảnh</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/product-detail?id=${p.productid}" target="_blank" style="color:#1a1a2e; text-decoration:none; font-weight:600;">
                        <c:out value="${p.productname}"/>
                    </a>
                </td>
                <td><span class="cate-badge"><c:out value="${p.category.categoryname}"/></span></td>
                <td><fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>&nbsp;đ</td>
                <td>
                    <c:out value="${p.quantity}"/>
                    <c:if test="${p.quantity == 0}"><span class="qty-zero">Hết hàng</span></c:if>
                </td>
                <td class="row-actions">
                    <a class="edit" href="${pageContext.request.contextPath}/admin/product/edit?id=${p.productid}">Sửa</a>
                    <a class="delete" href="${pageContext.request.contextPath}/admin/product/delete?id=${p.productid}"
                       onclick="return confirm('Xoá sản phẩm này?');">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty listproduct}">
            <tr>
                <td colspan="7" class="empty">Chưa có sản phẩm nào</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="admin-footer.jsp"/>
