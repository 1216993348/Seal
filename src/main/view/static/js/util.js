/**
 * 批量操作数据执行操作
 * @param title 要进行的提示信息
 * @param elename 要操作的元素信息
 * @param url 要执行的url操作
 */
function operateChecked(title,elename,url) {
    var ids = "" ;
    $("input[id='"+elename+"']:checked").each(function() {
        ids += $(this).val() + "|" ;
    }) ;
    if (ids == "") {
        $("#alertDiv").attr("class","alert alert-danger") ;
        $("#alertText").text("您还未选择任何数据，请确认您的操作！") ;
        $("#alertDiv").fadeIn(1000,function(){
            $("#alertDiv").fadeOut(3000) ;
        }) ;
    } else {
        if (window.confirm("您确定要继续执行此操作吗？")) {
            url += "&ids=" + ids ;
            window.location = url ;
        }
    }
}
/**
 * 警告框操作信息
 * @param flag 操作成功或失败的标记
 * @param suctext 操作成功时的显示文本内容
 * @param faltext 操作失败时的显示文本内容
 */
function operateAlert(elmentId,flag,suctext,faltext) {
	$(elmentId).css("position","relative");
	var obj = $("<div></div>");
	obj.css("position","absolute");
	obj.css("width","80%");
    if (flag) {
    	obj.attr("class","alert alert-success") ;
    	obj.text(suctext) ;
    } else {
    	obj.attr("class","alert alert-danger") ;
    	obj.text(faltext) ;
    }
    //追加元素之前使其他子元素向上偏移
    $(elmentId).children().each(function(){
		var thisTop = parseInt($(this).css("top"));
		$(this).css("top",thisTop-50);
	});
    $(elmentId).append(obj);
    obj.fadeIn(300,function(){
    	obj.fadeOut(5000,function(){
    		obj.remove();
    	}) ;
    }) ;
}


/**
 * 实现四舍五入的处理操作
 * @param num 要进行操作的数字
 * @param sca 保留的小数位
 */
function round(num,sca) {
    return Math.round(num * Math.pow(10,sca))/Math.pow(10,sca) ;
}
/**
 * 为【全选】、【普通】复选框绑定事件
 * @param checkAllId 表示的是触发全选的复选框id
 * @param checkboxId 表示的是被触发的复选框选择器id
 */
function checkboxAll(checkAllId,checkboxId){
    $("#" + checkAllId).on("click",function () {//将【全选】复选框绑定单击事件
        $("input[id='"+ checkboxId +"']").each(function () {//只要点击【全选】复选框就更新其他【普通】复选框的状态
            this.checked =  $("#" + checkAllId).prop("checked");
        });
    });
    $("input[id='"+ checkboxId +"']").on("click",function(){checkboxEvent(this.checked,checkAllId,checkboxId); });//将【普通】复选框绑定单击事件
    stopBubble("input[type='checkbox']","click");// util.js.stopBubble()是防止冒泡函数
}

/**
 * 根据当前【普通】复选框状态，更新【全选】复选框状态
 * @param flag  当前【普通】复选框状态
 * @param checkAllId 表示的是触发全选的复选框选择器
 * @param checkboxId 表示的是被触发的复选框选择器
 */
function checkboxEvent(flag,checkAllId,checkboxId) {
    if( $("#" + checkAllId).prop("checked") && !flag){// 如果【全选】复选框为选中状态，并且单击后【普通】复选框为未选中状态，则更新【全选】复选框状态
        $("#" + checkAllId).prop("checked",false);
    }
    if(!$("#" + checkAllId).prop("checked")&& flag){// 如果【全选】复选框为未选中状态，并且单击后所有的【普通】复选框都为选中状态，则适当更新【全选】复选框状态
        var x = 0;
        $("input[id='"+ checkboxId +"']").each(function () {//计算被选中复选框的数量
            if(this.checked){
                x++;
            }
        });

        if($("input[id='"+ checkboxId +"']").length == x){//如果被选中复选框的数量与全部【普通】复选框数量相同，则更新【全选】复选框状态
            $("#" + checkAllId).prop("checked",true);
        }
    }
}
//ele: 表示删除按钮的选择器
//url: 表示进行ajax异步处理的路径
//cele: 表示所有复选框组件的选择器
function setDeleteButton(ele,url,cele) {//参数名称固定为ids
    $(ele).on("click",function () {//将【全选】复选框绑定单击事件
        var ids = "";
        $(cele).each(function () {
            if(this.checked){
                ids += this.value+"_";//以下划线为分隔符号
            }
        });
        if(ids == ""){//如果还未选中任何复选框则提示信息
            alert("您还为选中任何数据,无法操作");
        }else{
            if(window.confirm("确定要删除吗？")){
                $.ajax({
                    url:url,		//ajax提交路径,相当于open
                    data:{					//提交数据
                        "ids":ids
                    },
                    type : "post"	,		//表单提交方式
                    dataType:"text",		//服务器返回的数据类型
                    success : function(obj){//操作成功后返回的数据,类型由之前设置的type决定的
                        if(obj.trim() == "true"){
                            // alert("删除成功");
                            $("#alertDiv").attr("class","alert alert-success");
                            $("#alertText").text("新闻数据删除成功！");
                            loadData();//加载数据
                        }else{
                            //alert("删除失败");
                            $("#alertDiv").attr("class","alert alert-danger");
                            $("#alertText").text("新闻数据删除失败！");
                        }
                        $("#alertDiv").fadeIn(500,function () {
                            $("#alertDiv").fadeOut(3000);
                        });
                    },
                    error :function(){		//异常处理
                        alert("操作异常！请稍后再试");
                    }
                });
            }
        }
    });
}
function updateHandel(title,url,cele) {//参数名称固定为ids
    var ids = [];
    $(cele).each(function () {
        if(this.checked){
            ids.push(this.value);
        }
    });

    if(ids.length == 0){//如果还未选中任何复选框则提示信息
        alert("您还为选中任何数据,无法操作");
    }else{
        if(window.confirm(title)){return ids;
            /* $.ajax({
                 url:url,        //ajax提交路径,相当于open
                 data:{                  //提交数据
                     "ids":ids
                 },
                 type : "post"   ,       //表单提交方式
                 dataType:"text",        //服务器返回的数据类型
                 success : function(obj){//操作成功后返回的数据,类型由之前设置的type决定的
                     if(obj.trim() == "true"){
                        // alert("删除成功");
                         $("#alertDiv").attr("class","alert alert-success");
                         $("#alertText").text("新闻数据删除成功！");
                         loadData();//加载数据
                     }else{
                         //alert("删除失败");
                         $("#alertDiv").attr("class","alert alert-danger");
                         $("#alertText").text("新闻数据删除失败！");
                     }
                     $("#alertDiv").fadeIn(500,function () {
                         $("#alertDiv").fadeOut(3000);
                     });
                     return ids;
                 },
                 error :function(){      //异常处理
                     alert("操作异常！请稍后再试");
                 }
             });*/
        }
    }

}
/**
 * 设置组件事件触发类型为非冒泡
 * @param selector 组件选择器
 * @param type  事件类型
 * @param fun   函数引用
 */
function stopBubble(selector,type,fun) {
    if(arguments.length ==3){
        $(selector).on(type,function (e) {
            fun();
            e.stopPropagation();
        });
    }else{
        $(selector).on(type,function (e) {
            e.stopPropagation();
        });
    }
}

/**
 * 为表格行绑定事件
 * @param checkAllId 表示的是触发全选的复选框选择器
 * @param checkboxId 表示的是被触发的复选框选择器
 * @param checkboxValue 【普通】复选框按钮的value
 * @param trObj  <tr>元素的jQuery对象
 */
function setTrEvent(checkAllId,checkboxId,checkboxValue,trObj){
    trObj.on("click",function () {
        var flag = $("input[value='"+checkboxValue+"'][type='checkbox']").prop("checked");
        $("[value='"+checkboxValue+"']").prop("checked",!flag);
        /* 引用普通复选框单击事件函数*/
        checkboxEvent(!flag,checkAllId,checkboxId);
    });
}
var cloneObj = function(obj){
    var str, newobj = obj.constructor === Array ? [] : {};
    if(typeof obj !== 'object'){
        return;
    } else if(window.JSON){
        str = JSON.stringify(obj), //序列化对象
            newobj = JSON.parse(str); //还原
    } else {
        for(var i in obj){
            newobj[i] = typeof obj[i] === 'object' ? cloneObj(obj[i]) : obj[i];
        }
    }
    return newobj;
};

function validateParam(formSelector,rule,method){
    $(formSelector).validate({  //针对此表单验证
        debug :true,    //采用调试模式，表单不会自动提交,会执行submitHandler操作
        submitHandler:function (form) {//当覆写此函数时表单不会提交，只会执行函数体
            /*//可以手动提价，也可以在此使用ajax异步提交
            form.submit();
            //form.submit();*/
            method();
        },
        errorPlacement:function(error,element){//覆写errorPlacement函数，默认错误信息直接输出在元素后
            var objName = element.attr("name") + "Msg";
            if(objName.indexOf(".") != -1){ //注意如果id值带有“.”那么jQuery会当做样式选择器，所以需要转义。
                objName = objName.replace(".","\\."); //转义名称
            }
            $("#" + objName).append(error);
        },
        highlight:function(element,errorClass){//错误强调显示,element是JS表单对象
            if($(element).attr("name") != undefined){
                $("#"+$(element).attr("name").replace(".","\\.")+"Div").attr("class","form-group has-error");
            }
        },
        unhighlight:function(element,errorClass){//正确操作，element是JS表单对象
            console.log();
            if($(element).attr("name") != undefined){
                $("#"+$(element).attr("name").replace(".","\\.")+"Div").attr("class","form-group has-success");
            }
        },
        errorClass :"text-danger",
        rules:rule
    });
}



Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

function compile(code)  
{    
   var c=String.fromCharCode(code.charCodeAt(0)+code.length);  
   for(var i=1;i<code.length;i++){  
       c+=String.fromCharCode(code.charCodeAt(i)+code.charCodeAt(i-1));  
   }  
   return escape(c);  
}  
// 解密函数
function uncompile(code)  
{  
   code=unescape(code);  
   var c=String.fromCharCode(code.charCodeAt(0)-code.length);  
   for(var i=1;i<code.length;i++){  
       c+=String.fromCharCode(code.charCodeAt(i)-c.charCodeAt(i-1));  
   }  
   return c;  
} 

