tt = [60.0, 14.0, 59.0, 84.0, 120, 150]

def restante(tiempo):
  if (tiempo % 60.0) == 0:
    return 0
  else:
    return 60.0-(tiempo % 60.0)

for tiempo in tt:
  print(tiempo, restante(tiempo))