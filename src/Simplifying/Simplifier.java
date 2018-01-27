package Simplifying;

import Tree.Tree;

import java.util.ArrayList;

public class Simplifier {

    public static Rule simplify(Tree tree){
        return RuleSet.getMatchingRule(tree);
    }

}
