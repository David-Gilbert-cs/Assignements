#!/bin/bash

while true; do
echo "new game"


#fix the if stament, average outside gives 0

if [[ -s Data.pg ]]
then
echo "file exit"
else
n=0
while [[ $n -lt 10 ]]
do
echo $((RANDOM%50))>>Data.pg
n=$((n+1))
done
fi

sum=0
counter=0
win=1 
average=0

while [[ ($win) && ($counter<3) ]]  #single game loop
do
counter=$((counter+1))
        
       # recompute average
while read line
do
sum=$((sum+line))
average=$(($sum/10))
done < Data.pg


echo "$average this is it "

echo 'enter your guess for the 2\3 of the average up to a 10% difference: '
read guess									#prompt for guess
echo "$guess is the guess"


if [ $guess -ge $((2*9*$average/30)) ] && [ $guess -le $((11*2*$average/30)) ]      # is guess right ?
then     
win=0
echo "YOU WON !!!!!!!!!!!!!"
echo "you took $counter tries"

echo $guess >> Data.pg # replacing one the guesses
sed -i -e '1d' Data.pg


   # finds the average number of tries
while read line
do
sum2=$((sum2+line))
done < Data2.pg

numOfLines=$(wc -l < Data2.pg)
averagetries=$(($sum2/$numOfLines))
echo "the average number of tries is $averagetries "

echo $counter>>Data2.pg     #adds the new number of guess 
counter=0
sum=0
average=0
sum2=0
else
echo "incorect try again:"
fi

echo $guess >> Data.pg # replacing one the guesses
sed -i -e '1d' Data.pg

done 

done #end infinite game loop
# STILL NEEDS THE D) THE ENCRYPTION THING
