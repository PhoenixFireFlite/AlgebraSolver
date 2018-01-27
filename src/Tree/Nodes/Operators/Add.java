package Tree.Nodes.Operators;

import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Nary;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public class Add extends Nary{

    public Add(Node... nodes){
        super(nodes);
    }
    public Add(Node[]... nodess) {super(nodess);}
    public Add(Node[] nodeArray, Node node) {super(nodeArray,node);}


    public BigDecimal getDecimalValue() {
        BigDecimal sum = BigDecimal.ZERO;
        for(Node node: getChildren()){
            BigDecimal val = node.getDecimalValue();
            if(val == null)
                return null;
            sum = sum.add(val);
        }
        return sum;
    }

    public Fraction getFractionalValue(){
        Fraction sum = new Fraction(0);
        for(Node node: getChildren()){
            Fraction val = node.getFractionalValue();
            if(val == null)
                return null;
            sum = sum.add(val);
        }
        return sum;
    }

    public Node getCopy() {
        Node[] children = getChildren();
        for(int i=0;i<children.length;i++){
            children[i] = children[i].getCopy();
        }
        return new Add(children);
    }

    public String getTypeString(){
        return "+";
    }

    public Type getType(){
        return Type.Add;
    }
}
