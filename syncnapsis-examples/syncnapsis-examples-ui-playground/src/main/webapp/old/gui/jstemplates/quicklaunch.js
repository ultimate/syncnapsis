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

//var templateQuicklaunch = ''; /*+
/*'<div id="quicklaunch" style="position:absolute; top:60px; left:20px; width:52px; height:60px;">'+
'<div style="position:absolute; top:0px; left:0px; height:13px; width:52px; background-image:url({imagepath}{skinname}/quicklaunch/top.png)"></div>'+
'<div style="position:absolute; top:13px; left:0px; bottom:5px; right:0px; background-image:url({imagepath}{skinname}/quicklaunch/middle.png)"></div>'+
'<div style="position:absolute; bottom:0px; left:0px; height:5px; width:52px; background-image:url({imagepath}{skinname}/quicklaunch/bottom.png)"></div>'+
'</div>';
*/
var templateQuickLaunchContext = ''+
'<div id="quicklaunch_context" style="position:absolute; width:200px; height:100px; display:none; z-index:255;">'+
'<div style="position:absolute; width:200px; height:6px; background-image:url({imagepath}{skinname}/contextmenu/top.png)"></div>'+
'<div style="position:absolute; width:200px; bottom:5px; top:6px; background-image:url({imagepath}{skinname}/contextmenu/middle.png)"></div>'+
'<div style="position:absolute; width:200px; bottom:0px; height:5px; background-image:url({imagepath}{skinname}/contextmenu/bottom.png)"></div>'+
'<div id="quicklaunch_menupunkt1" onclick="quicklaunch_remove_button();" onmouseout="context_highlight_off(this);" onmouseover="context_highlight_on(this);" style="position:absolute; top:4px; left:4px; width:192px; height:24px; color:#0293c9; font-size:14px; background-image:url({imagepath}{skinname}/contextmenu/menupunkt_normal.png);"><div style="position:absolute; top:3px; left:6px; cursor:default;">Men�punkt entfernen</div></div>'+
'</div>';
