package interface_adapter.write_journal_entry;

import use_case.write_journal_entry.WriteJournalEntryOutputBoundary;
import use_case.write_journal_entry.WriteJournalEntryOutputData;

import java.time.format.DateTimeFormatter;

/**
 * Updates the journal entry view model with the result of saving an entry.
 */
public class WriteJournalEntryPresenter implements WriteJournalEntryOutputBoundary {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy");

    private final WriteJournalEntryViewModel viewModel;

    public WriteJournalEntryPresenter(WriteJournalEntryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(WriteJournalEntryOutputData outputData) {
        WriteJournalEntryState state = viewModel.getState();
        state.setText(outputData.getText());
        // Point the view at the stored copies so thumbnails survive the originals moving.
        state.setPhotoPaths(outputData.getStoredPhotoPaths());
        state.setErrorMessage(null);

        String date = outputData.getDate().format(DATE_FORMAT);
        if (outputData.isUpdatedExisting()) {
            state.setStatusMessage("Updated your entry for " + date + ".");
        }
        else {
            state.setStatusMessage("Saved your entry for " + date + ".");
        }
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        WriteJournalEntryState state = viewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setStatusMessage(null);
        viewModel.firePropertyChanged();
    }
}
