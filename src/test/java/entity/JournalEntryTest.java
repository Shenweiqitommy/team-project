package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JournalEntryTest {
    private static final LocalDate DAY = LocalDate.of(2026, 7, 1);

    @Test
    void holdsAtMostThreePhotos() {
        JournalEntry entry = new JournalEntry("trip", DAY, "Text.");
        entry.addPhoto(new Photo("1.jpg"));
        entry.addPhoto(new Photo("2.jpg"));
        assertTrue(entry.canAddPhoto());
        entry.addPhoto(new Photo("3.jpg"));

        assertFalse(entry.canAddPhoto());
        assertThrows(IllegalStateException.class, () -> entry.addPhoto(new Photo("4.jpg")));
        assertEquals(3, entry.getPhotos().size());
    }

    @Test
    void removingPhotoMakesRoom() {
        JournalEntry entry = new JournalEntry("trip", DAY, "Text.");
        entry.addPhoto(new Photo("1.jpg"));
        entry.addPhoto(new Photo("2.jpg"));
        entry.addPhoto(new Photo("3.jpg"));

        entry.removePhoto(new Photo("2.jpg"));

        assertTrue(entry.canAddPhoto());
        assertEquals("3.jpg", entry.getPhotos().get(1).getFilePath());
    }

    @Test
    void photoListCannotBeModifiedFromOutside() {
        JournalEntry entry = new JournalEntry("trip", DAY, "Text.");
        assertThrows(UnsupportedOperationException.class, () -> entry.getPhotos().add(new Photo("x.jpg")));
    }

    @Test
    void rejectsBlankText() {
        assertThrows(IllegalArgumentException.class, () -> new JournalEntry("trip", DAY, " "));
        JournalEntry entry = new JournalEntry("trip", DAY, "Text.");
        assertThrows(IllegalArgumentException.class, () -> entry.setText(""));
    }

    @Test
    void rejectsMissingTripOrDate() {
        assertThrows(IllegalArgumentException.class, () -> new JournalEntry(null, DAY, "Text."));
        assertThrows(IllegalArgumentException.class, () -> new JournalEntry("trip", null, "Text."));
    }
}
