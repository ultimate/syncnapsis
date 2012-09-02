//@requires("RPCSockets")

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