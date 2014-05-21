/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
var menu;
var button;
	
	

function c_menu() {

	this.menuItems = new Array();
	this.parentNode;
	this.length = 0;
	
	// fügt dem menu ein item hinzu
	// wird nur von c_menuItem aufgerufen
	this.add = function (mItem) {
		var ids = mItem.div.id.split(".");
		this.length++;		
		this.menuItems[ids[ids.length-1]] = mItem;
	}
	
	// liefert den menüpunkt mit dem entsprechenden indices
	this.getNode = function(indexes) {
		if(indexes.length == 1) return this.menuItems[indexes[0]];
		index = indexes.shift();

		return this.menuItems[index].subMenu.getNode(indexes);
	}
	
}
function c_menuItem(div,template) {
	this.subMenu = null;
	this.div = div;
	this.template = template;
	this.searchString;
	
	this.display = function() {
		
	}
	// fügt dem entsprechenden submenu ein item hinzu
	this.addSubMenuItem = function(mItem) {
		if(this.subMenu == null) {
			this.subMenu = new c_menu();
			this.subMenu.parentNode = this;
		}
		this.subMenu.add(mItem);
	}

	this.searchRec = function(what, result) {
		if(this.div.getAttribute("text") != null && this.div.getAttribute("text").substr(0,what.length) == what) result.push(this);				
		if(this.subMenu != null) {
			for(var menuIndex in this.subMenu.menuItems) {			
				this.subMenu.menuItems[menuIndex].searchRec(what, result);
			}
		}
	}
	
	this.search = function(what) {
		var result = new Array();
		this.searchRec(what,result);
		return result;
	}
}
	
var startMenu;	
var divs;	

function createModelAndInsertMenu(menu, parent, template) {
	var rootNode = createMenuModel(menu);
	parent.appendChild(createStartmenu(rootNode, template));
	return rootNode;
}


function createMenuModel(menu) {
	if (menu == "") return;
	var div = document.createElement("div");
	div.innerHTML = menu;
	divs = new Array();
	divs = div.getElementsByTagName("div");
	
	for(var i=0;i<divs.length;i++) {
		if(divs[i].getAttribute("type") == 'root') {
			startMenu = new c_menuItem(divs[i]);
			continue;
		}
		var ids = divs[i].id.split(".");
		ids.shift();
		ids.shift();
		ids.pop();
		if(ids.length == 0)
			startMenu.addSubMenuItem(new c_menuItem(divs[i]));
		else
			startMenu.subMenu.getNode(ids).addSubMenuItem(new c_menuItem(divs[i]));
	}
	startMenu.searchString = div.getElementsByTagName('span')[0].value;
	return startMenu;	
}
/*
function createSubMenu(parentNode) {
	var newSubMenu = document.createElement("div");	
	newSubMenu.id 						= parentNode.div.id;
	newSubMenu.style.height 			= parentNode.subMenu.length*startmenuItemHeight+"px";
	newSubmenu.style.width  			= "194px";
	newSubMenu.style.top				= "0px";
	newSubMenu.style.left   			= "194px";
	newSubMenu.style.position			= "absolute";
	newSubMenu.style.backgroundColor 	= "#ff0000";
	return newSubMenu;
}
*/
function menuClickAction(menuItem) {
		var newWin = createWindow("profile", "Profil", 200, 200, 400, 600, [0, 0, 0, 0], ['max', 'max', 'max', 'max'], true, true, true, true);
			setContent(newWin.id, 'Loading');
			sendRequest(menuItem.div.action+".html", RETURN_CONTENT, SEND_STRING, null, null, "setContent('" + newWin.id + "', {content});");

}
function createStartmenu(rootNode, template)
{
	if(	rootNode.div.getAttribute("type") != "root" ) return null;
	
	var templ = new templateMenu(template);
	
	var rootMenu = templ.getRootMenu();
	if(template.root.searchable) {
		rootMenu.input.value = rootNode.searchString;
		addListener(rootMenu.input, "focus", function() {rootMenu.input.value = "";});
		addListener(rootMenu.input, "keyup", function() {
							if(rootMenu.input.value != "") {
								while(rootMenu.middle.hasChildNodes()) rootMenu.middle.removeChild(rootMenu.middle.firstChild);
								var result = rootNode.search(rootMenu.input.value);
								var topPos = 0;
								for(var i=0;i<result.length;i++) {

									var id = result[i].div.getAttribute("id");
									var icon = result[i].div.getAttribute("imageURL");
									var text = result[i].div.getAttribute("text");
									var page = result[i].div.getAttribute("link");
									newItem = templ.getMenuNode(id,text,icon,page,topPos, rootMenu);
									topPos += getHeight(newItem);									
									if(topPos > getHeight(rootMenu.middle)) break;									
									rootMenu.middle.appendChild(newItem);
								}
							}
							else {
								while(rootMenu.middle.hasChildNodes()) rootMenu.middle.removeChild(rootMenu.middle.firstChild);
								createSubMenus(rootNode.subMenu.menuItems, rootMenu, templ);						
							}
						  });
	}
	createSubMenus(rootNode.subMenu.menuItems, rootMenu, templ);
	
	if(template.rootButton.show) {
		var rootButton = templ.getRootButton(rootMenu)
		return rootButton;
	}
	return rootMenu;
}
function createSubMenus(nodes, parentMenu, templ) 
{
	var topPos = 0;

	for(var menuIndex in nodes)
	{
		var newItem;
		switch (nodes[menuIndex].div.getAttribute("type"))
		{
			case "node":
				var id = nodes[menuIndex].div.getAttribute("id");
				var icon = nodes[menuIndex].div.getAttribute("imageURL");
				var text = nodes[menuIndex].div.getAttribute("text");
				var page = nodes[menuIndex].div.getAttribute("link");
				newItem = templ.getMenuNode(id,text,icon,page,topPos, parentMenu);
				break;
			case "separator":
				newItem = templ.getMenuSeperator(topPos);
				break;
		}
		parentMenu.middle.appendChild(newItem);
		topPos += getHeight(newItem);
		if(nodes[menuIndex].subMenu != null) 
		{
			var newSub = templ.getSubMenu(newItem);
			newItem.appendChild(newSub);
			createSubMenus(nodes[menuIndex].subMenu.menuItems, newSub, templ);
		}
	}
		setHeight(parentMenu.middle, topPos);

}

function menu_highlight_on(node) {
	node.style.background = 'url('+imagePath+skinname+'/'+node.templ.id+'/hightlight.png)';
}
function menu_highlight_off(node) {
	node.style.background = '';
}
function menu_show(menu) {
	show(menu);
	menu.style.zIndex = ++lastZ;	
//	menu.button.src = imagePath+skinname+'/'+menu.templ.id+'/startbutton_pushed.png';
}
function menu_hide(menu) {
	hide(menu);
//	menu.button.src = imagePath+skinname+'/startmenu/startbutton_normal.png';
}
function menu_showhide(menu) {

	if(menu.style.display == 'none')
		menu_show(menu);
	else
		menu_hide(menu);
}
function startbutton_highlight_on() {
	if(menu.style.display == 'none')
		button.src = imagePath+skinname+'/startmenu/startbutton_mouseover.png';
}
function startbutton_highlight_off() {
	if(menu.style.display == 'none')
		button.src = imagePath+skinname+'/startmenu/startbutton_normal.png';
}

var startmenu_dragdrop_isDown = false;
var startmenu_dragdrop_comp = null;

function startmenu_dragdrop_down(comp, e) {
	if(getMouseButton(e) == 1) {
		startmenu_dragdrop_isDown = true;
		startmenu_dragdrop_comp = comp;

	}
}


var startmenu_dragdrop_buttonCreated = false;
var buttonID = 1;
var startmenu_dragdrop_button = null;
var startmenu_dragdrop_readytodrop = false;

function startmenu_dragdrop_up() {
	if((startmenu_dragdrop_button != null)) {
		if(startmenu_dragdrop_readytodrop == false) {
			document.body.removeChild(startmenu_dragdrop_button);
			startmenu_dragdrop_button = null;
		}
		else {
			addToQuicklaunch(startmenu_dragdrop_button);
		}
	}

	startmenu_dragdrop_buttonCreated = false;
	startmenu_dragdrop_isDown = false;
	startmenu_dragdrop_comp = null;
}


function startmenu_dragdrop_move(e) {

	if(startmenu_dragdrop_isDown == true) {
		if(startmenu_dragdrop_buttonCreated == false) {
			startmenu_dragdrop_buttonCreated = true;
			var newButton;
  			newButton					= document.createElement('div');
  			newButton.id 				= 'dragdropbutton'+buttonID++;
  			newButton.style.position 	= 'absolute';
  			newButton.style.top 		= getMouseY(e)-24+'px';
  			newButton.style.left 		= getMouseX(e)-24+'px';  
  			newButton.style.width 		= '48px';
  			newButton.style.height 		= '48px';
		  	newButton.style.background	= 'url('+imagePath+skinname+'/startmenu/button_normal.png)';
			newButton.style.zIndex		= lastZ++;
			newButton.nextButton		= null;
			newButton.prevButton		= null;
			
			newButton.oncontextmenu 	= function() {return false;};
			newButton.onclick  = function() {createWindow(100,100,200,400,new Array(0,0,100,100), new Array('max', 'max', 400,400));};
			addListener(newButton, "mousedown", quicklaunch_dragdrop_down);
			addListener(newButton, "mouseup", contextmenu_show);
			var newImage				= document.createElement('div');
			newImage.id					= newButton.id+'.icon';
			newImage.style.position		= 'absolute';
  			newImage.style.width 		= '30px';
  			newImage.style.height 		= '30px';
			newImage.style.top			= '8px';
			newImage.style.left			= '8px';			
			var src = document.getElementById(startmenu_dragdrop_comp.id+'.icon').style.backgroundImage;
			newImage.style.backgroundImage	= 'url('+imagePath+skinname+'/icons/30'+getFilename(src);			
			startmenu_dragdrop_button = newButton;
			newButton.appendChild(newImage);
			document.body.appendChild(newButton);
		}
		else
			var x = getMouseX(e)-24;
			var y = getMouseY(e);
			if(quicklaunch_movebutton == true) {
				quicklaunch_contextmenu_button = startmenu_dragdrop_button;
				quicklaunch_remove_button();
				quicklaunch_movebutton = false;
			}
			if(x) {
				if((x > 2) && (x < 42) && (y > getY(document.getElementById("quicklaunch"))-15) && (y <getY(document.getElementById("quicklaunch"))+15+getHeight(document.getElementById("quicklaunch")))) {
					x = 22;
					startmenu_dragdrop_readytodrop = true;
				}
				else 
					startmenu_dragdrop_readytodrop = false;
				startmenu_dragdrop_button.style.top 		= getMouseY(e)-24+'px';
				startmenu_dragdrop_button.style.left 		= x+'px';  
			}
	}
	else {
		startmenu_dragdrop_up();
	}
}

function context_highlight_on(obj) {
		obj.style.backgroundImage = 'url('+imagePath+skinname+'/contextmenu/menupunkt_highlight.png)';
}
function context_highlight_off(obj) {
		obj.style.backgroundImage = 'url('+imagePath+skinname+'/contextmenu/menupunkt_normal.png)';
}




function key(e) {
	var keynum;
	if(window.event) 
	{
		keynum = e.keyCode;
	}
	else if(e.which)
	{
		keynum = e.which;
	}
	if(keynum == 19) startmenu_showhide();
}

function getFilename(url)  {
	var lastPos = url.lastIndexOf('\\');
	if(lastPos == -1)
		lastPos = url.lastIndexOf('/');
	if(lastPos == -1)
		alert('Keine URL');
	return  url.substr(lastPos+1);
}
function getPath(url)  {
	var lastPos = url.lastIndexOf('\\');
	if(lastPos == -1)
		lastPos = url.lastIndexOf('/');
	if(lastPos == -1)
		alert('Keine URL');
	return  url.substr(0,lastPos+1);
}



function everywhere_click() {
	 hide(document.getElementById("quicklaunch_context"));
	 //startmenu_hide();
}
