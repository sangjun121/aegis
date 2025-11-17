package me.sangjun.aegis.core.util;

import static me.sangjun.aegis.core.exception.AegisErrorCode.INVALID_URI_SYNTAX;
import static me.sangjun.aegis.core.exception.AegisErrorCode.IO_ERROR_READ_FILE;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import me.sangjun.aegis.core.exception.AegisException;

@Deprecated
public class ClassScanner {

    @Deprecated
    public static Path resolveClassesRoot(Class<?> primarySource) {
        try {
            URL location = primarySource.getProtectionDomain()
                    .getCodeSource()
                    .getLocation();

            return Paths.get(location.toURI());
        } catch (URISyntaxException e) {
            throw new AegisException(INVALID_URI_SYNTAX);
        }
    }

    public static List<Path> getClassFilePaths(Path basePath) {
        try {
            return Files.walk(basePath)
                    .filter(path -> path.toString().endsWith(".class"))
                    .toList();
        } catch (IOException e) {
            throw new AegisException(IO_ERROR_READ_FILE);
        }
    }

    @Deprecated
    public static String parseClassName(Path rootPath, Path classPath) {
        Path relativize = rootPath.relativize(classPath);
        String removedExt = relativize.toString().replace(".class", "");
        return removedExt.replace(File.separatorChar, '.');
    }
}
