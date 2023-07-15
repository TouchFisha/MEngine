package touchfish.unit.inter;

/**
 * 委托
 * () -> { return OUT; }
 * @author  Touchfish
 */
public interface IDelegateOut<OUT> {
    OUT invoke();
}