syncnapsis installation guide
=============================

This a short instruction on "how to install syncnapsis?".
I won't go into detail too deeply so I assume you have some experience in configuring development environments...

preparation
===========

Before we can acutally start with syncnapsis specific stuff we want to be well prepared... 
The following list contains all the stuff you will have to download and since we do not necessarily wait for a download each time we process to the next step in this guide I included this so we can donwload all the stuff before we start and while we do the first steps.
The versions posted here are those versions I worked with by 2013-05-06. Please forgive me if I might forget to update versions in future but newer versions should mostly work...

A. Java JDK 6 or 7
(be sure to install the JDK - the JRE won't be enough)
-> http://www.oracle.com/technetwork/java/javase/downloads/index.html

B. Git for Windows 1.8.1.2 (msysgit)
-> http://code.google.com/p/msysgit/downloads/list

C. Tortoise Git 1.8.0.0
-> http://code.google.com/p/tortoisegit/downloads/list

D. PostgreSQL 9.1.5
-> http://postgresql.org/download

E. Eclipse IDE for Java EE Developers
(classic version will do, too - but won't contain all the web editors & tools)
-> http://www.eclipse.org/downloads/

F. jetty 8.1.9
-> http://download.eclipse.org/jetty/

G. maven 2.2.1
(or higher version within maven 2 - not maven 3!)
-> http://maven.apache.org/download.cgi

installing & configuring the tools
==================================

To run syncnapsis we need the java runtime environment and the jetty servlet container but if you want to build or use syncnapsis we need some more.
The following steps will give you a short guide on how to install the required tools and configure your development environment.

1. Install java
Download and install the Java JDK (see A).
Installation should be self explaning.

2. Install git
Download and install git for windows (see B) first.
Whithin that installation you have the choice between "Explorer Integration" or not. I personally prefer tortoise git (see C) for explorer integration, so if you choose to deselect that option I recommend to install tortoise git afterwards.
The rest of the installations should be self explaning. For me all the default selected settings (except "Explorer Integration" for git for windows) were satisfying...

3. Install postgres
Download and install postgreSQL database (see D).
The installation should be self explaning as well... Be sure to remember the password you define for the super user "postgres" during installation.
Before we proceed we have to create the database and the db-user used by syncnapsis during tests.
3.1. start pgAdmin (you will find this installed with your postgreSQL database
3.2. login to localhost with the super-user "postgres" and the password you defined during installation by double clicking the server "PostgreSQL 9.1 (localhost:5432)" on the left
3.3. right click the role option in the tree view (don't know the English label since my postgres is installed in German) to create a new login-role (aka. user)
- name that user "syncnapsis_test" with password "syncnapsis_test" (on second tab)
3.4. right click the database option in the tree view (don't know the English label here, too) to create a new database
- name that database "syncnapsis_test"
- select "syncnapsis_test" as the owner
- ensure encoding is set to UTF-8
3.5. to browse the database double click the new item in the tree view: "syncnapsis_test"
Generally it is the best option to create separate databases (and users) for tests and production since you do not want to overwrite the "real" database accidentially. So repeat the previous steps again for your individual user and database...

4. Install eclipse
Actually installing eclipse is simply done be donwloading and unpacking the archive to the location you want eclipse to be installed - that's it...

5. Install jetty
installing jetty is simple, since it is ready to use just by extracting the archive as well. But personally prefer to add a start script for instant use:
- create a file called "start.bat" with the following content: "java -jar start.jar" (without quotation marks)
Now we need to create the context file for syncnapsis or your project or otherwise it won't be loaded on jetty startup:
To do this create a file named "syncnapsis.xml" in %jett_home%/contexts with the following content:
	<?xml version="1.0"  encoding="ISO-8859-1"?>
	<!DOCTYPE Configure PUBLIC "-//Jetty//Configure//EN" "http://www.eclipse.org/jetty/configure.dtd">
	<Configure class="org.eclipse.jetty.webapp.WebAppContext">
		<Set name="contextPath">/universe</Set>
		<Set name="resourceBase">D:/info/syncnapsis/syncnapsis-universe/syncnapsis-universe-conquest/target/webapp</Set>
		<Set name="copyWebDir">false</Set>
	</Configure>
Of course you have to modify the properties according to your requirements so here is the short explanation:
- contextPath: the path the application will be available - in this example: http://localhost:8080/universe
- resourceBase: the path to the assembled webapp in your project / syncnapsis project (syncnapsis is configured to create a webapp dir in the target folder of the specififc project) - of course you can use the hello-world project here as well
- copyWebDir: configures jetty directly to access the files within the webapp assembly folder and not to copy the files into a directory within jetty (guarantees the application to be always up-to-date when developing)
The name of the file is up to your choice and does not have any influence on the configuration.

6. Install maven
maven is installed by just extracting the archive, too. Generally maven is ready to start immediately but I personally prefer to do some configurations I want to explain shortly:
Maven contains a repository holding all the libraries used by your project. Normally this repository is located in your profiles director at .m2/repository but since I like separating data from the OS I relocated this repository.
- to change the path of your local repo you have to edit %maven_home%/conf/settings.xml. Within that file there should be a line containing the location of your repo which has to uncommented and modified to the desired location, e.g.
	<localRepository>D:/info/repository</localRepository>

7. Configure the environment variables
Some of the tools we just installed require some environment variables to work properly as well as the build tools from syncnapsis will need some.
To edit the environment variables navigate to (assuming you have Windows 7 or 8)
- right-click on computer -> properties
- select extended system settings (don't know the exact label, but it should be the last option on the upper left)
- select the third tab "extended" (or something like that)
- select "Environment Variables"
- create all of the following variables in the lower "system variables" part (all paths below are examples and might have to be modified for your system)
M2_HOME: 	D:\info\develop\apache-maven-2.2.1
M2_REPO: 	D:\info\repository
S_HOME:		D:\info\syncnapsis
GIT_HOME:	C:\Program Files (x86)\Git
JAVA_HOME:	C:\Program Files\Java\jdk1.7.0
DEV_PATH:	%M2_HOME%\bin;%S_HOME%\bin;%JAVA_HOME%\bin;%GIT_HOME%\bin
PATH:		append ;%DEV_PATH% to the existing value
- verify the onfiguration by opening a console and type the following commands. If they are found (and some output other than "not found" is printed the environment is configured correctly.
mvn 		-> maven is configured correctly
java		-> java is configured correctly
s_java		-> syncnapsis is configured correctly

installing syncnapsis
=====================

Well now that we got all the tools we can finally start installing and using syncnapsis. Using git we can simply clone the complete repository from github and using maven the build procedure is very easy as well.

1. clone syncnapsis from github:
in the folder you want the syncnapsis-directory to be placed do the following:
- right-click "Git clone" (using tortoise git explorer integration)
- select URL: https://github.com/syncnapsis/syncnapsis.git
- be sure the directory points to the path you configured for syncnapsis (git will automatically append "/syncnapsis" to the current folder, so if you already are within the syncnapsis-directory you might have to remove one folder level)
- click "OK"
Now syncnapsis will be cloned (this may take a while...)

2. init & pull all the submodules
Since syncnapsis is a modular project git requires you to init and pull all the submodules. I provided a script that will do that for you, so you don't have to do it manually for all the modules (which are quite a lot...). Additionally git does not automatically swith to the master branch which will be done in an extra command.
- open a console and navigate to your syncnapsis-directory
- execute "s_git_initsubmodules"
- execute "s_git_checkoutmaster"
Now you should see the following modules at top level of syncnapsis:
- syncnapsis-core
- syncnapsis-dev
- syncnapsis-examples
- syncnapsis-modules
- syncnapsis-universe
Wihtin all of these directories there should be multiple modules and each of those directories should not be empty but contain at least a the maven "pom.xml" and a "src"-folder. If this is the case, initialization was successful and we can start using syncnapsis :-)

3. using syncnapsis in eclipse
If you just want to build syncnapsis without modifying you can skip the following steps...
To use the project within eclipse we need a project file to be generated by maven. As a shortcut I created a script for that, too:
- in the console type "s_eclipse" within the syncnapsis-directory
Maven will create several ".project" and ".classpath" files and a ".settings" directory in the submodules which are used by eclipse
- start eclipse and select the syncnapsis directory as your workspace
- now we can import all the projects into eclipse. Unfortunately this requires some manual work (since eclipse is not designed for nested projects which are created due to the group directories like "syncnapsis-core"...)
- you can choose wether you want to import those group projects as well. They are not necessary for building or programming (they are just used for configuration and modularity)
- for each project do: File -> Import -> Existing Projects into Workspace -> Next -> Browse to the project directory -> select the project -> Finish
When you are finished there should be one project for each project directory you can see within the syncnapsis-directory (and subdirectories).
If you just want to use/build some of the modules be sure to remember the dependencies and at least add all required projects to make them available as prerequisites.
Finally you will notice all the projects are marked with a red exclamation mark notifying you something prevents the projects from being built because eclipse does not know "M2_REPO"...
- select "Window" -> "Preferences" -> "Java" -> "Build Path" -> "Classpath Variables"
- create a new variable named "M2_REPO" poiting to your maven repo (see above)
- confirm with OK and let eclipse rebuild the whole workspace

4. additional eclipse configuration 
The following configurations are optional, but if you want to participate in syncnapsis I kindly ask you to configure your workspace with the following settings to guarantee a uniform style of the code:
- select "Window" -> "Preferences" -> "Java" -> "Code Style" -> "Formatter" -> "import" -> select "format-java.xml" from the syncnapsis-directory
- select "Window" -> "Preferences" -> "Java Script" -> "Code Style" -> "Formatter" -> "import" -> select "format-js.xml" from the syncnapsis-directory

5. building syncnapsis (simple)
Assuming you did not make any changes to syncnapsis building is done using a single command:
- just enter "s_build_untested" in the console in the syncnapsis-directory and all underlying projects will be build in dependency order and installed to your local repo

6. building syncnapsis (advanced)
The full build process (including tests, setting up the database, etc.) ist not yet fully automated but is quite good to handle using a couple of scripts
- execute "s_db" inside one of the child projects (this is a fusion of the following three commands, see explanation below). Of course you can execute all the commands separately, too if you want full control.
-- executes "s_db_drop" inside one of the child projects (currently only works in projects containing a jdbc.properties like syncnapsis-core-application-base or syncnapsis-universe-conquest etc.)
-- executes "s_db_prepare" at top level of syncnapsis
-- executes "s_db_sequence" inside one of the child projects (reason see "s_db_drop")
- execute "s_build" at top level of syncnapsis
