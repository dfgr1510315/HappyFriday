<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2019/2/7 0007
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div style="width: 80%;height: 42%;margin-top: -32px;">
    <div style="margin-left: 0;height: 148px;width: 148px;float: left">
        <div style="border: 4px solid #FFF;box-shadow: 0 4px 8px 0 rgba(7,17,27,.1);width: 148px;height: 148px;position: relative;border-radius: 50%;background: #fff;top: 24px;">
            <img id="radio_cover" src="${pageContext.request.contextPath}/image/efb37fee400582742424a4ce08951213.png" style="text-align: center;width: 140px;height: 140px;border-radius: 50%;">
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $('#radio_cover').attr('src',"${pageContext.request.contextPath}"+head_image);
    })
</script>
</html>
