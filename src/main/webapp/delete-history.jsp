<%@ page import="com.example.mission1.model.WifiDao" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<body>
    <%
        WifiDao wifiDao = new WifiDao();
        int id = Integer.parseInt(request.getParameter("id"));
        wifiDao.deleteHistory(id);
    %>
    <script>
        location.href="history.jsp";
    </script>
</body>
</html>
