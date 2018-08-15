import Simplifying.Rule;
import Simplifying.Simplifier;
import Tree.Tree;

import java.util.HashMap;
import java.util.Random;

public class Main {

    private static Random rand = new Random();

    private final static String test = "1+a+1-2-b-2/3/c/3*4*d*4^5^e^5--6--f+-7+-x+2sin(8+x)+xsin(8-x)+sin(-8+x)+sin(-8-x)-sin(x+1)+-sin(x+1)+pii+ipi";

    public static void main(String args[]){
        testParser();
        testMatcher();

//        Tree tree = new Tree("(1+2+3+4+5)/(2+3+4+5)");
        Tree tree = new Tree("(1+2+3+4)*(3+4+5)");
        System.out.println(tree);
        System.out.println(Simplifier.simplify(tree));

//        for(int i=0;i<10000000;i++) {
//            String s = generateEquation(10);
////            System.out.println(s);
//            Tree tree = new Tree(s);
////            System.out.println(tree);
//            Tree tree2 = new Tree(tree.toString());
////            System.out.println(tree2);
//            if(!tree.equalTo(tree2)){
//                System.out.println(s);
//                System.out.println(tree);
//                System.out.println(tree2);
//            }
//        }

//        System.out.println(new Tree(":any(1-csc(i))"));

//        System.out.println(s);
//        System.out.println(tree);

    }

    private static String generateEquation(int tokens){
        StringBuilder expression = new StringBuilder();

        char[] operators = new char[] {'+','-','*','/','^'};
        String[] functions = new String[]{"sin","cos","tan","csc","sec","cot","sqrt"};
        String[] constants = new String[]{"pi","e","i"};

        int pc = 0;

        tokens += (tokens%2==0)? 1:0;

        for(int i=0;i<tokens;i++){
            if(i%2==0){
                switch (rand.nextInt(3)) {
                    case 0:
                        expression.append(constants[rand.nextInt(constants.length)]);
                        break;
                    case 1:
                        expression.append(rand.nextInt(9));
                        break;
                    case 2:
                        expression.append(functions[rand.nextInt(functions.length)]).append("(");
                        i--;
                        pc++;
                        break;
                }
            }else {
                expression.append(operators[rand.nextInt(operators.length)]);
            }

            if(pc > 0 && i%2==0 && rand.nextBoolean() && rand.nextBoolean()) {
                expression.append(")");
                pc--;
            }
        }

        while(pc-- > 0){
            expression.append(")");
        }

        return expression.toString();
    }

    private static void testParser(){
        new Tree(test);
    }

    private static void testMatcher(){
        HashMap<Integer, Boolean> tests = new HashMap<>();

        tests.put(1, (new Rule(":any(a)+0",":any(a)").matches(new Tree("x+0").getRoot())).checkValidity());
        tests.put(2, !(new Rule(":any(a)+0",":any(a)").matches(new Tree("x").getRoot())).checkValidity());

        tests.put(3, (new Rule(":any(a)/1",":any(a)").matches(new Tree("x/1").getRoot())).checkValidity());
        tests.put(4, !(new Rule(":any(a)/1",":any(a)").matches(new Tree("x").getRoot())).checkValidity());

        tests.put(5, (new Rule("(:any(a)/:any(b))/:any(c)","(:any(a)/(:any(b)*:any(c)))").matches(new Tree("(x/y)/z").getRoot())).checkValidity());
        tests.put(6, !(new Rule("(:any(a)/:any(b))/:any(c)","(:any(a)/(:any(b)*:any(c)))").matches(new Tree("x/y").getRoot())).checkValidity());

        tests.put(7, (new Rule(":any(a)*(:any(b)/:any(c))","(:any(a)*:any(b))/:any(c)").matches(new Tree("x*(a/b)").getRoot())).checkValidity());
        tests.put(8, !(new Rule(":any(a)*(:any(b)/:any(c))","(:any(a)*:any(b))/:any(c)").matches(new Tree("x*a").getRoot())).checkValidity());

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
