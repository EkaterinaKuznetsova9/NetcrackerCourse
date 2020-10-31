package utils2BundleConverter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ListResourceBundle;

/**
 * Main class for working with the program
 * НЕ ДОДЕЛАН
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (args.length == 0) {
            throw new IOException("The class name cannot be empty");
        }
        //Locale l = new Locale("en","US","WINDOWS");
        //Locale l = Locale.getDefault();
        //String nameClass = "Bundle";
        BundleConverter bundleConverter = new BundleConverter();
        bundleConverter.createProperties(args[0]);
    }
}
