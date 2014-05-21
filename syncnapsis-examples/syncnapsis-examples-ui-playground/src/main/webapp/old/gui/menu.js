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
var menuAllMenus = new Array();

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
function createModelAndInsertMenu(menu, parent, template, hasRoot) {
	var rootNode = createMenuModel(menu, hasRoot);
	var elements = createStartmenu(rootNode, template);
	parent.appendChild(elements);
	return {"model": rootNode, "elements": elements};
}
function createMenuModel(menu, hasRoot) {
	if (menu == "") return;
	var div = document.createElement("div");
	div.innerHTML = menu;
	divs = new Array();
	divs = div.getElementsByTagName("div");
	
	if(!hasRoot) {
		startMenu = new c_menuItem();
		startMenu.div = document.createElement("div");
		startMenu.div.setAttribute("type", 'root', 0);
		
	}

	for(var i=0;i<divs.length;i++) {
		if(divs[i].getAttribute("type") == 'root' && hasRoot) {
			startMenu = new c_menuItem(divs[i]);
			continue;
		}
		var ids = divs[i].id.split(".");
		ids.shift();
		if(hasRoot)
			ids.shift();
			
		ids.pop();
		if(ids.length == 0)
			startMenu.addSubMenuItem(new c_menuItem(divs[i]));
		else
			startMenu.subMenu.getNode(ids).addSubMenuItem(new c_menuItem(divs[i]));
	}
	startMenu.searchString = div.getElementsByTagName('span')[0].getAttribute("value");
	return startMenu;	
}
function menuClickAction(menuItem) {
//		var newWin = createWindow("profile", "Profil", 200, 200, 400, 600, [0, 0, 0, 0], ['max', 'max', 'max', 'max'], true, true, true, true);
//			setContent(newWin.id, 'Loading');
//			sendRequest(menuItem.div.action+".html", RETURN_CONTENT, SEND_STRING, null, null, "setContent('" + newWin.id + "', {content});");
	executeAction(menuItem.div.action, menuItem.div.params, null);

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
									var action = result[i].div.getAttribute("action");
									var param = result[i].div.getAttribute("param");
									newItem = templ.getMenuNode(id,text,icon,topPos, rootMenu,action, param);
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
	menuAllMenus.push(rootMenu);
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
				var action = nodes[menuIndex].div.getAttribute("action");
				var param = nodes[menuIndex].div.getAttribute("param");
				newItem = templ.getMenuNode(id,text,icon,topPos, parentMenu, action, param);
				break;
			case "separator":
				newItem = templ.getMenuSeperator(topPos);
				break;
		}
		//alert(nodes[menuIndex].div.getAttribute("type") + "  " + newItem);
		if(parentMenu.middle.dropEnabled == true) {
			var tmp = parentMenu.middle.elementAddedEvent;
			parentMenu.middle.elementAddedEvent = null;
			parentMenu.middle.addElement(newItem, parentMenu.middle.size);
			parentMenu.middle.elementAddedEvent = tmp;
		} else {
			parentMenu.middle.appendChild(newItem);
		}
		if(nodes[menuIndex].div.getAttribute("quickLaunchEnabled") == "true") {
			var copyNodeTempl = new templateMenu(templateQuickLaunch);
			var id = nodes[menuIndex].div.getAttribute("id");
			var icon = nodes[menuIndex].div.getAttribute("imageURL");
			var text = nodes[menuIndex].div.getAttribute("text");
			var page = nodes[menuIndex].div.getAttribute("action");
			
			var copyNode = copyNodeTempl.getMenuNode(id,text,icon,page,0, null);
			enableDrag(newItem, templ.templ.menuNode.dropFieldIDs, templ.templ.menuNode.copyOnDrag, copyNode);
		}
		topPos += getHeight(newItem);
		if(nodes[menuIndex].subMenu != null) 
		{
			var newSub = templ.getSubMenu(newItem);
			newItem.appendChild(newSub);
			createSubMenus(nodes[menuIndex].subMenu.menuItems, newSub, templ);
		}
	}
	if(parentMenu.middle.dropEnabled != true)
		setHeight(parentMenu.middle, topPos);

}

function menu_highlight_on(node) {
	node.style.background = 'url('+imagePath+skinname+'/'+node.templ.id+'/highlight.png)';
}
function menu_highlight_off(node) {
	node.style.background = 'url('+imagePath+skinname+'/'+node.templ.id+'/'+node.templ.menuNode.background+')';
}
function menu_show(menu) {
	fadeIn(menu,100);
	info("fade in menu");
	menu.style.zIndex = ++lastZ;	
	menu.button.style.background = 'url('+imagePath+skinname+'/'+menu.templ.id+'/startbutton_pushed.png)';
}
function menu_hide(menu) {
	fadeOut(menu,100);
	menu.button.style.background = 'url('+imagePath+skinname+'/startmenu/startbutton_normal.png)';
}
function menu_showhide(menu) {

	if(menu.style.display == 'none')
		menu_show(menu);
	else
		menu_hide(menu);
}
function menubutton_highlight_on(menu) {
	if(menu.style.display == 'none')
		menu.button.style.background = 'url('+imagePath+skinname+'/startmenu/startbutton_mouseover.png)';
}
function menubutton_highlight_off(menu) {
	if(menu.style.display == 'none')
		menu.button.style.background = 'url('+imagePath+skinname+'/startmenu/startbutton_normal.png)';
}