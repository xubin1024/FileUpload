<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2019/11/9
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>$Title$</title>
  </head>
  <body>
  <form method="post" action="${pageContext.request.contextPath}/fileServlet" enctype="multipart/form-data">
    姓名：<input type="text" name="name"><br/>
    年纪：<input type="text" name="age"><br/>
    文件:<input type="file" name="file"><br/>
    <input type="submit" value="提交">
  </form>
  </body>
</html>
