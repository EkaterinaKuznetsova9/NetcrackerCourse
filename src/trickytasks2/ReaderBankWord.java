package trickytasks2;

/**
 * Интерфейс для возможности чтения словаря (банка слов) из различных источников (файл, БД и др.)
 */
public interface ReaderBankWord {
    String[] getBankWord();
}
