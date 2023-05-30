<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>安翔航空</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- CSS -->
        <link href="${path }/homepage/css/bootstrap-combined.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${path }/homepage/css/juicyslider.css" type="text/css" />
        <script src="http://api.map.baidu.com/api?v=2.0&ak=6ztAHOKYwA3lMAEPe4TT5YmB" type="text/javascript"></script>
        <style type="text/css">

            /* Sticky footer styles
            -------------------------------------------------- */
			li {
        		list-style-type:none;
        	    height:20px;
        	    line-height: 20px;
        	}
            html,
            body {
                height: 90%;
                
                /* The html and body elements cannot have any padding or margin. */
            }

            /* Wrapper for page content to push down footer */
            #wrap {
                min-height: 100%;
                height: auto !important;
                height: 100%;
                /* Negative indent footer by it's height */
                margin: 0 auto -60px;
            }

            -------------------------------------------------- */
            /* Not required for template or sticky footer method. */

            #wrap > .container {
                padding-top: 20px;
            }
            .container .credit {
                margin: 20px 0;
            }

            code {
                font-size: 80%;
            }

            /* Some adjustments for Bootstrap */

            .lead {
                font-size: 18px;
            }

            .btn {
                margin: 5px 2px;
            }

        </style>
    </head>

	<script type="text/javascript">
		function tohbcx(){
			 window.location.href="${path}/front/hb/index.htm";
		}
		
		function togrzx(){
			var customerid="<%=session.getAttribute("customerid")%>"; 
			if(customerid==null || customerid.length < 20){
				window.location.href="${path}/front/customer/toLogin.htm";
			} else {
				window.location.href="${path}/front/customer/myinfo.htm";
			}
		}
		
		function totzgg(){
			window.location.href="${path}/front/noticead/tonotice.htm";
		}
	</script>

    <body>
	
		<div style="height:50px;width:70%;margin-left:15%;line-height: 50px;">
			<div style="float:left;width:24%;text-align:center;color:white;background:red;font-size:22px;">首页</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="tohbcx();" >航班查询</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;"  onclick="totzgg();" >通知公告</div>
			<div style="float:left;width:24%;text-align:center;color:white;background:gray;font-size:22px;" onclick="togrzx();" >个人中心</div>
		</div>
	
        <div id="wrap">
            <!-- Begin page content -->
            <div class="container">
                <div class="row-fluid">
                    <div class="pagination-centered">
                        <div id="myslider0" class="juicyslider">
                            <ul id="adad">
                                <li><img src="${path }/homepage/data/0.jpg" alt=""></li>
                                <li><img src="${path }/homepage/data/2.jpg" alt=""></li>
                                <li><img src="${path }/homepage/data/4.jpg" alt=""></li>
                            </ul>
                            <div class="nav next"></div>
                            <div class="nav prev"></div>
                            <div class="mask"></div>
                        </div>
                    </div>
                </div>
                
                
            </div>
            <br><br>
            <div id="push"></div>
			
			<div style="position:relative;z-index:9999px;width:500px;margin-top:-500px;margin-left:250px ;background:white;height:400px;border:1px solid white;filter:alpha(Opacity=60);-moz-opacity:0.6;opacity: 0.6">
				<table style="border-collapse:separate; border-spacing:0px 20px;margin-left:20px;margin-top:30px">
					<tr>
						<td>出发地&nbsp;&nbsp;&nbsp;</td>
						<td><select id="orgin" ></select>
					</tr
					
					<tr>
						<td>目的地&nbsp;&nbsp;&nbsp;</td>
						<td><select id="destination" ></select>
					</tr>
					<tr>
						<td>日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input onClick="WdatePicker()"  id="date" /></td>
					</tr>
					
					<tr>
						<td colspan="2" style="align:center;text-align:center;"><button onclick="tohbsearch();" class="btn btn-success">查询</button>   </td>
					</tr>
					
				</table>
				
				<div style="margin-left"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;通知公告</span></div><br/>
				<div style="margin-left:20px;" id="noticeinfo">
					<a>公告1</a><br/><a>公告1</a><br/><a>公告1</a>
				</div>
			</div>
        </div>
		<script type="text/javascript">
			$(function(){
				$.ajax({
					url : '${path}/notice/list.htm',
					data:{page:1,rows:3},
					dataType:'json',
					type:'post',
					async:true,
					success:function(data){
						var ns = data.rows;
						var str = "<ul>";
						for(var i=0;i<ns.length;i++){
							var notice = ns[i];
							str += "<li><a javascript:void(0)  onclick='openNotice(\""+notice.id+"\")' >" + notice.title + "</a></li>";
						}
						str += "</ul>"
						$('#noticeinfo').html(str);
					}
				});
				setInterval('autoScroll("#noticeinfo")',3000)
			});
			function autoScroll(obj){
            	var n=$(obj).find("li").height();
            	$(obj).find("ul").animate({marginTop:-n},500,function(){
             		$(this).css({marginTop:"0px"}).find("li:first").appendTo(this);
        		})
			}
			function openNotice(id){
				window.open("${path}/notice/findOne.htm?id="+id);
			}
			
			 
            function tohbsearch(){
           	 	var orgin = $("#orgin").val();
           	 	var destination = $("#destination").val();
           	 	var date = $("#date").val();
           	 	window.location.href="${path}/front/hb/index.htm?orgin="+orgin+"&destination="+destination+"&date="+date;
            }
		</script>
        
        <script src="${path }/homepage/js/jquery.min.js"></script>
        <script src="${path }/homepage/js/jquery-ui.min.js"></script>
        <script src="${path }/homepage/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${path }/homepage/js/juicyslider.js"></script>
       <script type="text/javascript" src="${path }/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript">
        
        
        
        	function  openad(link){
        		window.open(link);
        	}
            
             $(function() {
            	 $.ajax({
            		 url : '${path}/front/noticead/loadad.htm',
            		 data : {page:1,rows:4},
            		 dataType:'json',
            		 type:'post',
            		 async:false,
            		 success:function(data){
						var str = "";
            			for(var i=0;i<data.length;i++){
							var ad = data[i];
							str += "<li onclick='openad(\""+ad.link+"\")' ><img src='${path}/"+ad.cover+"' ></li>";
						}
            			$("#adad").html(str);
            		 }
            	 });
            	
            	 
                $('#myslider0').juicyslider({
                    width: '100%',
                    height: 500,
                    mask : 'none',
                    autoplay: 4000,
                    shuffle: false,
                });
                
                $.ajax({
                	url:path + '/city/cityCombobox.htm',
                	dataType:'json',
                	type:'post',
                	async:false,
                	success:function(data){
                		var str = "<option value=''>地点</option>";
                		for(var i=0;i<data.length;i++){
                			var c = data[i];
                			str += "<option value='"+c.id+"'>"+c.name+"</option>";
                		}
                		$("#destination").html(str);
                		$("#orgin").html(str);
                		
                		$("#destination").val("7a60df46f10040fe91d6d02d2effcfc9"); // 这个ID时city表北京的ID
                		getLocation();
                		
                	}
                });
                
            });
             
             
             function getLocation(){  
            	 var geolocation = new BMap.Geolocation();
            		geolocation.getCurrentPosition(function(r){
            			if(this.getStatus() == BMAP_STATUS_SUCCESS){
            				var lng = r.point.lng;
            				var lat = r.point.lat;
            				$.ajax({
            					url:path + '/city/distince.htm',
                            	dataType:'json',
                            	type:'post',
                            	data:{lng:lng,lat:lat},
                            	async:false,
                            	success:function(data){
                            		if(data.success){
                            			$("#orgin").val(data.min);
                            		}
                            	}
            				});
            			}else {
            				alert('获取经纬度失败');
            			}        
            		},{enableHighAccuracy: true})
             } 
             
            
             
        </script>
       

</body>
</html>