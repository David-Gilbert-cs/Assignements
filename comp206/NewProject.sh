#!/bin/bash

#checks if there is the minimum number of argument (project name)
if [[ $1 == 0 ]]
then
echo “Project name is missing. Type in: NewProject path project_name” 
exit 1
fi


if [[ $2 == 0 ]]
 then

   if [[ ! -d "$1" ]]
 then                 #test if the path is a valid one
   echo “Your path name is malformed. Type in: NewProject path project_name” 
   exit 2
   fi

cd $1                # $1 should be the path in this case
mkdir $2
cd $2
mkdir docs & mkdir source & mkdir backup  & mkdir archive

echo '#!/bin/bash' > compile           # writing the compile file
chmod 700 compile
echo 'shift' >> compile                    #shifting arguments
echo 'gcc $@ ' >> compile               #the gcc command with all argument including all files

else    #if only one argument
mkdir $1
cd $1
mkdir docs & mkdir source & mkdir backup  & mkdir archive
echo '#!/bin/bash' > compile
chmod 700 compile
echo 'shift' >> compile                    #shifting arguments
echo 'gcc $@ ' >> compile               #the gcc command with all argument including all files


fi
~                                                                                                                                                                                                           
~           
