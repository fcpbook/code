package chap05

object Section_5:
   enum BoolExpr:
      case T
      case F
      case Not(e: BoolExpr)
      case And(e1: BoolExpr, e2: BoolExpr)
      case Or(e1: BoolExpr, e2: BoolExpr)

   def demo: BoolExpr =
      import BoolExpr.*
      And(T, Or(F, Not(And(T, F))))

   def eval(expr: BoolExpr): Boolean =
      import BoolExpr.*
      expr match
         case T           => true
         case F           => false
         case Not(e)      => !eval(e)
         case And(e1, e2) => eval(e1) && eval(e2)
         case Or(e1, e2)  => eval(e1) || eval(e2)
