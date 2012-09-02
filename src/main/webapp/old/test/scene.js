var displayer;
var viewport;
var camera;
var object;
var object2;
var object3;
var rotX = 45;
var rotY = 20;
var rotZ = 0;

function initPage()
{
	displayer = new Displayer();
	info("displayer created");
	displayer.createViewport(350,50,0,500,400, "#EEEEEE");
	info("viewport created");
	
	displayer.addDrawable(new Polygon(new Array( 40,-40,-40, 40), new Array( 40, 40,-40,-40), new Array( 40, 40, 40, 40), "#FF0000", true));
	displayer.addDrawable(new Polygon(new Array( 40,-40,-40, 40), new Array( 40, 40,-40,-40), new Array(-40,-40,-40,-40), "#FF0000", true));
	displayer.addDrawable(new Polygon(new Array( 40,-40,-40, 40), new Array( 40, 40, 40, 40), new Array( 40, 40,-40,-40), "#00FF00", true));
	displayer.addDrawable(new Polygon(new Array( 40,-40,-40, 40), new Array(-40,-40,-40,-40), new Array( 40, 40,-40,-40), "#00FF00", true));
	displayer.addDrawable(new Polygon(new Array( 40, 40, 40, 40), new Array( 40,-40,-40, 40), new Array( 40, 40,-40,-40), "#0000FF", true));
	displayer.addDrawable(new Polygon(new Array(-40,-40,-40,-40), new Array( 40,-40,-40, 40), new Array( 40, 40,-40,-40), "#0000FF", true));
		
	info("cube planes created");

	displayer.addDrawable(new EventPoint( 40, 40, 40, EVENT_POINT_HOVER, new ListenerPoint_Square(0,0,5), function(x,y,ctx){hover(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint( 40, 40,-40, EVENT_POINT_HOVER, new ListenerPoint_Square(0,0,5), function(x,y,ctx){hover(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint( 40,-40, 40, EVENT_POINT_HOVER, new ListenerPoint_Square(0,0,5), function(x,y,ctx){hover(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint( 40,-40,-40, EVENT_POINT_HOVER, new ListenerPoint_Square(0,0,5), function(x,y,ctx){hover(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint(-40, 40, 40, EVENT_POINT_HOVER, new ListenerPoint_Square(0,0,5), function(x,y,ctx){hover(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint(-40, 40,-40, EVENT_POINT_HOVER, new ListenerPoint_Square(0,0,5), function(x,y,ctx){hover(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint(-40,-40, 40, EVENT_POINT_HOVER, new ListenerPoint_Square(0,0,5), function(x,y,ctx){hover(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint(-40,-40,-40, EVENT_POINT_HOVER, new ListenerPoint_Square(0,0,5), function(x,y,ctx){hover(this, x,y,ctx)}));
	
	displayer.addDrawable(new EventPoint( 40, 40, 40, EVENT_POINT_CLICK, new ListenerPoint_Square(0,0,5), function(x,y,ctx){click(this, x,y,ctx, "front top    right")}));
	displayer.addDrawable(new EventPoint( 40, 40,-40, EVENT_POINT_CLICK, new ListenerPoint_Square(0,0,5), function(x,y,ctx){click(this, x,y,ctx, "front top    left ")}));
	displayer.addDrawable(new EventPoint( 40,-40, 40, EVENT_POINT_CLICK, new ListenerPoint_Square(0,0,5), function(x,y,ctx){click(this, x,y,ctx, "front bottom right")}));
	displayer.addDrawable(new EventPoint( 40,-40,-40, EVENT_POINT_CLICK, new ListenerPoint_Square(0,0,5), function(x,y,ctx){click(this, x,y,ctx, "front bottom left ")}));
	displayer.addDrawable(new EventPoint(-40, 40, 40, EVENT_POINT_CLICK, new ListenerPoint_Square(0,0,5), function(x,y,ctx){click(this, x,y,ctx, "back  top    right")}));
	displayer.addDrawable(new EventPoint(-40, 40,-40, EVENT_POINT_CLICK, new ListenerPoint_Square(0,0,5), function(x,y,ctx){click(this, x,y,ctx, "back  top    left ")}));
	displayer.addDrawable(new EventPoint(-40,-40, 40, EVENT_POINT_CLICK, new ListenerPoint_Square(0,0,5), function(x,y,ctx){click(this, x,y,ctx, "back  bottom right")}));
	displayer.addDrawable(new EventPoint(-40,-40,-40, EVENT_POINT_CLICK, new ListenerPoint_Square(0,0,5), function(x,y,ctx){click(this, x,y,ctx, "back  bottom left ")}));
	
	displayer.addDrawable(new EventPoint( 40, 40, 40, EVENT_POINT_DRAG, new ListenerPoint_Square(0,0,5), function(x,y,ctx){drag(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint( 40, 40,-40, EVENT_POINT_DRAG, new ListenerPoint_Square(0,0,5), function(x,y,ctx){drag(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint( 40,-40, 40, EVENT_POINT_DRAG, new ListenerPoint_Square(0,0,5), function(x,y,ctx){drag(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint( 40,-40,-40, EVENT_POINT_DRAG, new ListenerPoint_Square(0,0,5), function(x,y,ctx){drag(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint(-40, 40, 40, EVENT_POINT_DRAG, new ListenerPoint_Square(0,0,5), function(x,y,ctx){drag(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint(-40, 40,-40, EVENT_POINT_DRAG, new ListenerPoint_Square(0,0,5), function(x,y,ctx){drag(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint(-40,-40, 40, EVENT_POINT_DRAG, new ListenerPoint_Square(0,0,5), function(x,y,ctx){drag(this, x,y,ctx)}));
	displayer.addDrawable(new EventPoint(-40,-40,-40, EVENT_POINT_DRAG, new ListenerPoint_Square(0,0,5), function(x,y,ctx){drag(this, x,y,ctx)}));	
	
	info("cube corners interactivity created");
	
	camera = new Camera(new Vector4D(100,100,70), new Vector4D(-1,-1,-0.7), new Vector4D(0,0,1), 1, 100, 10);
	displayer.addCamera(camera, 1);
	camera = new Camera(new Vector4D(0,1,100), new Vector4D(0,0,-1), new Vector4D(0,1,0), 1, 100, 10);
	displayer.addCamera(camera, 2);
	camera = new Camera(new Vector4D(100,100,110), new Vector4D(-1,-1,-1), new Vector4D(-1,-1,1), 1, 100, 10);
	displayer.addCamera(camera, 3);
	camera = new CameraPolar(new Vector4D(0,0,0), 135, 45, 0, 200, 1, 100, 10);
	displayer.addCamera(camera, 4);
	camera = new CameraPolar(new Vector4D(0,0,0), 0, 90, 0, 200, 1, 100, 10);
	displayer.addCamera(camera, 5);
	
	info("cameras created");

	displayer.setActiveCamera(4);
	
	setTimeout("draw()", 100);
}

function rotate(dx, dy, dz)
{
	displayer.cameras[displayer.activeCamera].increasePhi(dy);
	displayer.cameras[displayer.activeCamera].increaseTheta(dx);
	displayer.cameras[displayer.activeCamera].increaseZeta(dz);
	
	draw();
}

function rotate2(dx, dy, dz)
{
	rotate3(dx, dy, dz);
	draw();	
}

function rotate3(dx, dy, dz)
{
	var t_rotY = new TransformationRotationY(dy*Math.PI/180);
	var t_rotX = new TransformationRotationX(dx*Math.PI/180);
	
	displayer.cameras[displayer.activeCamera].addExtraTransformation(t_rotX);	
	displayer.cameras[displayer.activeCamera].addExtraTransformation(t_rotY);	
}

function dist(dd)
{
	displayer.cameras[displayer.activeCamera].distance += dd;
	draw();
}

function draw()
{
	displayer.redraw();
}

function hover(eventpoint, x, y, ctx)
{
	ctx.strokeStyle = "#000000";
	ctx.beginPath();
	ctx.moveTo(eventpoint.area.xC+5, eventpoint.area.yC);
	ctx.arc(eventpoint.area.xC, eventpoint.area.yC, 5, 0, Math.PI*2, false);
	ctx.stroke();
} 

function click(eventpoint, x, y, ctx, message)
{
	alert("clicked: " + message);
} 

function drag(eventpoint, x, y, ctx)
{
	if(eventpoint.lastX)
	{
		var rotX = (x-eventpoint.lastX);
		var rotY = -(y-eventpoint.lastY);
		
		rotate3(rotY, rotX, 0);
	
		eventpoint.viewport.displayer.calculateCameraTransformation();
	}
	
	eventpoint.lastX = x;
	eventpoint.lastY = y;
} 