package io;

import math.Matrix;
import math.MatrixArrayList;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatrixReader implements AutoCloseable {
    BufferedReader reader;

    public MatrixReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public Matrix readMatrix() throws IOException {
        Integer size;
        try {
            size = Integer.parseInt(reader.readLine().strip());
        } catch (NumberFormatException e) {
            throw new IOException("Error: not a integer number");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(size < 2 || size > 20){
            throw new IOException("Size can be only from 2 to 20");
        }

        List<List<Double>> mtx = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            String row = reader.readLine();
            List<String> strings = Arrays.stream(row.strip().split("\s+")).toList();

            if (strings.size() != size + 1) {
                throw new IOException("Size mismatch");
            }

            List<Double> rowList = new ArrayList<>(size + 1);
            for (int j = 0; j < size + 1; j++){
                try {
                    rowList.add(Double.parseDouble(strings.get(j).replace(",",".")));
                } catch (NumberFormatException e) {
                    throw new IOException("Not a number in " + (i + 1) + " row");
                }
            }
            mtx.add(rowList);
        }

        return new MatrixArrayList(mtx);
    }

    public Matrix readMatrix( Integer size) throws IOException {

        List<List<Double>> mtx = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            String row = reader.readLine();
            List<String> strings = Arrays.stream(row.strip().split("\s+")).toList();

            if (strings.size() != size + 1) {
                throw new IOException("Size mismatch");
            }

            List<Double> rowList = new ArrayList<>(size + 1);
            for (int j = 0; j < size + 1; j++){
                try {
                    rowList.add(Double.parseDouble(strings.get(j).replace(",",".")));
                } catch (NumberFormatException e) {
                    throw new IOException("Not a number in " + (i + 1) + " row");
                }
            }
            mtx.add(rowList);
        }

        return new MatrixArrayList(mtx);
    }



    @Override
    public void close() throws Exception {
        reader.close();
    }
}
