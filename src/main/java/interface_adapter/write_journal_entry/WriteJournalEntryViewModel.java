package interface_adapter.write_journal_entry;

import interface_adapter.ViewModel;

/**
 * View model for the journal entry view.
 */
public class WriteJournalEntryViewModel extends ViewModel<WriteJournalEntryState> {
    public static final String VIEW_NAME = "write journal entry";

    public WriteJournalEntryViewModel() {
        super(VIEW_NAME);
        setState(new WriteJournalEntryState());
    }
}
