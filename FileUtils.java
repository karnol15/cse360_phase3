import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static void ensureDirectoryExists(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("Failed to create directory: " + directoryPath);
            }
        }
    }

    public static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        ensureDirectoryExists(file.getParent());
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Failed to create file: " + filePath);
            }
        }
        return file;
    }
}
