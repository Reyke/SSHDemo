var currentEmployeeNumber, currentEmployeeName, currentEmployeeEmail, currentRoleId, currentRoleName;
var currentLanguage = "en_US";
var loginForm = "login.html";
var taskAssignmentForm = "tasks/taskAssignment.html";
var updateReportForm = "updateReport/updateReport.html";
var currentScreenURI = loginForm;
var AJAX_TIME_OUT = 5000;

var ADMIN_ROLE = "QA_ADMIN";
var TESTER_ROLE = "TESTER";

// if anyone access from other screen, jump to the index screen.
if ("undefined" == typeof(loginFromIndex)) {
	//location.href = '/qrt/';
}

$(function () {
	// register a unload function.
	$(window).unload(function () {
		try {
			destroy();
		} catch (e) {}
	});
	
	// loading i18n data
	loadingI18N();
	//language selector
	$("#languageBox").buttonset();
	//change language
	$("#lang_zh,#lang_en").click(function(){
		switch($(this).attr("id").split("_")[1]){
			case 'en' : {currentLanguage = "en_US";break;}
			case 'zh' : {currentLanguage = "zh_CN";break;}
		}
		loadingI18N();
		propertyBinding.binding($("body"), true);
		try{
			afterLanguageChange();
		}catch(e){}
		
	});
	// unable cache
	$.ajaxSetup({ cache: false, async : false});
	// setup loading screen
	$(document).ajaxStart(function() { 
		loading(true);
	});
	$(document).ajaxComplete(function() { 
		loading(false);
	});
	
	// loading login.html, it's the default screen.
	loadLoginPage();
	
});

/**
*load the login page with the PlaceholderSupporter plugin
*/
function loadLoginPage(){
	navigate(loginForm);
	var phs = new PlaceholderSupporter();
	phs.init();	
}

function loadingI18N() {
	 jQuery.i18n.properties({
	 name:'applicationResources', 
	 path:'i18n/', 
	 mode:'map', 
	 language:currentLanguage,
	 callback: function() {
	 } 
	 }); 
}

function showPublicMessage(message) {
	return $("#divMainMessageBox").html(message);
}

// we need to use this function to navigate between different screens.
function navigate(surl) {
	
	// destroy current screen first.
	try {
		destroy();
	} catch (e) {};
	
	$("#divMainMessageBox").html("");
	currentScreenURI = surl;
	$.get(surl, function(result) {
		var BODY_REGEXP = /\<body\>([\s\S]*)\<\/body\>/;
		var r = BODY_REGEXP.exec(result);
		// Hide body & footer till they are ready, thus
		// avoiding the intermediary placeholders for I18N fields.
		$("#divBody, #divFooter").css({visibility : "hidden"});
		if (r == null) {
			$("#divBody").html(result);
		} else {
			$("#divBody").html(r[1]);
		}
		$("#divBody").ready(function(){$("#divBody, #divFooter").css({visibility : "visible"});});
		try {
			debugOnly();
		} catch (e){}
		try{
			inited();
		} catch(e){}
	});
	propertyBinding.binding($("body"), true);
}

function ajax(setting) {
	setting.timeout = AJAX_TIME_OUT;
	
	$.ajax(setting);
}

function popUserInfo(empNo, empName, email, roleId, roleName) {
	currentEmployeeNumber = empNo;
	currentEmployeeName = empName;
	currentEmployeeEmail = email;
	currentRoleId = roleId;
	currentRoleName = roleName;
	
	initUserInfo();
	initMenuInfo("on");
}

function initMenuInfo(status) {
	if (status == "on") {
		// log on
		if (currentRoleName == ADMIN_ROLE) {
			$("#menuTaskAssignment").css("display", "");
			$("#menuUserManagement").css("display", "");
			$("#menuUpdateReport").css("display", "");
			$("#menuReportDefinition").css("display", "");
			$("#menuExportReport").css("display", "");
			$("#divMenu").css("display", "");
			
			// navigate the the default screen.
			navigate(taskAssignmentForm);
		} else {
			if (currentRoleName == TESTER_ROLE) {
				$("#menuTaskAssignment").css("display", "none");
				$("#menuUserManagement").css("display", "none");	
				$("#menuUpdateReport").css("display", "none");
				$("#menuReportDefinition").css("display", "none");
				$("#menuExportReport").css("display", "none");
				$("#divMenu").css("display", "none");
				// navigate the the default screen.
				navigate(updateReportForm);
			}
		}
	} else {
		// log off
		$("#menuUserManagement").css("display", "none");
		$("#menuTaskAssignment").css("display", "none");	
		$("#menuUpdateReport").css("display", "none");
		$("#menuReportDefinition").css("display", "none");
		$("#menuExportReport").css("display", "none");
		$("#divMenu").css("display", "none");
	}
}

function initUserInfo() {
	if (currentEmployeeName != null && currentEmployeeName != "") {
		$("#txtEmpNo,#txtRole,#anchorLogOff").css("display", "");
		$("#txtEmpNoValue").text(currentEmployeeName).css("color","red").attr("title", currentEmployeeName);
		$("#txtRoleValue").text(currentRoleName).css("color","red").attr("title", currentRoleName);
	}
}

function logOff() {
	// give a confirmation
	popConfirm("Logoff Confirmation", "Are you sure to logoff ?", 
		function() {
			// when OK clicked
			clearUp();
			navigate(loginForm);
		}, function() {
			// when cancel clicked, do nothing
		}
	);
	return false;
}

function clearUp() {
	currentEmployeeNumber = "";
	currentEmployeeName = "";
	currentEmployeeEmail = "";
	role = "";
	
	$("#txtEmpNo").text("");
	$("#txtEmpNo").css("display", "none");
	$("#txtRole").text("");
	$("#txtRole").css("display", "none");
	$("#txtEmpNoValue").text("").removeAttr("title");
	$("#txtRoleValue").text("").removeAttr("title");
	$("#anchorLogOff").css("display", "none");
	// if error do nothing, because the login form maybe destroyed.
	try {
		clearLoginForm();
	} catch (e) {
	}
	initMenuInfo("off");
	currentScreenURI = loginForm;
}

// the function to pop an alert dialog
function popAlert(title, msg) {
	$("#dialog-message").text(msg);
	$("#dialog-message").dialog({
		title: title,
		modal: true,
		position : ['center','center'],
		buttons: {
			Ok: function() {
				$(this).dialog( "close" );
			}
		}
	});
}

function loading(flag){
	if(flag){
		$("#dialog-loading").css("display","block");
	}else{
		$("#dialog-loading").css("display","none");
	}
}

// the function to pop a confirmation dialog.
function popConfirm(title, msg, fnOK, fnCancel) {
	$("#dialog-confirm").text(msg);
	$("#dialog-confirm").dialog({
		title : title,
	    resizable : false,
	    position : ['center','center'],
	    height :140,
	    modal : true,
	    buttons : {
	      "OK" : function() {
	    	var temp = fnOK != undefined ? fnOK() : null;
	  		$(this).dialog("close");
	      },
	      Cancel : function() {
	    	  var temp = fnCancel != undefined ? fnCancel() : null;
	    	  $(this).dialog("close");
	      }
	    }
	  });	
}

//the function to pop an html box dialog
function popBox(title, html,closeCall) {
	$("#dialog-message").html(html);
	return $("#dialog-message").dialog({
		title	: title,
		modal	: false,
		position : ['center','center'],
		zIndex 	: 999999,
		close 	: function(){
			var temp = closeCall != undefined ? closeCall() : null;
			$("#dialog-message").dialog('destroy');
		}
	});
}

//the function to pop an html box dialog
function popModalMessage(title, msg) {
	$("#dialog-message").text(msg);
	return $("#dialog-message").dialog({
		title	: title,
		modal	: true,
		position : ['center','center'],
		zIndex 	: 999999
	});
}

function mask (show) {
	if (show) {
		$("#coverDiv").css("display", "block");
	} else {
		$("#coverDiv").css("display", "none");
	}
}

