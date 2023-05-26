package math;

import java.util.Arrays;
import java.util.List;

public class EquationFabric {
    private Equation cubeEq;
    private Equation sinEq;

    public EquationFabric() {
        MultiArgFunc tempFunc = x-> x[0]*x[0]*x[0] -(4 * x[0] * x[0]) + 3*x[0] + 1;
        MultiArgFunc tempFi = x -> x[0] - 0.3 * tempFunc.apply(x);
        this.cubeEq = new Equation(tempFunc,tempFi,"x^3 - 4*x^2 + 3*x + 1 = 0",1);
        MultiArgFunc tempFunc2 =  x -> Math.sin(x[0]) * 3 + x[0] + 1;
        this.sinEq = new Equation(tempFunc2, createSimpleFi(tempFunc2,0.25,0),"3 * sin(x) + x + 1 = 0",2);

    }

    public Equation getCubeEq() {
        return cubeEq;
    }

    public Equation getSinEq() {
        return sinEq;
    }


    //0.8 05
    public List<Equation> getFirstSystem(){
        MultiArgFunc f1 = x -> Math.sqrt(1- x[0] * x[0]);
        MultiArgFunc f2 = x-> Math.cbrt(x[0]);
        Equation e1 = new Equation(f1,"x^2  + y^2 - 1 = 0", 1);
        Equation e2 = new Equation(f2,"x^3 - y = 0", 2);
        return Arrays.asList(e1, e2);
    }


    //1.8 5.7
    public List<Equation> getSecondSystem(){
        MultiArgFunc f1 = x -> Math.sin(x[0]) * 3 + x[0] + 1;
        MultiArgFunc f2 = x-> Math.cbrt(x[0]);
        Equation t1 = new Equation(f1,"3 * sin(x) + x + 1 - y = 0 ",1);
        Equation t2 = new Equation(f2,"x^3 - y = 0",2);
        return Arrays.asList(t1,t2);
    }

    private MultiArgFunc createSimpleFi(MultiArgFunc origin, Double lambda, int ind) {
        return x -> x[ind] - lambda * origin.apply(x);
    }
}
