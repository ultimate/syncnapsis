/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
random = Math.random;

var psi = 0.0001;

function sin(x)
{
	var result = Math.sin(x);
	if((result < psi) && (result > -psi))
		result = 0;
	return result;
}

function cos(x)
{
	var result = Math.cos(x);
	if((result < psi) && (result > -psi))
		result = 0;
	return result;
}

function randomInt(limit) { return parseInt(random()*limit); }

function h2d(h) { return parseInt(h,16); }
function d2h(d) { return d.toString(16); }
function d2hn(d, n)
{
	h = d2h(d);
	while(h.length < n)
		h = "0" + h;
	return h;
}

function Matrix(rows, cols)
{
	var k = new Array(rows);
	
	for (var y = 0; y < k.length; y++)
		k[y] = new Array(cols);

	return k; 
}

function EmptyVector()
{
	return Matrix(3,1);
}

function EmptyVector4D()
{
	return Matrix(4,1);
}

function Vector(x,y,z)
{
	var result = new EmptyVector();
	result[0][0] = x;
	result[1][0] = y;
	result[2][0] = z;
	return result;
}

function Vector4D(x,y,z)
{
	var result = new EmptyVector4D();
	result[0][0] = x;
	result[1][0] = y;
	result[2][0] = z;
	result[3][0] = 1;
	return result;
}

function TransformationScaling(factorX, factorY, factorZ)
{
	var result = new Matrix(4,4);
	result[0][0] = factorX;		result[0][1] = 0; 			result[0][2] = 0; 			result[0][3] = 0; 			
	result[1][0] = 0; 			result[1][1] = factorY; 	result[1][2] = 0; 			result[1][3] = 0; 			
	result[2][0] = 0; 			result[2][1] = 0; 			result[2][2] = factorZ; 	result[2][3] = 0; 			
	result[3][0] = 0; 			result[3][1] = 0; 			result[3][2] = 0; 			result[3][3] = 1; 		
	return result;	
}

function TransformationTranslation(deltaX, deltaY, deltaZ)
{
	var result = new Matrix(4,4);
	result[0][0] = 1;			result[0][1] = 0; 			result[0][2] = 0; 			result[0][3] = deltaX; 			
	result[1][0] = 0; 			result[1][1] = 1; 			result[1][2] = 0; 			result[1][3] = deltaY; 			
	result[2][0] = 0; 			result[2][1] = 0; 			result[2][2] = 1; 	 		result[2][3] = deltaZ; 			
	result[3][0] = 0; 			result[3][1] = 0; 			result[3][2] = 0; 			result[3][3] = 1; 		
	return result;	
}

function TransformationRotationX(a)
{
	var result = new Matrix(4,4);
	result[0][0] = 1;			result[0][1] = 0; 			result[0][2] = 0; 			result[0][3] = 0; 			
	result[1][0] = 0; 			result[1][1] = cos(a); 		result[1][2] = -sin(a); 	result[1][3] = 0; 			
	result[2][0] = 0; 			result[2][1] = sin(a); 		result[2][2] = cos(a); 	 	result[2][3] = 0; 			
	result[3][0] = 0; 			result[3][1] = 0; 			result[3][2] = 0; 			result[3][3] = 1; 		
	return result;	
}

function TransformationRotationY(a)
{
	var result = new Matrix(4,4);
	result[0][0] = cos(a);		result[0][1] = 0; 			result[0][2] = sin(a); 		result[0][3] = 0; 			
	result[1][0] = 0; 			result[1][1] = 1; 			result[1][2] = 0; 			result[1][3] = 0; 			
	result[2][0] = -sin(a); 	result[2][1] = 0; 			result[2][2] = cos(a); 	 	result[2][3] = 0; 			
	result[3][0] = 0; 			result[3][1] = 0; 			result[3][2] = 0; 			result[3][3] = 1; 		
	return result;	
}

function TransformationRotationZ(a)
{
	var result = new Matrix(4,4);
	result[0][0] = cos(a);		result[0][1] = -sin(a); 	result[0][2] = 0; 			result[0][3] = 0; 			
	result[1][0] = sin(a); 		result[1][1] = cos(a); 		result[1][2] = 0; 			result[1][3] = 0; 			
	result[2][0] = 0; 			result[2][1] = 0; 			result[2][2] = 1; 	 		result[2][3] = 0; 			
	result[3][0] = 0; 			result[3][1] = 0; 			result[3][2] = 0; 			result[3][3] = 1; 		
	return result;	
}

function TransformationsRotationAxis(axis, a)
{
	var ax = vectorNorm(axis);
	var v1 = ax[0][0];
	var v2 = ax[1][0];
	var v3 = ax[2][0];
	var s = sin(a);
	var c = cos(a);
	var result = new Matrix(4,4);
	result[0][0] = c+v1*v1*(1-c);		result[0][1] = v1*v2*(1-c)-v3*s; 	result[0][2] = v1*v3*(1-c)+v2*s; 	result[0][3] = 0; 			
	result[1][0] = v2*v1*(1-c)+v3*s; 	result[1][1] = c+v2*v2*(1-c); 		result[1][2] = v2*v3*(1-c)-v1*s; 	result[1][3] = 0; 			
	result[2][0] = v3*v1*(1-c)-v2*s; 	result[2][1] = v3*v2*(1-c)+v1*s; 	result[2][2] = c+v3*v3*(1-c); 	 	result[2][3] = 0; 			
	result[3][0] = 0; 					result[3][1] = 0; 					result[3][2] = 0; 					result[3][3] = 1; 		
	return result;	
}

function TransformationAny(x1, x2, x3, x4, y1, y2, y3, y4, z1, z2, z3, z4)
{
	var result = new Matrix(4,4);
	result[0][0] = x1;			result[0][1] = x2; 			result[0][2] = x3; 			result[0][3] = x4; 			
	result[1][0] = y1; 			result[1][1] = y2; 			result[1][2] = y3; 			result[1][3] = y4; 			
	result[2][0] = z1; 			result[2][1] = z2; 			result[2][2] = z3; 	 		result[2][3] = z4; 			
	result[3][0] = 0; 			result[3][1] = 0; 			result[3][2] = 0; 			result[3][3] = 1; 		
	return result;	
}

function TransformationAnyFull(a00, a01, a02, a03, a10, a11, a12, a13, a20, a21, a22, a23, a30, a31, a32, a33)
{
	var result = new Matrix(4,4);
	result[0][0] = a00;			result[0][1] = a01;			result[0][2] = a02;			result[0][3] = a03; 			
	result[1][0] = a10;			result[1][1] = a11;			result[1][2] = a12;			result[1][3] = a13; 			
	result[2][0] = a20;			result[2][1] = a21; 		result[2][2] = a22;	 		result[2][3] = a23; 			
	result[3][0] = a30;			result[3][1] = a31; 		result[3][2] = a32;			result[3][3] = a33; 		
	return result;	
}

function vectorDotProduct(vec1, vec2)
{
	return matrixProduct(matrixTranspose(vec1), vec2)[0][0] - 1;
}

function vectorCrossProduct(vec1, vec2)
{
	var result = new EmptyVector4D();
	result[0][0] = vec1[1][0]*vec2[2][0] - vec1[2][0]*vec2[1][0];
	result[1][0] = vec1[2][0]*vec2[0][0] - vec1[0][0]*vec2[2][0];
	result[2][0] = vec1[0][0]*vec2[1][0] - vec1[1][0]*vec2[0][0];
	result[3][0] = 1;
	return result;
}

function vectorLength(vec)
{
	return Math.sqrt(vectorDotProduct(vec, vec));
}

function vectorNorm(vec)
{
	var result = new EmptyVector4D();
	var scalar = vectorLength(vec);
	result[0][0] = vec[0][0]/scalar;
	result[1][0] = vec[1][0]/scalar;
	result[2][0] = vec[2][0]/scalar;
	result[3][0] = 1;
	return result;
}

function vectorRescale(vec)
{
	var result = new EmptyVector4D();
	var factor = 1/vec[3][0];
	result[0][0] = vec[0][0]*factor;
	result[1][0] = vec[1][0]*factor;
	result[2][0] = vec[2][0]*factor;
	result[3][0] = 1;
	return result;
}

function matrixCopy(mat)
{	
	var result = new Matrix(mat.length, mat[0].length);
	for(var y = 0; y < result.length; y++)
	{
		for(var x = 0; x < result[y].length; x++)
		{
			result[y][x] = mat[y][x];
		}
	}
	return result;
}

function matrixTranspose(mat)
{	
	var result = new Matrix(mat[0].length, mat.length);
	for(var y = 0; y < result.length; y++)
	{
		for(var x = 0; x < result[y].length; x++)
		{
			result[y][x] = mat[x][y];
		}
	}
	return result;
}

function matrixRound(mat)
{
	var result = new Matrix(mat.length, mat[0].length);
	for(var y = 0; y < result.length; y++)
	{
		for(var x = 0; x < result[y].length; x++)
		{
			result[y][x] = Math.round(mat[y][x]);
		}
	}
	return result;
}

function matrixScale(factor, mat)
{	
	var result = new Matrix(mat.length, mat[0].length);
	for(var y = 0; y < result.length; y++)
	{
		for(var x = 0; x < result[y].length; x++)
		{
			result[y][x] = factor*mat[y][x];
		}
	}
	return result;
}

function matrixAdd(mat1, mat2)
{	
	if((mat1.length != mat2.length) || (mat1[0].length != mat2[0].length))
		throw new Error("Matrix dimension mismatch");
	var result = new Matrix(mat1.length, mat1[0].length);
	for(var y = 0; y < result.length; y++)
	{
		for(var x = 0; x < result[y].length; x++)
		{
			result[y][x] = mat1[y][x] + mat2[y][x];
		}
	}
	return result;
}

function matrixSub(mat1, mat2)
{	
	if((mat1.length != mat2.length) || (mat1[0].length != mat2[0].length))
		throw new Error("Matrix dimension mismatch");
	var result = new Matrix(mat1.length, mat1[0].length);
	for(var y = 0; y < result.length; y++)
	{
		for(var x = 0; x < result[y].length; x++)
		{
			result[y][x] = mat1[y][x] - mat2[y][x];
		}
	}
	return result;
}

function matrixProduct(mat1, mat2)
{
	if(mat1[0].length != mat2.length)
		throw new Error("Matrix dimension mismatch");
	var result = new Matrix(mat1.length, mat2[0].length);
	for(var y = 0; y < result.length; y++)
	{
		for(var x = 0; x < result[y].length; x++)
		{
			result[y][x] = 0;
			for(var i = 0; i < mat2.length; i++)
			{
				result[y][x] += mat1[y][i]*mat2[i][x];
			}
		}
	}
	return result;
}

function matrixPrint(mat)
{
	var table = document.createElement("table");
	var row;
	var cell;
	for(var y = 0; y < mat.length; y++)
	{
		row = document.createElement("tr");
		for(var x = 0; x < mat[y].length; x++)
		{
			cell = document.createElement("td");
			cell.innerHTML = mat[y][x];
			row.appendChild(cell);
		}
		table.appendChild(row);
	}
	return table;
}

function matrixAlert(mat)
{
	var text = "";
	for(var y = 0; y < mat.length; y++)
	{
		for(var x = 0; x < mat[y].length; x++)
		{
			text = text + mat[y][x] + "\t";
		}
		text = text + "\n";
	}
	alert(text);
}