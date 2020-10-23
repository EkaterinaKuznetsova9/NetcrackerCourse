package trickytasks2;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

/**
 * Главный класс игры "Угадай слово".
 * Игра заканчивается, когда пользователь проигрывает или выигрывает (один запуск приложения = одна игра).
 */
public class Main {

    public static void main(String[] args) throws IOException {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your name?");
        user.setName(sc.next());
        Date date = new Date();

        IHistoryGameFile history = new IHistoryGameFile();
        history.logGame("### " + date.toString() + " ###");
        history.logGame("UserName: " + user.getName());

        String[] languageBank = languageSelection(sc);
        ReaderBankWord reader = new IReaderBankWordFile(languageBank[0]);
        history.logGame("Dictionary: " + languageBank[0]);

        GuessTheWord newGame = new GuessTheWord(history, reader, languageBank[1]);
        newGame.start();
    }

    /**
     * Определяет язык слова, которое будет угадываться
     *
     * @param sc ввод в консоль
     * @return название файла, в котором содержится банк со словами выбранного языка, а также регулярное выражение
     * алфавита этого языка
     */
    private static String[] languageSelection(Scanner sc) {
        String[] langDefault = {"BankWords", "[A-Za-z]"};
        System.out.println("By default, the English word Bank is used, but you can choose Russian.\nIf you want" +
                " to change the language, enter - rus, otherwise any character or word...");
        String language = sc.next();
        if (!language.equals("rus")) {
            return langDefault;
        }
        return new String[]{"BankWordsRus", "[А-Яа-яЁё]"};
    }
}
