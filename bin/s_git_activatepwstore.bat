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
git submodule foreach --recursive git config credential.helper store
:no