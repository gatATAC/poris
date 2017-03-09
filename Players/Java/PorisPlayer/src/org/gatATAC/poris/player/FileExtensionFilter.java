package org.gatATAC.poris.player;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Class to implement a file filter by extension
 * @author txinto
 */
public class FileExtensionFilter extends FileFilter {

    /**
     * The extension to filter
     */
    private final String extension;
    /**
     * The description for the extension filtering
     */
    private final String description;
    /**
     * The extensions considered ok
     */
    private final String[] okFileExtensions;

    /**
     * Constructor
     * @param extension the extension to filter
     * @param description the description of the extension filter
     */
    public FileExtensionFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
        this.okFileExtensions =
                new String[]{this.extension, this.extension.toLowerCase(), this.extension.toUpperCase()};
    }

    /**
     * The accept function
     * @param file file to consider
     * @return true if accepted
     */
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        for (String ext : okFileExtensions) {
            if (file.getName().toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the filter description
     * @return the filter description
     */
    @Override
    public String getDescription() {
        return this.description;
    }
}
