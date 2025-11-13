package me.sangjun.aegis.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ClassScanner {

    public static Path resolveClassesRoot(Class<?> primarySource) {
        try {
            URL location = primarySource.getProtectionDomain()
                    .getCodeSource()
                    .getLocation();

            return Paths.get(location.toURI());
        } catch (Exception e) {
            //TODO: 예외 로그 및 부트스트랩 실패
        }
    }

    public static List<Path> getClassFilePaths(Path basePath) {
        try {
            return Files.walk(basePath)
                    .filter(path -> path.toString().endsWith(".class"))
                    .toList();
        } catch (IOException e) {
            //TODO: 예외 로그 및 부트스트랩 실패
        }
    }

    public static String parseClassName(Path rootPath, Path classPath) {
        Path relativize = rootPath.relativize(classPath);
        String removedExt = relativize.toString().replace(".class", "");
        return removedExt.replace(File.separatorChar, '.');
    }
}
