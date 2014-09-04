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
function setCookie(name, value, expires, path, domain, secure)
{
	document.cookie = name + "=" + escape (value) +
		((expires) ? "; expires=" + expires.toGMTString() : "") +
		((path) ? "; path=" + path : "") +
		((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}

function getCookie(name)
{
    var prefix = name + "="; 
    var start = document.cookie.indexOf(prefix); 
    if (start == -1)
    	return null;    
    var end = document.cookie.indexOf(";", start+prefix.length);    
    if (end == -1)
    	end = document.cookie.length;
    var value = document.cookie.substring(start+prefix.length, end); 
    return value;
}

function deleteCookie(name, path, domain)
{
	if (getCookie(name)) {
		document.cookie = name + "=" +
		  ((path) ? "; path=" + path : "") +
		  ((domain) ? "; domain=" + domain : "") +
		  "; expires=Thu, 01-Jan-70 00:00:01 GMT";
	}
}

function saveUserCookie()
{
    var expires = new Date();
    expires.setTime(expires.getTime() + 24 * 30 * 60 * 60 * 1000); // sets it for approx 30 days.
    setCookie("username",	document.getElementById("j_username").value,		expires,	"/");
    setCookie("rememberMe",	document.getElementById("rememberMe").checked,		expires,	"/");
}