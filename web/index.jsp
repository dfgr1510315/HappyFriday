
<%@page contentType="text/html; charset=GBK"%>
<%
   if (application.getAttribute("count") == null) {
   application.setAttribute("count", new Integer(0));
   }
  Integer count = (Integer) application.getAttribute("count");
  application.setAttribute("count", new Integer(count.intValue() + 1));
%>
<html>
<head>
  <title>页面被访问数统计</title>
</head>
<body bgcolor="#ffffff">
<form action="RequestJsp.jsp">
  <p></p>
  <p></p>
  <h1>
     <input type="submit" name="sub" value="点击!"/>
  </h1>

  <!--输出访问次数 -->
  <h1>此页面已被访问了
    ${applicationScope.count} 次
  </h1>
</form>
</body>
</html>
