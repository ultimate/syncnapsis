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
echo WARNING: credentials will be stored unencrypted in .git-credentials in your home directory!
echo Do you really want to store your credentials for this project and all submodules? Y/N
:accept
set /P accept=
if "%accept%"=="Y" goto yes
if "%accept%"=="y" goto yes
if "%accept%"=="N" goto no
if "%accept%"=="n" goto no
echo invalid selection
goto accept
:yes
git config credential.helper store
git submodule foreach --recursive git config credential.helper store
:no