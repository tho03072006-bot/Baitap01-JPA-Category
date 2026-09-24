<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="public-header.jsp">
    <jsp:param name="pageTitle" value="${product.productname}"/>
    <jsp:param name="activeMenu" value="products"/>
</jsp:include>

<style>
    .breadcrumb { font-size:13px; color:#888; margin-bottom:18px; }
    .breadcrumb a { color:#0d6efd; text-decoration:none; }
    .breadcrumb a:hover { text-decoration:underline; }
    .detail-box { background:#fff; border-radius:12px; padding:28px; box-shadow:0 4px 16px rgba(0,0,0,0.05); display:flex; gap:32px; flex-wrap:wrap; }
    .detail-box .thumb { width:320px; height:320px; background:#eef1f7; border-radius:10px; display:flex; align-items:center; justify-content:center; overflow:hidden; flex-shrink:0; }
    .detail-box .thumb img { width:100%; height:100%; object-fit:cover; }
    .detail-box .thumb .no-img { color:#aaa; font-size:14px; }
    .detail-box .info { flex:1; min-width:260px; }
    .detail-box h1 { margin:0 0 10px; color:#1a1a2e; font-size:24px; }
    .detail-box .cate { display:inline-block; margin-bottom:14px; font-size:12px; color:#555; background:#f0f2f5; padding:4px 12px; border-radius:12px; }
    .detail-box .price { color:#dc3545; font-weight:700; font-size:26px; margin-bottom:16px; }
    .detail-box .qty { font-size:14px; color:#444; margin-bottom:16px; }
    .detail-box .qty .zero { color:#b91c1c; font-weight:600; }
    .detail-box .desc-label { font-size:14px; font-weight:600; color:#333; margin-bottom:6px; }
    .detail-box .desc { font-size:14px; color:#555; line-height:1.6; white-space:pre-wrap; }
</style>

<div class="breadcrumb">
    <a href="${pageContext.request.contextPath}/">Trang chủ</a> &raquo;
    <a href="${pageContext.request.contextPath}/product">Sản phẩm</a> &raquo;
    <c:out value="${product.productname}"/>
</div>

<div class="detail-box">
    <div class="thumb">
        <c:choose>
            <c:when test="${not empty product.images and fn:startsWith(product.images, 'http')}">
                <img src="${product.images}">
            </c:when>
            <c:when test="${not empty product.images}">
                <c:url value="/image" var="imgUrl">
                    <c:param name="fname" value="${product.images}"/>
                    <c:param name="type" value="product"/>
                </c:url>
                <img src="${imgUrl}">
            </c:when>
            <c:otherwise><span class="no-img">Chưa có ảnh</span></c:otherwise>
        </c:choose>
    </div>
    <div class="info">
        <h1><c:out value="${product.productname}"/></h1>
        <span class="cate"><c:out value="${product.category.categoryname}"/></span>
        <div class="price"><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>&nbsp;đ</div>
        <div class="qty">
            Số lượng còn lại:
            <c:choose>
                <c:when test="${product.quantity == 0}"><span class="zero">Hết hàng</span></c:when>
                <c:otherwise><b><c:out value="${product.quantity}"/></b></c:otherwise>
            </c:choose>
        </div>
        <div class="desc-label">Mô tả sản phẩm</div>
        <div class="desc"><c:out value="${not empty product.description ? product.description : 'Chưa có mô tả.'}"/></div>
    </div>
</div>

<jsp:include page="public-footer.jsp"/>
