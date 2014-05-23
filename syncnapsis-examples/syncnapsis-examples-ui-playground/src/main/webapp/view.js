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
//@requires("vertexshader")
//@requires("fragmentshader")

var ViewUtil = {};

ViewUtil.loadShader = function(name) {
	var index = DependencyManager.indexOf(name);
	return DependencyManager.scriptContents[index];
};

ViewUtil.AnimatedVariable = function(initialValue, min, max, animationStepSize) {
	this.value = initialValue;
	this.target = initialValue;
	this.min = min;
	this.max = max;	
	this.stepSize = animationStepSize;	
	
	this.animate = function(instant) {
		if(this.min != null && this.target < this.min)
			this.target = this.min;
		if(this.max != null && this.target > this.max)
			this.target = this.max;
			
		var dist = Math.abs(this.target - this.value);
		var sgn = Math.sign(this.target - this.value);
			
		if(instant || dist < this.stepSize)
			this.value = this.target;
		else 
			this.value += sgn*this.stepSize;		
	};
};

ViewUtil.AnimatedVector3 = function(initialValue, animationStepSize) {
	this.value = initialValue.clone();
	this.target = initialValue.clone();
	this.stepSize = animationStepSize;
	
	this.animate = function(instant) {
		var dx = this.target.x - this.value.x;
		var dy = this.target.y - this.value.y;
		var dz = this.target.z - this.value.z;
		var dist2 = dx*dx + dy*dy + dz*dz;
		
		if(instant || dist2 < (this.stepSize*this.stepSize))
		{
			// copy each coordinate individually
			// otherwise we would have the same object and manipulating the target-object would not be possible
			this.value.x = this.target.x;
			this.value.y = this.target.y;
			this.value.z = this.target.z;
		}
		else
		{
			var anim = 2;
			if(anim == 1)
			{
				var max = Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)));
				this.value.x += dx/max * this.stepSize;
				this.value.y += dy/max * this.stepSize;
				this.value.z += dz/max * this.stepSize;
			}
			else
			{
				var s = Math.abs(this.stepSize) / Math.sqrt(dist2);
				this.value.x += s*dx;
				this.value.y += s*dy;
				this.value.z += s*dz;
			}
		}
	};
};

// for debugging without Request.js - START
ViewUtil.loadShader = function(name) {
	if(name == "vertexshader")
	{	
		return "uniform float amplitude;\
attribute float size;\
attribute vec3 customColor;\
varying vec3 vColor;\
void main() {\
	vColor = customColor;\
	vec4 mvPosition = modelViewMatrix * vec4( position, 1.0 );\
	gl_PointSize = size * ( 300.0 / length( mvPosition.xyz ) );\
	gl_Position = projectionMatrix * mvPosition;\
}";
	}
	else if(name == "fragmentshader")
	{
		return "uniform vec3 color;\
uniform sampler2D texture;\
varying vec3 vColor;\
void main() {\
	gl_FragColor = vec4( color * vColor, 1.0 );\
	gl_FragColor = gl_FragColor * texture2D( texture, gl_PointCoord );\
}";
	}
};
// for debugging without Request.js - END







var View = function(container) {
	this.shaders = {
		attributes: {
			size: {	type: 'f', value: [] },
			customColor: { type: 'c', value: [] }
		},
		uniforms: {
			amplitude: { type: "f", value: 1.0 },
			color:     { type: "c", value: new THREE.Color( 0xffffff ) },
			texture:   { type: "t", value: THREE.ImageUtils.loadTexture( "img/star.png" ) },
		},
		vertexShader:   ViewUtil.loadShader( "vertexshader" ),
		fragmentShader: ViewUtil.loadShader( "fragmentshader" ),
		sizeMin: 10,
		sizeMax: 30,
	};
	this.materials = {
		system:  new THREE.ShaderMaterial( {
			uniforms: 		this.shaders.uniforms,
			attributes:     this.shaders.attributes,
			vertexShader:   this.shaders.vertexShader,
			fragmentShader: this.shaders.fragmentShader,
			blending: 		THREE.AdditiveBlending,
			depthTest: 		false,
			transparent:	true,
		}),
		planeT: new THREE.MeshBasicMaterial( { opacity:0.2 , wireframe: false, transparent:	true, side: THREE.DoubleSide } ),
		planeW: new THREE.MeshBasicMaterial( { opacity:0.6 , wireframe: true } ),	
		invisible: new THREE.MeshBasicMaterial( { visible: false} ),
	};	

	this.camera = {
		target: new ViewUtil.AnimatedVector3(new THREE.Vector3(0,0,0), 10),
		radius: new ViewUtil.AnimatedVariable(2000, 100, 2500, 50),
		sphere_phi: new ViewUtil.AnimatedVariable(0, null, null, 0.005),
		sphere_theta: new ViewUtil.AnimatedVariable(0.4, - Math.PI / 2 + 0.01, Math.PI / 2 - 0.01, 0.005),
		sphere_axis: new ViewUtil.AnimatedVariable(0, 0, 0, 0),
		flip: 0,
		up: new THREE.Vector3(0, 0, 1),
		camera: new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 1, 10000 ),
		
		animate: function() {
			this.target.animate();
			this.radius.animate();
			this.sphere_phi.animate();
			this.sphere_theta.animate();
			this.sphere_axis.animate();		
						
			//console.log("camera:    angles=" + this.sphere_phi.value + "|" + this.sphere_theta.value + "|" + this.sphere_axis.value + "    target=" + this.target.value.x + "|" + this.target.value.y + "|" + this.target.value.z);
		},
		
		update: function() {
				var x = this.radius.value * Math.sin(Math.PI/2 - this.sphere_theta.value) * Math.cos(this.sphere_phi.value);
				var y = this.radius.value * Math.sin(Math.PI/2 - this.sphere_theta.value) * Math.sin(this.sphere_phi.value);
				var z = this.radius.value * Math.cos(Math.PI/2 - this.sphere_theta.value); 
										
				//console.log("camera:    pos=" + this.camera.position.x + "|" + this.camera.position.y + "|" + this.camera.position.z + "    rot=" + this.camera.rotation.x + "|" + this.camera.rotation.y + "|" + this.camera.rotation.z);
				this.camera.position.x = x + this.target.value.x;
				this.camera.position.y = y + this.target.value.y;
				this.camera.position.z = z + this.target.value.z;
				//console.log("camera:    pos=" + this.camera.position.x + "|" + this.camera.position.y + "|" + this.camera.position.z + "    rot=" + this.camera.rotation.x + "|" + this.camera.rotation.y + "|" + this.camera.rotation.z);
				//console.log("look at: " + this.target.value.x + "|" + this.target.value.y + "|" + this.target.value.z + ", up: " + this.up);
				this.camera.up = this.up;
				//console.log("camera:    pos=" + this.camera.position.x + "|" + this.camera.position.y + "|" + this.camera.position.z + "    rot=" + this.camera.rotation.x + "|" + this.camera.rotation.y + "|" + this.camera.rotation.z);
				this.camera.lookAt(this.target.value);
				//console.log("camera:    pos=" + this.camera.position.x + "|" + this.camera.position.y + "|" + this.camera.position.z + "    rot=" + this.camera.rotation.x + "|" + this.camera.rotation.y + "|" + this.camera.rotation.z);
				//console.log("camera:    pos=" + this.camera.position.x + "|" + this.camera.position.y + "|" + this.camera.position.z + "    rot=" + this.camera.rotation.x + "|" + this.camera.rotation.y + "|" + this.camera.rotation.z);
				this.camera.rotation.z += this.sphere_axis.value; // otherwise we will always look upright
		}
	};
	
	this.renderer = new THREE.WebGLRenderer({canvas: container, antialias: true, clearColor: 0x000000, clearAlpha: 1 }); 
	
	this.updateSize = function() {
		this.renderer.setSize( window.innerWidth, window.innerHeight, true );
		this.camera.camera.aspect = window.innerWidth / window.innerHeight;
		this.camera.camera.updateProjectionMatrix();
		console.log("updating view size: " + window.innerWidth + "x" + window.innerHeight + " (aspect: " + this.camera.camera.aspect + ")");
	};
	
	console.log("the camera:    pos=" + this.camera.camera.position.x + "|" + this.camera.camera.position.y + "|" + this.camera.camera.position.z + "    rot=" + this.camera.camera.rotation.x + "|" + this.camera.camera.rotation.y + "|" + this.camera.camera.rotation.z);
	this.camera.update();
		
	Events.addEventListener(Events.ONRESIZE, Events.wrapEventHandler(this, this.updateSize), window);	
	Events.fireEvent(window, Events.ONRESIZE);	
}