function searchFlight() {
	$('#dg').datagrid('load', {
		code : $('#s_code').val(),
		origincity : $('#s_origincity').val(),
		destinationcity : $('#s_destinationcity').val()
	});
}

var url = "";
function openFlightAddDialog(){
	url = "reserveFlight.htm";
	$("#dlg").dialog("open").dialog("setTitle", "添加");
}

function openFlightUpdateDialog() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要编辑的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle", "修改");
	$("#fm").form("load", row);
	url = "reserveFlight.htm?id=" + row.id;
	
	$("#planeid").combobox("setValue",row.plane.id);
	$("#origincityid").combobox("setValue",row.origincity.id);
	$("#originportid").combobox("setValue",row.originport.id);
	$("#destinationcityid").combobox("setValue",row.destinationcity.id);
	$("#destinationportid").combobox("setValue",row.destinationport.id);
	var ws = row.weeks;
	$(ws.split(",")).each(function(i,dom){
		$(":checkbox[id='"+dom+"']").prop("checked",true);
	});
	
}


function closeFlightDialog(){
	$("#fm").form('clear');
	$("#dlg").dialog("close");
}


function reserveFlight(){
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
						closeFlightDialog();
						$("#dg").datagrid("reload");
					}
				}
	});
}



function deleteFlight() {
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
			$.post("deleteFlight.htm", {
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
	$("#planeid").combobox({
		url : path + '/plane/planeCombobox.htm',
		method : 'post',
		valueField : 'id',
		textField : 'name',
		editable : false,
		panelHeight : 'auto'
	});
	
	
	
	
	$("#origincityid").combobox({
		url : path + '/city/cityCombobox.htm',
		method : 'post',
		valueField : 'id',
		textField : 'name',
		editable : false,
		panelHeight : 'auto',
		onChange: function (newValue, oldValue) {  
			$("#originportid").combobox({
				url : path + '/airport/airportCombobox.htm?cityid='+newValue,
				method : 'post',
				valueField : 'id',
				textField : 'name',
				editable : false,
				panelHeight : 'auto'
			})
        } 
	});
	
	
	$("#destinationcityid").combobox({
		url : path + '/city/cityCombobox.htm',
		method : 'post',
		valueField : 'id',
		textField : 'name',
		editable : false,
		panelHeight : 'auto',
		onChange: function (newValue, oldValue) {  
			$("#destinationportid").combobox({
				url : path + '/airport/airportCombobox.htm?cityid='+newValue,
				method : 'post',
				valueField : 'id',
				textField : 'name',
				editable : false,
				panelHeight : 'auto'
			})
        } 
	});
	
});
