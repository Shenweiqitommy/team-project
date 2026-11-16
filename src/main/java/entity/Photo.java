package entity;

import java.util.Objects;

/**
 * A photo attached to a journal entry. The image itself lives on disk; this entity only records where.
 */
public class Photo {
    private final String filePath;

    /**
     * Creates a photo that refers to an image file.
     * @param filePath the path of the stored image file
     * @throws IllegalArgumentException if filePath is null or blank
     */
    public Photo(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("A photo needs a file path.");
        }
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Photo)) {
            return false;
        }
        return filePath.equals(((Photo) other).filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }
}
