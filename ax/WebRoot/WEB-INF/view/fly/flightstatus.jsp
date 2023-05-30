<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>航班主页</title>
   	<script type="text/javascript" >
   		var url = "";
   		function wandianDialog(){
   			var selectedRows = $("#dg").datagrid('getSelections');
   			if (selectedRows.length != 1) {
   				$.messager.alert('系统提示', '请选择一条要编辑的数据！');
   				return;
   			}
   			var row = selectedRows[0];
   			if(row.status=='晚点' || row.status=='取消'){
   				$.messager.alert('系统提示', '该航班状态不能变成晚点！');
   				return;
   			}
   			$("#dlg").dialog("open").dialog("setTitle", "晚点");
   			url = "updateFlightstatus.htm?id=" + row.id + "&flightid="+row.flight.id+"&status=1&date="+row.date+"&code="+row.flight.code;
   			$("#txtx").html("晚点时间");
   		}
   		
   		function quxiaoDialog(){
   			var selectedRows = $("#dg").datagrid('getSelections');
   			if (selectedRows.length != 1) {
   				$.messager.alert('系统提示', '请选择一条要编辑的数据！');
   				return;
   			}
   			var row = selectedRows[0];
   			if(row.status=='取消'){
   				$.messager.alert('系统提示', '该航班状态不能变成取消！');
   				return;
   			}
   			$("#dlg").dialog("open").dialog("setTitle", "取消");
   			url = "updateFlightstatus.htm?id=" + row.id + "&flightid="+row.flight.id+"&status=2&date="+row.date+"&code="+row.flight.code;
   			$("#txtx").html("取消原因");
   		}
   		
   		function reserveFlightstatus(){
   			$("#fm").form("submit",
   					{
   						url : url,
   						onSubmit : function() {
   							return true;
   						},
   						success : function(result) {
   							var result = eval('(' + result + ')');
   							if (result.errorMsg) {
   								$.messager.alert('系统提示', "<font color=red>"+ result.errorMsg + "</font>");
   								return;
   							} else {
   								$.messager.alert('系统提示', '操作成功');
   								closeFlightstatusDialog();
   								$("#dg").datagrid("reload");
   							}
   						}
   			});
   		}
   		
   		function closeFlightstatusDialog(){
   			$("#fm").form('clear');
   			$("#dlg").dialog("close");
   		}
   		
   		
   		function searchFlightstatus() {
   			$('#dg').datagrid('load', {
   				keyword : $('#s_keyword').val(),
   				date : $('#s_date').datebox("getValue")
   			});
   		}
   	</script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="flightstatusList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
            		<th field="ids" width="100" align="center" data-options="hidden:'true'" formatter="formatID">班次ID</th>
                	<th field="date" width="100" align="center"   >日期</th>
                	<th field="code" width="100" align="center" formatter="formatCode">班次</th>
                	<th field="status" width="100" align="center"  >状态</th>
                	<th field="remarks" width="200" align="center"  >晚点时间/取消原因</th>
            	</tr>
        </thead>
</table>
<script type="text/javascript">
	function formatCode(value,row,index){
		return row.flight.code;
	}
	
	function formatID(value,row,index){
		return row.flight.id;
	}
	
</script>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="fd195bc2848b4c6aa665fefdf59d65" clazz="easyui-linkbutton" onClick="wandianDialog()" name="晚点"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="3ed8fe99d5f04b949ef7a978a0b539" clazz="easyui-linkbutton" onClick="quxiaoDialog()" name="取消"  iconCls="icon-edit" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;关键字：&nbsp;<input type="text" name="s_keyword" id="s_keyword" size="20" onkeydown="if(event.keyCode==13) searchFlightstatus()"/>
		&nbsp;日期：&nbsp;<input class="easyui-datebox" name="s_date" id="s_date" size="20" onkeydown="if(event.keyCode==13) searchFlightstatus()"/>
		<a href="javascript:searchFlightstatus()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 新增和修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 320px;height: 120px;padding: 1px 2px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
	<table cellspacing="5px;">
		<tr>
  			<td id="txtx">晚点时间</td>
  			<td><input name="remarks" /></td>
  		</tr>
  	</table>
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveFlightstatus()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeFlightstatusDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


</body>
</html>
