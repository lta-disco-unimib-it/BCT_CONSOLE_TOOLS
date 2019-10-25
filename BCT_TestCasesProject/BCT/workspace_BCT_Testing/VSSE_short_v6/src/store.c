#include "store.h"
#include <stdlib.h>

t_user* create_user(){
	t_user* user = (t_user*) malloc( sizeof ( t_user ) );
	user->units = map_create();
	return user;
}

void add_unit( t_user* user_data, char* name, int qty ){
	int old_qty = get_unit( user_data, name );
	map_set( user_data->units, name, old_qty + qty );
}

int get_unit( t_user* user_data, char* name ){
	return map_get( user_data->units, name );
}

int isInitialized(t_map_t* units){
	return units->name == 0;
}

int isAvailable( t_map_t* units ){
	if ( isInitialized(units) ){
		return 0;
	}
	if ( units->value > 0 ){
		return 1;
	}
	if ( units->value > 0 ){
		return 1;
	}
	return 0;
}

long available_items( t_user* user_data )
{
	t_map_t* product = user_data->units;
	long total = 0;

	while (product != NULL) {
		total += 	
			isAvailable( product ) ?
			1 : 0;
		//lines above could create problems to assertions injector
		//a comment line like this could create problems too (to the following line)
		product = next ( product );
	};

	return total;
}

t_map_t* next( t_map_t* product ){
	return ( product == 0 ) ?
		0 : 
		product->nxt;
}

