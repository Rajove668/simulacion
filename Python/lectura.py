import random

from poker import poker

# CONSTANTES
DEBUG = True

# Lectura de numeros
archivo = open("100-numeros.txt", "r")
numeros_raw = archivo.readlines()

numeros = []

for numero in numeros_raw:
    numeros.append(
        numero.replace(',', '.').replace('\n', '')
    )
if DEBUG:
    print("Numeros leidos(", len(numeros), ")", numeros)
numeros_sistema = []
for i in range(100):
    numeros_sistema.append(str(random.uniform(0, 1))[:7])

print(poker(numeros_sistema, DEBUG))
