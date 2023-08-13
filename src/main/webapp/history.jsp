<%@ page import="com.example.mission1.model.WifiDao" %>
<%@ page import="com.example.mission1.model.HistoryBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <style>
    #customers {
      font-family: Arial, Helvetica, sans-serif;
      border-collapse: collapse;
      width: 100%;
    }

    #customers td, #customers th {
      border: 1px solid #ddd;
      padding: 5px;
    }

    #customers tr:nth-child(even){background-color: #f2f2f2;}

    #customers tr:hover {background-color: #ddd;}

    #customers th {
      padding-top: 12px;
      padding-bottom: 12px;
      text-align: center;
      background-color: #04AA6D;
      color: white;
    }
  </style>
</head>
<body>
<%
  WifiDao wifiDao = new WifiDao();
  List<HistoryBean> historyBeanList = wifiDao.getAllHistory();
%>
<h1>위치 히스토리 목록</h1>
<a href="index.jsp">홈</a>&nbsp
<a href="history.jsp">위치 히스토리 목록</a>&nbsp
<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
<table id="customers">
  <tr>
    <th>ID</th>
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>조회일자</th>
    <th>비고</th>
  </tr>
  <%
    if (historyBeanList == null) {
  %>
  <tr>
    <td colspan="5" style="text-align: center">저장된 히스토리가 없습니다.</td>
  </tr>
  <%
    } else {
      for (HistoryBean historyBean : historyBeanList) {
  %>
  <tr>
    <td><%=historyBean.getId()%></td>
    <td><%=historyBean.getX()%></td>
    <td><%=historyBean.getY()%></td>
    <td><%=historyBean.getUpdate()%></td>
    <td style="text-align: center"><button onclick="location.href='delete-history.jsp?id=<%=historyBean.getId()%>'">삭제</button></td>
  </tr>
  <%
      }
    }
  %>
</table>
</body>
</html>