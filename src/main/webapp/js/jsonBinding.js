var maindata={"employee":{"name":"eric","age":"31","sex":"male","height":"170"}};

var jsonBinding = new JsonBinding();

function JsonBinding() {
	var reg = /\#\{([^\{]*)\}/;
	
	this.binding = _binding;
		
	function _binding(data, container, recursion) {
		// binding data to this element first.
		bindingData2Element(data, container);
		
		if (isContainer(container) && recursion) {
			// this is a container, and we want to do a recursion in this container
			container.children().each(function (idx, ele) {
				_binding(data, $(ele), recursion);
			});
		} 
		
	};
	
	function bindingData2Element(data, ele) {
		if (ele.is("input")) {
			if (ele.attr("type").toLocaleLowerCase() == "text") {
				ele.val(renderText(data, ele.val()));
			} 
		} else if (ele.is("div") || ele.is("span") || ele.is("td")) {
			ele.contents().each(function () {
				if (this.nodeType == 3) {
					// this is a text node, we need render it.
					var t = renderText(data, this.data);
					this.data = t;
				}
			});
		}
	}

	function renderText(data, txt) {
		if (reg.test(txt)) {
			return txt.replace(reg, function () {
				// find an expression, let's replace it.
				var r;
				try {
					r = eval("_my_temp_data." + arguments[1]);
					_my_temp_data = null;
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
		var c = $(ele);
		return c.is("div") || c.is("form") || c.is("body") || c.is("table");
	}
};
