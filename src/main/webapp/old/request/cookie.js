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