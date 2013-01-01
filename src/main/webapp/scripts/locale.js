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

Locale.select = function(selection)
{
	// select locale on server via WS
	server.localeProvider.set(selection); // loose dependency of base.js
	DependencyManager.reloadScript("Lang", Events.wrapEventHandler(Locale, Locale.update));
};