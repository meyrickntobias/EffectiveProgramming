#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
/* #include <stdio.h> */

struct person {
    char *firstName;
    char *lastName;
    char *phone;
    char *emailAddress;
};

static int i, j;
static int count;

/* 
    Sort by first name
*/
void sortfn(struct person** ss){
    struct person *s;
    for(i = 0; i < count; i++) {
        for (j = 0; j < count; j++) {
            if (strcmp(ss[i]->firstName, ss[j]->firstName) > 0) {
                s = ss[i];
                ss[i] = ss[j];
                ss[j] = s;
            }
        }
    }
}

/* Check every s in the array of structs and if first name == str,
    then return 1/true, otherwise return 0/false */
int findbyfn(struct person** ss, char *s){
    while(++i < count)
        if(strcmp(ss[i]->firstName, s) == 0)
            return 1;
    return 0;
}

void sortln(struct person** ss){
    struct person *s;
    for(i = 0; i < count; i++) {
        for (j = 0; j < count; j++) {
            if (strcmp(ss[i]->lastName, ss[j]->lastName) > 0) {
                s = ss[i];
                ss[i] = ss[j];
                ss[j] = s;
            }
        }
    }
}

int findbyln(struct person** ss, char* s){
    while(++i < count){
        if(strcmp(ss[i]->lastName, s) == 0)
            return 1;
    }
    return 0;
}

void sortem(struct person** ss){
    struct person *s;
    for(i = 0; i < count; i++) {
        for (j = 0; j < count; j++) {
            if (strcmp(ss[i]->emailAddress, ss[j]->emailAddress) > 0) {
                s = ss[i];
                ss[i] = ss[j];
                ss[j] = s;
            }
        }
    }
}

int findbyem(struct person** ss, char* s){
    while(++i < count){
        if(strcmp(ss[i]->emailAddress, s) == 0)
            return 1;
    }
    return 0;
}

/* Phone number is string now, so compare strings */
void sortph(struct person** ss){
    struct person *s;
    for(i = 0; i < count; i++) {
        /* For loop is missing variable initialisation */
        for (j = 0; j < count; j++) {
            if (strcmp(ss[i]->phone, ss[j]->phone) > 0) {
                s = ss[i];
                ss[i] = ss[j];
                ss[j] = s;
            }
        }
    }
}

/* Phone number is string now, so compare strings */
int findbyph(struct person** ss, char * s){
    /* Can you just compare character arrays with regular equality comparison? */
    while(++i < count){
        if(strcmp(ss[i]->phone, s) == 0)
            return 1;
    }
    return 0;
}

int main(int argc, char ** argv) {
    /*
        argc is the number of arguments, argv are the actual string arguments,
        to be accessed like argv[0]...argv[argc]
        print commands each times
    */
    int i;
    int count = 0;
    int fscanf_val;
    /* Might be more than 10 characters? */
    char buffer[10];
    int command = 10;
    FILE *f = fopen(argv[1], "r");
    /* Opens a file according to the command line arguement. */
    if (argc != 2) {
        printf("There is %d argument/s supplied\n", argc);
        fprintf(stderr, "There needs to be one argument supplied\n");
        return EXIT_FAILURE;
    }
    /* What happens if the file cannot be found? Handle possible errors? */
    if (f == NULL) {
        fprintf(stderr, "Could not find the file\n");
        return EXIT_FAILURE;
    }
    /* 
        A pointer to a pointer of type 'struct person' is being allocated
        enough memory for 100 struct persons. 
    
     */
    struct person **ss = malloc(100 * (sizeof *ss[0]));
    struct person *s = malloc(sizeof *s);
    for (i = 0; i < 100; i++) {
        s->firstName = malloc(80 * sizeof(s->firstName[0]));
        s->lastName = malloc(80 * sizeof(s->lastName[0]));
        s->phone = malloc(80 * sizeof(s->phone[0]));
        s->emailAddress = malloc(80 * sizeof(s->emailAddress[0]));
        /* 
            For strings, don't use ampersand - &
            Don't use integer for phone number
            fscanf does not check the length of the input
            maybe use fgets? Can only read a whole line
            strsep could be a good function to use
            fscanf should not be used
         */
	    fscanf_val = fscanf(f, "%s %s %s %s", s->firstName, s->lastName, s->phone, s->emailAddress);
        if (fscanf_val < 4) {
            break;
        }
	    ss[count] = s;
        count += 1;
    }

    /* There is no terminating condition, as command is not changed */
    /* atoi converts a string to an integer */
    while (command != 0) {
        char* val = malloc(100*sizeof(val[0]));
        /* Use gets or fgets? */
        fgets(buffer, 10, f);
        /* atoi converts a string into an integer */
        command = atoi(buffer);
        fgets(buffer, 10, f);
        /* strcpy(*dst, *src) */
        strcpy(val, buffer);
        switch(command){
            case 1:
                printf("looking for email %s\n", val);
                sortem(ss);
                printf("found it? %d\n", findbyem(ss, val));
                break;
            case 2:
                printf("looking for firstname %s\n", val);
                sortfn(ss);
                printf("found it? %d\n", findbyfn(ss, val));
                break;
            case 3:
                printf("looking for lastname %s\n", val);
                sortln(ss);
                printf("found it? %d\n", findbyln(ss, val));
                break;
            case 4:
                printf("looking for phone number %s\n", val);
                sortph(ss);
                printf("found it? %d\n", findbyph(ss, val));
            default:
                break;
        }
        /* free(val); */
    }
    /* 
        We need to free all of the memory
            Start with every individual person
                Free their email, their phone, their last name, and their first name
            Then free the person struct
            Now free the entire person struct array
    */
    printf("count is %d\n", count);
    /* Now we know that the ss array is not even holding any values */
    for (i = 0; i < count; i++) {
        printf("%s \t", ss[i]->emailAddress);
        printf("%s \t", ss[i]->phone);
        printf("%s \t", ss[i]->lastName);
        printf("%s \t", ss[i]->firstName);
        /*
        free(ss[i]->emailAddress);
        free(ss[i]->phone);
        free(ss[i]->lastName);
        free(ss[i]->firstName);
        free(ss[i]);
        */
    }
    free(ss);
    fclose(f);
    return EXIT_SUCCESS;
}
