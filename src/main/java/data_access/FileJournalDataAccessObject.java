package data_access;

import entity.JournalEntry;
import entity.Photo;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.write_journal_entry.WriteJournalEntryDataAccessInterface;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Stores journal entries in a JSON file, so they survive restarting the app.
 *
 * <p>File format:
 * <pre>
 * {"entries": [{"tripId": "...", "date": "2026-07-01", "text": "...", "photos": ["path", ...]}]}
 * </pre>
 */
public class FileJournalDataAccessObject implements WriteJournalEntryDataAccessInterface {
    private final Path jsonFile;
    private final Map<String, JournalEntry> entries = new LinkedHashMap<>();

    /**
     * Opens the journal file, loading any entries already in it.
     * @param jsonFile the file to read and write; created on the first save if missing
     * @throws UncheckedIOException if the file exists but cannot be read
     */
    public FileJournalDataAccessObject(Path jsonFile) {
        this.jsonFile = jsonFile;
        if (Files.exists(jsonFile)) {
            load();
        }
    }

    @Override
    public boolean existsByTripAndDate(String tripId, LocalDate date) {
        return entries.containsKey(key(tripId, date));
    }

    @Override
    public void save(JournalEntry entry) {
        entries.put(key(entry.getTripId(), entry.getDate()), entry);
        write();
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

    private void load() {
        try {
            String content = new String(Files.readAllBytes(jsonFile), StandardCharsets.UTF_8);
            JSONArray array = new JSONObject(content).getJSONArray("entries");
            for (int i = 0; i < array.length(); i++) {
                JournalEntry entry = fromJson(array.getJSONObject(i));
                entries.put(key(entry.getTripId(), entry.getDate()), entry);
            }
        }
        catch (IOException e) {
            throw new UncheckedIOException("Could not read journal file " + jsonFile, e);
        }
    }

    private void write() {
        JSONArray array = new JSONArray();
        for (JournalEntry entry : entries.values()) {
            array.put(toJson(entry));
        }
        JSONObject root = new JSONObject().put("entries", array);
        try {
            Path parent = jsonFile.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.write(jsonFile, root.toString(2).getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            throw new UncheckedIOException("Could not write journal file " + jsonFile, e);
        }
    }

    private static JSONObject toJson(JournalEntry entry) {
        JSONArray photos = new JSONArray();
        for (Photo photo : entry.getPhotos()) {
            photos.put(photo.getFilePath());
        }
        return new JSONObject()
                .put("tripId", entry.getTripId())
                .put("date", entry.getDate().toString())
                .put("text", entry.getText())
                .put("photos", photos);
    }

    private static JournalEntry fromJson(JSONObject json) {
        JournalEntry entry = new JournalEntry(
                json.getString("tripId"),
                LocalDate.parse(json.getString("date")),
                json.getString("text"));
        JSONArray photos = json.optJSONArray("photos");
        if (photos != null) {
            for (int i = 0; i < photos.length(); i++) {
                entry.addPhoto(new Photo(photos.getString(i)));
            }
        }
        return entry;
    }
}
