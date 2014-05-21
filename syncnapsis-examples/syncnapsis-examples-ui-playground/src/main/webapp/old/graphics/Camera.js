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
function Camera(camPos, lookVec, upVec, zoom, far, near)
{
	this.camPos = camPos;
	this.lookVec = lookVec;
	this.upVec = upVec;
	this.zoom = zoom;
	this.far = far;
	this.near = near;
	this.transformation = null;
	
	this.calculateCameraTransformation = function()
	{		
		this.calculateCameraTransformation1();
		this.calculateCameraTransformation2();
	};
	
	this.calculateCameraTransformation1 = function()
	{
		var t_translateCOP = TransformationTranslation(-this.camPos[0][0], -this.camPos[1][0], -this.camPos[2][0]);
		
		var w = vectorNorm(matrixScale(-1, this.lookVec));
		var u = vectorNorm(vectorCrossProduct(this.upVec, w));
		var v = vectorNorm(matrixScale(-1, vectorCrossProduct(w, u)));
		var t_cameraOrth = TransformationAny(	u[0][0], u[1][0], u[2][0], 0,
												v[0][0], v[1][0], v[2][0], 0,
												w[0][0], w[1][0], w[2][0], 0);

		var t_cameraScale = TransformationScaling(this.zoom, this.zoom, this.zoom);
				
		var tmp = t_translateCOP;
		tmp = matrixProduct(t_cameraOrth, tmp);
		tmp = matrixProduct(t_cameraScale, tmp);		
				
		this.transformation = tmp;	
	};
	
	this.calculateCameraTransformation2 = function()
	{
		var far_rez = 1/this.far;
		var t_far = TransformationScaling(far_rez, far_rez, far_rez);
		
		var k = this.near/this.far;
		var k_rez1 = 1/(1-k);
		var t_near = TransformationAnyFull(1,0,0,0, 0,1,0,0, 0,0,k_rez1,k*k_rez1, 0,0,-1,0);
		
		tmp = this.transformation;
		
		tmp = matrixProduct(t_far, tmp);
		tmp = matrixProduct(t_near, tmp);		
				
		this.transformation = tmp;	
	};
	
	this.getTransformation = function()
	{
		this.calculateCameraTransformation();
		return this.transformation;
	};
}

function CameraPolar(camCenter, phi, theta, zeta, distance, zoom, far, near)
{
	this.camCenter = camCenter;
	this.phi = phi;
	this.theta = theta;
	this.zeta = zeta;
	this.distance = distance;
	
	this.zoom = zoom;
	this.far = far;
	this.near = near;
	this.transformation = null;
	this.extraTransformation = new TransformationScaling(1,1,1);
	
	this.calculateCameraTransformation = function()
	{
		var cp = cos(this.phi*Math.PI/180);
		var sp = sin(this.phi*Math.PI/180);
		var dx = this.distance*sin(this.theta*Math.PI/180)*cp;
		var dy = this.distance*sin(this.theta*Math.PI/180)*sp;
		var dz = this.distance*cos(this.theta*Math.PI/180);
		var t_translateCam = TransformationTranslation(dx,dy,dz);
		this.camPos = matrixProduct(t_translateCam, this.camCenter);
		
		this.lookVec = matrixSub(this.camCenter, this.camPos);
		
		this.upVec = vectorCrossProduct(new Vector4D(0,0,1), this.lookVec);
		this.upVec = vectorCrossProduct(this.lookVec, this.upVec);
		if((this.upVec[0][0] == 0) && (this.upVec[1][0] == 0) && (this.upVec[2][0] == 0))
		{
			this.upVec = new Vector4D(-cp, -sp, 0);
		}
		var t_rotateUp = TransformationsRotationAxis(this.lookVec, this.zeta*Math.PI/180);
		this.upVec = matrixProduct(t_rotateUp, this.upVec);
		
		this.calculateCameraTransformation1();
		this.calculateCameraTransformationExtra();
		this.calculateCameraTransformation2();
	};
	
	this.calculateCameraTransformationExtra = function()
	{
		var camCenterT = matrixProduct(this.transformation, this.camCenter);
		var dx = -camCenterT[0][0];
		var dy = -camCenterT[1][0];
		var dz = -camCenterT[2][0];
		this.transformation = matrixProduct(new TransformationTranslation( dx, dy, dz), this.transformation);
		this.transformation = matrixProduct(this.extraTransformation, this.transformation);
		this.transformation = matrixProduct(new TransformationTranslation(-dx,-dy,-dz), this.transformation);
	};
	
	this.addExtraTransformation = function(extraTransformation)
	{		
		this.extraTransformation = matrixProduct(extraTransformation, this.extraTransformation);
	};
	
	this.increasePhi = function(dPhi)
	{
		this.phi += dPhi;
		if(this.phi > 180)
			this.phi -= 360;
		if(this.phi < -180)
			this.phi += 360;				
	};
	
	this.increaseTheta = function(dTheta)
	{
		this.theta += dTheta;
		if(this.theta < 0)
			this.theta = 0;
		if(this.theta > 180)
			this.theta = 180;		
	};	
	
	this.increaseZeta = function(dZeta)
	{
		this.zeta += dZeta;
		if(this.zeta > 180)
			this.zeta -= 360;
		if(this.zeta < -180)
			this.zeta += 360;				
	};
}

CameraPolar.prototype = new Camera(0,0,0,0,0,0);

var standardCameras = new Array();
var standardCameraNames = new Array();

function registerStandardCamera(camera, index, cameraName)
{
	standardCameras[index] = camera;
	standardCameraNames[index] = cameraName;
}

function getStandardCamera(index)
{
	var c = standardCameras[index];
	return new Camera(c.camPos, c.lookVec, c.upVec, c.zoom, c.far, c.near);
}