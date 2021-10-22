#include <stdio.h>
#include <stdlib.h>

int main(void) {
    /* 
        Imagine we want an array of integers with the size defined by the program's user
    */
    int * list = malloc(3 * sizeof(int));
    if (list == NULL) {
        fprintf(stderr, "Failed\n");
        return EXIT_FAILURE;
    }

    free(list);
    return EXIT_SUCCESS;
}

