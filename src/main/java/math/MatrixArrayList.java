package math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixArrayList implements Matrix {
    final List<List<Double>> mtx;
    final Integer width;
    final Integer height;

    public MatrixArrayList(Matrix matrix) {
        this.width = matrix.getWidth();
        this.height = matrix.getHeight();
        this.mtx = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            mtx.add(new ArrayList<>(matrix.getRow(i)));
        }
    }

    public MatrixArrayList(Integer height, Integer width) {
        this.width = width;
        this.height = height;
        mtx = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            mtx.add(new ArrayList<>(Collections.nCopies(width, 0.0)));
        }
    }

    public MatrixArrayList(List<List<Double>> mtx) {
        this.mtx = mtx;
        this.height = mtx.size();
        this.width = mtx.get(0).size();
    }

    @Override
    public void setElem(Integer row, Integer column, Double value) {
        mtx.get(row).set(column, value);
    }

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public void normalize(Integer rowInd, Double value) {
        if (value == 0) {
            return;
        }
        mtx.set(rowInd, mtx.get(rowInd).stream().map(x -> {if (x != 0D) {return x / value;} else return x;}).toList());
    }

    @Override
    public Double getElem(Integer row, Integer column) {
        return mtx.get(row).get(column);
    }

    @Override
    public List<Double> getRow(Integer row) {
        return mtx.get(row);
    }

    @Override
    public void swapRows(Integer i, Integer j) {
        List<Double> temp = mtx.set(j, mtx.get(i));
        mtx.set(i, temp);
    }

    @Override
    public Double getMaxElem() {
        Double max = Double.NEGATIVE_INFINITY;
        Double t;
        for (List<Double> row : mtx){
            t = Collections.max(row);
            if (t > max){
                max = t;
            }
        }
        return max;
    }

    @Override
    public void subRows(Integer i, Integer j, Double multiplier) {
        List<Double> row = mtx.get(i);
        List<Double> row2 = mtx.get(j);
        for (int k = 0; k < width; k++){
            row.set(k, (row.get(k) - multiplier * row2.get(k)));
        }
    }

    public List<List<Double>> getMtx() {
        return mtx;
    }

    @Override
    public String toString() {
        return "MatrixArrayList{" +
                "mtx=" + mtx +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
