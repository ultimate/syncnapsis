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