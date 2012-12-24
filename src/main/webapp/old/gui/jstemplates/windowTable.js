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
var windowTable = '<table width="100%" height="100%" border=0 cellspacing=0 cellpadding=0>' +
						 '<tr bgcolor="#000000" height={outerFrameSize}>' +
						 	'<td id="{windowName}.resize1" width={outerFrameSize} style="cursor: nw-resize; background-image:url(images/skins/{skinname}/window/window_northwest.png)" direction="nw"><img src="images/spacer.gif"></td>' +
						 	'<td id="{windowName}.resize2" width={innerFrameSize} style="cursor: nw-resize; background-image:url(images/skins/{skinname}/window/window_north.png)" direction="nw"><img src="images/spacer.gif"></td>' +
						 	'<td id="{windowName}.resize3"                        style="cursor: n-resize; background-image:url(images/skins/{skinname}/window/window_north.png)"  direction="n" ><img src="images/spacer.gif"></td>' +
						 	'<td id="{windowName}.resize4" width={innerFrameSize} style="cursor: ne-resize; background-image:url(images/skins/{skinname}/window/window_north.png)" direction="ne"><img src="images/spacer.gif"></td>' +
						 	'<td id="{windowName}.resize5" width={outerFrameSize} style="cursor: ne-resize; background-image:url(images/skins/{skinname}/window/window_northeast.png)" direction="ne"><img src="images/spacer.gif"></td>' +
						 '</tr>' +
						 '<tr height={titelSize} id="{windowName}.titelrow">' +
						 	'<td id="{windowName}.resize6" width={outerFrameSize} bgcolor="#000000" style="cursor: nw-resize; background-image:url(images/skins/{skinname}/window/window_west.png)" direction="nw"><img src="images/spacer.gif"></td>' +
						 	'<td bgcolor="#777777" colspan=3 style="vertical-align: top;">' +
						 		'<table height="100%"  width="100%" border=0 cellspacing=0 cellpadding=0>' +
						 			'<tr>' +
						 			 '<td width={innerFrameSize} style="vertical-align: top; background-image:url(images/skins/{skinname}/window/window_northwest2.png)"><img src="images/spacer.gif"></td>'+
						 				'<td style="cursor: default; vertical-align: top; color:white;font-family:Verdana; font-size:14px;background-image:url(images/skins/{skinname}/window/window_north2.png)" id="{windowName}.titelbar">' +
						 					'{windowTitle}' +
						 				'</td>' +
						 				'<td style="vertical-align: top; text-align:right;background-image:url(images/skins/{skinname}/window/window_north2.png)">' +
						 					'<img id="{windowName}.upbutton" src="images/skins/{skinname}/button/up_normal.png" onmouseover="mouseOverUP('+"'"+'{windowName}'+"'"+');" onmouseout="mouseOutUP('+"'"+'{windowName}'+"'"+');" onmousedown="mouseDownUP('+"'"+'{windowName}'+"'"+');" onmouseup="mouseOutUP('+"'"+'{windowName}'+"'"+');"  onClick="minmaxWindow('+"'"+'{windowName}'+"'"+');">' +
						 					'<img id="{windowName}.closebutton" src="images/skins/{skinname}/button/close_normal.png" onmouseover="mouseOverCLOSE('+"'"+'{windowName}'+"'"+');" onmouseout="mouseOutCLOSE('+"'"+'{windowName}'+"'"+');" onmousedown="mouseDownCLOSE('+"'"+'{windowName}'+"'"+');" onmouseup="mouseOutCLOSE('+"'"+'{windowName}'+"'"+');" onClick="closeWindow('+"'"+'{windowName}'+"'"+');">' +
						 				'</td>' +
						 			 '<td width={innerFrameSize} style="background-image:url(images/skins/{skinname}/window/window_northeast2.png)"><img src="images/spacer.gif"></td>'+						 				
						 			'</tr>' +
						 		'</table>' +
						 	'</td>' +

						 	'<td id="{windowName}.resize7" width={outerFrameSize} bgcolor="#000000" style="cursor: ne-resize; background-image:url(images/skins/{skinname}/window/window_east.png)" direction="ne"><img src="images/spacer.gif"></td>' +
						 '</tr>' +
						 '<tr height={contentHeight} id="{windowName}.contentRow">' +
							 '<td id="{windowName}.resize8"  width={outerFrameSize} style="cursor: w-resize; background-image:url(images/skins/{skinname}/window/window_west.png)" direction="w"><img src="images/spacer.gif"></td>' +
							 '<td width={innerFrameSize} bgcolor="{contentbgcolor}" style="background-image:url(images/skins/{skinname}/window/window_west2.png)"><img src="images/spacer.gif"></td>' +
							 '<td width={contentWidth}  bgcolor="{contentbgcolor}">'+
							 '<div id="{windowName}.contentField" style="width:{contentWidth}; height:{contentHeight}; overflow:scroll;"></div>' +
//							 '<object data="empty.html" type="text/html" width="100%" height="100%"><param name="scr" value="empty.html">Das geht nicht</object>'+
							 '</td>' +
							 '<td width={innerFrameSize} bgcolor="{contentbgcolor}" style="background-image:url(images/skins/{skinname}/window/window_east2.png)"><img src="images/spacer.gif"></td>' +
							 '<td id="{windowName}.resize9" width={outerFrameSize} style="cursor: e-resize; background-image:url(images/skins/{skinname}/window/window_east.png)" direction="e"><img src="images/spacer.gif"></td>' +
						 '</tr>' +
						 '<tr height={innerFrameSize} id="{windowName}.southInnerBorderRow">' +
						 	'<td id="{windowName}.resize10" width={outerFrameSize} style="cursor: sw-resize; background-image:url(images/skins/{skinname}/window/window_west.png)" direction="sw"><img src="images/spacer.gif"></td>' +
						 	'<td width={innerFrameSize} bgcolor="{contentbgcolor}" style="background-image:url(images/skins/{skinname}/window/window_southwest2.png)"><img src="images/spacer.gif"></td>' +
						 	'<td style="background-image:url(images/skins/{skinname}/window/window_south2.png)" bgcolor="{contentbgcolor}"><img src="images/spacer.gif"></td>' +
						 	'<td width={innerFrameSize} bgcolor="{contentbgcolor}" style="background-image:url(images/skins/{skinname}/window/window_southeast2.png)"><img src="images/spacer.gif"></td>' +
						 	'<td id="{windowName}.resize11" width={outerFrameSize} style="cursor: se-resize; background-image:url(images/skins/{skinname}/window/window_east.png)" direction="se"><img src="images/spacer.gif"></td>' +
						 '</tr>' +
							'<tr bgcolor="#000000" height={outerFrameSize}>' +
						 	'<td id="{windowName}.resize12" direction="sw" width={outerFrameSize} style="cursor: sw-resize; background-image:url(images/skins/{skinname}/window/window_southwest.png)"></td>' +
						 	'<td id="{windowName}.resize13" direction="sw" width={innerFrameSize} style="cursor: sw-resize; background-image:url(images/skins/{skinname}/window/window_south.png)"><img src="images/spacer.gif"></td>' +
						 	'<td id="{windowName}.resize14" direction="s" style="cursor: s-resize; background-image:url(images/skins/{skinname}/window/window_south.png)"><img src="images/spacer.gif" ></td>' +
						 	'<td id="{windowName}.resize15" direction="se" width={innerFrameSize} style="cursor: se-resize; background-image:url(images/skins/{skinname}/window/window_south.png)"><img src="images/spacer.gif"></td>' +
						 	'<td id="{windowName}.resize16" direction="se" width={outerFrameSize} style="cursor: se-resize; background-image:url(images/skins/{skinname}/window/window_southeast.png)"><img src="images/spacer.gif"></td>' +
						 '</tr>' +
						 '</table>';
