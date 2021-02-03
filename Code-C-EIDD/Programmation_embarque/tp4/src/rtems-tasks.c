/*  Test_task
 *
 *	Autor : Maxime Vienne & Antoine Duriez
 *
 */

#include <rtems.h>
#include <assert.h>
#include "windows-producer.h"
#include "buffer_circulaire.h"

/* functions */

rtems_task Init(rtems_task_argument argument);

rtems_task Acquisition_task(rtems_task_argument argument);

rtems_task Processing_task(rtems_task_argument argument);

rtems_task TelemetryManager_task(rtems_task_argument argument);

void timer_1_entry();

void f_affiche();

double calculFluxPondere(float window[], float mask[]);

/* global variables */

/*
 *  Keep the names and IDs in global variables so another task can use them.
 */ 

extern rtems_id   Task_id[ 4 ];         /* array of task ids */
extern rtems_name Task_name[ 4 ];       /* array of task names */


/* configuration information */

#include <bsp.h> /* for device driver prototypes */

#define CONFIGURE_INIT
#define CONFIGURE_APPLICATION_NEEDS_CONSOLE_DRIVER
#define CONFIGURE_APPLICATION_NEEDS_CLOCK_DRIVER

#define CONFIGURE_MAXIMUM_TASKS             10

#define CONFIGURE_RTEMS_INIT_TASKS_TABLE

#define CONFIGURE_EXTRA_TASK_STACKS         (3 * RTEMS_MINIMUM_STACK_SIZE)


#define TASK1_PRIORITY 1
#define TASK2_PRIORITY 2
#define TASK3_PRIORITY 3
#define TASK_STACK_SIZE 10240
#define MESSAGE_QUEUE_COUNT 3
#define MESSAGE_QUEUE_SIZE 4
#define CONFIGURE_MICROSECONDS_PER_TICK 10000
//#define CONFIGURE_APPLICATION_NEEDS_TIMER_DRIVER
#define CONFIGURE_MAXIMUM_TIMERS 5
#define CONFIGURE_MAXIMUM_MESSAGE_QUEUES 10
#define CONFIGURE_MAXIMUM_SEMAPHORES 3
#define BUFFER_SIZE 1

#include <rtems/confdefs.h>

/* If --drvmgr was enabled during the configuration of the RTEMS kernel */
#ifdef RTEMS_DRVMGR_STARTUP
 #ifdef LEON3
  /* Add Timer and UART Driver for this example */
  #ifdef CONFIGURE_APPLICATION_NEEDS_CLOCK_DRIVER
   #define CONFIGURE_DRIVER_AMBAPP_GAISLER_GPTIMER
  #endif
  #ifdef CONFIGURE_APPLICATION_NEEDS_CONSOLE_DRIVER
   #define CONFIGURE_DRIVER_AMBAPP_GAISLER_APBUART
  #endif

  #include <grlib/ambapp_bus.h>
  /* OPTIONAL FOR GRLIB SYSTEMS WITH GPTIMER AS SYSTEM CLOCK TIMER */
  struct drvmgr_key grlib_drv_res_gptimer0[] =
  {
  	/* If all timers should not be used (typically on an AMP system, or when timers
  	 * are used customly in a project) one can limit to a range of timers.
  	 * timerStart: start of range (0..6)
  	 * timerCnt: Number of timers
  	 */
	#if 0
  	{"timerStart", DRVMGR_KT_INT, {(unsigned int)SET_START}},
  	{"timerCnt", DRVMGR_KT_INT, {(unsigned int)SET_NUMBER_FO_TIMERS}},
	  /* Select Prescaler (Base frequency of all timers on a timer core) */
  	{"prescaler", DRVMGR_KT_INT, {(unsigned int)SET_PRESCALER_HERE}},
  	/* Select which timer should be used as the system clock (default is 0) */
  	{"clockTimer", DRVMGR_KT_INT, {(unsigned int)TIMER_INDEX_USED_AS_CLOCK}},
  	#endif
  	DRVMGR_KEY_EMPTY
  };

  /* Use GPTIMER core 4 (not present in most systems) as a high
   * resoulution timer */
  struct drvmgr_key grlib_drv_res_gptimer4[] =
  {
  	{"prescaler", DRVMGR_KT_INT, {(unsigned int)4}},
  	DRVMGR_KEY_EMPTY
  };

  struct drvmgr_bus_res grlib_drv_resources =
  {
    .next = NULL,
    .resource = {
  #if 0
  	{DRIVER_AMBAPP_GAISLER_GPTIMER_ID, 0, &grlib_drv_res_gptimer0[0]},
  	{DRIVER_AMBAPP_GAISLER_GPTIMER_ID, 1, NULL}, /* Do not use timers on this GPTIMER core */
  	{DRIVER_AMBAPP_GAISLER_GPTIMER_ID, 2, NULL}, /* Do not use timers on this GPTIMER core */
  	{DRIVER_AMBAPP_GAISLER_GPTIMER_ID, 3, NULL}, /* Do not use timers on this GPTIMER core */
  	{DRIVER_AMBAPP_GAISLER_GPTIMER_ID, 4, &grlib_drv_res_gptimer4[0]},
  #endif
  	DRVMGR_RES_EMPTY
    }
  };
 #endif

 #include <drvmgr/drvmgr_confdefs.h>
#endif

/*
 *  Handy macros and static inline functions
 */

/*
 *  Macro to hide the ugliness of printing the time.
 */

#define print_time(_s1, _tb, _s2) \
  do { \
    iprintf( "%s%02"PRIu32":%02"PRIu32":%02"PRIu32"   %02"PRIu32"/%02"PRIu32"/%04"PRIu32"%s", \
       _s1, (_tb)->hour, (_tb)->minute, (_tb)->second, \
       (_tb)->month, (_tb)->day, (_tb)->year, _s2 ); \
    fflush(stdout); \
  } while ( 0 )

/*
 *  Macro to print an task name that is composed of ASCII characters.
 *
 */

#define put_name( _name, _crlf ) \
  do { \
    uint32_t c0, c1, c2, c3; \
    \
    c0 = ((_name) >> 24) & 0xff; \
    c1 = ((_name) >> 16) & 0xff; \
    c2 = ((_name) >> 8) & 0xff; \
    c3 = (_name) & 0xff; \
    putchar( (char)c0 ); \
    if ( c1 ) putchar( (char)c1 ); \
    if ( c2 ) putchar( (char)c2 ); \
    if ( c3 ) putchar( (char)c3 ); \
    if ( (_crlf) ) \
      putchar( '\n' ); \
  } while (0)

/*
 *  This allows us to view the "Test_task" instantiations as a set
 *  of numbered tasks by eliminating the number of application
 *  tasks created.
 *
 *  In reality, this is too complex for the purposes of this
 *  example.  It would have been easier to pass a task argument. :)
 *  But it shows how rtems_id's can sometimes be used.
 */

#define task_number( tid ) \
  ( rtems_object_id_get_index( tid ) - \
     rtems_configuration_get_rtems_api_configuration()->number_of_initialization_tasks )


#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>



/*
 *  Keep the names and IDs in global variables so another task can use them.
 */

#define NB_IMG 100
float img[NB_IMG][36];
Windows_producer wp;
rtems_id   Task_id[ 4 ];         /* array of task ids */
rtems_name Task_name[ 4 ];       /* array of task names */
rtems_id message_queue_id_1;
rtems_id message_queue_id_2;
rtems_id timer_id_1;
rtems_id semaphore_id_1;



#define FLUX_LENGTH 10

typedef struct flux{
	uint32_t id_window ;
	uint32_t id_first_acquisition ; ///< valeur du compteur d'acquisition de measures[0]
	float measures[FLUX_LENGTH]
} flux ;

typedef struct buffer_circulaire_partagee {
	buffer_circulaire buffer_circulaire;
} buffer_circulaire_partagee;

buffer_circulaire_partagee buffercp;
flux buffer_flux[100]; // adresse mémoire pour initialiser le buffer circulaire

uint32_t break_simu(uint32_t break_id, const char * break_name){
	uint32_t static test_counter=0;
	return test_counter++;
}

double calculFluxPondere(float window[], float mask[]) {
  double res = 0;
  uint8_t xi = 0;
  uint8_t yi = 0;
  // --- parcoure window
  for (xi = 0; xi < 6; xi++) {
  	for (yi = 0; yi < 6; yi++) {
  		// somme les pixels, pondérés du facteur de mask
  		res += window[xi * 6 + yi] * mask[xi * 6 + yi];
  	}
  }
  return res;
}

/*
 *
 * Fonction encapsulé du buffer_circulaire
 * Init/Push/Pop
 */

void init_buffer_circulaire_partagee (buffer_circulaire_partagee* buffer_circulaire, uint8_t* buffer, uint16_t taille_donnee, uint32_t nombre_donnees){
	init_buffer_circulaire(&buffer_circulaire->buffer_circulaire, buffer, taille_donnee, nombre_donnees);
}

int push_partagee(buffer_circulaire_partagee* fifo, uint8_t* source, uint16_t taille) {
	int var_return;
	rtems_semaphore_obtain(semaphore_id_1, RTEMS_WAIT, RTEMS_NO_TIMEOUT);// verrouille la ressource partagée
	var_return = push(&fifo->buffer_circulaire, source, taille);
	rtems_semaphore_release(semaphore_id_1);// libère la ressources partagée
	return var_return;
}

int pop_partagee(buffer_circulaire_partagee* fifo, uint8_t* destination, uint16_t taille_max) {
	int var_return;
	rtems_semaphore_obtain(semaphore_id_1, RTEMS_WAIT, RTEMS_NO_TIMEOUT);
	var_return = pop(&fifo->buffer_circulaire, destination, taille_max);
	rtems_semaphore_release(semaphore_id_1);
	return var_return;
}


void timer_1_entry(rtems_id timer_id, void* timer_input) {
	static uint32_t data = 0;
	rtems_status_code status;
	data++;
	status = rtems_message_queue_send(message_queue_id_1, &data, sizeof(uint32_t));//poste un message dans la queue
	status = rtems_timer_fire_after(timer_id_1, 50, timer_1_entry, 0); // réarme le timer à 0.5 secondes
}

/**
* Simule l'émission d'une séquence de mesures de photométrie.
* @param f structure enregistrant la séquence
* @param time moment de l'émission, exprimé en ticks depuis le démarrage du programme .
*/
void send_flux(uint32_t time, flux f){
	static int cpt = 0 ;
	cpt++ ;
}

rtems_task Init(rtems_task_argument argument)
{
  rtems_status_code status;


  Task_name[ 1 ] = rtems_build_name( 'T', 'A', '1', ' ' );
  Task_name[ 2 ] = rtems_build_name( 'T', 'A', '2', ' ' );
  Task_name[ 3 ] = rtems_build_name( 'T', 'A', '3', ' ' );

  // tache 1 est la tache d'acquisition
  status = rtems_task_create(Task_name[1],TASK1_PRIORITY, TASK_STACK_SIZE,
  RTEMS_PREEMPT | RTEMS_NO_TIMESLICE | RTEMS_ASR | RTEMS_INTERRUPT_LEVEL(0),
  RTEMS_LOCAL | RTEMS_FLOATING_POINT, &Task_id[1]);
  assert(status == RTEMS_SUCCESSFUL);

  // tache 2 est la tache de traitement
  status = rtems_task_create(Task_name[2],TASK2_PRIORITY, TASK_STACK_SIZE,
  RTEMS_PREEMPT | RTEMS_NO_TIMESLICE | RTEMS_ASR | RTEMS_INTERRUPT_LEVEL(0),
  RTEMS_LOCAL | RTEMS_FLOATING_POINT, &Task_id[2]);
  assert(status == RTEMS_SUCCESSFUL);

  // tache 3 pour la Télémetrie
  status = rtems_task_create(Task_name[ 3 ], TASK3_PRIORITY, TASK_STACK_SIZE,
  RTEMS_PREEMPT | RTEMS_NO_TIMESLICE | RTEMS_INTERRUPT_LEVEL(0),
  RTEMS_LOCAL | RTEMS_FLOATING_POINT, &Task_id[ 3 ]);
  assert(status == RTEMS_SUCCESSFUL);

  // ajout de queue message, pas d'assert dans le cours ici
  status = rtems_message_queue_create(rtems_build_name('M','S','Q', '1'),
  MESSAGE_QUEUE_COUNT, MESSAGE_QUEUE_SIZE,
  RTEMS_LOCAL | RTEMS_PRIORITY, &message_queue_id_1);

  // 2 ème queue message pour processing task
  status = rtems_message_queue_create(rtems_build_name('M','S','Q', '2'),
    MESSAGE_QUEUE_COUNT, MESSAGE_QUEUE_SIZE,
    RTEMS_LOCAL | RTEMS_PRIORITY, &message_queue_id_2);

  // le semaphore se créer
  status = rtems_semaphore_create(rtems_build_name('S','E','M', '1'),1, // le 1 permet de récupérer une donnée
    RTEMS_PRIORITY | RTEMS_BINARY_SEMAPHORE | RTEMS_INHERIT_PRIORITY, 0,
    &semaphore_id_1);
  	assert(status == RTEMS_SUCCESSFUL);

  // ajout du timer, il n'y a pas d'assert dans le cours ici
  status = rtems_timer_create(rtems_build_name('T','I','M', '1'), &timer_id_1);
  assert(status == RTEMS_SUCCESSFUL);

  status = rtems_timer_fire_after(timer_id_1, 50, timer_1_entry, 0); // arme le timer sur 0.5 secondes
  assert(status == RTEMS_SUCCESSFUL);

  // initialisation du buffer_circulaire_partageee
  init_buffer_circulaire_partagee(&buffercp, (uint8_t*)buffer_flux, sizeof(flux), 100); // déclarer un buffer et lui donné en paramètre en tant qu'espace mémoire

  //break_simu(1,"timer_1_entry");// affiche dans gdb

  // démarrer les tâches
  status = rtems_task_start( Task_id[ 1 ], Acquisition_task, 1 );
  assert(status == RTEMS_SUCCESSFUL);

  status = rtems_task_start( Task_id[ 2 ], Processing_task, 1 );
  assert(status == RTEMS_SUCCESSFUL);

  status = rtems_task_start( Task_id[ 3 ], TelemetryManager_task, 3 );
    assert(status == RTEMS_SUCCESSFUL);

  status = rtems_task_delete( RTEMS_SELF );
  assert(status == RTEMS_SUCCESSFUL);
}
#include <stdio.h>

//permet d'afficher dans gdb le clock_get_ticks
rtems_interval f_affichage(rtems_interval a){
	return a;
}


rtems_task Acquisition_task(rtems_task_argument unused)
{
	uint32_t buffer[BUFFER_SIZE];
	size_t size;
  rtems_id          tid;
  rtems_time_of_day time;
  uint32_t  task_index;
  rtems_status_code status;
	  /*
    status = rtems_clock_get_tod( &time );
    assert(status == RTEMS_SUCCESSFUL);*/

    init(&wp, img, NB_IMG);
    while(1){
    //appel à produce_images
    produce_images(&wp);
    /*
    break_simu(0,"reset");//reset
    f_affichage(rtems_clock_get_ticks_since_boot());// temps écoulé, on l'appel dans gdb script Gets the current ticks counter value.
    break_simu(1,"f_affichage");// affiche dans gdb
	*/
    //receive queue et send
    status = rtems_message_queue_receive(message_queue_id_1,buffer, &size, RTEMS_WAIT, RTEMS_NO_TIMEOUT);// reçoit le timer
    assert(status == RTEMS_SUCCESSFUL);
    //break_simu(1,"receive");
    status = rtems_message_queue_send(message_queue_id_2,buffer, size);// envoie la valeur du compteur d'acquisition
    assert(status == RTEMS_SUCCESSFUL);
    //rtems_task_wake_after(50); // acquiert une image toutes les 500 ms

  }
}

// créer une troisième task, pour le processing task qui receive dans une seconde, une task par tache

rtems_task Processing_task(rtems_task_argument unused)
{
	uint32_t buffer[BUFFER_SIZE];
	size_t size;
	int cpt=0;
	rtems_status_code status;
	int var_return;
	  // flux pour les imagettes
	  flux f[100];

	  while(cpt< 100){

	    // la tache2 reçoit la valeur du compteur d'acquisition
	    // indique que le buffer d'imagette est à jour
	    status = rtems_message_queue_receive(message_queue_id_2,buffer, &size, RTEMS_WAIT, RTEMS_NO_TIMEOUT);
	    assert(status==RTEMS_SUCCESSFUL);

	    f[cpt].id_window = &wp;
	    f[cpt].id_first_acquisition = buffer[0];


	    // utiliser tsim avec -nofpu dans le load
	    // problème dans le get_mask
	    for(uint32_t i = 0 ; i< FLUX_LENGTH ; i++){
	    	/*printf("image buffer : %d\n", img[buffer[0]]);
	    	printf("get mask : %d\n", get_mask(&wp, i));*/
	    	f[cpt].measures[i] = calculFluxPondere(img[buffer[0]], get_mask(&wp, i));
	    	printf("measures[%d] : %lf\n", i, f[cpt].measures[i]);
	    }
	    //alimente le buffer circulaire partage en structure de flux
	    printf("avant le push cpt : %d \n ",cpt);
	    var_return = push_partagee(&buffercp, (uint8_t*)(&f[cpt]), sizeof(flux)); // la taille doit être supérieur
	    printf("var_return du push partagee: %d\n", var_return);
	    printf("cpt : %d \n ",cpt);
	    cpt++;
	  }
}

/**
 * Tache TelemtryManager_task pour recevoir les données du buffer partagée
 * puis les lires si il n'est pas vide
 */

rtems_task TelemetryManager_task(rtems_task_argument unused)
{
	const uint32_t interval_de_regulation = 10;
	const uint32_t nombre_maximal_de_flux = 30;
	uint32_t debut = rtems_clock_get_ticks_since_boot(); //On conserve l'heure de début du slot (moment d'émission)
	uint32_t static cpt_flux = 0; // nombre de flux déjà émis depuis le début
	flux pop_flux;		//Prend le flux du buffer circulaire partagee
	int vide;// si le pop_partagee ne rend rien on affiche rien

	while(1){
		if(cpt_flux > nombre_maximal_de_flux){	//Si 30 flux ont été émis depuis de début du slot.
			rtems_task_wake_after(interval_de_regulation - (rtems_clock_get_ticks_since_boot() - debut));	//la tâche se met en pause jusqu'à la fin du slot
		}else if(rtems_clock_get_ticks_since_boot() > debut + interval_de_regulation){	//Quand un flux est à émettre et que la fin de slot est passé,
			cpt_flux = 0; // remise à 0 du compteur
			debut = rtems_clock_get_ticks_since_boot(); // on redonne l'heure
		}else{
			vide = pop_partagee(&buffercp, (uint8_t*)(&pop_flux), sizeof(flux));
			if(vide > 0){// on affiche seulement si il y a quelque chose dans le buffer circulaire
				break_simu(0,"reset");//reset
				send_flux(rtems_clock_get_ticks_since_boot(), pop_flux);
				break_simu(1,"send_flux");// affiche dans gdb
				cpt_flux++;
			 }
		}
	}
}


