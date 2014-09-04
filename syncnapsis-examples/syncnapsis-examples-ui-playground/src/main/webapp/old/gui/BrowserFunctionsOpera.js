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
function getMouseX(e) {
	return Number(e.clientX);
}
function getMouseY(e) {
	return Number(e.clientY);
}
function getMouseButton(e) {
	return Number(e.which);	
}
function getDocumentWidth() {
	return Number(window.innerWidth);
}
function getDocumentHeight() {
	return Number(window.innerHeight);
}

function addListener(comp, lis, fun) {
	comp.addEventListener(lis,fun, true);
}
function removeListener(comp, lis, fun) {
	comp.removeEventListener(lis,fun, true);
}

function show(comp) {
  comp.style.display = '';	
}
function hide(comp) {
  comp.style.display = 'none';	
}
function getSrcElement(e) {
	return e.target;
}