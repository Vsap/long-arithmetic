
import javafx.util.Pair;

import static java.lang.Math.log;
import static java.lang.Math.pow;

public class LongMath {
    private int w;
    private int d = (int)log(w);
    private long[] num;
    public LongMath(String s){
        w = 4;
        this.num = new long[s.length()];
        char[] c = s.toCharArray();

        for(int i = 0; i < this.num.length; i++) this.num[i] = Long.parseLong(String.valueOf(c[this.num.length-1 - i]),16);
    }
    @Override
    public String toString(){
        String s = "";
        for(int i = this.num.length-1;i >=0; i--) s+= Long.toHexString(this.num[i]);
        return s;
    }
    public LongMath() {
        w = 4;
        num = new long[32];
    }
    public LongMath(int l){
        w = 4;
        num = new long[l];
    }
    public LongMath(long l){
        w = 4;
        num = new long[32];
        num[0] = l;
    }
    public LongMath(int l, long constant){
        w = 4;
        num = new long[l];
        num[0] =1;
    }
    public LongMath(LongMath that){
        this.w = that.w;
        this.num = new long[that.num.length];
        for(int i =0; i< that.num.length;i++) num[i] = that.num[i];
    }
    public int BitLength(){
        int ld = this.DigitLength()-1;
        int rest = Integer.toBinaryString((int)this.num[ld]).length();
        return  ld+rest;
    }
    public int DigitLength(){
        int i = this.num.length-1;
        while(this.num[i] == 0 && i > -1) i--;
        return i+1;
    }
    public void InsertBit(int insert){
        this.num[insert >> d] = this.num[insert >> d] | 1 << w-1 & insert;
    }
    public long GetBit(int place){return this.num[place >> d] | 0 << w-1 & place;}
    public LongMath LongShiftDigitToHigh (int shift){
        LongMath c = new LongMath(this.num.length);
        for(int i = 0; i < this.num.length - shift; i++){
            c.num[i+shift] = this.num[i];
        }
        return c;
    }
    public LongMath LongShiftDigitToLow(int shift){
        LongMath c = new LongMath(this.num.length);
        for(int i = 0; i < this.num.length - shift; i++){
            c.num[i] = this.num[i+shift];
        }
        return c;
    }
    public LongMath LongAdd(LongMath that){
        LongMath c = new LongMath(that.num.length);
        long carry = 0;
        long temp = 0;
        for(int i = 0; i < this.num.length; i++){
            temp = this.num[i] + that.num[i]+ carry;
            c.num[i] = temp & (int)(pow(2,w)-1);
            carry = temp >> w;
        }
        return c;
    }

    public LongMath LongSub(LongMath that){
        LongMath c;
        if(that.Cmp(this) > 0){return null;}
        else { c = new LongMath(this.num.length);}
        int borrow = 0;
        long temp = 0;
        for(int i =0; i < num.length; i++){
            temp = this.num[i] - that.num[i] - borrow;
            if(temp >= 0){
                c.num[i] = temp;
                borrow = 0;
            }
            else{
                c.num[i] = (int)pow(2,w) + temp;
                borrow = 1;
            }
        }
        return c;
    }

    public int Cmp(LongMath that){
        if (this.num.length > that.num.length) return 1;
        else if (this.num.length < that.num.length) return -1;
        else {
        int i = that.num.length-1;
        while(i >=0 && this.num[i]==that.num[i]) i--;
        if(i == -1) return 0;
        if(this.num[i] > that.num[i]) return 1;
        else return -1;}
    }

    public LongMath LongMulOneDigit(long d){
        int n = this.num.length;
        LongMath c = new LongMath(n);
        long temp = 0;
        long carry = 0;
        for(int i = 0;i < this.num.length; i++){
            temp = this.num[i] * d + carry;
            c.num[i] = temp & (long)(pow(2,w)-1);
            carry = temp >> w;
        }
        return c;
    }
    public LongMath LongMul(LongMath that){
        LongMath c;
        LongMath temp;
        int len=0;
        c = new LongMath(this.num.length);
        if(this.Cmp(that) >=0) len=that.num.length;
        else len = this.num.length;
        for(int i = 0; i< len;i++){
            temp = this.LongMulOneDigit(that.num[i]);
            temp = temp.LongShiftDigitToHigh(i);
            c = c.LongAdd(temp);
        }
        return c;
    }
    public LongMath LongPow1(LongMath p){
        LongMath c = new LongMath(this.num.length, (long)1);
        LongMath a = new LongMath(this);
        for(int i=0; i < p.BitLength(); i++){
            if(p.GetBit(i) == 1) c = c.LongMul(a);
            a = a.LongMul(a);
        }
        return c;
    }
    public LongMath LongPow2(LongMath P){
        LongMath c = new LongMath(this.num.length, (long)1);
        for(int i = P.BitLength()-1;i >=0;i--){
            if(P.GetBit(i) == (long)1) c = c.LongMul(this);
            if(i != 0) c = c.LongMul(c);
        }
        return c;
    }
    public Pair<LongMath,LongMath> LongDiv(LongMath that){
        LongMath r = new LongMath(this);
        LongMath q = new LongMath((long)0);
        LongMath c = new LongMath();
        int k = that.BitLength();
        int t = 0;
        while(r.Cmp(that) >= 0) {
            t = r.BitLength();
            c = that.LongShiftDigitToHigh(t-k);
            if(r.Cmp(c) < 0){
                t--;
                c = that.LongShiftDigitToHigh(t-k);
            }
            r = r.LongSub(c);
            q.InsertBit((int)pow(2,t-k));
        }
        return new Pair<LongMath,LongMath>(q,r);
    }
}
