package trickytasks2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для чтения из файла банка слов (получение слов из заданного словаря)
 */
public class IReaderBankWordFile implements ReaderBankWord {
    private final String fileName;

    IReaderBankWordFile(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Получает "банк слов" из файла
     * @return массив слов из файла, которые будут угадываться в игре
     */
    @Override
    public String[] getBankWord() {
        List<String> wordBankArrayList = new ArrayList<>();
        try {
            File file = new File(fileName + ".txt");
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            if (line == null) {
                throw new IllegalArgumentException("The word Bank is empty");
            }
            while (line != null) {
                if (line.length() != 0) {
                    wordBankArrayList.add(line);
                }
                line = reader.readLine();
            }
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordBankArrayList.toArray(new String[0]);
    }
}
