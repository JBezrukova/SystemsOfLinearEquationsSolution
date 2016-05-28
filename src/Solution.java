
import java.text.DecimalFormat;

/**
 * Created by Юля on 28.03.2016
 */
public class Solution {

    private float[][] copy(float[][] matrix, int width, int height) {
        float[][] newMatrix = new float[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    public float[] singleDivisionMethod(float[][] matrix1, int width, int height) {

        float[][] matrix = copy(matrix1, width, height);

        int j = 0;
        int k = 0;
        float l;
        for (int n = 1; n < height; n++) {
            for (int i = k + 1; i < height; i++) {
                if (matrix[k][j] == 0) {
                    float[] temp = matrix[k];

                    for (int s = i; s < height; s++) {
                        if (matrix[s][j] != 0) {
                            matrix[k] = matrix[s];
                            matrix[s] = temp;
                        }
                    }
                }
                l = matrix[i][j] / matrix[k][j];
                for (int s = 0; s < width; s++) {
                    matrix[i][s] = matrix[i][s] - matrix[j][s] * l;
                }
            }
            k++;
            j++;
        }

        float[] answers = new float[height];

        answers = findingAnswers(width, height, matrix, answers);

        return answers;
    }

    private float[] findingAnswers(int width, int height, float[][] matrix, float[] answers) {
        int j;
        int k;
        j = 1;
        for (int i = height - 1; i >= 0; i--) {
            answers[i] = matrix[i][width - 1] / matrix[i][width - 1 - j];
            if (i > 0) {
                for (k = i - 1; k >= 0; k--) {
                    matrix[k][width - 1] = matrix[k][width - 1] - (matrix[k][width - 1 - j] * answers[i]);
                }
                j++;
            }
        }

        return answers;
    }

    public float[] methodWithPartionPivotingOnAColumn(float[][] matrix1, int width, int height) {

        float[][] matrix = copy(matrix1, width, height);
        int j = 0;
        int k = 0;
        float l;
        for (int n = 1; n < height; n++) {
            for (int i = k + 1; i < height; i++) {

                float max = matrix[k][j];
                int maxIndex = k;
                int f = k + 1;

                while (f != height) {

                    if (Math.abs(matrix[f][j]) > Math.abs(max)) {
                        max = matrix[f][j];
                        maxIndex = f;
                    }
                    f++;
                }

                if (Math.abs(matrix[k][j]) != Math.abs(max)) {
                    float[] temp = matrix[k];
                    matrix[k] = matrix[maxIndex];
                    matrix[maxIndex] = temp;
                }

                l = matrix[i][j] / matrix[k][j];
                for (int s = 0; s < width; s++) {
                    matrix[i][s] = matrix[i][s] - matrix[j][s] * l;
                }
            }
            k++;
            j++;
        }

        float[] answers = new float[height];

        answers = findingAnswers(width, height, matrix, answers);

        return answers;
    }


    public float[] fullPivot(float[][] matrix1, int width, int height) {
        float[][] matrix = copy(matrix1, width, height);
        float l = 0;
        int k = 0;
        int j = 0;
        for (int n = 1; n < height; n++) {
            int count = 0;
            for (int i = k + 1; i < height; i++) {

                float max = matrix[k][j];
                int maxWidth = j;
                int maxHeight = k;

                for (int t = k + 1; t < height; t++) {
                    for (int s = 0; s < width - 1; s++) {
                        if (Math.abs(matrix[t][s]) > Math.abs(max)) {
                            maxWidth = s;
                            maxHeight = t;
                            max = matrix[t][s];
                        }
                    }
                }

                if (Math.abs(matrix[k][j]) != Math.abs(max)) {
                    float[] temp = matrix[k];
                    matrix[k] = matrix[maxHeight];
                    matrix[maxHeight] = temp;

                    for (int t = 0; t < height; t++) {
                        float value = matrix[t][j];
                        matrix[t][j] = matrix[t][maxWidth];
                        matrix[t][maxWidth] = value;
                    }

                }

                l = matrix[i][j] / matrix[k][j];
                for (int t = 0; t < width; t++) {
                    matrix[i][t] -= matrix[j][t] * l;
                }
                count++;
            }
            k++;
            j++;
        }

        float[] m1 = new float[width - 1];

        int f = 1;
        for (int t = height - 1; t >= 0; t--) {
            m1[t] = matrix[t][width - 1] / matrix[t][width - 1 - f];
            if (t > 0) {
                for (int n = t - 1; n >= 0; n--) {
                    matrix[n][width - 1] -= matrix[n][width - 1 - f] * m1[t];
                }
                f++;
            }
        }
        return m1;
    }

    public float[] yakobyMethod(float[][] matrix1, int width, int height) {
        float[][] matrix = changeMatrixWithNull(copy(matrix1, width, height), width, height);

        float eps = 0.001F;
        float[] previousValues = new float[height];

        int iteration = 0;
        float[] currentValues = new float
                [height];
        float norm = findingStopPoint(width, height, matrix, eps);
        System.out.println("Stop point : " + norm);

        while (true) {
            iteration++;
            for (int i = 0; i < height; i++) {

                currentValues[i] = matrix[i][width - 1];

                for (int j = 0; j < height; j++) {
                    if (i != j) {
                        currentValues[i] = currentValues[i] - matrix[i][j] * previousValues[j];
                    }
                }
                currentValues[i] = currentValues[i] / matrix[i][i];
            }

            float max = Math.abs(currentValues[0] - previousValues[0]);
            for (int i = 1; i < height; i++) {
                if (max < Math.abs(currentValues[i] - previousValues[i])) {
                    max = Math.abs(currentValues[i] - previousValues[i]);
                }
            }

            if (max < norm) {
                break;
            }


            for (int i = 0; i < height; i++) {
                previousValues[i] = currentValues[i];
            }

            System.out.println("------------------------------------------------------------");
            System.out.println("Number of iteration: " + iteration);

            String result = "{";
            for (int i = 0; i < height; i++) {
                result += currentValues[i] + "; ";
            }
            result += "}";
            System.out.println(result);
        }

        return currentValues;
    }


    private float findingStopPoint(int width, int height, float[][] matrix, float eps) {
        float[][] matrixB = new float[height][width - 1];


        float[] vectorB = new float[height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - 1; j++) {
                float element = matrix[i][i];
                if (i != j) {
                    matrixB[i][j] = (-matrix[i][j]) / element;
                } else {
                    matrixB[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < height; i++) {
            float sum = 0;
            for (int j = 0; j < width - 1; j++) {
                sum += Math.abs(matrixB[i][j]);
            }
            vectorB[i] = sum;
        }

        float max = 0;
        for (int i = 0; i < height; i++) {
            if (Math.abs(vectorB[i]) > Math.abs(max)) {
                max = Math.abs(vectorB[i]);
            }
        }
        float norm = eps * (1 - max) / max;
        return norm;
    }


    public float[] zeydelMethod(float[][] matrix1, int width, int height) {
        float[][] matrix = changeMatrixWithNull(copy(matrix1, width, height), width, height);
        float eps = 0.001f;
        float norm = findingStopPoint(width, height, matrix, eps);
        System.out.println("Stop point: " + norm);
        int iteration = 0;
        float[] previousValues = new float[height];
        float[] previous = new float[height];

        for (int i = 0; i < height; i++) {
            previousValues[i] = matrix[i][width - 1];
        }

        float k;
        float s;
        float v;
        do {
            iteration++;
            System.out.println("----------------------------------------------");
            System.out.println("Number of iteration: " + iteration);
            k = 0;
            for (int i = 0; i < height; i++) {
                s = 0;
                for (int j = 0; j < height; j++) {
                    if (i != j) {
                        s = s + matrix[i][j] * previous[j];
                    }
                }
                v = previous[i];
                previous[i] = (previousValues[i] - s) / matrix[i][i];
                k = Math.abs(previous[i] - v);
            }
            String result = "{";
            for (int l = 0; l < height; l++) {
                result += previous[l] + "; ";
            }
            result += "}";
            System.out.println(result);
        } while (k > norm);
        return previous;
    }


    private float matrixNorm(float[][] matrix1, int width, int height) {
        float[][] matrix = copy(matrix1, width, height);

        float max = 0;
        for (int i = 0; i < height; i++) {
            float sum = 0;
            for (int j = 0; j < width; j++) {
                sum = sum + Math.abs(matrix[i][j]);
            }
            if (sum > max) {
                max = sum;
            }
        }

        return max;
    }

    public String printArray(float[] array) {
        String result = "Answers:";
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        for (float element : array) {
            result += " " + decimalFormat.format(element) + "; ";
        }
        return result;
    }

    private float[][] changeMatrixWithNull(float[][] matrix, int width, int height) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][i] == 0) {
                float[] temp = matrix[i];
                matrix[i] = matrix[i + 1];
                matrix[i + 1] = temp;
                i = 0;
            }
        }
        if (matrix[height - 1][width - 2] == 0) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][width - 2] != 0) {
                    float[] temp = matrix[height - 1];
                    matrix[height - 1] = matrix[i];
                    matrix[i] = temp;
                }
            }
        }
        return matrix;
    }

}


