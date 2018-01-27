package Simplifying;

import Tree.Nodes.Matchers.Matcher;
import Tree.Nodes.Primitives.Node;
import Tree.Tree;

import java.util.HashMap;

public class Rule {

    private Tree searchTree;
    private Tree replaceTree;

    public Rule(String searchString, String replaceString){
        searchTree = new Tree(searchString);
        replaceTree = new Tree(replaceString);
    }

    public Tree getSearchTree(){
        return searchTree;
    }

    public MatchResponse matches(Node node){
        return node.matches(searchTree.getRoot());
    }

    public void applyRule(Node node, HashMap<Character,Node> varmap){
        Node newReplaceTree = replaceTree.getRoot().getCopy();
        setVars(newReplaceTree, varmap);
        node.setTo(newReplaceTree);
    }

    private void setVars(Node node, HashMap<Character,Node> varmap){
        if(node.isMatcher){
            node.setTo(varmap.get(((Matcher)node).getName()).getCopy());
        }else if(node.getChildrenCount() != 0){
            for(Node child: node.getChildren()){
                setVars(child,varmap);
            }
        }
    }


    public String toString(){
        return searchTree+" = "+replaceTree;
    }

}
