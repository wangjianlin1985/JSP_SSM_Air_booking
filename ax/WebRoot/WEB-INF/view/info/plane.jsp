<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>飞机主页</title>
   	<script type="text/javascript" src="${path }/js/info/plane.js"></script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="planeList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="fidel:'id',hidden:'true'">编号</th>
                	<th field="name" width="60" align="center">名称</th>
                	<th field="model" width="60" align="center">型号</th>
                	<th field="mfr" width="60" align="center">制造商</th>
                	<th field="total" width="60" align="center">座位容量</th>
                	<th field="colnum" width="60" align="center">每排人数</th>
            	</tr>
        </thead>
</table>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="bcf6874ab5c442149a65b0f719fa7e" clazz="easyui-linkbutton" onClick="openPlaneAddDialog()" name="添加"  iconCls="icon-add" ></privilege:operation>
		<privilege:operation operationId="8aec801cdb61401db99972710f8469" clazz="easyui-linkbutton" onClick="openPlaneUpdateDialog()" name="修改"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="2af6d42e4cd24a829baf683ebb4467" clazz="easyui-linkbutton" onClick="deletePlane()" name="删除"  iconCls="icon-remove" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;名称：&nbsp;<input type="text" name="s_name" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchPlane()"/>
		<a href="javascript:searchPlane()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 新增和修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 620px;height: 380px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
 	<table cellspacing="5px;">
  		<tr>
  			<td>名称：</td>
  			<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>型号：</td>
  			<td><input type="text" id="model" name="model" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>制造商：</td>
  			<td><input type="text" id="mfr" name="mfr" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>座位容量：</td>
  			<td><input type="number" id="total" name="total" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>每排人数：</td>
  			<td><input type="number" id="colnum" name="colnum" class="easyui-validatebox" required="true"/></td>
  		</tr>
  	</table>
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reservePlane()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closePlaneDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


</body>
</html>
