package trickytasks2;

import java.util.*;

/**
 * Класс, отвечающий за игровую логику.
 * Количество букв в загадываемом слове равно числу символов решетки (#) в начале игры после фразы "Guess the word: ".
 * Если буква, введенная пользователем есть в слове, то все места, где она встречается заменяются с решетки на эту
 * букву. Если нет, то дается следующая попытка для ввода. Если игрок ввел слово и оно совпадает с загаданным, он
 * победил, иначе - проигрыш.
 */
public class GuessTheWord {
    private final IHistoryGameFile history;
    private final ReaderBankWord reader;
    private final String checkExp;

    GuessTheWord(IHistoryGameFile history, ReaderBankWord reader, String checkExp) {
        this.history = history;
        this.reader = reader;
        this.checkExp = checkExp;
    }

    /**
     * Реализация логика игры.
     */
    public void start() {
        Scanner sc = new Scanner(System.in);
        int trial = 0;
        boolean isWinner = false;
        String secretWord = getRandomWord();

        history.logGame("SecretWord: " + secretWord);
        StringBuilder guessed = new StringBuilder(secretWord.replaceAll(checkExp, "#"));

        addLine();
        System.out.println("Guess the word: " + guessed);
        addLineTranspToNewLine();

        while (!isWinner) {
            System.out.println("Enter one letter or word at once: ");
            String userWord = sc.next().toLowerCase();
            ++trial;
            history.logGame("Trial: " + trial + ". " + "Introduced: " + userWord);

            if (userWord.length() == 1) {
                if (secretWord.contains(userWord)) {
                    for (int i = 0; i < secretWord.length(); i++) {
                        if (secretWord.charAt(i) == userWord.charAt(0)) {
                            guessed.replace(i, i + 1, userWord);
                        }
                    }
                    if (guessed.indexOf("#") > -1) {
                        System.out.println("Trial: " + trial + ". This letter is in the word. Keep it up:)\n");
                        System.out.println("Word - " + guessed);
                        history.logGame("This is OK");
                    } else {
                        isWinner = true;
                    }
                } else {
                    System.out.println("Trial: " + trial + ". Alas, you did not guess. Try again ;)");
                    history.logGame("This is wrong");
                }
            } else {
                if (userWord.equals(secretWord)) {
                    isWinner = true;
                }
                break;
            }
        }

        getResult(isWinner, trial, history);
        addLineTranspToNewLine();
        sc.close();
    }

    /**
     * Выводит результирующую надпись по итогам игры: информацию о том угадано ли слово
     *
     * @param isWinner показывает, игрок победил в игре или проиграл
     * @param trial количество сделанных попыток, которые привели к концу игры
     * @param history история игры
     */
    protected static void getResult(boolean isWinner, int trial, IHistoryGameFile history) {
        if (isWinner ){
            System.out.println("\nGREAT!!! :) You won with " + trial + " attempts.");
            history.logGame("Victory!");
        } else {
            System.out.println("\nUnfortunately, you didn't guess... :( your game is lost on " + trial + " attempts.");
            history.logGame("Loss!");
        }
    }

    /**
     * Получает слово (все буквы нижнего регистра) из специального банка случайным образом
     *
     * @return рандомное слово из банка слов
     */
    private String getRandomWord() {
        String[] wordBank = reader.getBankWord();
        if (wordBank.length == 0) {
            throw new IllegalArgumentException("The word Bank is empty");
        }
        Random rnd = new Random(System.currentTimeMillis());
        return wordBank[rnd.nextInt(wordBank.length)].toLowerCase();
    }

    /**
     * Добавляет в консоль линию из *
     */
    private static void addLine() {
        System.out.println("***********************************************************************");
    }

    /**
     * Добавляет в консоль линию из * с дальнейшим переводом на след строку
     */
    private static void addLineTranspToNewLine() {
        System.out.println("***********************************************************************\n");
    }
}
