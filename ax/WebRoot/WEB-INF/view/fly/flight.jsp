<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>航班主页</title>
   	<script type="text/javascript" src="${path }/js/fly/flight.js"></script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" 
   				 pagination="true" rownumbers="true" url="flightList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="fidel:'id',hidden:'true'">编号</th>
                	<th field="code" width="100" align="center">班次</th>
                	<th field="origincity" width="100" align="center"  formatter="formatOrigincity" >始发地</th>
                	<th field="originport" width="200" align="center"  formatter="formatOriginport" >始发站</th>
                	<th field="destinationcity" width="100" align="center"  formatter="formatDestinationcity" >目的地</th>
                	<th field="destinationport" width="200" align="center"  formatter="formatDestinationport" >目的站</th>
                	<th field="start" width="100" align="center"   >出发时间</th>
                	<th field="end" width="100" align="center"  >到达时间</th>
                	<th field="planeid" width="100" align="center" formatter="formatPlane">飞机信息</th>
                	<th field="weeks" width="300" align="center">每星期航行</th>
            	</tr>
        </thead>
</table>
<script type="text/javascript">
	function formatOrigincity(value,row,index){
		return row.origincity.name;
	}
	
	function formatOriginport(value,row,index){
		return row.originport.name;
	}
	
	function formatDestinationcity(value,row,index){
		return row.destinationcity.name;
	}
	
	function formatDestinationport(value,row,index){
		return row.destinationport.name;
	}
	
	function formatPlane(value,row,index){
		return row.plane.name;
	}
</script>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="8396093d892f46289f80beb8f121ae" clazz="easyui-linkbutton" onClick="openFlightAddDialog()" name="添加"  iconCls="icon-add" ></privilege:operation>
		<privilege:operation operationId="d771682803784d22a7bcb76264b259" clazz="easyui-linkbutton" onClick="openFlightUpdateDialog()" name="修改"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="ec0164e1130c456db43ef83878722e" clazz="easyui-linkbutton" onClick="deleteFlight()" name="删除"  iconCls="icon-remove" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;班次：&nbsp;<input type="text" name="s_code" id="s_code" size="20" onkeydown="if(event.keyCode==13) searchFlight()"/>
		&nbsp;始发地：&nbsp;<input type="text" name="s_origincity" id="s_origincity" size="20" onkeydown="if(event.keyCode==13) searchFlight()"/>
		&nbsp;目的地：&nbsp;<input type="text" name="s_destinationcity" id="s_destinationcity" size="20" onkeydown="if(event.keyCode==13) searchFlight()"/>
		<a href="javascript:searchFlight()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 新增和修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 620px;height: 420px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
	<table cellspacing="5px;">
		<tr>
  			<td>名称：</td>
  			<td><input id="code" name="code" class="easyui-validatebox" required="true" /></td>
  			<td>&nbsp;&nbsp;</td>
  			<td>飞机：</td>
  			<td><input id="planeid" name="plane.id" class="easyui-combobox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>始发地：</td>
  			<td><input id="origincityid" name="origincity.id" class="easyui-combobox" required="true"/></td>
  			<td>&nbsp;&nbsp;</td>
  			<td>始发站：</td>
  			<td><input id="originportid" name="originport.id" class="easyui-combobox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>目的地：</td>
  			<td><input id="destinationcityid" name="destinationcity.id" class="easyui-combobox" required="true"/></td>
  			<td>&nbsp;&nbsp;</td>
  			<td>目的站：</td>
  			<td><input id="destinationportid" name="destinationport.id" class="easyui-combobox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>出发时间：</td>
  			<td><input id="start" name="start" class="easyui-timespinner" required="true" /></td>
  			<td>&nbsp;&nbsp;</td>
  			<td>到达时间：</td>
  			<td><input id="end" name="end" class="easyui-timespinner" required="true" /></td>
  		</tr>
  		<tr>
  			<td>飞行星期：</td>
  			<td colspan="4">
  				<input type="checkbox" id="星期一"  name="weeks" value="星期一" />星期一<br/>
  				<input type="checkbox" id="星期二"  name="weeks" value="星期二" />星期二<br/>
  				<input type="checkbox" id="星期三"  name="weeks" value="星期三" />星期三<br/>
  				<input type="checkbox" id="星期四"  name="weeks" value="星期四" />星期四<br/>
  				<input type="checkbox" id="星期五"  name="weeks" value="星期五" />星期五<br/>
  				<input type="checkbox" id="星期六"  name="weeks" value="星期六" />星期六<br/>
  				<input type="checkbox" id="星期日"  name="weeks" value="星期日" />星期日<br/>
  			</td>
  		</tr>
  	</table>
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveFlight()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeFlightDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


</body>
</html>
