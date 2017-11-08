# simulacion
Ejercicios de simulacion

## Codigo util

```
mensaje += (Util.DEBUG) ? "\n[PRUEBAS] Poker OK" : "";
Integer.parseInt(Integer.toString(number).substring(0, 1));
```

## Generacion

D. Coddington, P & J. Newell, A. (2004). JAPARA — A Java Parallel Random Number Generator Library for High-Performance Computing. Parallel and Distributed Processing Symposium, International. 6. 156a. 10.1109/IPDPS.2004.1303143.

[JAPARA Articulo encontrado](https://www.researchgate.net/publication/232635848_JAPARA_-_A_Java_Parallel_Random_Number_Generator_Library_for_High-Performance_Computing)

[JAPARA IEEE Articulo](http://ieeexplore.ieee.org/document/1303143/?reload=true)

[JAPARA a Java parallel random number generator library for high-performance computing Doc](https://www.cs.rit.edu/~ark/pj/doc/overview-summary.html)

[En C++](https://github.com/cslarsen/mersenne-twister)

[En C](https://stackoverflow.com/questions/1640258/need-a-fast-random-generator-for-c)

## Calculos mas rapido

[Estadistica mas rapido](http://commons.apache.org/proper/commons-math/javadocs/api-3.2/org/apache/commons/math3/stat/descriptive/SummaryStatistics.html)

## Hallazgos

Libreria por defecto
```
50M
numeros aleatorios 15s
verificando duplicados 37s
```

Libreria JAPARA
```
50M
numeros aleatorios 10s
verificando duplicados 38s
```

## Resultados 5.6

```
[SIM 5.6 FIN] Tiempo: 359023ms
{38700.0=1.0, 38900.0=1.0, 39000.0=2.0, 39300.0=4.0, 39900.0=1.0, 39800.0=1.0}
{41000.0=1.0, 38000.0=1.0, 39000.0=4.0, 40000.0=4.0}
```

## Resultados 5.12

Util `Tiempo max = (hora - cliente.horaEntrada + 1) * 60`.

```
[SIM 5.12 FIN] Tiempo: 1005ms
F1 {0=10.0}
F2 {2=2.0, 3=1.0, 6=1.0, 7=2.0, 9=3.0, 10=1.0}
T promedio en sistema 48.11248945584497 min
```

## TO DO

- [ ] Presentacion