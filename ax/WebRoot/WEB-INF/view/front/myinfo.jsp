<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>
<html>
<head>
		  <link href="${path }/homepage/css/bootstrap-combined.min.css" rel="stylesheet">
		 <link href="${path }/mui/css/mui.min.css" rel="stylesheet">
		 <script src="${path }/mui//js/mui.min.js"></script>
		 
		 <style>
        	.title{
        		height:100px;
        		line-height: 100px;
        		border-top:1px solid white;
        		text-align:center;
        		align:center;
        		font-size:22px;
        	}
        	
        </style>
        <title>安翔航空</title>
		 <script type="text/javascript">
		 function tohbcx(){
			 window.location.href="${path}/front/hb/index.htm";
			}
		 
		 function tohome(){
			 window.location.href="${path}/tohome.htm";
		 }
		 
		 function tomyorder(){
			 window.location.href="${path}/front/order/toMyOrder.htm";
		 }
		 
		 function tomytporder(){
			 window.location.href="${path}/front/order/toMyTPOrder.htm";
		 }
		 
		 function totzgg(){
				window.location.href="${path}/front/noticead/tonotice.htm";
			}
		 </script>
        
    </head>

    <body style="width:70%;margin-left:15%">
	
		<div style="height:50px;line-height: 50px;">
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="tohome()">首页</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="tohbcx()">航班查询</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="totzgg()">通知公告</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:red;font-size:22px;">个人中心</div>
		</div>
		
		<div style="width:150px;height:400px;background:#fbfbfb">
			<div class="title" style="background:red">个人信息</div>
			<div class="title" onclick="tomyorder();return false;">我的订单</div>
			<div class="title" onclick="tomytporder();return false;">我的退单</div>
		</div>
		<div style="width:700px;height:400px;float:left;margin-left:150px;margin-top:-399px">
			<form id="fm" >
					<table style="border-collapse:separate; border-spacing:30px 20px;width:100%;margin:auto;;margin-left:100px">
					
					<input type="hidden" name="id" id="customerid" value="${currentCustomer.id }" />
					<tr>
						<td align="right">中文名&nbsp;&nbsp;&nbsp;</td>
						<td id="cname">${currentCustomer.cname }</td>
					</tr
					
					<tr>
						<td align="right">英文名&nbsp;&nbsp;&nbsp;</td>
						<td id="ename">${currentCustomer.ename }</td>
					</tr>
					
					<tr>
						<td align="right">身份证&nbsp;&nbsp;&nbsp;</td>
						<td id="idcardno"> ${currentCustomer.idcardno } </td>
					</tr>
					
					<tr>
						<td align="right">性别&nbsp;&nbsp;&nbsp;</td>
						<td id="sex">${currentCustomer.sex }</td>
					</tr>
					
					<tr>
						<td align="right">生日&nbsp;&nbsp;&nbsp;</td>
						<td id="birth">${currentCustomer.birth }</td>
					</tr>
					
					<tr>
						<td align="right">邮箱&nbsp;&nbsp;&nbsp;</td>
						<td id="email">${currentCustomer.email }</td>
					</tr>
					
					<tr>
						<td align="right">电话&nbsp;&nbsp;&nbsp;</td>
						<td id="phone">${currentCustomer.phone }</td>
					</tr>
					
					<tr><td></td>
						<td  id="bt">	<button onclick="tobianji();return false" style="width:98px"  class="btn btn-success">编辑</button>&nbsp;
							<button onclick="xiugai();return false" style="width:98px"  class="btn btn-success">修改密码</button>
						 </td>
					</tr>
					
				</table>	
				</form>
		</div>
		
		<div id="detail" style="align:center;text-align:center;position:fixed;z-index:999px;top:180px;width:500px;height:300px;left:450px;background:white;display:none">
			<table style="margin:auto;align:left;border-collapse: separate;border-spacing:30px 10px" >
				<tr><td>旧密码</td><td><input type="password" name="oldpwd" id="oldpwd"/></td></tr>
				<tr><td>新密码</td><td><input type="password" name="newpwd" id="newpwd1" /></td></tr>
				<tr><td>确认密码</td><td><input type="password" id="newpwd2" id="newpwd2" /></td></tr>
				<tr ><td></td><td ><button onclick='savepwd()' class="btn btn-success">修改</button>&nbsp;<button class="btn btn-success" onclick='closexiugai();return false;'>关闭</button></td></tr>
			</table>
		</div>
	
		<script type="text/javascript">
			function xiugai(){
				 $("#detail").show();
			}
			
			function savepwd(){
				var customerid = $("#customerid").val();
				var oldpwd = $('#oldpwd').val();
				var newpwd1 = $('#newpwd1').val();
				var newpwd2 = $('#newpwd2').val();
				if(newpwd1==null || newpwd1=='' || newpwd2==null || newpwd2=='' || newpwd1!=newpwd2){
					alert("两次密码不一致，请重新输入");
					return false;
				}
				$.messager.confirm("系统提示", "您确认要修改密码吗？", function(r) {
					if (r) {
						$.ajax({
							url : '${path}/front/customer/updatepwdFront.htm',
							data:{customerid:customerid,oldpwd:oldpwd,newpwd:newpwd1},
							dataType:'json',
							type:'post',
							success:function(result){
								if (result.errorMsg) {
									alert(result.errorMsg);
								} else {
									alert("修改成功");
									closexiugai();
								}
							}
						});
					}
				});
				
			}
		
			function closexiugai(){
				$("#oldpwd").val("");
				$("#newpwd1").val("");
				$("#newpwd1").val("");
				$("#detail").hide();	
			}
			
			
			function tobianji(){
				$.ajax({
					url :'${path}/front/customer/findOne.htm',
					dataType:'json',
					type:'post',
					async:false,
					success:function(data){
						var customer = data.customer;	
						$("#cname").html("<input name='cname' value='"+customer.cname+"'  />");
						$("#ename").html("<input name='ename' value='"+customer.ename+"'  />");
						$("#idcardno").html("<input name='idcardno' value='"+customer.idcardno+"' onblur='checkExist(1,this.value)'  />");
						$("#email").html("<input name='email' value='"+customer.email+"'  onblur='checkExist(2,this.value)' />");
						$("#phone").html("<input name='phone' value='"+customer.phone+"'  onblur='checkExist(3,this.value)'  />");
						$("#birth").html("<input name='birth' value='"+customer.birth+"'  />");
						$("#sex").html("<input name='sex' type='radio' value='男' >男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name='sex' type='radio' value='女' >女");
						 $(":radio[name='sex'][value='" + customer.sex + "']").prop("checked", "checked");
					}
				});
				$("#bt").html("<button onclick='tosave();return false;'  style='width:98px'  class='btn btn-success'>保存</button>&nbsp;<button style='width:98px'  class='btn btn-success' onclick='tocancel();return false;'>取消</button>");
			}
			
			function tocancel(){
				$("#cname").html("${currentCustomer.cname}");
				$("#ename").html("${currentCustomer.ename}");
				$("#idcardno").html("${currentCustomer.idcardno}");
				$("#email").html("${currentCustomer.email}");
				$("#phone").html("${currentCustomer.phone}");
				$("#birth").html("${currentCustomer.birth}");
				$("#sex").html("${currentCustomer.sex}");
				$("#bt").html('<button onclick="tobianji();return false" style="width:98px"  class="btn btn-success">编辑</button>&nbsp;<button onclick="xiugai();return false" style="width:98px"  class="btn btn-success">修改密码</button>');
			}
			
			function checkExist(type,keyword){
				var id = "${currentCustomer.id }" ;
				$.ajax({
					url :'${path}/front/customer/checkExist.htm',
					data:{type:type,keyword:keyword,id:id},
					dataType:'json',
					type:'post',
					async:false,
					success:function(data){
						if(data.success){
							if(type==1){
								var birth = keyword.substring(6,10)+"-"+keyword.substring(10,12)+"-"+keyword.substring(12,14);
								$("#birth").html("<input name='birth' value='"+birth+"'  />");
							}
						}else {
							if(type==1){
								$("#idcardno").html("<input name='idcardno' value='${currentCustomer.idcardno}' onblur='checkExist(1,this.value)'  />");
								alert("身份证号重复");
							}
							if(type==2){
								$("#email").html("<input name='email' value='${currentCustomer.email}'  onblur='checkExist(2,this.value)' />");
								alert("邮箱重复");
							}
							if(type==3){
								$("#phone").html("<input name='phone' value='${currentCustomer.phone}'  onblur='checkExist(3,this.value)'  />");
								alert("电话重复");
							}
						}					
					}
				});
				
			}
			
			function tosave(){
				$.ajax({
					url:"${path}/front/customer/update.htm",
					data:$('#fm').serialize(),
					dataType:'json',
					type:'post',
					async:false,
					success:function(result){
						if (result.errorMsg) {
							alert(result.errorMsg);
						} else {
							alert("修改成功");
							var c = result.rows[0];
							$("#cname").html(c.cname);
							$("#ename").html(c.ename);
							$("#idcardno").html(c.idcardno);
							$("#email").html(c.email);
							$("#phone").html(c.phone);
							$("#birth").html(c.birth);
							$("#sex").html(c.sex);
							$("#bt").html('<button onclick="tobianji();return false" style="width:98px"  class="btn btn-success">编辑</button>&nbsp;<button onclick="xiugai();return false" style="width:98px"  class="btn btn-success">修改密码</button>');
						}
					}
				});
			}
		</script>
	
</body>
</html>


