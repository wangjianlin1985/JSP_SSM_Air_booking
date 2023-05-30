

function searchAirport() {
	$('#dg').datagrid('load', {
		keyword : $('#s_keyword').val()
	});
}

var url = "";
function openAirportAddDialog(){
	url = "reserveAirport.htm";
	$("#dlg").dialog("open").dialog("setTitle", "添加");
}

function openAirportUpdateDialog() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要编辑的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle", "修改");
	$("#fm").form("load", row);
	url = "reserveAirport.htm?id=" + row.id;
	$("#cityid").combobox("setValue",row.city.id);
}


function closeAirportDialog(){
	$("#fm").form('clear');
	$("#dlg").dialog("close");
}


function reserveAirport(){
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
						$.messager.alert('系统提示', '保存成功');
						closeAirportDialog();
						$("#dg").datagrid("reload");
					}
				}
	});
}



function deleteAirport() {
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
			$.post("deleteAirport.htm", {
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





$(function() {
	$("#cityid").combobox({
		url : path + '/city/cityCombobox.htm',
		method : 'post',
		valueField : 'id',
		textField : 'name',
		editable : false,
		panelHeight : 'auto'
	});
});
