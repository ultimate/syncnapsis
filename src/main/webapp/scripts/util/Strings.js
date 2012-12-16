// additional functionality for Strings
String.prototype.startsWith = function(prefix)
{
	if(prefix == null)
		return false;
	if(this.length < prefix.length)
		return false;
	return this.substring(0, prefix.length) == prefix;
};

String.prototype.endsWith = function(suffix)
{
	if(suffix == null)
		return false;
	if(this.length < suffix.length)
		return false;
	return this.substring(this.length - suffix.length, this.length) == suffix;
};

String.prototype.contains = function(part)
{
	return (this.indexOf(part) != -1);
};

// Initializes a new instance of the StringBuilder class
// and appends the given value if supplied
StringBuilder = function(value)
{
    this.strings = new Array("");
    this.append(value);
};

// Appends the given value to the end of this instance.
StringBuilder.prototype.append = function (value)
{
    if (value)
    {
        this.strings.push(value);
    }
};

// Clears the string buffer
StringBuilder.prototype.clear = function ()
{
    this.strings.length = 1;
};

// Converts this instance to a String.
StringBuilder.prototype.toString = function ()
{
    return this.strings.join("");
};