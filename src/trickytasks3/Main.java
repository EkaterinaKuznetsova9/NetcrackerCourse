package trickytasks3;

/**
 * Класс, в котором представлены примеры применения класса Date и методов класса DateWorker
 */
public class Main {

    public static void main(String[] args) {
        Date myDate = new Date(2020, 10, 10);
        boolean isLeapYear = DateWorker.isLeapYear(myDate);
        boolean isValidDate = DateWorker.isValidDate(myDate);
        int dayWeek = DateWorker.getDayOfWeek(myDate);
        String s1 = DateWorker.toStringSimple(myDate);
        String s2 = DateWorker.toString(myDate);
        int countDay = DateWorker.countDays(myDate);
        System.out.println(isLeapYear);
        System.out.println(isValidDate);
        System.out.println(dayWeek);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(countDay);
    }
}
