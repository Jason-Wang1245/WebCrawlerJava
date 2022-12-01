public class MatrixMultiplication {
    public static void scalarMultiplication(double[][] matrix, int scalarValue){
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][i] *= scalarValue;
    }

    public static double[][] multiplyMatrix(double[][] a, double[][] b) {
        if (a[0]. length != b.length)
            return null;
        double[][] result = new double[a.length][b[0].length];

        for(int i=0; i < result.length; i++){
            for(int j=0; j < result[0].length; j++){
                result[i][j]=0;
                for(int k=0; k < b.length; k++)
                {
                    result[i][j]+=a[i][k]*b[k][j];
                }
            }
        }
        return result;
    }

    public static double euclideanDistance(double[][] a, double[][] b){
        double sum = 0;

        for (int i = 0; i < a.length; i++)
            sum += Math.pow((a[0][i] - b[0][i]), 2);
        return Math.sqrt(sum);
    }
    /*

     */
}
