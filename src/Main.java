import Tree.Tree;

//1           = ~0.004 secs
//10          = ~0.006 secs
//100         = ~0.02  secs
//1,000       = ~0.06  secs
//10,000      = ~0.30  secs
//100,000     = ~1.50  secs
//1,000,000   = ~5.12  secs
//10,000,000  = ~44.26 secs
//100,000,000 = ~413.5 secs

public class Main {

    private final static String test = "1+a+1-2-b-2/3/c/3*4*d*4^5^e^5--6--f+-7+-x+2sin(8+x)+xsin(8-x)+sin(-8+x)+sin(-8-x)-sin(x+1)+-sin(x+1)+pii+ipi";

    public static void main(String args[]){
        testParser();
//        for(int i=0;i<100000;i++)
//            new Tree(test);
//        long start = System.nanoTime(); // 3.5 - 2.3 secs
//        for(int i=0;i<100000;i++)
//            new Tree(test);
//        System.out.println((System.nanoTime()-start)/1000000000.0+" secs");

        Tree tree1 = new Tree("(-b+sqrt(b^2-4ac))/(2a)");
        Tree tree2 = new Tree("(sqrt(b^2-4ca)-b)/(2a)");

        System.out.println(tree1.getRoot().equalTo(tree2.getRoot()));

//        System.out.println("Fraction: "+tree.getFractionalValueString());
//        System.out.println("Decimal: "+tree.getDecimalValueString());
//        System.out.println("Infix: "+tree);
    }

    private static void testParser(){
        new Tree(test);
    }

}
