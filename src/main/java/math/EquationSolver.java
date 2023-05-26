package math;

import java.util.Arrays;
import java.util.Collections;

public class EquationSolver {
    static int iterationLimit = 10000;
    static double epsilon = 0.00000001;

    public static Solution solveBisectionMethod(Equation equation, double a, double b) {
        if((equation.apply(a) * equation.apply(b)) >= 0 ){
            return new Solution(false, null);
        }
        int count = 0;
        double mid = 0;
        double result = 0;
        while (count < iterationLimit){
            mid = (a + b) / 2;
            result = equation.apply(mid);
            if (result == 0D) {
                return new Solution(true, Collections.singletonList(mid));
            }
            if((equation.apply(mid) * equation.apply(a)) < 0) {
                b = mid;
            }
            else {
                a = mid;
            }
            count++;
        }

        return new Solution(true, Collections.singletonList(mid));
    }

    public static Solution solveFixedPointIteration(Equation equation, double a, double b){

        double root = (a + b) / 2;
        double lastRoot = root;
        int count = 0;
        while (count < iterationLimit){
            root = equation.applyFi(lastRoot);
            if(equation.apply(root) == 0D){
                return new Solution(true,Collections.singletonList(root));
            }
            if (Math.abs(lastRoot - root) < epsilon){
                return new Solution(true,Collections.singletonList(root));
            }
            lastRoot = root;
            count++;
        }
        if(root < a || root > b){
            return new Solution(false, null);
        }
        return new Solution(true, Collections.singletonList(root));
    }


    public static Solution solveSystemIterationMethod(Equation equation1, Equation equation2, Double init1, Double init2){
        double root1 = init1;
        double root2 = init2;
        double last1 = root1;
        double last2 = root2;
        int count = 0;
        while (count < iterationLimit){
            root1 = equation1.apply(last2);
            root2 = equation2.apply(last1);
            if(Math.abs(root1 - last1) < epsilon || Math.abs(root2 - last2) < epsilon){
                return new Solution(true, Arrays.asList(root2,root1));
            }
            last1 = root1;
            last2 = root2;
            count++;

        }
        return new Solution(false, null);
    }
}
