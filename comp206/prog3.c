#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include  <sys/types.h>
#include <sys/wait.h>
#include "lib.h"  // include .c for testing purpose

int main(int argc,char* argv[]){

int x = atoi(argv[1]);
int y = atoi(argv[2]);
int f;
int g;
int status=0;

f=fork();
if((f!=0)) g=fork();

if( f==0){// first child works here 
  sort(x);
}

if(f!=0 && g==0){ //second works here
  long fibo;
  printf("fibonacci started  \n y=%d \n",y);
  fibo=fib(y);
  printf("   fib(%d)=%ld \n ",y,fibo);
  printf("fibonacci ended \n ");
}
wait(0);
wait(0);

return 0;
}

