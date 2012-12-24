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
function viewportMouseDown(e, viewport)
{
	for(var i = 0; i < viewport.clickListeners.length; i++)
	{
		if(viewport.clickListeners[i].isInArea(viewport.mouseX, viewport.mouseY))
		{
			viewport.clickListeners[i].setActive();
		}
	}
	for(var i = 0; i < viewport.dragListeners.length; i++)
	{
		if(viewport.dragListeners[i].isInArea(viewport.mouseX, viewport.mouseY))
		{
			viewport.dragListeners[i].setActive();
		}
	}
};
	
function viewportMouseUp(e, viewport)
{
	for(var i = 0; i < viewport.clickListeners.length; i++)
	{
		if(viewport.clickListeners[i].isInArea(viewport.mouseX, viewport.mouseY)
			&& viewport.clickListeners[i].isActive())
		{
			viewport.clickListeners[i].event(e);
		}
		viewport.clickListeners[i].setInactive();
	}
	for(var i = 0; i < viewport.dragListeners.length; i++)
	{
		viewport.dragListeners[i].setInactive();
	}		
};
	
function viewportMouseMove(e, viewport)
{		
	viewport.mouseX = getMouseX(e)-viewport.posX-viewport.w_2;
	viewport.mouseY = getMouseY(e)-viewport.posY-viewport.h_2;
	viewport.redraw();
};