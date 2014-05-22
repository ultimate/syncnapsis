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
//@requires("RPCSocket")

Types = {};
Types.Action = "com.syncnapsis.data.model.Action";
Types.BlackList = "com.syncnapsis.data.model.BlackList";
Types.BlackListEntry = "com.syncnapsis.data.model.BlackListEntry";
Types.Messenger = "com.syncnapsis.data.model.Messenger";
Types.MessengerContact = "com.syncnapsis.data.model.MessengerContact";
Types.Pinboard = "com.syncnapsis.data.model.Pinboard";
Types.PinboardMessage = "com.syncnapsis.data.model.PinboardMessage";
Types.User = "com.syncnapsis.data.model.User";
Types.UserContact = "com.syncnapsis.data.model.UserContact";
Types.UserRole = "com.syncnapsis.data.model.UserRole";

EntityManager = function(server)
{
	if(server == null)
		throw new Error("server must not be null!");
	var _server = server;
	
	this.getType = function(entity)
	{
		return entity["j_type"];
	};
	
	this.getManagerNameForType = function(type)
	{
		type = type.substring(type.lastIndexOf(".")+1);
		type = type.substring(0,1).toLowerCase() + type.substring(1);
		return type + "Manager";
	};
	
	this.extend = function(entity)
	{
		if(entity.merge == undefined)
		{
			entity.merge = function(other, typeMask)
			{
				if(typeMask == undefined)
					typeMask = Reflections.typeMask.ALL;
				for( var prop in other)
				{
					if((Reflections.getTypeMask(typeof other[prop]) & typeMask) != 0)
					{
						this[prop] = other[prop];
					}
				}
				return this;
			};			
		}
	};
	
	this.load = function(entity, callback)
	{
		// assure entity-functionality
		this.extend(entity);
		// get the type of the entity
		var type = this.getType(entity);
		// get the corresponding manager-name
		var manager = this.getManagerNameForType(type);
		if(_server[manager] == null)
			throw new Error("required manager '" + manager + "' not found for type '" + type + "'");
		// get the entity from the manager
		_server[manager].get(entity.id, function(result) { entity.merge(result); if(callback != undefined) (callback)(entity); } );
	};
};

// unused and experimental!!!
/*
// the ApplicationBase-Object
ApplicationBase = function()
{
	// manager functions will be filled be proxy
	this.userManager = function()
	{
		this.login = function(username, password)
		{
		};
		this.logout = function()
		{
		};
		this.register = function(username, email, password, password2)
		{
		};
		this.isNameAvailable = function(username)
		{
		};
		this.getOrderedByName = function()
		{
		};
	};
	// TODO more managers
};

if(!config)
{
	console.error("No application configuration provided! Please add global 'config'-variable!");
	throw new Error("No application configuration provided! Please add global 'config'-variable!");
}
*/