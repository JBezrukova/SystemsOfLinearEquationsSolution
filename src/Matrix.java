
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Юля on 28.03.2016.
 */
public class Matrix {

    private int width;
    private int height;
    private float[][] matrix;

    public void printMatrix() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void readFromFile(String fileName) throws FileNotFoundException {
        width = 0;
        height = 0;
        Scanner scanner = new Scanner(new File(fileName));
        String str1 = "";
        while (scanner.hasNextLine()) {
            str1 = scanner.nextLine();
            height++;
        }

        Scanner scanner1 = new Scanner(str1);

        while (scanner1.hasNextDouble()) {
            scanner1.nextDouble();
            width++;
        }

         matrix = new float[height][width];

        Scanner scanner3 = new Scanner(new File(fileName));
        int i = 0;
        int j = 0;

        while (scanner3.hasNextLine()) {
            str1 = scanner3.nextLine();
            Scanner scanner2 = new Scanner(str1);
            while (scanner2.hasNextFloat()) {
                matrix[i][j] = scanner2.nextFloat();
                j++;
            }
            j = 0;
            i++;
        }

    }

    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float[][] getMatrix() {
        return matrix;
    }
}
