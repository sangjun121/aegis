package me.sangjun.aegis.core.scanner;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import me.sangjun.aegis.core.annotations.AegisDomain;

public class DomainScanner {

    /**
     * AegisDomain 어노테이션을 기반으로만 도메인을 탐색하는 경우 진입점
     *
     * @param primarySource : 사용자 어플리케이션의 main 클래스이다.
     * @return 스캔된 도메인을 제공한다.
     */
    public Set<Class<?>> scanDomain(Class<?> primarySource, Set<String> basePackages) {
        Set<Class<?>> domains = new HashSet<>();

        ClassLoader loader = primarySource.getClassLoader(); // 1. primarySource을 로드한 ClassLoader 불러오기

        for (String basePackage : basePackages) {
            String path = basePackage.replace('.', '/'); // 2. classLoader가 알아볼 수 있는 path 포멧으로 수정

            try {
                Enumeration<URL> resources = loader.getResources(
                        path); //3. classpath에서 basePackage 하위 경로에 매핑되는 모든 리소스 조회
                while (resources.hasMoreElements()) { // 4. 전체 리소스 하나씩 순회
                    URL url = resources.nextElement();
                    String protocol = url.getProtocol();

                    if ("file".equals(protocol)) { // 5-1. 리소스의 형태가 디렉토리의 파일인 경우, 초기 구현과 동일하게 scan 수행 진입점
                        File dir = new File(url.getPath());
                        scanDirectoryResource(loader, basePackage, dir, domains);
                    } else if ("jar".equals(protocol)) { // 5-2. 리소스의 형태가 압축된 jar 파일인 경우, scan 수행 진입점
                        scanJarResource(loader, basePackage, url, domains);
                    }
                }
            } catch (IOException e) {
                // TODO: 예외 발생 및 로그 처리
            }
        }

        return domains;
    }

    private void scanDirectoryResource(ClassLoader loader,
                                          String currentPackage,
                                          File dir,
                                          Set<Class<?>> domains) {

        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                String subPackage = currentPackage + "." + file.getName();
                scanDirectoryResource(loader, subPackage, file, domains); // 재귀 탐색으로 하위 패키지 탐색 수행
            } else if (file.getName().endsWith(".class")) {
                String parsedClassName = file.getName().substring(0, file.getName().length() - 6); // 확장자 제거
                String fqcnClassName = currentPackage + "." + parsedClassName;
                checkAegisDomain(loader, fqcnClassName, domains);
            }
        }
    }

    private void scanJarResource(ClassLoader loader,
                                 String basePackage,
                                 URL url,
                                 Set<Class<?>> domains) {

        try {
            JarURLConnection conn = (JarURLConnection) url.openConnection();
            try (JarFile jarFile = conn.getJarFile()) {
                String packagePath = basePackage.replace('.', '/') + "/";

                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();

                    if (name.startsWith(packagePath)
                            && name.endsWith(".class")
                            && !entry.isDirectory()) {

                        String className = name
                                .substring(0, name.length() - 6)
                                .replace('/', '.');

                        checkAegisDomain(loader, className, domains);
                    }
                }
            }
        } catch (IOException e) {
            //TODO: 예외 및 로그 처리
        }
    }

    private void checkAegisDomain(ClassLoader loader,
                                   String className,
                                   Set<Class<?>> domains) {

        try {
            Class<?> clazz = Class.forName(className, false, loader);

            if (clazz.isAnnotationPresent(AegisDomain.class)) {
                domains.add(clazz);
            }
        } catch (ClassNotFoundException e) {
            //TODO: 예외 및 로그 처리
        }
    }
}
