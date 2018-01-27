package Simplifying;

import Tree.Nodes.Primitives.Node;

import java.util.*;

public class MatchResponse {

    private HashMap<Long, Node> seenNodes = new HashMap<>();
    private HashMap<Character, ArrayList<Long>> variablePermutations = new HashMap<>();

    private boolean match = true;

    public MatchResponse(){}

    public MatchResponse addPossibility(char varName, Node node){
        long nodeID = node.getID();

        for(Node n: seenNodes.values()) {
            if (n.equalTo(node)) {
                nodeID = n.getID();
                node = n;
                break;
            }
        }

        seenNodes.put(nodeID, node);

        if(!variablePermutations.containsKey(varName)) {
            variablePermutations.put(varName, new ArrayList<>(Collections.singletonList(nodeID)));
        }else {
            ArrayList<Long> intersect = intersection(variablePermutations.get(varName), new ArrayList<>(Collections.singletonList(nodeID)));

            variablePermutations.put(varName, intersect);

            if (intersect.size() == 0)
                return setMatch(false);

            if (intersect.size() == 1) {
                removeIDFromAllExcept(nodeID, varName);
            }
        }

        return this;
    }

    public MatchResponse addPossibility(char varName, ArrayList<Node> nodes){
        addSeenNodes(nodes);

        ArrayList<Long> newIDs = new ArrayList<>();
        for (Node n : nodes) {
            if(seenNodes.containsKey(n.getID()))
                newIDs.add(n.getID());
        }

        if(!variablePermutations.containsKey(varName)) {
            variablePermutations.put(varName, newIDs);
        }else {
            ArrayList<Long> intersect = getIntersectionOfIDs(varName, newIDs);

            variablePermutations.put(varName, intersect);

            if (intersect.size() == 0)
                return setMatch(false);

            if (intersect.size() == 1) {
                long decidedID = intersect.get(0);
                removeIDFromAllExcept(decidedID, varName);
            }
        }

        return this;
    }

    private void removeIDFromAllExcept(long id, char excludedVarName){
        for(char name: variablePermutations.keySet()){
            if(name != excludedVarName){
                ArrayList<Long> ids = variablePermutations.get(name);
                for(int i=0;i<ids.size();i++){
                    if(ids.get(i) == id) {
                        ids.remove(i);
                        break;
                    }
                }
            }
        }
    }

    private ArrayList<Long> getIntersectionOfIDs(char varName, ArrayList<Long> newIDs){
        ArrayList<Long> oldIDs = variablePermutations.get(varName);
        return intersection(oldIDs,newIDs);
    }

    private void addSeenNodes(ArrayList<Node> nodes){
        xx:
        for (Node node : nodes) {
            for (Node n : seenNodes.values())
                if (n.equalTo(node)) {
                    seenNodes.put(n.getID(), n);
//                    nodes.set(i, n);
                    continue xx;
                }
            seenNodes.put(node.getID(), node);
        }
    }

    private <T> ArrayList<T> intersection(ArrayList<T> a, ArrayList<T> b){
        ArrayList<T> intersect = new ArrayList<>();

        if(a.size() < b.size()){
            for(T x: a)
                for(T y: b)
                    if(x == y) intersect.add(x);
        }else{
            for(T x: b)
                for(T y: a)
                    if(x == y) intersect.add(x);
        }
        return intersect;
    }

    public MatchResponse setMatch(boolean stat){ match = stat;return this; }
    public boolean getValidity(){ return match; }
    public HashMap<Character, ArrayList<Node>> getMap(){
        HashMap<Character, ArrayList<Node>> newMap = new HashMap<>();
        for(char c: variablePermutations.keySet()){
            ArrayList<Node> nodes = new ArrayList<>();
            for(long id: variablePermutations.get(c)){
                nodes.add(seenNodes.get(id));
            }
            newMap.put(c, nodes);
        }
        return newMap;
    }

}
//[1,3,6,7]
//
// 1 == 2
// 6 == 1
// [] <-- a=[1,2,3]
// [] <-- a=[1,1,3]
// [] <-- a=[1,3]
// [ a=[1,3] ] <-- b=[3,1,7]
// [ a=[1,3] b=[3,1,7] ]
// [ a=[1,3] b=[3,1,7] ] <-- b=[6]
// [ a=[1,3] b=[3,1,7] ] <-- b=[1]
// [ a=[3] b=[1] ] COMPLETE