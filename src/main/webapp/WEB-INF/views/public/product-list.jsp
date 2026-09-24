<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="public-header.jsp">
    <jsp:param name="pageTitle" value="Sản phẩm"/>
    <jsp:param name="activeMenu" value="products"/>
</jsp:include>

<style>
    h1.page-title { color:#1a1a2e; font-size:22px; margin:0 0 20px; }
    .product-grid { display:grid; grid-template-columns:repeat(auto-fill, minmax(200px, 1fr)); gap:20px; margin-bottom:28px; }
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
    .pagination { display:flex; justify-content:center; gap:8px; flex-wrap:wrap; }
    .pagination a, .pagination span {
        display:inline-block; min-width:36px; text-align:center; padding:8px 12px; border-radius:6px;
        font-size:14px; font-weight:600; text-decoration:none;
    }
    .pagination a { background:#fff; color:#1a1a2e; box-shadow:0 1px 4px rgba(0,0,0,0.08); }
    .pagination a:hover { background:#0d6efd; color:#fff; }
    .pagination span.current { background:#0d6efd; color:#fff; }
    .pagination span.disabled { background:#f0f2f5; color:#bbb; }
</style>

<h1 class="page-title">Tất cả sản phẩm</h1>

<c:choose>
    <c:when test="${empty products}">
        <div class="empty">Chưa có sản phẩm nào</div>
    </c:when>
    <c:otherwise>
        <div class="product-grid">
            <c:forEach items="${products}" var="p">
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

<c:if test="${totalPages > 1}">
    <div class="pagination">
        <c:choose>
            <c:when test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/product?page=${currentPage - 1}">&laquo; Trước</a>
            </c:when>
            <c:otherwise><span class="disabled">&laquo; Trước</span></c:otherwise>
        </c:choose>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}"><span class="current">${i}</span></c:when>
                <c:otherwise><a href="${pageContext.request.contextPath}/product?page=${i}">${i}</a></c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/product?page=${currentPage + 1}">Sau &raquo;</a>
            </c:when>
            <c:otherwise><span class="disabled">Sau &raquo;</span></c:otherwise>
        </c:choose>
    </div>
</c:if>

<jsp:include page="public-footer.jsp"/>
