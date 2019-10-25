#include <stdio.h>
#include <stdlib.h>
#include <math.h>
/*sottrazione riceve in ingresso due numeri a virgola mobile, confronta il valore assoluto delle due variabili e ritorna una variabile float ottenuta effettuando una sottrazione tra i valori in ingresso */
float sottrazione(float x, float y){
     float risultato;
     printf("sottr %f %f \n",x,y);
     if(fabs(x)>fabs(y)) { risultato=fabs(x)-fabs(y); }
     if(x==y){ risultato=fabs(x)-fabs(y)+1;}
     if(fabs(x)<fabs(y)){risultato=fabs(y)-fabs(x);}
	risultato = -risultato;
     return risultato;
}
/* Funzione che calcola il logaritmo del numero in ingresso, ritorna il valore dell’operazione */
float logaritmo(float x){
      printf("log %f \n",x);
      return logf(x);
}
/* riceve in ingresso due numeri reali e chiama la funzione logaritmo sul valore restituito dalla funzione sottrazione. Ritorna il valore ottenuto. */
float logDaSottrazione(float x, float y){
	return logaritmo(-sottrazione(x, y));
}

