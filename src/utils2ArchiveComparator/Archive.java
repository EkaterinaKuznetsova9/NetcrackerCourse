package utils2ArchiveComparator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A class that stores information about an archive: the full directory where the archive is located, its name, and a
 * list of files attached to it
 */
public class Archive {
    private String dir;
    private String name;
    private List<ArchiveFile> listArchiveFiles;

    /**
     * Constructor
     *
     * @param dir the path to the archive
     * @throws IOException The directory is not set or does not lead to a file
     */
    public Archive(String dir) throws IOException {
        if (dir.isEmpty()) {
            throw new IOException("The directory where the archive is located is not specified");
        }
        Path path = Paths.get(dir);
        if (!path.toFile().isFile()) {
            throw new IOException("The specified directory does not lead to the file");
        }
        this.setName(path.toFile().getName());
        this.setDir(dir);
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArchiveFile> getListArchiveFiles() {
        return listArchiveFiles;
    }

    public void setListArchiveFiles(List<ArchiveFile> listArchiveFiles) {
        this.listArchiveFiles = listArchiveFiles;
    }
}
