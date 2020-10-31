package utils2URLdown;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The main functionality class for working with URLS and files: save to a local file, a web page or file with any
 * Internet sites by connecting to them via higher-level protocols (HTTP, FTP, etc.), and downloading internal files of
 * the img and link (if you downloaded the main html file), implementation file open after downloading, working with
 * reading and writing of the file, the job file name by default, the definition of the encoding (for html)
 */
public class URLDownloaderService {

    /**
     * Downloading the main file from the specified site and opening the file with the specified parameter,
     * replacing links in the file if it is html
     *
     * @param urlDowloader object with information about the downloaded file and him Internet resource
     * @throws IOException input and output errors
     */
    public void downloadMainFile(URLDowloader urlDowloader) throws IOException {
        download(urlDowloader);
        openFile(urlDowloader);
        if (urlDowloader.isHTML()) {
            Files.createDirectory(Paths.get(urlDowloader.getDirForInnerFiles()));
            parseHTML(urlDowloader);
        }
    }

    /**
     * Downloading files to the specified directory
     *
     * @param urlDowloader object with information about the downloaded file and him Internet resource
     */
    public void download(URLDowloader urlDowloader) {
        try {
            URL url = new URL(urlDowloader.getLink());
            URLConnection connection = url.openConnection();
            String redirect = connection.getHeaderField("Location");
            if (redirect != null) {
                connection = new URL(redirect).openConnection();
            }
            InputStream input = new BufferedInputStream(connection.getInputStream());

            writingToFile(urlDowloader, input, Paths.get(urlDowloader.getDirectoryOutputFile()), url, connection);

            input.close();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.toString());
        }
    }

    /**
     * Partial parsing of the html file (finds links in img and link and replaces them with local paths of downloaded
     * files), overwriting existing html with these changes
     *
     * @param urlDowloader object with information about the downloaded file (with the html extension) and him
     *                     Internet resource
     * @throws IOException input and output errors
     */
    protected void parseHTML(URLDowloader urlDowloader) throws IOException {
        Document doc = Jsoup.parse(urlDowloader.getOutputFile(), urlDowloader.getCharsetName(), "");
        String dir = urlDowloader.getDirForInnerFiles();
        workWithImgAndLink("img[src]", "src", doc, dir, urlDowloader);
        workWithImgAndLink("img[data-src]", "data-src", doc, dir, urlDowloader);
        workWithImgAndLink("link[href]", "href", doc, dir, urlDowloader);
        rewriteOutFile(doc, urlDowloader);
    }

    /**
     * Replaces links to files with local directories of these files (after downloading them)
     *
     * @param cssQuery         html tag with the attribute
     * @param tag              attribute responsible for the file address
     * @param doc              downloaded document prepared for working with it as html (for parse)
     * @param outDir           directory for saving files found in html
     * @param mainUrlDowloader main html-file downloaded at the beginning
     */
    protected void workWithImgAndLink(String cssQuery, String tag, Document doc, String outDir,
                                      URLDowloader mainUrlDowloader) {
        Elements elements = doc.select(cssQuery);
        for (Element element : elements) {
            if (!element.attr(tag).isEmpty() && isURL(element.attr(tag))) {
                try {
                    URLDowloader urlDowloader = new URLDowloader(element.attr(tag), outDir);
                    download(urlDowloader);
//                    element.replaceWith(new TextNode(mainUrlDowloader.getNameFolderForInnerFiles()
//                            + "/" + urlDowloader.getNameAndExtensionOfFile(), ""));
                    String oldValue = element.getElementsByAttribute(tag).toString();
                    String newValue = oldValue.replaceAll(element.attr(tag), mainUrlDowloader.getNameFolderForInnerFiles()
                            + "/" + urlDowloader.getNameAndExtensionOfFile());
                    element.attr(tag, "\\" + newValue + "\\");

                } catch (IOException e) {
                    System.out.println("An error occurred when working with internal files from html: "
                            + e.toString());
                }
            }
        }
    }

    /**
     * Overwriting an old html file
     *
     * @param doc              prepared for parsing the file
     * @param mainUrlDowloader main html-file downloaded at the beginning
     */
    protected void rewriteOutFile(Document doc, URLDowloader mainUrlDowloader) {
        String newFile = mainUrlDowloader.getFullPathToFile();
        File file = new File(newFile);

        try {
            if (mainUrlDowloader.getOutputFile() != null && mainUrlDowloader.getOutputFile().delete()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(doc.toString().replaceAll("&quot;", "\""));
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks whether the passed string is a URL
     *
     * @param url string that is assumed to be the url
     * @return is the argument a link
     */
    public static boolean isURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Writing a read stream to a file by ur
     *
     * @param urlDowloader object with information about the downloaded file and him Internet resource
     * @param input        information read from the site
     * @param path         full path to the file
     * @param url          resource where you need to download the file
     * @param connection   url-connection
     * @throws IOException input and output errors
     */
    protected void writingToFile(URLDowloader urlDowloader, InputStream input, Path path, URL url,
                                 URLConnection connection) throws IOException {
        if (Files.exists(path)) {
            if (path.toFile().isFile()) {
                Scanner sc = new Scanner(System.in);
                String inputDir;
                System.out.println("A file with this full name already exists. Enter 1 if you want to overwrite the" +
                        " file and 2 if you want to specify a new name.");
                while (true) {
                    inputDir = sc.nextLine();
                    if (inputDir.equals("1") || inputDir.equals("2")) {
                        break;
                    }
                    System.out.println("Please choose 1 or 2: ");
                }
                if (inputDir.equals("1")) {
                    Files.copy(input, Paths.get(path.toString()),
                            StandardCopyOption.REPLACE_EXISTING);
                    String fileNameFull = path.getFileName().toString();
                    String fileName = fileNameFull;
                    if (fileNameFull.contains(".")) {
                        fileName = fileNameFull.substring(0, fileNameFull.indexOf('.'));
                        urlDowloader.setExtension(fileNameFull.substring(fileNameFull.indexOf('.')));
                    }
                    urlDowloader.setNameOutputFile(fileName);
                } else {
                    System.out.println("Enter a new full name: ");
                    writingToFile(urlDowloader, input, Paths.get(sc.nextLine()), url, connection);
                    return;
                }
            } else if (path.toFile().isDirectory()) {
                urlDowloader.setDirectoryOutputFile(path.toString());
                initAutoNameFile(urlDowloader, url, connection);
                if (Files.exists(Paths.get(urlDowloader.getFullPathToFile()))) {
                    writingToFile(urlDowloader, input, Paths.get(urlDowloader.getFullPathToFile()), url, connection);
                    return;
                }
                Files.copy(input, Paths.get(urlDowloader.getFullPathToFile()));
            }

        }
        if (Files.notExists(path)) {
            Files.createDirectories(path.getParent());
            Files.copy(input, path);
        }
        urlDowloader.setOutputFile(Paths.get(urlDowloader.getFullPathToFile()).toFile());
    }

    /**
     * Determining the name of the saved file (by default, the name is fixed, otherwise it is from the url)
     *
     * @param urlDowloader object with information about the downloaded file and him Internet resource
     * @param url          resource where you need to download the file
     * @param connection   url-connection
     */
    private void initAutoNameFile(URLDowloader urlDowloader, URL url, URLConnection connection) {
        if (url.getFile().isEmpty()) {
            urlDowloader.setNameOutputFile(URLDowloader.FIX_NAME_OUT_FILE);
            urlDowloader.setExtension(URLDowloader.DEFAULT_EXTENSION);
        } else {
            addNameAndExtensionForFile(url, connection, urlDowloader);
        }
    }

    /**
     * Adding a non-default name and extension to the downloaded file
     *
     * @param url          resource where you need to download the file
     * @param connection   url-connection
     * @param urlDowloader object with information about the downloaded file and him Internet resource
     */
    protected void addNameAndExtensionForFile(URL url, URLConnection connection, URLDowloader urlDowloader) {
        String nameFile = url.getFile();
        String mimeType = connection.getContentType();
        String extension = mimeType.substring(mimeType.lastIndexOf('/') + 1);
        String charset = "charset=";
        if (extension.contains(charset)) {
            urlDowloader.setCharsetName(extension.substring(extension.indexOf(charset) + charset.length()));
        }
        if (extension.contains(";")) {
            extension = extension.substring(0, extension.indexOf(';'));
        }
        if (nameFile.contains("/")) {
            if (nameFile.contains("?") && nameFile.indexOf('?') > nameFile.lastIndexOf('/')) {
                nameFile = nameFile.substring(nameFile.lastIndexOf('/') + 1, nameFile.indexOf('?'));
            } else if (!nameFile.contains("?")) {
                nameFile = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
            }
        }
        if (nameFile.contains(".")) {
            nameFile = nameFile.substring(0, nameFile.indexOf('.'));
        }
        urlDowloader.setNameOutputFile(nameFile);
        urlDowloader.setExtension("." + extension);
    }

    /**
     * If the parameter "isOpenAfterDown" is set to true, it opens the downloaded file in the default program to read
     * files of this extension
     *
     * @param urlDowloader object with information about the downloaded file and him Internet resource
     */
    protected void openFile(URLDowloader urlDowloader) {
        if (urlDowloader.isOpenAfterDown() && Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(urlDowloader.getOutputFile());
            } catch (IOException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
