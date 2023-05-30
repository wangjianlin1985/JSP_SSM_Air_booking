function searchSchedular() {
	$('#dg').datagrid('load', {
		keyword : $('#s_keyword').val(),
		start : $('#s_start').datebox("getValue"),
		end : $('#s_end').datebox("getValue")
	});
}

function openSchedularAddDialog(){
	$("#dlg").dialog("open").dialog("setTitle", "生成");
}


function closeSchedularDialog(){
	$("#fm").form('clear');
	$("#dlg").dialog("close");
}


function reserveSchedular(){
	$("#fm").form("submit",
			{
				url : "addSchedular.htm",
				onSubmit : function() {
					return true;
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.errorMsg) {
						$.messager.alert('系统提示', "<font color=red>"+ result.errorMsg + "</font>");
						return;
					} else {
						$.messager.alert('系统提示', '生成成功');
						closeSchedularDialog();
						$("#dg").datagrid("reload");
					}
				}
	});
}



function deleteSchedular() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length == 0) {
		$.messager.alert('系统提示', '请选择要删除的数据！');
		return;
	}
	var strIds = [];
	var strDates = [];
	for ( var i = 0; i < selectedRows.length; i++) {
		strIds.push(selectedRows[i].flight.id);
		strDates.push(selectedRows[i].date);
	}
	var ids = strIds.join(",");
	var dates = strDates.join(",");
	$.messager.confirm("系统提示", "您确认要删除这些数据吗？", function(r) {
		if (r) {
			$.post("deleteSchedularByFlight.htm", {
				ids : ids,
				dates : dates
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
	$("#flightid").combobox({
		url : path + '/flight/flightCombobox.htm',
		method : 'post',
		valueField : 'id',
		textField : 'code',
		editable : false,
		panelHeight : 'auto'
	});
});
