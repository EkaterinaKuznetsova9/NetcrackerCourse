package trickytasks;

/**
 * Интерфейс для работ с матрицами
 *
 * Дополнительные задачи:
 * 1) Реализовать класс описывающий матрицу как сущность (POJO)
 * 2) Реализовать метод считающий определитель матрицы методом Крамера
 */
public interface IMatrixWorker {

    public void print(double[][] m);

    public boolean haveSameDimension(double[][] m1, double[][] m2);

    public double[][] add(double[][] m1, double[][] m2) throws Exception;

    public double[][] subtract(double[][] m1, double[][] m2) throws Exception;

    public double[][] multiply(double[][] m1, double[][] m2) throws Exception;
}
