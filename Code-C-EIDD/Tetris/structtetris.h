#include <stdlib.h>
#include <stdio.h>
/*
 * Type enumere enum shape_e
 */
enum shape_e {I,O,T,L,J,Z,S};


/*
 * Type alias shape_t du enumere enum shape_e
 */
typedef enum shape_e shape_t;


/*
 * Type enumere enum rot_e et son alias rot_t
 */
enum rot_e {ROTATION_0,ROTATION_90,ROTATION_180,ROTATION_270};
typedef enum rot_e rot_t;


/*
 * Type alias tetrim_t du type produit pour les tetriminos
 */
struct tetrim_s{
  shape_t shape;
  rot_t rotation;
};
typedef struct tetrim_s tetrim_t;

/*
 *
 */
struct list_tetrim_s{
  tetrim_t tetrim;
  int ligne;
  int colonne;
  struct list_tetrim_s* next;
  struct list_tetrim_s* previous;
};
typedef struct list_tetrim_s list_tetrim_t;

/*
 *Type alias de la structure option
 */
struct option_s{
  int log; /*Définie si l'historique est affiché ou pas*/
  int senslog; /*Définie le sens d'affichage de l'historique*/
};
typedef struct option_s option_t;

/*
 *Type alias de la structure sates_s (exerice 3)
 */

struct state_s{
  char plateau_du_jeu[10][10];
  int nb_pieces_placees;
  int score_cumulee;
  int nb_ligne_ecrase;
  int nb_ligne_vide;
  tetrim_t piece_a_place;
  list_tetrim_t* log;
  list_tetrim_t* logchronologic;
  option_t option;
  char* liste_tetrim_a_mettre;
};
typedef struct state_s state_t;
