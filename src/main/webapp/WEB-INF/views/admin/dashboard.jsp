<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="admin-header.jsp">
    <jsp:param name="pageTitle" value="Dashboard"/>
    <jsp:param name="activeMenu" value="dashboard"/>
</jsp:include>

<style>
    .stat-cards { display: flex; gap: 20px; margin-bottom: 24px; }
    .stat-card {
        background: #fff; border-radius: 10px; padding: 22px 26px;
        box-shadow: 0 4px 16px rgba(0,0,0,0.06); min-width: 220px;
        display: flex; align-items: center; gap: 16px;
    }
    .stat-card .icon {
        width: 48px; height: 48px; border-radius: 10px;
        background: #e7f0ff; color: #0d6efd;
        display: flex; align-items: center; justify-content: center; font-size: 22px;
    }
    .stat-card .num { font-size: 26px; font-weight: 700; color: #1a1a2e; line-height: 1; }
    .stat-card .label { font-size: 13px; color: #666; margin-top: 4px; }
</style>

<h1 style="margin:0 0 6px; color:#1a1a2e; font-size:22px;">Dashboard</h1>
<p style="color:#666; font-size:14px; margin:0 0 24px;">Tổng quan Bài tập 01 &ndash; CRUD Category bằng JPA/Hibernate.</p>

<div class="stat-cards">
    <div class="stat-card">
        <div class="icon">&#128193;</div>
        <div>
            <div class="num"><c:out value="${totalCategory}"/></div>
            <div class="label">Danh mục (Category)</div>
        </div>
    </div>
</div>

<jsp:include page="admin-footer.jsp"/>
