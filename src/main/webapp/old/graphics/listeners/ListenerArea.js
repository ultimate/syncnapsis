function ListenerPoint_Square(x,y,r)
{
	this.xC = x;
	this.yC = y; 
	this.r = r;
	
	this.isIn = function(x,y)
	{
		return ((x <= this.xC+this.r) && (x >= this.xC-this.r) && (y <= this.yC+this.r) && (y >= this.yC-this.r));
	};
}

function ListenerPoint_Circle(x,y,r)
{
	this.xC = x;
	this.yC = y; 
	this.r = r;
	
	this.isIn = function(x,y)
	{		
		return ((x-this.xC)*(x-this.xC) + (y-this.yC)*(y-this.yC)) <= this.r*this.r;
	};
}

function ListenerArea(xArray, yArray, zArray)
{
	this.xArray = xArray;
	this.yArray = yArray;
	this.zArray = zArray;
}