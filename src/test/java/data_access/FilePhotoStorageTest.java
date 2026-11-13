package data_access;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilePhotoStorageTest {
    @TempDir
    Path tempDir;

    @Test
    void copiesPhotoIntoStorageFolder() throws IOException {
        Path original = Files.write(tempDir.resolve("Beach.JPG"), new byte[] {1, 2, 3});
        Path storageDir = tempDir.resolve("photos");

        Path stored = Paths.get(new FilePhotoStorage(storageDir).store(original.toString()));

        assertTrue(stored.startsWith(storageDir.toAbsolutePath()));
        assertTrue(stored.toString().endsWith(".jpg"));
        assertNotEquals(original.toAbsolutePath(), stored);
        assertArrayEquals(new byte[] {1, 2, 3}, Files.readAllBytes(stored));
    }

    @Test
    void alreadyStoredPhotoIsNotCopiedAgain() throws IOException {
        Path original = Files.write(tempDir.resolve("a.png"), new byte[] {9});
        FilePhotoStorage storage = new FilePhotoStorage(tempDir.resolve("photos"));
        String stored = storage.store(original.toString());

        assertEquals(stored, storage.store(stored));
        try (Stream<Path> files = Files.list(tempDir.resolve("photos"))) {
            assertEquals(1, files.count());
        }
    }

    @Test
    void missingPhotoThrows() {
        FilePhotoStorage storage = new FilePhotoStorage(tempDir.resolve("photos"));

        assertThrows(IOException.class, () -> storage.store(tempDir.resolve("gone.jpg").toString()));
    }
}
