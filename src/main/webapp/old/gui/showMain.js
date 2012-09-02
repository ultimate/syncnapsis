function createPagePart(template) {
  template = template.replace(/{imagepath}/g,imagePath);
  template = template.replace(/{skinname}/g,skinname);
  var part			  = document.createElement('div');
  part.innerHTML = template;
  return part;
	
}

var hallo;
var startmenuModel;
function showMain() {
	elem = document.getElementsByTagName('body')[0];
//	addListener(elem, "dragstart", function() { return false; });
//	addListener(elem, "selectstart", function() { return false; });
//	addListener(elem, "mouseup", startmenu_dragdrop_up);
//	addListener(elem, "mousemove", startmenu_dragdrop_move);
//	document.getElementsByTagName('body')[0].ondragstart = function() { return false;}
//	document.getElementsByTagName('body')[0].onselectstart = function() { return false;}
//	document.getElementsByTagName('body')[0].onmouseup = startmenu_dragdrop_up;
//	document.getElementsByTagName('body')[0].mousemove = startmenu_dragdrop_move;	
	document.getElementById("backgroundframeContainer").appendChild(createPagePart(templateBackgroundFrame));
//	document.getElementsByTagName('body')[0].appendChild(createPagePart(templateQuicklaunch));
//	document.getElementsByTagName('body')[0].appendChild(createPagePart(templateStartMenuRoot));
//	document.getElementsByTagName('body')[0].appendChild(createPagePart(templateQuickLaunchContext));	
//	document.getElementsByTagName('body')[0].appendChild(createPagePart(templateStartButton));	
	
	var template = new templateMenu(templateStartMenu);
//	createStartmenu(rootNode, elementToAddTo, template)
//	document.getElementsByTagName('body')[0].appendChild(template.getRoot());	
	
//	disableSelect(document.getElementById("startbutton"));
	document.oncontextmenu = function() {return false;};
	
//	menu = document.getElementById("startmenu");
//	button = document.getElementById("startbutton");
	document.body.style.overflow = "hidden";	

// TODO noch aendern in sendActionRequest
	sendPlainRequest('mainMenu.html', 	 null, TYPE_RETURN_CONTENT, function(content, success) {
																var tmp = createModelAndInsertMenu(content,document.getElementsByTagName("body")[0], templateStartMenu, true);
																startmenuModel = tmp.model;
																startMenuElements = tmp.elements;
																//disableSelectForAll(startMenuElements);
																info("Startmenu geladen");});
	sendPlainRequest('quickLaunch.html', null, TYPE_RETURN_CONTENT, function(content, success) {
																var tmp = createModelAndInsertMenu(content,document.getElementsByTagName("body")[0], templateQuickLaunch, false);
																quickLaunchModel = tmp.model;
																quickLaunchElements = tmp.elements;
																//disableSelectForAll(quickLaunchElements);
																info("QuickLaunch geladen");});
	
}


