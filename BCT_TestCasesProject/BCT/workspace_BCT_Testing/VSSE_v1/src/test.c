#include <stdio.h>
#include "map_lib.h"



int main(int argc,char **argv) {
   struct map_t *test;

   test=map_create();
   map_set(test,"One","Won");
   map_set(test,"Two","Too");
}

