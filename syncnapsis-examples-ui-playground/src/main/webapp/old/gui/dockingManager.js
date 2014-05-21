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
var dockingDistance=20;

function isLeftCornerLeft(w1,w2)
	{
		return (getX(w2)+dockingDistance >= getX(w1) 
			 && getX(w2)-dockingDistance <= getX(w1));
	}

function isLeftCornerRight(w1,w2)
	{
		return (getX(w2)+getWidth(w2)+dockingDistance>=getX(w1) 
			 && getX(w2)+getWidth(w2)-dockingDistance<=getX(w1));
	}
function isRightCornerLeft(w1, w2)
	{
		return (getX(w2)+dockingDistance>=getX(w1)+getWidth(w1) 
			 && getX(w2)-dockingDistance<=getX(w1)+getWidth(w1));
	}

function isRightCornerRight(w1,w2)
	{
		return (getX(w2)+getWidth(w2)+dockingDistance>=getX(w1)+getWidth(w1)
			 && getX(w2)+getWidth(w2)-dockingDistance<=getX(w1)+getWidth(w1));
	}
	
function isTopCornerOver(w1,w2)
	{
		return (getY(w2)+dockingDistance>=getY(w1) 
			 && getY(w2)-dockingDistance<=getY(w1));
	}

function isTopCornerUnder(w1,w2)
	{
		return (getY(w2)+getHeight(w2)+dockingDistance>=getY(w1) 
			 && getY(w2)+getHeight(w2)-dockingDistance<=getY(w1));
	}
	
function isBottomCornerOver(w1,w2)
	{
		return (getY(w2)+dockingDistance>=getY(w1)+getHeight(w1) 
			 && getY(w2)-dockingDistance<=getY(w1)+getHeight(w1));
	}
	
function isBottomCornerUnder(w1,w2)
	{
		return (getY(w2)+getHeight(w2)+dockingDistance>=getY(w1)+getHeight(w1)
			 && getY(w2)+getHeight(w2)-dockingDistance<=getY(w1)+getHeight(w1));
	}
	
function isDockableX(w1,w2)
	{
		return ((getY(w1)>=getY(w2) && getY(w1)<=getY(w2)+getHeight(w2))
			 || (getY(w2)>=getY(w1) && getY(w2)<=getY(w1)+getHeight(w1)));
	}
	
function isDockableY(w1,w2)
	{
		return ((getX(w1)>=getX(w2) && getX(w1)<=getX(w2)+getWidth(w2))
			 || (getX(w2)>=getX(w1) && getX(w2)<=getX(w1)+getWidth(w1)));
	}

function dragFrame(frame, xCoordinate, yCoordinate) 
	{
			 var width = getDocumentWidth();
			 var height = getDocumentHeight();
			 var w = getWidth(frame);
			 var h = getHeight(frame);			 			 
			 var dx = 0;
			 var distx = dockingDistance;
			 var dy = 0;
			 var disty = dockingDistance;
//			 in x direction
			 for(var i=0;i<document.getElementById('WindowContainer').childNodes.length;i++)
			 {
			 	comp = document.getElementById('WindowContainer').childNodes[i];

					 if(comp != frame)
					 {
						 if(isDockableX(frame, comp))
						 {
							 dx=disty;
							 if(isLeftCornerRight(frame, comp))
							 {
								 dx=Math.abs(xCoordinate-getX(comp)-getWidth(comp));
								 if(dx < distx)
								 {
									 distx=dx;
									 xCoordinate = getX(comp)+getWidth(comp);
								 }
							 }
							 else if(isRightCornerLeft(frame, comp))
							 {
								 dx=Math.abs(getX(comp)-xCoordinate-w);
								 if(dx < distx)
								 {
									 distx=dx;
									 xCoordinate = getX(comp)-w;
								 }
							 }
						 }
						 if(isLeftCornerLeft(frame, comp) && isBottomCornerOver(frame, comp))
						 {
							 dx=Math.abs(xCoordinate-getX(comp));
							 if(dx < distx)
							 {
								 distx=dx;
								 xCoordinate = getX(comp);
							 }
							 dy=Math.abs(getY(comp)-yCoordinate-h);
							 if(dy < disty)
							 {
								 disty=dy;
								 yCoordinate = getY(comp)-h;
							 }
						 }
						 if(isRightCornerRight(frame, comp) && isBottomCornerOver(frame, comp))
						 {
							 dx=Math.abs(xCoordinate+w-getX(comp)-getWidth(comp));
							 if(dx < distx)
							 {
								 distx=dx;
								 xCoordinate = getX(comp)+getWidth(comp)-w;
							 }
							 dy=Math.abs(getY(comp)-yCoordinate-h);
							 if(dy < disty)
							 {
								 disty=dy;
								 yCoordinate = getY(comp)-h;
							 }
						 }
						 
						 if(isLeftCornerLeft(frame, comp) && isTopCornerUnder(frame, comp))
						 {
							 dx=Math.abs(xCoordinate-getX(comp));
							 if(dx < distx)
							 {
								 distx=dx;
								 xCoordinate = getX(comp);
							 }
							 dy=Math.abs(getY(comp)+getHeight(comp)-yCoordinate);
							 if(dy < disty)
							 {
								 disty=dy;
								 yCoordinate = getY(comp)+getHeight(comp);
							 }
						 }
						 if(isRightCornerRight(frame, comp) && isTopCornerUnder(frame, comp))
						 {
							 dx=Math.abs(xCoordinate+w-getX(comp)-getWidth(comp));
							 if(dx < distx)
							 {
								 distx=dx;
								 xCoordinate = getX(comp)+getWidth(comp)-w;
							 }
							 dy=Math.abs(getY(comp)+getHeight(comp)-yCoordinate);
							 if(dy < disty)
							 {
								 disty=dy;
								 yCoordinate = getY(comp)+getHeight(comp);
							 }
						 }
					 }
						 if(xCoordinate<distx)
						 {
							 dx = xCoordinate;
							 if(dx<distx)
							 {
								 distx=dx;
								 xCoordinate = 0;
							 }
						 }
						 if(xCoordinate+w>width-distx)
						 {
							 dx = width-xCoordinate-w;
							 if(dx<distx)
							 {
								 distx=dx;
								 xCoordinate = width-w;
							 }
						 }
					 
				 }		 
//			 in y direction
			 for(var i=0;i<document.getElementById('WindowContainer').childNodes.length;i++)
			 {
			 	comp = document.getElementById('WindowContainer').childNodes[i];	
					 if(comp != frame)
					 {
						 if(isDockableY(frame, comp))
						 {

							 dy=disty;
							 if(isTopCornerUnder(frame, comp))
							 {
								 dy=Math.abs(yCoordinate-getY(comp)-getHeight(comp));
								 if(dy < disty)
								 {
									 disty=dy;
									 yCoordinate = getY(comp)+getHeight(comp);
								 }
							 }
							 else if(isBottomCornerOver(frame, comp))
							 {
								 dy=Math.abs(getY(comp)-yCoordinate-h);
								 if(dy < disty)
								 {
									 disty=dy;
									 yCoordinate = getY(comp)-h;
								 }
							 }
						 }
						 if(isTopCornerOver(frame, comp) && isRightCornerLeft(frame, comp))
						 {
							 dx=Math.abs(getX(comp)-xCoordinate-w);
							 if(dx < distx)
							 {
								 distx=dx;
								 xCoordinate = getX(comp)-w;
							 }
							 dy=Math.abs(getY(comp)-yCoordinate);
							 if(dy < disty)
							 {
								 disty=dy;
								 yCoordinate = getY(comp);
							 }
						 }
						 if(isBottomCornerUnder(frame, comp) && isRightCornerLeft(frame, comp))
						 {
							 dx=Math.abs(getX(comp)-xCoordinate-w);
							 if(dx < distx)
							 {
								 distx=dx;
								 xCoordinate = getX(comp)-w;
							 }
							 dy=Math.abs(getY(comp)+getHeight(comp)-yCoordinate-h);
							 if(dy < disty)
							 {
								 disty=dy;
								 yCoordinate = getY(comp)+getHeight(comp)-h;
							 }
						 }
						 if(isTopCornerOver(frame, comp) && isLeftCornerRight(frame, comp))
						 {
							 dx=Math.abs(xCoordinate-getX(comp)-getWidth(comp));
							 if(dx < distx)
							 {
								 distx=dx;
								 xCoordinate = getX(comp)+getWidth(comp);
							 }
							 dy=Math.abs(getY(comp)-yCoordinate);
							 if(dy < disty)
							 {
								 disty=dy;
								 yCoordinate = getY(comp);
							 }
						 }
						 if(isBottomCornerUnder(frame, comp) && isLeftCornerRight(frame, comp))
						 {
							 dx=Math.abs(xCoordinate-getX(comp)-getWidth(comp));
							 if(dx < distx)
							 {
								 distx=dx;
								 xCoordinate = getX(comp)+getWidth(comp);
							 }
							 dy=Math.abs(getY(comp)+getHeight(comp)-yCoordinate-h);
							 if(dy < disty)
							 {
								 disty=dy;
								 yCoordinate = getY(comp)+getHeight(comp)-h;
							 }
						 }
					 }
						 if(yCoordinate>0 && yCoordinate<disty)
						 {
							 dy = yCoordinate;
							 if(dy < disty)
							 {
								 disty=dy;
								 yCoordinate = 0;
							 }
						 }
						 if(yCoordinate+h>height-disty)
						 {
							 dy = height-yCoordinate-h;
							 if(dy<disty)
							 {
								 disty=dy;
								 yCoordinate = height-h;
							 }
						 }			 
			 }
			 // Bereichsbegrenzung
			 if (xCoordinate < 0) 
			 {
			    xCoordinate = 0;
			 }
			 if (xCoordinate + w > width) 
			 {
			    xCoordinate = width - w;
			 }
			 if (yCoordinate < 0) 
			 {
			    yCoordinate = 0;
			 }
			 if (yCoordinate + h > height) 
			 {
			    yCoordinate = height - h;
			 }
			 setX(frame,xCoordinate);
			 setY(frame,yCoordinate);	
}
