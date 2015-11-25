PlaceholderSupporter = function () {
	_html5Support = function () {return (typeof(Worker) != "undefined")};

	this.init = _init;
	
	function _init() {
		if (!_html5Support()) {
			// html5 not supported, we need implement placeholder ourselvies.
			_initPlaceHolder();
		}
	}

	function _initPlaceHolder () {
		$("input[type='text'], [type='password']").each(function () {
			if (this.placeholder) {
				// create a label for this text box.
				var labelName = "_label_4_" + this.id;
				$(this).parent().append("<label for='" + this.id + "' id='" + labelName + "'>" + this.placeholder + "</label>");
				// set up CSS for the label created to display on the correct place.
				$("#" + labelName).css({
					"opacity":".3",
					"cursor":"text",
					"position":"absolute"
				});
				var p = $(this).offset();
				$("#" + labelName).offset({left: p.left + 40, top: p.top + 10});
				$(this).bind("focus", function () {
					// call the original handler.
					this.focus();
					// hide the label.
					$("#" + labelName).css("visibility", "hidden");
				});
				$(this).bind("blur", function () {
					// call the original handler.
					this.blur();
					// hide the label.
					if ($(this).val() == "") {
						$("#" + labelName).css("visibility", "visible");
					}
				});
			}
		});
	};
};