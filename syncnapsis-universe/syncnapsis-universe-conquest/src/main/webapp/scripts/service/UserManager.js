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
//@requires("GenericManager")
//@requires("Client")

UserManager = function()
{	
	this.requestPasswordReset = function(username, email)
	{
		// return PLayer
		return function(result) {
			if(result == true)
			{
				client.uiManager.showErrorMessage(null, document.getElementById(UI.constants.FORGOT_MESSAGE_ID), "message.register");
			}
			else
			{
				client.uiManager.showErrorMessage(null, document.getElementById(UI.constants.FORGOT_ERROR_ID), "message.error");
			}
		};
	};
};

UserManager.prototype = new GenericManager();