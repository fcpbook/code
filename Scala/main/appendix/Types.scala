package appendix

object Types:
   object Opaque:
      // inside a package
      opaque type Length = Double

      def fromMeters(meters: Double): Length = meters
      def wholeMeters(len: Length): Length   = len.ceil
   end Opaque

   import Opaque.*
   // outside the package
   val oneInch: Length = fromMeters(0.0254)
   val len: Length     = wholeMeters(oneInch)
end Types
