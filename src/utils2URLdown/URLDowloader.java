package utils2URLdown;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is responsible for storing information about the downloaded file, its source resource, name, extension,
 * directory, flag for opening the file after downloading, and encoding (for html)
 */
public class URLDowloader {
    private String link;
    private String nameOutputFile;
    private String extension;
    private String directoryOutputFile;
    private File outputFile;
    private boolean isOpenAfterDown;
    private String charsetName;

    public static String FIX_NAME_OUT_FILE = "index";
    public static String DEFAULT_EXTENSION = ".html";
    public static String DEFAULT_DIRECTORY_FILE = "src\\utils2URLdown\\downloadedFiles";

    public URLDowloader(String link) {
        this.setLink(link);
        this.setDirectoryOutputFile(DEFAULT_DIRECTORY_FILE);
    }

    public URLDowloader(String link, String directoryName) throws IOException {
        if (directoryName.equals("default")) {
            this.setDirectoryOutputFile(DEFAULT_DIRECTORY_FILE);
        } else if (Files.isDirectory(Paths.get(directoryName))) {
            this.setDirectoryOutputFile(directoryName);
        } else {
            throw new IOException("Error - The argument passed as a directory is not a directory (and not \"default\")");
        }
        this.setLink(link);
    }

    public URLDowloader(String link, String directoryName, String isOpenAfterDown) throws IOException {
        if (directoryName.equals("default")) {
            this.setDirectoryOutputFile(DEFAULT_DIRECTORY_FILE);
        } else if (Files.isDirectory(Paths.get(directoryName))) {
            this.setDirectoryOutputFile(directoryName);
        } else {
            throw new IOException("Error - The argument passed as a directory is not a directory (and not \"default\")");
        }
        this.setLink(link);
        this.setIsOpenAfterDown(Boolean.parseBoolean(isOpenAfterDown));
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }


    public String getNameOutputFile() {
        return nameOutputFile;
    }

    public void setNameOutputFile(String nameOutputFile) {
        this.nameOutputFile = nameOutputFile;
    }

    public String getDirectoryOutputFile() {
        return directoryOutputFile;
    }

    public void setDirectoryOutputFile(String directoryOutputFile) {
        this.directoryOutputFile = directoryOutputFile;
    }

    public String getCharsetName() {
        return charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName.toUpperCase();
    }

    public boolean isOpenAfterDown() {
        return isOpenAfterDown;
    }

    public void setIsOpenAfterDown(boolean openAfterDown) {
        isOpenAfterDown = openAfterDown;
    }

    public String getNameAndExtensionOfFile() {
        return getNameOutputFile() + getExtension();
    }

    public String getDirForInnerFiles() {
        return getDirectoryOutputFile() + "\\" + getNameOutputFile() + "_files";
    }

    public String getNameFolderForInnerFiles() {
        return getNameOutputFile() + "_files";
    }

    public String getFullPathToFile() {
        return getDirectoryOutputFile() + "\\" + getNameAndExtensionOfFile();
    }

    public boolean isHTML() {
        return getExtension().equals(".html");
    }
}
