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
	
	this.animate = function()
	{
		if(this.min != null && this.target < this.min)
			this.target = this.min;
		if(this.max != null && this.target > this.max)
			this.target = this.max;
			
		if(this.value < (this.target - this.stepSize))
			this.value += this.stepSize;
		else if(this.value > (this.target + this.stepSize))
			this.value -= this.stepSize;
		else
			this.value = this.target;
	};
};

ViewUtil.AnimatedVector = function(initialValue, animationStepSize) {
	this.value = initialValue.clone();
	this.target = initialValue.clone();
	this.stepSize = animationStepSize;
	
	this.animate = function()
	{
		var dx = this.target.x - this.value.x;
		var dy = this.target.y - this.value.y;
		var dz = this.target.z - this.value.z;
		var dist2 = dx*dx + dy*dy + dz*dz;
		
		if(dist2 < (this.stepSize*this.stepSize))
		{
			// copy each coordinate individually
			// otherwise we would have the same object and manipulating the target-object would not be possible
			this.value.x = this.target.x;
			this.value.y = this.target.y;
			this.value.z = this.target.z;
		}
		else
		{
			var s = Math.abs(this.stepSize) / Math.sqrt(dist2);
			this.value.x += s*dx;
			this.value.y += s*dy;
			this.value.z += s*dz;
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

	this.camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 1, 10000 );
	
	this.renderer = new THREE.WebGLRenderer({canvas: container, antialias: true, clearColor: 0x000000, clearAlpha: 1 }); 
	
	this.updateSize = function() {
		this.renderer.setSize( window.innerWidth, window.innerHeight, true );
		this.camera.aspect = window.innerWidth / window.innerHeight;
		this.camera.updateProjectionMatrix();
		console.log("updating view size: " + window.innerWidth + "x" + window.innerHeight + " (aspect: " + this.camera.aspect + ")");
	};
		
	Events.addEventListener(Events.ONRESIZE, Events.wrapEventHandler(this, this.updateSize), window);	
	Events.fireEvent(window, Events.ONRESIZE);	
}