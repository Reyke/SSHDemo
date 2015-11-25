function clearLoginForm(){
	$("#name").val("");
	$("#password").val("");
}

function getTarget(event) {
	if (event.target) {
		return event.target;
	} else {
		return event.srcElement;
	}
}

function validNumber(len, e) {
	var event = window.event || e;
	var t = getTarget(event);
	
	if(event.keyCode == 13){
		dologin();
		return true;
	}
	
	if (t.value.length >= len && event.keyCode != 8 && event.keyCode != 9
			&& event.keyCode != 13 && !t.selected) {
		// length exceeded.
		if (t.id == "name") {
			$("#login_re").css("display", "block");
			return false ;			
		} else if (t.id =="password") {
			$("#login_rp").css("display", "block");
			return false ;			
		}
		t.selected = false;
	}
	
	if ((event.keyCode >= 48 && event.keyCode <= 57)
			|| (event.keyCode >= 96 && event.keyCode <= 105)
			|| event.keyCode == 8 ||event.keyCode == 9){
		$("#login_re").css("display", "none");
		$("#login_rp").css("display", "none");
		return true;
	}else{
		if (t.id == "name") {
			$("#login_re").css("display", "block");
			return false ;			
		} else if (t.id =="password") {
			$("#login_rp").css("display", "block");
			return false ;			
		}
	}
	
	
		
	return false;
}


function checkEmployee(){
	var s = $("#name").val();
	var regu = /^[0-9]{8}$/; 
	var re = new RegExp(regu); 	
	
	if(re.test(s)){
		$("#login_re").css("display", "none");
		return true ;
	}else{
		$("#login_re").css("display", "block");
		return false ;
	}
}
function checkPassword(){
	var s = $("#password").val();
	var regu = /^[0-9]{4}$/; 
	var re = new RegExp(regu); 
	
	if(re.test(s)){
		$("#login_rp").css("display", "none");
		return true;
	}else{
		$("#login_rp").css("display", "block");
		return false ;
	}
}

function debugOnly() {
	
}

function vailForm(){
	
	if(checkPassword()& checkEmployee()){
		$("#login_re").css("display", "none");
		$("#login_rp").css("display", "none");
		return true;
	}else{
		 popAlert("faild", "Please input correct employee No. and Password to login");

		return false ;
	}
}


function dologin(){
	if(vailForm()){
		ajax({
		   type: "GET",
		   url: "/qrt/service/login.json",
		   data: $('#loginForm').serialize(),
		   success: function(msg){
			 if(msg){
				 popUserInfo(msg.number, msg.name, msg.email, msg.role.id, msg.role.name);
			 }else{
				 popAlert("faild", "Please input correct employee No. and Password to login");
			 }
		   },
			error:function(msg){
				 popAlert("faild", "Please input correct employee No. and Password to login");
			}
		});
	}
}
