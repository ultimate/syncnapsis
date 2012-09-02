EVENT_POINT_DRAG 	= 100;
EVENT_POINT_CLICK 	= 101;
EVENT_POINT_HOVER 	= 102;

function EventPoint(x, y, z, type, area, event)
{
	this.x = x;
	this.y = y;
	this.z = z;
	this.type = type;
	this.area = area;
	this.event = event;
	this.listener = null;
	
	this.tranformedPoint = null;
	this.averageZ = 0;
	
	this.transform = function(transformation, viewport)
	{
		var vec = new Vector4D(this.x, this.y, this.z);
		vec = matrixProduct(transformation, vec);		
		vec = vectorRescale(vec);
		vec = matrixRound(vec);		
		
		this.tranformedPoint = vec;
		
		if(this.listener == null)
		{
			this.listener = new ViewportAreaListener(this.area, this.event);
			this.listener.viewport = viewport;
			if(this.type == EVENT_POINT_DRAG)
				viewport.dragListeners[viewport.dragListeners.length] = this.listener;
			if(this.type == EVENT_POINT_CLICK)
				viewport.clickListeners[viewport.clickListeners.length] = this.listener;
			if(this.type == EVENT_POINT_HOVER)
				viewport.hoverListeners[viewport.hoverListeners.length] = this.listener;
		}	
		this.listener.area.xC = vec[0][0];
		this.listener.area.yC = vec[1][0];
	};
	
	this.draw = function(ctx)
	{	
	};
}