package linear.algebra;

import java.util.Arrays;

public class GaussianElimination{
    private int rows;
    private int cols;
    private double[][] matrix;

    public GaussianElimination(int rows, int cols, double[][] matrix){
        this.rows = rows;
        this.cols = cols;
        setMatrix(matrix);
    }
    
    public int getRows(){
        return this.rows;
    }
    
    public int getCols(){
        return this.cols;
    }

    public double[][] getMatrix(){
        return this.matrix;
    }

        public void setMatrix(double[][] matrix) {
        try{
            if (matrix == null || matrix.length == 0) {
                throw new IllegalArgumentException("Matrix cannot be null or empty.");
            }

            int newRows = matrix.length;
            int newColumns = matrix[0].length;

            if (this.rows != 0 && (this.rows != newRows || this.cols != newColumns)) {
                throw new IllegalArgumentException("Cannot set a new matrix with different dimensions.");
            }

            this.matrix = new double[newRows][newColumns];//we will only allocate memory space for our matrix only if the specified rows and cols match the given rows and cols
            for (int i = 0; i < newRows; i++) {
                this.matrix[i] = Arrays.copyOf(matrix[i], newColumns);
                // this.matrix = matrix;
            }
        }
        catch(IllegalArgumentException msg){
            System.err.println(msg.getMessage());
        }
    }

    public void rowEchelonForm(){
        int h = 0, k = 0;
        while(h < rows && k < cols){
            int i_max = argMax(h, k);
            if(matrix[i_max][k] == 0)
                k++;
            else{
                swapRows(h, i_max);
                multiplyRow(h, k);//inorder to make it 0
                multiplyAndAddRow(h, k, k);
                h++;
                k++;
            } 
        }
    }

    private void multiplyAndAddRow(int h, int k, int ratio){
        for(int i = h+1; i < rows; i++){
            double f = matrix[i][k] / matrix[h][k];
            matrix[i][k] = 0;

            for(int j = k + 1; j < cols; j++){
                matrix[i][j] = matrix[i][j] - matrix[h][j] * f;
            }
        }
    }

    private int argMax(int i , int j){
        double maxi = Math.abs(matrix[i][j]);
        int maxIndex = i;
        for( i = i + 1; i < rows; i++){
            double value = Math.abs(matrix[i][j]);
            if( maxi < value){
                maxi =  value;
                maxIndex = i;
                //Math.max(maxi, maxi)
            }
        }
        return maxIndex;
    }

    private void checkMatrixDimensions(double[][] matrix){
        int rows = matrix.length;
        int columns = matrix[0].length;
        
        System.out.println("Number of rows: " + rows);
        System.out.println("Number of columns: " + columns);
    }

    private void swapRows(int row1, int row2) {
        if (row1 < 0 || row1 >= rows || row2 < 0 || row2 >= rows) {
            throw new IllegalArgumentException("Invalid row index.");
        }

        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private void multiplyRow(int rowIndex, int colIndex) {
        double divisor = matrix[rowIndex][colIndex];
        if (divisor == 0) {
            throw new IllegalArgumentException("System of equations has no unique solution. matrix[" + rowIndex +"]" + "[" + colIndex +"] can not be zero.");
        }

        for (int j = colIndex; j < cols; j++) {
            matrix[rowIndex][j] /= divisor;
        }
    }

    public void backSubstitution() {
        //matrix[0][0] = 0.0;
        for (int i = rows - 1; i >= 0; i--) {
            if ( matrix[i][i] == 0.0) {
                throw new IllegalArgumentException("System of equations has no unique solution.");
            }
    
            for (int j = i - 1; j >= 0; j--) {
                double ratio = matrix[j][i] / matrix[i][i];
                for (int n = i; n < cols; n++) {
                    matrix[j][n] -= ratio * matrix[i][n];
                }
            }
        }
    }

    public void print(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j< cols - 1; j++){
                System.out.print(matrix[i][j] +""+ (char)(97 + j));
                if(j < cols - 2)
                    System.out.print(" + ");
            }
            System.out.print(" = " + matrix[i][cols - 1] + "\n");
        }
    }
}