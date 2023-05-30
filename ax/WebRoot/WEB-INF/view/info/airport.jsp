<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>城市主页</title>
   	<script type="text/javascript" src="${path }/js/info/airport.js"></script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="airportList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="fidel:'id',hidden:'true'">编号</th>
                	<th field="name" width="60" align="center">名称</th>
                	<th field="cityid" width="60" align="center" formatter="formatCity">所属城市</th>
            	</tr>
        </thead>
</table>
<script type="text/javascript">
	function formatCity(value,row,index){
		return row.city.name
	}
</script>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="653b943f3a534a318671e44aa1afa7" clazz="easyui-linkbutton" onClick="openAirportAddDialog()" name="添加"  iconCls="icon-add" ></privilege:operation>
		<privilege:operation operationId="19e5af4964f744738e9fc015b61298" clazz="easyui-linkbutton" onClick="openAirportUpdateDialog()" name="修改"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="91cd583e0ec341c089d9dc1d86be88" clazz="easyui-linkbutton" onClick="deleteAirport()" name="删除"  iconCls="icon-remove" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;名称：&nbsp;<input type="text" name="s_keyword" id="s_keyword" size="20" onkeydown="if(event.keyCode==13) searchAirport()"/>
		<a href="javascript:searchAirport()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 新增和修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 620px;height: 320px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
 	<table cellspacing="5px;">
  		<tr>
  			<td>名称：</td>
  			<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>城市：</td>
  			<td><input type="text" id="cityid" name="city.id" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>备注：</td>
  			<td><input  id="remarks" name="remarks" class="easyui-validatebox" /></td>
  		</tr>
  		
  	</table>
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveAirport()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeAirportDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


</body>
</html>
