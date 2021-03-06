package Simplifying;

import java.util.ArrayList;
import Tree.Tree;

class RuleSet {

    private static ArrayList<Rule> rules = new ArrayList<>();

    static{
//        rules.add(new Rule("0/:any(a)","0"));
//        rules.add(new Rule(":any(a)/1",":any(a)"));
//        rules.add(new Rule(":any(a)+0",":any(a)"));
//        rules.add(new Rule("(:any(a)+:any(b))/(:any(a)*:any(b))", "1/:any(b)+1/:any(a)"));
//        rules.add(new Rule(":any(a)/:any(b)+:any(b)/:any(c)+:any(c)/:any(d)", ":any(a)"));
//          rules.add(new Rule(":any(a)/:any(b)+:any(b)/:any(c)+:any(a)+:any(b)", ":any(a)"));
//        rules.add(new Rule("(:any(a)+:any(b)+:any(c))/(:any(a)+:any(b))", ":any(a)"));
        rules.add(new Rule("(:any(a)+:any(b))*(:any(c)+:any(d))", "0"));
//        rules.add(new Rule("(:any(a)+:any(b))/(:any(a)*:any(b))", ":any(a)"));
//        rules.add(new Rule(":any(a)/:any(b)+:any(c)/:any(b)","(:any(a)+:any(c))/:any(b)"));
    }

    public static Rule getMatchingRule(Tree tree){
        for(Rule rule: rules){
            System.out.println("Checking if "+tree+" matches "+rule.getSearchTree());
            if(tree.matches(rule.getSearchTree()))
                return rule;
        }
        return null;
    }

}
