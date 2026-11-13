package use_case.write_journal_entry;

import entity.JournalEntry;

import java.time.LocalDate;

/**
 * Storage the Write Journal Entry use case needs for journal entries.
 */
public interface WriteJournalEntryDataAccessInterface {

    /**
     * Returns whether a trip already has an entry for a date.
     * @param tripId the trip
     * @param date the day
     * @return true if an entry exists
     */
    boolean existsByTripAndDate(String tripId, LocalDate date);

    /**
     * Saves an entry, replacing any existing entry for the same trip and date.
     * @param entry the entry to save
     */
    void save(JournalEntry entry);
}
