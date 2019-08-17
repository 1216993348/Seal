//html:<div id="splitbarDiv"></div>
/*$.post("/actions/company/list",{cp:jsCommonCp,ps:jsCommonPs=8,col:jsCommonCol,kw:jsCommonKw},function(data){
					createSplitBar(data,"#splitbarDiv",app.query);
				});*/
var jsCommonCp = 1; //默认的当前所在页 
var jsCommonPs = 5; //默认的页面大小
var jsCommonCol = "";//定义模糊查询列
var jsCommonKw = "";//定义模糊查询值
var jsCommonPageCount ;//总页数 
function createSplitBar(data,selector,method){	//创建分页条
	//calPageCount(data.allRecorders);//进行计算总页数
	jsCommonPageCount = data.result.pages;
	clearBar(selector);//清空已有分页条
	//创建分页工具条
	if(jsCommonCp != 1){//如果不是第一页应该出现上一页按钮
		var liObj = $("<li></li>");
		var aObj = $("<a style='cursor:hand'>上一页</a>");
		aObj.on("click",function(){
			jsCommonCp--;//为a元素设置事件，一旦单击则更改当前页变量jsCommonCp
			method();//更新数据
		});
		liObj.append(aObj);
		$(selector+" .pagination").append(liObj);
	}
	//追加首页
	addBar(1,method,selector);
	if(jsCommonPageCount > 1){
		var seed = 3 //设置一个种子值，即当前页的前后分页条数，除了首页尾页最多为种子数
		if(jsCommonCp > seed + 3){//1、如果当前页码大于[种子数 + 1（首页） + 2（省略号）]，即大于seed + 3时生成省略号按钮
			$(selector+" .pagination").append("<li class='disabled'><a>...</a></li>");
		}
		var startPage = jsCommonCp - seed;//设起点
		var endPage = jsCommonCp + seed;//设終点
		if(  jsCommonCp <= seed + 3){//3、如果当前页码小于等于seed + 3，不需要省略号，起点要从2开始
			startPage = 2;
		}
		if(jsCommonPageCount - jsCommonCp < seed + 3){//4、如果当前页码距离尾页小于[种子数 + 1（尾页） + 2（省略号）]，即小于seed + 3时終点要到尾页数减1
			endPage = jsCommonPageCount - 1;
		}
		for(var x = startPage; x <= endPage ;x++ ){//迭代创建分页条
			addBar(x,method,selector);
		}
		if(jsCommonPageCount - jsCommonCp >= seed + 3){//2、如果当前页码距离尾页大于seed+3,需要用省略号代替
			$(selector+" .pagination").append("<li class='disabled'><a>...</a></li>");
		}
		//追加尾页
		addBar(jsCommonPageCount,method,selector);//（以上1、2、3、4为编程思考顺序）
		if(jsCommonCp != jsCommonPageCount){//如果不是最后一页应该出现下一页按钮
			var liObj = $("<li></li>");
			var aObj = $("<a style='cursor:hand'>下一页</a>");
			aObj.on("click",function(){
				jsCommonCp++;//为a元素设置事件，一旦单击则更改当前页变量jsCommonCp
				method();//更新数据 
			}); 
			liObj.append(aObj);
			$(selector+" .pagination").append(liObj);
		}
	}
}
function addBar(index,method,selector){
	var liObj = $("<li></li>");
	var aObj = $("<a >" + index +"</a>");
	if(jsCommonCp == index){//如果当前所在页，设置样式
		liObj.addClass("active");
	}else{
		aObj.css("cursor","hand")
		aObj.on("click",function(){
			jsCommonCp = index;//为a元素设置事件，一旦单击则更改当前页变量jsCommonCp
			method();//更新数据
		});
	}
	liObj.append(aObj);
	$(selector+" .pagination").append(liObj);
}
function calPageCount(allRecorders){
	if(allRecorders == 0){//如果总记录数为0，总页数应为1
		jsCommonPageCount = 1;
	}else{
		//计算结果为小数，转化为整数
		jsCommonPageCount = parseInt((allRecorders + jsCommonPs - 1) / jsCommonPs);//使11/10与20/10一致
	}
}
function clearBar(selector){//清空分页，每次重新生成
	$(selector).empty(); //清空子元素
	$(selector).append("<ul class='pagination'></ul>");//追加一个ul元素
}

/*********************************************************************************/
//html:<div id="searchDiv"></div>
//js:initCol(data);
function initCol(data,colSelector){
	if(data.message != undefined){
		var result = data.message.split("|");
		for(var x = 0 ; x < result.length ; x++){
			var temp = result[x].split(":");
			var optionObj = $("<option>" + temp[0] + "</option>");
			if(jsCommonCol == temp[1]){
				optionObj.attr("selected","selected");
			}
			optionObj.attr("value",temp[1]);
			$(colSelector).append(optionObj);
		}
	}
}
function pageCount(allRecorders){
	if(allRecorders == 0){//如果总记录数为0，总页数应为1
		return 1;
	}else{
		//计算结果为小数，转化为整数
		return parseInt((allRecorders + jsCommonPs - 1) / jsCommonPs);
	}
}