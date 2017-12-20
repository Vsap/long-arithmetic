import field.Field._

object Program{
  def main(args: Array[String]): Unit ={
    val a = new GF(List(1,1,1), 3)
    val b = new GF(List(1,0,1), 3)
    val c = new GF(List(1,1,0), 3)
    val d = new GF(List(0,1,1))
    val e = GF(List(0,1,2))
    println("a+b=" + (a + b))
    println("a= " + a)
    println("b= " + b)
    println("c= " + c)
    println("a mod d=" + (a mod d))
    println("d= " + d)
    println("e= " + e)

    println("d>>2 = " + (d >> 2))
    println(b.getO.toString())
    println(a.getO.toString())
    println("c * b = " + c * b)
    println("b * c = " + b * c)
    println("c * d = " + c * d)
    println("d * c = " + d * c)
    //println(a ^ 2 mod c)
  }
}
