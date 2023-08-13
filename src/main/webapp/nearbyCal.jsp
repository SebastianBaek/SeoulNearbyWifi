<%@ page import="java.util.List" %>
<%@ page import="com.example.mission1.model.WifiBean" %>
<%@ page import="com.example.mission1.model.WifiDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
</head>
<body>
    <%
        WifiDao wifiDao = new WifiDao();
        double x = Double.parseDouble(request.getParameter("x"));
        double y = Double.parseDouble(request.getParameter("y"));

        if (wifiDao.getWifiCount() == 0) {
    %>
    <script>
        alert("와이파이 정보를 가져온 후 조회해 주세요.");
        history.go(-1);
    </script>

    <%
        } else if (x == 0 && y == 0){
    %>
    <script>
        alert("내 위치를 가져온 후 조회해 주세요.");
        history.go(-1);
    </script>
    <%
        } else {
            wifiDao.insertMyLocation(x, y);
            List<ArrayList> nearbyWifiList = wifiDao.getNearbyWifi(x, y);
            session.setAttribute("nearbyWifiList", nearbyWifiList);
    %>
    <script>
        location.href="index.jsp";
    </script>
    <%
        }
    %>
</body>
</html>

