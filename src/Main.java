import javafx.util.Pair;

public class Main {
    public static void main(String[] args){
        LongMath a = new LongMath("0000F008");
        LongMath b = new LongMath("0000F008");
        LongMath c = a.LongMul(b);
        System.out.println("A > B = "+ a.Cmp(b));
        System.out.println("  A = "+a);
        System.out.println("  A.bitLength = "+a.BitLength());
        System.out.println("A*2 = "+a.LongMulOneDigit(2));
        System.out.println("A+B = "+c);
        System.out.println("A-B = "+a.LongSub(b));
        System.out.println("A*B = "+a.LongMul(b));
        System.out.println("A^B 1= "+a.LongPow1(b));///??????????????????
        System.out.println("A^B 2= "+a.LongPow2(b));///??????????????????
        Pair<LongMath,LongMath> p = c.LongDiv(b);
        System.out.println("A/B = "+ p.getValue());
        System.out.println("A/B mod B = " + p.getKey());///*****&&&&&&&&&&&&&&&&&
    }
}
