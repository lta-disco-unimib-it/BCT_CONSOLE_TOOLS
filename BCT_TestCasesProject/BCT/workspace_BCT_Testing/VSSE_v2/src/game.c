#include "game.h"
#include <stdlib.h>

t_user* create_user(){
	t_user* user = (t_user*) malloc( sizeof ( t_user ) );
	user->units = map_create();
	return user;
}

void add_unavailable_unit( t_user* user_data, char* name ){
	map_set( user_data->units, name, 0 );
}

void add_unit( t_user* user_data, char* name, int qty ){
	int old_qty = get_unit( user_data, name );
	map_set( user_data->units, name, old_qty + qty );
}

int get_unit( t_user* user_data, char* name ){
	return map_get( user_data->units, name );
}

int isAvailable( t_map_t* units ){
	if ( units->name == 0 ){
		return 0;
	}
	if ( units->value > 0 ){
		return 1;
	}
	if ( units->value < 0 ){
		return -1;
	}
	return 0;
}

long available_items( t_map_t** res, t_user* user_data ){
	t_map_t* units = user_data->units;
	long total = 0;
	t_map_t* list = NULL;

	do {
		int value = isAvailable( units );

		if ( value != 0){
			t_map_t *list = (t_map_t *) malloc ( sizeof ( t_map_t ) );
			*list = *units;
			list->nxt = NULL;

			total += value;
		}
		units = units->nxt;
	} while ( units != 0 );

	*res = list;

	return total;
}
