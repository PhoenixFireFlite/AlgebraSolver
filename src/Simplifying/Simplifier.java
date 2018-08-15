package Simplifying;

import Tree.Tree;

public class Simplifier {

    // Eventually return Tree instead of Rule
    public static Rule simplify(Tree tree){
        return RuleSet.getMatchingRule(tree);
    }

    // Eventually return Tre instead of bool
    public static boolean simplifyWithRule(Tree tree, Rule rule){
        return tree.matches(rule.getSearchTree());
    }

}
