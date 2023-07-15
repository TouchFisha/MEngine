package touchfish.unit.inter;

/**
 * 委托
 * (IN) -> { return OUT; }
 * @author  Touchfish
 */
public interface IDelegateInOut<IN,OUT> {
    OUT invoke(IN in);
}