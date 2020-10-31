package utils2ArchiveComparator;

import java.io.IOException;

/**
 * Main class for working with the program
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String dir1;
        String dir2;
        ArchiveComparator archiveComparator = new ArchiveComparator();
        if (args.length == 2) {
            dir1 = args[0];
            dir2 = args[1];
        } else {
            dir1 = archiveComparator.chooseFile();
            dir2 = archiveComparator.chooseFile();
        }
        //dir1 = "C:\\Users\\kater\\IdeaProjects\\NetcrackerCourse\\src\\utils2ArchiveComparator\\example\\MyZIP1.zip";
        //dir2 = "C:\\Users\\kater\\IdeaProjects\\NetcrackerCourse\\src\\utils2ArchiveComparator\\example\\MyZIP2.zip";
        archiveComparator.compareArchives(dir1, dir2);
    }
}
