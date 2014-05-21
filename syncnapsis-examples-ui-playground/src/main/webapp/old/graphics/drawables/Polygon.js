/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
function Polygon(xArray, yArray, zArray, color, fill)
{
	this.xArray = xArray;
	this.yArray = yArray;
	this.zArray = zArray;
	this.color = color;
	this.fill = fill;
	
	this.tranformedPoints = null;
	
	this.averageZ = 0;
	
	this.transform = function(transformation, element)
	{		
		this.tranformedPoints = new Array();
		var vec;
		this.averageZ = 0;
		for(var i = 0; i < this.xArray.length; i++)
		{
			vec = new Vector4D(this.xArray[i], this.yArray[i], this.zArray[i]);
			vec = matrixProduct(transformation, vec);		
			vec = vectorRescale(vec);	
			vec = matrixRound(vec);			
			this.tranformedPoints[this.tranformedPoints.length] = vec;
			
			this.averageZ += vec[2][0];
		}
		this.averageZ /= this.xArray.length;
	};
	
	this.draw = function(ctx)
	{
		ctx.strokeStyle = this.color;
		ctx.fillStyle = this.color;
		ctx.beginPath();
		ctx.moveTo(this.tranformedPoints[0][0][0], this.tranformedPoints[0][1][0]);
		for(var i = 1; i < this.tranformedPoints.length; i++)
			ctx.lineTo(this.tranformedPoints[i][0][0], this.tranformedPoints[i][1][0]);
		ctx.closePath();
		if(this.fill)
			ctx.fill();
		else
			ctx.stroke();
	};
}