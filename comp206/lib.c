#include <time.h>
#include <unistd.h>
#include <stdio.h>

long fib(long n){
    if (n <= 2) return 1;
    else return fib(n-1) + fib(n-2);  
}

void sort(int x){//sorting algo
srand(time(NULL));
printf("sorting started \n");
int random[x];
int temp;
printf("random number generated \n ");
for(int i=0;i<x;i++){// creates random int and put them in array
random[i]=rand()%100;
printf("%4d",random[i]);
}


for (int c=0; c<(x-1); c++){
 for (int d = 0 ; d<(x-c-1); d++){
  if (random[d] > random[d+1]){
temp = random[d];
random[d] = random[d+1];
random[d+1] = temp;
  }
 }
}
printf("\n sorted \n "); //recursive fibonacci 
for(int i=0;i<x;i++) printf("%4d ",random[i]);
printf("\n sorting ended \n");

}
