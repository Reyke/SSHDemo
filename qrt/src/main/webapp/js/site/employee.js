function addrow() {
	var clonedtr = $("#content_inner tr:last").clone();
	clonedtr.find("input.delete").bind("click", deleterow);
	clonedtr.find("input.change").bind("click", changerow);
	clonedtr.insertBefore($("#content_head tr:first"));
	clonedtr.bind("mouseenter", function() {
		clonedtr.addClass("hover");
	});
	clonedtr.bind("mouseleave", function() {
		clonedtr.removeClass("hover");
	});
	return clonedtr;

}
function doDelete(number,tr) {
	$.post("service/deleteEmp", {
		"empno" : number
	}, function(data) {
		if (data) {
			popAlert("success", "employee "+tr.find("td:first").text()+" has delete success");
			tr.remove();
			
		} else {
			popAlert("failure", "fail to delete employee");
			
		}

	});
}
function deleterow() {
	doReset();
	var tr = $(this).closest("tr");

	function fnOK() {
		if (activedRow) {
			activedRow.removeClass("change");
			activedRow = null;
		}

		doDelete(tr.find("td").get(0).innerHTML, tr);

		
	}

	function fnCancel() {
	}

	popConfirm("tips", "Are you asure delete "+tr.find("td:first").text()+" employee?", fnOK, fnCancel);

}

function changerow() {
	doReset();
	opflag = "update";
	var tr = $(this).closest("tr");

	var tds = tr.find("td");
//----------------------
	
	
	$("#empno").val($(tds.get(0)).text());
	
	$("#empnoClone").remove();
	$("#empno").show();
	var empnoClone = $("#empno").clone();
	empnoClone.insertBefore("#empno");
	empnoClone.attr("disabled", true);
	empnoClone.attr("id", "empnoClone");
	$("#empno").hide();
//----------------------
	$("#empname").val($(tds.get(1)).text());
	$("#empemail").val($(tds.get(2)).text());
	$("#emprole").val($(tds.get(3)).attr("val"));
	$("#register").attr("disabled", true);
	$("#save").attr("disabled", false);
	$("#empno").attr("readonly", true);
	if (activedRow) {
		activedRow.removeClass("changeColor");
	}
	$("#content_inner tr").removeClass("changeColor");
	tr.addClass("changeColor", 100);
	activedRow = tr;

}
function doRegister() {

	if (valid("register")) {
		var queryString = $("#target").serialize();
		$.post("service/register",queryString ,
				function(employee) {
					if (!employee) {
						popAlert("tips", "user register failure");
						return;
					}
					var tr = addrow();
					var tds = tr.find("td");
					$(tds.get(0)).text(employee.number);
					$(tds.get(1)).text(employee.name);
					$(tds.get(2)).text(employee.email);
					$(tds.get(3)).text(employee.role.name);
					$(tds.get(3)).attr("val",employee.role.id);
					var employees = g_employee.employees;
					employees.push(employee);
					tr.addClass("changeColor");
					tr.show();
					tr.removeClass("changeColor",3000);
					doReset();

				});
	}
}
function doUpdate() {

	if (valid("update")) {
		var queryString = passChanged?$("#target").serialize():$("#target").serialize().replace("&password=1234","");
		$.post("service/updateEmp", queryString,
				function(employee) {
					if (!employee) {
						popAlert("tips", "user update failure");
						return;
					}
					var tds = activedRow.find("td");
					$(tds.get(0)).text(employee.number);
					$(tds.get(1)).text(employee.name);
					$(tds.get(2)).text(employee.email);
					$(tds.get(3)).attr("val",employee.role.id);
					$(tds.get(3)).text(employee.role.name);
					if (activedRow) {
						activedRow.removeClass("changeColor", 3000);
					}
					doReset();

				});
	}
}

function doReset() {
	passChanged = false;
	opflag = "save";
	$("#empnoClone").remove();
	$("#empno").show();
	$("#empno,#empname,#empemail").val("");
	$("#password").val("1234");
	$("#emprole").val("");
	$("#empno").attr("readonly", false);

	$("#register").attr("disabled", false);
	$("#save").attr("disabled", true);
	$("#target table tr>td>div span").text("");
	if (activedRow) {
		activedRow.removeClass("changeColor");
		activedRow = null;
	}

}

function valid(type) {

	var result = true;

	if (type == "update") {

		result = $("#target").valid();
	} else if (type == "register") {

		result = $("#target").valid();
		if (!validEmpNo()) {
			result = false;
		}
		if (!validPass()) {
			result = false;
		}

	}
	return result;
}

function debugOnly() {
	
}

function doValidate() {

	$("#empno")
			.bind(
					"keydown",
					function(e) {
						var key = (e.keyCode) || (e.which) || (e.charCode);// 兼容IE(e.keyCode)和Firefox(e.which)

						if (!validateDigitsOnly(key)) {

							$("#empno").parent("td").find("span").html(
									"Your username must consist of 8 digits");
							stopDefault(e);
						} else if ($(this).val().length >= 8) {
							if (!(key == 8 || key == 9 || key == 13
									|| key == 27 || key == 46)) {
								$("#empno")
										.parent("td")
										.find("span")
										.html(
												"Your username must consist of 8 digits");
								stopDefault(e);
							}

						} else if ($(this).val().length == 7) {
							
							$("#empno").parent("td").find("span").html("");

						} else {

							$("#empno").parent("td").find("span").html("");

						}

					});
	$("#password")
			.bind(
					"keydown",
					function(e) {
						var key = (e.keyCode) || (e.which) || (e.charCode);// 兼容IE(e.keyCode)和Firefox(e.which)

						if (!validateDigitsOnly(key)) {

							$("#password").parent("td").find("span").html(
									"Your password must consist of 4 digits");
							stopDefault(e);
						} else if ($(this).val().length >= 4) {
							if (!(key == 8 || key == 9 || key == 13
									|| key == 27 || key == 46)) {
								$("#password")
										.parent("td")
										.find("span")
										.html(
												"Your password must consist of 4 digits");
								stopDefault(e);
							}

						} else {
							passChanged = true;
							$("#password").parent("td").find("span").html("");

						}

					});

}
function validEmpNo() {

	var empno = $("#empno").val();
	if (empno.length != 8) {
		$("#empno").parent("td").find("span").html(
				"Your username must consist of 8 digits");
		return false;
	}
	var employees = g_employee.employees;
	for ( var i in employees) {
		if (employees[i].number == empno) {
			$("#empno").parent("td").find("span").html(
					"Your input emp number has used");
			return false;
		}
	}
	return true;

}
function validPass() {

	var emppass = $("#password").val();
	if (emppass.length != 4) {
		$("#password").parent("td").find("span").html(
				"Your password must consist of 4 digits");
		return false;
	}
	return true;

}

function stopDefault(e) {
	// 阻止默认浏览器动作(W3C)
	if (e && e.preventDefault)
		e.preventDefault();
	// IE中阻止函数器默认动作的方式
	else
		window.event.returnValue = false;
	return false;
}
function validateDigitsOnly(key) {
	if (
	// Backspace, Tab, Enter, Esc, Delete
	key == 8 || key == 9 || key == 13 || key == 27 || key == 46 ||
	// Ctrl + A
	(key == 65 && event.ctrlKey === true) ||
	// Home, End, 四个方向键
	key >= 35 && key <= 40) {
		return true;
	}

	if (
	// 数字
	key >= 48 && key <= 57 ||
	// 数字键盘输入的数字
	key >= 96 && key <= 105) {

		return true;
	}
}
