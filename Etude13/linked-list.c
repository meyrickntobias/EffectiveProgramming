#include <stdio.h>
#include <stdlib.h>

typedef struct node {
    int number;
    struct node *next;
} node;

int main(void) {
    node *list = NULL;
    node *tmp = NULL;
    /* Allocate memory to the first node */
    node *n = malloc(sizeof(node));
    if (n == NULL) {
        return EXIT_FAILURE;
    }
    /* Set the number in the list to 1 and the next node as NULL */
    n->number = 1;
    n->next = NULL;
    /* Set the list equal to n, remember list is a pointer to a node */
    list = n;
    /* Allocate another n node */
    n = malloc(sizeof(node));
    if (n == NULL) {
        free(list);
        return EXIT_FAILURE;
    }
    /* Set the node's properties */
    n->number = 2;
    n->next = NULL;
    /* Update the list's next property */
    list->next = n;

    n = malloc(sizeof(node));
    if (n == NULL) {
        free(list->next);
        free(list);
        return EXIT_FAILURE;
    }
    n->number = 3;
    n->next = NULL;
    list->next->next = n;
    /* Loop through list and print out values */
    for (tmp = list; tmp != NULL; tmp = tmp->next) {
        printf("%d\n", tmp->number);
    }
    /* Free all of the memory */
    while (list != NULL) {
        tmp = list->next;
        free(list);
        list = tmp;
    }
    return EXIT_SUCCESS;
}