package trickytasks;

public class Main {

    public static void main(String[] args) throws Exception {
        // примеры использования класса IMatrixWorkerImpl
        double[][] test = {{3, 5}, {4, 4}};
        double[][] test2 = {{1, 1}, {4.67, -5.054}};
        double[][] test3 = {{3, 4, 5}, {6, 7, 10}, {2, 1, 9}};
        IMatrixWorkerIml matrixWorker = new IMatrixWorkerIml();
        boolean t = matrixWorker.haveSameDimension(test, test2);
        //double[][] sum = matrixWorker.subtract(test, test2);
        System.out.println("Определитель = " + matrixWorker.getDeterminant(test3));
    }
}
