# FCP Code Resources

This repository contains the source code used in the book [_Functional and Concurrent Programming: Core Concepts and Features_](https://www.fcpbook.org), published by Addison-Wesley Professional.
It is laid out as follows:

  - `Scala` contains all the Scala code used in the book.
    Illustrations are organized by chapter and section.
    Code snippets that are not standalone classes or functions are implemented as _demos_.
    Demos sometimes rely on arguments for data that is not mentioned in the book.
    Data that the book references but does not provide explicitly are implemented in terms of _mock_ objects, often using unrealistic code.
    Some unit tests are written using Scalatest.
    Note that mock objects and unit tests tend to use dirty, uncommented code that is fairly unreadable.
    You are advised to stay away from them.
    
  - `Java` contains almost all the Java code used in the book (see `MoreJava` for exceptions).
    This code is used in a joint `sbt` project with `Scala` code and depends on mock objects written in Scala.
    
  - `MoreJava` contains additional Java code that is not part of the `sbt` project:
    
      - Code from Chapter 14 that uses Java records.
        Due to a bug, Java records are not currently supported in Scala 3 (https://github.com/lampepfl/dotty/issues/14846).
      - Code from the appendix that uses features only available as a preview in Java 19.  

  - `Kotlin` contains all the Kotlin code.
    It is setup as a `gradle` project, independent from the main `sbt` project.
    
  - `Python` contains (very little) Python code, with no particular setup.
  
  - `JavaScript` contains (very little) JavaScript code, with no particular setup.

  - `Bash` contains (very little) Bash script code, with no particular setup.
    
The `Scala` and `Java` source repositories are setup as a single `sbt` project, which should make it easy to load and compile the code using `sbt` or any IDE that is `sbt` aware, like JetBrains' `IntelliJ`.
The project specifies the following dependencies:

  - [`tiny-scala-utils`](https://charpov.github.io/TinyScalaUtils/docs/index.html): This is my own library, used mostly to implement tests and mock objects, though it also occasionally appear in the main code (e.g., to display time and thread information in demos).
  - [`scala-parallel-collections`](https://github.com/scala/scala-parallel-collections/): A semi-standard Scala library that implements collections with parallel processing (see section 21.4).
  - [`dotty-cps-async`](https://github.com/rssh/dotty-cps-async/): An experimental "_async/await_" implementation in Scala (see section 27.4).
  - [`akka-actor-typed`](https://doc.akka.io/docs/akka/2.7.0/typed/index.html): The Akka actors framework (see section 27.5).
  - [`slf4j-simple`](https://www.slf4j.org): The logging framework used by Akka.
  - [`reactor-core`](https://projectreactor.io): The Reactor project, a popular Java implementation of _reactive streams_ (see section 27.6).
  
The last four dependencies are used only in Chapter 27.
