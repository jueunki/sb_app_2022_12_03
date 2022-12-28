<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="게시물 수정" />
<%@ include file="../common/head.jspf" %>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="POST" action="../article/doModify">
      <input type="hidden" name="id" value="${article.id}"> <!-- 서버에 적용시키려면 이부분이 꼭 필요하다. -->

      <table border="1">
        <colgroup>
          <col width="200"/>
        </colgroup>
        <tbody>
          <tr>
            <th>번호</th>
            <td>
              <div class="badge badge-primary">
                ${article.id}
              </div>
            </td>  <!-- 몇번글인지 보여주는것이다. -->
          </tr>
          <tr>
            <th>작성날짜</th>
            <td>${article.regDateForPrint}</td>
          </tr>
          <tr>
            <th>수정날짜</th>
            <td>${article.updateDateForPrint}</td>
          </tr>
          <tr>
            <th>조회</th>
            <td>
              <span class="badge badge-primary article-detail__hit-count">${article.hitCount}</span>
            </td>
          </tr>
          <tr>
            <th>추천</th>
            <td>
              <span class="badge badge-primary article-detail__hit-count">${article.extra__goodReactionPoint}</span>
            </td>
          </tr>
          <tr>
            <th>작성자</th>
            <td>${article.extra__writerName}</td>
          </tr>
          <tr>
            <th>제목</th>
            <td>
              <input name="title" class="input input-bordered w-96 max-w-xs" type="text" placeholder="제목" value="${article.title}">
            </td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <textarea class="textarea textarea-bordered w-full" name="body" rows="10" placeholder="내용">${article.body}</textarea> <!-- 쓸때 엔터 치면 안된다.-->
            </td>
          </tr>
          <tr>
            <th>수정</th>
            <td>
              <button type="submit" class="btn btn-primary">수정</button>
              <button type="button" onclick="history.back();" class="btn btn-outline btn-secondary">뒤로가기</button>
            </td>
          </tr>
        </tbody>
      </table>
    </form>

    <div class="btns">
      <button class="btn btn-link" type="button" onclick="history.back()">뒤로가기</button>
      <a class="btn btn-link" href="../article/detail?id=${article.id}">게시물 상세페이지</a>
      <c:if test="${article.extra__actorCanModify}">
        <a class="btn btn-link" href="../article/modify?id=${article.id}">게시물 수정</a>
      </c:if>
      <c:if test="${article.extra__actorCanDelete}">
        <a class="btn btn-link" onclick="if(confirm('정말 삭제하시겠습니까?') == false ) return false" href="../article/doDelete?id=${article.id}">게시물 삭제</a>
      </c:if>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf" %>




