package use_case.write_journal_entry;

/**
 * Input boundary for the Write Journal Entry use case.
 */
public interface WriteJournalEntryInputBoundary {

    /**
     * Saves a journal entry, creating it or replacing the existing entry for that date.
     * @param inputData what the user submitted
     */
    void execute(WriteJournalEntryInputData inputData);
}
