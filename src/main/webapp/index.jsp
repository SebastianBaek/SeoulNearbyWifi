<%@ page import="com.example.mission1.model.WifiDao" %>
<%@ page import="com.example.mission1.model.WifiBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
    List<ArrayList> nearbyWifiList = (ArrayList<ArrayList>) session.getAttribute("nearbyWifiList");
%>
    <h1>와이파이 정보 구하기</h1>
    <a href="index.jsp">홈</a>&nbsp
    <a href="history.jsp">위치 히스토리 목록</a>&nbsp
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a><br><br>

    LAT:&nbsp<input type="text" id="latitude" value="0.0">
    LNT:&nbsp<input type="text" id="longitude" value="0.0">
    <button onclick="getLocation()">내 위치 가져오기</button>
    <button onclick="getNearbyWifiInfo()">근처 WIFI 정보 보기</button><br>
    <table id="customers">
        <tr>
            <th>거리(Km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>WIFI접속환경</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>작업일자</th>
        </tr>
        <%
            if(nearbyWifiList == null) {
        %>
        <tr>
            <td colspan="17" style="text-align: center">위치 정보를 입력한 후에 조회해 주세요.</td>
        </tr>
        <%
            } else {
                for (ArrayList wifiAndDistance : nearbyWifiList) {
                    WifiBean wifiBean = (WifiBean) wifiAndDistance.get(0);
                    double wifiDistance = (double) wifiAndDistance.get(1);
                    String distance = String.format("%.4f", wifiDistance);
        %>
        <tr>
            <td><%=distance%></td>
            <td><%=wifiBean.getNumber()%></td>
            <td><%=wifiBean.getGu()%></td>
            <td><%=wifiBean.getName()%></td>
            <td><%=wifiBean.getDoroAddress()%></td>
            <td><%=wifiBean.getDetailAddress()%></td>
            <td><%=wifiBean.getFloor()%></td>
            <td><%=wifiBean.getType()%></td>
            <td><%=wifiBean.getAgency()%></td>
            <td><%=wifiBean.getService()%></td>
            <td><%=wifiBean.getNet()%></td>
            <td><%=wifiBean.getInstallYear()%></td>
            <td><%=wifiBean.getInOut()%></td>
            <td><%=wifiBean.getAccess()%></td>
            <td><%=wifiBean.getY()%></td>
            <td><%=wifiBean.getX()%></td>
            <td><%=wifiBean.getRecentWork()%></td>
        </tr>
        <%
                }
            }
        %>
    </table>
<script>
    function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        } else {
            alert("이 브라우저에서 지원하지 않는 기능입니다.");
        }
    }

    function showPosition(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        document.getElementById("latitude").value = latitude;
        document.getElementById("longitude").value = longitude;
    }

    function getNearbyWifiInfo() {
        var latitude = document.getElementById("latitude").value;
        var longitude = document.getElementById("longitude").value;
        var url = 'nearbyCal.jsp?x=' + latitude + '&y=' + longitude;
        location.href = url;
    }
</script>
</body>
</html>