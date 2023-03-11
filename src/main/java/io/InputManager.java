package io;

import generate.MatrixGenerator;
import math.GaussMethodImpl;
import math.Matrix;
import math.Solution;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class InputManager {

    String availableOptions =
            """
            \tconsole - read matrix from console input
            \tfile - read matrix from file
            \trandom - generate matrix
            \texit - exit program
            """;

    public void run(Integer roundLim) throws IOException {
        BufferedWriter consoleWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        consoleWriter.write("Choose method to read matrix from:");
        consoleWriter.newLine();
        consoleWriter.write(availableOptions);
//        consoleWriter.newLine();

        consoleWriter.flush();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        GaussMethodImpl gaussMethod = new GaussMethodImpl();
        while (true){
            consoleWriter.write("Enter option: ");
            consoleWriter.flush();
            String input = consoleReader.readLine().strip().toLowerCase();
            switch (input) {
                case "console" -> {
                    consoleWriter.write("Enter size of matrix (one integer number): ");
                    consoleWriter.flush();
                    try {
                        MatrixReader matrixReader = new MatrixReader(consoleReader);
                        Integer size = Integer.parseInt(consoleReader.readLine().strip());
                        if(size < 2 || size > 20){
                            consoleWriter.write("Size can be only from 2 to 20");
                            consoleWriter.newLine();
                            consoleWriter.flush();
                            continue;
                        }
                        consoleWriter.write("Enter matrix: ");
                        consoleWriter.newLine();
                        consoleWriter.flush();
                        Matrix matrix = matrixReader.readMatrix(size);
                        Solution solution = gaussMethod.calculate(matrix);
                        writeSolution(consoleWriter, solution, roundLim);
                    } catch (NumberFormatException e) {
                        consoleWriter.write("Error: not a integer number");
                        consoleWriter.newLine();
                        consoleWriter.flush();
                    } catch (IOException e) {
                        consoleWriter.write(e.getMessage());
                        consoleWriter.newLine();
                        consoleWriter.flush();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    consoleReader = new BufferedReader(new InputStreamReader(System.in));
                }
                case "file" -> {
                    System.out.println("Enter file name or path");
                    String fileName = consoleReader.readLine().strip();


                    if(!Files.exists(Path.of(fileName), LinkOption.NOFOLLOW_LINKS)){
                        consoleWriter.write("File not found");
                        consoleWriter.newLine();
                        consoleWriter.flush();
                        continue;
                    }
                    FileReader fileReader = new FileReader(fileName);
                    try (MatrixReader matrixReader = new MatrixReader(fileReader)){
                        Matrix matrix = matrixReader.readMatrix();
                        Solution solution = gaussMethod.calculate(matrix);
                        writeSolution(consoleWriter, solution, roundLim);
                    }catch (IOException e){
                        consoleWriter.write(e.getMessage());
                        consoleWriter.newLine();
                        consoleWriter.flush();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case "random" -> {
                    consoleWriter.write("Enter size of matrix (one integer number): ");
                    consoleWriter.flush();
                    try {
                        Integer size = Integer.parseInt(consoleReader.readLine().strip());
                        if(size < 2 || size > 20){
                            consoleWriter.write("Size can be only from 2 to 20");
                            consoleWriter.newLine();
                            consoleWriter.flush();
                            continue;
                        }
                        consoleWriter.write("Generated matrix:");
                        consoleWriter.newLine();
                        consoleWriter.flush();
                        Matrix matrix = MatrixGenerator.generate(size,-20D, 20D);
                        consoleWriter.write(SolutionFormatter.formatMatrix(matrix,roundLim));
                        consoleWriter.newLine();
                        consoleWriter.flush();
                        Solution solution = gaussMethod.calculate(matrix);
                        writeSolution(consoleWriter, solution, roundLim);
                    } catch (NumberFormatException e) {
                        consoleWriter.write("Error: not a integer number");
                        consoleWriter.newLine();
                        consoleWriter.flush();
                    }
                }
                case "exit" -> {
                    System.exit(0);
                }
                default -> {
                    consoleWriter.write("No such option");
                    consoleWriter.newLine();
                    consoleWriter.flush();
                }
            }
        }
    }

    void writeSolution(BufferedWriter writer, Solution solution, Integer roundLim) throws IOException {
        writer.write(SolutionFormatter.format(solution,roundLim));
        writer.newLine();
        writer.flush();
    }
}
