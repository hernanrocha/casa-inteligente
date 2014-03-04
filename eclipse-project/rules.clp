(defglobal ?*refvalue* = 20)

(deffunction temp-checking ()
	(if (<> ?*refvalue* ?temp) then 
        (assert (temp-change))
    	(printout t "[   JESS   ] Cambio de temperatura detectado" crlf)
    )
)

(defrule activate-thermo (temp-change)
=>
	(if (> ?*refvalue* ?temp) then 
    	(printout t "[   JESS   ] Temperatura BAJA detectada" crlf)
		(subirTemp (- ?*refvalue* ?temp))
    )
    (if (< ?*refvalue* ?temp) then 
    	(printout t "[   JESS   ] Temperatura ALTA detectada" crlf)
		(bajarTemp (- ?*refvalue* ?temp))
    )
	(retract-string "(temp-change)")
)