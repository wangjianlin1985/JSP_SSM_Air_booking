<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>通知主页</title>
    
    <style type="text/css">
    	.datagrid-row-selected {
    		background:white;
    	}
    </style>
    
    <script type="text/javascript">
		// 条件搜索通知信息
		function searchAd(){
    		$('#dg').datagrid('load',{
    			isshow:$("#s_isshow").val()
    		});
        }

		var urlx = "";
		// 打开新增通知信息对话框
        function openAdAddDialog(){
			urlx = "reserveAd.htm";
        	$("#dlg").dialog("open").dialog("setTitle", "添加");
        }

		// 打开修改通知信息对话框
        function openAdUpdateDialog(){
    		var selectedRows=$("#dg").datagrid('getSelections');
    		if(selectedRows.length!=1){
    			$.messager.alert('系统提示','请选择一条要编辑的数据！');
    			return;
    		}
    		var row=selectedRows[0];
    		$("#fm").form("load", row);
			$('#photo').attr("src","${path}/"+row.cover);
    		urlx = "reserveAd.htm?id=" + row.id;
    		$("#dlg").dialog("open").dialog("setTitle", "修改");
    		
    	}

		
		

        function closeAdDialog(){
        	$("#fm").form('clear');
        	$("#dlg").dialog("close");
        }


        function reserveAd(){
        	$("#fm").form("submit",
        			{
        				url : urlx,
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
        						closeAdDialog();
        						$("#dg").datagrid("reload");
        					}
        				}
        	});
        }

		
		
		function deleteAd(){
			var selectedRows=$("#dg").datagrid('getSelections');
    		if(selectedRows.length == 0){
    			$.messager.alert('系统提示','请选择一条要编辑的数据！');
    			return;
    		}
    		var strIds=[];
    		for(var i=0;i<selectedRows.length;i++){
    			strIds.push(selectedRows[i].id);
    		}
    		var ids=strIds.join(","); 
    		$.messager.confirm("系统提示","您确认要删除这些数据吗？",function(r){
    			if(r){
    				$.post("deleteAd.htm",{ids:ids},function(result){
    					if(result.success){
    						$.messager.alert('系统提示',"您已成功删除数据！");
    						$("#dg").datagrid("reload");
    					}else{
    						$.messager.alert('系统提示',result.errorMsg);
    					}
    				},"json");
    			}
    		});
    		
		}
		
		
	
		
		
    </script>
    </head>
 
 
<body  style="margin:1px">


<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="list.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="field:'id',width:80,hidden:'true'" align="center">通知编号</th>
                	<th field="title" width="60" align="center"    >标题</th>
                	<th field="cover" width="60" align="center" formatter="formatCover">查看图片</th>
                	<th field="link" width="60" align="center" formatter="formatLink">链接地址</th>
                	<th field="isshow" width="60" align="center" formatter="formatSHOW">是否显示</th>
            	</tr>
        </thead>
        <script>
        	function formatCover(value,row,index){
        		return "<a javascript:void(0) onclick='openCover(\""+row.cover+"\")'>图片地址</a>";
        	}
        	
        	function formatLink(value,row,index){
        		return "<a javascript:void(0) onclick='openLink(\""+row.link+"\")'>"+value+"</a>";
        	}
        	
        	function formatSHOW(value,row,index){
        		if(value=="1"){
        			return "是";
         		} else {
         			return "否";
         		}
        	}
        	
        	function openCover(cover){
        		window.open("${path}/"+cover);
        	}
        	
        	function openLink(link){
        		window.open(link);
        	}
        </script>
</table>
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="9bed5609b44448ae828d1af446e935" clazz="easyui-linkbutton" onClick="openAdAddDialog()" name="添加"  iconCls="icon-add" ></privilege:operation>
		<privilege:operation operationId="8ebfd9407368433996bdc70868bf07" clazz="easyui-linkbutton" onClick="openAdUpdateDialog()" name="修改"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="b05959ff5ed54f03b9b0480b31977e" clazz="easyui-linkbutton" onClick="deleteAd()" name="删除"  iconCls="icon-remove" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		<input type="hidden" id="hdeptid"  />
		&nbsp;是否显示：&nbsp;<input type="text" name="s_show" id="s_show" size="20" onkeydown="if(event.keyCode==13) searchAd()"/>
		<a href="javascript:searchAd()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 新增和修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 620px;height: 420px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post" enctype="multipart/form-data" >
 	<table cellspacing="5px;">
  		<tr>
  			<td>标题：</td>
  			<td><input type="text" id="title" name="title" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>链接：</td>
  			<td><input type="text" id="link" name="link" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>显示：</td>
  			<td><select name="isshow" style="width:200px">
  				<option value="0">不显示</option>
  				<option value="1">显示</option>
  			</select></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		<tr>
  			<td>图片：</td>
  			<td><input type="file" name="file" id="ii" onchange="preImg(this.id,'photo');" /></td>
  		</tr>
  		<tr>
  			<td>预览：</td>
  			<td><img id="photo" src="" width="100px" height="100px" style="display: block;" /></td>
  		</tr>
  		<tr><td>&nbsp;</td></tr>
  		
  		
  	</table>
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveAd()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeAdDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<script type="text/javascript">
function getFileUrl(sourceId) {  
    var url;  
    if (navigator.userAgent.indexOf("MSIE")>=1) { // IE  
        url = document.getElementById(sourceId).value;  
    } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox  
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));  
    } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome  
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));  
    }  
    return url;  
}  
function preImg(sourceId, targetId) {   
    var url = getFileUrl(sourceId);   
    var imgPre = document.getElementById(targetId);   
    imgPre.src = url;   
}   
</script>




</body>
</html>
