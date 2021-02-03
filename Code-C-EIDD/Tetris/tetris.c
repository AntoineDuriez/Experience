/*
 * Le jeu de Tetris
 * 
 * @author: GANTET Saturnin & DURIEZ Antoine
 */
/*
 * On doit toujours utiliser cette directive de compilation pour utiliser les
 * fonctions 'printf' et 'scanf'.
 */
#include <stdio.h>
#include <stdlib.h>
/*
 * On doit toujours utiliser cette directive de compilation pour utiliser les
 * fonctions qui dépendent du temps
 */
# include <time.h>

#include "fonctiontetris.h"

/********************************************************************
 * Functions pour shape_t
 ********************************************************************/

/*
 * Affichage d'un tetriminos
 * Rotation normal
 */
void shape_print(shape_t shape){
   switch(shape)
   {
      case I:
      printf("IIII\n");
      break;
    case O:
      printf("OO\nOO\n");
      break;
    case T:
      printf("TTT\n T\n");
      break;
    case L:
      printf("LLL\nL\n");
      break;
    case J:
      printf("JJJ\n  J\n");
      break;
    case Z:
      printf("ZZ\n ZZ\n");
      break;
    case S:
      printf(" SS\nSS\n");
      break;
    }
  return;
}

/*
 *Affichahe d'un tetrimos
 *Rotation 90 degres
 */
void shape_print90(shape_t shape){
  switch(shape){
    case I:
      printf("I\nI\nI\nI\n");
      return;
    case O:
      printf("OO\nOO\n");
      return;
    case T:
      printf("T\nTT\nT\n");
      return;
    case L:
      printf("L\nL\nLL\n");
      return;
    case J:
      printf("JJ\nJ\nJ\n");
      return;
    case Z:
      printf(" Z\nZZ\nZ\n");
      return;
    case S:
      printf("S\nSS\n S\n");
      return;
    default :
      printf("Invalid tetrim shape\n");
      return;
  }
}

/*
 *Affichahe d'un tetrimos
 *Rotation 180 degres
 */
void shape_print180(shape_t shape){
  switch(shape){
    case I:
      printf("IIII\n");
      return;
    case O:
      printf("OO\nOO\n");
      return;
    case T:
      printf(" T\nTTT\n");
      return;
    case L:
      printf("  L\nLLL\n");
      return;
    case J:
      printf("J\nJJJ\n");
      return;
    case Z:
      printf("ZZ\n ZZ\n");
      return;
    case S:
      printf(" SS\nSS\n");
      return;
    default:
      printf("Invalid tetrim shape\n");
      return;
  }
}

/*
 *Affichahe d'un tetrimos
 *Rotation 270 degres
 */
void shape_print270(shape_t shape){
  switch(shape){
    case I:
      printf("I\nI\nI\nI\n");
      return;
    case O:
      printf("OO\nOO\n");
      return;
    case T:
      printf(" T\nTT\n T\n");
      return;
    case L:
      printf("LL\n L\n L\n");
      return;
    case J:
      printf(" J\n J\nJJ\n");
      return;
    case Z:
      printf(" Z\nZZ\nZ\n");
      return;
    case S:
      printf("S\nSS\n S\n");
      return;
    default:
      printf("Invalid tetrim shape");
      return;
  }
}
    

/********************************************************************
 * Functions pour tetrim_t
 ********************************************************************/
/*
 *Affichage d'un tetriminos en fonction de son forme et de sa rotation
 *On appelle differentes fonctions en fonction de la rotation définie plus haut
 *On utilise des appels de fonctions pour segmenter le code et le rendre plus lisible plutot que de mettre 100 ligne de switch case à la suite dans une meme fonction. 
 */
void tetrim_print(tetrim_t* tetrim){
  switch(tetrim->rotation){
    
  case ROTATION_0:
    shape_print(tetrim->shape);
    return;
    
  case ROTATION_90:
    shape_print90(tetrim->shape);
    return;
    
  case ROTATION_180:
    shape_print180(tetrim->shape);
    return;
    
  case ROTATION_270:
    shape_print270(tetrim->shape);
    return;
    
  default:
    printf("Invalid tetrim rotation");
    return;
  }
}




/*Affichage de la rotation*/
void rot_print(rot_t rotation){
  switch(rotation){
  case ROTATION_0 :
    printf("Rotation 0°\n");
    break;
  case ROTATION_90 :
    printf("Rotation 90°\n");
    break;
  case ROTATION_180 :
    printf("Rotation 180°\n");
    break;
  case ROTATION_270 :
    printf("Rotation 270°\n");
    break;
  default:
    printf("Invalid Rotation\n");
  }
}


/*****************************************************
 *****************************************************
 *             Fonctions 2eme semaine                *
 *****************************************************
 ****************************************************/

/*
 *Exercice 1 
 *
 */
void line_print(char lin[] , size_t len)
{
  int i;
  for(i=0;i<len;i++){
    switch (lin[i]){
    case 'I':
      printf("|\033[30;41m__\033[0m");
      break;
    case 'O':
      printf("|\033[30;42m__\033[0m");
      break;
    case 'T':
      printf("|\033[30;43m__\033[0m");
      break;
    case 'L':
      printf("|\033[30;44m__\033[0m");
      break;
    case 'J':
      printf("|\033[30;45m__\033[0m");
      break;
    case 'Z':
      printf("|\033[30;46m__\033[0m");
      break;
    case 'S':
      printf("|\033[30;47m__\033[0m");
      break;
    default :
      printf("|  ");
      break;
    }
   
  }
  printf("|\n");
}

int line_first_free(char lin[], size_t len){
  int i;
  for(i=0;i<len;i++){
    if(lin[i] == ' '){
      return i;
    }
  }
  return -1;
}


int line_space(char lin[], size_t len){
  int i,acc;
  acc = 0;
  for(i=0;i<len;i++){
    if(lin[i] == ' '){
      acc+=1;
    }
  }
  return acc;
}

void line_clear(char lin[], size_t len){
  int i;
  for(i=0;i<len;i++){
    lin[i]=' ';
  }
}



/* 
 *Exercice 2
 */

void board_init(char board[][10],size_t nl){
  int i;
  for(i=0;i<nl;i++){
    line_clear(board[i],nl);
  }
}


void board_print(char board[][10],size_t nl){
  int i;
  for(i=0;i<nl;i++){
  printf(" %d ",9-i);
  line_print(board[9-i],nl);
  }
  printf("    0  1  2  3  4  5  6  7  8  9\n");
}


int board_is_free_case(char board[][10], size_t nl, int lin, int col){
  if(board[lin][col]==' '){
    return 0;
  }
  else{
  return 1;
  }
}



int board_is_full_line(char board[][10], size_t nl, int lin){
  if(line_space(board[lin],nl)==0){
    return 1;
  }
  return 0;
}


/*
 * Exercice 3
 */

void state_print(state_t* s,int opt_full){
  printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

  /*Affichage de l'historique*/
  if(opt_full==1 && s->log!=NULL){
    if(s->option.senslog==1 && s->log!=NULL){
      print_list_anti_chronological(s->log,-s->nb_pieces_placees);
    }
    if(s->option.senslog==0 && s->logchronologic!=NULL){
      print_list_chronological(s->logchronologic,1);
    }
  }
  
  /*Affichage du plateau*/
  board_print(s->plateau_du_jeu,10);

  /*Affichage des autres infos*/
  printf("Nombre Tetriminos: %d\n",s->nb_pieces_placees);
  printf("Nombre lignes ecrasees: %d\n",s->nb_ligne_ecrase);
  printf("Nombre lignes vides: %d\n",s->nb_ligne_vide);
  printf("Score : %d\n",s->score_cumulee);
  printf("A placer:  \n");
  tetrim_print(&s->piece_a_place);
  printf("Donnez la rotation et la ligne et colonne: r 0 9 ou option pour ouvrir le menu des options\n");
}



/*
 * Exercice 4
 */

shape_t shape_random(void){
  return rand()%7;
}


void play(state_t* jeu){
  int i,j,ligne,colonne,continuer,defaite;
  char rotation[40];
  continuer = 1;
  defaite = 1;
  i=0;
  
  /*Tant que le condition de defaite n'est pas vérifiée*/
  while(defaite){
    line_clear(rotation,40);
    continuer=1;

    /*calcule du nombre de lignes vides*/
    jeu->nb_ligne_vide = 0;
    for(j=0;j<10;j++){
      if(line_space(jeu->plateau_du_jeu[j],10) == 10){
	jeu->nb_ligne_vide += 1;
      } 
    }
    
    /*Obtention de la pliece aléatoire à placer*/
    if(jeu->liste_tetrim_a_mettre==NULL){
      jeu->piece_a_place.shape = shape_random();
    }else{
      if(jeu->liste_tetrim_a_mettre[i] =='\0'){
        printf("Tous les tetrims demandes en arguments ont ete place\n");
        return;
      }
      jeu->piece_a_place.shape = trad_shape(jeu->liste_tetrim_a_mettre[i]);
      i++;
    }
    
    /*On regarde si il est possible de placer la piece*/
    defaite = is_this_possible(jeu->plateau_du_jeu, 10,jeu->piece_a_place);

    
    
    jeu->piece_a_place.rotation = 0;
    state_print(jeu,jeu->option.log);
    while(continuer && defaite == 1){
      /* On reste dans cette boucle tant que l'entrée de l'utilisateur n'est pas valide*/
      /* scanf("%s %d %d",rotation,&ligne,&colonne);*/
      scanf("%s",rotation);
      if((rotation[0] == 'x' || rotation[0] == 'X') && rotation[1] == '\0'){
         defaite =0;
        return ;
       }
       
      if(rot_is_option(rotation) == 1){
	changer_option(&jeu->option);
	state_print(jeu,jeu->option.log);
	continue;
      }
           scanf("%d %d",&ligne,&colonne);
      
      /*On vérifie que les coordonnées saisies par l'utilisateur sont possibles*/
      if(board_is_position(ligne,colonne,10,10)==1){
	continuer = 0;
      }
      else{
	printf("Cette position n'existe pas, veuillez entrer une ligne et une colonne valide\n");
	continue;
      }

      
      /*On regarde si le texte saisie par l'utilisateur est valide*/
      if(rot_is_valid(rotation,40)==1)
	{
	  /*Si oui on compte le nombre de rotations demandées*/
	  jeu->piece_a_place.rotation = rot_of_string(rotation,40);
	}
      else{
	continuer = 1;
	printf("Veuillez entrer une rotation valide (0 pour rien ou un r pour chaque rotation de 90°)\n");
      }

      
      /*Si continuer == 0 alors on essaye de placer la piece avec la rotation demandé;
       *si on ne peut pas placer la piece alors on redemande une entrée à l'utilisateur
       *sinon la piece est placée et on passe à la piece suivante
       */
      if(continuer == 0 && board_fall_tetrim(jeu->plateau_du_jeu,10,jeu->piece_a_place,ligne,colonne) == 0){
	continuer = 1;
      }
      jeu->nb_ligne_ecrase += board_squash_lines(jeu->plateau_du_jeu,10);
    }
    jeu->nb_pieces_placees+=1;
    states_log(jeu,jeu->piece_a_place,ligne, colonne);
    jeu->score_cumulee = jeu->nb_ligne_ecrase*10 + jeu->nb_pieces_placees*4;
  }
  /*On a perdu*/
  printf("Vous avez perdu, votre score est de : %d\n",jeu->score_cumulee);
  /*On supprime l'historique des piece placées*/
  if(jeu->logchronologic!=NULL){
    free_list(jeu->logchronologic);
  }
}



/*
 ***********************************************************************************
 ***********************************************************************************
 ********                                                                   ********
 ********                       Fonction 3me semaine                        ********
 ********                                                                   ********
 ***********************************************************************************
 ***********************************************************************************
 */
/*
 * Exercie 1
 */

/*
 *Cette fonction renvoie 1 si la position (m,n) est possible dans une matrice[lin][col]
 * Ou m,n,lin et col sont fournis en arguments
 * Elle renvoie 0 sinon
 */

int board_is_position(int m,int n, size_t lin, size_t col){
  if(m>=0 && m<lin && n>=0 && n<col){
    return 1;
  }
  return 0;
}

/*
 * Exercice 2
 */

/*
 *Cette fonction renvoie 1 si la chaine de caractere donnée en argument contient uniquement des 'r'
 *               renvoie 0 sinon
 * Argument :
 * rstr la chaine de caractere à tester
 * maxlen la taille de cette chaine de caractere
 */
int rot_is_valid(char* rstr, size_t maxlen){
  int i;
  
  /*On vérifie le cas rotation de 0 degres avec une entrée utilisateur "0"*/
  if(rstr[0] == '0' && rstr[1] == '\0'){
    return 1;
  }
  
  /*On parcout la chaine de caractere juqu'au bout ou juqu'a rencontré un '\0'*/
  for(i=0;i<maxlen && rstr[i] != '\0';i++){
    if(rstr[i]!='r' && rstr[i]!='R'){
      /*Si on trouve un caractere different de 'r' ou 'R' on renvoie 0*/
      return 0;
    }
  }
  return 1;
}



/*
 *Cette fonction peremt de compter le nombre de r dans une chaine de caractere
 *Argument : rstr la chaine de caractere à tester
 *           maxlen la taille de la chaine de caractere
 * Elle renvoie le nombre de r rencontré modulo 4 pour correspondre à la plage de valeur qu'attend un rot_t
 */
rot_t rot_of_string(char *rstr,size_t maxlen){
  int acc,i;
  /*acc permet de compter le nombre de r rencontrés*/
  acc=0;

  /*On vérifie le cas rotation de 0 degres avec une entrée utilisateur "0"*/
  if(rstr[0] == '0' && rstr[1] == '\0'){
    return 0;
  }
  
  /*On parcout la chaine*/
  for(i=0;rstr[i]!='\0' && i<maxlen;i++){
    if(rstr[i]=='r' || rstr[i] == 'R'){
      /*A chaque fois que l'on rencontre un r ou un R on ajoute 1 à acc*/
      acc+=1;
    }
  }
  return acc%4;
}

/*
 *Exercice 3
 */

/*
 *Cette fonction remplie la matrice donnée en argument par une representation à rotation 0° de la forme du tetrim donnée en argument
 */
void take_shape(char matrice[][4],shape_t shape){
  switch(shape){
  case I:
    matrice[3][0]='I';
    matrice[3][1]='I';
    matrice[3][2]='I';
    matrice[3][3]='I';
    break;
    
  case O:
    matrice[3][0]='O';
    matrice[3][1]='O';
    matrice[2][0]='O';
    matrice[2][1]='O';
    break;

  case T:
    matrice[3][1]='T';
    matrice[2][0]='T';
    matrice[2][1]='T';
    matrice[2][2]='T';
    break;

  case L:
    matrice[3][0]='L';
    matrice[2][0]='L';
    matrice[2][1]='L';
    matrice[2][2]='L';
    break;

  case J:
    matrice[3][2]='J';
    matrice[2][0]='J';
    matrice[2][1]='J';
    matrice[2][2]='J';
    break;

  case Z:
    matrice[3][1]='Z';
    matrice[3][2]='Z';
    matrice[2][0]='Z';
    matrice[2][1]='Z';
    break;

  case S:
    matrice[3][0]='S';
    matrice[3][1]='S';
    matrice[2][1]='S';
    matrice[2][2]='S';
    break;
  }
}

/*
 * Cette fonction va effectuer des rotation de 90° de la matrice donnée en argument dans le sens trigonométrique
 * Elle va faire autant de rotation que le nombre nbrrotation en argument lui indique de faire 
 */
void take_rotation(char matrice[][4],rot_t nbrrotation){
  char backup[4][4];
  /*On utilisera cette matrice pour faire une copie de ce que l'on veut tourner*/
  int i,j;
  /*Variable pour parcourir les matrices*/

  /*On fait autant de rotation qu'indiqué en argument*/
  for(;nbrrotation>0;nbrrotation--){
    /*On copie la matrice*/
    for(i=0;i<4;i++){
      for(j=0;j<4;j++){
	backup[i][j] = matrice[i][j];
      }
    }
    /*On change la valeur de la matrice pour que ça corresponde à une rotation de 90°*/
    for(i=0;i<4;i++){
      for(j=0;j<4;j++){
	matrice[i][j] = backup[j][3-i];
      }
    }
  }
}


/*
 * Cette fonction permet de trouver le caractere different de ' ' le plus en bas à gauche de la matrice donnée 
 * Argument :
 * matrice :  Une matrice de caractere
 * *x et *y des pointeurs sur des entiers
 * Renvoie rien mais change la valeur dans l'adresse de x et y par les coordonnées du point le plus en bas à gauche 
 *
 * On represente la matrice comme ça :
 * |0|1|2|3|
 *0| | | | |
 *1| | | | |
 *2| | | | |
 *3| | | | |
 *
 */
void find_X(char matrice[][4],int *x,int *y){
  int i,j;
  /*On parcourt la matrice de bas en haut et de gauche à droite*/
  for(i=0;i<4;i++){
    for(j=0;j<4;j++){
      /* Quand on trouve un caractere != de ' ' on change les valeur de x et y par les coordonnées dans la metrice de ce point puis on arrete la fonction*/
      if(matrice[3-i][j] != ' '){
	*x = 3-i;
	*y = j;
	return;
      }
    }
  }
}

/*
 *Cette fonction va placer la piece saisie par l'utilisateur dans le plateau de jeu
 * Argument :
 * board : le plateau dans lequel on place la piece
 * nl : la taille du tableau
 * tetrim : la piece que l'on veut placer
 *  m et n les coordonnées où l'on veut placer le bout le plus en bas à gauche de la piece
 * 
 * Renvoie 0 si on a pa pu placer la piece
 * Renvoie 1 si la piece a été placé avec succes
 */
int board_put_tetrim(char board[][10], size_t nl, tetrim_t tetrim, int m, int n){
  char matrice[4][4];
  int i,j,x,y;
  /*
   * i et j permettent de parcourir les matrice via des boucles for (on prend i pour parcourir les lignes et j pour les colonnes)
   * x et y sont les coordonnées du point X, le point le plus en bas à gauche de la piece à placer
   */
  /*Initialisation de matrice*/
  for(i=0;i<4;i++){
    line_clear(matrice[i],4);
  }
  /*On stock la forme de base dans matrice*/
  take_shape(matrice,tetrim.shape);
  
  /*On tourne la piece pour que ça corresponde à ce que veut l'utilisateur*/
  take_rotation(matrice,tetrim.rotation);

  /*On trouve le bout de piece le plus en bas à gauche*/
  find_X(matrice,&x,&y);

  /*Dans ce for on vérifie que l'on peut bien placer la piece dans le tableau*/
  for(i=0;i<4;i++){
    for(j=0;j<4;j++){
      /*Dans cette condition on regarde :
       *Si on a une lettre à placer et que la position du plateau correspondante n'est pas valide (hors limite ou deja une lettre) alors on renvoie 0*/
      /* On compare un tableau 10x10 representé comme ça : (board)
	 * |0|1|2|3|4|5|6|7|8|9|
	 *9| | | | | | | | | | |
	 *8| | | | | | | | | | |
	 *7| | | | | | | | | | |
	 *6| | | | | | | | | | |
	 *5| | | | | | | | | | |
	 *4| | | | | | | | | | |
	 *3| | | | | | | | | | |
	 *2| | | | | | | | | | |
	 *1| | | | | | | | | | |
	 *0| | | | | | | | | | |
	 * 
	 * avec un tableau representé comme ça : (matrice)
	 * |0|1|2|3|
	 *0| | | | |
	 *1| | | | |
	 *2| | | | |
	 *3| | | | |
	 *
	 *-> L'axe des ordonnées est inversé
	 *On ne doit donc pas parcourir les deux tableaux dans le meme sens
	 *Remarque aussi valable pour la boucle de remplissage
	 */
      if(matrice[i][j] != ' ' && (board_is_position(m+x-i,n-y+j,10,10)==0 || board[m+x-i][n-y+j] != ' ')){
	return 0;
      }
    }
  }
  
  /*Dans ce for on place la piece dans le tableau*/
  for(i=0;i<4;i++){
    for(j=0;j<4;j++){
      if(matrice[i][j] != ' '){
        board[m+x-i][n-y+j] = matrice[i][j];
      }
    }	
  }
  return 1;
}



/*
 * Exercice 4 :
 */

/*
 * Cette fonction va placer la piece saisie par l'utilisateur dans le plateau de jeu en la faisant tomber
 * Argument :
 * board : le plateau dans lequel on place la piece
 * nl : la taille du tableau
 * tetrim : la piece que l'on veut placer
 * m et n les coordonnées où l'on veut placer le bout le plus en bas à gauche de la piece
 *
 * Renvoie 0 si la piece n'a pas pu être placé
 * Renvoie 1 si la piece a été placé avec succes
 */
int board_fall_tetrim(char board[][10], size_t nl, tetrim_t tetrim, int m, int n){
  char matrice[4][4];
  /*matrice va stocker la piece à placé*/
  int i,j,x,y,h,acc=0,continuer=1;
  /*
   * i et j permettent de parcourir les matrice via des boucles for (on prend i pour parcourir les lignes et j pour les colonnes)
   * x et y sont les coordonnées du point X, le point le plus en bas à gauche de la piece à placer
   * acc va compter le nombre de fois que l'on peut se permettre de faire descendre la piece
   */
  clock_t temps;
  /*va permettre de faire tomber la piece en fonction du temps*/

  /*Initialisation de matrice*/
  for(i=0;i<4;i++){
    line_clear(matrice[i],4);
  }
  
  /*On stock la forme de base dans matrice*/
  take_shape(matrice,tetrim.shape);

  /*On tourne la piece pour que ça corresponde à ce que veut l'utilisateur*/
  take_rotation(matrice,tetrim.rotation);

  /*On trouve le bout de piece le plus en bas à gauche*/
  find_X(matrice,&x,&y);

  /*Dans cette boucle on regarde si on peut mettre la piece et combien de fois on peut la faire tomber d'une ligne*/
  while(continuer){
    for(i=0;i<4;i++){
      for(j=0;j<4;j++){
	
	/* Dans cette condition on regarde si pour chaque element de la matrice non vide, c'est à dire quand il y'a un bout de piece à placer, il y a de la place dans le plateau du jeu ou non
	 *ie si on peut placer la piece ou pas
	 */
	/* On compare un tableau 10x10 representé comme ça : (board)
	 * |0|1|2|3|4|5|6|7|8|9|
	 *9| | | | | | | | | | |
	 *8| | | | | | | | | | |
	 *7| | | | | | | | | | |
	 *6| | | | | | | | | | |
	 *5| | | | | | | | | | |
	 *4| | | | | | | | | | |
	 *3| | | | | | | | | | |
	 *2| | | | | | | | | | |
	 *1| | | | | | | | | | |
	 *0| | | | | | | | | | |
	 * 
	 * avec un tableau representé comme ça : (matrice)
	 * |0|1|2|3|
	 *0| | | | |
	 *1| | | | |
	 *2| | | | |
	 *3| | | | |
	 *
	 *-> L'axe des ordonnées est inversé
	 *On ne doit donc pas parcourir les deux tableaux dans le meme sens
	 *Remarque aussi valable pour la boucle de remplissage
	 */
	if(matrice[i][j] != ' ' && (board_is_position(m+x-i-acc,n-y+j,10,10)==0 || board[m+x-i-acc][n-y+j] != ' ')){
	  
	  /*Si acc = 0 alors on a tester la position de base, on ne peut donc pas mettre la piece*/
	  if(acc==0){
	    printf("On ne peut pas placer la piece ici\n");
	    return 0;
	  }
	  
	  /*Si acc != 0 alors on a tester une position plus bas que la position de base, on ne peut pas mettre la piece plus bas donc on arrete la boucle*/
	  else {
	    continuer = 0;
	  }
	}
      }
    }
    acc++;
  }
  acc--;
  /* On ajoute 1 à acc meme quand on rencontre une position impossible donc on lui enleve 1 pour qu'il corresponde à ce qu'on attend de lui, le nombre de fois que l'on peut descendre la piece*/

  /*On fait tomber la piece petit à petit*/
  for(h=0;h<acc;h++){
    /*On efface la position precedente de la piece, seulement si elle a deja été placé une fois (h!=0)*/
    if(h!=0){
      for(i=0;i<4;i++){
	for(j=0;j<4;j++){
	  if(matrice[i][j]!=' '){
	    board[m+x-i-h+1][n-y+j] = ' ';
	  }
	}
      }
    }
    /*On place la piece une position plus bas*/
    for(i=0;i<4;i++){
      for(j=0;j<4;j++){
	if(matrice[i][j] != ' '){
	  board[m+x-i-h][n-y+j] = matrice[i][j];
	}
      }	
    }
    /*On remplie la console de retour à la ligne pour n'avoir qu'un plateau de jeu affiché à la fois*/
    for(i=0;i<100;i++){
      printf("\n");
    }
    /* On affiche le plateau de jeu pour voir la piece tomber
     * Si h=acc-1 le plateau sera afficher avec les autres éléments dans la fonction play 
     */
    if(h<acc){
      board_print(board,10);
      /*Pour garder le plateau du jeu à une position stable*/
      printf("\n\n\n\n\n\n\n\n");
      /*On fait une boucle qui va "tourner dans le vide" pendant 0.3 secondes pour avoir le temps de voir la position de la piece à chaque étape de sa chute*/
      temps = clock() + 0.30*CLOCKS_PER_SEC;
      while(clock()<temps){};
    }
  }
  return 1;
}



/*
 ***********************************************************************************
 ***********************************************************************************
 ********                                                                   ********
 ********                       Fonction 4me semaine                        ********
 ********                                                                   ********
 ***********************************************************************************
 ***********************************************************************************
 */

/*Cette fonction prend un argument un tableau de pointeur, une taille et un numero de ligne
 *Elle va vider une ligne et faire descendre d'une case toute les lignes au dessus de cette ligne
 *La ligne la plus haute est donc vidée
 */
void board_squash_line(char board[][10], size_t nl, int lin){
  int i,j;
  char backup[10];
  clock_t temps;
  for(i=0;i<nl;i++){
    backup[i] =  board[lin][i];
  }
  for(j=0;j<10;j++){
    for(i=0;i<nl;i++){
      board[lin][i] = board[lin][i]==' '?backup[i]:' ';
    }
    printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    board_print(board, nl);
    printf("\n\n\n\n\n\n\n\n");
    temps = clock() + 0.15*CLOCKS_PER_SEC;
    while(clock()<temps){}
  }
  /*Dans cette boucle on fait descendre les lignes d'une case*/
  for(i=lin;i<nl-1;i++){
    for(j=0;j<nl;j++){
      board[i][j] = board[i+1][j];
    }
  }
  /*Ici on vide la ligne du haut si la ligne donnée en argument est valide*/
  if(lin<nl){
    line_clear(board[9], nl);
  }
}

/*Cette fonction prend en argument une matrice et la longueur de cette matrice
 *Elle va appeller la fonction board_squash_line pour effacer une ligne et faire descendre celle d'au dessus à chaque fois qu'elle rencontre une ligne qui ne contient aucun ' '
 *Elle renvoie le nombre de lignes ecrasées
 */
int board_squash_lines(char board[][10], size_t nl){
  int i,acc;
  acc=0;
  for(i=0;i<nl;i++){
    if(board_is_full_line(board, nl, i)==1){
      acc+=1;
      board_squash_line(board,nl,i);
      i--;
    }
  }
  return acc;
}

/*
 * Exercice 4:
 */

/*Cette fonction prend en argument un pointeur vers l'état du jeu, un tetrim aussi que le numero d'une ligne et d'une colonne
 *A chaque fois qu'elle est appelée elle va creer une structure que l'on va stocker dans le tas qui contient les informations données en argument ainsi qu'un pointeur vers la derniere structure de ce type créée et un pointeur qui va accueullir l'adresse de la prochaine structure de ce type créée et qui restera initialisé à NULL jusqu'ici
 */
void states_log(state_t* state, tetrim_t tetrim, int ligne, int colonne){
  /*On alloue de l'espace dans le tas pour creer une nouvelle structure list_tetrim*/
  list_tetrim_t* nouveau = (list_tetrim_t*)malloc(sizeof(list_tetrim_t));
  /*On test si l'allocation a marché ou non*/
  if(nouveau == NULL){
    printf("Problème d'historique des tetrims, l'option d'affichage est désactivée automatiquement\n");
    state->option.log = 0;
    return;
  }
  
  /*On initialise la structure avec les arguments donnés*/
  nouveau->previous = state->log;
  nouveau->next = NULL;
  nouveau->tetrim = tetrim;
  nouveau->ligne = ligne;
  nouveau->colonne=colonne;
  
  /*On met à jour state->log pour que ce pointeur contiennent toujours l'adresse de la liste la plus récente*/
  state->log = nouveau;

  /*On test si c'est le premier tetrim que l'on place, dans ce cas on donne stock son adresse dans la structure déstinée à l'état du jeu*/
  if(state->logchronologic==NULL){
    state->logchronologic=nouveau;
  }

  /*On test si l'adresse de la structure précédente existe bien pour ne pas creer d'erreur et on lui donne un pointeur vers la structure que l'on est entrain de creer dans cette fonction*/
  if(nouveau->previous != NULL){
    nouveau->previous->next = nouveau;
  }
}


/*Cette fonction va afficher les tetrims joué par ordre anti chronologique
 *Elle prend en argument le pointeur vers la structure d'un tetrim joué ainsi que le nombre total de tetrims joué pour pouvoir faire un décompte
 */
void print_list_anti_chronological(list_tetrim_t* log, int nbrplace){
  printf("Tetrim numero %d :\n",abs(nbrplace));
  /*On affiche les informations stockés dans la strucure donnée en argument*/
  tetrim_print(&log->tetrim);
  printf("ligne %d,\t colonne %d\n\n",log->ligne,log->colonne);

  /*Si il existe un tetrim précédent alors on rappel la fonction pour afficher cette fois ce dernier*/
  if(log->previous!=NULL){
    print_list_anti_chronological(log->previous,nbrplace+1);    
  }
}

/*Cette fonction va afficher les tetrims joué par ordre chronologique
 *Elle prend en argument le pointeur vers la structure d'un tetrim joué ainsi que le nombre de tetrims deja affiché pour pouvoir faire un décompte
 */
void print_list_chronological(list_tetrim_t* log, int nbrplace){
  printf("Tetrim numero %d :\n",abs(nbrplace));
  tetrim_print(&log->tetrim);
  printf("ligne %d,\t colonne %d\n\n",log->ligne,log->colonne);
  if(log->next!=NULL){
    print_list_chronological(log->next,nbrplace+1);    
  }
}

/*Cette fonction permet de liberer de la mémoire en supprimant l'historique liste de tetrim joué
 *elle prend en argument l'adresse de la structure contenant le premier tetrim joué
*/
void free_list(list_tetrim_t* log){
  /*On stock l'adresse de la prochaine structure que l'on doit supprimer*/
  list_tetrim_t *suivant = log->next;
  /*On "supprime" la structure donnée en argument de la mémoire*/
  free(log);
  /*Si il existe un tetrim suivant alors on rappelle la fonction pour le supprimer aussi*/
  if(suivant != NULL){
    free_list(suivant);
  }
}


/*
 ***********************************************************************************
 ***********************************************************************************
 ********                                                                   ********
 ********                          Fonctions BONUS                           ********
 ********                                                                   ********
 ***********************************************************************************
 ***********************************************************************************
 */

/*cette fonction va tester toutes les combinaisons possibles pour placer le tetrim donné en argument dans le plateau donné en argument jusqu'à en trouver une valide
 *Elle renvoie 1 si elle trouve un placement possible
 *Elle renvoie 0 si elle ne trouve pas de placement possible
 */
int is_this_possible(char board[][10], size_t nl, tetrim_t tetrim){
  int i,j;
  /*On crée une copie de la matrice donnée en argument pour faire des tests sans affecter le vrai plateau de jeu*/
  char back_up[10][10];
  for(i=0;i<nl;i++){
    for(j=0;j<nl;j++){
      back_up[i][j]=board[i][j];
    }
  }
  /*On parcours toutes les cases du tableau*/
  for(i=0;i<nl;i++){
    for(j=0;j<nl;j++){
      /*A chaque fois que l'on trouve une case vide on essaye d'y mettre le tetrim en testant toute ses rotations possibles*/
      if(back_up[i][j] == ' '){
	for(tetrim.rotation=0;tetrim.rotation<4;tetrim.rotation++){
	  if(board_put_tetrim(back_up, nl, tetrim, i, j) == 1){
	    /*Si board_put_tetrim renvoie 1 alors la piece a pu être placé avec succes, on a donc au moins un placement valide*/
	    return 1;
	  }
	}
      }
    }
  }
  /*Si on a parcouru tout le tableau sans pouvoir placé la piece alors on renvoie 0 pour indiquer que l'utilisateur a perdu*/
  return 0;
}

/*Cette fonction sert à changer les options
 *Elle affiche un petit menu avec lequel on peut interagir en tapant des nombres
 */
void changer_option(option_t *option){
  int saisie,i;
  /*boucle pour l'ésthétique du programme*/
  while(1){
    for(i=0;i<100;i++){
      printf("\n");
    }
    /*On affiche les options que l'on peut changer*/
    printf("Option :\n1 - %sable tetrim history \n2 - Change tetrim display order (current : %s)\n0 - Resume\n",option->log==0?"en":"dis" , option->senslog==0?"chronological":"anti chronological");
    scanf("%d",&saisie);
    /*On change les options en fonction de la saisie utilisateur*/
    switch(saisie){
    case 0:
      return;
      
    case 1:
      option->log++;
      option->log%=2;
      break;
      
    case 2:
      option->senslog++;
      option->senslog%=2;
      break;

    default:
      printf("Invalid command\n");
    }
  }
}


int trad_shape(char x){
  switch (x){
  case 'i':
  case 'I':
    return 0;
  case 'O':
  case 'o':
    return 1;
  case 'T':
  case 't':
    return 2;
  case 'L':
  case 'l':
    return 3;
  case 'j':
  case 'J':
    return 4;
  case 'Z':
  case 'z':
    return 5;
  case 'S':
  case 's':
    return 6;
  default :
    return -1;
  }
}
    
/*Cette fonction prend en argument une chaine de caractere et regarde si elle contient la suite de caractere : 'o','p','t','i','o','n','\0'
 *Elle renvoie 1 si c'est le cas et 0 sinon
 */
int rot_is_option(char* chaine){
  if(chaine[0]=='o' && chaine[1] == 'p' && chaine[2] == 't' && chaine[3] == 'i' && chaine[4] == 'o' && chaine[5] == 'n' &&  chaine[6] == '\0'){
    return 1;
  }
  return 0;
}
	


/********************************************************************
 * Fonctions de test
 ********************************************************************/
/*
 * Voici le programme. On doit toujours définir une fonction 'main' pour que
 * le programme soit exécuté. C'est la fonction par laquelle commence
 * l'exécution.
 */


int main(int argc, char** argv){
/* On déclare toutes les variables pour les tests de toutes les fonctions au début du main:
 */
  int i;
  state_t state;
  char keep_playing;
  FILE* fichier = fopen("score.txt","r+");
  int highscore;
  fscanf(fichier,"%d",&highscore);
  srand(time(NULL));
  keep_playing = 'y';

  /*
   *On vérifie que l'argument donné correspond à une suite de tetriminos
   */
  if(argc > 1){
    for(i=0;argv[1][i]!='\0';i++){
      if(argv[1][i]=='I' || argv[1][i]=='i' || argv[1][i]=='O' || argv[1][i]=='o' || argv[1][i]=='T' || argv[1][i]=='t' || argv[1][i]=='L' || argv[1][i]=='l' || argv[1][i]=='J' || argv[1][i]=='j' || argv[1][i]=='S' || argv[1][i]=='s' || argv[1][i]=='Z' || argv[1][i]=='z'){}
      else {
        printf("La sequence donnee en argument n'est pas valide\n");
        return 0;
      }
    }
    state.liste_tetrim_a_mettre=argv[1];
  }else{
    state.liste_tetrim_a_mettre=NULL;
  }
  
   /*
   *Test fonction Semaine 3 
   */
  while(keep_playing!='n' && keep_playing!='N'){
    switch(keep_playing){
    case 'Y':
    case 'y':
      board_init(state.plateau_du_jeu,10);
      state.nb_pieces_placees = 0;
      state.nb_ligne_ecrase = 0;
      state.nb_ligne_vide = 0;
      state.score_cumulee = 0;
      state.piece_a_place.rotation = 0;
      state.piece_a_place.shape=0;
      state.log=NULL;
      state.logchronologic=NULL;
      state.option.log=1;
      state.option.senslog=0;
      play(&state);
      if(highscore<state.score_cumulee){
        printf("Bien joué vous avez battu le reccord ! UwU\n");
        rewind(fichier);
        fprintf(fichier,"%d",state.score_cumulee);
      }
      break;

    case 'O':
    case 'o':
      changer_option(&state.option);
      break;

    default:
      printf("Invalid command\n");
    }
    printf("Voulez vous rejouer ? y/n (or o to toggle option menu)\n");
    scanf(" %c",&keep_playing);
  } 
  return 0;
}
