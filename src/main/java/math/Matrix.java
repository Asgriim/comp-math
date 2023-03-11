package math;

import java.util.List;

public interface Matrix {
    void setElem(Integer row, Integer column, Double value);
    Integer getWidth();
    Integer getHeight();
    void normalize(Integer rowInd, Double value);
    Double getElem(Integer row, Integer column);
    List<Double> getRow(Integer row);
    List<List<Double>> getMtx();
    void swapRows(Integer i, Integer j);

    Double getMaxElem();

    void subRows(Integer i, Integer j, Double multiplier);
}
