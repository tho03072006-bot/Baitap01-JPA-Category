<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="public-header.jsp">
    <jsp:param name="pageTitle" value="Trang chủ"/>
    <jsp:param name="activeMenu" value="home"/>
</jsp:include>

<style>
    .hero { background:#fff; border-radius:12px; padding:32px; margin-bottom:28px; box-shadow:0 4px 16px rgba(0,0,0,0.05); }
    .hero h1 { margin:0 0 8px; color:#1a1a2e; font-size:26px; }
    .hero p { margin:0; color:#666; font-size:14px; }
    .section-head { display:flex; justify-content:space-between; align-items:center; margin-bottom:16px; }
    .section-head h2 { margin:0; color:#1a1a2e; font-size:19px; }
    .section-head a { color:#0d6efd; text-decoration:none; font-size:13px; font-weight:600; }
    .section-head a:hover { text-decoration:underline; }
    .product-grid { display:grid; grid-template-columns:repeat(auto-fill, minmax(200px, 1fr)); gap:20px; }
    .product-card { background:#fff; border-radius:10px; overflow:hidden; box-shadow:0 2px 10px rgba(0,0,0,0.06); text-decoration:none; color:inherit; display:block; transition:transform .15s; }
    .product-card:hover { transform:translateY(-3px); box-shadow:0 6px 18px rgba(0,0,0,0.1); }
    .product-card .thumb { width:100%; height:170px; background:#eef1f7; display:flex; align-items:center; justify-content:center; overflow:hidden; }
    .product-card .thumb img { width:100%; height:100%; object-fit:cover; }
    .product-card .thumb .no-img { color:#aaa; font-size:13px; }
    .product-card .info { padding:12px 14px; }
    .product-card .name { font-size:14px; font-weight:600; color:#1a1a2e; margin:0 0 6px; line-height:1.3; height:36px; overflow:hidden; }
    .product-card .price { color:#dc3545; font-weight:700; font-size:15px; }
    .product-card .cate { display:inline-block; margin-top:6px; font-size:11px; color:#888; background:#f0f2f5; padding:2px 8px; border-radius:10px; }
    .empty { text-align:center; color:#999; padding:40px 0; background:#fff; border-radius:10px; }
</style>

<div class="hero">
    <h1>Chào mừng đến với cửa hàng!</h1>
    <p>Bài tập 03 - WEBPR330479: CRUD Products (JPA/Hibernate), 10 sản phẩm mới nhất bên dưới, xem tất cả tại mục "Sản phẩm".</p>
</div>

<div class="section-head">
    <h2>10 sản phẩm mới nhất</h2>
    <a href="${pageContext.request.contextPath}/product">Xem tất cả sản phẩm &rarr;</a>
</div>

<c:choose>
    <c:when test="${empty latestProducts}">
        <div class="empty">Chưa có sản phẩm nào</div>
    </c:when>
    <c:otherwise>
        <div class="product-grid">
            <c:forEach items="${latestProducts}" var="p">
                <a class="product-card" href="${pageContext.request.contextPath}/product-detail?id=${p.productid}">
                    <div class="thumb">
                        <c:choose>
                            <c:when test="${not empty p.images and fn:startsWith(p.images, 'http')}">
                                <img src="${p.images}">
                            </c:when>
                            <c:when test="${not empty p.images}">
                                <c:url value="/image" var="imgUrl">
                                    <c:param name="fname" value="${p.images}"/>
                                    <c:param name="type" value="product"/>
                                </c:url>
                                <img src="${imgUrl}">
                            </c:when>
                            <c:otherwise><span class="no-img">Chưa có ảnh</span></c:otherwise>
                        </c:choose>
                    </div>
                    <div class="info">
                        <p class="name"><c:out value="${p.productname}"/></p>
                        <span class="price"><fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>&nbsp;đ</span>
                        <div><span class="cate"><c:out value="${p.category.categoryname}"/></span></div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="public-footer.jsp"/>
