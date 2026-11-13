package use_case.write_journal_entry;

import entity.JournalEntry;
import entity.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Saves a traveller's journal entry for one day of a trip, with up to {@value JournalEntry#MAX_PHOTOS} photos.
 */
public class WriteJournalEntryInteractor implements WriteJournalEntryInputBoundary {
    /** Image file extensions the journal accepts. */
    public static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    private final WriteJournalEntryDataAccessInterface journalDataAccess;
    private final PhotoStorage photoStorage;
    private final WriteJournalEntryOutputBoundary presenter;

    /**
     * Creates the interactor.
     * @param journalDataAccess where entries are saved
     * @param photoStorage where photos are copied
     * @param presenter what shows the result
     */
    public WriteJournalEntryInteractor(WriteJournalEntryDataAccessInterface journalDataAccess,
                                       PhotoStorage photoStorage,
                                       WriteJournalEntryOutputBoundary presenter) {
        this.journalDataAccess = journalDataAccess;
        this.photoStorage = photoStorage;
        this.presenter = presenter;
    }

    @Override
    public void execute(WriteJournalEntryInputData inputData) {
        String error = validate(inputData);
        if (error != null) {
            presenter.prepareFailView(error);
            return;
        }

        List<String> storedPaths = new ArrayList<>();
        for (String sourcePath : inputData.getPhotoPaths()) {
            try {
                storedPaths.add(photoStorage.store(sourcePath));
            }
            catch (IOException e) {
                presenter.prepareFailView("Could not save photo " + fileName(sourcePath) + ".");
                return;
            }
        }

        JournalEntry entry = new JournalEntry(inputData.getTripId(), inputData.getDate(), inputData.getText());
        for (String path : storedPaths) {
            entry.addPhoto(new Photo(path));
        }

        boolean updatedExisting = journalDataAccess.existsByTripAndDate(entry.getTripId(), entry.getDate());
        journalDataAccess.save(entry);

        presenter.prepareSuccessView(new WriteJournalEntryOutputData(
                entry.getTripId(), entry.getDate(), entry.getText(), storedPaths, updatedExisting));
    }

    /**
     * Checks the input against the use case's rules.
     * @param inputData what the user submitted
     * @return a message for the user, or null if the input is valid
     */
    private String validate(WriteJournalEntryInputData inputData) {
        if (inputData.getTripId() == null || inputData.getTripId().isBlank()) {
            return "Choose a trip first.";
        }
        if (inputData.getDate() == null) {
            return "Choose a date for this entry.";
        }
        if (inputData.getText() == null || inputData.getText().isBlank()) {
            return "Write something before saving.";
        }
        if (inputData.getPhotoPaths().size() > JournalEntry.MAX_PHOTOS) {
            return "You can attach at most " + JournalEntry.MAX_PHOTOS + " photos.";
        }
        for (String path : inputData.getPhotoPaths()) {
            if (!isSupportedImage(path)) {
                return fileName(path) + " is not a supported image (jpg, jpeg, png, gif).";
            }
        }
        return null;
    }

    private static boolean isSupportedImage(String path) {
        if (path == null) {
            return false;
        }
        int dot = path.lastIndexOf('.');
        if (dot < 0) {
            return false;
        }
        return SUPPORTED_EXTENSIONS.contains(path.substring(dot + 1).toLowerCase(Locale.ROOT));
    }

    private static String fileName(String path) {
        if (path == null) {
            return "(missing file)";
        }
        int separator = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        return path.substring(separator + 1);
    }
}
