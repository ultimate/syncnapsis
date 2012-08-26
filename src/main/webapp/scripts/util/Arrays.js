// finde Index eines Elements in Array
Array.prototype.indexOf = function(what)
{ 
	for(var i=0; i<this.length;i++)
	{
		if(this[i] == what) return i;
	}
	return -1;
};

// Sortierung von numerischen Arrays
Array.prototype.numSort = function()
{
	this.sort(function(a,b) {return a-b;});
};