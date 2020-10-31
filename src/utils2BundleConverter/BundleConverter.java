package utils2BundleConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class BundleConverter {
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void createProperties(String nameClass) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object myInstanceBundle = Class.forName("utils2BundleConverter.Bundle").newInstance();
        Object contents;
        if (myInstanceBundle instanceof ListResourceBundle) {
            Method m = myInstanceBundle.getClass().getDeclaredMethod("getContents");
            contents = m.invoke(myInstanceBundle);
            Map<String, Long> tmp = new HashMap<>();
            File file = new File(nameClass + ".properties");
            setFile(file);
//            for (int i = 0; ) {
//                writeCompareOutputFile();
//            }
        }




    }

    public void writeCompareOutputFile(String outputText) {
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
