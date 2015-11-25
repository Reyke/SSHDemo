var bindImg = function(){
	$("div#img-show > div > img").on("mouseover",function(){
		$(this).css("cursor","pointer");
	}).on("mouseout",function(){
		$(this).css("cursor","default");
	}).on("click",function(){
		$("#divPop,#imgPop").remove();
		var imgDiv = ''
		+ '<div id="divPop" style=""></div>'
		+ '<div id="imgPop" style="">'
		+ 	'<div style="position:absolute;height:15px;width:100%;z-index:99999;;overflow:hidden;">'
		+ 		'<img style="float:right;height:15px;display:none;vertical-align: middle" src="/qrt/image/close.png"/>'
		+ 	'</div>'
		+ 	'<div style="position:absolute;left:50%;top:50%;"><img style="padding:4px;height:{iHeight}px;width:{iWidth}px;display:none;" src="{imgSrc}"/></div>'
		+ '</div>';

		var theImage = new Image();
		theImage.src = $(this).attr("src");
		theImage.onload = function(){
			loading(true);
			var imageWidth = theImage.width;
			var imageHeight = theImage.height;
			var iWidth = -imageWidth;
			var iHeight = -imageHeight;
			if(imageWidth > 940){
				iWidth = -940;
				iHeight = imageHeight * iWidth / imageWidth;
				$("#imgPop div:last img:first").css("width",940);
			}
			if(imageHeight > 540){
				iHeight = -540;
				iWidth = imageWidth * iHeight / imageHeight;
				$("#imgPop div:last img:first").css("height",540).css("width",-iWidth).css("display","");
			}
			$("#imgPop div:last").css("margin",(iHeight/2-4) +"px 0px 0px " + (iWidth/2-4) +"px");
			loading(false);
		};
		// Get accurate measurements from that.
		var imageWidth = theImage.width;
		var imageHeight = theImage.height;
		var iWidth = -imageWidth;
		var iHeight = -imageHeight;
		if(imageWidth > 940){
			iWidth = -940;
			iHeight = imageHeight * iWidth / imageWidth;
			imgDiv = imgDiv.replace(/{iWidth}/g,940);
		}
		if(imageHeight > 540){
			iHeight = -540;
			iWidth = imageWidth * iHeight / imageHeight;
			imgDiv = imgDiv.replace(/{iHeight}/g,540);
		}
		imgDiv = imgDiv.replace(/{imgSrc}/g,$(this).attr("src"));
		$("body")
		.append(imgDiv)
		.find("#imgPop div:last")
		.css("margin",(iHeight/2-4) +"px 0px 0px " + (iWidth/2-4) +"px")
		.find("img")
		.css("width",-iWidth)
		.css("display","");
		$("#imgPop div:first").on("mouseover",function(){
			$(this)
			.css("cursor","hand")
			.children().css("cursor","pointer")
			.css("display","");
		}).on("mouseout",function(){
			$(this)
			.css("cursor","default")
			.children().css("cursor","default")
			.css("display","none");
		});
		$("#imgPop div:first").on("click",function(){
			$("#divPop,#imgPop").remove();
		});
	});
};