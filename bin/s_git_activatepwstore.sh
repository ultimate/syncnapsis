#
# Syncnapsis Framework - Copyright (c) 2012 ultimate
# 
# This program is free software; you can redistribute it and/or modify it under the terms of
# the GNU General Public License as published by the Free Software Foundation; either version
# 3 of the License, or any later version.
# 
# This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
# without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
# See the GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Plublic License along with this program;
# if not, see <http://www.gnu.org/licenses/>.
#
echo WARNING: credentials will be stored unencrypted in .git-credentials in your home directory!
echo Do you really want to store your credentials for this project and all submodules? Y/N

valid=0

while [ $valid -eq 0 ]
do
	read -n1 CHAR
	echo
	
	if [ $CHAR = "y" ] || [ $CHAR = "Y" ]
	then
		valid=1
		accept=1
	fi
	if [ $CHAR = "n" ] || [ $CHAR = "N" ]
	then
		valid=1
		accept=0
	fi
	
	if [ $valid -eq 0 ]
	then
		echo invalid selection
	fi
done

if [ $accept -eq 1 ]
then
	echo enabling creadentials storing
	git config credential.helper store
	git submodule foreach --recursive git config credential.helper store
fi