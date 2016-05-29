import java.io.FileNotFoundException;
import java.text.DecimalFormat;

/**
 * Created by Юля on 28.05.2016.
 */
public class LevelMethod {

    public static void main(String[] args) {
        Matrix matrix = new Matrix();
        try {
            matrix.readFromFile("./matrix2.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        matrix.printMatrix();

        LevelMethod levelMethod = new LevelMethod();
        levelMethod.countValuesSecondRealisation(matrix.getMatrix(), matrix.getHeight(), matrix.getWidth());
    }

    private final float e = 0.001f;

    private float maxValue;

    private float[][] copy(float[][] matrix, int height, int width) {
        float[][] newMatrix = new float[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    public void countValuesfirstRealisation(float[][] matrix1, int height, int width) {
        float[][] matrix = copy(matrix1, height, width);
        float[] vectorPrevious = inicializeInitialApproximation(height);

        int itterationNumber = 0;
        float[] values = new float[500];
        while (true) {
            itterationNumber++;
            System.out.println("*************************************************");
            System.out.println("Number of itteration: " + itterationNumber);

            float[] vectorCurrent = multiplyMatrixAndVector(matrix, height, width, vectorPrevious);

            values[itterationNumber] = vectorCurrent[0] / vectorPrevious[0];
            float value1 = values[itterationNumber - 1];
            float value2 = values[itterationNumber];
            if (itterationNumber > 1 && checkIfOperationMustBeStopped(value1, value2)) {
                System.out.println("Maximum value is: " + value2);
                maxValue = value2;
                float[] vector = findVector(matrix, width, height);
                System.out.println("Vector: " + printArray(vector));
                break;
            }
            vectorPrevious = vectorCurrent;
            System.out.println("Value : " + values[itterationNumber]);
        }

    }

    public void countValuesSecondRealisation(float[][] matrix1, int height, int width) {
        float[][] matrix = copy(matrix1, height, width);
        float[] vectorPrevious = inicializeInitialApproximation(height);

        int itterationNumber = 0;
        float[] values = new float[500];
        while (true) {
            itterationNumber++;
            System.out.println("*************************************************");
            System.out.println("Number of itteration: " + itterationNumber);

            float[] vectorCurrent = multiplyMatrixAndVector(matrix, height, width, vectorPrevious);


            for (int i = 0; i < height; i++) {
                values[itterationNumber] += vectorCurrent[i] * vectorPrevious[i];
            }
            float[] NormingVector = new float[height];

            float normOfCurrentVector = 0;

            for (int i = 0; i < height; i++) {
                normOfCurrentVector += (float) Math.pow(vectorCurrent[i], 2);
            }
            normOfCurrentVector = (float) Math.sqrt(normOfCurrentVector);

            for (int i = 0; i < height; i++) {
                NormingVector[i] = vectorCurrent[i] / normOfCurrentVector;
            }

            float value1 = values[itterationNumber - 1];
            float value2 = values[itterationNumber];
            if (itterationNumber > 1 && checkIfOperationMustBeStopped(value1, value2)) {
                System.out.println("Maximum value is: " + value2);
                maxValue = value2;
                float[] vector = findVector(matrix, width, height);
                System.out.println("Vector: " + printArray(vector));
                break;
            }
            vectorPrevious = NormingVector;
            System.out.println("Value : " + values[itterationNumber]);

        }

    }


    private float[] findVector(float[][] matrix, int width, int height) {
        float[][] matrixForVectorSolution = new float[height][width + 1];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width + 1; j++) {
                if (i == j) {
                    matrixForVectorSolution[i][j] = matrix[i][j] - maxValue;
                    System.out.print(matrixForVectorSolution[i][j] + " ");
                } else {
                    if (j == width) {
                        matrixForVectorSolution[i][j] = 0.0001f;
                        System.out.print(matrixForVectorSolution[i][j] + " ");
                    } else {
                        matrixForVectorSolution[i][j] = matrix[i][j];
                        System.out.print(matrixForVectorSolution[i][j] + " ");
                    }
                }
            }
            System.out.println();
        }
        Solution solution = new Solution();
        return solution.singleDivisionMethod(matrixForVectorSolution, width + 1, height);
    }


    private float[] multiplyMatrixAndVector(float[][] matrix, int height, int width, float[] vector) {
        float[] y = new float[height];
        for (int i = 0; i < height; i++) {
            float sum = 0;
            for (int j = 0; j < width; j++) {
                sum = sum + matrix[i][j] * vector[j];
            }
            y[i] = sum;
        }
        return y;
    }

    private boolean checkIfOperationMustBeStopped(float value1, float value2) {
        return Math.abs(value2 - value1) <= e;
    }

    private float[] inicializeInitialApproximation(int height) {
        float[] initialApproximation = new float[height];
        for (int i = 0; i < height; i++) {
            initialApproximation[i] = 1f;
        }
        return initialApproximation;
    }

    public String printArray(float[] array) {
        String result = "";
        DecimalFormat decimalFormat = new DecimalFormat("0.000000000");
        for (float element : array) {
            result += " " + decimalFormat.format(element) + "; ";
        }
        return result;
    }
}
