var testTarget;

var movingWindow = false;
var windowToMove = null;
var resizeingWindow = false;
var windowToResize = null;
var resizeDirection = null;
var windowID = 1;
var lastZ = 1;
var mouseXStart, mouseYStart, windowXStart, windowYStart, windowHeightStart, windowWidthStart;

var innerFrameSize = 9;
var outerFrameSize = 3;
var titelSize = 24;
var contentBackgroundColor = '#9a93b1';



function closeWindow(w) {
	document.getElementById('windowContainer').removeChild(document.getElementById(w));
}

function minmaxWindow(win) {
	w = document.getElementById(win);
	if (w.minimized == true) {
		show(document.getElementById(w.id+'.contentRow'));
		show(document.getElementById(w.id+'.southInnerBorderRow'));
		setHeight(w,w.oldSize);
		setWidth(document.getElementById(w.id+'.contentRow'),getWidth(w));
		w.minimized = false;
		w.resizeable = true;
		document.getElementById(w.id+".upbutton").src = 'images/skins/'+skinname+'/button/up_mouseover.png';		
	}
	else {
	w.oldSize = getHeight(w);
	hide(document.getElementById(w.id+'.contentRow'));
	hide(document.getElementById(w.id+'.southInnerBorderRow'));
	setHeight(w,2*outerFrameSize+titelSize);
	w.resizeable = false;
	w.minimized = true;
	document.getElementById(w.id+".upbutton").src = 'images/skins/'+skinname+'/button/down_mouseover.png';	
	}
}


function moveWindow(e) {

 var windowID 		= getSrcElement(e).id.substr(0,getSrcElement(e).id.indexOf("."));
 windowToMove 		= document.getElementById(windowID);
 mouseXStart 		= getMouseX(e);
 mouseYStart 		= getMouseY(e);
 windowXStart 		= getX(windowToMove);
 windowYStart 		= getY(windowToMove);
 movingWindow 		= true;
  
}

function WindowResize(e) {

 var windowID 		= getSrcElement(e).id.substr(0,getSrcElement(e).id.indexOf("."));
 windowToResize		= document.getElementById(windowID);
 resizeDirection 	= getSrcElement(e).getAttribute("direction");
 mouseXStart 		= getMouseX(e);
 mouseYStart 		= getMouseY(e);
 windowXStart 		= getX(windowToResize);
 windowYStart 		= getY(windowToResize);
 windowWidthStart 	= getWidth(windowToResize);
 windowHeightStart 	= getHeight(windowToResize); 
 resizeingWindow 	= true;	
}



function mouseMove(e) {
  if (getMouseButton(e) == 0 )
  	mouseUp();
  	


  // move window with docking
  if (movingWindow == true && windowToMove.moveable == true) {
 
    windowToMove.style.zIndex = ++lastZ;
    var mouseX = getMouseX(e);
	var mouseY = getMouseY(e);
	    
    var newX = windowXStart-mouseXStart+mouseX;
    var newY = windowYStart-mouseYStart+mouseY;
    
  	if(newY < windowToMove.minDim[1])
  		newY = windowToMove.minDim[1];
  	if(newX < windowToMove.minDim[0])
  		newX = windowToMove.minDim[0];
  	if(newY > windowToMove.maxDim[1]-getHeight(windowToMove))
  		newY = windowToMove.maxDim[1]-getHeight(windowToMove);
  	if(newX > windowToMove.maxDim[0]-getWidth(windowToMove))
  		newX = windowToMove.maxDim[0]-getWidth(windowToMove);  		
  		    
    setX(windowToMove,newX);
	setY(windowToMove,newY);    
	//dragFrame(windowToMove,newX,newY);
     


  }
  
  // resize window
  if (resizeingWindow == true && windowToResize.resizeable == true)
  {
    var mouseX = getMouseX(e); 
    var mouseY = getMouseY(e);
	if (mouseX < 5) mouseX = 5;    
	if (mouseY < 5) mouseY = 5;
	        
    var newHeight, newTop;
    if (resizeDirection.indexOf("n") == -1)     	
  		newHeight = windowHeightStart+mouseY-mouseYStart;
  	else {
  		newHeight = windowHeightStart-mouseY+mouseYStart;
  		newTop 	  =	windowYStart+mouseY-mouseYStart;
  	}
  		
  	if (newHeight < windowToResize.minDim[3]+innerFrameSize+titelSize+2*outerFrameSize) {
  		newHeight = windowToResize.minDim[3]+innerFrameSize+titelSize+2*outerFrameSize;
  		newTop 	  = windowYStart-newHeight+windowHeightStart;
  	}
  	if (newHeight > getDocumentHeight()-getY(windowToResize))
  		newHeight = getDocumentHeight()-getY(windowToResize);

    var newWidth, newLeft;
    if (resizeDirection.indexOf("w") == -1)     	
  		newWidth = windowWidthStart+mouseX-mouseXStart;
  	else {
  		newWidth = windowWidthStart-mouseX+mouseXStart;
  		newLeft  = windowXStart+mouseX-mouseXStart;  		
  	}
  		  	
  	if (newWidth < windowToResize.minDim[2]+2*innerFrameSize+2*outerFrameSize) {
  		newWidth = windowToResize.minDim[2]+2*innerFrameSize+2*outerFrameSize;
  		newLeft  = windowXStart-newWidth+windowWidthStart;
  	}
  	if (newWidth > getDocumentWidth()-getX(windowToResize))
  		newWidth = getDocumentWidth()-getX(windowToResize);
  		
  	if(newTop < windowToResize.minDim[1])
  		newTop = windowToResize.minDim[1];
  	if(newLeft < windowToResize.minDim[0])
  		newLeft = windowToResize.minDim[0];
  		  		  		
  	switch(resizeDirection) {
  		case "s":
  		  setHeight(windowToResize,newHeight);
  		  setHeight(document.getElementById((windowToResize.id+'.contentRow')),newHeight-innerFrameSize-titelSize-2*outerFrameSize);
  		break;
  		case "se":
  		  setHeight(windowToResize,newHeight);
  		  setHeight(document.getElementById((windowToResize.id+'.contentRow')),newHeight-innerFrameSize-titelSize-2*outerFrameSize);
		  setWidth(document.getElementById((windowToResize.id+'.contentField')),newWidth-2*innerFrameSize-2*outerFrameSize);  		  
  		  setWidth(windowToResize,newWidth);  		  
  		break;
  		case "e":
  		  setWidth(windowToResize,newWidth);
		  setWidth(document.getElementById((windowToResize.id+'.contentField')),newWidth-2*innerFrameSize-2*outerFrameSize);  		    		  
  		break;  		
  		case "ne":
  		  setHeight(windowToResize,newHeight);
  		  setHeight(document.getElementById((windowToResize.id+'.contentRow')),newHeight-innerFrameSize-titelSize-2*outerFrameSize);
		  setWidth(document.getElementById((windowToResize.id+'.contentField')),newWidth-2*innerFrameSize-2*outerFrameSize);  		  
  		  setWidth(windowToResize,newWidth);
		  setY(windowToResize,newTop);  		  
  		break; 
  		case "n":
  	  	  setHeight(windowToResize,newHeight);
  		  setHeight(document.getElementById((windowToResize.id+'.contentRow')),newHeight-innerFrameSize-titelSize-2*outerFrameSize);
 		  setY(windowToResize,newTop);
  		break;
  		case "nw":
  		  setHeight(windowToResize,newHeight);
  		  setHeight(document.getElementById((windowToResize.id+'.contentRow')),newHeight-innerFrameSize-titelSize-2*outerFrameSize);
		  setWidth(document.getElementById((windowToResize.id+'.contentField')),newWidth-2*innerFrameSize-2*outerFrameSize);  		  
  		  setWidth(windowToResize,newWidth);
   	      setY(windowToResize,newTop);
		  setX(windowToResize,newLeft);		      		
  		break;
  		case "w":
  		  setWidth(windowToResize,newWidth);
		  setWidth(document.getElementById((windowToResize.id+'.contentField')),newWidth-2*innerFrameSize-2*outerFrameSize);  		  
		  setX(windowToResize,newLeft);  		  
  		break;
  		case "sw":
  		  setHeight(windowToResize,newHeight);
  		  setHeight(document.getElementById((windowToResize.id+'.contentRow')),newHeight-innerFrameSize-titelSize-2*outerFrameSize);
		  setWidth(document.getElementById((windowToResize.id+'.contentField')),newWidth-2*innerFrameSize-2*outerFrameSize);  		    		
  		  setWidth(windowToResize,newWidth);
		  setX(windowToResize,newLeft);
  		break; 		
  	}
  	
  }
}

function mouseUp() {
    movingWindow = false;
	resizeingWindow = false;
}

function setWindowResizeable(w, resizeable) {
	w.resizeable = resizeable;

}

function setWindowResizeable(w, closeable) {
	w.closeable = closeable;
    if(!w.closeable) hide(document.getElementById(w.id+'.closebutton'));	
}




function mouseOverUP(w) {
	w = document.getElementById(w);
	if (w.minimized == true) {
		document.getElementById(w.id+".upbutton").src = 'images/skins/'+skinname+'/button/down_mouseover.png';
	}
	else
		document.getElementById(w.id+".upbutton").src = 'images/skins/'+skinname+'/button/up_mouseover.png';
}

function mouseDownUP(w) {
	w = document.getElementById(w);
	if (w.minimized == true)	
		document.getElementById(w.id+".upbutton").src = 'images/skins/'+skinname+'/button/down_mousedown.png';
	else	
		document.getElementById(w.id+".upbutton").src = 'images/skins/'+skinname+'/button/up_mousedown.png';
}

function mouseOutUP(w) {
	w = document.getElementById(w);	
	if (w.minimized == true)	
		document.getElementById(w.id+".upbutton").src = 'images/skins/'+skinname+'/button/down_normal.png';
	else
		document.getElementById(w.id+".upbutton").src = 'images/skins/'+skinname+'/button/up_normal.png';
}
function mouseOverCLOSE(w) {
	document.getElementById(w+".closebutton").src = 'images/skins/'+skinname+'/button/close_mouseover.png';
}
function mouseDownCLOSE(w) {
	document.getElementById(w+".closebutton").src = 'images/skins/'+skinname+'/button/close_mousedown.png';
}

function mouseOutCLOSE(w) {
	document.getElementById(w+".closebutton").src = 'images/skins/'+skinname+'/button/close_normal.png';	
}