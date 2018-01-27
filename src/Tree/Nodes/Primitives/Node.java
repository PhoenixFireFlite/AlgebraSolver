package Tree.Nodes.Primitives;

import Simplifying.MatchResponse;
import Tree.Nodes.Matchers.Matcher;
import Tree.Nodes.NodeType;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Type;
import Tree.Tree;

import java.math.BigDecimal;
import java.util.*;

public abstract class Node {
    private static long ID_COUNTER = 0;

    private long ID;
    private Node parent;
    private Tree tree;
    public boolean isMatcher = false;

    Node(){
        ID = setID();
    }

    private static long setID() { return ID_COUNTER++; }

    private static String stringRepeat(String toRepeat, int times){
        StringBuilder sb = new StringBuilder("");
        times = (times < 0)? 0 : times;
        for(int i=0;i<times;i++)
            sb.append(toRepeat);
        return sb.toString();
    }

    private String toString_recursive(Node node, int depth){
        StringBuilder sb = new StringBuilder("");

        String spacing = stringRepeat(" ",depth-1);
        spacing += (depth == 0)? "" : "|";

        System.out.println(spacing+node.getTypeString());

        if(node.getChildrenCount() > 0) {
            for (Node n : node.getChildren()) {
                sb.append(toString_recursive(n, depth + 1));
            }
        }

        return sb.toString();
    }

    public int compare(Node node){
        int diff = getType().ordinal() - node.getType().ordinal();
        if(diff == 0){
            return sameTypeCompare(node);
        }else
            return (diff > 0)? -1 : 1;
    }

    public int sameTypeCompare(Node node){
        int diff = getChildrenCount() - node.getChildrenCount();
        if(diff == 0){
            Node[] nodeChildren = node.getChildren();
            Node[] children = getChildren();
            for(int i=0;i<getChildrenCount();i++){
                int compared = children[i].compare(nodeChildren[i]);
                if(compared != 0) return compared;
            }
            return 0;
        }else{
            return (diff > 0)? -1 : 1;
        }
    }

    public boolean equalTo(Node node) {
        return (getType() == node.getType()) && sameTypeEquals(node);
    }

    public boolean sameTypeEquals(Node node){
        if(getChildrenCount() == node.getChildrenCount()){
            Node[] nodeChildren = node.getChildren();
            Node[] children = getChildren();
            for(int i=0;i<getChildrenCount();i++){
                if(!children[i].equalTo(nodeChildren[i]))
                    return false;
            }
            return true;
        }
        return false;
    }

    public MatchResponse matches(Node matchNode){
        return matches(matchNode, new MatchResponse());
    }

    private MatchResponse matches(Node matchNode, MatchResponse response){
        if(!response.getValidity()) // If response has failed, stop searching
            return response;

        else if(matchNode.isMatcher){
            return matcherMatches((Matcher)matchNode, this) ? // If matcher matches node, add variable to response, else set to failure
                    response.addPossibility(((Matcher) matchNode).getName(), this) :
                    response.setMatch(false);
        }

        else{
            if(getType() == matchNode.getType()){
                switch (getNodeType()) {
                    case Leaf:
                        return response.setMatch(sameTypeEquals(matchNode));

                    case Nary:
                        Queue<Node> patternQueue = new LinkedList<>(((Nary) matchNode).getChildrenAsList());
                        ArrayList<Node> compareToList = ((Nary) this).getChildrenAsList();

//                        System.out.println("Starting:\n  Queue: "+patternQueue + "\n  Compare: " + compareToList);

                        xx:
                        while (!patternQueue.isEmpty()) {
                            Node pattern = patternQueue.peek(); // Get next pattern
                            if(pattern.isMatcher){
                                ArrayList<Node> matches = new ArrayList<>();

                                for (Node compare : compareToList) {
                                    if (matcherMatches((Matcher) pattern, compare)) {
                                        matches.add(compare);
                                    }
                                }

                                if(matches.size() == 0){
//                                    System.out.println("FAIL");
                                    return response.setMatch(false);
                                }

                                if(!response.addPossibility(((Matcher)pattern).getName(), matches).getValidity())
                                    return response;

                                patternQueue.remove();

                            }else {
                                for(int i=0;i<compareToList.size();i++){
                                    Node compare = compareToList.get(i);
                                    response.setMatch(true);
                                    if (compare.matches(pattern, response).getValidity()) {
                                        patternQueue.remove();
                                        compareToList.remove(i);
                                        continue xx;
                                    }
                                }
//                                System.out.println("FAIL");
                                return response.setMatch(false);
                            }
                        }

//                        System.out.println("Left over:\n  Compare: " + compareToList);

                        return response;

                    default:  // Binary or Unary
                        Node[] nodeChildren = matchNode.getChildren();
                        Node[] children = getChildren();
                        for (int i = 0; i < getChildrenCount(); i++) {
                            if (!children[i].matches(nodeChildren[i], response).getValidity())
                                return response.setMatch(false);
                        }
                        return response;
                }
            }
        }

        return response.setMatch(false);
    }

    private static boolean matcherMatches(Matcher matcher, Node node){
        switch(matcher.getType()){
            case Any: return true;
            default: System.err.println("Invalid Matcher String: "+matcher.getTypeString()); return false;
        }
    }

    public void setTo(Node node){
        if(parent == null){
            tree.setRoot(node);
        }else{
            parent.setChildTo(this, node);
        }
    }

    public static int getPrecedence(String s){
        switch (s){
            case "+": return 0;
            case "-": return 0;
            case "*": return 5;
            case "/": return 5;
            case "^": return 10;
            case "(": return -10;
            default: System.err.println("Unknown precedence: "+s); return -1;
        }
    }

    protected static int getNodePrecedence(Node n){
        if(n.getNodeType() == NodeType.Leaf || n.getNodeType() == NodeType.Unary){
            return 100;
        }else{
            return getPrecedence(n.getTypeString());
        }
    }

    public long getID(){
        return ID;
    }

    public String getBranches(){
        return toString_recursive(this, 0);
    }

    public void setTree(Tree tree){
        this.tree = tree;
    }

    void setParent(Node node){
        parent = node;
    }
    public Node getParent(){
        return parent;
    }

    public abstract String toString();

    public abstract void setChildTo(Node from, Node to);
    public abstract Node[] getChildren();
    public abstract int getChildrenCount();
    public abstract String getTypeString();
    public abstract Node getCopy(); // doesn't copy Node.tree var

    public abstract Type getType();
    public abstract NodeType getNodeType();

    public abstract BigDecimal getDecimalValue();
    public abstract Fraction getFractionalValue();

}
