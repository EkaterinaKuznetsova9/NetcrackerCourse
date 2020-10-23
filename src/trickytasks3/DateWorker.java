package trickytasks3;

/**
 * Класс для работы с объектами типа Date
 */
public class DateWorker {
    /**
     * Определяет високосный год или нет.
     * Логика такая: год високосный, если он кратен 4, но не кратен 100, либо кратен 400
     *
     * @param date дата
     * @return високосный ли год передан
     */
    public static boolean isLeapYear(Date date) {
        return (date.getYear() % 4 == 0) && (date.getYear() % 100 != 0) || (date.getYear() % 400 == 0);
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Осуществляет проверку даты на корректность.
     *
     * @param date дата
     * @return валидная ли дата
     */
    public static boolean isValidDate(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        if (year < 1 || month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }

        boolean isLeapYear = isLeapYear(date);
        switch (month) {
            //февраль
            case 2:
                return (isLeapYear && day < 30) || (!isLeapYear && day < 29);
            // апрель, июнь, сентябрь, ноябрь
            case 4:
            case 6:
            case 9:
            case 11:
                return day < 31;
        }

        return true;
    }

    /**
     * Возвращает номер дня недели, где 0 – MON ... 6- SUN
     * Алгоритм вычисления дня недели для любой даты григорианского календаря позднее 1583 года.
     * Григорианский календарь начал действовать в 1582.
     *
     * Формула:
     * a = (14 − месяц) / 12
     * y = год − a
     * m = месяц + 12 * a − 2
     * ДеньНедели = (7000 + (день + y + y / 4 − y / 100 + y / 400 + (31 * m) / 12)) % 7
     * По формуле ДеньНедели - это индекс в массиве
     * {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}
     * Все деления целочисленные (остаток отбрасывается) 45
     *
     * @param date дата
     * @return -1, если дата не прошла валидацию, иначе индекс дня недели от 0 до 6
     */
    public static int getDayOfWeek(Date date) {
        if (!isValidDate(date) || date.getYear() < 1584) {
            return -1;
        }
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        int a = (14 - month) / 12;
        int y = year - a;
        int m = month + 12 * a - 2;
        int d = (7000 + day + y + y/4 - y/100 + y/400 + 31*m/12)%7;

        return d == 0 ? 6 : d - 1;
    }

    /**
     * Форматирует дату в красивом виде, если получается. Если нет возможности получить день недели, то
     * представляет строку без этой информации. Если дата не валидна, возвращает слово "#ERROR"
     * Например, Tuesday 14 Feb 2012
     *
     * @param date дата
     * @return текстовое представление переданной даты
     */
    public static String toString(Date date) {
        if (!isValidDate(date)) {
            // Дата не проходит валидацию
            return "#ERROR";
        }
        MonthName[] monthNames = MonthName.values();
        if (date.getYear() < 1584) {
            // Дата ранее 1584 года, поэтому не может быть выведена в полностью красивом виде
            return toStringSimple(date);
        }
        Weekday[] week = Weekday.values();
        return week[getDayOfWeek(date)] + " " + toStringSimple(date);
    }

    /**
     *
     * @param date дата
     * @return текстовое представление переданной даты (без дня недели)
     */
    public static String toStringSimple(Date date) {
        MonthName[] monthNames = MonthName.values();
        return date.getDay() + " " + monthNames[date.getMonth() - 1].name() + " " + date.getYear();
    }

    /**
     * Вычисляет сколько дней прошло с данной даты по сегодняшнюю
     *
     * @param date дата
     * @return количество прошедших дней (возвращает -1, если передана текущая дата или если переданная дата ранее,
     * чем 1970 год, а также если переданная дата не является валидной)
     */
    public static int countDays(Date date) {
        if (date.getYear() < 1970 || !isValidDate(date)) {
            return -1;
        }
        //количество пройденных дней с 1 янв 1970 по текущий день
        int totalDaysNow = (int)(((System.currentTimeMillis() / 1000)  / 60) / 60) / 24;

        //считаем число дней в переданной дате по годам до года в дате (не включительно)
        int countDaysBeforeCurrentYear = 0;
        for (int i = 1970; i < date.getYear(); i++) {
            countDaysBeforeCurrentYear += isLeapYear(i) ? 366 : 365;
        }
        // массив с количествами дней по месяцам для високосного года
        int[] leapYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        // массив с количествами дней по месяцам для не високосного года
        int[] noLeapYear = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // определяем список месяцев по количеству дней в них в году из даты
        int[] countDaysInCurrenYear = DateWorker.isLeapYear(date) ? leapYear : noLeapYear;

        // число дней в году даты (т.е. сколько дней прошло с 1 января года date.getYear() по date.getDate())
        int countYearDay = 0;
        for (int i = 0; i < date.getMonth() - 1; i++) {
            countYearDay += countDaysInCurrenYear[i];
        }
        // суммируем все дни за прошедшие года и в последнем
        int allDaysDate = countDaysBeforeCurrentYear + countYearDay + date.getDay();

        if (allDaysDate > totalDaysNow) {
            return -1;
        }
        return totalDaysNow - allDaysDate + 1;
    }
}

enum Weekday {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday
}

enum MonthName {
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December
}
