function RasterPoint(x, y, z, color)
{
	x = Math.round(x);
	y = Math.round(y);
	this.x = x;
	this.y = y;
	this.z = z;
	this.color = color;
}


function rasterizeLine(x1, y1, z1, x2, y2, z2, color)
{
	x1 = Math.round(x1);
	y1 = Math.round(y1);
	//z1 = Math.round(z1);
	x2 = Math.round(x2);
	y2 = Math.round(y2);
	//z2 = Math.round(z2);
	if((x1 == x2) && (y1 == y2))
	{
		var list = new Array();
		if(z1 != z2)
			warn("xs and ys are equal but z is not");
		list[0] = new RasterPoint(x1, y1, Math.min(z1, z2), color);
		return list;
	}
	else if(x1 == x2)
		return rasterizeLineVertical(x1, y1, z1, x2, y2, z2, color);
	else if(y1 == y2)
		return rasterizeLineHorizontal(x1, y1, z1, x2, y2, z2, color);
		
	if(x1 > x2)
		return rasterizeLine(x2, y2, z2, x1, y1, z1, color);
	
	var dx = x2-x1;
	var dy = y2-y1;
	
	if((dx == dy) || (dx == -dy))
		return rasterizeLineDiagonal(x1, y1, z1, x2, y2, z2, color);	
	else
		return rasterizeLineMidPoint(x1, y1, z1, x2, y2, z2, color);	
}

function rasterizeLineVertical(x1, y1, z1, x2, y2, z2, color)
{
	if(y1 > y2)
		return rasterizeLineVertical(x2, y2, z2, x1, y1, z1, color);
	if(x1 != x2)
		throw new Error("x-Coordinate must match");
	var list = new Array();
	var dz = (z2-z1)/(y2-y1);
	var z = z1;
	for(var y = y1; y <= y2; y++)
	{
		list[list.length] = new RasterPoint(x1, y, z, color);
		z = z+dz;
	}
	return list;
}

function rasterizeLineHorizontal(x1, y1, z1, x2, y2, z2, color)
{
	if(x1 > x2)
		return rasterizeLineHorizontal(x2, y2, z2, x1, y1, z1, color);
	if(y1 != y2)
		throw new Error("y-Coordinate must match");
	var list = new Array();
	var dz = (z2-z1)/(x2-x1);
	var z = z1;
	for(var x = x1; x <= x2; x++)
	{
		list[list.length] = new RasterPoint(x, y1, z, color);
		z = z+dz;
	}
	return list;
}

function rasterizeLineDiagonal(x1, y1, z1, x2, y2, z2, color)
{
	if(x1 > x2)
		return rasterizeLineDiagonal(x2, y2, z2, x1, y1, z1, color);
	var dx = x2-x1;
	var dy = y2-y1;
	if((dx != dy) && (dx != -dy))
		throw new Error("m must be 1");
	var list = new Array();
	var dz = (z2-z1)/dx;
	var z = z1;
	for(d = 0; d <= dx; d++)
	{
		if(dy > 0)
			list[list.length] = new RasterPoint(x1+d, y1+d, z, color);
		else
			list[list.length] = new RasterPoint(x1+d, y1-d, z, color);
		z = z+dz;
	}
	return list;
}

function rasterizeLineMidPoint(x1, y1, z1, x2, y2, z2, color)
{
	if(x1 > x2)
		return rasterizeLineMidPoint(x2, y2, z2, x1, y1, z1, color);
	var dx = x2-x1;
	var dy = y2-y1;
	var list = new Array();
	var x, y, g;
	var dz;
	if(Math.abs(dx) > Math.abs(dy))
		dz = (z2-z1)/dx;
	else
		dz = (z2-z1)/dy;
	var z = z1;
	if((dy > 0) && (dy > dx))
	{
		//m > 1
		x = x1;	
		y = y1;
		g = dy*(x+0.5) - dx*(y+1) + dx*y1 - dy*x1;
		list[list.length] = new RasterPoint(x, y, z, color);
		for(; y < y2;)
		{
			z = z+dz;
			if(g <= 0)
			{ 
				y++;
				g = g + dx;
			}
			else
			{		
				y++;
				x++;		
				g = g + dx - dy;
			}
			list[list.length] = new RasterPoint(x, y, z, color);
		}
	}	
	else if((dy > 0) && (dy < dx))
	{
		//0 < m < 1
		x = x1;	
		y = y1;
		g = dy*(x+1) - dx*(y+0.5) + dx*y1 - dy*x1;
		list[list.length] = new RasterPoint(x, y, z, color);
		for(; x < x2;)
		{
			z = z+dz;
			if(g <= 0)
			{
				x++;
				g = g + dy;
			}
			else
			{		
				x++;
				y++;		
				g = g + dy - dx;
			}
			list[list.length] = new RasterPoint(x, y, z, color);
		}
	}
	else if((dy < 0) && (-dy > dx))
	{
		//m < -1
		x = x1;	
		y = y1;
		dy = -dy;
		g = dy*(x+0.5) - dx*(y+1) + dx*y1 - dy*x1;
		list[list.length] = new RasterPoint(x, y, z, color);
		for(; y > y2;)
		{
			z = z+dz;
			if(g <= 0)
			{
				y--;
				g = g + dx;
			}
			else
			{		
				y--;
				x++;		
				g = g + dx - dy;
			}
			list[list.length] = new RasterPoint(x, y, z, color);
		}
	}	
	else if((dy < 0) && (-dy < dx))
	{
		//-1 < m < 0
		x = x1;	
		y = y1;
		dy = -dy;
		g = dy*(x+1) - dx*(y+0.5) + dx*y1 - dy*x1;
		list[list.length] = new RasterPoint(x, y, z, color);
		for(; x < x2;)
		{
			z = z+dz;
			if(g <= 0)
			{
				x++;
				g = g + dy;
			}
			else
			{		
				x++;
				y--;		
				g = g + dy - dx;
			}
			list[list.length] = new RasterPoint(x, y, z, color);
		}	
	}
	else
	{
		throw new Error("cannot happen...");
	}
	return list;
}

function rasterizeCircle(rasterPoint, r)
{
	var fullList = new Array();
	var quartList = rasterizeCircleQuart(r, rasterPoint.color);
	for(var i = 0; i < quartList.length; i++)
	{
		fullList[fullList.length] = new RasterPoint(rasterPoint.x+quartList[i].x, rasterPoint.y+quartList[i].y, rasterPoint.z, rasterPoint.color);
		fullList[fullList.length] = new RasterPoint(rasterPoint.x+quartList[i].x, rasterPoint.y-quartList[i].y, rasterPoint.z, rasterPoint.color);
		fullList[fullList.length] = new RasterPoint(rasterPoint.x-quartList[i].x, rasterPoint.y+quartList[i].y, rasterPoint.z, rasterPoint.color);
		fullList[fullList.length] = new RasterPoint(rasterPoint.x-quartList[i].x, rasterPoint.y-quartList[i].y, rasterPoint.z, rasterPoint.color);
	}
	return fullList;
}

function rasterizeCircleQuart(r, color)
{
	var list = new Array();
	for(var x = 0; x <= r*Math.SQRT1_2+1; x++)
	{
		y = Math.round(Math.sqrt(r*r - x*x));
		list[list.length] = new RasterPoint(x, y, 0, color);
		list[list.length] = new RasterPoint(y, x, 0, color);
	}
	return list;
}

function rasterizeTriangle(x1, y1, z1, x2, y2, z2, x3, y3, z3, color, fill)
{
	return rasterizePolygon(new Array(x1, x2, x3), new Array(y1, y2, y3), new Array(z1, z2, z3), color, fill);
}

function rasterizePolygon(xArray, yArray, zArray, color, fill)
{
	if(xArray == null)
		throw new Error("xArray is null");
	if(yArray == null)
		throw new Error("yArray is null");
	if(xArray.length != yArray.length)
		throw new Error("Arrays must have same length");
	var finalList = new Array();
	var listI;
	var ylist = new Array();
	var y;
	for(var i = 0; i < xArray.length; i++)
	{
		listI = rasterizeLine(xArray[i], yArray[i], zArray[i],
						xArray[(i+1)%xArray.length], yArray[(i+1)%yArray.length], zArray[(i+1)%zArray.length], color);
		for(var i2 = 0; i2 < listI.length; i2++)
		{	
			if(fill)
			{
				y = listI[i2].y;
				if(ylist[y] == null)
					ylist[y] = new Array();
				ylist[y][ylist[y].length] = listI[i2];
			}
			else
			{
				finalList[finalList.length] = listI[i2];
			}
		}
	}
	if(!fill)
		return finalList;
	var xMin, xMax;
	var iMin, iMax;
	var min, ax;
	var xList;
	for(var i in ylist)
	{
		xMax = -100000000; 
		xMin =  100000000; 
		for(var i2 = 0; i2 < ylist[i].length; i2++)
		{
			if(ylist[i][i2].x < xMin)
			{
				xMin = ylist[i][i2].x;
				iMin = i2;
			}
			if(ylist[i][i2].x > xMax)
			{
				xMax = ylist[i][i2].x;
				iMax = i2;
			}
		}
		min = ylist[i][iMin];
		max = ylist[i][iMax]
		xList = rasterizeLine(min.x, min.y, min.z, max.x, max.y, max.z, color);
		for(var i3 = 0; i3 < xList.length; i3++)
			finalList[finalList.length] = xList[i3];
	}
	return finalList;	
}
	