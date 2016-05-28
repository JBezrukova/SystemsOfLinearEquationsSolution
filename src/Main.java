import java.io.FileNotFoundException;

/**
 * Created by Юля on 02.04.2016.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Matrix matrix = new Matrix();

        matrix.readFromFile("./matrix.txt");
        System.out.println("Single division nethod:");
        matrix.printMatrix();

        Solution solutionMethod = new Solution();
        System.out.println(solutionMethod.printArray(solutionMethod.singleDivisionMethod(matrix.getMatrix(), matrix.getWidth(), matrix.getHeight())));

        System.out.println("Method with partial pivoting on a column: ");
        matrix.printMatrix();
        System.out.println(solutionMethod.printArray(solutionMethod.methodWithPartionPivotingOnAColumn(matrix.getMatrix(), matrix.getWidth(), matrix.getHeight())));

        System.out.println("Method with full pivoting: ");
        matrix.printMatrix();
        System.out.println(solutionMethod.printArray(solutionMethod.fullPivot(matrix.getMatrix(),matrix.getWidth(), matrix.getHeight())));


        matrix.printMatrix();
        /*
        matrix.setMatrix(solutionMethod.changeMatrixWithNull(matrix.getMatrix(), matrix.getWidth(), matrix.getHeight()));
        matrix.printMatrix();
        */
        System.out.println("Yakoby method:");

        System.out.println(solutionMethod.printArray(solutionMethod.yakobyMethod(matrix.getMatrix(),matrix.getWidth(), matrix.getHeight())));

        matrix.printMatrix();
        System.out.println("Zeidel method: ");
        System.out.println(solutionMethod.printArray(solutionMethod.zeydelMethod(matrix.getMatrix(), matrix.getWidth(), matrix.getHeight())));

      //  System.out.println("Aprioir quality:"+solutionMethod.apriorQuality(matrix.getMatrix(), matrix.getWidth(), matrix.getHeight()));
    }
}

