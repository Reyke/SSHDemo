function ColorTable() {
	this.evenRowStyle = {"background-color" : "#F0F0F0"};
	this.oddRowStyle = {"background-color" : "#FFFFFF"};
	this.hoverRowStyle = {"background-color" : "#FFEEDD"};
	
	var _this = this;
	
	this.bind = function (tb) {		
		if (!tb.is("table")) {
			// not a table, return
			return;
		}
		
		// add event to table rows.
		if (tb.attr("colorBound")) {
			// bound before, just bound once for a table, return;
			return;
		}
		
		var i = 0;
		tb.find("tr").each(function () {
			if ($(this).has("th").length > 0) {
				// this is the header line, let's set the background color for this table
				$(this).addClass("ui-widget-header");
			} else {
				// format lines
				var _orginalStyle;
				if (i % 2 > 0) {
					$(this).css(_this.evenRowStyle);
					_orginalStyle = _this.evenRowStyle;
				} else {
					$(this).css(_this.oddRowStyle);
					_orginalStyle = _this.oddRowStyle;
				}
				
				// add onmouseenter event listener
				$(this).mouseenter (function () {
					$(this).css(_this.hoverRowStyle);
				}).mouseleave (function () {
					$(this).css(_orginalStyle);
				});
				i++;
			}
		});
		tb.attr("colorBound", true);
	};
};