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
function quicklaunchAdded(menuID, position) {
		sendPlainRequest('addQuickLaunchItem.html','qlPosTo='+position+'&miId='+menuID, TYPE_RETURN_CONFIRM, null);
}

function quicklaunchRemoved(position) {
		sendPlainRequest('removeQuickLaunchItem.html','qlPosFrom='+position, TYPE_RETURN_CONFIRM, null);	
}

function quicklaunchMoved(from, to) {
		sendPlainRequest('moveQuickLaunchItem.html', 'qlPosFrom='+from+'&qlPosTo='+to, TYPE_RETURN_CONFIRM, null);	
}

function reloadQuicklaunch() {
	sendPlainRequest('quickLaunch.html', null, TYPE_RETURN_CONTENT, function(content, success) {
																quickLaunchElements.parentNode.removeChild(quickLaunchElements);																			 
																disableDrop(quickLaunchElements.middle);
																var tmp = createModelAndInsertMenu(content,document.getElementsByTagName("body")[0], templateQuickLaunch, false);
																delete quickLaunchElements;
																quickLaunchModel = tmp.model;
																quickLaunchElements = tmp.elements;
																disableSelectForAll(quickLaunchElements);
																info("QuickLaunch geladen");});

}