package Simplifying;

import Tree.Tree;

public class Simplifier {

    public static Rule simplify(Tree tree){
        return RuleSet.getMatchingRule(tree);
    }

}
