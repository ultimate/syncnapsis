/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
//@requires("game-base")

Types.Galaxy = "com.syncnapsis.data.model.Galaxy";
Types.Match = "com.syncnapsis.data.model.Match";
Types.Participant = "com.syncnapsis.data.model.Participant";
Types.SolarSystem = "com.syncnapsis.data.model.SolarSystem";
Types.SolarSystemInfrastructure = "com.syncnapsis.data.model.SolarSystemInfrastructure";
Types.SolarSystemPopulation = "com.syncnapsis.data.model.SolarSystemPopulation";

ServerUIManager = function()
{
};

ServerUIManager.prototype.selectLocale = function(selection)
{
	// stub for server-entity
};

ServerMessageManager = function()
{
};

//public void postPinboardMessage(Long pinboardId, String title, String message);
ServerMessageManager.prototype.postPinboardMessage = function(pinboardId, title, message)
{
	// stub for server-entity
};

//public void requestPinboardUpdate(Long pinboardId, int messageCount);
ServerMessageManager.prototype.requestPinboardUpdate = function(pinboardId, messageCount)
{
	// stub for server-entity
};