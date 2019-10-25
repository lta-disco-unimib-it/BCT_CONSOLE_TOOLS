(declare-datatypes () ((Struct__data NILStruct__data( cons (__nwaiters Int)(__wakeup_seq Int)(__futex Int)(__lock Int)(__woken_seq Int)(__broadcast_seq Int)(__total_seq Int)(__count Int)(__elision Int)(__nusers Int)(__spins Int)(__kind Int)(__owner Int) ) )))
(declare-datatypes () ((Structmutex NILStructmutex( cons (__align Int)(__data Struct__data) ) )))
(declare-datatypes () ((Structcond NILStructcond( cons (__align Int)(__data Struct__data) ) )))
(declare-datatypes () ((Struct*q NILStruct*q( cons (mutex Structmutex)(cond Structcond) ) )))
(declare-datatypes () ((StructreturnValue NILStructreturnValue( cons (eax Int) ) )))
(declare-datatypes () ((Struct*strm NILStruct*strm( cons (flags Int)(mode Int) ) )))
(assert (forall (
(*q Struct*q) (*strm Struct*strm) (returnValue StructreturnValue) (__data Struct__data) (mutex Structmutex) (cond Structcond) )
(implies
(and (and (and (and (= ( flags *strm) ( eax returnValue)) (= ( flags *strm) 0) ) (= ( mode *strm) 243536302) ) (not (= *q NILStruct*q)) ) (not (= *strm NILStruct*strm)) ) (and (and (and (and (= ( flags *strm) ( eax returnValue)) (= ( flags *strm) 0) ) (= ( mode *strm) 243536302) ) (not (= *q NILStruct*q)) ) (not (= *strm NILStruct*strm)) )) ))
(assert (forall (
(*q Struct*q) (*strm Struct*strm) (returnValue StructreturnValue) (__data Struct__data) (mutex Structmutex) (cond Structcond) )
(implies
(and (and (and (and (= ( flags *strm) ( eax returnValue)) (= ( flags *strm) 0) ) (= ( mode *strm) 243536302) ) (not (= *q NILStruct*q)) ) (not (= *strm NILStruct*strm)) ) (and (and (and (and (= ( flags *strm) ( eax returnValue)) (= ( flags *strm) 0) ) (= ( mode *strm) 243536302) ) (not (= *q NILStruct*q)) ) (not (= *strm NILStruct*strm)) )) ))
(check-sat-using (then qe smt))
(exit)