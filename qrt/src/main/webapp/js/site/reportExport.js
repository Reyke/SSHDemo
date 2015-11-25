$(function() {
	var errorHandler = {
		popupError : function(title, msg) {

		}
	};

	var request = {
		get : function(surl, objRender) {
			ajax({
				url : surl,
				method : "GET",
				error : function(data) {
					errorHandler.popupError("", data);
				},
				success : function(data) {
					objRender.render(data);
				}
			});
		},

		post : function(surl, odata, objRender) {
			ajax({
				url : surl,
				method : "POST",
				data : JSON.stringify(odata),
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				error : function(data) {
					errorHandler.popupError("", data);
				},
				success : function(data) {
					objRender.render(data);
				}
			});
		}

	};

	var renderer = {
		FactoryRender: {
			render: function (data) {
				$.each(data.object, function (i, d) {
					$("#countrySelector").append("<option value='" + d.country_code + "'>" + d.country_name + "</option>");
				});
			}
		}, 
		ProjectRender: {
			render: function (data) {
				/*
				 * add default option
				 */
				var defaultOption = $("#projectSelector option:eq(0)");
				$("#projectSelector").text("");
				$("#projectSelector").append(defaultOption);
				
				$.each(data.object, function (i, d) {
					$("#projectSelector").append("<option value='" + d.scope_id + "'>" + d.project_name + "</option>");
				});
			}
		}, 
		TaskRender: {
			render: function (data) {
				var defaultOption = $("#taskSelector option:eq(0)");
				$("#taskSelector").text("");
				$("#taskSelector").append(defaultOption);
				$.each(data.object, function (i, d) {
					$("#taskSelector").append("<option value='" + d.task_id + "'>" + d.task_name + "</option>");
				});
			}
		}, 
		TemplateRender: {
			render: function (data) {
				var defaultOption = $("#templateSelector option:eq(0)");
				$("#templateSelector").text("");
				$("#templateSelector").append(defaultOption);
				$.each(data, function (i, d) {
					$("#templateSelector").append("<option value='" + d.templateId + "'>" + d.name + "</option>");
				});
			}
		},
		TemplateSummaryRender:{
			
			render: function (data) {
				var defaultOption = $("#taskInfo option:eq(0)");
				$("#taskInfo").text("");
				$("#taskInfo").append(defaultOption);
				$.each(data, function (i, d) {
					$("#taskInfo").append("<li><span>" + d.emp.number + "</span><span>" + d.emp.name + "</span>"+(d.finishedInd=="Y"?"<span class='finished'>finished</span>":"<span class='unfinished'>unfinished</span>")+"</li>");
				});

			}
		},
		DoNothing: {
			render: function () {
				
			}
		}
	};

	var reportExport = {
		init : function() {
			this._initCountryList();
			this._initTemplateList();
			
			this._registerEvents();
		},
		exportReport: function () {
			// verify input
			if ($("#countrySelector").val() == '') {
				// contry not selected
				popAlert("Report Export", "Please select a Country.");
				return;
			}
			if ($("#projectSelector").val() == '') {
				// contry not selected
				popAlert("Report Export", "Please select a Project.");
				return;
			}
			if ($("#taskSelector").val() == '') {
				// contry not selected
				popAlert("Report Export", "Please select a Task.");
				return;
			}
			if ($("#templateSelector").val() == '') {
				// contry not selected
				popAlert("Report Export", "Please select a Template.");
				return;
			}
			
			// export.
			// request.get("/qrt/service/report?taskId=" + $("#taskSelector").val() + "&templateId=" + $("#templateSelector").val(), renderer.DoNothing);
			window.open("/qrt/service/report?taskId=" + $("#taskSelector").val() + "&templateId=" + $("#templateSelector").val());
		}, 
		_initCountryList : function() {
			request.get("/qrt/service/findAllCountries.json", renderer.FactoryRender);
		},
		_initProjectList : function() {
			request.get("/qrt/service/findAllProjects/" + $("#countrySelector").val() + ".json", renderer.ProjectRender);
		},
		_initTaskList : function() {
			request.get("/qrt/service/findAssignTasks/" + $("#projectSelector").val() + ".json", renderer.TaskRender);
		},
		_initTemplateList : function() {
			request.get("/qrt/service/template/allTemplate", renderer.TemplateRender);
		}, 
		_initSummary: function(){
			request.get("/qrt/service/taskSummary/"+($("#taskSelector").val()?$("#taskSelector").val():"0"), renderer.TemplateSummaryRender);
		},
		_registerEvents: function () {
			$("#countrySelector").change(this._initProjectList);
			
			$("#projectSelector").change(this._initTaskList);
			$("#taskSelector").change(this._initSummary);
			
			
			$("#exportButton").click(this.exportReport);
		}
	};

	reportExport.init();

});