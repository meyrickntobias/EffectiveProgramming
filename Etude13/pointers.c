#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/* Let's say we wish to swap the values of integer a and integer b */
void swap(int *a, int *b) {
    int temp = *a;
    *b = *a;
    *a = temp;
}

int main() {
    /* Create an array */
    int *arr;
    int array_size;
    int i;

    printf("Enter how many numbers you would like to print out \n");
    scanf("%d", &array_size);

    arr = malloc(array_size * sizeof arr[0]);
    if (NULL == arr) {
        fprintf(stderr, "Memory allocation failed!\n");
        return EXIT_FAILURE;
    }

    for (i = 0; i < array_size; i++) {
        arr[i] = pow(2, i);
    }
    printf("What's in the array?\n");
    for (i = 0; i < array_size; i++) {
        printf("%d\n", arr[i]);
    }
    free(arr);
    
    return EXIT_SUCCESS;
}
