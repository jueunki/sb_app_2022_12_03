<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sbs.exam.sb_app_2022_10_13.util.Ut" %>

<c:set var="pageTitle" value="마이페이지" />
<%@ include file="../common/head.jspf" %>


<section class="mt-5 con-min-width">
  <div class="con mx-auto px-3">
    <div class="table-box-type-1">
      <table>
        <colgroup>
          <col width="200"/>
        </colgroup>
        <tbody>
        <tr>
          <th>로그인 아이디</th>
          <td>
            ${rq.loginedMember.loginId}
          </td>
        </tr>
        <tr>
          <th>이름</th>
          <td>
            ${rq.loginedMember.name}
          </td>
        </tr>
        <tr>
          <th>별명</th>
          <td>
            ${rq.loginedMember.nickname}
          </td>
        </tr>
        <tr>
          <th>이메일</th>
          <td>
            ${rq.loginedMember.email}
          </td>
        </tr>
        <tr>
          <th>전화번호</th>
          <td>
            ${rq.loginedMember.cellphoneNo}
          </td>
        </tr>
        <tr>
          <th>비고</th>
          <td>
            <a href="../member/checkPassword?replaceUri=${Ut.getUriEncoded('../member/modify')}" class="btn btn-primary">회원정보수정</a>
            <button type="button" onclick="history.back();" class="btn btn-outline btn-secondary">뒤로가기</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf" %>