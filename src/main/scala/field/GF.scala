package field

object Field {
  //val gen = 10^251+10^14+10^4 + 10^1 + 1
  case class GF(elem: List[Int] = List(0), m: Int = 251){
//    def this(ones: List[Int]) = {
//        this((for(i <- 0 to ones.max)yield if(ones.contains(i)) 1 else 0).toList.reverse,ones.max)
//    }
    def +(that: GF):GF = {
      def add(answ: List[Int], el1: List[Int], el2: List[Int]): List[Int] =
        (Option(el1), Option(el2)) match {
          case (Some(h1::t1),Some(h2::t2)) => add((h1^h2)::answ, t1, t2)
          case _ => answ.reverse ++ el1 ++ el2
      }
      GF(add(List(), this.elem, that.elem), if(this.m > that.m)this.m else that.m)
    }
    def -(that: GF): GF = this + that
    //def unary_!(a: GF): GF = ???
    def mod(that: GF): GF = {
//      def cutO: GF ={
//        val newEl = this.elem.dropWhile(_ == 0)
//        new GF(newEl, m)
//      }
      def div(answ: List[Int], el2: List[Int]): List[Int] = (answ.lengthCompare(el2.length) < 0, answ) match{
        case (false, 1::_) => div((new GF(el2,0) + new GF(answ ,0)).elem, el2)
        case (false, 0::t) => div(t , el2)
        case _ => answ.reverse
      }
      GF(div(elem.reverse, that.elem.reverse), that.m)
    }
    def >>(shift: Int): GF = if(shift > 0) GF(0::elem, m+1) >> shift-1 else this

    def *(that: GF): GF = {
      def mul(answ: List[Int], el1: List[Int], el2: List[Int], num: Int): List[Int] =
        Option(el2) match {
          case Some(1::t) => mul((new GF(answ, answ.length) + (new GF(el1, this.m) >> num)).elem, el1, t, num+1)
          case Some(0::t) => mul(answ, el1, t, num+1)
          case _ => answ
        }
      GF(mul(List(), this.elem, that.elem, 0), this.m + that.m)
    }
    lazy val getO: List[Int] = {
      def getOnes(answ: List[Int], el: List[Int], acc: Int): List[Int] = Option(el) match{
        case Some(1::t) => getOnes(acc::answ, t, acc+1)
        case Some(0::t) => getOnes(answ, t, acc+1)
        case _ => answ.reverse
      }
      getOnes(List(), elem, 0)
    }
    //val trace = ???
    lazy val squared: GF = GF(this.getO.map(_ * 2))
    def ^ (power: Int): GF = {GF()
//      def pow(p: Int, el: GF, acc: GF): GF = p match{
//        case 0 => el * acc
//        case t => t % 2 match {
//          case 0 => pow(p/2, el.squared, acc)
//          case _ => pow(p/2, el.squared, el * acc)}
//      }
//      pow(power, this, new GF(List(1),1))
    }
  }
  object GF{
    def apply(ones: List[Int]) = {
      new GF((for(i <- 0 to ones.max)yield if(ones.contains(i)) 1 else 0).toList.reverse,ones.max+1)
    }
  }
}
