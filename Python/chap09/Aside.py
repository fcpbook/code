#!/usr/bin/env python3

x = 1


# noinspection PyUnboundLocalVariable,PyShadowingNames
def f():
    x = 2
    if x > 0:
        x = 3
        y = 4
    print(x)
    print(y)


f()

print(x)
# noinspection PyUnresolvedReferences
print(y)
