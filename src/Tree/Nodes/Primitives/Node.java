package Tree.Nodes.Primitives;

import Simplifying.MatchResponse;
import Tree.Nodes.Matchers.Matcher;
import Tree.Nodes.Matchers.Scope;
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

    protected Node(){
        ID = nextID();
    }

    private static long nextID() { return ID_COUNTER++; }

    private static String stringRepeat(String toRepeat, int times){
        StringBuilder sb = new StringBuilder("");
        times = (times < 0)? 0 : times;
        for(int i=0;i<times;i++)
            sb.append(toRepeat);
        return sb.toString();
    }

    private String toString_recursive(Node node, int depth){
        StringBuilder sb = new StringBuilder();

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

    int compare(Node node){
        int diff = getType().ordinal() - node.getType().ordinal();
        if(diff == 0){
            return sameTypeCompare(node);
        }else
            return (diff > 0)? -1 : 1;
    }

    private Node getEqualNode(Node node, Node[] nodes){
        for(Node n: nodes){
            if(n.equalTo(node))
                return n;
        }
        return null;
    }

    private ArrayList<Node> getEqualNodes(ArrayList<Node> nodes1, Node[] nodes){
        ArrayList<Node> equalNodes = new ArrayList<>();
        for(Node node: nodes1) {
            for (Node n : nodes) {
                if (n.equalTo(node)) {
                    equalNodes.add(n);
                    break;
                }
            }
        }
        return equalNodes;
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
        return matches(matchNode, new MatchResponse(), new Scope());
    }

    private MatchResponse matches(Node matchNode, MatchResponse response, Scope scope){
        if(!response.checkValidity())
            return response;

        else if(matchNode.isMatcher){
            // If matcher matches node, add variable to response, else set to failure
            return ((Matcher)matchNode).matcherMatches(this) ?
                    response.addUnaryPossibility((Matcher) matchNode, this, scope) :
                    response.setMatch(false);
        }// GOING TO NEED TO CHANGE BECAUSE OF SCOPE CONSIDERATIONS **************************************************************************

        else{
            // If this node and pattern are the same type
            if(getType() == matchNode.getType()){
                switch (getNodeType()) {
                    // If nodes are leaf and are totally equal, return corresponding boolean
                    case Leaf:
                        if(sameTypeEquals(matchNode)){
                            response.addNonPatternMatch(this);
                            return response.setMatch(true);
                        }else{
                            return response.setMatch(false);
                        }
                    // If nodes are not commutative, check if each of their children are totally equal
                    case Binary:
                    case Unary:
                        Node[] nodeChildren = matchNode.getChildren();
                        Node[] children = getChildren();
                        for (int i = 0; i < getChildrenCount(); i++) {
                            if (!children[i].matches(nodeChildren[i], response, scope).checkValidity())
                                return response.setMatch(false);
                        }
                        return response;
                    // If nodes are commutative, do this crap
                    case Nary:
                        Queue<Node> patternList = new LinkedList<>(((Nary) matchNode).getClonedChildrenAsList());
                        ArrayList<Node> compareToList = ((Nary) this).getClonedChildrenAsList();

                        xx:
                        while (!patternList.isEmpty()) {
                            Node pattern = patternList.peek();

                            if(pattern.isMatcher){ // TODO: Ensure this method still words if mpattern doesn't match all nodes in compareToList
                                ArrayList<Node> matches = new ArrayList<>();

                                for (Node compare : compareToList) {
                                    if (((Matcher)pattern).matcherMatches(compare)) {
                                        matches.add(compare);
                                    }
                                }

                                if(matches.size() == 0)
                                    return response.setMatch(false);

                                //TODO: see if I can add way to use the checkValidity() here, otherwise, remove the check all together
                                // Use getEqualNodes to get original nodes with original ids
                                if(!response.addNaryPossibilities((Matcher)pattern, getEqualNodes(matches, getChildren()), this).checkValidity())
                                    return response;

                                patternList.remove();
                            }else {
                                boolean anyFound = false;
                                Scope newScope = new Scope();

                                for(int i=0;i<compareToList.size();i++){
//                                     Get state of response variable so we can revert back if no match was found
                                    // TODO: also have to clone nonPatternMatches list
                                    HashMap<Character, HashMap<Node, ArrayList<Node>>> responseMapClone =
                                            (HashMap<Character, HashMap<Node, ArrayList<Node>>>) response.getMap().clone();

                                    // Use getEqualNode to get original node with original id
                                    if (getEqualNode(compareToList.get(i),getChildren()).matches(pattern, response, newScope).checkValidity()) {
                                        if(pattern.getNodeType() == NodeType.Leaf) {
                                            compareToList.remove(i);
                                            patternList.remove();
                                            continue xx;
                                        }
                                        anyFound = true;
                                    }else {
//                                         Reset response variable
                                        response.setMap(responseMapClone);
                                        response.setMatch(true);
                                    }
                                }
                                if(!anyFound)
                                    return response.setMatch(false);
                                else
                                    patternList.remove();
                            }
                        }

                        return response;
                }
            }
        }

        return response.setMatch(false);
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
