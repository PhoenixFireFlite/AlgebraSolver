package Tree.Nodes.Operators;

import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Nary;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;
import Tree.Nodes.NodeType;

import java.math.BigDecimal;

public class Multiply extends Nary{

    public Multiply(){}
    public Multiply(Node... nodes){
        super(nodes);
    }
    public Multiply(Node[]... nodess) {super(nodess);}
    public Multiply(Node[] nodeArray, Node node) {super(nodeArray,node);}

    public BigDecimal getDecimalValue() {
        BigDecimal product = BigDecimal.ONE;
        for(Node node: getChildren()){
            BigDecimal val = node.getDecimalValue();
            if(val == null)
                return null;
            product = product.multiply(val);
        }
        return product;
    }

    public Fraction getFractionalValue(){
        Fraction sum = new Fraction(1);
        for(Node node: getChildren()){
            Fraction val = node.getFractionalValue();
            if(val == null)
                return null;
            sum = sum.multiply(val);
        }
        return sum;
    }

    public Node getEmptyCopy() {
        return new Multiply();
    }

    public String getTypeString(){
        return "*";
    }

    public Type getType(){
        return Type.Multiply;
    }
}
