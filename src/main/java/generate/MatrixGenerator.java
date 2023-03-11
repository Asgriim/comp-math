package generate;

import math.Matrix;
import math.MatrixArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MatrixGenerator {
    public static Matrix generate(Integer size, Double min, Double max) {
        Random random = new Random();
        List<List<Double>> mtx = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            List<Double> row = new ArrayList<>(size + 1);
            mtx.add(random.doubles(size + 1, min, max).boxed().toList());
        }
        return new MatrixArrayList(mtx);
    }
}
