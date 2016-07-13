package org.symphonyoss.maven.enforcer.utils;

import java.io.*;
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
     * @param destinationFolder
     * @param fileNameToExtract
     * @throws IOException
     */
    public boolean extractFileFromJar(String jarPath, File destinationFolder, String fileNameToExtract) throws IOException {

        java.util.jar.JarFile jar = new java.util.jar.JarFile(jarPath);
        java.util.Enumeration enumEntries = jar.entries();
        boolean fileFound = false;
        while (enumEntries.hasMoreElements()) {
            java.util.jar.JarEntry jarEntry = (java.util.jar.JarEntry) enumEntries.nextElement();
            if (fileNameToExtract.equals(jarEntry.getName()) || jarEntry.getName().endsWith("/"+fileNameToExtract)) {
                fileFound = true;
                File f = new java.io.File(destinationFolder, File.separator + jarEntry.getName().substring(jarEntry.getName().lastIndexOf('/')));
                InputStream is = jar.getInputStream(jarEntry); // get the input stream
                FileOutputStream fos = new FileOutputStream(f);
                while (is.available() > 0) {  // write contents of 'is' to 'fos'
                    fos.write(is.read());
                }
                fos.close();
                is.close();
                return true;
            }
        }

        return false;
    }
}
