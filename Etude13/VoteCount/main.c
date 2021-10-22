#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    char * name;
    /* FILE * fpointer = fopen("votes.txt", "r"); */
    /* Store the first line of fpointer in line */
    printf("Enter name: ");
    
    scanf("%s\n", name);
    printf("%s\n", name);
    /* fgets(line, 255, fpointer); */
    /* fprintf() */

    /* fclose(fpointer); */
    return EXIT_SUCCESS;
}
