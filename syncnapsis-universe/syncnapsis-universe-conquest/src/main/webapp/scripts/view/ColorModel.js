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
ColorModel = {
		// TODO update for more system properties
	/**
	 * each ColorModel must be of the following structure:
	 * {
	 *   name: "colormodel_x",
	 *   getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
	 *   	// calculate RGB
	 *   	return new THREE.Color(r, g, b);
	 *   }
	 * }  
	 */
	white: {
		name: "white",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			return new THREE.Color(1,1,1);
		}
	},	
	yellowwhite1: {
		name: "yellowwhite1",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			return new THREE.Color(1,1,Math.sqrt(heat));
		}
	},	
	yellowwhite2: {
		name: "yellowwhite2",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			return new THREE.Color(1,1,heat);
		}
	},	
	yellowwhite3: {
		name: "yellowwhite3",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			return new THREE.Color(1,1,Math.pow(heat,2));
		}
	},	
	yellow: {
		name: "yellow",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			return new THREE.Color(1,1,0);
		}
	},	
	fullrange1 : {
		name: "fullrange1",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound1 = 0.1;
			var bound2 = 0.5;
			var bound3 = 0.9;
			var redStart = 0.6;
			var blueEnd = 0.8;			
				
			if(param < bound1)
				return new THREE.Color(param/bound1*(1-redStart)+redStart, 0, 0);
			else if(param < bound2)
				return new THREE.Color(1, (param-bound1)/(bound2-bound1), 0);
			else if(param < bound3)
				return new THREE.Color(1, 1, (param-bound2)/(bound3-bound2));
			else
				return new THREE.Color((1-(param-bound3)/(1-bound3))*(1-blueEnd)+blueEnd, 1, 1);
		}
	},
	fullrange2 : {
		name: "fullrange2",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound1 = 0.1;
			var bound2 = 0.5;
			var bound3 = 0.9;
			var redStart = 0.6;
			var blueEnd = 0.8;			
				
			if(param < bound1)
				return new THREE.Color(param/bound1*(1-redStart)+redStart, 0, 0);
			else if(param < bound2)
				return new THREE.Color(1, (param-bound1)/(bound2-bound1), 0);
			else if(param < bound3)
				return new THREE.Color(1, 1, Math.pow((param-bound2)/(bound3-bound2), 2));
			else
				return new THREE.Color((1-(param-bound3)/(1-bound3))*(1-blueEnd)+blueEnd, 1, 1);
		}
	},
	partrange1a: {
		name: "partrange1a",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound = 0.5;
			
			if(param < bound)
				return new THREE.Color(1, param/bound, 0);
			else
				return new THREE.Color(1, 1, (param-bound)/(1-bound));
		}
	},	
	partrange1b: {
		name: "partrange1b",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound = 0.5;
			
			if(param < bound)
				return new THREE.Color(1, Math.sqrt(param/bound), 0);
			else
				return new THREE.Color(1, 1, Math.pow((param-bound)/(1-bound),2));
		}
	},	
	partrange1c: {
		name: "partrange1c",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound = 0.3;
			
			if(param < bound)
				return new THREE.Color(1, param/bound, 0);
			else
				return new THREE.Color(1, 1, (param-bound)/(1-bound));
		}
	},	
	partrange1d: {
		name: "partrange1d",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound = 0.3;
			
			if(param < bound)
				return new THREE.Color(1, Math.sqrt(param/bound), 0);
			else
				return new THREE.Color(1, 1, Math.pow((param-bound)/(1-bound),2));
		}
	},	
	partrange2a: {
		name: "partrange2a",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound = 0.3;
			var greenStart = 0.6;	
			
			if(param < bound)
				return new THREE.Color(1, param/bound*(1-greenStart)+greenStart, 0);
			else
				return new THREE.Color(1, 1, (param-bound)/(1-bound));
		}
	},	
	partrange2b: {
		name: "partrange2b",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound = 0.3;
			var greenStart = 0.6;	
			
			if(param < bound)
				return new THREE.Color(1, param/bound*(1-greenStart)+greenStart, 0);
			else
				return new THREE.Color(1, 1, Math.sqrt((param-bound)/(1-bound)));
		}
	},	
	orangewhite1: {
		name: "orangewhite1",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			var greenStart = 0.6;	
			return new THREE.Color(1, param*(1-greenStart)+greenStart, param);
		}
	},	
	orangewhite2: {
		name: "orangewhite2",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			var greenStart = 0.6;	
			return new THREE.Color(1, Math.sqrt(param*(1-greenStart)+greenStart), Math.sqrt(param));
		}
	},	
	fullrange3a : {
		name: "fullrange3a",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound1 = 0.3;	
			var bound2 = 0.7;		
				
			if(param < bound1)
				return new THREE.Color(1, param/bound1, 0);
			else if(param < bound2)
				return new THREE.Color(1, 1, (param-bound1)/(bound2-bound1));
			else
				return new THREE.Color((1-(param-bound2)/(1-bound2)), (1-(param-bound2)/(1-bound2)), 1);
		}
	},
	fullrange3b : {
		name: "fullrange3b",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound1 = 0.3;	
			var bound2 = 0.7;		
				
			if(param < bound1)
				return new THREE.Color(1, param/bound1, 0);
			else if(param < bound2)
				return new THREE.Color(1, 1, (param-bound1)/(bound2-bound1));
			else
				return new THREE.Color(Math.sqrt(1-(param-bound2)/(1-bound2)), Math.sqrt(1-(param-bound2)/(1-bound2)), 1);
		}
	},
	fullrange4a : {
		name: "fullrange4a",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound1 = 0.3;	
			var bound2 = 0.7;
			var notBlueMin = 0.5;			
				
			if(param < bound1)
				return new THREE.Color(1, param/bound1, 0);
			else if(param < bound2)
				return new THREE.Color(1, 1, (param-bound1)/(bound2-bound1));
			else
				return new THREE.Color((1-(param-bound2)/(1-bound2))*(1-notBlueMin)+notBlueMin, (1-(param-bound2)/(1-bound2))*(1-notBlueMin)+notBlueMin, 1);
		}
	},
	fullrange4b : {
		name: "fullrange4a",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound1 = 0.3;	
			var bound2 = 0.7;
			var notBlueMin = 0.5;			
				
			if(param < bound1)
				return new THREE.Color(1, param/bound1, 0);
			else if(param < bound2)
				return new THREE.Color(1, 1, (param-bound1)/(bound2-bound1));
			else
				return new THREE.Color(Math.sqrt(1-(param-bound2)/(1-bound2))*(1-notBlueMin)+notBlueMin, Math.sqrt(1-(param-bound2)/(1-bound2))*(1-notBlueMin)+notBlueMin, 1);
		}
	},
	fullrange5a : {
		name: "fullrange5a",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound1 = 0.3;	
			var bound2 = 0.7;
			var redMin = 0.5;	
			var greenMin = 0.75;	
						
			if(param < bound1)
				return new THREE.Color(1, param/bound1, 0);
			else if(param < bound2)
				return new THREE.Color(1, 1, (param-bound1)/(bound2-bound1));
			else
				return new THREE.Color((1-(param-bound2)/(1-bound2))*(1-redMin)+redMin, (1-(param-bound2)/(1-bound2))*(1-greenMin)+greenMin, 1);
		}
	},
	fullrange5b : {
		name: "fullrange5b",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = heat;
			
			var bound1 = 0.3;	
			var bound2 = 0.7;
			var redMin = 0.5;	
			var greenMin = 0.75;	
						
			if(param < bound1)
				return new THREE.Color(1, param/bound1, 0);
			else if(param < bound2)
				return new THREE.Color(1, 1, (param-bound1)/(bound2-bound1));
			else
				return new THREE.Color(Math.sqrt(1-(param-bound2)/(1-bound2))*(1-redMin)+redMin, Math.sqrt(1-(param-bound2)/(1-bound2))*(1-greenMin)+greenMin, 1);
		}
	},
	fullcolorrange : {
		name: "fullcolorrange",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = radius;
			
			var bound1 = 1/6;	
			var bound2 = 2/6;	
			var bound3 = 3/6;	
			var bound4 = 4/6;		
			var bound5 = 5/6;
						
			if(param < bound1)
				return new THREE.Color(1-param/bound1, 0, 1);
			else if(param < bound2)
				return new THREE.Color(0, (param-bound1)/(bound2-bound1), 1);
			else if(param < bound3)
				return new THREE.Color(0, 1, 1-(param-bound2)/(bound3-bound2));
			else if(param < bound4)
				return new THREE.Color((param-bound3)/(bound4-bound3), 1, 0);
			else if(param < bound5)
				return new THREE.Color(1, 1-(param-bound4)/(bound5-bound4), 0);
			else
				return new THREE.Color(1, 0, (param-bound5)/(1-bound5));
		}
	},
	bluewhite1 : {
		name: "bluewhite1",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = 1-radius;
			return new THREE.Color(param, param, 1);
		}
	},	
	bluewhite2 : {
		name: "bluewhite2",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = 1-radius;
			return new THREE.Color(Math.pow(param,2), Math.sqrt(param), 1);
		}
	},	
	bluewhite3 : {
		name: "bluewhite3",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = 1-radius;
			return new THREE.Color(Math.sqrt(param), Math.pow(param,2), 1);
		}
	},	
	spectrum1 : {
		name: "spectrum1",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = 1-radius;
			
			var bound = 0.6;
			var red1 = 0.3;
			var green1 = 0.7;
			var green2 = 0.4;
			var blueMin = 0.6;		
						
			if(param < bound)
				return new THREE.Color((param/bound)*(1-red1)+red1, green1-Math.pow(param/bound,2)*(green1-green2), (1-Math.pow(param/bound,2))*(1-blueMin)+blueMin);
			else
				return new THREE.Color(1, Math.pow((param-bound)/(1-bound),0.7)*(1-green2)+green2, Math.pow((param-bound)/(1-bound),0.7)*(1-blueMin)+blueMin);
		}
	},	
	spectrum2 : {
		name: "spectrum2",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = 1-radius;
			
			var bound = 0.6;
			var red1 = 0.3;
			var green1 = 0.7;
			var green2 = 0.2;
			var blueMin = 0.3;		
						
			if(param < bound)
				return new THREE.Color((param/bound)*(1-red1)+red1, green1-Math.pow(param/bound,2)*(green1-green2), (1-Math.pow(param/bound,2))*(1-blueMin)+blueMin);
			else
				return new THREE.Color(1, Math.pow((param-bound)/(1-bound),0.7)*(1-green2)+green2, Math.pow((param-bound)/(1-bound),0.7)*(1-blueMin)+blueMin);
		}
	},
	spectrum3 : {
		name: "spectrum3",
		getRGB: function(size, heat, habitability, infrastructure, population, maxPopulation, radius) {
			var param = 1-radius;
			
			var bound = 0.6;
			var red1 = 0.3;
			var green1 = 0.7;
			var green2 = 0.2;
			var blueMin = 0.3;			
						
			if(param < bound)
				return new THREE.Color((param/bound)*(1-red1)+red1, green1-Math.pow(param/bound,2)*(green1-green2), (1-Math.pow(param/bound,2))*(1-blueMin)+blueMin);
			else
				return new THREE.Color(1, Math.pow((param-bound)/(1-bound),0.7)*(1-green2)+green2, Math.pow((param-bound)/(1-bound),1.2)*(1-blueMin)+blueMin);
		}
	},
}; 