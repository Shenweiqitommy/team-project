package use_case.write_journal_entry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The saved journal entry, as reported back to the presenter.
 */
public class WriteJournalEntryOutputData {
    private final String tripId;
    private final LocalDate date;
    private final String text;
    private final List<String> storedPhotoPaths;
    private final boolean updatedExisting;

    /**
     * Creates the output data.
     * @param tripId the trip the entry belongs to
     * @param date the day the entry is about
     * @param text the saved text
     * @param storedPhotoPaths where the photos were stored, in order
     * @param updatedExisting true if an entry for this date already existed and was replaced
     */
    public WriteJournalEntryOutputData(String tripId, LocalDate date, String text,
                                       List<String> storedPhotoPaths, boolean updatedExisting) {
        this.tripId = tripId;
        this.date = date;
        this.text = text;
        this.storedPhotoPaths = new ArrayList<>(storedPhotoPaths);
        this.updatedExisting = updatedExisting;
    }

    public String getTripId() {
        return tripId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public List<String> getStoredPhotoPaths() {
        return new ArrayList<>(storedPhotoPaths);
    }

    public boolean isUpdatedExisting() {
        return updatedExisting;
    }
}
