package utils2ArchiveComparator;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A class that implements the main functionality for comparing two archives. Provides selection of the archive
 * directory through the window, compares archives, and saves the comparison results to a file.
 */
public class ArchiveComparator {
    private File outputFile;
    public static String CHANGED = " - Changed";
    public static String DELETE = " - Delete";
    public static String ADDED = " - Added";
    public static String UNCHANGED = " - Unchanged";
    public static String PRESUMABLY_RENAMED = " - Presumably renamed";

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     *The main function of comparing two archives. Sets archives using the Archiver class, reads the files attached to
     * them, analyzes them, and writes the result to a file step-by-step
     *
     * @param dir1 path to the first archive file
     * @param dir2 path to the second archive file
     * @throws IOException input and output errors
     */
    public void compareArchives(String dir1, String dir2) throws IOException {
        Archive archive1 = new Archive(dir1);
        Archive archive2 = new Archive(dir2);
        readArchive(archive1);
        readArchive(archive2);
        File outputFile = new File(Paths.get(archive2.getDir()).getParent().toString()
                + "\\compareOutputFile.txt");
        setOutputFile(outputFile);
        writeCompareOutputFile(archive1.getName());
        for (ArchiveFile archiveFile : archive1.getListArchiveFiles()) {
            compare(archiveFile, archive2, DELETE);
        }
        writeCompareOutputFile(archive2.getName());
        for (ArchiveFile archiveFile : archive2.getListArchiveFiles()) {
            compare(archiveFile, archive1, ADDED);
        }
    }

    /**
     * Compares a file from one archive with each of the files from another archive. Files are considered the same if
     * their name and size are equal
     *
     * @param targetArchiveFile any archive file
     * @param archive one of the archives
     * @param resultCompare responsible for the reason that a file is missing from one of the archives
     */
    private void compare(ArchiveFile targetArchiveFile, Archive archive, String resultCompare) {
        for (ArchiveFile archiveFile : archive.getListArchiveFiles()) {
            if (targetArchiveFile.equals(archiveFile)) {
                writeCompareOutputFile(targetArchiveFile.getName() + UNCHANGED);
                return;
            }
            if (targetArchiveFile.getSize() == archiveFile.getSize()
                    && !targetArchiveFile.getName().equals(archiveFile.getName())) {
                writeCompareOutputFile(targetArchiveFile.getName() + PRESUMABLY_RENAMED);
                return;
            }
            if (targetArchiveFile.getSize() != archiveFile.getSize()
                    && targetArchiveFile.getName().equals(archiveFile.getName())) {
                writeCompareOutputFile(targetArchiveFile.getName() + CHANGED);
                return;
            }
        }
        writeCompareOutputFile(targetArchiveFile.getName() + resultCompare);
    }

    /**
     * Reads files from the archive to a separate list.
     *
     * @param archive one of the archives
     */
    protected void readArchive(Archive archive) {
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(archive.getDir())))
        {
            ZipEntry entry;
            List<ArchiveFile> archiveFileList = new ArrayList<>();
            while((entry = zin.getNextEntry()) != null) {
                ArchiveFile archiveFile = new ArchiveFile();
                archiveFile.setName(entry.getName());
                archiveFile.setSize(entry.getSize());
                zin.closeEntry();
                archiveFileList.add(archiveFile);
            }
            archive.setListArchiveFiles(archiveFileList);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Writes the comparison result to a file. Recording occurs line by line each time after determining the result
     * in a comparison between files
     *
     * @param outputText information for recording the comparison result
     */
    public void writeCompareOutputFile(String outputText) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getOutputFile(), true));
            String lineSeparator = System.getProperty("line.separator");
            writer.write(outputText + lineSeparator);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Opens a window for selecting the archive location directory
     *
     * @return absolute path of the archive location
     */
    public String chooseFile() {
        JFileChooser fileOpen = new JFileChooser();
        int ret = fileOpen.showDialog(null, "Find the archive you need");
        if (ret == JFileChooser.APPROVE_OPTION) {
            return fileOpen.getSelectedFile().getAbsolutePath();
        }
        return "";
    }
}
