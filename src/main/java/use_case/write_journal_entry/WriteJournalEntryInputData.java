package use_case.write_journal_entry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * What the user submitted when saving a journal entry.
 */
public class WriteJournalEntryInputData {
    private final String tripId;
    private final LocalDate date;
    private final String text;
    private final List<String> photoPaths;

    /**
     * Creates the input data.
     * @param tripId the trip the entry belongs to
     * @param date the day the entry is about
     * @param text the entry text
     * @param photoPaths paths of the images the user chose, in order
     */
    public WriteJournalEntryInputData(String tripId, LocalDate date, String text, List<String> photoPaths) {
        this.tripId = tripId;
        this.date = date;
        this.text = text;
        this.photoPaths = photoPaths == null ? new ArrayList<>() : new ArrayList<>(photoPaths);
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

    public List<String> getPhotoPaths() {
        return new ArrayList<>(photoPaths);
    }
}
