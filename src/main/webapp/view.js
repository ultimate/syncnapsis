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

var View = {};

View.loadShader = function(name) {
	var index = DependencyManager.indexOf(name);
	return DependencyManager.scriptContents[index];
};

// for debugging without Request.js - START
View.loadShader = function(name) {
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

View.shaders = {
	attributes: {
		size: {	type: 'f', value: [] },
		customColor: { type: 'c', value: [] }
	},
	uniforms: {
		amplitude: { type: "f", value: 1.0 },
		color:     { type: "c", value: new THREE.Color( 0xffffff ) },
		texture:   { type: "t", value: THREE.ImageUtils.loadTexture( "img/star.png" ) },
	},
	vertexShader:   View.loadShader( "vertexshader" ),
	fragmentShader: View.loadShader( "fragmentshader" ),
	sizeMin: 10,
	sizeMax: 30,
};

View.materials = {
	initial: new THREE.MeshBasicMaterial( { color: 0xff0000, wireframe: true } ),
	selected: new THREE.MeshBasicMaterial( { color: 0x000000, wireframe: true } ),
	neighbor: new THREE.MeshBasicMaterial( { color: 0xff0099, wireframe: true } ),
	inactive:  new THREE.ShaderMaterial( {
		uniforms: 		View.shaders.uniforms,
		attributes:     View.shaders.attributes,
		vertexShader:   View.shaders.vertexShader,
		fragmentShader: View.shaders.fragmentShader,
		blending: 		THREE.AdditiveBlending,
		depthTest: 		false,
		transparent:	true,
	}),
	planesXY: new THREE.MeshBasicMaterial( { color: 0x00ff99 , opacity:0.6 , wireframe: true } ),
	planesXZ: new THREE.MeshBasicMaterial( { color: 0xff9900 , opacity:0.6 , wireframe: true } ),
	planesYZ: new THREE.MeshBasicMaterial( { color: 0x9900ff , opacity:0.6 , wireframe: true } ),
	planesInvisible: new THREE.MeshBasicMaterial( { visible: false} ),
};