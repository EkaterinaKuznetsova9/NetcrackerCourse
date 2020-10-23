package trickytasks2;

/**
 * Интерфейс для возможности вести историю игры в разных форматах отображения (файл, БД и др)
 */
public interface HistoryGame {
    void logGame(String logInfo);
}
