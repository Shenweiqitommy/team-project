package interface_adapter.write_journal_entry;

import use_case.write_journal_entry.WriteJournalEntryInputBoundary;
import use_case.write_journal_entry.WriteJournalEntryInputData;

import java.time.LocalDate;
import java.util.List;

/**
 * Turns the view's Save action into a call to the Write Journal Entry use case.
 */
public class WriteJournalEntryController {
    private final WriteJournalEntryInputBoundary interactor;

    public WriteJournalEntryController(WriteJournalEntryInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Saves a journal entry.
     * @param tripId the trip the entry belongs to
     * @param date the day the entry is about
     * @param text the entry text
     * @param photoPaths paths of the images the user chose
     */
    public void execute(String tripId, LocalDate date, String text, List<String> photoPaths) {
        interactor.execute(new WriteJournalEntryInputData(tripId, date, text, photoPaths));
    }
}
