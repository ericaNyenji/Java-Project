package linear;

import linear.algebra.GaussianElimination;

public class EquationSolver {

    public static double[][] stringToDouble(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No matrix elements provided.");
        }

        int numRows = args.length;
        int numCols = args[0].split(",\\s*").length;

        double[][] matrix = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String[] values = args[i].split(",\\s*");
            if (values.length != numCols) {
                throw new IllegalArgumentException("Inconsistent number of elements in matrix rows.");
            }
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = Double.parseDouble(values[j]);
            }
        }

        return matrix;
    }

    public static void main(String[] args) {
        double[][] matrix = stringToDouble(args);

        int numRows = matrix.length;
        int numCols = matrix[0].length;

        GaussianElimination ge = new GaussianElimination(numRows, numCols, matrix);

        System.out.println("Original Matrix:");
        ge.print();

        System.out.println("\nAfter rowEchelonForm:");
        ge.rowEchelonForm();
        ge.print();

        System.out.println("\nAfter backSubstitution:");
        ge.backSubstitution();
        ge.print();
    }
}