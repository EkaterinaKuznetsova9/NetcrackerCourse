package utils2URLdown;

import java.io.IOException;

/**
 * Main class for working with the program
 *
 * Example with zip - http://www.rulit.me/download-books-608079.html?t=pdf
 * Example with ftp - ftp://ftp.intel.com/images/UserTroubleshootingPic2.JPG
 *
 * By default, files are saved in: \\src\\utils2URLdown\\downloadedFiles
 */
public class Main {

    public static void main(String[] args) throws IOException {
        URLDowloader urlDowloader = null;
        URLDownloaderService urlDownloaderService = new URLDownloaderService();
        switch (args.length) {
            case 1:
                urlDowloader = new URLDowloader(args[0]);
                break;
            case 2:
                urlDowloader = new URLDowloader(args[0], args[1]);
                break;
            case 3:
                urlDowloader = new URLDowloader(args[0], args[1], args[2]);
                break;
            default:
                System.out.println("You didn't specify any command line arguments or you specified too many of them");
        }
        //urlDowloader = new URLDowloader("http://www.rulit.me/download-books-608079.html?t=pdf", "default", "true");
        if (urlDowloader != null) {
            urlDownloaderService.downloadMainFile(urlDowloader);
        }
    }
}
