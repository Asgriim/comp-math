import math.Equation;
import math.EquationFabric;
import math.EquationSolver;
import math.Solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class InputManager {

    void  run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        int option;
        double a, b;
        EquationFabric fabric = new EquationFabric();
        Equation eq1 = fabric.getCubeEq();
        Equation eq2 = fabric.getSinEq();
        List<Equation> sys = fabric.getFirstSystem();
        List<Equation> sys2 = fabric.getSecondSystem();
        while (true){
            writeL("Choose action: ");
            writeL("1 - solve an equation");
            writeL("2 - solve system");
            writeSign();
            input = reader.readLine().strip();
            try {
                option = Integer.parseInt(input);
                if(option == 1){
                    writeL("Choose equation to solve");
                    writeL("1) " + eq1.getStringFunction());
                    writeL("2) " + eq2.getStringFunction());
                    writeSign();
                    option = Integer.parseInt(reader.readLine().strip());
                    writeL("Enter a");
                    writeSign();
                    a = Double.parseDouble(reader.readLine().strip());
                    writeL("Enter b");
                    writeSign();
                    b = Double.parseDouble(reader.readLine().strip());
                    switch (option){
                        case 1 -> {
                            writeSolutionOfSingle(eq1,a,b);
                        }
                        case 2 -> {
                            writeSolutionOfSingle(eq2,a,b);
                        }
                    }
                }
                if (option == 2){
                    writeL("Choose system to solve ");
                    writeL("1)");
                    writeSys(sys);
                    writeL("2)");
                    writeSys(sys2);
                    writeSign();
                    option = Integer.parseInt(reader.readLine().strip());
                    switch (option){
                        case 1 -> {
                            writeSystemSol(EquationSolver.solveSystemIterationMethod(sys.get(0),sys.get(1),0.8,0.5));
                        }
                        case 2 ->{
                            writeSystemSol(EquationSolver.solveSystemIterationMethod(sys2.get(0),sys2.get(1),1.8,5.7));
                        }
                    }
                }
            }catch (NumberFormatException e){
                continue;
            }
        }

    }


    void writeSystemSol(Solution s){
        writeL("x = " +  s.answers().get(0));
        writeL("y = " +  s.answers().get(1));
    }

    void writeSys(List<Equation> sys){
        writeL("\t" + sys.get(0).getStringFunction());
        writeL("\t" + sys.get(1).getStringFunction());
    }

    void writeSolutionOfSingle(Equation equation,double a, double b){
        Solution s1 = EquationSolver.solveBisectionMethod(equation,a,b);
        if(!s1.exist()){
            writeL("Bisection method: solution not exist");
        }
        else {
            writeL("Bisection method: " + s1.answers().get(0));
        }

        Solution s2 = EquationSolver.solveFixedPointIteration(equation, a, b);

        writeL("Iteration method: " + s2.answers().get(0));
    }

    void  writeL(String s){
        System.out.println(s);
    }

    void writeSign(){
        System.out.print(">");
    }

    void writeS(String s){
        System.out.print(s);
    }
}
