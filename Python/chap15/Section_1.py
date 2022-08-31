#!/usr/bin/env python3


class Book:
    def __init__(self, title, author, pagecount):
        self.title = title
        self.author = author
        self.pagecount = pagecount


book = Book(title="Mathias Sandorf", author="Jules Verne", pagecount=552)
book1 = book
book2 = book

books = [book1, book2, 42]
# for book in books:
#    print(book.title)

book_or_string = book
# use variable book_or_string as a book
book_or_string = "Le Comte de Monte-Cristo"
# use variable book_or_string as a string

price = 12.95
#book.title + ": " + price
#price + ": " + book.title

if book.pagecount:
    print(book.title)

books = {book} # a set of books

added = 0
# DON'T DO THIS!
if books.add(book):
    added += 1

def print_title(book):
    print(book.title)


def print_title2(book):
    if isinstance(book, Book):
        print(book.title)
    else:
        raise TypeError("not a book")
