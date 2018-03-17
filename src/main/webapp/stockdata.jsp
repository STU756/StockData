<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>title</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="jquery/jquery-1.4.2.js"></script>
<script type="text/javascript">
    alert($("input[name='stockNum']").toString());
	var stc = $("input[name='stockNum']")[0].value();

	alert(sto);
	//当离开焦点时验证
	$("input[name='stockNum']").onblur = function() {
		return checkStockNum();
	}

	function check(){
		return checkStockNum()&&checkStockStartTime();
	}
	function checkStockNum() {
		var stockNum = $("input[name='stockNum']")[0].value()


		var stockNumReg = /^[A-Z]/;
		if(stockNumReg.test(stockNum)) {
			removeError($("input[name='stockNum']"));
			return true;
		}else {
			addError($("input[name='stockNum']"), "期货号输入有问题，请检查!")
			return false;
		}
	}
	function checkStockStartTime(){
		
	}
	
	
	function addError(where, what){
		//0.判断是否存在错误消息,
		if (where.parentNode.getElementsByTagName("font").length > 0) {
			//如果存在，直接return
			return;
		}
		//1.创建一个font元素对象
		var font = document.createElement("font");
		//2.添加属性color="red"
		font.setAttribute("color", "red");
		//3.添加内容innerHTML
		font.innerHTML = what;
		//4.添加到input后面
		where.parentNode.append(font);
	}
	
	//移除错误消息
	function removeError(where) {
		//1.找到错误消息
		var font = where.parentNode.getElementsByName("font")[0];
		//2.移除
		if (font) {
			font.parentNode.removeChild(font);
		}
	}
</script>
</head>
<body>
	<table align="center">
		<tr align="center">
			<td>例子:</td>
			<td>M1805</td>
			<td>13:00</td>
			<td>16:00</td>
			<td>30</td>
			<td>c:/stock/</td>
		</tr>
		<tr align="center">
			<td>解释:</td>
			<td>期货号</td>
			<td>开盘时间</td>
			<td>收盘时间</td>
			<td>获取期货数据间隔时间（单位：分钟）</td>
			<td>期货数据存放文件夹路径</td>
		</tr>
	</table>
	==============================================================================================================================

	<form name="form1" action="${pageContext.request.contextPath}/StockDataServlet" method="post" onsubmit="check()">
		<table>
			<tr>
				<th>期货号</th>
				<td><input type="text" name="stockNum"></td>
			</tr>
			<tr>
				<th>开盘时间</th>
				<td><input type="text"></td>
			</tr>
			<tr>
				<th>收盘时间</th>
				<td><input type="text"></td>
			</tr>
			<tr>
				<th>获取数据间隔时间（分钟）</th>
				<td><input type="text"></td>
			</tr>
			<tr>
				<th>期货数据存放文件夹路径</th>
				<td><input type="text"></td>
			</tr>
		</table>
		<br /> <input type="submit" value="提交任务">
	</form>
	==============================================================================================================================
	<br/>
	<b>执行提示：</b>
			<div>
				<textarea rows="30" cols="100" name="message"></textarea>
			</div>
</body>
</html>