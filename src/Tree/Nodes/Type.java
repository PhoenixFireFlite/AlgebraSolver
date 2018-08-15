package Tree.Nodes;

public enum Type {
    // Operators
    Add,
    Divide,
    Multiply,
    Power,
    //Factorial,

    // Operands
    Fraction,
    Variable,

    // Constants
    Pi,
    E,
    I,

    // Trig Functions
    Sin,
    Cos,
    Tan,
    Csc,
    Sec,
    Cot,

    // Other Functions
    Sqrt,
    Root,

    // Matchers (ALWAYS LAST)
    Scope,
    Any,
}
