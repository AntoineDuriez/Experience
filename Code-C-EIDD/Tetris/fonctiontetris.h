#include <stdlib.h>
#include <stdio.h>
#include "structtetris.h"

/*********
 *
 *********/

/*
 * fonctions 
 */

/*
 * Premiere semaine :
 */

/*
 *affichages tetrimos
 */
void shape_print(shape_t shape);
void shape_print90(shape_t shape);
void shape_print180(shape_t shape);
void shape_print270(shape_t shape);
void tetrim_print(tetrim_t* tetrim);
/*
 *afficahge rotation
 */
void rot_print(rot_t rotation);

/*
 *2eme semaine :
 */
/*
 *Exercice 1:
 */
void line_print(char lin[] , size_t len);
int line_first_free(char lin[], size_t len);
int line_space(char lin[], size_t len);
void line_clear(char lin[], size_t len);

/*
 * Exercice 2:
 */
void board_init(char board[][10],size_t nl);
void board_print(char board[][10],size_t nl);
int board_is_free_case(char board[][10], size_t nl, int lin, int col);
int board_is_full_line(char board[][10], size_t nl, int lin);

/*
 *Exerice 3:
 */
void state_print(state_t* s,int opt_full);

/*
 *Exerice 4:
 */
shape_t shape_random(void);
void play(state_t* jeu);


/*
 * Semaine 3 :
 */
/*
 *Exerice 1:
 */
int board_is_position(int m,int n, size_t lin, size_t col);
/*
 *Exercice 2:
 */
int rot_is_valid(char* rstr, size_t maxlen);
rot_t rot_of_string(char *rstr,size_t maxlen);
/*
 *Exercice 3:
 */
void take_shape(char matrice[][4],shape_t shape);
void take_rotation(char matrice[][4],rot_t nbrrotation);
void find_X(char matrice[][4],int *x,int *y);
int board_put_tetrim(char board[][10], size_t nl, tetrim_t tetrim, int m, int n);
/*
 * Exercice 4
 */
int board_fall_tetrim(char board[][10], size_t nl, tetrim_t tetrim, int m, int n);


/*
 *Semaine 4 :
 */
/*
 *Exercice 1:
 */
void board_squash_line(char board[][10], size_t nl, int lin);
/*
 *exercice 2:
 */
int board_squash_lines(char board[][10], size_t nl);
/*
 *Exercice 4:
 */
void states_log(state_t* state, tetrim_t tetrim, int ligne, int colonne);
void print_list_anti_chronological(list_tetrim_t* log, int nbrplace);
void print_list_chronological(list_tetrim_t* log,int nbrplace);
void free_list(list_tetrim_t* log);
/*Bonus*/
int is_this_possible(char board[][10], size_t nl, tetrim_t tetrim);
void changer_option(option_t *option);
int rot_is_option(char* chaine);
int trad_shape(char x);

