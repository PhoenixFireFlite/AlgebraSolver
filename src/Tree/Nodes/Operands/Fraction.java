package Tree.Nodes.Operands;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Primitives.Leaf;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Fraction extends Leaf {

    private BigInteger numerator, denominator;

    private static BigInteger ONE = BigInteger.ONE, ZERO = BigInteger.ZERO;

    public Fraction(String string){
        if(!string.contains(".")){
            numerator = new BigInteger(string);
            denominator = ONE;
        }else{
            BigDecimal decimal = new BigDecimal(string);

            String[] parts = decimal.toString().split("\\.");

            BigDecimal den = BigDecimal.TEN.pow(parts[1].length());
            BigDecimal num = (new BigDecimal(parts[0]).multiply(den)).add(new BigDecimal(parts[1]));

            numerator = num.toBigIntegerExact();
            denominator = den.toBigIntegerExact();
        }
        reduce();
    }

    public Fraction(BigDecimal decimal){
        if(!decimal.toString().contains(".")){
            numerator = decimal.toBigIntegerExact();
            denominator = ONE;
        }else {
            String[] parts = decimal.toString().split("\\.");
            BigDecimal den = BigDecimal.TEN.pow(parts[1].length());
            BigDecimal num = (new BigDecimal(parts[0]).multiply(den)).add(new BigDecimal(parts[1]));

            numerator = num.toBigIntegerExact();
            denominator = den.toBigIntegerExact();
        }
    }

    public Fraction(int integer){
        numerator = new BigInteger(Integer.toString(integer));
        denominator = ONE;
    }

    public Fraction(int num, int den){
        numerator = new BigInteger(Integer.toString(num));
        denominator = new BigInteger(Integer.toString(den));
    }

    public Fraction(BigInteger num, BigInteger den){
        numerator = num;
        denominator = den;
    }

    private void reduce(){
        BigInteger GCD = gcd(numerator.abs(),denominator);
        if(greater(GCD,ZERO)){
            numerator = numerator.divide(GCD);
            denominator = denominator.divide(GCD);
        }
    }

    private static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger r;
        while(notequal(b, ZERO)){
            r = a.mod(b);
            a = b;
            b = r;
        }
        return a;
    }

    public Fraction add(Fraction frac){
        Fraction answer;
        if(equal(denominator, frac.denominator)){
            answer = new Fraction(numerator.add(frac.numerator), denominator);
        }else{
            answer = new Fraction(numerator.multiply(frac.denominator).add(frac.numerator.multiply(denominator)), denominator.multiply(frac.denominator));
        }

        answer.reduce();

        return answer;
    }

    public Fraction multiply(Fraction frac){
        Fraction answer = new Fraction(numerator.multiply(frac.numerator), denominator.multiply(frac.denominator));

        answer.reduce();

        return answer;
    }

    public Fraction divide(Fraction frac){
        Fraction answer = new Fraction(numerator.multiply(frac.denominator), denominator.multiply(frac.numerator));

        answer.reduce();

        return answer;
    }

    public Fraction pow(Fraction frac){
        BigDecimal a = getDecimalValue();
        BigDecimal b = frac.getDecimalValue();
        BigDecimal c = BigDecimalMath.pow(a, b, CONSTANTS.RoundingPlacesMathContext);
        Fraction answer = new Fraction(c);

        answer.reduce();

        return answer;
    }

    public Fraction reciprocal(){
        return new Fraction(denominator, numerator);
    }

    public BigDecimal getDecimalValue() {
        return new BigDecimal(numerator).divide(new BigDecimal(denominator), CONSTANTS.BigDecimalRoundingPlaces, CONSTANTS.BigDecimalRoundingMethod);
    }

    public Fraction getFractionalValue(){
        return this;
    }

    private static boolean greater(BigInteger a, BigInteger b){
        return a.compareTo(b) > 0;
    }

    private static boolean lesser(BigInteger a, BigInteger b){
        return a.compareTo(b) > 0;
    }

    private static boolean equal(BigInteger a, BigInteger b){
        return a.compareTo(b) == 0;
    }

    private static boolean notequal(BigInteger a, BigInteger b){
        return a.compareTo(b) != 0;
    }

    public Node getCopy(){
        return new Fraction(numerator,denominator);
    }

    @Override
    public int sameTypeCompare(Node n) {
        Fraction node = (Fraction) n;
        BigInteger aNum = numerator.multiply(node.denominator);
        BigInteger bNum = node.numerator.multiply(denominator);
        return -aNum.compareTo(bNum);
    }

    @Override
    public boolean sameTypeEquals(Node n) {
        Fraction frac = (Fraction)n;
        return numerator.equals(frac.numerator) && denominator.equals(frac.denominator);
    }

    public String getTypeString(){
        if(equal(denominator,ONE))
            return numerator.toString();
        else
            return numerator.toString()+"÷"+denominator.toString();
    }

    public Type getType(){
        return Type.Fraction;
    }

    public String toString(){
        return getTypeString();
    }
}
