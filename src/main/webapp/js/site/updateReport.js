$(function(){
	var imgServiceUrl = '';
	loading(true);
	
	$(".task-select").change(function(){
		if($(this).val()!= '' && $(this).val() != undefined){
			if(response.userFlag > 0 ){
				$("#sortBy").css({"display":"inline-block","*display":"inline","zoom":"1"});
				request.getTesterByTask($(".task-select").val());
			}else{
				$("#sortBy").css({"display":"none"});
			}
			request.showSetCaseTreeList($(this).val());
		}else{
			$("#task_status,#sortBy").css({"display":"none"});
		}
	});
	
	$("#issueType").change(function(){
		if($(this).val() != ''){
			var screenShotDiv = $("div#issue-data div#img-show").children().clone(true);
			$("div#issue-data").children().remove();
			var elemName = $(this).val().replace(/ /g,"-");
			if(elemName != 'Re-run-Passed'){
				if($(this).val() == 'Script Issue' || $(this).val() == 'Other Issue'){
					$(".manualPassed").css({"visibility":"visible"}).attr("disabled",false).prop("checked",false);
				}else{
					$(".manualPassed").css({"visibility":"hidden"}).attr("disabled","disabled");
				}
				$("#issue-data").append($("."+elemName.toLowerCase()).clone())
				.append($(".screen-shot").clone(true))
				.find("div#img-show")
				.append(screenShotDiv);
				// response.();
				$("div#screen-shot-file").css("display", "");
			} else {
				$("div#screen-shot-file").css("display", "none");
			}
		}
	});
	
	$("#manual_title").mouseover(function(){
		$(this).css({"cursor":"pointer"});
	}).click(function(){
		var checkbox = $("#Manual_passed");
		if($(checkbox).attr("checked") == "checked"){
			$(checkbox).prop("checked",false);
		}else{
			$(checkbox).prop("checked",true);
		}
	});
	
	$("#data-save").click(function(){
		loading(true);
		$.ajax({
			url 	: "/qrt/service/issues/update",
			method 	: "POST",
			data 	: $("#form-issue-data").serialize(),
			error 	: function(data){
				loading(false);
                popAlert($("#case-update-title").text(),$("#case-update-failed").text());
			},
			success : function(data){
				loading(false);
				$("#li_" + $("#data-save").attr("var"))
				.removeClass("status_red")
				.removeClass("status_green")
				.removeClass("status_orange")
				.addClass("status_"+data)
				.attr("title",$("#case_statu_"+data).text());
				
				propertyBinding.binding($("#li_" + $("#data-save").attr("var")), true);
				
				popAlert($("#case-update-title").text(),$("#case-update-success").text());
			}
		});
	});
	
	$("span.div-task-menu-mytasks,span.div-task-menu-alltasks").click(function(){
		$("#testcase-tree").html("");
		$("#task_status,#sortBy").css({"display":"none"});
		$(this).parent().find("span")
		.removeClass("background-fff").removeClass("border-bottom0").addClass("background-gray").addClass("border-bottom1");
		$(this)
		.removeClass("background-gray").removeClass("border-bottom1").addClass("background-fff").addClass("border-bottom0");
		
		response.userFlag = $(this).attr("var");
		if(response.userFlag > 0){
			$("#complete").hide();
			$("#div-now-test")
			.css("display","inline-block")
			.css("*display","inline")
			.css("zoom","1");
		}else{
			$("#complete").show();
			$("#div-now-test").hide();
		}
		request.showTaskList();
	});
	
	$("#data-reset").click(function(){
		request.showIssue($("#data-save").attr("var"));
	});
	
	$("#complete").click(function(){
		var taskId = $(".task-select").val();
		if(taskId != ''){
			request.taskCompelete($(".task-select").val());
		}else{
			popAlert($("#task-noselect-title").text(),$("#task-noselect-alert")).text();
		}
	});

	$("#byPeople,#byIssue").click(function(){
		if($(this).attr("id") == "byPeople"){
			if($("#divPeople").attr("var") == 0){
				$("#divPeople").css({"display":"inline-block","*display":"inline","z-index":"99"}).attr("var","1");
			}else{
				$("#divPeople").css({"display":"none","z-index":"9"}).attr("var","0");
			}
			$("#divIssue").css({"display":"none","z-index":"9"}).attr("var","0");
		}else{
			if($("#divIssue").attr("var") == 0){
				$("#divIssue").css({"display":"inline-block","*display":"inline","z-index":"99"}).attr("var","1");
			}else{
				$("#divIssue").css({"display":"none","z-index":"9"}).attr("var","0");
			}
			$("#divPeople").css({"display":"none","z-index":"9"}).attr("var","0");
		}

	});

	
	var request = {
		showTaskList : function(){
			$.ajax({
				url 	: "/qrt/service/lastTaskList/"+currentEmployeeNumber+"/"+response.userFlag,
				method 	: "GET",
				error 	: function(data){
					
				},
				success : function(data){
					response.showTaskList(data);
				}
			});
		},
		showSetCaseTreeList : function(taskId,userId,issueType){
			userId = userId == undefined ? null : userId;
			issueType = issueType == undefined ? null : issueType;
			loading(true);
			$.ajax({
				url 	: "/qrt/service/testSetCaseList",
				method 	: "POST",
				data 	: {'taskId':taskId,'empno':currentEmployeeNumber,'flag':response.userFlag,'userId':userId,'issueType':issueType},
				async 	: true,
				error 	: function(data){
					loading(false);
				},
				success : function(data){
					loading(false);
					response.showSetCaseTreeList(data);
				}
			});
		},
		showIssue : function(caseId){
			$.ajax({
				url 	: "/qrt/service/issues/"+caseId,
				method 	: "GET",
				error 	: function(data){
					
				},
				success : function(data){
					response.showIssue(data);
				}
			});
		},
		showIssueType : function(){
			$.ajax({
				url 	: "/qrt/service/issues/getAllIssueType",
				method 	: "GET",
				error 	: function(data){
						
				},
				success : function(data){
					response.showIssueType(data);
				}
			});
		},
		showDateIssueType : function(){
			$.ajax({
				url 	: "/qrt/service/issues/getAllDataIssueType",
				method 	: "GET",
				async 	: true,
				error 	: function(data){
				},
				success : function(data){
					response.showDateIssueType(data);
				}
			});
		},
		showDefectStatusType : function(){
			$.ajax({
				url 	: "/qrt/service/issues/getAllDefectStatusType",
				method 	: "GET",
				async 	: true,
				error 	: function(data){
				},
				success : function(data){
					response.showDefectStatusType(data);
				}
			});
		},
		showIssueHistories : function(caseId){
			loading(true);
			$.ajax({
				url 	: "/qrt/service/getTestCaseHistory/"+caseId,
				method 	: "GET",
				async 	: true,
				error 	: function(data){
					loading(false);
				},
				success : function(data){
					loading(false);
					response.showIssueHistories(data);
				}
			});
		},
		showIssueHistory : function(caseId){
			loading(true);
			$.ajax({
				url 	: "/qrt/service/issues/"+caseId,
				method 	: "GET",
				async 	: true,
				error 	: function(data){
					loading(false);
				},
				success : function(data){
					loading(false);
					response.showIssueHistory(data);
				}
			});
		},
		getImgServiceUrl : function(){
			$.ajax({
				url 	: "/qrt/service/issues/getImgServiceUrl",
				method 	: "GET",
				success : function(data){
					imgServiceUrl = data;
				}
			});
		},
		taskCompelete : function(taskId){
			ajax({
				url 	: "/qrt/service/completeTask/"+taskId+"/"+currentEmployeeNumber+".json",
				method 	: "GET",
				error 	: function(data){
					popAlert($("#task-complete-title").text(),$("#task-complete-failed").text());
				},
				success : function(data){
					if(data){
						popAlert($("#task-complete-title").text(),$("#task-complete-success").text());
					}else{
						popAlert($("#task-complete-title").text(),$("#task-complete-failed").text());
					}
				}
			});
		},
		getTesterByTask : function(taskId){
			ajax({
				url : "/qrt/service/findTesterByTask/"+taskId+".json",
				method : "GET",
				error : function(data){

				},
				success : function(data){
					response.getTesterByTask(data);
				}
			});
		}
	};
	
	var response = {
		userFlag : 0,
		init : function(){
			var flag = false;
			if($("span#txtRoleValue").text() == 'QA_ADMIN'){
				$("span.div-task-menu-alltasks")
				.css("display","");
			}

			flag = request.showTaskList(0);
			if(flag == false){
				loading(false);
				
				return false;
			}
			request.getImgServiceUrl();
			request.showIssueType();
			request.showDateIssueType();
			request.showDefectStatusType();
			response.initUploadify();
			return true;
		},
		showTaskList : function(data){
			if(data.length < 1){
				popModalMessage("Task Update",$("#notask-alert").text());
				return false;
			}
			var html = '';
			var options = '<option value="{task_id}">{country_code} {project_name} {task_name}</option>';
			$.each(data,function(k,task){
				html 	+= options.replace(/{task_id}/g,task['task_id'])
							.replace(/{country_code}/g,task['country_code'])
							.replace(/{project_name}/g,task['project_name'])
							.replace(/{task_name}/g,task['task_name']);
			});
			$(".task-select option[value!='']").remove();
			$(".task-select").append(html);
		},
		showSetCaseTreeList : function(data){
			var html = '';
			var greenCount,Count;
			greenCount = Count= 0;
			var li = '<li id="li_{case_id}" class="set status_{status}" title="{title}" var="{case_id}">{case_name}</li>';
			$.each(data,function(setName,caseList){
				html += ('<ul styple="">' + li.replace(/{case_name}/g,setName)).replace(/{title}/g,'');
				var hasUl = false;
				var ulHtml = '';
				$.each(caseList,function(k,taskCase){
					hasUl = true;
					ulHtml += li.replace(/{case_name}/g,taskCase['case_name'])
								.replace(/{case_id}/g,taskCase['case_id'])
								.replace(/{status}/g,taskCase['status'])
								.replace(/{title}/g,$("#case-statu-"+taskCase['status']).text());
					Count ++;
					if(taskCase['status'] == 'green'){
						greenCount ++;
					}
				});
				
				if(hasUl){
					html += ('<ol isShow="1" >' + ulHtml + '</ol>');
				}
				html += '</li></ul>';
			});
			
			$("#task_status").html("<font style='color:green;'>" + greenCount + "</font>/" + Count)
			.css({"display":"inline-block","*display":"inline","font-weight":"bolder","margin-left":"5px"});
			
			$("#testcase-tree").children().remove();
			$("#testcase-tree").append(html);
			$("#testcase-tree  ol > li").on("click",function(){
				$("#testcase-tree  ol > li").attr("isSelected","0").css("background-color","#FFF");
				$(this).attr("isSelected","1").css("background-color","#BBDDFF");
				request.showIssue($(this).attr("var"));
				request.showIssueHistories($(this).attr("var"));
			});
			$("#testcase-tree  ul > li").on("click",function(){
				var ol = $(this).parent().find("ol");
				if($(ol).attr("isShow") > 0){
					$(ol).slideDown(1000,function(){
						$(ol).attr("isShow",0).hide();
					});
				}else if($(ol).attr("isShow") < 1){
					$(ol).slideDown(1000,function(){
						$(ol).attr("isShow",1).show();
					});
				}
			});
			$("#testcase-tree ol > li").mouseover(function(){
				$(this).css("cursor","pointer").addClass("hover_li_color1");
			}).mouseout(function(){
				if($(this).attr("isSelected") == undefined || $(this).attr("isSelected") < 1){
					$(this).css("cursor","default").addClass("hover_li_color2");
				}
			});
			
			propertyBinding.binding($("#testcase-tree"), true);
		},
		showIssue : function(data){
			/**
			 * init the div #content-body-right-top
			 */
			$("#caseId").val(data.caseId);
			$("#data-save").attr("var",data.caseId);
			$("#tester").html(data.testCase.tester);
			$("#now_tester").html(data.responser.name);
			if(data.testCase.original_status != 'Passed'){
				$("#status").html(data.testCase.original_status).css("color","red");
			}else{
				$("#status").html(data.testCase.original_status).css("color","green");
			}
			$("#content-body-right-issue-form").css("display","").scrollTop(0);
			
			var element = data.issueType.toLowerCase().replace(/ /g,"-");
			/**
			 * init the select#issueType value
			 */
			// we need to change the issue type Undetermined to blank.
			$("#issueType").val(data.issueType.replace(/Undetermined/g, ""));
			$("#issue-data").children().remove();
			var issueDataElement = $("#issue-data")
			.append($("."+element).clone())
			.append($(".screen-shot").clone(true));
			
			/**
			*if the issue type is scriptIssue or otherIssue , we will show the checkbox of mannulPassed
			*/
			if(data.issueType == 'Script Issue' || data.issueType == 'Other Issue'){
				$(".manualPassed")
				.css({"visibility":"visible"})
				.find("input")
				.prop("checked",data.manualPassedInd == 'Y');
			}else{
				$(".manualPassed")
				.css({"visibility":"hidden"})
				.find("input")
				.prop("checked",false);
			}
			/**
			 * init when the new uplodify plugin was used in new div 
			 */
			//response.initUploadify();
			
			/**
			 * init div#issue-data childrens (input,select,textarea) value
			 */
			response.autoBindToPopBox(issueDataElement,"input",data,false);
			response.autoBindToPopBox(issueDataElement,"select",data,false);
			response.autoBindToPopBox(issueDataElement,"textarea",data,false);
			
			// fix the error message while openning a new issue without error screenshots.
			if (data['errorScreenshotSet'] != undefined) {
				$.each(data['errorScreenshotSet'],function(k,ScreenShot){
					response.showImages(ScreenShot['screenShotLink'], ScreenShot['ssId']);
				});
			}
		},
		showIssueType : function(data){
			var html 	= '';
			var html2 	= '<li val="null">ALL</li>';
			var option 	=  '<option value="{issue_type}">{issue_type_desc}</option>';
			var li = '<li val="{issueType}">{issueType}</li>';
			$.each(data,function(k,issueType){
				if(issueType.id != 'Undetermined'){
					html += option.replace(/{issue_type}/g, issueType.id)
							.replace(/{issue_type_desc}/g, issueType.id);
					html2 += li.replace(/{issueType}/g, issueType.id);
				}
			});
			var li = $("#divIssue").find("ul").html(html2).find("li");
			$("#issueType").append(html);
			response.bindElement(li);
		},
		showDateIssueType : function(data){
			var html = '';
			var option = '<option value="{dateType}">{dateType}</option>"';
			$.each(data,function(k,dataIssueType){
				html += option.replace(/{dateType}/g,dataIssueType.dateType);
			});
			$("#data-type").html(html);
		},
		showDefectStatusType : function(data){
			var html = '';
			var option = '<option value="{defectStatus}" {selected}>{defectStatus}</option>';
			$.each(data,function(k,defectStatusType){
				html += option.replace(/{defectStatus}/g,defectStatusType.defectStatus)
					.replace(/{selected}/g, "N/A" == defectStatusType.defectStatus ? "selected='true'" : "");
			});
			$("select.defect-status").html(html);
			
		},
		showIssueHistories : function(data){
			var html 	= '';
			var tr 		= '<tr><td>{original_status}</td><td>{issueType}</td><td>{description}</td><td><a href="javascript:;" var="{caseId}">{date}</a></td></tr>';
			$.each(data,function(k,issue){
				var tempTr = tr.replace(/{original_status}/g,issue.testCase.original_status)
						.replace(/{issueType}/g,issue.issueType)
						.replace(/{date}/g,issue.versionDateNumber)
						.replace(/{caseId}/g,issue.caseId);
				switch(issue.issueType){
					case "Data Issue": { tempTr = tempTr.replace(/{description}/g,issue.dataType+" "+issue.errorData);break;}
					case "Device Issue": { tempTr = tempTr.replace(/{description}/g,issue.deviceName);break;}
					//case "Object Change": { tempTr = tempTr.replace(/{description}/g,issue.errorPageName+" "+issue.afterChange);break;}
					case "Possible Defect": { }
					case "Known Issue": { }
					case "Other Issue": { tempTr = tempTr.replace(/{description}/g,issue.title);break;}
					//case "Performance Issue": { }
					case "Script Issue": { tempTr = tempTr.replace(/{description}/g,issue.errorPageName);break;}
					default : {tempTr = tempTr.replace(/{description}/g,'');break;}
				}
				html += tempTr;
			});
			var messageTable = $(showPublicMessage( $("#message-body").html(html).parent().clone()) );
			var CT = new ColorTable();
			CT.bind(messageTable);
			messageTable.find("a").on("click",function(){
				request.showIssueHistory($(this).attr("var"));
			});
		},
		showIssueHistory : function(data){
			var html = $("."+(data.issueType.toLowerCase()).replace(/ /g,'-')).clone()[0];
			var popbox = popBox(data.testCase.case_name + "History",html).hide();
			response.autoBindToPopBox(popbox,"input",data,true);
			response.autoBindToPopBox(popbox,"select",data,true);
			response.autoBindToPopBox(popbox,"textarea",data,true);
			popbox.show();
		},
		autoBindToPopBox : function(popBox,element,data,isPop){
			$.each($(popBox).find(element),function(k,elem){
				if(isPop){
					var label = '<label> : '+data[$(elem).attr("name")]+'</label>';
					$(elem).hide().parent().append(label);
				}else{
					$(elem).val(data[$(elem).attr("name")]);
				}
			});
		},
		initUploadify : function(){
			// only init once.
			if ($('#screen-shot-file').is("div")) {
				return;
			}
			$('#screen-shot-file').uploadify({
				swf      	: '/qrt/js/uploadify/uploadify.swf',
				uploader 	: '/qrt/service/fileUpload.json',
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
						$("div#img-show > input#imgIds").val($("#imgIds").val() + data['id'] + ",");
						response.showImages(data['url'],data['id']);
					}
				}
			});
			$("#screen-shot-file-button").on("click",function(){
				$(".swfupload").click();
			});
		},
		showImages : function(url,id){
			var imgDiv = '<div id="div-showImg">'
							+ '<img src="'+ imgServiceUrl + url+'"/>'
							+ '<div><a href="javascript:;">reomve</a></div>'
							+ '<input type="hidden" name="ssId" id="imgId" value="'+id+'"/>'
						 + '</div>';
			$("#form-issue-data")
			.find("div#img-show")
			.append(imgDiv)
			.find("div#div-showImg")
			.css("width", $(this).find("img:last").width())
			.find("div > a")
			.on("click",function(){
				$(this).parent().parent().css("display","none").find("input#imgId").attr("disabled","disabled");
				return;
			});
			$.each($("#form-issue-data").find("div#div-showImg"),function(k,v){
				$(v).css("width",$(v).find("img").width() + 6);
			});
			bindImg();
		},
		getTesterByTask : function(data){
			var html = '<li val="null">ALL</li>';
			var li = "<li val='{emp_nbr}'>{emp_name}</li>";
			$.each(data,function(k,v){
				html += li.replace(/{emp_nbr}/g,v[0]).replace(/{emp_name}/g,v[1]);
			});
			var li = $("#divPeople").find("ul").html(html)
			.find("li");
			response.bindElement(li);
		},
		bindElement : function(element){
			$(element).on('mouseover',function(){
				$(this).css({"cursor":"pointer","background-color":"#fcfcfc"});
			}).on('mouseout',function(){
				if($(this).attr("var") != 1){
					$(this).css({"background-color":"#dedede"});
				}
			}).on('click',function(){
				$(this)
				.parent().parent()
				.css({"display":"none"})
				.attr("var","0")
				.find("li")
				.attr("var","0")
				.css({"background-color":"#dedede"});

				$(this)
				.attr("var","1")
				.css({"background-color":"#fcfcfc"});
				
				if($(this).parent().parent().attr("id") == 'divPeople'){
					$("#byPeople").attr("title",$(this).text());
				}else{
					$("#byIssue").attr("title",$(this).text());
				}
				
				request.showSetCaseTreeList(
					$(".task-select").val(),
					$("#divPeople li[var='1']").attr("val"),
					$("#divIssue li[var='1']").attr("val")
					);
			});
		}
	};
	
	var validate = {
		isEmpty : function(data){
			if(data != undefined && data != null && data != ''){
				return false;
			}
			return true;
		},
		elemIsEmpty : function(element){
			var flag = false;
			$.each(element,function(k,input){
				if(validate.isEmpty($(input).val())){
					flag = true;
					return false;
				}
			});
			return flag;
		}
	};
	
	return response.init();
	loading(false);
});

function destroy() {
	if ($("#screen-shot-file").length > 0) {
		$("#screen-shot-file").uploadify("destroy");
	}
}

function afterLanguageChange(){
	
}

function debugOnly () {
	var i = 1;
}