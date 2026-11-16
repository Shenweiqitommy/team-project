package use_case.write_journal_entry;

import java.io.IOException;

/**
 * Keeps a copy of a user's chosen image so the journal still shows it if the original file moves.
 * Kept separate from {@link WriteJournalEntryDataAccessInterface} so image storage can change on its own.
 */
public interface PhotoStorage {

    /**
     * Stores an image and returns where the stored copy is.
     * Storing an image that is already in storage returns its path unchanged.
     * @param sourcePath the image the user chose
     * @return the path of the stored copy
     * @throws IOException if the image cannot be read or copied
     */
    String store(String sourcePath) throws IOException;
}
