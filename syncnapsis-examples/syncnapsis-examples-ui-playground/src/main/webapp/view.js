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
//@requires("vertexshader")
//@requires("fragmentshader")

var ViewUtil = {};

ViewUtil.INSTANT = Number.MAX_VALUE;
ViewUtil.INFINITY = new THREE.Vector3(1000000,1000000,1000000);
ViewUtil.WHITE = new THREE.Color(1,1,1);
ViewUtil.BLACK = new THREE.Color(0,0,0);
ViewUtil.AMPLITUDE = 500.0;

ViewUtil.SYSTEM_SIZE_MIN = 10;
ViewUtil.SYSTEM_SIZE_MAX = 30;
	
ViewUtil.loadShader = function(name) {
	var index = DependencyManager.indexOf(name);
	return DependencyManager.scriptContents[index];
};

ViewUtil.AnimatedVariable = function(initialValue, min, max, animationSpeed) {
	this.value = initialValue;
	this.target = initialValue;
	this.min = min;
	this.max = max;	
	this.speed = animationSpeed;	
	
	this.animate = function(time) {
		if(this.min != null && this.target < this.min)
			this.target = this.min;
		if(this.max != null && this.target > this.max)
			this.target = this.max;
			
		var step = this.speed * time / 1000;
			
		var dist = Math.abs(this.target - this.value);
		var sgn = Math.sign(this.target - this.value);
		
		var changed;
		if(time == ViewUtil.INSTANT || dist < step)
		{
			changed = (this.value != this.target);
			this.value = this.target;
		}
		else 
		{
			changed = true;
			this.value += sgn*step;		
		}
		
		return changed;
	};
};

ViewUtil.AnimatedVector3 = function(initialValue, animationSpeed) {
	this.value = initialValue.clone();
	this.target = initialValue.clone();
	this.speed = animationSpeed;
	
	this.animate = function(time) {
		var dx = this.target.x - this.value.x;
		var dy = this.target.y - this.value.y;
		var dz = this.target.z - this.value.z;
		var dist2 = dx*dx + dy*dy + dz*dz;
		
		var step = this.speed * time / 1000;
		
		var changed;
		if(time == ViewUtil.INSTANT || dist2 < (step*step))
		{
			changed = ((this.value.x != this.target.x) || (this.value.y != this.target.y) || (this.value.z != this.target.z));
			// copy each coordinate individually
			// otherwise we would have the same object and manipulating the target-object would not be possible
			this.value.x = this.target.x;
			this.value.y = this.target.y;
			this.value.z = this.target.z;
		}
		else
		{
			changed = true;
			var anim = 2; // DECIDE WHICH OPTION TO USE...
			if(anim == 1)
			{
				var max = Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)));
				this.value.x += dx/max * step;
				this.value.y += dy/max * step;
				this.value.z += dz/max * step;
			}
			else
			{
				var s = Math.abs(step) / Math.sqrt(dist2);
				this.value.x += s*dx;
				this.value.y += s*dy;
				this.value.z += s*dz;
			}
		}
		
		return changed;
	};
};

ViewUtil.AnimatedColor = function(initialValue, animationSpeed) {
	this.value = initialValue.clone();
	this.target = initialValue.clone();
	this.speed = animationSpeed;
	
	this.animate = function(time) {
		var dx = this.target.r - this.value.r;
		var dy = this.target.g - this.value.g;
		var dz = this.target.b - this.value.b;
		var dist2 = dx*dx + dy*dy + dz*dz;
		
		var step = this.speed * time / 1000;
		
		var changed;
		if(time == ViewUtil.INSTANT || dist2 < (step*step))
		{
			changed = ((this.value.r != this.target.r) || (this.value.g != this.target.g) || (this.value.b != this.target.b));
			// copy each coordinate individually
			// otherwise we would have the same object and manipulating the target-object would not be possible
			this.value.r = this.target.r;
			this.value.g = this.target.g;
			this.value.b = this.target.b;
		}
		else
		{
			changed = true;
			var anim = 2; // DECIDE WHICH OPTION TO USE...
			if(anim == 1)
			{
				var max = Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)));
				this.value.r += dx/max * step;
				this.value.g += dy/max * step;
				this.value.b += dz/max * step;
			}
			else
			{
				var s = Math.abs(step) / Math.sqrt(dist2);
				this.value.r += s*dx;
				this.value.g += s*dy;
				this.value.b += s*dz;
			}
		}
		
		return changed;
	};
};

ViewUtil.ColorModel = {
	white: {
		name: "white",
		getRGB: function(size, heat, radius) {
			return new THREE.Color(1,1,1);
		}
	}
}; 

ViewUtil.GalaxyInfo = function() {
	this.reset = function() {
		this.minX = 1000000000;
		this.minY = 1000000000;
		this.minZ = 1000000000;
		this.maxX = -1000000000;
		this.maxY = -1000000000;
		this.maxZ = -1000000000;
		this.maxR = 0;
	};
	
	this.update = function(system) {
		if(system.coords.value.x < this.minX)	this.minX = system.coords.value.x;
		if(system.coords.value.x > this.maxX)	this.maxX = system.coords.value.x;
		if(system.coords.value.y < this.minY)	this.minY = system.coords.value.y;
		if(system.coords.value.y > this.maxY)	this.maxY = system.coords.value.y;
		if(system.coords.value.z < this.minZ)	this.minZ = system.coords.value.z;
		if(system.coords.value.z > this.maxZ)	this.maxZ = system.coords.value.z;
		if(system.radius > this.maxR) 			this.maxR = system.radius;
	};
};

ViewUtil.GalaxyShader = function() {

	this.attributes = {
		size: {	type: 'f', value: [] },
		customColor: { type: 'c', value: [] }
	};
	
	this.uniforms = {
		amplitude: { type: "f", value: ViewUtil.AMPLITUDE },
		color:     { type: "c", value: ViewUtil.WHITE },
		texture:   { type: "t", value: THREE.ImageUtils.loadTexture( "img/star.png" ) },
	};
	
	this.uniformsSelected = {
			amplitude: { type: "f", value: ViewUtil.AMPLITUDE },
			color:     { type: "c", value: ViewUtil.WHITE },
			texture:   { type: "t", value: THREE.ImageUtils.loadTexture( "img/circle.png" ) },
	};
	
	this.vertexShader = ViewUtil.loadShader( "vertexshader" );
	this.fragmentShader = ViewUtil.loadShader( "fragmentshader" );
		
	this.materialNormal = new THREE.ShaderMaterial( {
		uniforms: 		this.uniforms,
		attributes:     this.attributes,
		vertexShader:   this.vertexShader,
		fragmentShader: this.fragmentShader,
		blending: 		THREE.AdditiveBlending,
		depthTest: 		false,
		transparent:	true,
	});
	
	this.materialSelected = new THREE.ShaderMaterial( {
		uniforms: 		this.uniformsSelected,
		attributes:     this.attributes,
		vertexShader:   this.vertexShader,
		fragmentShader: this.fragmentShader,
		blending: 		THREE.AdditiveBlending,
		depthTest: 		false,
		transparent:	true,
	});
};

ViewUtil.Galaxy = function(systems) {

	this.shader = new ViewUtil.GalaxyShader();

	this.systems = systems; // TODO think about copying the array?!
	
	this.particles = new THREE.Geometry();
	this.particles.vertices = new Array(this.systems.length); // can't change the amount later!!!
	for(var v = 0; v < this.particles.vertices.length; v++)
	{
		this.particles.vertices[v] = ViewUtil.INFINITY.clone();
		this.shader.attributes.size.value[v] = 0;
		this.shader.attributes.customColor.value[v] = ViewUtil.BLACK.clone();
	}
	this.particles.dynamic = true;
	
	this.particleSystem = new THREE.ParticleSystem(this.particles, this.shader.materialNormal)
	this.particleSystem.dynamic = true;

	this.info = new ViewUtil.GalaxyInfo();
			
	for(var s = 0; s < this.systems.length; s++)
	{
		this.systems[s].index = s;
		this.systems[s].galaxy = this;
		this.systems[s].updateColor();
		this.info.update(this.systems[s]);
	}
	
	this.animate = function(time) {
		for(var s = 0; s < this.systems.length; s++)
		{
			if(this.systems[s])
				this.systems[s].animate(time);	
		}
	};
};

ViewUtil.System = function(x, y, z, size, heat) {
	
	var animationSpeed = 100;
	this.coords = new ViewUtil.AnimatedVector3(new THREE.Vector3(x,y,z), animationSpeed);
	this.size = new ViewUtil.AnimatedVariable(size, 10, 30, animationSpeed); 
	this.heat = new ViewUtil.AnimatedVariable(heat, 0, 1, animationSpeed); 
	this.color = new ViewUtil.AnimatedColor(new THREE.Color(), animationSpeed);
	this.colorModel = ViewUtil.ColorModel.white;
	
	this.firstAnimation = true;
	
	this.updateColor = function() {
		var x = this.coords.target.x;
		var y = this.coords.target.y;
		var z = this.coords.target.z;
		this.radius = Math.sqrt(x*x + y*y + z*z) / this.galaxy.info.maxR;
		this.color.target = this.colorModel.getRGB(this.size, this.heat, this.radius);
	};
	
	this.animate = function(time) {
		if(this.coords.animate(time) || this.firstAnimation)
		{
			this.galaxy.particles.vertices[this.index] = this.coords.value;
			this.galaxy.particleSystem.geometry.verticesNeedUpdate = true;
		}
		if(this.size.animate(time) || this.firstAnimation)
		{
			this.galaxy.shader.attributes.size.value[this.index] = this.size.value;
			this.galaxy.shader.attributes.size.needsUpdate = true;	
		}
		if(this.color.animate(time) || this.firstAnimation)
		{
			this.galaxy.shader.attributes.customColor.value[this.index] = this.color.value;
			this.galaxy.shader.attributes.customColor.needsUpdate = true;	
		}
		
		this.heat.animate(time);
		
		this.firstAnimation = false;
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
	gl_PointSize = size * ( amplitude / length( mvPosition.xyz ) );\
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
	
	this.container = container;
	this.canvas = document.createElement("canvas");
	this.container.appendChild(this.canvas);
	
	this.materials = {
		planeT: new THREE.MeshBasicMaterial( { opacity:0.2 , wireframe: false, transparent:	true, side: THREE.DoubleSide } ),
		planeW: new THREE.MeshBasicMaterial( { opacity:0.6 , wireframe: true } ),	
		invisible: new THREE.MeshBasicMaterial( { visible: false} ),
	};	

	this.camera = {
		target: new ViewUtil.AnimatedVector3(new THREE.Vector3(0,0,0), 500),
		radius: new ViewUtil.AnimatedVariable(2000, 100, 2500, 2500),
		sphere_phi: new ViewUtil.AnimatedVariable(0, null, null, 0.25),
		sphere_theta: new ViewUtil.AnimatedVariable(0.4, - Math.PI / 2 + 0.01, Math.PI / 2 - 0.01, 0.25),
		sphere_axis: new ViewUtil.AnimatedVariable(0, 0, 0, 0),
		flip: 0,
		up: new THREE.Vector3(0, 0, 1),
		camera: new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 1, 10000 ),
		projection: new THREE.Matrix4(), // set in update function
		
		animate: function(time) {
			this.target.animate(time);
			this.radius.animate(time);
			this.sphere_phi.animate(time);
			this.sphere_theta.animate(time);
			this.sphere_axis.animate(time);		
						
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
				
				this.projection.multiplyMatrices(this.camera.projectionMatrix, this.camera.matrixWorldInverse);
		}
	};
	
	this.renderer = new THREE.WebGLRenderer({canvas: this.canvas, antialias: true, clearColor: 0x000000, clearAlpha: 1 }); 
	this.scene = new THREE.Scene(); 
	
	this.updateSize = function() {
		this.renderer.setSize( this.container.offsetWidth, this.container.offsetHeight, true );
		this.camera.camera.aspect = this.container.offsetWidth / this.container.offsetHeight;
		this.camera.camera.updateProjectionMatrix();
		console.log("updating view size: " + this.container.offsetWidth + "x" + this.container.offsetHeight + " (aspect: " + this.camera.camera.aspect + ")");
	};
	
	this.getScreenCoords = function(vector) {
		var vec = vector.clone();
		vec.applyProjection(this.camera.projection);
		vec.x = (vec.x + 1) * this.container.offsetWidth / 2 + this.container.offsetLeft;
		vec.y = (-vec.y + 1) * this.container.offsetHeight / 2 + this.container.offsetTop;
		return vec;
	};
	
	this.getScreenSize = function(system) {
		var p1 = system.coords.value;
		var p2 = this.camera.camera.position;
		var dist = Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y) + (p1.z-p2.z)*(p1.z-p2.z));
		return system.size.value / (2 * Math.tan((camera.camera.fov * Math.PI / 180) / 2) * dist);
	};
	
	// TODO for debug only
	var g = new THREE.CubeGeometry(100,100,100);
	var m = new THREE.MeshBasicMaterial( { color: 0x00ff00 } );
	var cube = new THREE.Mesh( g, m );
	this.scene.add(cube);
	// TODO end
	
	this.load = function(sectors, stepSize) {
		console.log("loading " + sectors.length + " sectors...");
		if(stepSize == undefined)
			stepSize = 1;	
			
		if(this.galaxy != null && this.galaxy.particleSystem != null)
		{
			console.log("removing previous galaxy...");
			// TODO animate?!
			
			// remove the old particle system
			this.scene.remove(this.galaxy.particleSystem);
			delete this.galaxy;
		}
		
		console.log("parsing sectors...");
		
		var systems = new Array();
		var x, y, z, size, heat;
				
		for(var s = 0; s < sectors.length; s += stepSize)
		{			
			x = sectors[s][0];
			y = sectors[s][1];
			z = sectors[s][2];
			size = Math.random()*(ViewUtil.SYSTEM_SIZE_MAX-ViewUtil.SYSTEM_SIZE_MIN)+ViewUtil.SYSTEM_SIZE_MIN; // TODO
			heat = Math.random(); // TODO
			systems[systems.length] = new ViewUtil.System(x, y, z, size, heat);
		};
		
		// create a new galaxy object
		console.log("creating galaxy...");
		this.galaxy = new ViewUtil.Galaxy(systems);//, this.shaders.attributes, this.materials.system);
		// add the new particle system
		console.log("adding galaxy...");
		this.scene.add(this.galaxy.particleSystem);			
		
		// TODO animate?!
	};
	
	this.lastAnimation = new Date().getTime();
	
	this.render = function() {
		var now = new Date().getTime();
		var time = now - this.lastAnimation;
		this.lastAnimation = now;
		
		if(this.galaxy != null)
			this.galaxy.animate(time);
		this.camera.animate(time);
		
		this.camera.update();
		this.renderer.render( this.scene, this.camera.camera );
	};
	
	console.log("the camera:    pos=" + this.camera.camera.position.x + "|" + this.camera.camera.position.y + "|" + this.camera.camera.position.z + "    rot=" + this.camera.camera.rotation.x + "|" + this.camera.camera.rotation.y + "|" + this.camera.camera.rotation.z);
	this.camera.update();
		
	Events.addEventListener(Events.ONRESIZE, Events.wrapEventHandler(this, this.updateSize), window);	
	Events.fireEvent(window, Events.ONRESIZE);	
}