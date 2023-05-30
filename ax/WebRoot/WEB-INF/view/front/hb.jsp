<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>
<html>
<head>
		 <link href="${path }/homepage/css/bootstrap-combined.min.css" rel="stylesheet">
		 <link href="${path }/mui/css/mui.min.css" rel="stylesheet">
		 <style>
        	li {
        		list-style-type:none;
        	    height:80px;
        	    line-height: 80px;
        	    border-top:1px solid gray
        	}
        	.code {
        		width:20%;float:left;align:center;text-align:center;
        	}
        	.buy {
        		width:20%;
        		float:left;
        		align:center;
        		text-align:center;
        		position: relative;
        		top: 50%;
        		transform: translateY(-50%);
        	}
        </style>
        <title>安翔航空</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<script type="text/javascript">
			$(function(){
				$("#s_origincity").combobox({
					url : path + '/city/cityCombobox.htm',
					method : 'post',
					valueField : 'id',
					textField : 'name',
					editable : false,
					panelHeight : 'auto'
				});
				
				$("#s_destinationcity").combobox({
					url : path + '/city/cityCombobox.htm',
					method : 'post',
					valueField : 'id',
					textField : 'name',
					editable : false,
					panelHeight : 'auto'
				});
				
				var o = "${orgin}";
				var d = "${destination}";
				var date = "${date}";
				$("#s_origincity").combobox("setValue",o);
				$("#s_destinationcity").combobox("setValue",d);
				$("#s_date").datebox("setValue",date);
				
				searchFlight();
			});
			
			
			function searchFlight(){
				var o = $("#s_origincity").combobox("getValue");              
				var d = $("#s_destinationcity").combobox("getValue");
				var date = $("#s_date").datebox("getValue");
				$.ajax({
					url : '${path}/front/hb/searchHB.htm',
					data:{
						o:o,
						d:d,
						date:date
					},
					dataType:'json',
					type:'post',
					async:false,
					success:function(data){
						var hbs = data.rows;
						if(hbs==null || hbs.length==0){
							$("#hbinfo").html("暂无航班");
						} else {
							var str = "";
							for(var i=0;i<hbs.length;i++){
								var hb = hbs[i];
								str += "<li >";
			       				str += "<div class='code' >" + hb.oport+" " + hb.start + "</div>";
			       				str += "<div class='code' >"+hb.code+"（"+hb.planemodel+"）"+"</div>";
			       				str += "<div class='code' >" + hb.dport+" " + hb.end + "</div>";
			       				str += "<div class='code' >￥"+hb.price + "</div>";
			       				str += "<div class='buy'><button class='btn btn-danger'  onclick='yuding(\""+hb.flightid+"\")' >预定</button></div>"; 
			       				str += "</li>";
							}
							$("#hbinfo").html(str);
						}
					}
					
				});
			}
			
			
			function yuding(flightid){
				var customerid="<%=session.getAttribute("customerid")%>"; 
				if(customerid==null || customerid.length < 20){
					window.location.href="${path}/front/customer/toLogin.htm";
				} else {
					$.messager.confirm("系统提示", "您确认要购买该航班吗？", function(r) {
						if (r) {
							var date = $("#s_date").datebox("getValue");
							$.ajax({
								url : '${path}/schedular/buy.htm',
								data:{date:date,flightid:flightid},
								dataType:'json',
								type:'post',
								success:function(result){
									if (result.errorMsg) {
										alert(result.errorMsg);
									} else {
										alert("预定成功，请在30分钟内付款购买，否则会取消订单");
										window.location.href="${path}/front/order/toMyOrder.htm";
									}
								}
							});
						}
					});
				}
			}
			
			
			function togrzx(){
				var customerid="<%=session.getAttribute("customerid")%>"; 
				if(customerid==null || customerid.length < 20){
					window.location.href="${path}/front/customer/toLogin.htm";
				} else {
					window.location.href="${path}/front/customer/myinfo.htm";
				}
			}
			
			function tohome(){
				 window.location.href="${path}/tohome.htm";
			 }
			
			function totzgg(){
				window.location.href="${path}/front/noticead/tonotice.htm";
			}
		</script>
        
    </head>

    <body style="width:70%;margin-left:15%">
	
		<div style="height:50px;line-height: 50px;">
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="tohome();">首页</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:red;font-size:22px;">航班查询</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="totzgg();">通知公告</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="togrzx();">个人中心</div>
		</div>
	
       <div>
       		<div class="updownInterval"> </div><div class="updownInterval"> </div><div class="updownInterval"> </div>
       		<div class="updownInterval"> </div><div class="updownInterval"> </div><div class="updownInterval"> </div>
       		<div  style="height:50px;">
				&nbsp;日期：&nbsp;<input class="easyui-datebox" name="s_date" id="s_date" size="20" onkeydown="if(event.keyCode==13) searchFlight()"/>
				&nbsp;&nbsp;&nbsp;始发地：&nbsp;<input class="easyui-combobox" name="s_origincity" id="s_origincity" size="20" onkeydown="if(event.keyCode==13) searchFlight()"/>
				&nbsp;&nbsp;&nbsp;目的地：&nbsp;<input class="easyui-combobox" name="s_destinationcity" id="s_destinationcity" size="20" onkeydown="if(event.keyCode==13) searchFlight()"/>
				&nbsp;&nbsp;&nbsp;<a href="javascript:searchFlight()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
       		</div>
       		<ul id="hbinfo">
       			<li style="height:80px;line-height: 80px;border-top:1px solid gray">
       				<div style="width:20%;float:left;align:center;text-align:center;">北京国际机场&nbsp;08:00</div>
       				<div style="width:20%;float:left;align:center;text-align:center;">KZ205（波音747）</div>
       				<div style="width:20%;float:left;align:center;text-align:center;">上海虹桥机场&nbsp;14:00</div>
       				<div style="width:20%;float:left;align:center;text-align:center;">￥760</div>
       				<div style="width:20%;float:left;align:center;text-align:center;position: relative;top: 50%;transform: translateY(-50%);">
       					<button class="btn btn-danger">预定</button>
       				</div>
       			</li>
       		</ul>
       		
       </div>

</body>
</html>