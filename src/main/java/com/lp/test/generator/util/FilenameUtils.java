package com.lp.test.generator.util;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class to perform various operations when dealing with file names.
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class FilenameUtils {

    /**
     * Normalize the passed String to represent a valid file name by allowing only:
     * <ul>
     * <li>a-z</li>
     * <li>A-Z</li>
     * <li>0-9</li>
     * <li>.</li>
     * <li>_</li>
     * </ul>
     * Invalid characters are replaced with underscore, and the resulting name is then returned as lowercase.
     * Examples:
     * <pre>
     * null                -->   ""
     * "     "             -->   ""
     * "abc/def"           -->   "abc_def"
     * "ABC DEF"           -->   "abc_def"
     * "ABC DEF"           -->   "abc_def"
     * </pre>
     *
     * @param fileName the file name to normalize
     * @return the normalized file name, or "" if blank
     */
    public static String normalize(String fileName) {
        fileName = StringUtils.trimToEmpty(fileName);
        if (!fileName.isEmpty()) {
            return fileName.replaceAll("[^a-zA-Z0-9.-]", "_").toLowerCase();
        }
        return "";
    }

}
