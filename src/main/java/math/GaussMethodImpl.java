package math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GaussMethodImpl implements GaussMethod{
    @Override
    public Integer chooseMainElementOfColumn(Matrix matrix, Integer column) {
        Double lastMax = Double.NEGATIVE_INFINITY;
        Integer rowInd = 0;
        List<List<Double>> mtx = matrix.getMtx();
        for(int i = column; i < matrix.getHeight(); i++){
            Double x = mtx.get(i).get(column);
            if (x > lastMax){
                lastMax = x;
                rowInd = i;
            }
        }
        return rowInd;
    }

    @Override
    public void straightWay(Matrix matrix, Integer index) {
        List<Double> initialRow = matrix.getRow(index);
        Double mainElem = initialRow.get(index);
        double modifier = 1D;
        for(int i = index + 1; i < matrix.getHeight(); i++){
            List<Double> temp = matrix.getMtx().get(i);
            modifier = - matrix.getElem(i,index) / mainElem;

            if(modifier == 0D){
                continue;
            }

            for (int j = index + 1; j < matrix.getWidth(); j++){
                double n = temp.get(j) + (modifier * (initialRow.get(j)));
                temp.set(j,n);
            }

            temp.set(index,0D);
        }
    }

    @Override
    public Solution calculate(Matrix matrix) {
        Matrix mtx = new MatrixArrayList(matrix);
        double det = convertToTriangle(mtx);

        if (det == 0){
            return new Solution(det,null,null,mtx, false);
        }

        List<Double> answer = reverseWay(mtx);
        List<Double> residual = calculateResidual(mtx,answer);
        return new Solution(det,answer,residual,mtx,true);
    }

    @Override
    public List<Double> reverseWay(Matrix matrix) {
        List<Double> answer = new ArrayList<>(Collections.nCopies(matrix.getHeight(),0D));
        answer.set(matrix.getHeight() - 1, matrix.getElem(matrix.getHeight() - 1, matrix.getWidth() - 1) / matrix.getElem(matrix.getHeight() - 1, matrix.getWidth() - 2));

        for (int i = matrix.getHeight() - 2; i >= 0; --i){
            Double t = 0D;
            for (int j = i +1; j < matrix.getWidth() - 1; j++){
                t += matrix.getElem(i,j) * answer.get(j);
            }
            t = (matrix.getElem(i, matrix.getWidth() - 1) - t) / matrix.getElem(i,i);
            answer.set(i,t);
        }
        return answer;
    }

    @Override
    public Double convertToTriangle(Matrix matrix) {
        Integer mainElemInd;
        Double det = 1D;
        int sign = 1;
        for (int i = 0; i < matrix.getHeight() - 1; i++){
            mainElemInd = chooseMainElementOfColumn(matrix,i);
            if (mainElemInd != i){
                matrix.swapRows(i,mainElemInd);
                sign *= -1;
            }
            det *= matrix.getElem(i,i);
            straightWay(matrix,i);
        }
        det *= matrix.getElem(matrix.getHeight() - 1,matrix.getWidth() - 2);

        return det * sign;
    }

    List<Double> calculateResidual(Matrix matrix, List<Double> answer){
        List<Double> residual = new ArrayList<>();

        for (int i = 0; i < matrix.getHeight(); i++){
            double temp = 0D;

            for (int j = 0; j < matrix.getWidth() - 1; j++){
                temp += matrix.getElem(i,j) * answer.get(j);
            }
            Double a = matrix.getElem(i,matrix.getWidth() - 1);
            residual.add(Math.abs(a - temp));
        }
        return residual;
    }
}
