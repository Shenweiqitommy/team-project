package data_access;

import use_case.write_journal_entry.PhotoStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Copies chosen photos into the app's photo folder under unique names.
 */
public class FilePhotoStorage implements PhotoStorage {
    private final Path photoDirectory;

    /**
     * Creates the storage.
     * @param photoDirectory the folder stored photos are copied into; created when needed
     */
    public FilePhotoStorage(Path photoDirectory) {
        this.photoDirectory = photoDirectory.toAbsolutePath().normalize();
    }

    @Override
    public String store(String sourcePath) throws IOException {
        Path source;
        try {
            source = Paths.get(sourcePath).toAbsolutePath().normalize();
        }
        catch (InvalidPathException e) {
            throw new IOException("Not a valid file path: " + sourcePath, e);
        }

        // Re-saving an entry passes back photos that are already stored; don't copy them again.
        if (source.startsWith(photoDirectory)) {
            return source.toString();
        }
        if (!Files.isRegularFile(source)) {
            throw new IOException("Photo not found: " + source);
        }

        Files.createDirectories(photoDirectory);
        Path target = photoDirectory.resolve(UUID.randomUUID() + extensionOf(source));
        Files.copy(source, target);
        return target.toString();
    }

    private static String extensionOf(Path file) {
        String name = file.getFileName().toString();
        int dot = name.lastIndexOf('.');
        if (dot < 0) {
            return "";
        }
        return name.substring(dot).toLowerCase();
    }
}
