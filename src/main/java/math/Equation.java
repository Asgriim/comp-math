package math;

import java.util.function.Function;

public class Equation {
//    Function<Double,Double> function;
    MultiArgFunc function;
    MultiArgFunc fi;
    String stringFunction;
    int id;

    public Equation(MultiArgFunc function) {
        this.function = function;
    }

    public Equation(MultiArgFunc function, String stringFunction, int id) {
        this.function = function;
        this.stringFunction = stringFunction;
        this.id = id;
    }

    public Equation(MultiArgFunc function, MultiArgFunc fi, String stringFunction, int id) {
        this.function = function;
        this.fi = fi;
        this.stringFunction = stringFunction;
        this.id = id;
    }

    public Double applyFi(Double ...x){
        return fi.apply(x);
    }

    public String getStringFunction() {
        return stringFunction;
    }

    public int getId() {
        return id;
    }

    public Double apply(Double ...x) {
        return function.apply(x);
    }

    @Override
    public String toString() {
        return "Equation{" +
                "function=" + function +
                ", stringFunction='" + stringFunction + '\'' +
                ", id=" + id +
                '}';
    }
}
