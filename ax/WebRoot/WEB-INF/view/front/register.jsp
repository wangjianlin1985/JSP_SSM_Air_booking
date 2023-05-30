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
       				新用户注册
       			</div>
       	<form id="fm">
       		<div  style="width:70%;margin-left:15%;align:center;text-align:center;">
       			<table style="border-collapse:separate; border-spacing:30px 20px;width:100%;margin:auto;margin-left:100px">
					<tr>
						<td align="right">中文名&nbsp;&nbsp;&nbsp;</td>
						<td><input  name="cname" /></td>
					</tr
					
					<tr>
						<td align="right">英文名&nbsp;&nbsp;&nbsp;</td>
						<td><input  name="ename" /></td>
					</tr>
					
					<tr>
						<td align="right">身份证&nbsp;&nbsp;&nbsp;</td>
						<td><input  name="idcardno" id="idcardno" onblur="checkIdcardno(this.value)" /></td>
					</tr>
					
					<tr>
						<td align="right">性别&nbsp;&nbsp;&nbsp;</td>
						<td><input type="radio" name="sex" value="男" checked="checked" />男&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="sex" value="女" />女
						
						</td>
					</tr>
					
					<tr>
						<td align="right">生日&nbsp;&nbsp;&nbsp;</td>
						<td><input  name="birth" id="birth" /></td>
					</tr>
					
					<tr>
						<td align="right">邮箱&nbsp;&nbsp;&nbsp;</td>
						<td><input  name="email" id="email"  onblur="checkEmail(this.value)"  /></td>
					</tr>
					
					<tr>
						<td align="right">电话&nbsp;&nbsp;&nbsp;</td>
						<td><input  name="phone" id="phone"  onblur="checkPhone(this.value)" /></td>
					</tr>
					
					<tr>
						<td align="right">密码&nbsp;&nbsp;&nbsp;</td>
						<td><input  name="password" id="pwd1" type="password"  style="height:26px;width:206px"  /></td>
					</tr>
					
					<tr>
						<td align="right">密码确认&nbsp;&nbsp;&nbsp;</td>
						<td><input  id="pwd2"   type="password"   style="height:26px;width:206px" /></td>
					</tr>
					
					<tr>
						<td > </td>
						<td ><button onclick="register1();return false" class="btn btn-success">注册</button> &nbsp;&nbsp;&nbsp;&nbsp; 
						<button onclick="login1();return false" class="btn btn-success">直接登陆</button>   </td>
					</tr>
					</form>
				</table>	
				</div>
       </div>
		<script type="text/javascript">
			function login1(){
				window.location.href="${path}/front/customer/toLogin.htm";
			}
		
			function register1(){
				var pwd1 = $("#pwd1").val();
				var pwd2 = $("#pwd2").val();
				if(pwd1=='' || pwd2 =='' || pwd1!=pwd2){
					alert("两次密码不一样");
					return;
				}
				$.ajax({
					url:"${path}/front/customer/register.htm",
					data:$('#fm').serialize(),
					dataType:'json',
					type:'post',
					async:false,
					success:function(result){
						if (result.errorMsg) {
							alert(result.errorMsg);
						} else {
							alert("注册成功请登陆");
							window.location.href="${path}/front/customer/toLogin.htm";
						}
					}
				});
			}
		
		
		
			function checkIdcardno(idcardno){
				checkExist(1,idcardno);
			}
			
			function checkEmail(email){
				checkExist(2,email);
			}
			function checkPhone(phone){
				checkExist(3,phone);
			}
			function checkExist(type,keyword){
				$.ajax({
					url :'${path}/front/customer/checkExist.htm',
					data:{type:type,keyword:keyword},
					dataType:'json',
					type:'post',
					async:false,
					success:function(data){
						if(data.success){
							if(type==1){
								var birth = keyword.substring(6,10)+"-"+keyword.substring(10,12)+"-"+keyword.substring(12,14);
								$("#birth").val(birth);
							}
						}else {
							if(type==1){
								$("#idcardno").val("");
								alert("身份证重复");
							}
							if(type==2){
								$("#email").val("");
								alert("邮箱重复");
							}
							if(type==3){
								$("#phone").val("");
								alert("电话重复");
							}
						}					
					}
				});
			}
		</script>
</body>
</html>


