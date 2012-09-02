function Path(xArray, yArray, zArray, color)
{
	this.xArray = xArray;
	this.yArray = yArray;
	this.zArray = zArray;
	this.color = color;
	
	this.tranformedPoints = null;
	
	this.averageZ = 0;
	
	this.transform = function(transformation, element)
	{		
		this.tranformedPoints = new Array();
		var vec;
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
		ctx.beginPath();
		ctx.moveTo(this.tranformedPoints[0][0][0], this.tranformedPoints[0][1][0]);
		for(var i = 1; i < this.tranformedPoints.length; i++)
			ctx.lineTo(this.tranformedPoints[i][0][0], this.tranformedPoints[i][1][0]);
		ctx.stroke();
	};
}