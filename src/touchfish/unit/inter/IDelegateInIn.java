package touchfish.unit.inter;

/**
 * 委托
 * (IN0,IN1) -> { ... }
 * @author  Touchfish
 */
public interface IDelegateInIn<IN0,IN1> {
    void invoke(IN0 a,IN1 b);
}
