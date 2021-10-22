#include <stdio.h>
#include <stdlib.h>
#include <math.h>
/*
    Goal: Create a program that prints out the first 100 primes 
*/

int is_prime(int num) {
    int i;
    if (num <= 1) return 0;
    for (i = 2; i < num; i++) if ((double) (num % i) == 0) return 0;
    return 1;
}

int main() {
    int n = 100;
    int counter = 2;
    while (counter < n) {
        if (is_prime(counter)){
            printf("%d\n", counter);
        }
        counter += 1;
    }
    return EXIT_SUCCESS;
}

