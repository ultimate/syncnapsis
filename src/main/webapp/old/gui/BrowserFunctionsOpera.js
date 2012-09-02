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