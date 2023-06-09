<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/priveliege" prefix="privilege" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<%
	/**-= ===================================================================
	*                               基本常量的设定
	*=====================================================================**/
	//设定context path
	String path = request.getContextPath();
	if("/".equals(path.trim())) path = "";
	pageContext.setAttribute("path",path);
%>
<script>
    var path = "${pageContext.request.contextPath}";
</script>
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${path }/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${path }/jquery-easyui-1.3.3/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${path }/style/main.css">

<!-- JAVASCRIPT -->
<script type="text/javascript" src="${path }/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${path }/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path }/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${path }/jquery-easyui-1.3.3/browser.js"></script>
<script type="text/javascript" src="${path }/jquery-easyui-1.3.3/jquery.jqprint-0.3.js"></script>
<script type="text/javascript" src="${path }/jquery-easyui-1.3.3/jquery.cookie.js"></script>

 <script type="text/javascript" src="${path }/My97DatePicker/WdatePicker.js"></script>
 
 

<script type="text/javascript" src="${path }/utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" src="${path }/utf8-jsp/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/utf8-jsp/lang/zh-cn/zh-cn.js"></script>