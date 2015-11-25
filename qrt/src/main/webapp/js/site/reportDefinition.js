$(function () {
	
	var errorHandler = {
			popupError : function (title, msg) {
				
			}
	};
	
	var components = {};
	
	var request = {
			get : function (surl, objRender) {
				ajax({
					url : surl,
					method : "GET",
					error : function (data) {
						errorHandler.popupError("", data);
					},
					success : function (data) {
						objRender.render(data);
					}
				});				
			},
			
			post : function (surl, odata, objRender) {
				ajax({
					url : surl,
					method : "POST",
					data : JSON.stringify(odata),
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					error : function (data) {
						errorHandler.popupError("", data);
					},
					success : function (data) {
						objRender.render(data);
					}
				});						
			}
			
	};
	
	var renderer = {
			renderTemplateList : {
				render : function (data) {
					var table = $("table#reportTemplates");
					var anchorChange = $("div#componentBox .changeTemplate").prop("outerHTML");
					var anchorDelete = $("div#componentBox .deleteTemplate").prop("outerHTML");
					var anchorClone = $("div#componentBox .cloneTemplate").prop("outerHTML");
					var tr = "<tr><td>{templateName}</td><td>{createBy}</td><td>{status}</td><td>" + anchorChange + anchorDelete + anchorClone + "</td></tr>";
					var tbody = table.find("tbody");
					var html = "";
					// clear the original table
					tbody.html("");
					$.each(data, function (i, template) {
						html += tr.replace(/{templateName}/g, template["name"])
								.replace(/{createBy}/g, template["createdBy"])
								.replace(/{status}/g, template["status"] == "N" ? "Disabled" : "Enabled")
								.replace(/{id}/g, template["templateId"]);
					});
					tbody.append(html);
					
					// change event
					tbody.find(".changeTemplate").on("click", function () {
						templateUpdateForm.update($(this).attr("var"));
					});
					
					// delete event
					tbody.find(".deleteTemplate").on("click", function () {
						var templateId = $(this).attr("var");
						popConfirm ("Report Definition", "Are you sure to delete this template ?", function () {
							templateUpdateForm.del(templateId);
						}, function () {
							// do nothing.
						});
					});
					
					// clone event
					tbody.find(".cloneTemplate").on("click", function () {
						templateUpdateForm.clone($(this).attr("var"));
					});
					
					var ct = new ColorTable();
					table.removeAttr("colorBound");
					ct.bind(table);
				}
			},
			renderExistsTemplate : {
				render : function (data) {
					renderer._renderTemplate(data, false);
				}
			}, 
			renderCloneTemplate : {
				render : function (data) {
					renderer._renderTemplate(data, true);
				}
			},
			renderNewTemplate : {
				render : function () {
					renderer._renderTemplate({"templateDetails":[]}, true);
				}
			},
			_renderTemplate : function (data, isClone) {
				var columns = $("#templateUpdateForm #divColumns");
				
				var columnText = $("div#componentBox .componentRow").html();
				var appendButton = $("div#componentBox .buttonAppend").prop("outerHTML");
				var defaultAppendButton = $("div#componentBox .defaultAppendButton").prop("outerHTML");
				
				var html = "";
				
				var fnAppendDefaultButtonIfNoneColumn = function () {
					if ($("#divColumns input.columnId").length <= 0) {
						// no column left, we need add a append button to create new column.
						
						// maybe the append button still exists, let's remove it firstly. 
						columns.empty();
						
						columns.append(defaultAppendButton);
						
						columns.find(".defaultAppendButton").on("click", fnAppendColumn);
						
						// remove this button
						$(this).remove();
					}
				};
				
				var fnPopExpression = function () {
					var myTarget = $(this);
					
					$("#templateExpression #clonedExpression").val(myTarget.val());

					$("#templateExpression #clonedExpression").attr("var", myTarget.attr("var"));
					
					$("#templateExpression #orginalInputIndex").val(myTarget.attr("idx"));
					
					$("#templateExpression").css({"display": "block", "z-index": "101"}).offset($(this).offset());
				};
				
				var fnAppendColumn = function () {
					var idx = $("#divColumns input.columnId").length + 1;
					
					$(this).after(columnText.replace(/{i}/g, idx)
							.replace(/{title}/g, "")
							.replace(/{orginal_expression}/g, "")
							.replace(/{expression}/g, "")
							.replace(/{id}/g, "") + appendButton);
					
					// attach listener to the new button.
					$("#divColumns .buttonAppend").on("click", fnAppendColumn);
					
					$("#divColumns .buttonInsert[idx=" + idx + "]").on("click", fnInsertColumn);

					$("#divColumns .buttonRemove[idx=" + idx + "]").on("click", fnRemoveColumn);
					
					$("#divColumns input.column_expression[idx=" + idx + "]").on("click", fnPopExpression);
					
					// remove this button
					$(this).remove();					
				};
				
				var fnInsertColumn = function () {
					// create new column
					var idx = $(this).attr("idx");
					
					// all columns after this one need to be updated.
					$("#divColumns input.columnId").each(function (i) {
						var curIdx = $(this).attr("idx");
						if (parseInt(curIdx) >= parseInt(idx)) {
							var newIdx = parseInt(curIdx) + 1;
							$("#divColumns label[idx=" + curIdx + "][done!=1]").text("Column " + newIdx);
							$("#divColumns [idx=" + curIdx + "][done!=1]").attr("idx", newIdx).attr("done", 1);
						}
					});
					
					$("#divColumns [done=1]").removeAttr("done");
					
					// we need to insert a new column before this idx.
					$("#divColumns br[idx=" + (parseInt(idx) + 1) + "]").before(columnText.replace(/{i}/g, idx)
							.replace(/{title}/g, "")
							.replace(/{expression}/g, "")
							.replace(/{orginal_expression}/g, "")
							.replace(/{id}/g, ""));		
					
					$("#divColumns .buttonInsert[idx=" + idx + "]").on("click", fnInsertColumn);

					$("#divColumns .buttonRemove[idx=" + idx + "]").on("click", fnRemoveColumn);
					
					$("#divColumns input.column_expression[idx=" + idx + "]").on("click", fnPopExpression);
				};
				
				var fnRemoveColumn = function () {
					var idx = $(this).attr("idx");
					
					//remove this column
					$("#divColumns [idx=" + idx + "]").remove();
					
					// all columns with index greater than this one need to be updated.
					$("#divColumns input.columnId").each(function (i) {
						var curIdx = $(this).attr("idx");
						if (parseInt(curIdx) > parseInt(idx)) {
							var newIdx = parseInt(curIdx) - 1;
							$("#divColumns label[idx=" + curIdx + "][done!=1]").text("Column " + newIdx);
							$("#divColumns [idx=" + curIdx + "][done!=1]").attr("idx", newIdx).attr("done", 1);
						}
					});
					
					$("#divColumns [done=1]").removeAttr("done");
					
					// if no column left, append a default append button.
					fnAppendDefaultButtonIfNoneColumn();
				};
				
				
				$("#textTemplateName").val(data.name);
				$("#templateId").val(isClone ? "" : data.templateId);
				
				$("#templateStatus").prop("checked", data.status == "Y");
			
				var expressionRegExp = /\[([^\]]*)\]/g;
				
				$.each(data.templateDetails, function (i, column) {
					var expression = column["expression"];
					
					var exp = expression;
					if (expressionRegExp.test(expression)) {
						exp = exp.replace(expressionRegExp, function () {
							// find an expression, let's replace it.
							var r;
							try {
								r = "[" + components[arguments[1]] + "]";
							} catch (e) {
								r = arguments[0];
							}
							return r;
						});
						
					} 
						
					html += columnText.replace(/{i}/g, i + 1)
							.replace(/{title}/g, column["columnLable"])
							.replace(/{orginal_expression}/g, column["expression"])
							.replace(/{expression}/g, exp)
							.replace(/{id}/g, column["tdId"]);
				} );
				
				// create a append button at the end of this section.
				html += appendButton;
				
				columns.html(html);
				
				$("#divColumns .buttonInsert").on("click", fnInsertColumn);

				$("#divColumns .buttonRemove").on("click", fnRemoveColumn);
				
				$("#divColumns .buttonAppend").on("click", fnAppendColumn);
				
				$("#divColumns input.column_expression").on("click", fnPopExpression);
				// if no column left, append a default append button.
				fnAppendDefaultButtonIfNoneColumn();
				
				$("#templateUpdateForm").dialog("open");
			}, 
			emptyRender : {
				render : function () {
					// do nothing here.
				}
			}, 
			refreshRender : {
				render : function () {
					reportDefinition.initTemplateList();
				}
			}, 
			issueComponentRender: {
				_saveData: function (data) {
					$.each(data, function (i, component) {
						components[component.fieldName] = component.title;
					});
				},
				
				render: function (data) {
					// save the component list in local, we just need get from server one time.
					this._saveData(data);
					
					// clear the components selector first.
					$("#templateExpression #selectArea").empty();
					
					var html = "";
					var item = "<div id='component_{id}' var={id} class='componentCheckBox'>{name}</div>";
					
					var attachPlus = function () {
						return $("#templateExpression #clonedExpression").val() == "" ? "" : " + ";
					};
					
					$.each(data, function (i, component) {
						html += item.replace(/{id}/g, component.fieldName)
									.replace(/{name}/g, component.title);
					});
					
					$("#templateExpression #selectArea").html(html);
					
					$("#templateExpression #selectArea .componentCheckBox").on("click", function () {
						var expressionBox = $("#templateExpression #clonedExpression");

						expressionBox.attr("var", (typeof(expressionBox.attr("var")) == "undefined" ? "" : expressionBox.attr("var")) + attachPlus() + "[" + $(this).attr("var") + "]");
						expressionBox.val(expressionBox.val() + attachPlus() + "[" + $(this).text() + "]");
						
						expressionBox.attr("title", expressionBox.val());
					}).mouseenter(function () {
						// add new class to this div, it's focused.
						$(this).addClass("focus");
					}).mouseleave(function () {
						// remove focused class.
						$(this).removeClass("focus");
					});
					
					$("#templateExpression #componentBackSpace").on("click", function () {
						var expressionBox = $("#templateExpression #clonedExpression");
						if (expressionBox.val() != "" ) {
							var lastIndexOfPlus = expressionBox.val().lastIndexOf(" + ");
							if (lastIndexOfPlus < 0) {
								// last component left, clear the expression.
								expressionBox.val("");
								expressionBox.attr("var", "");
							} else {
								expressionBox.val(expressionBox.val().substring(0, lastIndexOfPlus));
								var v = expressionBox.attr("var");
								if ('undefined' != typeof(v)) {
									expressionBox.attr("var", v.substring(0, v.lastIndexOf(" + ")));						
								}
							}
						}
						expressionBox.attr("title", expressionBox.val());
					}).mouseenter(function () {
						// add new class to this div, it's focused.
						$(this).addClass("focus");						
					}).mouseleave(function () {
						// remove focused class.
						$(this).removeClass("focus");						
					});
					
					$("#templateExpression").mouseleave(function () {
						var orginalInputIndex = $("#orginalInputIndex").val();
						var myTarget = $("#divColumns input.column_expression[idx=" + orginalInputIndex + "]");
						myTarget.val($("#templateExpression #clonedExpression").val());
						myTarget.attr("title", $("#templateExpression #clonedExpression").val());
						myTarget.attr("var", $("#templateExpression #clonedExpression").attr("var"));
						$("#templateExpression").css("display", "none");
					});
				}
			}
	};
	
	var templateUpdateForm = {
		createNew : function () {
			renderer.renderNewTemplate.render();
		},
		update : function (id) {
			request.get("/qrt/service/template/" + id, renderer.renderExistsTemplate);
		},
		
		clone : function (id) {
			request.get("/qrt/service/template/" + id, renderer.renderCloneTemplate);
		}, 
		del : function (templateId) {
			request.get("/qrt/service/template/" + templateId + "/delete", renderer.refreshRender);
		},
		clear : function () {
			$("#templateUpdateForm").find("#templateId").val("");
			$("#templateUpdateForm").find("#textTemplateName").html("");
			$("#templateUpdateForm").find("#divColumns").html("");
		}, 
		generateJSONFromForm : function () {
			// fix the bug to generate duplicated columns.
			if ($("div.ui-dialog[aria-describedby=templateUpdateForm]").length > 1) {
				// more than 1 dialog created, unkown reason.
				$("div.ui-dialog[aria-describedby=templateUpdateForm]").each(function () {
					if ($(this).css("display") == "none") {
						// remove the hidden one.
						$(this).remove();
					}
				});
				
			}
			var result = {
				templateId: $("#templateUpdateForm #templateId").val(),
				name: $("#templateUpdateForm #textTemplateName").val(),
				status: $("#templateUpdateForm #templateStatus").prop("checked") ? "Y" : "N",
				createdBy: currentEmployeeNumber
			};
			
			var columns = [];

			$("#divColumns input.column_title").each(function () {
				var idx = $(this).attr("idx");
				var column = {
					columnLable: $(this).val(),
					expression: $(".column_expression[idx=" + idx + "]").attr("var"),
					columnIndex: idx,
					createdBy: currentEmployeeNumber
				};
				
				columns.push(column);
			});
			
			result.templateDetails = columns;
			return result;
		}
	};
	
	
	var reportDefinition = {
			init : function () {
				this._initTemplateUpdateForm();
				this.initTemplateList();
				this._initNewButton();
				this._initIssueComponent();
			},
			
			initTemplateList : function () {
				request.get("/qrt/service/template/allTemplate", renderer.renderTemplateList);
			}, 
			
			_initTemplateUpdateForm : function () {
				$("#templateUpdateForm").dialog({
					autoOpen: false,
					height: 500,
					width:600,
					modal: true,
					buttons: {
						"Save": function () {
							request.post("/qrt/service/template/saveTemplate", templateUpdateForm.generateJSONFromForm(), renderer.refreshRender);
							$(this).dialog("close");
						}, 
						Cancel: function () {
							$(this).dialog("close");
						}
					},
					close: function () {
						templateUpdateForm.clear();
						$(this).dialog("close");
					}
				});
			}, 
			
			_initNewButton : function () {
				$("#addNewTemplate").on("click", function () {
					templateUpdateForm.createNew();
				});
			}, 
			_initIssueComponent: function () {
				request.get("/qrt/service/issueComponent", renderer.issueComponentRender);
			}
	};

	reportDefinition.init();
});

function debugOnly () {
	var i = 1;
}

function destroy() {
	// destroy the components popup.
	// fix the bug to generate duplicated columns.
	if ($("div.ui-dialog[aria-describedby=templateUpdateForm]").length > 0) {
		// more than 1 dialog created, unkown reason.
		$("div.ui-dialog[aria-describedby=templateUpdateForm]").each(function () {
			$(this).remove();
		});
	}
}