package io;

import math.Matrix;
import math.Solution;

import java.util.List;
import java.util.stream.Collectors;

public class SolutionFormatter {
    static Integer offset = 3;
    public static String format(Solution solution, Integer roundLim) {
        String s = "";
        if (!solution.exist()){
            return "Solution not exist\n";
        }
        s += "Determinant = " + String.format("%."+roundLim+"f",solution.det()) + "\n";
        s += "Answer matrix:\n";
        s += formatAnswer(solution.answer(), roundLim) + "\n";
        s += "Residual matrix:\n";
        s += formatResidual(solution.residual(), roundLim) + "\n";
        s += "Triangle matrix:\n";
        s += formatMatrix(solution.triangleMtx(), roundLim);

        return s;
    }

    public static String formatMatrix(Matrix matrix, Integer roundLim) {
        String cellPattern = "%" + (getLengthOfLongestElem(matrix, roundLim) + offset) + "s";
        String pattern = getPattern(roundLim);
        return matrix.getMtx().stream()
                .map(l ->
                        "| " + l.stream().limit(l.size() - 1).map(x -> String.format(cellPattern,String.format(pattern, x))).collect(Collectors.joining(" ")) + " |"
                        + " " + String.format(cellPattern,String.format(pattern,l.get(l.size() - 1))) + " |"
                )
                .collect(Collectors.joining("\n"));
    }

    public static String formatAnswer(List<Double> answer, Integer roundLim) {
        String cellPattern = "%" + -(offset + roundLim) + "s";
//        String pattern = getPatternFromList(answer, roundLim);
        String pattern = getPattern(roundLim);
        String out = "";
        for (int i = 0; i < answer.size(); i++){
            out += String.format(cellPattern,"x" + (i + 1) + " = " + String.format(pattern, answer.get(i))) + "\n";
        }
        return out;
//        return answer.stream().map(x -> String.format(cellPattern,"x" + (answer.indexOf(x) + 1) + " = " + String.format(pattern, x))).collect(Collectors.joining("\n"));
    }


    public static String formatResidual(List<Double> residual, Integer roundLim){
        String cellPattern = "%" + -(offset + roundLim) + "s";
        String out = "";
        for (int i = 0; i < residual.size(); i++){
            out += String.format(cellPattern,"r" + (i + 1) + " = " + residual.get(i)) + "\n";
        }
        return out;
    }

    static String getPattern(Integer floatLim) {
//        return "%" + decLen + "." + floatLim + "f";
        return "%." + floatLim + "f";

    }

//    static String getPatternFromList(List<Double> list, Integer roundLim) {
//        return getPattern(roundLim);
//    }

    static int getLengthOfLongestElem(Matrix matrix, Integer roundLim){
        List<String> t;
        String max = "";
        for(List<Double> row : matrix.getMtx()){
            t = row.stream().map(x -> String.format(getPattern(roundLim),x)).toList();
            for (String s : t){
                if (s.length() > max.length()){
                    max = s;
                }
            }
        }
        return max.length();
    }

}
