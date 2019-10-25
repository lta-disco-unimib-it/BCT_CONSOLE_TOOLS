

all:
	rm -f uninit.o uninit.instr.o
	goto-cc uninit.c -o uninit.o
	goto-instrument --nondet-static uninit.o uninit.instr.o
	cbmc --all-claims uninit.instr.o

det:
	rm -f uninit.o uninit.instr.o
	goto-cc uninit.c -o uninit.o
	cbmc --all-claims uninit.o

