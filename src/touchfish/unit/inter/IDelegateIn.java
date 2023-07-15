package touchfish.unit.inter;

/**
 * 委托
 * (IN) -> { ... }
 * @author  Touchfish
 */
public interface IDelegateIn<IN> {
    void invoke(IN in);
}