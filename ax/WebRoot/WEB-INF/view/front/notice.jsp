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
        	
        	li {
        		list-style-type:none;
        	    height:40px;
        	    line-height: 40px;
        	    border-top:1px solid gray
        	}
        	
        	.t {
        		float:left;align:center;text-align:center;
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
		 function togrzx(){
			 window.location.href="${path}/front/customer/myinfo.htm";
		 }
		 
		 function openNotice(id){
			 window.open("${path}/notice/findOne.htm?id="+id);
		 }
		
		 </script>
        
    </head>

    <body style="width:70%;margin-left:15%">
	
		<div style="height:50px;line-height: 50px;">
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="tohome()">首页</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="tohbcx()">航班查询</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:red;font-size:22px;" >通知公告</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="togrzx()">个人中心</div>
		</div>
		
		
		<div style="width:880px;">
			
			
			<ul id="noticeinfo">
				<c:forEach items="${list }" var="notice">
				<li onclick="openNotice('${notice.id}')">
       				<div style="width:60%;float:left;align:left;text-align:left;">${notice.title }</div>
       				<div style="width:20%;float:right;align:right;text-align:right;">${notice.time }</div>
       			</li>
       			
       			</c:forEach>
       		</ul>
			
		
		</div>
	

	
		
</body>
</html>


