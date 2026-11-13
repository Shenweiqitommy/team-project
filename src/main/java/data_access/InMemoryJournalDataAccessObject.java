package data_access;

import entity.JournalEntry;
import use_case.write_journal_entry.WriteJournalEntryDataAccessInterface;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Keeps journal entries in memory only. Useful for tests and for trying the UI without touching files.
 */
public class InMemoryJournalDataAccessObject implements WriteJournalEntryDataAccessInterface {
    private final Map<String, JournalEntry> entries = new HashMap<>();

    @Override
    public boolean existsByTripAndDate(String tripId, LocalDate date) {
        return entries.containsKey(key(tripId, date));
    }

    @Override
    public void save(JournalEntry entry) {
        entries.put(key(entry.getTripId(), entry.getDate()), entry);
    }

    /**
     * Returns the entry for a trip and date.
     * @param tripId the trip
     * @param date the day
     * @return the entry, or null if there is none
     */
    public JournalEntry get(String tripId, LocalDate date) {
        return entries.get(key(tripId, date));
    }

    private static String key(String tripId, LocalDate date) {
        return tripId + "|" + date;
    }
}
