
#include "map_lib.h"


#ifndef GAME_H_
#define GAME_H_

typedef struct user {
	t_map_t* units;
} t_user;

t_user* create_user();

int isAvailable( t_map_t* unit );

long available_items( t_user* store );

void add_unavailable_unit( t_user* user_data, char* name );

void add_unit( t_user* user_data, char* name, int qty );

int get_unit( t_user* user_data, char* name );


#endif


