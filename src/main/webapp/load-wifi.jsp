<%@ page import="com.example.mission1.model.WifiDao" %>
<%@ page import="com.example.mission1.api.ApiExplorer" %>
<%@ page import="com.example.mission1.model.WifiBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        h1 { text-align: center; }
        .home { text-align: center;}
    </style>
</head>
<body>
<%
    WifiDao wifiDao = new WifiDao();
    int count = wifiDao.getWifiCount();

    if(count != 0) {
%>
<script>
    alert("Wifi를 이미 가져왔습니다.")
    history.go(-1)
</script>
<%
    } else {
        ApiExplorer.apiData();
        count = wifiDao.getWifiCount();
    }
%>
    <h1><%=count%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
    <div class="home">
        <a href="index.jsp">홈으로 가기</a>
    </div>
</body>
</html>