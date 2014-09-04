/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

var templateStartMenu = {
					"id":					"startmenu",
					"root":
					{
						"top": 					35,
						"left":					10,
						"width":				232,
						"topheight": 			21,
						"bottomheight":			46,
						"searchable":			true,
						"searchtop":			14,
						"searchleft":			30,
						"searchcolor":			"#999999",
						"searchfontsize":		12,
						"dropfieldID":			null,
						"layout":				null,
						"multiline":			null,
						"autoResize":			null,
						"elementAddedEvent":	null,
						"elementRemovedEvent":	null,
						"elementMovedEvent":	null,						
						"smoothInsert":			null
						
					},
					"menuNode": 
					{
						"width":			193,
						"height":			44,
						"left":				20,
						"dropFieldIDs": new Array("quicklaunch_dropfield"),						
						"copyOnDrag": true,	
						"copyNodeWithTemplate": templateQuickLaunch,						
						"icon": 
						{
							"size": 36,
							"width": 36,
							"height": 36,
							"left": 4,
							"top": 4
						},
						"label":
						{
							"top": 12,
							"left": 44,
							"visible": true
						},
						"seperator":
						{
							"height": 11,
							"linewidth": 1,
							"linecolor": "#000000"
						}
						
					},
					"rootButton":
					{
						"top": 0,
						"left": 70,
						"width": 48,
						"height": 45,
						"show": true
					},
					"submenu":
					{
						"top": 0,
						"left": 232,
						"width": 232,
						"topheight": 21,
						"bottomheight": 14
					}
				};

var templateQuickLaunch = {
					"id":					"quicklaunch",
					"root":
					{
						"top": 					60,
						"left":					20,
						"width":				52,
						"topheight": 			13,
						"bottomheight":			5,
						"searchable":			false,
						"searchtop":			14,
						"searchleft":			30,
						"searchcolor":			"#999999",
						"searchfontsize":		12,
						"dropfieldID":			"quicklaunch_dropfield",
						"layout":				new verticalLayout(),
						"multiline":			false,
						"autoResize":			true,
						"elementAddedEvent":	quicklaunchAdded,
						"elementRemovedEvent":	quicklaunchRemoved,
						"elementMovedEvent":	quicklaunchMoved,						
						"smoothInsert":			true					
					},
					"menuNode": 
					{
						"width":			48,
						"height":			48,
						"left":				2,
						"background": "button_normal.png",
						"dropFieldIDs": new Array("quicklaunch_dropfield"),
						"copyOnDrag": false,
						"copyNodeWithTemplate": templateQuickLaunch,
						"icon": 
						{
							"width": 30,
							"size": 30,
							"height": 30,
							"left": 8,
							"top": 8
						},
						"label":
						{
							"top": 12,
							"left": 44,
							"visible": false
						},
						"seperator":
						{
							"height": 11,
							"linewidth": 1,
							"linecolor": "#000000"
						}
						
					},
					"rootButton":
					{
						"top": 0,
						"left": 70,
						"width": 48,
						"height": 45,
						"show": false
					},
					"submenu":
					{
						"top": 0,
						"left": 55,
						"width": 52,
						"topheight": 13,
						"bottomheight": 5
					}
				};

							 
function templateMenu(templ) {
	
	this.templ = templ;
	this.getRootMenu = function() {
		
		var root = document.createElement("div");
			root.id = this.templ.id;
			root.style.position = "absolute";
			setX(root,this.templ.root.left);
			setY(root,this.templ.root.top);
			setWidth(root,this.templ.root.width);
			if(this.templ.rootButton.show)
				fadeOut(root,1);
		
		var rootTop = document.createElement("div");
			rootTop.style.position = "relative";
			setWidth(rootTop,this.templ.root.width);
			setHeight(rootTop, this.templ.root.topheight);
			rootTop.style.background = "url("+imagePath+skinname+"/"+this.templ.id+"/top.png)";
			root.appendChild(rootTop);

		var rootMiddle = document.createElement("div");
			rootMiddle.id = "rootMiddle";		
			rootMiddle.style.position = "relative";
			setWidth(rootMiddle,this.templ.root.width);
			rootMiddle.style.background = "url("+imagePath+skinname+"/"+this.templ.id+"/middle.png)";
			root.middle = rootMiddle;			
			root.appendChild(rootMiddle);			
			
			if(this.templ.root.dropfieldID != null) {
				enableDrop(rootMiddle, this.templ.root.dropfieldID, this.templ.root.layout, this.templ.root.multiline, this.templ.root.autoResize, this.templ.root.elementAddedEvent, this.templ.root.elementRemovedEvent, this.templ.root.elementMovedEvent, this.templ.root.smoothInsert);
			}

		var rootBottom = document.createElement("div");
			rootBottom.style.position = "relative";
			setWidth(rootBottom,templ.root.width);
			setHeight(rootBottom, this.templ.root.bottomheight);
			rootBottom.style.background = "url("+imagePath+skinname+"/"+this.templ.id+"/bottom.png)";
			root.appendChild(rootBottom);			
			
			
		if(templ.root.searchable) {
			var rootInput = document.createElement("input");
			rootInput.style.position = "relative";
			setX(rootInput,this.templ.root.searchleft);
			setY(rootInput,this.templ.root.searchtop);			
			rootInput.style.fontSize = this.templ.root.searchfontsize+"px";
			rootInput.style.color = this.templ.root.searchcolor;
			rootInput.style.border = "0px";
			rootBottom.appendChild(rootInput);			
			root.input = rootInput;
		}
		

		root.templ = this.templ;
		return root;
	}
	
	this.getMenuNode = function (id, text, icon, topPos, root, action, param) 
	{
			var newItem = document.createElement("div");	
			var image = document.createElement("div");	
			var label = document.createElement("div");				
			
			newItem.id = id;
			newItem.style.position = 'absolute';

			
			setWidth(newItem, this.templ.menuNode.width);
			setHeight(newItem, this.templ.menuNode.height);
			setX(newItem, this.templ.menuNode.left);
			setY(newItem, topPos);
			newItem.style.backgroundImage = 'url('+imagePath+skinname+"/"+this.templ.id+"/"+this.templ.menuNode.background+')';

			newItem.style.cursor = "default";
			addListener(newItem, "mouseover", function() { menu_highlight_on(newItem); });
			addListener(newItem, "mouseout", function() {  menu_highlight_off(newItem); });
			if(action != null) {
				addListener(newItem, "click", function() { executeAction(action, param,null); if(root.button) menu_hide(root);});
			}
			image.style.backgroundImage = 'url('+imagePath+skinname+'/icons/'+this.templ.menuNode.icon.size+"/"+icon+')';
			image.style.position = "absolute";
			image.style.backgroundRepeat = "no-repeat";
			setHeight(image, this.templ.menuNode.icon.height);
			setWidth(image,this.templ.menuNode.icon.width);
			setX(image,this.templ.menuNode.icon.left);
			setY(image,this.templ.menuNode.icon.top);
			newItem.appendChild(image);
			
			if(this.templ.menuNode.label.visible) {
				label.appendChild(document.createTextNode(text));
				label.style.position = "absolute";
				setX(label, this.templ.menuNode.label.left);
				setY(label, this.templ.menuNode.label.top);
				setHeight(label, getHeight(newItem));
				setWidth(label, getWidth(newItem)-getWidth(image));
				newItem.appendChild(label);			
			}
	
			newItem.templ = this.templ;
			return newItem;
	}

	this.getMenuSeperator = function(topPos) 
	{
			var newItem = document.createElement("div");	
			newItem.style.position = 'absolute';
			
			setWidth(newItem, this.templ.menuNode.width);
			setHeight(newItem, this.templ.menuNode.seperator.height);			
			setX(newItem, this.templ.menuNode.left);
			setY(newItem, topPos);
			
			var line = document.createElement("div");
			setHeight(line, this.templ.menuNode.seperator.linewidth);
			setWidth(line, this.templ.menuNode.width);
			line.style.backgroundColor = this.templ.menuNode.seperator.linecolor;
			line.style.position = "relative";
			setY(line, Math.round((this.templ.menuNode.seperator.height-this.templ.menuNode.seperator.linewidth)/2));
			newItem.appendChild(line);
			
			return newItem;
	}

	this.getRootButton = function(root) 
	{
		var rootButton = document.createElement("div");
		rootButton.style.position = "absolute";
		setX(rootButton, this.templ.rootButton.left);
		setY(rootButton, this.templ.rootButton.top);
		setHeight(rootButton, this.templ.rootButton.height);
		setWidth(rootButton, this.templ.rootButton.width);
		rootButton.templ = this.templ;
		rootButton.style.background = "url("+imagePath+skinname+"/"+this.templ.id+"/startbutton_normal.png)";
		addListener(rootButton, "mouseover", function() { menubutton_highlight_on(root);});
		addListener(rootButton, "mouseout", function() { menubutton_highlight_on(root);});
		addListener(rootButton, "click", function(ev) {if(getSrcElement(ev) == rootButton) menu_showhide(root); });
		root.button = rootButton;		
		rootButton.appendChild(root);

		return rootButton;

	}
	
	this.getSubMenu = function(parentItem) {
		var parentIsRoot = false;
		if(parentItem.parentNode.id == "rootMiddle")
		{
			parentIsRoot = true;
		}
		var subMenu = document.createElement("div");	
			subMenu.style.position = "absolute";
			setWidth(subMenu, this.templ.submenu.width);
			setX(subMenu, this.templ.submenu.left-this.templ.menuNode.left);
			if(parentIsRoot) {
				setY(subMenu, this.templ.submenu.top-this.templ.root.topheight);
			}
			else {
				setY(subMenu, this.templ.submenu.top-this.templ.submenu.topheight);
			}
			hide(subMenu);
			setOpacity(subMenu,0);
			
		var openHolder = document.createElement("div");
			openHolder.style.position = "absolute";
			setX(openHolder, -this.templ.submenu.left);
			setY(openHolder, this.templ.submenu.topheight);
			setWidth(openHolder, this.templ.submenu.left+this.templ.submenu.width);
			setHeight(openHolder, this.templ.menuNode.height);
			subMenu.appendChild(openHolder);
			
		var subTop = document.createElement("div");
			subTop.style.position = "relative";
			setWidth(subTop,this.templ.submenu.width);
			setHeight(subTop, this.templ.submenu.topheight);
			subTop.style.background = "url("+imagePath+skinname+"/"+this.templ.id+"/top.png)";
			subMenu.appendChild(subTop);

		var subMiddle = document.createElement("div");
			subMiddle.style.position = "relative";
			setWidth(subMiddle,this.templ.submenu.width);
			subMiddle.style.background = "url("+imagePath+skinname+"/"+this.templ.id+"/middle.png)";
			subMenu.appendChild(subMiddle);			

		var subBottom = document.createElement("div");
			subBottom.style.position = "relative";
			setWidth(subBottom,templ.submenu.width);
			setHeight(subBottom, this.templ.submenu.bottomheight);
			subBottom.style.background = "url("+imagePath+skinname+"/"+this.templ.id+"/bottomsub.png)";
			subMenu.appendChild(subBottom);			
			
		addListener(parentItem, "mouseover", function() { fadeIn(subMenu,100);});
		addListener(parentItem, "mouseout", function() { fadeOut(subMenu,100);});		

		subMenu.middle = subMiddle;
		subMenu.templ = this.templ;
		return subMenu;

	}
	
}