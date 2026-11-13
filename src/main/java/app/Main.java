package app;

import data_access.FileJournalDataAccessObject;
import data_access.FilePhotoStorage;
import interface_adapter.write_journal_entry.WriteJournalEntryController;
import interface_adapter.write_journal_entry.WriteJournalEntryPresenter;
import interface_adapter.write_journal_entry.WriteJournalEntryViewModel;
import use_case.write_journal_entry.WriteJournalEntryInteractor;
import view.WriteJournalEntryView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Starts the app. For now it only shows the journal entry view; other use cases' views get wired in here
 * (or in an AppBuilder) as they are written.
 */
public class Main {
    private static final Path DATA_DIRECTORY = Paths.get("data");

    /**
     * Placeholder until the Create Trip use case exists and the user can pick a real trip.
     */
    private static final String DEMO_TRIP_ID = "demo-trip";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShow);
    }

    private static void createAndShow() {
        FileJournalDataAccessObject journalDataAccess =
                new FileJournalDataAccessObject(DATA_DIRECTORY.resolve("journal.json"));
        FilePhotoStorage photoStorage = new FilePhotoStorage(DATA_DIRECTORY.resolve("photos"));

        WriteJournalEntryViewModel viewModel = new WriteJournalEntryViewModel();
        viewModel.getState().setTripId(DEMO_TRIP_ID);
        WriteJournalEntryPresenter presenter = new WriteJournalEntryPresenter(viewModel);
        WriteJournalEntryInteractor interactor =
                new WriteJournalEntryInteractor(journalDataAccess, photoStorage, presenter);
        WriteJournalEntryController controller = new WriteJournalEntryController(interactor);
        WriteJournalEntryView view = new WriteJournalEntryView(viewModel, controller);

        JFrame frame = new JFrame("Travel Planner & Journal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
