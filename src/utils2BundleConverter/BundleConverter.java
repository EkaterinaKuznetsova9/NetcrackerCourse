package utils2BundleConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * A class that works with converting from ListResourceBundle to ListResourceBundle
 */
public class BundleConverter {
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /*
    Creates a file with the extension based on the content of an existing class .property
     */
    public void createProperties(String nameClass) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object myInstanceBundle = Class.forName("utils2BundleConverter.Bundle").newInstance();
        if (myInstanceBundle instanceof ListResourceBundle) {
            Method m = myInstanceBundle.getClass().getDeclaredMethod("getContents");
            File file = new File("src\\utils2BundleConverter\\" + nameClass + ".properties");
            setFile(file);
            Object tmp = m.invoke(myInstanceBundle);
            Object[][] contents = (Object[][])tmp;
            for (Object[] content : contents) {
                writeCompareOutputFile(content[0] + "=" + content[1]);
            }
        }
    }

    protected void copyComments() {}

    /**
     * Writes data line by line to a file
     *
     * @param outputText string to write in the key=value format
     */
    protected void writeCompareOutputFile(String outputText) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(), true));
            String lineSeparator = System.getProperty("line.separator");
            writer.write(outputText + lineSeparator);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
