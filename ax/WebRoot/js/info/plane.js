

function searchPlane() {
	$('#dg').datagrid('load', {
		name : $('#s_name').val()
	});
}

var url = "";
function openPlaneAddDialog(){
	url = "reservePlane.htm";
	$("#dlg").dialog("open").dialog("setTitle", "添加");
}

function openPlaneUpdateDialog() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要编辑的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle", "修改");
	$("#fm").form("load", row);
	url = "reservePlane.htm?id=" + row.id;
}


function closePlaneDialog(){
	$("#fm").form('clear');
	$("#dlg").dialog("close");
}


function reservePlane(){
	$("#fm").form("submit",
			{
				url : url,
				onSubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.errorMsg) {
						$.messager.alert('系统提示', "<font color=red>"+ result.errorMsg + "</font>");
						return;
					} else {
						$.messager.alert('系统提示', '保存成功');
						closePlaneDialog();
						$("#dg").datagrid("reload");
					}
				}
	});
}



function deletePlane() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length == 0) {
		$.messager.alert('系统提示', '请选择要删除的数据！');
		return;
	}
	var strIds = [];
	for ( var i = 0; i < selectedRows.length; i++) {
		strIds.push(selectedRows[i].id);
	}
	var ids = strIds.join(",");
	$.messager.confirm("系统提示", "您确认要删除这些数据吗？", function(r) {
		if (r) {
			$.post("deletePlane.htm", {
				ids : ids
			}, function(result) {
				if (result.success) {
					$.messager.alert('系统提示', "您已成功删除<font color=red>"	+ strIds.length + "</font>条数据！");
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert('系统提示', result.errorMsg);
				}
			}, "json");
		}
	});
}

