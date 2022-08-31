package chap27

import java.util.concurrent.atomic.AtomicReference
import scala.annotation.tailrec

object Section_2:
   object LockStack:
      private class Node[A](val value: A, val next: Node[A])

      final class Stack[A]:
         private var top: Node[A] = null

         def peek: Option[A] = synchronized {
            if top == null then None else Some(top.value)
         }

         def push(value: A): Unit = synchronized {
            top = Node(value, top)
         }

         def pop(): Option[A] = synchronized {
            if top == null then None
            else
               val node = top
               top = node.next
               Some(node.value)
         }
      end Stack
   end LockStack

   object AtomicStack:
      private class Node[A](val value: A, var next: Node[A])

      final class Stack[A]:
         private val top = AtomicReference[Node[A]]()

         def peek: Option[A] = top.get match
            case null => None
            case node => Some(node.value)

         def push(value: A): Unit =
            val node = Node(value, top.get)
            while !top.compareAndSet(node.next, node) do node.next = top.get

         @tailrec
         def pop(): Option[A] = top.get match
            case null => None
            case node => if top.compareAndSet(node, node.next) then Some(node.value) else pop()
      end Stack
   end AtomicStack
