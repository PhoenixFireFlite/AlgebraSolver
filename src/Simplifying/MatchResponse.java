package Simplifying;

import Tree.Nodes.Matchers.Scope;
import Tree.Nodes.Matchers.Matcher;
import Tree.Nodes.NodeType;
import Tree.Nodes.Primitives.Node;

import java.util.*;

public class MatchResponse {

    private boolean match = true;

    private HashSet<Node> nonPatternMatches = new HashSet<>();
    private HashMap<Character, HashMap<Node, ArrayList<Node>>> variablePerms = new HashMap<>();
    private HashMap<Node, Set<Node>> extras = new HashMap<>();
    private HashMap<Character, HashMap<Node, ArrayList<Node>>> finals = new HashMap<>();

    public MatchResponse(){}

    public MatchResponse addUnaryPossibility(Matcher variable, Node possibility, Scope scope){
        char name = variable.getName();
        if(variablePerms.containsKey(name)){
            if(variablePerms.get(name).containsKey(scope)){
                variablePerms.get(name).get(scope).add(possibility);
            }else{
                variablePerms.get(name).put(scope, new ArrayList<>(Collections.singletonList(possibility)));
            }
        }else{
            variablePerms.put(name,
                    new HashMap<>(Collections.singletonMap(
                            scope,new ArrayList<>(Collections.singletonList(possibility)))));
        }

        return this;
    }

    public MatchResponse addNaryPossibilities(Matcher variable, ArrayList<Node> possibilities, Node parentNode){
        char name = variable.getName();
        if(variablePerms.containsKey(name)){
            if(variablePerms.get(name).containsKey(parentNode)){
                variablePerms.get(name).get(parentNode).addAll(possibilities);
            }else{
                variablePerms.get(name).put(parentNode, new ArrayList<>(possibilities));
            }
        }else{
            variablePerms.put(name,
                    new HashMap<>(Collections.singletonMap(
                            parentNode,new ArrayList<>(possibilities))));
        }

        return this;
    }

    private void addExtras(Node variable, ArrayList<Node> possibilities){
        if(extras.containsKey(variable)){
            extras.get(variable).addAll(possibilities);
        }else{
            extras.put(variable, new HashSet<>(possibilities));
        }
    }

    private void addFinals(Character key, Node parent, ArrayList<Node> values){
        HashMap<Node, ArrayList<Node>> h = new HashMap<>();
        h.put(parent, values);
        finals.put(key, h);
    }

    public void addNonPatternMatch(Node node){
        nonPatternMatches.add(node);
    }

    private <T,F> T getKey(HashMap<T, F> hashMap, int index){
        return (T) hashMap.keySet().toArray()[index];
    }

    public boolean hasPossibleSolution(){

        System.out.println("----- Reducing possible values");

        for(HashMap<Node,ArrayList<Node>> hash: variablePerms.values()){

            Node[] keyNodes = hash.keySet().toArray(new Node[0]);

            ArrayList<Node>[] valueNodes = hash.values().toArray(new ArrayList[0]);

            Node firstKey = keyNodes[0];                // Get first KEY node
            ArrayList<Node> firstValue = valueNodes[0]; // Get first VALUE node list

            for(int i=1;i<keyNodes.length;i++){ // | k:= a1 | a:= [a1,a2,a3,...] | k:= f(k,a[i+1]) | k
                ArrayList<Node> intersection = getIntersectionOfPossibilities(firstKey, firstValue, keyNodes[i], valueNodes[i]);

                if(intersection == null)
                    return false;

                firstKey = keyNodes[i];
                firstValue = intersection;
            }

            hash.clear();
            hash.put(firstKey, firstValue);
        }

        System.out.println("----- -----");

        System.out.println("PERMS: " + variablePerms);
        System.out.println("EXTRAS: " + extras + "\n");

        System.out.println("----- Checking for necessary matches");

        if(!checkForSolutions()) {
            System.out.println("No possible match!");
            return false;
        }

        System.out.println("----- -----");

        System.out.println("PERMS: " + variablePerms);
        System.out.println("EXTRAS: " + extras);
        System.out.println("FINALS: " + finals + "\n");

        if(variablePerms.size() == 0){
            if(extras.size() == 0)
                System.out.println("All solutions found!");
            return extras.size() == 0;
        }else{
            System.out.println("----- Checking for nary solutions");

//            (a+b)*(c+d)
//            (1+2+3+4)*(3+4+5)
//            [a,b]=[permSum(1,2,3,4)]

            System.out.println(variablePerms.get('a').equals(variablePerms.get('b')));

            return false;
        }
    }

    private boolean checkForSolutions(){
        HashMap<Node,ArrayList<Node>>[] values = variablePerms.values().toArray(new HashMap[0]);

        // Counter to keep track of removed nodes from variablePerms
        int removeCounter = 0;

        for(int i=0;i<values.length;i++){
            ArrayList<Node>[] nodeLists = values[i].values().toArray(new ArrayList[0]);

            // If nodeLists[0].size() == 1, then nodeLists.length == 1, therefore, only one possibility
            if(nodeLists[0].size() == 1){
                // Get key of vals[0]
                char varName = getKey(variablePerms, i-removeCounter);

                addFinals(varName, getKey(values[i], 0), nodeLists[0]);
                // Remove vals[0] from all other hashmaps. If that creates no solution for something, return false
                if(!discardNode(i-removeCounter, nodeLists[0].get(0)))
                    return false;

                variablePerms.remove(varName);

                removeCounter++;
            }
        }

        // If there is only one node left it has to have a match
        if(variablePerms.size() == 1){
            // Reset values list which should have one element now
            variablePerms.values().toArray(values);

            ArrayList<Node>[] nodeLists = values[0].values().toArray(new ArrayList[0]);

            char varName = getKey(variablePerms, 0);

            Node keyNode = getKey(values[0], 0);

            // If there are multiply nodes to be used and the node is not Nary, there is no possibility
            if(nodeLists[0].size() > 1 && keyNode.getNodeType() != NodeType.Nary)
                return false;

            addFinals(varName, keyNode, nodeLists[0]);

            for(int i=0;i<nodeLists[0].size();i++) {
                if (!discardNode(0, nodeLists[0].get(i)))
                    return false;
            }

            variablePerms.remove(varName);
        }

        return true;
    }

    private boolean discardNode(int exceptionIndex, Node node){
        int i = 0;
        for(HashMap<Node,ArrayList<Node>> hash: variablePerms.values()){
            if(i++ != exceptionIndex) {
                for (ArrayList<Node> val : hash.values()) {
                    if (val.contains(node)) {
                        val.remove(node);
                        if (val.size() == 0) {
                            System.out.println("No possible match for variable: ["+getKey(variablePerms, i-1)+"]\n");
                            return false;
                        }
                    }
                }
            }
        }

        extras.values().forEach(x -> x.remove(node));
        extras.values().forEach(x -> x.removeIf(this::shouldDiscardExpression));
        extras.values().removeIf(x -> x.size() == 0);

        return true;
    }

    private boolean nodeHasBeenMatched(Node node){
        for(HashMap<Node, ArrayList<Node>> vals: finals.values()){
            for(ArrayList<Node> v: vals.values()){
                if(v.contains(node)) {
                    return true;
                }
            }
        }
        return nonPatternMatches.contains(node);
    }

    private boolean shouldDiscardExpression(Node node){
        if(node.getNodeType() != NodeType.Nary){
            // If node is a leaf, it has to match
            if(node.getNodeType() == NodeType.Leaf)
                return nodeHasBeenMatched(node);
            // If node is not a leaf, it does not necessarily have to match
            else{
                if(nodeHasBeenMatched(node))
                    return true;
            }
        }
        for (Node n : node.getChildren()) {
            if (!shouldDiscardExpression(n))
                return false;
        }
        return true;
    }

    private ArrayList<Node> getIntersectionOfPossibilities(Node key1, ArrayList<Node> values1, Node key2, ArrayList<Node> values2){

//        System.out.println("START MATCHING\nKey - Value: "+key1+" - "+values1);
//        System.out.println("Key - Value: "+key2+" - "+values2+"\n");

        ArrayList<Node> list = new ArrayList<>();

        for(int o=0;o<2;o++) {
            xx:
            for (int i = 0; i < values1.size(); i++) {
                Node value = values1.get(i);

//            System.out.println("CV: "+value);

                for (int j = 0; j < values2.size(); j++) {
                    Node value2 = values2.get(j);

                    if (value.equalTo(value2)) {
                        list.add(value);
//                    System.out.println("Matched: "+value+" & "+value2);
                        values1.remove(value);
                        values2.remove(value2);
                        i--;
//                    System.out.println("List: "+list);
//                    System.out.println("Key Value: "+key1+" & "+values1);
//                    System.out.println("Key Value: "+key2+" & "+values2);
                        continue xx;
                    }
                }

                if (value.getNodeType() == NodeType.Nary && key2.getType() == value.getType()) {
                    int matches = 0;
//                System.out.println("Trying Nary match");
                    Node[] children = value.getChildren();
                    for (Node child : children) {
                        for (Node n2 : values2) {
                            if (child.equalTo(n2)) {
                                matches++;
                                break;
                            }
                        }
                    }
                    if (matches == children.length) {
//                    System.out.print("Succeeded and ");
                        for (Node child : children) {
                            for (Node n2 : values2) {
                                if (n2.equalTo(child)) {
                                    values2.remove(n2);
                                    break;
                                }
                            }
                        }
//                    System.out.println("matched: "+value);
                        list.add(value);
                        values1.remove(value);
                        i--;
//                    System.out.println("List: "+list);
//                    System.out.println("Key Value: "+key1+" & "+values1);
//                    System.out.println("Key Value: "+key2+" & "+values2);
                        continue;
                    }
                }
//                match = false;
//                break;
            }

            ArrayList<Node> permst = values1;
            values1 = values2;
            values2 = permst;

            Node nodet = key1;
            key1 = key2;
            key2 = nodet;
        }

//        if(values1.size() > 0 || values2.size() > 0)
//            match = false;

        if(list.size() == 0) {
            return null;
        }else {
            if (values1.size() > 0) {
                addExtras(key1, values1);
            }
            if (values2.size() > 0) {
                addExtras(key2, values2);
            }
        }

//        System.out.println(match);
//        System.out.println(list);
//        System.out.println("EXTRAS: "+extras+"\n");

        return list;
    }

    public void setMap(HashMap<Character, HashMap<Node, ArrayList<Node>>> map){variablePerms = map;}
    public MatchResponse setMatch(boolean stat){ match = stat;return this; }
    public boolean checkValidity(){ return match; }
    public HashMap<Character, HashMap<Node, ArrayList<Node>>> getMap(){ return variablePerms; }
    public HashMap<Character, HashMap<Node, ArrayList<Node>>> getFinals(){ return finals; }

}

