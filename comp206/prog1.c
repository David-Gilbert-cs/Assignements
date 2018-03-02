#include<stdlib.h>
#include<stdio.h>

int main(int argc, char *argv[]){


/*printing the sum of numbers */
 FILE* file = fopen (argv[1], "r");
  int i = 0;
  int sum=0;
printf("the input numbers are :");
  fscanf (file, "%d", &i);    
  while (!feof (file))
    {  
      sum = sum+i; 
      printf (" %d ", i);   /* prints all int from the file */
      fscanf (file, "%d", &i);      
    }
  fclose (file); 
  printf ("the input sum is %d \n ", sum); 


FILE* file2 = fopen (argv[1],"r" );

 fscanf (file2, "%d", &i); //gets first value in file
  while (!feof (file)) {

int k;   //r is

int j=1,f;
int m=1;

 for(k=2;k<i;k++){
         f=m+j; // add last two starting with 1+1  `
         m=j; 
         j=f;
            }
printf("fib(%d)=%d \n ",i,j);
fscanf(file2, "%d",&i);
    }
fclose (file2);
}
