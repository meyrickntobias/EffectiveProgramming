#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

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
int findbyfn(struct person** ss, char *s) {
    i = 0;
    while(i < count) {
        if(strcmp(ss[i]->firstName, s) == 0) 
            return 1;
        i++;
    }   
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
    i = 0;
    while(i < count){
        if(strcmp(ss[i]->lastName, s) == 0)
            return 1;
        i++;
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
    i = 0;
    while(i < count) {
        if(strcmp(ss[i]->emailAddress, s) == 0)
            return 1;
        i++;
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
    i = 0;
    while(i < count){
        if(strcmp(ss[i]->phone, s) == 0)
            return 1;
        i++;
    }
    return 0;
}

char* yesNo(int n) {
    char* yes = "Yes";
    char* no = "No";
    char* undefined = "Undefined";
    if (n == 1) return yes;
    if (n == 0) return no;
    return undefined;
}

int main(int argc, char ** argv) {
    /*
        argc is the number of arguments, argv are the actual string arguments,
        to be accessed like argv[0]...argv[argc]
        print commands each times
    */
    /* Might be more than 10 characters? */
    char buffer[82];
    int command = 5;
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
    struct person **people = malloc(100 * (sizeof (people[0])));
    struct person *single = NULL;

    /* Open the file for reading */
    char *pch;
    char *line_buf = NULL;
    size_t line_buf_size = 200;
    int line_count = 0;
    ssize_t line_size;

    /* Get the first line of the file. */
    line_size = getline(&line_buf, &line_buf_size, f);

    while (line_size >= 1) {
        // people[line_count-1] = malloc(sizeof (struct person));
        single = malloc(sizeof (struct person));
        line_count += 1;
        single->firstName = malloc(80 * sizeof(single->firstName[0]));
        single->lastName = malloc(80 * sizeof(single->lastName[0]));
        single->phone = malloc(80 * sizeof(single->phone[0]));
        single->emailAddress = malloc(80 * sizeof(single->emailAddress[0]));
        pch = malloc((strlen(line_buf) + 1) * sizeof (pch[0]));
        strcpy(pch, line_buf);
        pch = strtok (pch, " ");
        strcpy(single->firstName, pch);
        if (pch != NULL) {
            pch = strtok (NULL, " ");
            strcpy(single->lastName, pch);
        }
        if (pch != NULL) {
            pch = strtok (NULL, " ");
            strcpy(single->phone, pch); 
        }
        if (pch != NULL) {
            pch = strtok (NULL, " ");
            strcpy(single->emailAddress, pch); 
        }
	    people[line_count-1] = single;
        line_size = getline(&line_buf, &line_buf_size, f);
    }
    count = line_count;

    /* Prompt user to enter command */
    printf("Enter a command, for help press 5: \n");
    while (command != 0) {
        char* val = malloc(80*sizeof(val[0]));
        if (fgets(buffer, sizeof(buffer), stdin) == NULL) {
            return EXIT_FAILURE;
        } 
        memcpy(val, &buffer[2], 82);
        command = atoi(&buffer[0]);
        val[strlen(val)-1] = '\0';
        
        switch(command){
            case 1:
                printf("looking for email %s\n", val);
                sortem(people);
                printf("found it? %s\n", yesNo(findbyem(people, val)));
                break;
            case 2:
                printf("looking for firstname %s\n", val);
                sortfn(people);
                printf("found it? %s\n", yesNo(findbyfn(people, val)));
                break;
            case 3:
                printf("looking for lastname %s\n", val);
                sortln(people);
                printf("found it? %s\n", yesNo(findbyln(people, val)));
                break;
            case 4:
                printf("looking for phone number %s\n", val);
                sortph(people);
                printf("found it? %s\n", yesNo(findbyph(people, val)));
                break;
            case 5:
                printf("Commands: \n");
                printf("1 \t Search by email, e.g: 1 joe@gmail.com \n");
                printf("2 \t Search by first name, e.g: 2 joe \n");
                printf("3 \t Search by last name, e.g: 3 smith \n");
                printf("4 \t Search by phone number, e.g: 4 021738292 \n");
                printf(" * Note: use no whitespace in phone number \n");
                printf(" * Note: email must have an email format \n");
                printf(" * Note: all values are case sensitive, joe is treated differently to Joe \n");
            default:
                break;
        }
        free(val);
    }
    
    /* Free the memory that was allocated earlier */
    for (i = 0; i < line_count; i++) {
        free(people[i]->emailAddress);
        free(people[i]->phone);
        free(people[i]->lastName);
        free(people[i]->firstName);
        free(people[i]);
    }
    free(people);
    fclose(f);
    
    return EXIT_SUCCESS;
}
