#!/usr/bin/env python3

temps = [88, 91, 78, 69, 100, 98, 70]

filteredCelsius = [round((temp - 32) / 1.8) for temp in temps if temp > 75]

print(filteredCelsius)
