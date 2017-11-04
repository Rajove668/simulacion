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
