@REM
@REM Syncnapsis Framework - Copyright (c) 2012 ultimate
@REM 
@REM This program is free software; you can redistribute it and/or modify it under the terms of
@REM the GNU General Public License as published by the Free Software Foundation; either version
@REM 3 of the License, or any later version.
@REM 
@REM This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
@REM without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
@REM See the GNU General Public License for more details.
@REM 
@REM You should have received a copy of the GNU General Plublic License along with this program;
@REM if not, see <http://www.gnu.org/licenses/>.
@REM
@echo off
echo INFO: This will set global git config!
echo INFO: Credentials will be cached on every usage for about 15 minutes.
echo INFO: Caching for windows requires "git-credential-winstore.exe" either located in syncnapsis/bin or %%git-folder%%/bin.
echo Which OS do you run? W (windows) / X (unix/linux) / C (cancel)
:os
set /P accept=
if "%accept%"=="W" goto win
if "%accept%"=="w" goto win
if "%accept%"=="X" goto unix
if "%accept%"=="x" goto unix
if "%accept%"=="C" goto end
if "%accept%"=="c" goto end
echo invalid selection
goto os
:win
git config --global credential.helper winstore
goto end
:unix
git config --global credential.helper cache
:end