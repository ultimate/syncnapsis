//@requires("Events")

shrinkAd = function()
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

Events.addEventListener(Events.ONRESIZE, shrinkAd, window);

var AD_TOGGLE_DIR = 0;
var AD_TOGGLE_STEP = 10;
var AD_TOGGLE_DELAY = 10;
var AD_SHOW_INTERVAL = 300000;
var AD_SHOW_DURATION = 10000;
var AD_SHOW_USER = true;
var AD_TOGGLE_IMG_SHOW = "images/toggle-show.png";
var AD_TOGGLE_IMG_HIDE = "images/toggle-hide.png";

toggleAd = function(user)
{
	if(layout_horizontal.elementSizes[0] == 0)
	{
		AD_TOGGLE_DIR = AD_TOGGLE_STEP;
		document.getElementById("ad_left_toggle").src = AD_TOGGLE_IMG_HIDE;
		document.getElementById("ad_right_toggle").src = AD_TOGGLE_IMG_HIDE;
		if(user)
			AD_SHOW_USER = true;
	}
	else if(layout_horizontal.elementSizes[0] == AD_WIDTH)
	{
		AD_TOGGLE_DIR = -AD_TOGGLE_STEP;
		document.getElementById("ad_left_toggle").src = AD_TOGGLE_IMG_SHOW;
		document.getElementById("ad_right_toggle").src = AD_TOGGLE_IMG_SHOW;
		if(user)
			AD_SHOW_USER = false;
	}
	layout_horizontal.elementSizes[0] += AD_TOGGLE_DIR;
	layout_horizontal.elementSizes[2] += AD_TOGGLE_DIR;
	layout_horizontal.fill();
	layout_ad_left.fill();
	layout_ad_right.fill();
	if(layout_horizontal.elementSizes[0] == 0)
		AD_TOGGLE_DIR = 0;
	else if(layout_horizontal.elementSizes[0] == AD_WIDTH)
		AD_TOGGLE_DIR = 0;
	if(AD_TOGGLE_DIR != 0)
		setTimeout(toggleAd, AD_TOGGLE_DELAY);
};

showAd = function()
{
	if(layout_horizontal.elementSizes[0] == 0)
		toggleAd(false);
	setTimeout(hideAd, AD_SHOW_DURATION);
};

hideAd = function()
{
	if(!AD_SHOW_USER && layout_horizontal.elementSizes[0] == AD_WIDTH)
		toggleAd(false);
};

setInterval(showAd, AD_SHOW_INTERVAL);
