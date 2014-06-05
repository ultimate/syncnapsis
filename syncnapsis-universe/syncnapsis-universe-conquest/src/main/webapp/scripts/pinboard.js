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
//@requires("Server")

/*
 * This a view Entity for a Pinboard identified by its ID
 * (Messages are sorted newest first!) 
 */
Pinboard = function(container, pinboardIdOrName, removeOldMessages)
{
	this.pinboard = null;
	this.messages = [];
	this.messageCount = 0; 
	this.removeOldMessages = removeOldMessages;
	this.container = container;
	
	// TODO start - init view
	this.messageContainer = document.createElement("div");
	this.container.appendChild(this.messageContainer);
	// end - init view
	
	this.setUser = function(user)
	{
		this.user = user;
		// TODO lock/unlock
	};
	
	this.addMessage = function(message)
	{
		// add message at right position
		var i;
		for(i = 0; i < this.messages.length; i++)
		{
			if(this.messages[i].messageId < message.messageId)
				break;
		}
		this.messages.splice(i, 0, message);
		// create dom element
		message.element = document.createElement("span");
		message.element.innerHTML = message.content; // TODO
		
		this.messageContainer.insertBefore(message.element, this.messageContainer.children[i]);
	};
	
	this.removeMessage = function(messageId)
	{
		var index = null;
		for(var i = 0; i < this.messages.length; i++)
		{
			if(this.messages[i].messageId == messageId)
			{
				index = i;
				break;
			}
		}
		if(index != null)
		{
			this.messageContainer.removeChild(this.messages[index].element);
			this.messages.splice(index, 1);
		}
	};
	
	this.checkForMissingMessages = function()
	{
		var from = null;
		var to = null;
		// check for missing messages
		for(var i = 1; i < this.messages.length; i++)
		{
			if(this.messages[i].messageId != this.messages[i-1].messageId - 1)
			{
				// messageId not consecutive
				if(from == null || from > this.messages[i].messageId)
					from = this.messages[i].messageId;
				if(to == null || to < this.messages[i].messageId)
					to = this.messages[i].messageId;
			}
		}
		if(from != null && to != null)
			this.requestUpdate(from, to);
	};
	
	this.update = function(messages)
	{
		for(var i = 0; i < messages.length; i++)
		{
			this.addMessage(messages[i]);
		}

		if(this.messages.length < this.messageCount)
		{
			this.checkForMissingMessages();
		}
		if(this.removeOldMessages)
		{
			while(this.messages.length > this.messageCount)
			{
				// remove the last message
				this.removeMessage(this.messages[this.messages.length-1].messageId);
			}
		}
	};
	
	this.requestUpdate = function(from, to)
	{
		if(from == null || to == null)
			server.messageManager.requestPinboardUpdate(this.pinboard.id, this.messageCount);
		else
			server.messageManager.requestPinboardUpdate(this.pinboard.id, from, to);
	};
	
	this.setMessageCount = function(messageCount)
	{
		this.messageCount = messageCount;
		this.requestUpdate();
	};
	
	this.init = function(pinboard)
	{
		this.pinboard = pinboard;
		this.messageCount = this.pinboard.defaultMessageCount;
		this.requestUpdate();
	};
	
	// associate this view with a pinboard loaded by name or id
	if(typeof(pinboardIdOrName) == Reflections.type.STRING)
		server.pinboardManager.getByName(pinboardIdOrName, Events.wrapEventHandler(this, this.init));
	else
		server.pinboardManager.get(pinboardIdOrName, Events.wrapEventHandler(this, this.init));
};