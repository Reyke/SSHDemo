/**
 * 
 */
$(function(){
	$("#datepicker").datepicker({ minDate: new Date()}); 
	$("#caseexetime").datepicker({ maxDate: new Date()}); 
    $("#caseexetime").change(function(){
    	var taskId=$("#task_show").attr("var");
    	var EXETime= $("#caseexetime").val();
    	 request.getTaskSummary(taskId,EXETime);
    	});
	$("#country-selector,#project-selector,#task-selector,#admin-selector").button({
		text: false,
		icons: {primary: "ui-icon-triangle-1-s"}
	}).click(function() {
		var id 			= $(this).attr('id').split('-');
		$("ul[id$='_ul'][id!='"+id[0]+"_ul']").css("display","none");
		var ul = $( this ).parent().next();
		
		if($(this).attr('id').indexOf('task') > -1){
			$( ul ).attr("style","position:absolute;width:284px;z-index:99999;");
		}else{
			$( ul ).attr("style","position:absolute;width:136px;z-index:99999;");
		}
		var menu = $(ul).show().position({
			my: "right-14 top",
			at: "right-14 bottom",
			of: this
		});
		/**
		 * click any point of page will close the li
		 */
		$( document ).one( "click", function() {
			menu.hide();
		});
		return false;
	}).parent().buttonset().next().hide().menu();

	$("#admin_select").click(function(){
		$(this).attr("check") > 0 ? $(this).attr("check",0) : $(this).attr("check",1);
		reqsonseResult.adminSelected($("span#admin_show").attr("var"));
		reqsonseResult.taskAvg();
	});
	$("a#importData").click(function(){
		var html = $("#importData-div");
		var popDom = popBox($("#taskassignment-importdata-title").text(),html);
	});
	
	$("button#execute-import,span#execute-import").click(function(){
		ajax({
			url : "/qrt/",
			data : {},
			error : function(data){
				
			},
			success : function(data){
				
			}
		});
	});
	$("#sendtask").click(function(){
		var finishDate 	= $("#datepicker").val();
		var taskId 		= $("#task_show").attr("var");
		var userId 		= '';
		var EXETime= $("#caseexetime").val();
		var adminId 	= '';
		if($("#admin_select").attr("check") > 0){
			adminId 	= $('#admin_show').attr('var');
		}
		$.each($("input[id^='user-'][check='1']"),function(k,v){
			userId += '_' + $(v).attr('value');
		});
		if(userId != ''){
			userId 	= userId.substr(1,userId.length);
		}
		if(userId == '' && adminId != ''){
			popConfirm(
					$("#taskassignment-tester-select-title").text(),
					$("#taskassignment-tester-select-sendto").text() + adminId,
					function () {
						reqsonseResult.emailSend(taskId,userId,adminId,finishDate,currentEmployeeName,EXETime);
					},
					function () {
						return;
					}
			);
			return;
		}
		reqsonseResult.emailSend(taskId,userId,adminId,finishDate,currentEmployeeName,EXETime);
		
	});
	
	var validate = {
		finishDate : function(data){
			if(validate.isEmpty(data)){
				popAlert($("#taskassignment-title").text(),$("#taskassignment-finishdate").text());
				return false;
			}
			return true;
		},
		tester 	: function(data1,data2){
			if(validate.isEmpty(data1) && validate.isEmpty(data2)){
				popAlert($("#taskassignment-title").text(),$("#taskassignment-tester").text());
				return false;
			}
			return true;
		},
		task 	: function(data){
			if(validate.isEmpty(data)){
				popAlert($("#taskassignment-title").text(),$("#taskassignment-task").text());
				return false;
			}
			return true;
		},
		isEmpty : function(data){
			if(data != undefined && data != null && data != ''){
				return false;
			}
			return true;
		}
	};
	
	var reqsonseResult = {

		countryResponse 	: function(data,elem,key,val){
			/**
			 * init the <ul><li>
			 */
			var html 	= '';
			var li 		= '<li class="ui-menu-item" role="presentation">'
				+'<a href="javascript:;" id="select_{elem}" class="ui-corner-all" tabindex="-1"' 
				+'role="menuitem" key="{key}" val="{value}">{value}</a></li>';
			$.each(data['object'],function(k,v){
				//console.log("kes  "+keys +" : "+vals);
				html += li.replace(/{value}/g,v[val]).replace(/{key}/g,v[key]);
			});
			html 		= html.replace(/{elem}/g,elem);
			$("#"+ elem+ "_ul").append(html);
			var aElem 	= $("#"+ elem+ "_ul li:first a");
			var codeValue 	= $(aElem).attr("key");
			$("#"+ elem+ "_show").text($(aElem).text()).attr("var",codeValue);
			/**
			 * bind for element <a>
			 */
			reqsonseResult.bindUlLiA(elem);
			/**
			 * init the selection <ul><li>
			 */
			if(codeValue != undefined && codeValue != ''){
				reqsonseResult.nextAction(elem,codeValue);
			}
		},
		taskSummaryResponse : function(data){
			$.each(data['object'],function(k,v){
				$.each(v,function(key,val){
					$("#" + key ).text(val);
				});
			});
			reqsonseResult.taskAvg();
		},
		employeeResponse 	: function(data){
			var html 		= '';
			var tr 			= '<tr><td><input id="user-{number}" class="checkbox-assignment" type="checkbox" value="{number}" check="0"/></td>'
							+ '<td>{name}</td><td>{number}</td><td>{email}</td></tr>';
			$.each(data['object']['employees'],function(k,v){
				html 		+= tr.replace(/{number}/g,v["number"])
								.replace(/{name}/g,v["name"])
								.replace(/{email}/g,v["email"]);
			});
			$("#users").append(html);
			$("input[id^='user-']").on('click',function(){
				if($(this).attr("check") == 0){
					$(this).attr("check","1");
				}else{
					$(this).attr("check","0");
				}
				reqsonseResult.taskAvg();
			});
		},
		bindUlLiA 			: function(elem){
			$("a[id^='select_"+elem+"']").on("click",function(){
				$(this).parent().parent().css("display","none");
				$("#"+elem+"_show").html($(this).attr("val")).attr("var",$(this).attr("key"));
				reqsonseResult.nextAction(elem,$(this).attr("key"));
			});
		},
		nextAction 			: function(elem,code){
			switch(elem){
			case 'country' 	: {
				$("#project_ul,#task_ul").children().remove();
				$("#project_show,#task_show").attr("var","").text("");
				document.getElementById("caseexetime").value="";
				request.getProject(code);
				break;
				};
			case 'project' 	: {
				$("#task_ul").children().remove();
				$("#task_show").attr("var","").text("");
				request.getTask(code);
				break;
				};
			case 'task' : {
				var currentDate=new Date();
				var year=currentDate.getFullYear();
				var day=currentDate.getDate();
				var month=currentDate.getMonth();
				realMonth=month+1;
				if(currentDate.getDay()==1)
				{
					currentDate.setDate(currentDate.getDate()-3);
					document.getElementById("caseexetime").value=currentDate.getDate()+"/"+(currentDate.getMonth()+1)+"/"+currentDate.getFullYear();
				}
				else
				{
					var realMonth=realMonth>10?realMonth:("0"+realMonth);
					day=day>10?day:("0"+day);
				document.getElementById("caseexetime").value=realMonth+"/"+day+"/"+year;
				}
				var caseEXETime=realMonth+"/"+day+"/"+year;
			    request.getTaskSummary(code,caseEXETime);
				break;
				};
			case 'admin' : {
				// deal with this account in the user list
				reqsonseResult.adminSelected(code);
				break;
				};
			default : {}
			}
		},
		adminSelected		: function(admin){
			// deselect/disable this user
			if ($("input#admin_select").is(":checked")) {
				$("input[id^='user-']").removeAttr("disabled");
				$("input#user-" + admin).removeAttr("checked").attr("check", 0).attr("disabled", "disabled");
				reqsonseResult.taskAvg();
			} else {
				$("input[id^='user-']").removeAttr("disabled");
			}
		},
		taskAvg 			: function(){
			var tester 	= $("input[id^='user-'][check='1']").length + $("input[id^='admin_'][check='1']").length;
			$("#tester_num").text(tester);
			if(tester < 1 || $("#test_total").text() < 1){
				$("#task_avg").text("0");
			}else{
				var task_avg 	= $("#test_failed").text()/tester;
				$("#task_avg").text( Math.ceil(task_avg) );
				
			}
		},
		emailSend : function(taskId,userId,adminId,finishDate,sendman,EXETime){
			if(validate.finishDate(finishDate) 
					&& validate.task(taskId) 
					&& validate.tester(userId,adminId)
			){
				loading(true);
				$.ajax({
					url: '/qrt/service/assignTask.json',
					type:'POST',
					async : true,
					data:{'taskId':taskId,'tester':userId,'adminId':adminId,'finishDate':finishDate,'sendman':sendman,'empnum':currentEmployeeNumber,'EXETime':EXETime},
					error: function(data){
						loading(false);
						popAlert($("#taskassignment-emailsend-title").text(),$("#taskassignment-emailsend-failed").text());
					},
					success: function(data){
						loading(false);
						reqsonseResult.nextAction("project",$("#project_show").attr("var"));
						popAlert($("#taskassignment-emailsend-title").text(),$("#taskassignment-emailsend-success").text());
						navigate('tasks/taskAssignment.html');
					}
				});
			}
		},
		
		
		initUploadify : function(){
	       /**
		   $("#import-file").uploadify({
	            swf      	: '/qrt/js/uploadify/uploadify.swf',
	            uploader 	: '/qrt/service/excelUpload.json',
	            buttonText 	: ''+$("#select-file-span").text(),
	            method 		: 'post',
	            multi		: true,
	            fileObjName : 'myfile',
	            width		: 80,
	            height		: 25,
	            queueID     : 'screen-shots',
	            onUploadSuccess : function(file,data,resp){
	              if(resp){
	                  data = $.parseJSON(data);
	              }
	            }
          });
		  */
      },
      destoryUplodify : function(){
          $("#import-file").uploadify("destroy");
      }
	};
	
	var request = {
		getCountry 			: function(){
			$.ajax({
				url 		: "/qrt/service/findAllCountries.json",
				method 		: "GET",
				error 		: function(data){},
				success 	: function(data){
					reqsonseResult.countryResponse(data,'country','country_code','country_code');
				}
			});
		},
		getProject 			: function(countryCode){
			$.ajax({
				url 		: "/qrt/service/findAllProjects/"+countryCode+".json",
				method 		: "GET",
				error 		: function(data){},
				success 	: function(data){
					reqsonseResult.countryResponse(data,'project','scope_id','project_name');
				}
			});
		},
		getTask 			: function(scopeId){
			$.ajax({
				url : "/qrt/service/findAllTasks/"+scopeId+".json",
				method : "GET",
				error : function(data){},
				success : function(data){
					reqsonseResult.countryResponse(data,'task','task_id','task_name');
				}
			});
		},

		getTaskSummary : function(taskId,EXETime)
		{
			$.ajax({
				url : "/qrt/service/findTaskSummary.json",
				method : "POST",
				async : true,
				data:{'taskId':taskId,'EXETime':EXETime},
				error : function(data){},
				success : function(data){
					reqsonseResult.taskSummaryResponse(data);
				}
			});
		},
		getEmployee 		: function(){
			$.ajax({
				url 		: "/qrt/service/employee.json",
				method 		: "GET",
				error 		: function(data){},
				success 	: function(data){
					reqsonseResult.employeeResponse(data);
				}
			});
		},
		getQAAdmin 			: function(){
			$.ajax({
				url : "/qrt/service/getAllQAAdmin.json",
				method : "GET",
				error : function(data){},
				success : function(data){
					reqsonseResult.countryResponse(data,"admin","number","name");
				}
			});
		}
  };
	
	request.getQAAdmin();
	request.getEmployee();
	request.getCountry();
	//reqsonseResult.initUploadify();
	//request.getEmployee();
});

function debugOnly(){}

function destroy() {
	if ($("#import-file").length > 0) {
		$("#import-file").uploadify("destroy");
	}
}

function inited(){
	var CT = new ColorTable();
	CT.bind($("table#users"));
}

