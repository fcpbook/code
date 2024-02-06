---
layout: default
title: Errata
---

# Errata

  - **p. xxxiv:** In the third paragraph, "has been to used to" should be "has been used to".

  - **p. 219:** The `ListADT` class should either define _monomorphic_ lists:
    ```scala
    abstract class ListADT[A]:
       type List
       val empty: List
       def cons(x: A, list: List): List
       ...
    ```
    or _polymorphic_ lists:
    ```scala
    abstract class ListADT:
       type List[_]
       def empty[A]: List[A]
       def cons[A](x: A, list: List[A]): List[A]
       ...
    ```
    The version in the book defines monomorphic lists, but refers to them as `List[A]` instead of `List`; the type argument `[A]` is not used and should be removed.

  - **p. 246:** In the third paragraph, `Title[A]` should be `Titled[A]`.
  
  - **p. 289:** In the first listing, the list must be copied with the lock owned. The correct implementation is:
    ```scala
    def getAll: List[String] = synchronized(ArrayList(contents))
    ```
    
  - **p. 418:** The `{runner-seq}` line in the `Runner` listing is leftover LaTeX due to last minute changes; it is not part of the code and should be ignored.
