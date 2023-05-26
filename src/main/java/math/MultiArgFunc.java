package math;

@FunctionalInterface
public interface MultiArgFunc {
    Double apply(Double ...arg);
}
