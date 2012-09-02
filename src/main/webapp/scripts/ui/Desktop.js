DependencyManager.addDependency("lib/raphael-min.js");
DependencyManager.addDependency("ui/Component.js");

Desktop = function(theme)
{
	this.theme = new Theme();
	this.setTheme(theme);
		
	this.paper = new Raphael();
	
	this.components = new Array();
};

Desktop.prototype = Component;

Desktop.prototype.setTheme = function(theme)
{
	if(Themes.isValid(theme))
	{
		this.theme = theme;
	}
	else
	{
		throw new Error("invalid theme: " + theme);
	}
};