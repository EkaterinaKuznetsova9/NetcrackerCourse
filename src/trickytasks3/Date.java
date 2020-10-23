package trickytasks3;

public class Date {
    private int year;
    private int month;
    private int day;

    /**
     *
     * @param year год (начиная с 1)
     * @param month месяц (1 - 12)
     * @param day день (1 - 31)
     */
    Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Если указан только год и месяц, то по умолчанию число = 1
     *
     * @param year год
     * @param month месяц
     */
    Date(int year, int month) {
        this.year = year;
        this.month = month;
        this.day = 1;
    }

    /**
     * Если указан только год, то по умолчанию месяц - январь, число - 1
     *
     * @param year год
     */
    Date(int year) {
        this.year = year;
        this.month = 1;
        this.day = 1;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

}
