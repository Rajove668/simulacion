# simulacion
Ejercicios de simulacion

## Generacion

D. Coddington, P & J. Newell, A. (2004). JAPARA — A Java Parallel Random Number Generator Library for High-Performance Computing. Parallel and Distributed Processing Symposium, International. 6. 156a. 10.1109/IPDPS.2004.1303143.

[JAPARA Articulo encontrado](https://www.researchgate.net/publication/232635848_JAPARA_-_A_Java_Parallel_Random_Number_Generator_Library_for_High-Performance_Computing)

[JAPARA IEEE Articulo](http://ieeexplore.ieee.org/document/1303143/?reload=true)

[JAPARA a Java parallel random number generator library for high-performance computing Doc](https://www.cs.rit.edu/~ark/pj/doc/overview-summary.html)

[En C++](https://github.com/cslarsen/mersenne-twister)

[En C](https://stackoverflow.com/questions/1640258/need-a-fast-random-generator-for-c)

```java
static ArrayList<Double> numeros(int n) {
    ArrayList<Double> numeros;
    do {
        numeros = set_numeros(n);
    } while (!todas_pruebas(numeros));     
    return numeros;
}
static ArrayList<Double> set_numeros(int n) {
    ArrayList<Double> nuevo_set = new ArrayList<>();
    Random r = new Random();
    do {
        for (int i = 0; i < n; i++) {
            nuevo_set.add(r.nextDouble());
        }
    } while (duplicados(nuevo_set));
    return nuevo_set;
}
static boolean todas_pruebas(ArrayList<Double> numeros) {
    int aciertos = 0;
    if (poker(numeros)) {
        aciertos++;
    }
    if (kolmogorov_smirnov(numeros)) {
        aciertos++;
    }
    if (frecuencias_chi_cuadrado(numeros)) {
        aciertos++;
    }
    if (promedios(numeros)) {
        aciertos++;
    }
    System.gc();
    return aciertos >= 3;
}
static boolean duplicados(ArrayList<Double> numeros_aleatorios) {
    Set<Double> set = new HashSet<>();
    for (Double numero : numeros_aleatorios) {
        if (set.add(numero) == false) {
            return true;
        }
    }
    return false;
}
```

## Calculos mas rapido

[Estadistica mas rapido](http://commons.apache.org/proper/commons-math/javadocs/api-3.2/org/apache/commons/math3/stat/descriptive/SummaryStatistics.html)

## Resultados 5.6

```
[SIM 5.6 FIN] Tiempo: 394946ms
{41100.0=1.0, 38400.0=1.0, 38500.0=1.0, 38700.0=2.0, 39000.0=1.0, 39600.0=2.0, 39800.0=1.0, 40500.0=1.0}
{41000.0=4.0, 40000.0=6.0}
```

![5-6][10_Corridas_5_6.jpg]

## Resultados 5.12

Util `Tiempo max = (hora - cliente.horaEntrada + 1) * 60`.

```
[SIM 5.12 FIN] Tiempo: 1224ms
F1 {0=10.0}
F2 {1=1.0, 2=1.0, 4=1.0, 5=2.0, 6=2.0, 7=2.0, 10=1.0}
T promedio en sistema 49.61755134513993 min
```

![5-12][10_Corridas_5_12.jpg]

## TO DO

- [ ] Presentacion