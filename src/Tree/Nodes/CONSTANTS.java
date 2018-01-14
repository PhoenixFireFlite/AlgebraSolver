package Tree.Nodes;

import java.math.MathContext;
import java.math.RoundingMode;

public class CONSTANTS {

    public final static int BigDecimalRoundingPlaces = 20;
    public final static MathContext RoundingPlacesMathContext = new MathContext(BigDecimalRoundingPlaces);

    public final static RoundingMode BigDecimalRoundingMethod = RoundingMode.HALF_UP;

    private final static int MathConstantsRoundingPlaces = 20;
    public final static MathContext MathConstantsMathContext = new MathContext(MathConstantsRoundingPlaces);

    private final static int TrigFunctionsPrecision = 21;
    public final static MathContext TrigFunctionsMathContext = new MathContext(TrigFunctionsPrecision);
}
