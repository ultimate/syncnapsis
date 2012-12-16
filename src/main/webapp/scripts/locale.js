//@requires("Lang")

var Locale = {};

Locale.CHOOSER_ID = "locale_chooser";
Locale.LABEL_TAGNAME = "label";
Locale.KEY_ATTRIBUTE = "key";
Locale.STRING_VARIABLE = "lang";

Locale.populateChooser = function()
{
	var localeChooser = document.getElementById(Locale.CHOOSER_ID);
	// clear children
	while(localeChooser.hasChildNodes())
	{
		localeChooser.removeChild(localeChooser.lastChild);
	}
	// add options
	var option;
	for(var i in lang.EnumLocale)
	{
		option = document.createElement("option");
		option.value = i;
		option.text = lang.EnumLocale[i];
		if("EnumLocale." + i == lang.current)
		{
			option.setAttribute("selected", "selected");
		}
		localeChooser.appendChild(option);
	}
};

Locale.getString = function(key)
{
	return eval(Locale.STRING_VARIABLE + "." + key);
};

Locale.update = function()
{
	var elements = document.getElementsByTagName(Locale.LABEL_TAGNAME);
	for(var i = 0; i < elements.length; i++)
	{
		elements[i].innerHTML = Locale.getString(elements[i].getAttribute(Locale.KEY_ATTRIBUTE));
	}
};

Locale.select = function()
{
	// TODO select locale on server via WS
	DependencyManager.reloadScript("Lang", Events.wrapEventHandler(Locale, Locale.update));
};