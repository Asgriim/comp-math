import generate.MatrixGenerator;
import io.InputManager;
import io.MatrixReader;
import io.SolutionFormatter;
import math.GaussMethod;
import math.GaussMethodImpl;
import math.Matrix;
import math.MatrixArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        int precision = 3;
        if (System.getProperty("precision") != null){
            precision = Integer.parseInt(System.getProperty("precision"));
        }
        InputManager inputManager = new InputManager();
        inputManager.run(precision);
    }
}
