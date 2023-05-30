<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>
<html>
<head>
		 <link href="${path }/homepage/css/bootstrap-combined.min.css" rel="stylesheet">
		 <link href="${path }/mui/css/mui.min.css" rel="stylesheet">
		 <script src="${path }/mui//js/mui.min.js"></script>
		 <script type="text/javascript">
		 function tohbcx(){
			 window.location.href="${path}/front/hb/index.htm";
			}
		 
		 function tohome(){
			 window.location.href="${path}/tohome.htm";
		 }
		 
		 function totzgg(){
				window.location.href="${path}/front/noticead/tonotice.htm";
			}
		 </script>
        <title>安翔航空</title>
		
        
    </head>

    <body style="width:70%;margin-left:15%">
	
		<div style="height:50px;line-height: 50px;">
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="tohome()">首页</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="tohbcx()">航班查询</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="totzgg()">通知公告</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:red;font-size:22px;">个人中心</div>
		</div>
	
       <div style="">
       			<div style="align:center;text-align:center;height:50px;line-height:50px;font-size:24px;">
       				找回密码
       			</div>
       	<form id="fm">
       		<div  style="width:70%;margin-left:15%;align:center;text-align:center;">
       			<table style="border-collapse:separate; border-spacing:30px 20px;width:100%;margin:auto;;margin-left:100px">
				
					
					<tr>
						<td align="right">账户&nbsp;&nbsp;&nbsp;</td>
						<td><input id="keyword" name="keyword"  placeholder="手机/邮箱/身份证" />
					</tr>
					
					<tr>
						<td >&nbsp;</td>
						<td >
							<button onclick="updatepwd();return false" style="width:98px"  class="btn btn-success">找回密码</button> 
							<button onclick="login1();return false" style="width:98px"  class="btn btn-success">登陆</button> 
						 </td>
					</tr>
					
					</form>
				</table>	
				</div>
       </div>
		<script type="text/javascript">
			function login1(){
				window.location.href="${path}/front/customer/toLogin.htm";
			}
		
			function updatepwd(){
				$.ajax({
					url:'${path}/front/customer/updatepwd.htm',
					data:{
						keyword:$("#keyword").val(),
					},
					dataType:'json',
					type:'post',
					async:false,
					success:function(data){
						if(data.errorMsg){
							alert(data.errorMsg);
						} else {
							alert("密码已找回，新密码已发送您的邮箱"+data.email);
							login1();
						}
					}
				});
			}
		
		</script>
</body>
</html>


