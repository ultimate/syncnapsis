

var Locale = {};

Locale.CHOOSER_ID = "locale_chooser";
Locale.LABEL_TAGNAME = "label";
Locale.KEY_ATTRIBUTE = "key";
Locale.STRING_VARIABLE = "lang";

Locale.populateChooser = function()
{
	var localeChooser = document.getElementById(Locale.CHOOSER_ID);
	localeChooser.childNodes.clear(); // clear children?
	var select;
	for(var i in lang.EnumLocale)
	{
		select = document.createElement("select");
		select.value = i;
		select.text = lang.EnumLocale[i];
		if("EnumLocale." + i == lang.current)
		{
			select.setAttribute("selected", "selected");
		}
		localeChooser.appendChild(select);
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