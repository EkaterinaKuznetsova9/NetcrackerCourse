package trickytasks2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс для создания файла с историей игры (некое логирование действий или своего рода база данных информации
 * о ходе игры)
 */
public class IHistoryGameFile implements HistoryGame {
    public static String fileName = "LogGame.txt";
    private final File file;

    IHistoryGameFile() {
        this.file = new File(fileName);
    }

    public File getFile() {
        return file;
    }

    /**
     * Сохраняет в файл fileName различную информацию о ходе игры
     *
     * @param logInfo строка, которая добавляется в файл с историей по игре
     */
    public void logGame(String logInfo) {
        try {
            if (getFile().createNewFile()) {
                System.out.println("A game history file was created. There you can view your achievements and " +
                        "those of other users.\n");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(), true));
            String lineSeparator = System.getProperty("line.separator");
            writer.write(logInfo + lineSeparator);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
