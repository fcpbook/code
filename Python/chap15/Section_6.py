#!/usr/bin/env python3

from Section_1 import Book, print_title


book = Book(title="Le Comte de Monte-Cristo", author="Alexandre Dumas", pagecount=1476)

print_title(book) # prints "Le Comte de Monte-Cristo"

class Magazine:
    def __init__(self, title, number, pagecount):
        self.title = title
        self.number = number
        self.pagecount = pagecount


magazine = Magazine(title="Life", number=123, pagecount=45)
print_title(magazine) # prints "Life"

class Noble:
    def __init__(self, name, title):
        self.name = name
        self.title = title


person = Noble(name="Edmond Dantès", title="Comte de Monte-Cristo")
print_title(person) # prints "Comte de Monte-Cristo"
