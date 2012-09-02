function ViewportAreaListener(area, event)
{
	this.area = area;
	this.active = false;
	this.lastX = null;
	this.lastY = null;
	
	this.setActive = function()
	{
		this.active = true;
	};
	
	this.setInactive = function()
	{
		this.active = false;
		this.lastX = null;
		this.lastY = null;
	};
	
	this.isActive = function()
	{
		return this.active;
	};
	
	this.isInArea = function(x,y)
	{
		return this.area.isIn(x,y);
	};
	
	this.event = event;
}