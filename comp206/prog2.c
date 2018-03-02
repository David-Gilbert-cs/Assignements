#include<stdlib.h>
#include<stdio.h>
#include<string.h>

char* strncpy1(char* t,int n);
char* strncat1(char* s,char* t);
int strncmp1(char* s,char* t);
 
int main(){

int n;
char* s;
s = malloc(1000);
char* t;
t = malloc(1000);
char* cpy;
char* t2;
t2 = malloc(1000);
cpy = malloc(1000);
char* cat;
cat = malloc(1000);
char* t3;
t3 = malloc(1000);
char* s2;
s2 = malloc(1000);

    printf("Enter the first string:  ");

    fgets(s,1000,stdin); 
size_t size = strlen(s)-1;
if (s[size] == '\n') s[size] = '\0';


    printf("Enter second string ");
    fgets(t,1000,stdin);
size_t size2 = strlen(t)-1;
if (t[size2] == '\n') t[size2] = '\0';


int i =0;
while(t[i]!= '\0'){
t2[i]=t[i];
i++;
}
t2[i] = '\0';
i =0;
while(t[i]!= '\0'){
t3[i]=t[i];
i++;
}
t3[i] = '\0';
i =0;
while(s[i]!= '\0'){
s2[i]=s[i];
i++;
}
s2[i] = '\0';




    printf("Enter the number ");
    scanf("%d",&n);
    
int c;


    c=strncmp1(s,t);
    cpy=strncpy1(t,n);
    cat=strncat1(s,t2);
    

    printf("strncpy is \" %s \" \n ",cpy);
    printf("strcat is  %s \n ",cat);


if(c==0){
printf("strncmp is %s = %s \n ",s2,t3);
}
else if(c==1){
printf("strncmp is %s > %s \n ",s2,t3);
}
else if(c==2){
printf("strncmp is %s < %s \n ",s2,t3);
}

}


char* strncpy1(char* t,int n){
    // strncpy takes the n first char from second string 
char* dummy=t;
dummy = malloc(1000);
int i=0 ;

while(i < n){
dummy[i]=t[i];
i++;
}
dummy[n]='\0';



return dummy ;
}


char* strncat1(char* s,char* cpy){
    // strncat concatenate strncpy to first string s

char *cat=s ; 
     while (*s)
      s++;
    while (*s++ = *cpy++);

return cat ;
}
    // strncmp  return the size comparasin of the strings  ex s2>s1

int strncmp1(char* s,char* t){

int sizes=0;
int sizet=0;

while(*s!='\0') {
s++;
sizes++;

} 
while(*t!='\0') {
t++;
sizet++;

} 

if(sizes==sizet){
return 0;
}
else if(sizes > sizet){
return 1;
}
else if(sizes < sizet){
return 2;
}
else{
printf("error cmp"); 
return -1;
}
}


