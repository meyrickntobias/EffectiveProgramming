#include <stdio.h>
#include <stdlib.h>
/*
    This will take a text file as input, read in candidates and their votes, then decide who wins or if there is a tie
    who ties
*/

struct candidate {
    char *name;
    int votes;
};

int get_votes(struct candidate *c) {
    return &c->votes;
}

char * get_name(struct candidate *c) {
    return &c->name;
}

int main() {
    struct candidate can
}