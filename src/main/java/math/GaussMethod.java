package math;

import java.util.List;

public interface GaussMethod {
    Integer chooseMainElementOfColumn(Matrix matrix, Integer column);
    void straightWay(Matrix matrix, Integer index);
    Solution calculate(Matrix matrix);
    Double convertToTriangle(Matrix matrix);
    List<Double> reverseWay(Matrix matrix);
}
