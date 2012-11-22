Styles = {};

Styles.constant = {};
Styles.constant.WIDTH = "width";
Styles.constant.HEIGHT = "height";
Styles.constant.LEFT = "left";
Styles.constant.RIGHT = "right";
Styles.constant.TOP = "top";
Styles.constant.BOTTOM = "bottom";

Styles.layout = {};
Styles.layout.HORIZONTAL = 1;
Styles.layout.VERTICAL = 2;

Styles.FillLayout = function(elements, elementSizes, direction)
{
	if(elements.length != elementSizes.length)
		throw new Error("elements and elementSizes must have same length!");
	this.elements = new Array();
	this.elementParent = null;
	for(var i = 0; i < elements.length; i++)
	{
		if(elements[i] == null)
			throw new Error("elementIds must not contain null values!");
		if(typeof(elements[i]) == Reflections.type.STRING)
			this.elements[i] = document.getElementById(elements[i]);
		else 
			this.elements[i] = elements[i];
		if(this.elementParent == null)
			this.elementParent = this.elements[i].parentNode;
		else if(this.elements[i].parentNode != this.elementParent)
			throw new Error("elements must have same parent!");
	}
	this.elementSizes = elementSizes;
	this.nullSizes = 0;
	for(var i = 0; i < elementSizes.length; i++)
	{
		if(elementSizes[i] == null)
			this.nullSizes++;
	}
	if(direction != Styles.layout.HORIZONTAL && direction != Styles.layout.VERTICAL)
		throw new Error("Illegal direction '" + direction + "'!");
	this.direction = direction;
	
	this._handlerObject = this;
	this._handlerMethod = this.fill;
	Events.addEventListener(Events.ONRESIZE, this.anonymous() , window);
}

Styles.FillLayout.prototype = new Events.EventHandler();

Styles.FillLayout.prototype.fill = function()
{
	var coordinateTarget;
	if(this.direction == Styles.layout.HORIZONTAL)
		coordinateTarget = this.elementParent.offsetWidth;
	else
		coordinateTarget = this.elementParent.offsetHeight;
	var coordinateTotal = 0;
	var coordinateSum = 0;
	for(var i = 0; i < this.elements.length; i++)
	{
		if(this.elementSizes[i] != null)
			coordinateTotal += this.elementSizes[i];
	}
	var nullSize = (coordinateTarget - coordinateTotal) / this.nullSizes;
	var size;
	for(var i = 0; i < this.elements.length; i++)
	{
		if(this.elementSizes[i] != null)
			size = this.elementSizes[i];
		else
			size = nullSize;
		
		if(this.direction == Styles.layout.HORIZONTAL)
		{
			this.elements[i].style.left = coordinateSum + "px";			
			this.elements[i].style.width = size + "px";
			//this.elements[i].style.height = this.elementParent.offsetHeight + "px";
			coordinateSum += size;			
		}
		else
		{
			this.elements[i].style.top = coordinateSum + "px";
			this.elements[i].style.height = size + "px";
			//this.elements[i].style.width = this.elementParent.offsetWidth + "px";
			coordinateSum += size;
		}
	}
};