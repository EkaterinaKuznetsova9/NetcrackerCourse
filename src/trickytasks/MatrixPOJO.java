package trickytasks;

/**
 * Класс, описывающий матрицу как сущность (POJO - Plain Old Java Object)
 */
public class MatrixPOJO {
    /**
     * Матрица, представленная в виде двумерного массива
     */
    private double[][] matrix;

    /**
     * Создает матрицу с нулевыми элементами указанного размера
     *
     * @param n число строк
     * @param m число столбцов
     */
    public MatrixPOJO(int n, int m) {
        this.matrix = new double[n][m];
    }

    /**
     * Создает матрицу с указанными элементами
     *
     * @param m матрица
     */
    public MatrixPOJO(double[][] m) {
        this.matrix = new double[m.length][m[0].length];
        setMatrix(m);
    }


    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }
}
