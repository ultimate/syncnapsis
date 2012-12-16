//@requires("UI")

PlayerManager = function()
{
	this.login = function(username, password)
	{
		// return Player
		return function(player) { UI.onLogin(player); };
	};
	
	this.logout = function()
	{
		// return boolean
		return function(success) { UI.onLogout(success); };
	};
	
	this.register = function(username, email, password, passwordConfirm)
	{
		// return Player
	};
};