//@requires("glMatrix")

var GL = {};

GL.TYPE_2D = "2d";
GL.TYPE_3D = "3d";
GL.TYPE_UNKNOWN = "unknown";

GL.context3DNames = [ "webgl", "experimental-webgl", "moz-webgl", "webkit-3d" ];

// buffer to store all generated context
GL.contexts = {};
GL.contextCount = 0;

GL.create = function(id, type)
{
	if(!parent)
		parent = document.body;
		
	var canvas = document.getElementById(id);	
	var ctx = null;
	if(type == GL.TYPE_2D)
		ctx = GL.getContext2D(canvas);
	else if(type == GL.TYPE_3D)
		ctx = GL.getContext3D(canvas);
	else
		throw new Error("illegal type: '" + type + "'");
	
	if(ctx == null)
		throw new Error("context initialization failed!");
		
	GL.registerContext(id, ctx, type);	
	return ctx;	
};

GL.destroy = function(id)
{
	GL.contexts[id] = null;
};

GL.getContext2D = function(canvas)
{ 
	return canvas.getContext("2d");
};

GL.getContext3D = function(canvas)
{	
	var ctx = null;
	for(var i = 0; i < GL.context3DNames.length; i++)
	{
   		try
   		{ 
        	ctx = canvas.getContext(GL.context3DNames[i]);
        	if(ctx)
        		break;
   		}
   		catch(e)
   		{
   			// this webgl name is not supported
   		}
	}
	if(ctx == null)
	{
    	throw new Error("WebGL not found! Please check if your browser supports Web GL.");
	}
	ctx.viewportWidth = canvas.width;
    ctx.viewportHeight = canvas.height;
	return ctx;
};

GL.registerContext = function(id, ctx, type)
{
	GL.contexts[id] = {};
	GL.contexts[id].ctx = ctx;
	GL.contexts[id].type = type;
	
	if(type == GL.TYPE_3D)
	{
		GL.contexts[id].ctx.getShader = function(shaderId) { return GL.getShader(ctx, shaderId) };
		GL.contexts[id].ctx.initShaders = function(shaderIds) { return GL.initShaders(ctx, shaderIds) };
		GL.contexts[id].shaderProgram = null;
	}
	GL.contextCount++;
};

GL.getContextID = function(ctx)
{
	for(var id in GL.contexts)
	{
		if(GL.contexts[id] == ctx)
			return id;
	}
	var generatedId = "generatedId" + GL.contextCount;
	GL.registerContext(generatedId, ctx, GL.TYPE_UNKNOWN);
	return generatedId;
};

GL.getShader = function(gl, shaderId)
{
    var shaderScript = document.getElementById(shaderId);
    if (!shaderScript)
        return null;

    var str = "";
    var k = shaderScript.firstChild;
    while (k)
    {
        if (k.nodeType == 3)
            str += k.textContent;
        k = k.nextSibling;
    }
    
    alert(str);
    alert(shaderScript.innerHTML);

    var shader;
    if (shaderScript.type == "x-shader/x-fragment")
        shader = gl.createShader(gl.FRAGMENT_SHADER);
    else if (shaderScript.type == "x-shader/x-vertex")
        shader = gl.createShader(gl.VERTEX_SHADER);
    else
        return null;

    gl.shaderSource(shader, str);
    gl.compileShader(shader);

    if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS))
        throw new Error(gl.getShaderInfoLog(shader));

    return shader;
};

GL.initShaders = function(gl, shaderIds)
{
	var ctxId = GL.getContextID(gl);
	if(GL.contexts[ctxId].shaderProgram == null)
		GL.contexts[ctxId].shaderProgram = gl.createProgram();
	
	var shaders = new Array();
	for(var i = 0; i < shaderIds.length; i++)
	{
		shaders[i] = GL.getShader(gl, shaderIds[i]);
	    gl.attachShader(GL.contexts[ctxId].shaderProgram, shaders[i]);
	}
	
    gl.linkProgram(GL.contexts[ctxId].shaderProgram);
    if (!gl.getProgramParameter(GL.contexts[ctxId].shaderProgram, gl.LINK_STATUS))
        throw new Error("Could not initialise shaders");
	gl.useProgram(GL.contexts[ctxId].shaderProgram);;

	return GL.contexts[ctxId].shaderProgram;
};