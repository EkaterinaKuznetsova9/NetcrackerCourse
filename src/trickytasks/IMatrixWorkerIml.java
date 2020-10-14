package trickytasks;

import java.util.Arrays;

/**
 * Класс для работы с матрицами, реализующий интерфейс IMatrixWorker.
 * Матрицей считаем двумерный массив, в котором количество элементов в каждой строке равное, аналогично и для столбцов
 */
public class IMatrixWorkerIml implements IMatrixWorker {
    /**
     * Определитель матрицы (скалярная величина)
     */
    private double determinant;

    /**
     * Счетчик слагаемых в определении детерминанта. Используется для валидации результата работы метода
     * вычисления определителя матрицы
     */
    private int summandCounter = 0;

    public IMatrixWorkerIml() {}

    /**
     * Вывод в терминале матрицы в виде строки
     *
     * @param m матрица
     */
    @Override
    public void print(double[][] m) {
        System.out.println(Arrays.deepToString(m));
    }

    /**
     * Проверяет, имеют ли две матрицы одинаковую размерность (проверяет сначала переданные двумерные массивы на
     * принадлежность к матрицам)
     * @param m1 первая матрица
     * @param m2 вторая матрица
     * @return true, если переданные аргументы матрицы с одинаковыми размерностями, иначе false
     */
    @Override
    public boolean haveSameDimension(double[][] m1, double[][] m2) {
        if (isNoMatrix(m1.length, m1[0].length, m1) || isNoMatrix(m2.length, m2[0].length, m2)) {
            return false;
        }
        return m1.length == m2.length && m1[0].length == m2[0].length;
    }

    /** Сложение матриц. Производится поэлементно, выполнимо только для матриц одинаковой размерности.
     *
     * @param m1 первая матрица
     * @param m2 вторая матрица
     * @return сумма двух матриц или Exception
     * @throws Exception Возникает, если аргументы не подходят для сложения матриц
     */
    @Override
    public double[][] add(double[][] m1, double[][] m2) throws Exception {
        if (!haveSameDimension(m1, m2)) {
            throw new Exception("Аргументы не подходят для сложения матриц");
        }
        double[][] matrixSum = new double[m1.length][m1[0].length];
        for (int i = 0; i < matrixSum.length; i++) {
            for (int j = 0; j < matrixSum[0].length; j++) {
                matrixSum[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return matrixSum;
    }

    /**
     * Вычитание матриц. Производится поэлементно, выполнимо только для матриц одинаковой размерности.
     * C = A - B = A + (-1) * B
     * @param m1 первая матрица
     * @param m2 вторая матрица
     * @return Разность двух матриц
     * @throws Exception Возникает, если аргументы не подходят для разности матриц
     */
    @Override
    public double[][] subtract(double[][] m1, double[][] m2) throws Exception {
        if (!haveSameDimension(m1, m2)) {
            throw new Exception("Аргументы не подходят для разности матриц");
        }
        double[][] matrixDifference = new double[m1.length][m1[0].length];
        for (int i = 0; i < matrixDifference.length; i++) {
            for (int j = 0; j < matrixDifference[0].length; j++) {
                matrixDifference[i][j] = m1[i][j] - m2[i][j];
            }
        }
        return matrixDifference;
    }

    /**
     * Умножение двух матриц. Умножение матриц в общем случае некоммутативно, т.е. A*B != B*A, поэтому в данном методе
     * имеет значение порядок переданных аргументов. Умножение происходит по правилу "строка на столбец"
     *
     * @param m1 первая матрица
     * @param m2 вторая матрица
     * @return Произведение двух матриц
     * @throws Exception Возникает, если аргументы не подходят для разности матриц или если матрицы не согласованы
     */
    @Override
    public double[][] multiply(double[][] m1, double[][] m2) throws Exception {
        if (isNoMatrix(m1.length, m1[0].length, m1) || isNoMatrix(m2.length, m2[0].length, m2)) {
            throw new Exception("Аргументы или один из них не являются матрицами");
        }
        if (m1[0].length != m2.length) {
            throw new Exception("Матрицы не согласованы, найти их произведение нельзя");
        }

        double[][] matrixMultiply = new double[m1.length][m2[0].length];
        double tmp;
        for (int i = 0; i < matrixMultiply.length; i++) {
            for (int j = 0; j < matrixMultiply[0].length; j++) {
                tmp = 0;
                for (int k = 0; k < m1[0].length; k++) {
                     tmp = tmp + m1[i][k] * m2[k][j];
                }
                matrixMultiply[i][j] = tmp;
            }
        }
        return matrixMultiply;
    }

    /**
     * Проверяет, действительно ли заданный двумерный массив является матрицей
     * @param matrix проверяемая матрица
     * @return Равно ли число элементов в каждой строке и равно ли чисто элементов в каждом столбце
     */
    public boolean isNoMatrix(int numN, int numM, double[][] matrix) {
        if (numN != matrix.length) {
            return true;
        }
        for (double[] doubles : matrix) {
            if (doubles.length != numM) {
                return true;
            }
        }
        return false;
    }

    /**
     * По задаче нужно найти определитель матрицы методом Крамера. Однако метод Крамера – это метод решения систем
     * линейных уравнений (СЛУ), с его помощью нельзя найти дискриминатор. Более того, метод применим, только если
     * определитель не равен 0.
     *
     * Поэтому getDeterminant(m) просто находит определитель.
     * Определителем квадратной матрицы порядка n на n является сумма, содержащая n! слагаемых. Каждое слагаемое
     * представляет собой произведение n элементов матрицы, причем в каждом произведении содержится элемент из каждой
     * строки и из каждого столбца матрицы.
     *
     * Определитель равен сумме произведений элементов какой-либо строки (столбца) на их алгебраические дополнения.
     * Это свойство позволяет вычислять определители матриц любого порядка путем сведения их к сумме нескольких
     * определителей матриц порядка на единицу ниже. Это рекуррентная формула
     * Примеры нахождения определителей: http://www.cleverstudents.ru/matrix/computation_of_determinant.html
     *
     * @return дискриминатор матрицы
     * @throws Exception Возникает, если аргументы не подходят
     */
    public double getDeterminant(double[][] m) throws Exception {
        if (isNoMatrix(m.length, m[0].length, m)) {
            throw new Exception("Переданный аргумент не является матрицей");
        }
        if (m.length != m[0].length) {
            throw new Exception("Дискриминатор можно посчитать только у квадратной матрицы");
        }
        recursiveComputationDeterm(m, 1);
        if (summandCounter != getFactorial(m.length)) {
            throw new Exception("Дискриминатор для матрицы с размером n найден неправильно, т.к. в произведении, в" +
                    " котором он считался, число слагаемых не равно n!");
        }
        return determinant;
    }

    /**
     * Рекурсивно высчитывает определитель матрицы. Каждый шаг рекурсии направлен на сокращение размерности
     * @param subMinor дополнительный минор (подминор родительского, алгебраическое дополнение)
     * @param parentMinor минор предыдущего шага рекурсии (родительский)
     */
    private void recursiveComputationDeterm(double [][] subMinor, double parentMinor) {
        if (subMinor.length > 1){
            double [][] tmpMinor = new double[subMinor.length - 1][subMinor[0].length - 1];
            // по формуле разложения определителя по элементам 1-й строки
            for (int j = 0; j < subMinor[0].length; j++) {
                for (int i = 1; i < subMinor.length; i++) {
                    for (int c = 0; c < subMinor[0].length; c++) {
                        if (c < j) {
                            tmpMinor[i - 1][c] = subMinor[i][c];
                        }
                        else if (c > j) {
                            tmpMinor[i - 1][c - 1] = subMinor[i][c];
                        }
                    }
                }
                double tmpParamParentForSub = Math.pow(-1, j + 2) * subMinor[0][j] * parentMinor;
                recursiveComputationDeterm(tmpMinor, tmpParamParentForSub);
            }
        }
        else {
            /* determinant = сумма произведений элементов первой строки (parentMinor) на их алгебраические дополнения
             (subMinor[0][0]).
             Алгебраическим дополнением элемента m[i][j] матрицы M называется число
             M[i][j]=(-1)^{i+j} * Minor[i][j]}, где Minor[i][j] — дополнительный минор, определитель матрицы,
             получающейся из исходной матрицы M путём вычёркивания i-й строки и j-го столбца. */
            determinant += parentMinor * subMinor[0][0];
            ++summandCounter;
        }
    }

    /**
     * Метод нахождения факториала
     *
     * @param k целочисленное число
     * @return результат вычисления факториала ()
     */
    public static int getFactorial(int k) {
        if (k <= 1) {
            return 1;
        }
        else {
            return k * getFactorial(k - 1);
        }
    }
}
