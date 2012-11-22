//@requires("Styles")
//@requires("Events")

var layout_horizontal;
var layout_vertical;
var layout_ad_left;
var layout_ad_right;
var AD_WIDTH = 170;
var AD_BORDER = 2;
var BAR_HEIGHT = 30;

init = function()
{
	layout_horizontal = new Styles.FillLayout([ "ad_left", "center", "ad_right" ], [ AD_WIDTH, null, AD_WIDTH ], Styles.layout.HORIZONTAL);
	layout_vertical = new Styles.FillLayout([ "bar_top", "content", "bar_bottom" ], [ BAR_HEIGHT, null, BAR_HEIGHT ], Styles.layout.VERTICAL);

	layout_ad_left = new Styles.FillLayout([ "ad_left_inner", "ad_left_border" ], [ null, AD_BORDER ], Styles.layout.HORIZONTAL);
	layout_ad_right = new Styles.FillLayout([ "ad_right_border", "ad_right_inner" ], [ AD_BORDER, null ], Styles.layout.HORIZONTAL);

	Events.fireEvent(window, Events.ONRESIZE);
};