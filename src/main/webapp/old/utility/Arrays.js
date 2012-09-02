// finde Index eines Elements in Array
function find(what, where)
{
	for(var i=0; i<where.length;i++)
	{
		if(where[i] == what) return i;
	}
	return -1;
}

function NumCompare(a,b)
{
	return a-b;
}

Array.prototype.find = function(what) { return find(what, this); };
Array.prototype.numSort = function() { this.sort(NumCompare); };