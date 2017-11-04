# simulacion
Ejercicios de simulacion

## Codigo util

```
mensaje += (Util.DEBUG) ? "\n[PRUEBAS] Poker OK" : "";
Integer.parseInt(Integer.toString(number).substring(0, 1));
TimeUnit.NANOSECONDS.toMillis(numero)
```

## Generacion

https://www.researchgate.net/publication/232635848_JAPARA_-_A_Java_Parallel_Random_Number_Generator_Library_for_High-Performance_Computing
http://ieeexplore.ieee.org/document/1303143/?reload=true
https://www.cs.rit.edu/~ark/pj/doc/overview-summary.html
https://github.com/cslarsen/mersenne-twister
https://stackoverflow.com/questions/1640258/need-a-fast-random-generator-for-c

## Calculos mas rapido

http://commons.apache.org/proper/commons-math/javadocs/api-3.2/org/apache/commons/math3/stat/descriptive/SummaryStatistics.html

## Hallazgos

Libreria por defecto
50M
numeros aleatorios 15s
verificando duplicados 37s
Libreria buena
50M
numeros aleatorios 10s
verificando duplicados 38s

## Resultados 5.6

Tiempo: 394950ms
P1 38300.0=2.0, 38700.0=1.0, 39000.0=4.0, 39300.0=1.0, 39600.0=1.0, 39900.0=1.0}
P2 {41000.0=2.0, 38000.0=2.0, 42000.0=1.0, 39000.0=3.0, 40000.0=2.0}