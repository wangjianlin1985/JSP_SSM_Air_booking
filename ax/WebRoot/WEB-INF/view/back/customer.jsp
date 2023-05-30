<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>用户主页</title>
  	<script type="text/javascript">
  		function searchCustomer(){
  			$('#dg').datagrid('load', {
  				cname : $('#s_cname').val(),
  				phone : $('#s_phone').val()
  			});
  		}
  	</script>
   </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="list.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="fidel:'id',hidden:'true'">编号</th>
                	<th field="cname" width="60" align="center">中文名</th>
                	<th field="ename" width="60" align="center">英文名</th>
                	<th field="idcardno" width="60" align="center">身份证</th>
                	<th field="phone" width="60" align="center">电话</th>
                	<th field="email" width="60" align="center">邮件</th>
                	<th field="sex" width="60" align="center" >性别</th>
            	</tr>
        </thead>
</table>
<script type="text/javascript">
	function updatePwd(){
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert('系统提示', '请选择一条要编辑的数据！');
			return;
		}
		var row = selectedRows[0];
		$.messager.confirm("系统提示", "您确认要重置该用户密码吗？", function(r) {
			if (r) {
				$.post("updatePwd.htm", {
					id : row.id
				}, function(result) {
					if (result.success) {
						$.messager.alert('系统提示', "重置成功！");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
					}
				}, "json");
			}
		});
	}
</script>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="81e6f53133d4481cbb2e0ec74546b1" clazz="easyui-linkbutton" onClick="updatePwd()" name="重置密码"  iconCls="icon-remove" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;姓名：&nbsp;<input type="text" name="s_cname" id="s_cname" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
		&nbsp;电话：&nbsp;<input type="text" name="s_phone" id="s_phone" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
		<a href="javascript:searchCustomer()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>

</body>
</html>
