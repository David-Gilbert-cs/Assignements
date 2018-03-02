#include <stdio.h>
#include <stdlib.h>

int main( int argc, const char* argv[] ){


FILE *fp;
char encrypted[50];


 char *ptr;
int key=strtol(argv[2],&ptr,2);


fp = fopen(argv[1],"r");  /* reading from file */
int i=0 ;
char ch;
 while(!feof(fp)){

    ch = fgetc(fp);

if(ch==32 ){    /*space case*/
encrypted[i]=32;
}

else if(ch+key>90){   /* right shift passes Z and goes back to A*/
    encrypted[i]= ch-26+key;
    printf("%c",encrypted[i]);
 }
 else{              /*generic case*/
    encrypted[i]=ch+key;
   printf("%c",encrypted[i]);
 }

i++;

 }

  fclose(fp);

FILE *f = fopen(argv[1], "wb");    /*write the char array in the initial file */
fwrite(encrypted, sizeof(char), sizeof(encrypted), f);
fclose(f);

}

