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
//@requires("THREE")
//@requires("ColorModel")
//@requires("star-vertexshader")
//@requires("star-fragmentshader")

var ViewUtil = {};

ViewUtil.INSTANT = Number.MAX_VALUE;
ViewUtil.INFINITY = new THREE.Vector3(1000000,1000000,1000000);
ViewUtil.WHITE = new THREE.Color(1,1,1);
ViewUtil.BLACK = new THREE.Color(0,0,0);
ViewUtil.RED = new THREE.Color(1,0,0);
ViewUtil.GREEN = new THREE.Color(0,1,0);
ViewUtil.BLUE = new THREE.Color(0,0,1);
ViewUtil.SELECTION_COLOR_0 = new THREE.Color(1,0.16,0.16);
ViewUtil.SELECTION_COLOR_1 = new THREE.Color(0,1,1);
ViewUtil.AMPLITUDE = 500.0;
ViewUtil.INACTIVITY_TIMEOUT = 30000;

ViewUtil.SYSTEM_SIZE_MIN = 10;
ViewUtil.SYSTEM_SIZE_MAX = 30;
ViewUtil.SELECTIONS_MAX = 4;
ViewUtil.SELECTION_MODE_SCREEN_COORDS = 1;  // acurate but slow
ViewUtil.SELECTION_MODE_PROJECTION = 2;		// less acurate but fast
ViewUtil.SELECTION_MODE = ViewUtil.SELECTION_MODE_SCREEN_COORDS;
ViewUtil.SELECTION_ADD = 1;
ViewUtil.SELECTION_REMOVE = 2;
ViewUtil.SELECTION_CHANGE = 3;
	
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

ViewUtil.ColorModel = ColorModel;

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
	
	this.reset();
};

ViewUtil.GalaxyShader = function(imgPath) {

	this.attributes = {
		size: {	type: 'f', value: [] },
		customColor: { type: 'c', value: [] }
	};
	
	this.uniforms = {
		amplitude: { type: "f", value: ViewUtil.AMPLITUDE },
		color:     { type: "c", value: ViewUtil.WHITE },
		texture:   { type: "t", value: THREE.ImageUtils.loadTexture( imgPath ) },
	};
	
	this.vertexShader = ViewUtil.loadShader( "star-vertexshader" );
	this.fragmentShader = ViewUtil.loadShader( "star-fragmentshader" );
		
	this.material = new THREE.ShaderMaterial( {
		uniforms: 		this.uniforms,
		attributes:     this.attributes,
		vertexShader:   this.vertexShader,
		fragmentShader: this.fragmentShader,
		blending: 		THREE.AdditiveBlending,
		depthTest: 		false,
		transparent:	true,
	});
};

ViewUtil.Galaxy = function(systems) {

	this.systems = systems; // TODO think about copying the array?!
	this.colorModel 	= ViewUtil.ColorModel.white;
	
	{ // particles for all systems
		this.systemShader = new ViewUtil.GalaxyShader("images/star.png");
		
		this.systemGeometry = new THREE.Geometry();
		this.systemGeometry.vertices = new Array(this.systems.length); // can't change the amount later!!!
		for(var v = 0; v < this.systemGeometry.vertices.length; v++)
		{
			this.systemGeometry.vertices[v] = ViewUtil.INFINITY.clone();
			this.systemShader.attributes.size.value[v] = 0;
			this.systemShader.attributes.customColor.value[v] = ViewUtil.BLACK.clone();
		}
		this.systemGeometry.dynamic = true;
		
		this.systemParticles = new THREE.ParticleSystem(this.systemGeometry, this.systemShader.material);
		this.systemParticles.dynamic = true;
	}
	
	{ // particles for selected system(s)
		this.selectionShader = new ViewUtil.GalaxyShader("images/circle.png");
		this.selectionShader.material.blending = THREE.NormalBlending; // marker must always be in front of view
		
		this.selectionGeometry = new THREE.Geometry();
		this.selectionGeometry.vertices = new Array(ViewUtil.SELECTIONS_MAX); // can't change the amount later!!!
		for(var v = 0; v < this.selectionGeometry.vertices.length; v++)
		{
			this.selectionGeometry.vertices[v] = ViewUtil.INFINITY.clone();
			this.selectionShader.attributes.size.value[v] = 0;
			this.selectionShader.attributes.customColor.value[v] = ViewUtil.BLACK.clone();
		}
		this.selectionGeometry.dynamic = true;
		
		this.selectionParticles = new THREE.ParticleSystem(this.selectionGeometry, this.selectionShader.material);
		this.selectionParticles.dynamic = true;
	}

	this.info = new ViewUtil.GalaxyInfo();
			
	for(var s = 0; s < this.systems.length; s++)
	{
		this.systems[s].index = s;
		this.systems[s].galaxy = this;
		this.systems[s].updateColor();
		this.info.update(this.systems[s]);
	}
	
	this.selections = new Array(ViewUtil.SELECTIONS_MAX);
		
	this.select = function(system, selectionIndex) {
		if(selectionIndex == null)
			selectionIndex = 0;
			
		if(selectionIndex > this.selections.length)
			throw new Error("index exceeding max selections");
			
		this.selections[selectionIndex] = system;		
		return selectionIndex;
	};
	
	this.deselect = function(selectionIndex) {
		var system = this.selections[selectionIndex];
		this.select(null, selectionIndex);
		return system;
	};
	
	this.animate = function(time) {
		for(var s = 0; s < this.systems.length; s++)
		{
			if(this.systems[s])
				this.systems[s].animate(time);	
		}
		this.systemGeometry.verticesNeedUpdate = true;
		this.systemShader.attributes.size.needsUpdate = true;	
		this.systemShader.attributes.customColor.needsUpdate = true;
		
		for(var s = 0; s < this.selections.length; s++)
		{
			if(this.selections[s])
			{				
				this.selectionGeometry.vertices[s] = this.selections[s].coords.value.clone();
				this.selectionShader.attributes.size.value[s] = this.selections[s].displaySize.value * 1.5;
				if(s == 0)
					this.selectionShader.attributes.customColor.value[s] = ViewUtil.SELECTION_COLOR_0.clone();
				else
					this.selectionShader.attributes.customColor.value[s] = ViewUtil.SELECTION_COLOR_1.clone();
			}
			else
			{
				this.selectionGeometry.vertices[s] = ViewUtil.INFINITY.clone();
				this.selectionShader.attributes.size.value[s] = 0;
				this.selectionShader.attributes.customColor.value[s] = ViewUtil.BLACK.clone();
			}
		}
		this.selectionGeometry.verticesNeedUpdate = true;
		this.selectionShader.attributes.size.needsUpdate = true;	
		this.selectionShader.attributes.customColor.needsUpdate = true;	
	};
	
	this.updateColors = function(colorModel)
	{
		if(colorModel)
			this.colorModel = colorModel;
		for(i = 0; i < this.systems.length; i++)
			this.systems[i].updateColor();
	};
};

ViewUtil.System = function(id, x, y, z, size, heat) {
	
	var animationSpeed 	= 100;
	this.id 			= id;
	this.coords 		= new ViewUtil.AnimatedVector3(new THREE.Vector3(x,y,z), animationSpeed);
	// static properties (unchanged per match)
	this.size 			= new ViewUtil.AnimatedVariable(size, 0, 1e3, animationSpeed); 
	this.heat 			= new ViewUtil.AnimatedVariable(heat, 0, 1e3, animationSpeed);
	this.habitability 	= new ViewUtil.AnimatedVariable(0, 	  0, 1e3, animationSpeed);
	// dynamic properties
	this.infrastructure = new ViewUtil.AnimatedVariable(0, 0, 1e12, animationSpeed);
	this.population 	= new ViewUtil.AnimatedVariable(0, 0, 1e12, animationSpeed);
	this.maxPopulation 	= new ViewUtil.AnimatedVariable(0, 0, 1e12, animationSpeed);
	// calculated properties
	// additional properties
	var dispSize = Math.log10(size)/3*(ViewUtil.SYSTEM_SIZE_MAX-ViewUtil.SYSTEM_SIZE_MIN) + ViewUtil.SYSTEM_SIZE_MIN;
	this.displaySize 	= new ViewUtil.AnimatedVariable(dispSize, ViewUtil.SYSTEM_SIZE_MIN, ViewUtil.SYSTEM_SIZE_MAX, animationSpeed); 
	this.color 			= new ViewUtil.AnimatedColor(new THREE.Color(), animationSpeed);
	// other variables
	this.infoElement 	= null; // a reference to the element displaying the information for the system
	
	this.firstAnimation = true;
	
	this.updateColor = function() {
		var x = this.coords.target.x;
		var y = this.coords.target.y;
		var z = this.coords.target.z;
		this.radius = Math.sqrt(x*x + y*y + z*z);
		this.color.target = this.galaxy.colorModel.getRGB(	this.size.target / this.size.max,
													this.heat.target / this.heat.max,
													this.habitability.target / this.habitability.max, 
													Math.log10(this.infrastructure.target) / Math.log10(this.infrastructure.max),
													Math.log10(this.population.target) / Math.log10(this.population.max), 
													Math.log10(this.maxPopulation.target) / Math.log10(this.maxPopulation.max),
													this.radius / this.galaxy.info.maxR
													);
	};
	
	this.animate = function(time) {
		if(this.coords.animate(time) || this.firstAnimation)
		{
			this.galaxy.systemGeometry.vertices[this.index] = this.coords.value;
			this.galaxy.systemGeometry.verticesNeedUpdate = true;
		}
		if(this.displaySize.animate(time) || this.firstAnimation)
		{
			this.galaxy.systemShader.attributes.size.value[this.index] = this.displaySize.value;
			this.galaxy.systemShader.attributes.size.needsUpdate = true;	
		}
		if(this.color.animate(time) || this.firstAnimation)
		{
			this.galaxy.systemShader.attributes.customColor.value[this.index] = this.color.value;
			this.galaxy.systemShader.attributes.customColor.needsUpdate = true;	
		}

		this.size.animate(time);
		this.heat.animate(time);
		this.habitability.animate(time);
		this.infrastructure.animate(time);
		this.population.animate(time);
		this.maxPopulation.animate(time);
		
		this.firstAnimation = false;
	};
};

ViewUtil.EventManager = function(view)
{
	this.view = view;
	this.lastX = 0;
	this.lastY = 0;
	this.lastEvent = null;
	this.inDragMode = false;
	this.dragEventCount = 0;
	this.projector = new THREE.Projector();
	this.inactivityTimeout = null;
	
	this.inactivityAction = function()
	{
		// start camera rotation
		this.view.camera.sphere_phi.target += 1000000;
	};
	
	this.resetInactivityTimeout = function() 	{
		// reset timeout
		if(this.inactivityTimeout)
			clearTimeout(this.inactivityTimeout);
		// reset camere rotation
		this.view.camera.sphere_phi.target = this.view.camera.sphere_phi.value;
		// set new timeout
		this.inactivityTimeout = setTimeout(function(eventManager) {
			return function() {
				eventManager.inactivityAction();
			};
		}(this), ViewUtil.INACTIVITY_TIMEOUT);
	};
		
	this.handleDragStart = function(event) {
		this.lastX = event.pageX;
		this.lastY = event.pageY;
		this.inDragMode = true;
		this.dragEventCount = 0;
		this.lastEvent = event; // save event, to be able to determin button and modifiers later
		this.resetInactivityTimeout();
	};
		
	this.handleDragStop = function(event) {
		this.inDragMode = false;
		if(this.dragEventCount < 5)
			this.handleClick(event);
	};
		
	this.handleDrag = function(event) {
		if(this.inDragMode)
		{
			var x = event.pageX;
			var y = event.pageY;
			
			this.view.camera.sphere_phi.target = this.view.camera.sphere_phi.value - (x-this.lastX)/window.innerWidth * Math.PI * 1.5;
			this.view.camera.sphere_theta.target = this.view.camera.sphere_theta.value + (y-this.lastY)/window.innerHeight * Math.PI * 1.5;
			
			this.view.camera.sphere_phi.animate(ViewUtil.INSTANT);
			this.view.camera.sphere_theta.animate(ViewUtil.INSTANT);
			
			this.lastX = x;
			this.lastY = y;
			
			this.dragEventCount++;

			this.resetInactivityTimeout();
		}
	};
	
	this.handleScroll = function(event) {				
		var event = window.event || event;
		event.preventDefault();
		
		var delta = Math.max(-1, Math.min(1, (event.wheelDelta || -event.detail)));

		this.view.camera.radius.target -= delta * 100;
		
		this.resetInactivityTimeout();
		
		return false;			
	};
	
	this.onSelect = function(system) {
		// for overriding
	};
	this.onDeselect = function(system) {
		// for overriding
	};
	
	this.handleClick = function(event) {
		var clickTarget = null;
		if(ViewUtil.SELECTION_MODE == ViewUtil.SELECTION_MODE_SCREEN_COORDS)
		{
			var mouseX = event.clientX;
			var mouseY = event.clientY;
		
			var p;
			var dist2;
			var screenCoords;
			var screenSize;
			var zMin = 10000000;
			var distMin = 10000000;
			
			for(var i = 0; i < this.view.galaxy.systems.length; i++)
			{
				p = this.view.galaxy.systems[i];
				if(p.size.value == 0)
					continue;
					
				screenCoords = this.view.getScreenCoords(p.coords.value);
				screenSize = this.view.getScreenSize(p.coords.value, p.displaySize.value);
				dist2 = (screenCoords.x-mouseX)*(screenCoords.x-mouseX) + (screenCoords.y-mouseY)*(screenCoords.y-mouseY);

				if((dist2 < distMin || (dist2 == distMin && screenCoords.z < zMin)) && dist2 < screenSize*screenSize/4)
				{
					zMin = screenCoords.z;
					distMin = dist2;
					clickTarget = p;
				}
			}
		}
		else if(ViewUtil.SELECTION_MODE == ViewUtil.SELECTION_MODE_PROJECTION)
		{
			var mouseX = ( event.clientX / window.innerWidth ) * 2 - 1;
			var mouseY = - ( event.clientY / window.innerHeight ) * 2 + 1;	
		
			var vector = new THREE.Vector3( mouseX, mouseY, 1 );				
			this.projector.unprojectVector( vector, this.view.camera.camera );
			var ray = new THREE.Ray( this.view.camera.camera.position, vector.sub( this.view.camera.camera.position ).normalize() );
			var o = ray.origin;
			var d = ray.direction;
			var p;
			var n = new THREE.Vector3(0,0,0); // normal vector to line = (o-p)-((o-p)*d)d // * = dot-product
			var dist2; // = (|n|)^2 // use square to avoid sqrt
			var opd; // temp for (o-p)*d
			var opdMin = 10000000;
			var dist2Min = 10000000;
			
			for(var i = 0; i < this.view.galaxy.systems.length; i++)
			{
				p = this.view.galaxy.systems[i].coords.value;
				size = this.view.galaxy.systems[i].size.value;
				if(size == 0)
					continue;
				
				opd = (p.x-o.x)*d.x + (p.y-o.y)*d.y + (p.z-o.z)*d.z;
				// opd is in direction to n (intersection have positive opd)
				if(opd < 0) // wrong direction (behind the camera)
					continue;
				n.x = (o.x - p.x)+opd*d.x;
				n.y = (o.y - p.y)+opd*d.y;
				n.z = (o.z - p.z)+opd*d.z;
				dist2 = (n.x*n.x + n.y*n.y + n.z*n.z);
				if(opd < opdMin && dist2 <= size*size/4)
				{
					opdMin = opd;
					clickTarget = this.view.galaxy.systems[i];
				}									
			}
		}
		else
		{
			console.error("invalid selection mode");
		}
		
		// handle left and right click
		if(this.lastEvent.button == Events.BUTTON_LEFT) // left click
		{
			// TODO check if it was right-click selected before
			if(this.lastEvent.ctrlKey && clickTarget != null)
			{
				// check if target was selected before
				var previousIndex = -1;
				var numberOfSelections = 0;
				for(var i = 1; i < ViewUtil.SELECTIONS_MAX; i++)
				{
					if(this.view.galaxy.selections[i] == clickTarget)
						previousIndex = i;
					if(this.view.galaxy.selections[i] != null)
						numberOfSelections++;
				}
				if(previousIndex != -1)
				{
					// was selected before -> deselect
					this.view.deselect(previousIndex);
					for(var i = previousIndex + 1; i < ViewUtil.SELECTIONS_MAX; i++)
					{
						var s = this.view.deselect(i);
						this.view.select(s, i-1);
						if(s != null)
							this.onSelectionChange(s, false, ViewUtil.SELECTION_CHANGE);
					}
					this.onSelectionChange(clickTarget, false, ViewUtil.SELECTION_REMOVE);
				}
				else
				{
					// was not selected before -> add to selection if possible
					if(numberOfSelections+1 < ViewUtil.SELECTIONS_MAX)
					{
						this.view.select(clickTarget, numberOfSelections+1);
						this.onSelectionChange(clickTarget, false, ViewUtil.SELECTION_ADD);
					}
				}
			}
			else
			{
				// deselect all
				for(var i = 1; i < ViewUtil.SELECTIONS_MAX; i++)
				{
					var sys = this.view.deselect(i);
					if(sys != null)
						this.onSelectionChange(sys, false, ViewUtil.SELECTION_REMOVE);
				}
				if(clickTarget != null)
				{
					this.view.select(clickTarget, 1);
					this.onSelectionChange(clickTarget, false, ViewUtil.SELECTION_ADD);
				}
			}
		}
		else if(this.lastEvent.button == Events.BUTTON_RIGHT) // right click
		{
			// TODO check if it was left-click selected before
			if(clickTarget != this.view.galaxy.selections[0])
			{
				var previousSys = this.view.deselect(0); // right click is always stored at first position
				this.view.select(clickTarget, 0);
				
				if(previousSys != null)
					this.onSelectionChange(previousSys, true, ViewUtil.SELECTION_REMOVE);
	
				if(clickTarget == null)
				{
					this.view.camera.target.target = new THREE.Vector3(0,0,0);
				}
				else
				{
					this.onSelectionChange(clickTarget, true, ViewUtil.SELECTION_ADD);
					this.view.camera.target.target = clickTarget.coords.value;
				}
			}
		}
	};
	
	Events.addEventListener("mousedown", Events.wrapEventHandler(this, this.handleDragStart), this.view.canvas);
	Events.addEventListener("mousemove", Events.wrapEventHandler(this, this.handleDrag), this.view.canvas);
	Events.addEventListener("mouseup", Events.wrapEventHandler(this, this.handleDragStop), this.view.canvas);
	Events.addEventListener("DOMMouseScroll", Events.wrapEventHandler(this, this.handleScroll), this.view.canvas);
	Events.addEventListener("contextmenu", function(e) { e.preventDefault(); return false; }, this.view.canvas);
};

// for debugging without Request.js - START
/*
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
*/
// for debugging without Request.js - END







var View = function(container, stats, debug) {
	
	this.container = container;
	this.stats = stats;
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
	
	this.getScreenSize = function(vector, size) { //system) {
		var p1 = vector; //system.coords.value;
		//var size = system.size.value;
		var p2 = this.camera.camera.position;
		var dist = Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y) + (p1.z-p2.z)*(p1.z-p2.z));
		return size / (2 * Math.tan((this.camera.camera.fov * Math.PI / 180) / 2) * dist) * this.container.offsetWidth / 2;
	};
	
	if(debug)
	{
		var g = new THREE.CubeGeometry(10,10,10);
		var m = new THREE.MeshBasicMaterial( { color: 0x00ff00 } );
		var cube = new THREE.Mesh( g, m );
		this.scene.add(cube);
	}
	
	this.load = function(galaxy) {
		console.log("loading " + galaxy.systems.length + " systems...");
		if(this.galaxy != null)
		{
			console.log("removing previous galaxy...");
			// remove the old particle system(s)
			// TODO animate?!
			if(this.galaxy.systemParticles != null)
				this.scene.remove(this.galaxy.systemParticles);
			if(this.galaxy.selectionParticles != null)
				this.scene.remove(this.galaxy.selectionParticles);
				
			delete this.galaxy;
		}
		
		console.log("adding new galaxy...");
		this.galaxy = galaxy;
		// add the new particle system
		// TODO animate?!
		this.scene.add(this.galaxy.systemParticles);
		this.scene.add(this.galaxy.selectionParticles);			
		
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
	
	this.animate = function() {
		requestAnimationFrame( function(view) { return function() { view.animate() }; }(this) );
		this.render();
		if(this.stats)
			this.stats.update();	
	};
	
	//this.selections = new Array(ViewUtil.SELECTIONS_MAX);
		
	this.select = function(system, selectionIndex) {
		return this.galaxy.select(system, selectionIndex);
	};
	
	this.deselect = function(selectionIndex) {
		return this.galaxy.deselect(selectionIndex);
	};	
	
	this.eventManager = new ViewUtil.EventManager(this);
	
	console.log("the camera:    pos=" + this.camera.camera.position.x + "|" + this.camera.camera.position.y + "|" + this.camera.camera.position.z + "    rot=" + this.camera.camera.rotation.x + "|" + this.camera.camera.rotation.y + "|" + this.camera.camera.rotation.z);
	this.camera.update();
			
	Events.addEventListener(Events.ONRESIZE, Events.wrapEventHandler(this, this.updateSize), window);	
	Events.fireEvent(window, Events.ONRESIZE);	
};