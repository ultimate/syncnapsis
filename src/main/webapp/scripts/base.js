//@requires("Styles")
//@requires("Events")

var layout_horizontal;
var layout_vertical;
var layout_ad_left;
var layout_ad_right;
var layout_bar_top;
var layout_bar_bottom;
var AD_WIDTH = 170;
var AD_BORDER = 2;
var BAR_HEIGHT = 24;
var BAR_OUTER_WIDTH = 300;

init = function()
{
	layout_horizontal = new Styles.FillLayout([ "ad_left", "center", "ad_right" ], [ AD_WIDTH, null, AD_WIDTH ], Styles.layout.HORIZONTAL);
	layout_vertical = new Styles.FillLayout([ "bar_top", "content", "bar_bottom" ], [ BAR_HEIGHT, null, BAR_HEIGHT ], Styles.layout.VERTICAL);

	layout_ad_left = new Styles.FillLayout([ "ad_left_inner", "ad_left_border" ], [ null, AD_BORDER ], Styles.layout.HORIZONTAL);
	layout_ad_right = new Styles.FillLayout([ "ad_right_border", "ad_right_inner" ], [ AD_BORDER, null ], Styles.layout.HORIZONTAL);

	layout_bar_top = new Styles.FillLayout([ "bar_top_left", "bar_top_center", "bar_top_right" ], [ BAR_OUTER_WIDTH, null, BAR_OUTER_WIDTH ], Styles.layout.HORIZONTAL);
	layout_bar_bottom = new Styles.FillLayout([ "bar_bottom_left", "bar_bottom_center", "bar_bottom_right" ], [ BAR_OUTER_WIDTH, null, BAR_OUTER_WIDTH ], Styles.layout.HORIZONTAL);
	
	Events.fireEvent(window, Events.ONRESIZE);
	
	Locale.update();
};