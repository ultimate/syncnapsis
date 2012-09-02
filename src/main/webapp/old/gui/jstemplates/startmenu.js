
var templateStartMenu = {
					"id":					"startmenu",
					"root":
					{
						"top": 				35,
						"left":				10,
						"width":			232,
						"topheight": 		21,
						"bottomheight":		46,
						"searchable":		true,
						"searchtop":		14,
						"searchleft":		30,
						"searchcolor":		"#999999",
						"searchfontsize":	12
					},
					"menuNode": 
					{
						"width":			193,
						"height":			44,
						"left":				20,
						"icon": 
						{
							"width": 36,
							"height": 36,
							"left": 4,
							"top": 4
						},
						"label":
						{
							"top": 12,
							"left": 44
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
						"top": -21,
						"left": 212,
						"width": 232,
						"topheight": 21,
						"bottomheight": 14
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
			hide(root);
		
		var rootTop = document.createElement("div");
			rootTop.style.position = "relative";
			setWidth(rootTop,this.templ.root.width);
			setHeight(rootTop, this.templ.root.topheight);
			rootTop.style.background = "url("+imagePath+skinname+"/"+this.templ.id+"/top.png)";
			root.appendChild(rootTop);

		var rootMiddle = document.createElement("div");
			rootMiddle.id = this.templ.root.id+"_middle";		
			rootMiddle.style.position = "relative";
			setWidth(rootMiddle,this.templ.root.width);
			rootMiddle.style.background = "url("+imagePath+skinname+"/"+this.templ.id+"/middle.png)";
			root.middle = rootMiddle;			
			root.appendChild(rootMiddle);			

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
	
	this.getMenuNode = function (id, text, icon, page, topPos, root) 
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

			newItem.style.cursor = "default";
			addListener(newItem, "mouseover", function() { menu_highlight_on(newItem); });
			addListener(newItem, "mouseout", function() {  menu_highlight_off(newItem); });
			if(page != null) {
				addListener(newItem, "click", function() { startmenuOpenWindow(page);menu_hide(root);});
			}
			image.style.backgroundImage = 'url('+imagePath+skinname+'/icons/'+icon+')';
			image.style.position = "absolute";
			setHeight(image, this.templ.menuNode.icon.height);
			setWidth(image,this.templ.menuNode.icon.width);
			setX(image,this.templ.menuNode.icon.left);
			setY(image,this.templ.menuNode.icon.top);
			
			label.appendChild(document.createTextNode(text));
			label.style.position = "absolute";
			setX(label, this.templ.menuNode.label.left);
			setY(label, this.templ.menuNode.label.top);
			setHeight(label, getHeight(newItem));
			setWidth(label, getWidth(newItem)-getWidth(image));
			
			newItem.appendChild(image);
			newItem.appendChild(label);
			
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
		addListener(rootButton, "mouseover", function() { rootButton.style.background = "url("+imagePath+skinname+"/"+rootButton.templ.id+"/startbutton_mouseover.png)";});
		addListener(rootButton, "mouseout", function() { rootButton.style.background = "url("+imagePath+skinname+"/"+rootButton.templ.id+"/startbutton_normal.png)";});
		addListener(rootButton, "click", function(ev) {if(getSrcElement(ev) == rootButton) menu_showhide(root); });
		root.button = rootButton;		
		rootButton.appendChild(root);

		return rootButton;

	}
	
	this.getSubMenu = function(parentItem) {
		var subMenu = document.createElement("div");	
			subMenu.style.position = "absolute";
			setWidth(subMenu, this.templ.submenu.width);
			setX(subMenu, this.templ.submenu.left);
			setY(subMenu, this.templ.submenu.top);
			hide(subMenu);
			
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
			
		addListener(parentItem, "mouseover", function() { show(subMenu);});
		addListener(parentItem, "mouseout", function() { hide(subMenu);});		

		subMenu.middle = subMiddle;
		subMenu.templ = this.templ;
		return subMenu;

	}
	
}