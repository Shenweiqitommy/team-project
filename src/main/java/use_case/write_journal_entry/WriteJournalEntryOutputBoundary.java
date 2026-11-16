package use_case.write_journal_entry;

/**
 * Output boundary for the Write Journal Entry use case.
 */
public interface WriteJournalEntryOutputBoundary {

    /**
     * Shows that the entry was saved.
     * @param outputData the saved entry
     */
    void prepareSuccessView(WriteJournalEntryOutputData outputData);

    /**
     * Shows why the entry could not be saved.
     * @param errorMessage a message for the user
     */
    void prepareFailView(String errorMessage);
}
