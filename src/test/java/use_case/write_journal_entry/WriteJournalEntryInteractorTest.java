package use_case.write_journal_entry;

import data_access.InMemoryJournalDataAccessObject;
import entity.JournalEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WriteJournalEntryInteractorTest {
    private static final String TRIP = "tokyo-2026";
    private static final LocalDate DAY = LocalDate.of(2026, 7, 1);

    private InMemoryJournalDataAccessObject journal;
    private RecordingPresenter presenter;

    /** Pretends to copy a photo by prefixing its path with "stored/". */
    private final PhotoStorage fakeStorage = sourcePath -> "stored/" + sourcePath;

    @BeforeEach
    void setUp() {
        journal = new InMemoryJournalDataAccessObject();
        presenter = new RecordingPresenter();
    }

    private void run(PhotoStorage storage, String tripId, LocalDate date, String text, List<String> photos) {
        new WriteJournalEntryInteractor(journal, storage, presenter)
                .execute(new WriteJournalEntryInputData(tripId, date, text, photos));
    }

    @Test
    void savesNewEntryWithPhotos() {
        run(fakeStorage, TRIP, DAY, "Visited Senso-ji.", Arrays.asList("a.jpg", "b.PNG"));

        assertNull(presenter.error);
        assertFalse(presenter.output.isUpdatedExisting());
        assertEquals(Arrays.asList("stored/a.jpg", "stored/b.PNG"), presenter.output.getStoredPhotoPaths());

        JournalEntry saved = journal.get(TRIP, DAY);
        assertEquals("Visited Senso-ji.", saved.getText());
        assertEquals(2, saved.getPhotos().size());
        assertEquals("stored/a.jpg", saved.getPhotos().get(0).getFilePath());
    }

    @Test
    void savesEntryWithoutPhotos() {
        run(fakeStorage, TRIP, DAY, "Rest day.", Collections.emptyList());

        assertNull(presenter.error);
        assertTrue(journal.get(TRIP, DAY).getPhotos().isEmpty());
    }

    @Test
    void savingSameDateAgainReplacesEntry() {
        run(fakeStorage, TRIP, DAY, "First draft.", Collections.emptyList());
        run(fakeStorage, TRIP, DAY, "Second draft.", Collections.singletonList("c.jpg"));

        assertTrue(presenter.output.isUpdatedExisting());
        assertEquals("Second draft.", journal.get(TRIP, DAY).getText());
        assertEquals(1, journal.get(TRIP, DAY).getPhotos().size());
    }

    @Test
    void acceptsExactlyThreePhotos() {
        run(fakeStorage, TRIP, DAY, "Shrines.", Arrays.asList("1.jpg", "2.jpg", "3.jpg"));

        assertNull(presenter.error);
        assertEquals(3, journal.get(TRIP, DAY).getPhotos().size());
    }

    @Test
    void rejectsFourPhotos() {
        run(fakeStorage, TRIP, DAY, "Too many.", Arrays.asList("1.jpg", "2.jpg", "3.jpg", "4.jpg"));

        assertEquals("You can attach at most 3 photos.", presenter.error);
        assertFalse(journal.existsByTripAndDate(TRIP, DAY));
    }

    @Test
    void rejectsBlankText() {
        run(fakeStorage, TRIP, DAY, "   ", Collections.emptyList());

        assertEquals("Write something before saving.", presenter.error);
        assertFalse(journal.existsByTripAndDate(TRIP, DAY));
    }

    @Test
    void rejectsMissingTrip() {
        run(fakeStorage, "", DAY, "Text.", Collections.emptyList());

        assertEquals("Choose a trip first.", presenter.error);
    }

    @Test
    void rejectsMissingDate() {
        run(fakeStorage, TRIP, null, "Text.", Collections.emptyList());

        assertEquals("Choose a date for this entry.", presenter.error);
    }

    @Test
    void rejectsNonImageFile() {
        run(fakeStorage, TRIP, DAY, "Text.", Collections.singletonList("C:\\docs\\notes.txt"));

        assertEquals("notes.txt is not a supported image (jpg, jpeg, png, gif).", presenter.error);
        assertFalse(journal.existsByTripAndDate(TRIP, DAY));
    }

    @Test
    void reportsPhotoThatCannotBeCopied() {
        PhotoStorage failingStorage = sourcePath -> {
            throw new IOException("disk full");
        };
        run(failingStorage, TRIP, DAY, "Text.", Collections.singletonList("/pics/beach.jpg"));

        assertEquals("Could not save photo beach.jpg.", presenter.error);
        assertFalse(journal.existsByTripAndDate(TRIP, DAY));
    }

    /** Records what the interactor sent to the presenter. */
    private static class RecordingPresenter implements WriteJournalEntryOutputBoundary {
        private WriteJournalEntryOutputData output;
        private String error;

        @Override
        public void prepareSuccessView(WriteJournalEntryOutputData outputData) {
            output = outputData;
            error = null;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            error = errorMessage;
        }
    }
}
