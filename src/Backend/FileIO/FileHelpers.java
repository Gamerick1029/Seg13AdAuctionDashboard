package Backend.FileIO;

import java.io.*;

public class FileHelpers {

    // Modified function based on https://stackoverflow.com/a/14411695
    public static int countLines(File file) {
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))){
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean endsWithoutNewLine = false;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }
            if(endsWithoutNewLine) {
                ++count;
            }
            return count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
