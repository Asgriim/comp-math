package math;

import java.util.List;

public record Solution(Double det, List<Double> answer, List<Double> residual, Matrix triangleMtx, boolean exist) {
}
