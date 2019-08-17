/*! dsh_demo-1.0.0 | (c) demo Foundation | web.org/demo */



/***************************加载框插件  开始***********************************/
/**
* 插件一：加载框
* 加载框模板调用接口
* 调用方式: loading(xxxx)
**/
function loading(content,time){
	var data={content:content,time:time};

	/*
		1.  无时间    不关闭
		2. 有时间，但是已创建   不创建新的  只需更新消息 
		3. 有时间 且没有创建   直接创建 
		4. 关闭
	
	*/
	if(data.time == undefined){
		$.loading(data);			
	}else{
		if(JSON.stringify($("#_loading"))=='{}'){
			$.loading(data);
		}else{
			$("#_loading span").html(data.content);
		}
		closanimate($("#_loading"),data.time);
	}
}

$.loading=function(data){
		//初始化模板
		var loading=$("<div id='_loading'>"+
					"<img src='img/load.gif' alt='加载中预览图' width='17' height='17' class='_img'/> <span>"+
					data.content+"<span></div>");
		
		$("body").append(loading); //添加模板
		$.locationDiv(loading); //定位
		$.autoCenter(loading); //开启自动居中
	}
/***************************加载框插件  结束***********************************/



/***************************对话框插件 开始***********************************/
/*
*	插件二：对话框
*	对话框调用接口
*	调用方式：dialogs(xxxx);
*/
function dialogs(content,time){
	var json={content:content,time:time};
	
	$._dialog=function(data){
		//初始化模板
		var dialog=$("<div id='common-dialog' class='xxx-dialog'>"+
					 "<div class='head'><span class='btn-close'> × </span></div>"+
					 "<div class='content'>"+data.content+"</div>"+
						 "<div class='btn-nav'>"+
							"<div class='btn-oper' id='btn-yes'>确定</div>"+
							"<div class='btn-oper btn-close' id='btn-no'>取消</div>"+
						 "</div>"+
					 "</div>");

		$("body").append(dialog);	//添加模板
		$.locationDiv(dialog);	//定位
		$.autoCenter(dialog);	//开启自动居中

		initEvent();	//为当前对话框初始化事件
	}
	$._dialog(json);
}
/**
****插件的预设事件处理****
*
* 插件加载完成后
* 对动态添加的插件事件处理
*
*/
function initEvent(){
	$(".btn-close").click(function(){
		$("#common-dialog").slideUp(800);	//关闭动画
		$("#common-dialog").remove();	//移除当前对话框
	});
}
/***************************对话框插件  结束***********************************/



/***************************文件上传插件  开始***********************************/
/**
*
*插件三
*  文件上传插件
* 调用方式：$.fileDialog();
**/
$.fileDialog=function(){

	//判断是否已创建插件
	var isrun=JSON.stringify($("#file-dialog")) != "{}";
	if(isrun) {	return; }
	//创建模板
	var _filedialog=$("<div id='file-dialog' class='xxx-dialog'>"+
					"<div class='f-head'>"+
					"<h4><img src='img/load.gif' width='15' height='15' class='f-ico'/> 选择文件</h4>"+
					"<h3 class='f-exit'> × </h3>"+
					"</div>"+
					"<div class='addimg' id='addimg' contenteditable='true'>"+
					"<p>请将图片拖到这里</p>"+
					"<table>"+
					"<tr><td>文件名</td><td>大小(KB)</td><td>日 期</td></tr>"+
					"</table>"+
					"</div>"+
					"<div class='right-nav'>"+
					"<div class='_image'> 图片浏览 </div>"+
					"<div class='xxx_btn'>上传</div>"+
					"<div class='xxx_btn f-exit'>取消</div>"+
					"</div></div>");

	//添加模板
	$("body").append(_filedialog);
	//居中
	$.locationDiv(_filedialog);
	//自动居中
	$.autoCenter(_filedialog);
	//添加拖拽事件
	initdrop();
	//添加事件处理
	initmoveEvent();
}

//初始化拖拽事件
function initdrop(){
	var addimg=document.getElementById('addimg');
	addimg.ondrop=function(ev){ //拖拽事件
		ev.preventDefault(); //阻止浏览器对文件浏览
		var files = ev.dataTransfer.files; // 获取文件

		$(".addimg p").css('display','none');
		$(".addimg table").fadeIn(300);
		var size=Math.round(files[0].size * 100 / 1024) / 100;
		$(".addimg>table").append("<tr><td> "+files[0].name+" </td><td> "+
			size +"KB </td><td>2018-01-23<td></tr>");
	}
}

//初始化窗口移动事件
function initmoveEvent(){
	var ox = $("#file-dialog").offset().left; //获取当前窗口的left值
	var oy = $("#file-dialog").offset().top; //获取当前窗口的top值
	var sx = 0,sy = 0;  //点击点距窗口起点坐标的距离

	$("#file-dialog").mousedown(function(e){
		sx=e.clientX-ox;
		sy=e.clientY-oy;

		$(this).bind('mousemove',function(e){
			var newX,newY;
			newX=e.clientX-sx;
			newY=e.clientY-sy;
			$(this).css({left: newX+'px', top: newY+'px'});
		});

		$(this).mouseup(function(e){
			$(this).unbind('mousemove');
			ox=e.clientX-sx;
			oy=e.clientY-sy;
			$(this).css({left: ox+'px', top: oy+'px'});
		});
	});

	$(".f-exit").click(function(e){
		$("#file-dialog").fadeOut(500);
		setTimeout(function(){
			$("#file-dialog").remove();
		},500);
		e.stopPropagation();
	});
}





var UPLOAD_FILE_DATA={};
	$.upload=function(param){
			alert(1111);
			var domdem = $("<div id='upload-file'><div class='file-top'><div class='push-imgs f-t-left'>"+
				"<div class='s-bg-img'></div><div class='btn' id='btn-select'>点击选择文件</div></div>"+
				"<div class='push-imgs f-t-right'>或者将文件拖到此处</div></div><div class='file-center'>"+
				"<span>选中 3 个文件  共 30.12M</span><span class='btn btn-primaly' id='_exit'>关 闭</span>"+
				"<span id='_btn-finsh'  class='btn btn-primaly' > 开始上传 </span></div>"+
				"<div class='file-bottom'><ul></ul></div>"+
				"<form id='fildFrom' style='display: none;'><input type='file' name='file'/></form></div>");
			
			$("body").append(domdem);
			$.locationDiv(domdem);
			$.autoCenter(domdem);                 
			
			$("#btn-select").click(function(){
				$("#fildFrom input[type='file']").click();		
			});
			
			//上传项目
			$("#_btn-finsh").click(function(){
				var form = new FormData($('#fildFrom')[0]);
				$.ajax({
					type: param.type,
					url: param.url,
					data: form,
					beforeSend:function(){
						loading("文件上传中...");
					},
					timeout: 3000,
					processData: false,
					contentType:false,
					success: function(data){
						var domli;
						var json = eval("("+data+")");
						PATH=json;
						if(json.type =='img'){
							UPLOAD_FILE_DATA['img'] = json.name;
							domli=$("<li><img src='img/"+json.name+"' width='80' height='80'></li>");
						}else{
							UPLOAD_FILE_DATA['file'] = json.name;
							domli = $("<li><span>"+json.name+"</span></li>");
						}
						$(".file-bottom ul").append(domli);
						
						console.log("文件上传插件结果解析：【 "+ JSON.stringify(UPLOAD_FILE_DATA) +" 】");
						
						loading("上传成功！",3000);
					},
					error: function(){
						loading("上传失败！",2000);
					}
				});
			});
			
			//关闭控件
			$("#_exit").click(function(){
				$("#upload-file").slideUp(500);
				setTimeout(function(){
					$("#upload-file").remove();
				},600);
			});
		}


/***************************文件上传插件  结束***********************************/


/***************************工具方法设计***********************************/
/*
*
*插件自动居中计算算法
*调用方式：$.locationDiv(xxxx);
*/
$.locationDiv=function locationDiv(obj){
	var width = obj.width(); //插件宽度
	var height = obj.height(); //插件高度
	var ww = $(window).width(); //浏览器宽度
	var hh = $(window).height(); //浏览器高度

	//计算定位坐标
	var top = (hh - height)/2;
	var left = (ww - width)/2;

	//设置定位
	obj.css({'top': top+'px','left': left+'px'});
}



/**
*  通用型工具
*	1.外部文件引入自定义库文件
*	2.可直接通过$.xxxx(prams)进行调用定义的工具方法进行数据处理
*
**/


/*
*
* 工具1：
* 自适应窗口
* 调用方式：$.autoCenter(xxxx);
*/
$.autoCenter=function autoCenter(obj){
	$(window).resize(function(){
		$.locationDiv(obj);
	});
}


/**
*
* 工具2：
* 动画定时消失
* 调用方式: closanimate(xxx,xxx)
*/
function closanimate(obj,time){
	setTimeout(function(){
		//产生随机数
		var num=parseInt(Math.random()*10)%4+1;
		//分配动画效果
		switch(num){
			case 1:
			//obj.css('transform-origin','0 0');
			obj.css('transform','rotateZ(20deg)');
			obj.fadeOut(500);
			break;
			case 2:
			obj.css('animation','animateone');
			obj.hide(500);
			break;
			case 3:
			obj.slideUp(500);
			break;
			case 4:
			obj.fadeOut(500);
			break;
		};
		setTimeout(function(){
			obj.remove();	//删除本次创建的加载框
		},500);
	},time);
}

