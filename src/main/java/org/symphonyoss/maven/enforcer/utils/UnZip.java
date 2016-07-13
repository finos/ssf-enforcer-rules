package org.symphonyoss.maven.enforcer.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This utility extracts files and directories of a standard zip file to
 * a destination directory.
 * @author www.codejava.net
 *
 */
public class UnZip {
    /**
     * Extracts a Jar file
     * @param jarPath
     * @param destDir
     * @param fileNameToExtract
     * @throws IOException
     */
    public boolean extractFileFromJar(String jarPath, String destDir, String fileNameToExtract) throws IOException {

        File dest = new File(destDir);
        if (!dest.exists()) {
            dest.mkdir();
        }
        java.util.jar.JarFile jar = new java.util.jar.JarFile(jarPath);
        java.util.Enumeration enumEntries = jar.entries();
        boolean fileFound = false;
        while (enumEntries.hasMoreElements()) {
            java.util.jar.JarEntry jarEntry = (java.util.jar.JarEntry) enumEntries.nextElement();
            if (fileNameToExtract.equals(jarEntry.getName()) || jarEntry.getName().endsWith("/"+fileNameToExtract)) {
                fileFound = true;
                java.io.File f = new java.io.File(destDir + java.io.File.separator + jarEntry.getName().substring(jarEntry.getName().lastIndexOf('/')));
                java.io.InputStream is = jar.getInputStream(jarEntry); // get the input stream
                java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
                while (is.available() > 0) {  // write contents of 'is' to 'fos'
                    fos.write(is.read());
                }
                fos.close();
                is.close();
                return true;
            }
        }

        dest.delete();
        return false;
    }
}
