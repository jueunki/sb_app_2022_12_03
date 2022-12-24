<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="${board.name} 게시물 리스트" />
<%@ include file="../common/head.jspf" %>

<section class="mt-5 con-min-width">
  <div class="con mx-auto px-3">
    <div class="table-box-type-1">
      <table border="1">
        <colgroup>
          <col width="80"/>
          <col width="150"/>
          <col width="150"/>
          <col width="150"/>
          <col />
        </colgroup>
        <thead>
        <tr>
          <th>번호</th>
          <th>작성날짜</th>
          <th>수정날짜</th>
          <th>작성자</th>
          <th>제목</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="article" items="${articles}">
          <tr>
            <th>${article.id}</th>
            <th>${article.regDate.substring(2, 16)}</th>
            <th>${article.updateDate.substring(2, 16)}</th>
            <th>${article.extra__writerName}</th>
            <th>
              <a class="btn-text-link" href="../article/detail?id=${article.id}">${article.title}</a>
            </th>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <div class="btns">
        <button class="btn btn-link" type="button" onclick="history.back()">뒤로가기</button>
        <a class="btn btn-link" href="../article/write">게시물 작성</a>
      </div>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf" %>




