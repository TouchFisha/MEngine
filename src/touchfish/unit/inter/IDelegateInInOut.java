package touchfish.unit.inter;

/**
 * 委托
 * (IN0,IN1) -> { return OUT; }
 * @author  Touchfish
 */
public interface IDelegateInInOut<IN0,IN1,OUT> {
    OUT invoke(IN0 a,IN1 b);
}