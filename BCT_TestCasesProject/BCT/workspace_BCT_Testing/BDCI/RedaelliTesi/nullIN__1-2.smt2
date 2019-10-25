(declare-datatypes () ((StructreturnValue NILStructreturnValue( cons (eax Int) ) )))
(assert (forall (
(x Real) (y Real) )
(implies
(and (and (not (= x 0))(>= x 0.400000006))(and (not (= y 0))(>= y 0.600000024))) (and (and (not (= x 0))(>= x 0.400000006))(and (not (= y 0))(>= y 0.600000024)))) ))
(assert (forall (
(x Real) (y Real) )
(implies
(and (and (not (= x 0))(>= x 0.400000006))(and (not (= y 0))(>= y 0.600000024))) (and (and (not (= x 0))(>= x 0.400000006))(and (not (= y 0))(>= y 0.600000024)))) ))
(check-sat)
(exit)