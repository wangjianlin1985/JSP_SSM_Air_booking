<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>发布通知</title>
    
    <script type="text/javascript">
	
    var ue;
    $(function(){	
    	 ue = UE.getEditor('container');
    });
    

        function reserveNotice(){
        	 $.ajax({
       			url:'${path}/notice/reserveNotice.htm',
       			dataType:'json',
       			type:'post',
       			data: $('#fm').serialize(),
       			async:false,
       			success:function(result){
     				if(result.errorMsg){
     					alert(result.errorMsg);
     				}else{
     					alert('添加成功');
     					window.opener=null;window.close();
     				}
       			}
       		});
        }
        
        

    </script>
    </head>
 
 
<body  style="margin:5px">




 <form id="fm" method="post"><br/>
 	<div >
 		<label>标题</label>
 		<input type="text" id="title" name="title" class="easyui-validatebox" required="true"/>
 	</div>
 	
 	<br/>
 	<div >
 		<label>置顶</label>
 		<select name="istop" style="width:160px"><option value='0'>否</option><option value='1'>是</option></select>
 	</div>
 	
 	<br/>
 	<div >
 		<label>内容  &nbsp;&nbsp;&nbsp;&nbsp;<button style="width:100px" onclick="reserveNotice()">保存</button></label>
 		<script id="container" name="content" style="height:400px;margin-top:10px" type="text/plain" />
 	</div>
  		
 </form>


</body>
</html>
