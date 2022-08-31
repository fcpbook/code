package chap07

import chap05.Section_4.isEmpty
import chap07.Section_5.append

import scala.annotation.tailrec

object Section_7:
   object ReverseBad:
      // DON'T DO THIS!
      def reverse[A](list: List[A]): List[A] = list match
         case Nil          => list
         case head :: tail => append(reverse(tail), head)

   def reverse[A](list: List[A]): List[A] =
      @tailrec
      def addToStack(rem: List[A], rev: List[A]): List[A] = rem match
         case Nil           => rev
         case top :: bottom => addToStack(bottom, top :: rev)

      addToStack(list, Nil)
   end reverse

   def tokenize(stream: List[Char]): List[String] =
      def addToken(token: List[Char], tokens: List[String]): List[String] =
         if isEmpty(token) then tokens else reverse(token).mkString :: tokens

      @tailrec
      def loop(stream: List[Char], token: List[Char], tokens: List[String]): List[String] =
         stream match
            case w :: chars if w.isWhitespace => loop(chars, Nil, addToken(token, tokens))
            case c :: chars                   => loop(chars, c :: token, tokens)
            case Nil                          => addToken(token, tokens)

      reverse(loop(stream, Nil, Nil))
   end tokenize
