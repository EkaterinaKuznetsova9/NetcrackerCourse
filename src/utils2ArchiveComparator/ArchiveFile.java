package utils2ArchiveComparator;

import java.util.Objects;

/**
 * A class responsible for a file attached to an archive that has information about the file name and size
 */
public class ArchiveFile {
    private String name;
    // size in bytes
    private long size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArchiveFile that = (ArchiveFile) o;
        return getSize() == that.getSize() &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSize());
    }
}
