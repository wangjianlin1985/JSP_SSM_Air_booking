<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>城市主页</title>
   	<script type="text/javascript" src="${path }/js/info/city.js"></script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="cityList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="fidel:'id',hidden:'true'">用户编号</th>
                	<th field="name" width="60" align="center">名称</th>
                	<th field="lng" width="60" align="center">经度</th>
                	<th field="lat" width="60" align="center">纬度</th>
                	<th field="firstletter" width="60" align="center">首字母</th>
                	<th field="seq" width="60" align="center">排序</th>
                	<th field="ishot" width="60" align="center" formatter="formatIshot">是否热门城市</th>
            	</tr>
        </thead>
</table>
<script type="text/javascript">
	function formatIshot(value,row,index){
		if(value=="0"){
			return "否";
		} else {
			return "是";
		}
	}
</script>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="bac48c00792240ab8fe1a6c8b007e4" clazz="easyui-linkbutton" onClick="openCityAddDialog()" name="添加"  iconCls="icon-add" ></privilege:operation>
		<privilege:operation operationId="41114d5298e84dd6a075cec32f12c3" clazz="easyui-linkbutton" onClick="openCityUpdateDialog()" name="修改"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="2e2a86f7e4be41ed837e215aeebca7" clazz="easyui-linkbutton" onClick="deleteCity()" name="删除"  iconCls="icon-remove" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;首字母：&nbsp;<input type="text" name="s_firstletter" id="s_firstletter" size="20" onkeydown="if(event.keyCode==13) searchCity()"/>
		&nbsp;是否热门：&nbsp;<select id="s_ishot">
				<option value=''>全部</option>
				<option value='1'>是</option>
				<option value='0'>否</option>
			</select>
		<a href="javascript:searchCity()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
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
  			<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>首字母：</td>
  			<td><input type="text" id="firstletter" name="firstletter" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>经度：</td>
  			<td><input type="text" id="lng" name="lng" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>纬度：</td>
  			<td><input type="text" id="lat" name="lat" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>热门：</td>
  			<td><select name="ishot">
					<option value='0'>否</option>
					<option value='1'>是</option>
				</select>
			</td>
  		</tr><tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>排序：</td>
  			<td><input type="number" id="seq" name="seq" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		
  	</table>
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveCity()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeCityDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


</body>
</html>
