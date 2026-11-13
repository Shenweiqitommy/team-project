package data_access;

import entity.JournalEntry;
import entity.Photo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileJournalDataAccessObjectTest {
    private static final LocalDate DAY = LocalDate.of(2026, 7, 1);

    @TempDir
    Path tempDir;

    @Test
    void entriesSurviveReopeningTheFile() {
        Path file = tempDir.resolve("nested").resolve("journal.json");
        JournalEntry entry = new JournalEntry("trip", DAY, "Line one.\nLine \"two\".");
        entry.addPhoto(new Photo("photos/a.jpg"));
        new FileJournalDataAccessObject(file).save(entry);

        FileJournalDataAccessObject reopened = new FileJournalDataAccessObject(file);

        assertTrue(reopened.existsByTripAndDate("trip", DAY));
        JournalEntry loaded = reopened.get("trip", DAY);
        assertEquals("Line one.\nLine \"two\".", loaded.getText());
        assertEquals(1, loaded.getPhotos().size());
        assertEquals("photos/a.jpg", loaded.getPhotos().get(0).getFilePath());
    }

    @Test
    void savingSameTripAndDateReplacesEntry() {
        FileJournalDataAccessObject journal = new FileJournalDataAccessObject(tempDir.resolve("journal.json"));
        journal.save(new JournalEntry("trip", DAY, "Old."));
        journal.save(new JournalEntry("trip", DAY, "New."));

        assertEquals("New.", new FileJournalDataAccessObject(tempDir.resolve("journal.json")).get("trip", DAY).getText());
    }

    @Test
    void missingFileMeansNoEntries() {
        FileJournalDataAccessObject journal = new FileJournalDataAccessObject(tempDir.resolve("none.json"));

        assertFalse(journal.existsByTripAndDate("trip", DAY));
        assertNull(journal.get("trip", DAY));
    }
}
