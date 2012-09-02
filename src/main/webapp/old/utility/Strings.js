// Leerzeichen am Anfang loeschen
String.prototype.leftTrim = function ()
{
	return (this.replace(/^\s+/,""));
};
//Leerzeichen am Ende loeschen  
String.prototype.rightTrim = function ()
{
    return (this.replace(/\s+$/,""));
};
//Leerzeichen am Anfang und Ende loeschen
String.prototype.trim = function ()
{
	return (this.replace(/\s+$/,"").replace(/^\s+/,""));
};
//Leerzeichen am Anfang und Ende und mehrfach in der Mitte loeschen
String.prototype.superTrim = function ()
{
	return(this.replace(/\s+/g," ").replace(/\s+$/,"").replace(/^\s+/,""));
};
//Alle Leerzeichen loeschen
String.prototype.removeWhiteSpaces = function ()
{
    return (this.replace(/\s+/g,""));
};