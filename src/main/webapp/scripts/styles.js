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

FillLayout = function(elements, elementSizes, direction)
{
	if(elements.length != elementSizes.length)
		throw new Error("elements and elementSizes must have same length!");
	this.elements = new Array();
	for(var i = 0; i < elements.length; i++)
	{
		if(elements[i] == null)
			throw new Error("elementIds must not contain null values!");
		if(typeof(elements) == "string")
			this.elements[i] = document.getElementyById(elements[i]);
		else 
			this.elements[i] = elements[i];
	}
	this.elementSizes = elementSizes;
	this.nullSizes = 0;
	for(var i = 0; i < elementSizes.length; i++)
	{
		if(elementSizes[i] == null)
			this.nullSizes++;
	}
	if(direction != Layout.layout.HORIZONTAL && direction != Layout.layout.VERTICAL)
		throw new Error("Illegal direction '" + direction + "'!");
	this.direction = direction;
	
	// TODO attach event to resize
}

FillLayout.fill = function()
{
	var coordinateTarget = window[this.direction];
	var coordinateTotal = 0;
	var coordinateSum = 0;
	for(var i = 0; i < element.length; i++)
	{
		if(this.elementSizes[i] != null)
			coordinateTotal += this.elementSizes[i];
	}
	var nullSize = (coordinateTarget - coordinateTotal) / nullSizes;
	for(var i = 0; i < element.length; i++)
	{
		if(this.direction == Styles.layout.HORIZONTAL)
		{
			this.elements[i].style.left = coordinateSum + "px";
			if(this.elementSizes[i] != null)
				this.elements[i].style.width = this.elementSizes[i] + "px";
			else
				this.elements[i].style.width = nullSize + "px";
		}
		else
		{
			this.elements[i].style.top = coordinateSum + "px";
			if(this.elementSizes[i] != null)
				this.elements[i].style.height = this.elementSizes[i] + "px";
			else
				this.elements[i].style.height = nullSize + "px";
		}
	}
};