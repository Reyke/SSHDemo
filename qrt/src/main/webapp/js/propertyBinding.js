var propertyBinding = new PropertyBinding();

function PropertyBinding() {
	var reg = /\#\{ApplicationResource\.([^\{]*)\}/;
	var attributesForInput = ["value", "title", "placeholder"];
	var I_18_N_PRE = "_i18n_";
	this.binding = _binding;
		
	function _binding(container, recursion) {
		// binding data to this element first.
		bindingData2Element(container);
		
		if (isContainer(container) && recursion) {
			// this is a container, and we want to do a recursion in this container
			container.children().each(function (idx, ele) {
				_binding($(ele), recursion);
			});
		} 
		
	};
	
	function hasAttribute(ele, prop) {
		return typeof(ele.attr(prop)) != "undefined";
	}
	
	function i18nAlways(ele) {
		if (hasAttribute(ele, "i18n-always")) {
			return ele.attr("i18n-always");
		}
		return false;
	}
	
	function i18nIgnore (ele) {
		if (hasAttribute(ele, "i18n-ignore")) {
			return ele.attr("i18n-ignore");
		}
		return false;
	}
	
	function bindingData2Element(ele) {
		if (i18nIgnore(ele)) {
			return;
		}
		if (ele.is("input")) {
			var elemType = ele.attr("type").toLocaleLowerCase();
			if (elemType == "text" || elemType == "button" 
				|| elemType == "submit" || elemType == "reset"
				|| elemType == "password") {
				// since we need to reload this page, let's backup all these attributes first.
				// we'll check the i18n attributes first
				$.each(attributesForInput, function (i, prop) {
					if (i18nAlways(ele)) {
						// i18n always.
						if (hasAttribute(ele, prop)) { 
							// this attribute was set.
							// set this attribute to i18n.
							ele.attr(I_18_N_PRE + prop, ele.attr(prop));	
						}						
					} else if (!hasAttribute(ele, I_18_N_PRE + prop)) {
						// i18n not set.
						if (hasAttribute(ele, prop)) { 
							// this attribute was set.
							// set this attribute to i18n.
							ele.attr(I_18_N_PRE + prop, ele.attr(prop));	
						}
					}
					// now we have i18n property set, let's set the value back.
					ele.attr(prop, renderText(ele.attr(I_18_N_PRE + prop)));
				});
			} 
		} else if (ele.is("div") || ele.is("span") || ele.is("td") || ele.is("th") || ele.is("label") 
				|| ele.is("a")||ele.is("option")||ele.is("li")) {
			// handle title attribute
			if (hasAttribute(ele, "title")) {
				if (i18nAlways(ele)) {
					ele.attr(I_18_N_PRE + "title", ele.attr("title"));
				} else if(!hasAttribute(ele, I_18_N_PRE + "title")) {
					ele.attr(I_18_N_PRE + "title", ele.attr("title"));
				}
				ele.attr("title", renderText(ele.attr(I_18_N_PRE + "title")));
			}
			
			// backup the contents first.
			if (ele.contents().length > 1) {
				// more than 1 nodes in this element, let's surround a span element for each text node.
				ele.contents().each(function () {
					if (this.nodeType == 3){
						if (typeof(this.data) != 'undefined') {
							if (this.data.replace(/(^\s*)|(\s*$)/g, "") != '') {
								// a text node, let's surround a span for it.
								$(this).replaceWith("<span " + I_18_N_PRE + "content='" + this.data + "'>" + renderText(this.data) + "</span>");
							}
							
						}
					}
				});
			} else if (ele.contents().length > 0){
				// only one node, backup the data.
				var d = ele.contents().get(0);
				
				if (d.nodeType == 3) {
					if (i18nAlways(ele)) {
						ele.attr(I_18_N_PRE + "content", d.data);
					} else if (!hasAttribute(ele, I_18_N_PRE + "content")) {
						ele.attr(I_18_N_PRE + "content", d.data);
					}
					// d.data = renderText(ele.attr(I_18_N_PRE + "content"));
					ele.text(renderText(ele.attr(I_18_N_PRE + "content")));
				}
			} 
		}
	}

	function renderText(txt) {
		if (reg.test(txt)) {
			return txt.replace(reg, function () {
				// find an expression, let's replace it.
				var r;
				try {
					r = $.i18n.prop(arguments[1]);
				} catch (e) {
					r = arguments[0];
				}
				return r;
			});
		} else {
			return txt;
		}
	}

	function isContainer(ele) {
		var c = ele;
		return c.is("div") || c.is("form") || c.is("button") || c.is("body") 
					|| c.is("table") || c.is("thead") || c.is("tbody") || c.is("tr") 
					|| c.is("td") || c.is("option") ||ele.is("select") ||ele.is("label") || ele.is("ul") || ele.is("ol");
	}
};
