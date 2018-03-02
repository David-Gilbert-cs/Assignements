#!/bin/bash
#reated by: Vasileios Airantzis
# Edit by   : Joseph Vybihal

#assigns the variables acording to the number of arguments passed
NewProject=$0
if [ $# = 2 ]
then
        path=$1
        project_name=$2
else
        project_name=$1
        path=""
fi

#checks if the project name is missing
if [ "$project_name" = "" ]
then
        echo 'Project name is missing. Type in: NewProject path project_name'
        exit
fi

#checks if the user has entered a path and if yes then it checks if it is of thecorrect form
if [ "$path" != "" ]
then
        if [ -d "$path" ]
        then :
        else
                echo 'Your path name is malformed. Type in: NewProject path project_name'
                exit
        fi
fi

#makes all the directories
if [ "$path" != "" ]
then
        mkdir "$path"/"$project_name"
        mkdir "$path"/"$project_name"/docs
        mkdir "$path"/"$project_name"/source
        mkdir "$path"/"$project_name"/backup
        mkdir "$path"/"$project_name"/archive
        cd "$path"/"$project_name"/source
else
        mkdir "$project_name"
        mkdir "$project_name"/docs
        mkdir "$project_name"/source
        mkdir "$project_name"/backup
        mkdir "$project_name"/archive
        cd "$project_name"/source
fi

# ACTUAL CHANGE FOR Q3
touch makefile
chmod +x makefile
echo "#!/bin/bash ">makefile
name=`echo "$3" | cut -d'.' -f1`
ext=`echo "$3" | cut -d'.' -f2`

echo -n "$name :" >>makefile
shift
shift

for var in "$@"
do
  name1=`echo "$var" | cut -d'.' -f1`
  echo -n "$name1">>makefile
  echo -n ".o ">>makefile
done 
echo "">>makefile
echo -n  "gcc -o $name ">>makefile
for var in "$@"
do
  name1=`echo "$var" | cut -d'.' -f1`
  echo -n " $name1">>makefile
  echo -n ".o ">>makefile
done
echo "">>makefile
for var in "$@"
do
  name1=`echo "$var" | cut -d'.' -f1`
  echo -n "$name1">>makefile
  echo -n ".o : ">>makefile
  echo  "$var $name1.h ">>makefile
  echo  "gcc -c $var">>makefile
done 

#copy everything in the backup dir
cp -r * ../backup/

if [ "'"$1"'" = "'"-o"'" ]
then
   gcc -o "'"$exename"'" "'"$filenames"'" 2> error.txt
else
   gcc "'"$filenames"'" 2> error.txt
fi


exit
