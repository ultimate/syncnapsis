var lastZ = 1;

function getX(comp) {
	return Number(comp.style.left.replace(/px/g,""));
}

function getXAbs(comp) {
	var x = comp.offsetLeft;
	while(comp = comp.offsetParent)
		x += comp.offsetLeft;
	
	return x;
}

function getYAbs(comp) {
	var y = comp.offsetTop;
	while(comp = comp.offsetParent)
		y += comp.offsetTop;
	
	return y;
}

function getY(comp) {
	return Number(comp.style.top.replace(/px/g,""));
}

function getZ(comp) {
	return Number(comp.style.zIndex);	
}

function setX(comp,x) {
	comp.style.left = x+'px';
}

function setY(comp,y) {
	comp.style.top = y+'px';
}

function setZ(comp, z) {
	comp.style.zIndex = z;
}

function moveToX(comp,x, TimeInMs) {
	if(comp.moveToX)
		window.clearInterval(comp.moveToX);		
	comp.moveToXAim = x;
	var dx = x - getX(comp);
	if(dx == 0) return;
	var time = Math.round(TimeInMs/Math.abs(dx));
	if(dx > 0) 
		comp.moveToX = window.setInterval(function() {moveToXTimer(comp, 1);},time);	
	if(dx < 0) 
		comp.moveToX = window.setInterval(function() {moveToXTimer(comp, -1);},time);	
}

function moveToXTimer(comp, offset) {
	var newX = getX(comp)+offset;
	if( newX == comp.moveToXAim) {
		window.clearInterval(comp.moveToX);
	}
	setX(comp, newX);
}

function moveToY(comp,y, TimeInMs) {
	if(comp.moveToY)
		window.clearInterval(comp.moveToY);		
		
	comp.moveToYAim = y;
	var dy = y - getY(comp);
	var offset = Math.round(dy/(TimeInMs*25/1000));
	if(offset == 0) return;
	info(offset);
	comp.moveToY = window.setInterval(function() {moveToYTimer(comp, offset);},40);	

}

function moveToYTimer(comp, offset) {
	var newY = getY(comp)+Math.round(offset);
	info(Math.abs(newY-comp.moveToYAim) + "  -  " + offset);
	if( Math.abs(newY-comp.moveToYAim) < offset+1) {
		window.clearInterval(comp.moveToY);
		setY(comp, comp.moveToAim);
		return;
	}
	setY(comp, newY);
}

function getHeight(comp) {
	return Number(comp.style.height.replace(/px/g,""));
}

function setHeight(comp, height) {
	comp.style.height = height+'px';
}

function getWidth(comp) {
	return Number(comp.style.width.replace(/px/g,""));
}

function setWidth(comp, width) {
	comp.style.width = width+'px';
}

function fadeIn(comp, timeInMs) {
	show(comp);
	if(comp.fade)
		window.clearInterval(comp.fade);
	var oldOpa = (100-getOpacity(comp));
	var offset = Math.round(oldOpa/(timeInMs*25/1000));
	comp.fade = window.setInterval(function() {fade(comp, offset);},40);
}

function fadeOut(comp, timeInMs) {
	show(comp);
	if(comp.fade)
		window.clearInterval(comp.fade);	
	if(comp.fade)
		window.clearInterval(comp.fade);
	var oldOpa = getOpacity(comp);
	var offset = Math.round(oldOpa/(timeInMs*25/1000));
	comp.fade = window.setInterval(function() {fade(comp, -offset);},40);
		
}

function fade(comp, offset) {
	var newOpa = getOpacity(comp)+offset;
	if( newOpa > 100) {
		newOpa = 100;
		window.clearInterval(comp.fade);
	}
	if( newOpa < 0 ) { 
		newOpa = 0;
		window.clearInterval(comp.fade);
		hide(comp);
	}
	setOpacity(comp, newOpa);

}

function createDiv(posX, posY, posZ, width, height, color, tooltip)
{	
	var el = document.createElement("div");
	el.setPosition("absolute");
	el.setX(posX);
	el.setY(posY);
	el.setZ(posZ);
	el.setWidth(width);
	el.setHeight(height);
	el.setColor(color);
	return el;
}