//@requires("Events")
//@requires("UI")

var Ad = {};

Ad.shrink = function()
{
	// reset height
	document.getElementById("ad_left_img").style.height = "";
	document.getElementById("ad_right_img").style.height = "";

	// get target heights
	var available = document.getElementById("ad_left").offsetHeight;
	var original = document.getElementById("ad_left_img").height;

	// get the minimum
	var height = Math.min(available, original);

	// set the new height
	document.getElementById("ad_left_img").style.height = height + "px";
	document.getElementById("ad_right_img").style.height = height + "px";
};

Events.addEventListener(Events.ONRESIZE, Events.wrapEventHandler(Ad, Ad.shrink), window);

Ad.TOGGLE_DIR = 0;
Ad.TOGGLE_STEP = 10;
Ad.TOGGLE_DELAY = 10;
Ad.SHOW_INTERVAL = 300000;
Ad.SHOW_DURATION = 10000;
Ad.SHOW_USER = true;
Ad.TOGGLE_IMG_SHOW = "images/toggle-show.png";
Ad.TOGGLE_IMG_HIDE = "images/toggle-hide.png";

Ad.toggle = function(user)
{
	if(UI.layout_horizontal.elementSizes[0] == 0)
	{
		Ad.TOGGLE_DIR = Ad.TOGGLE_STEP;
		document.getElementById("ad_left_toggle").src = Ad.TOGGLE_IMG_HIDE;
		document.getElementById("ad_right_toggle").src = Ad.TOGGLE_IMG_HIDE;
		if(user)
			Ad.SHOW_USER = true;
	}
	else if(UI.layout_horizontal.elementSizes[0] == UI.constants.AD_WIDTH)
	{
		Ad.TOGGLE_DIR = -Ad.TOGGLE_STEP;
		document.getElementById("ad_left_toggle").src = Ad.TOGGLE_IMG_SHOW;
		document.getElementById("ad_right_toggle").src = Ad.TOGGLE_IMG_SHOW;
		if(user)
			Ad.SHOW_USER = false;
	}
	UI.layout_horizontal.elementSizes[0] += Ad.TOGGLE_DIR;
	UI.layout_horizontal.elementSizes[2] += Ad.TOGGLE_DIR;
	UI.layout_horizontal.fill();
	UI.layout_ad_left.fill();
	UI.layout_ad_right.fill();
	if(UI.layout_horizontal.elementSizes[0] == 0)
		Ad.TOGGLE_DIR = 0;
	else if(UI.layout_horizontal.elementSizes[0] == UI.constants.AD_WIDTH)
		Ad.TOGGLE_DIR = 0;
	if(Ad.TOGGLE_DIR != 0)
		setTimeout("Ad.toggle()", Ad.TOGGLE_DELAY);
};

Ad.show = function()
{
	if(UI.layout_horizontal.elementSizes[0] == 0)
		Ad.toggle(false);
	setTimeout("Ad.hide()", Ad.SHOW_DURATION);
};

Ad.hide = function()
{
	if(!Ad.SHOW_USER && UI.layout_horizontal.elementSizes[0] == UI.constants.AD_WIDTH)
		Ad.toggle(false);
};

setInterval("Ad.show()", Ad.SHOW_INTERVAL);
