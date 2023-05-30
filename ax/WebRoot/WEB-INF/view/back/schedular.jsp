<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>用户主页</title>
    <style type="text/css">
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
	margin-left:80px;margin-top:10px
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	width:60px;
	align:center;
	text-align:center;
	border-style: solid;
	border-color: #666666;
}

.buyed{
	background:red;
	color:white;
}
</style>
  	<script type="text/javascript">
  	$(function() {
  		$("#s_flightid").combobox({
  			url : path + '/flight/flightCombobox.htm',
  			method : 'post',
  			valueField : 'id',
  			textField : 'code',
  			editable : false,
  			panelHeight : 'auto'
  		});
  	});
  	
  	function searchSchedular(){
  		$.ajax({
  			url : '${path}/back/schedular/loadSchedular.htm',
  			data : {date:$("#s_date").datebox("getValue"),flightid:$("#s_flightid").combobox("getValue")},
  			dataType:'json',
  			type:'post',
  			async:false,
  			success:function(data){
  				if(data.errorMsg){
  					alert(data.errorMsg);
  					return false;
  				} else {
  					var seatinfo = "总座位"+data.total + " 已购买"+data.buyed+" 余座"+data.other;
  					$("#seatinfo").html(seatinfo);
  					var str = "";
  					for(var i=0;i<data.schedulars.length;i++){
  						str += "<tr >";
  						var x = data.schedulars[i];
  						for(var j=0;j<x.schedular.length;j++){
  							var y = x.schedular[j].schedular;
  							if(y.customerid==null || y.customerid==''){
  								str += ("<td>" + y.name + "</td>");
  							} else {
  								str += ("<td class='buyed'  >" + y.name+"  "+y.cname + "</td>");
  							}
  						}
  						str += "</tr>";
  					}
  					$("#dg").html(str);
  				}
  			}
  		});
  	}

  	</script>
   </head>
 
 
<body style="margin:1px">

<div class="updownInterval"> </div>
	<div>
		&nbsp;日期：&nbsp;<input class="easyui-datebox" name="s_date" id="s_date" size="20" />
		&nbsp;航班：&nbsp;<input class="easyui-combobox" id="s_flightid" size="20" />
		<a href="javascript:searchSchedular()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
<div style="margin-left:80px;margin-top:20px;heiht:40px;line-height:40px;font-size:26px" id="seatinfo"></div>
<!-- 加载数据表格 -->
<table  id="dg" class="gridtable"  >
       
</table>

</body>
</html>
