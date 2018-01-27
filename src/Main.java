import Simplifying.Rule;
import Simplifying.Simplifier;
import Tree.Tree;

import java.util.HashMap;

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
        testMatcher();
//        for(int i=0;i<100000;i++)
//            new Tree(test);
//        long start = System.nanoTime(); // 3.5 - 2.3 secs
//        for(int i=0;i<100000;i++)
//            new Tree(test);
//        System.out.println((System.nanoTime()-start)/1000000000.0+" secs");

        Tree tree = new Tree("x/1");
        Simplifier.simplify(tree);

//        long start = System.nanoTime();
//        System.out.println(Simplifier.simplify(tree));
//        System.out.println((System.nanoTime()-start)/1000000000.0);

//        System.out.println("Fraction: "+tree.getFractionalValueString());
//        System.out.println("Decimal: "+tree.getDecimalValueString());
//        System.out.println("Infix: "+tree);
    }

    private static void testParser(){
        new Tree(test);
    }

    private static void testMatcher(){
        HashMap<Integer, Boolean> tests = new HashMap<>();

        tests.put(1, (new Rule(":any(a)+0",":any(a)").matches(new Tree("x+0").getRoot())).getValidity());
        tests.put(2, !(new Rule(":any(a)+0",":any(a)").matches(new Tree("x").getRoot())).getValidity());

        tests.put(3, (new Rule(":any(a)/1",":any(a)").matches(new Tree("x/1").getRoot())).getValidity());
        tests.put(4, !(new Rule(":any(a)/1",":any(a)").matches(new Tree("x").getRoot())).getValidity());

        tests.put(5, (new Rule("(:any(a)/:any(b))/:any(c)","(:any(a)/(:any(b)*:any(c)))").matches(new Tree("(x/y)/z").getRoot())).getValidity());
        tests.put(6, !(new Rule("(:any(a)/:any(b))/:any(c)","(:any(a)/(:any(b)*:any(c)))").matches(new Tree("x/y").getRoot())).getValidity());

        tests.put(7, (new Rule(":any(a)*(:any(b)/:any(c))","(:any(a)*:any(b))/:any(c)").matches(new Tree("x*(a/b)").getRoot())).getValidity());
        tests.put(8, !(new Rule(":any(a)*(:any(b)/:any(c))","(:any(a)*:any(b))/:any(c)").matches(new Tree("x*a").getRoot())).getValidity());

        boolean failed = false;
        for(int val: tests.keySet()){
            if(!tests.get(val)) {
                System.err.println("Failed Matcher Test! : " + val);
                failed = true;
            }
        }
        if(!failed)
            System.out.println("Matcher Tests Succeeded!");
    }

}
