#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
int doesExist(char ** names, int size, char * name) {
    int i;
    for (i = 0; i < size; i++) 
        if (strcmp(names[i], name) == 0) return 1;
    return 0;
}\*/

int main(void) {
    int bufflength = 20;
    char buff[bufflength];
    char val[bufflength-2];
    printf("Enter command and query like so: \t 3 fraser\n");
    fflush(stdout);
    if (fgets(buff, sizeof(buff), stdin) == NULL) {
        return EXIT_FAILURE;
    }
    buff[strlen(buff)-1] = '\0';
    memcpy(val, &buff[2], 18);
    printf("%c\n", buff[0]);
    printf("%s\n", val);
    printf("%lu\n", strlen(val));
    return EXIT_SUCCESS;
}