package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A traveller's journal entry for one day of a trip, with up to {@value #MAX_PHOTOS} photos.
 * A trip has at most one entry per date.
 */
public class JournalEntry {
    public static final int MAX_PHOTOS = 3;

    private final String tripId;
    private final LocalDate date;
    private String text;
    private final List<Photo> photos = new ArrayList<>();

    /**
     * Creates a journal entry with no photos.
     * @param tripId the id of the trip this entry belongs to
     * @param date the day this entry is about
     * @param text what the traveller wrote
     * @throws IllegalArgumentException if any argument is missing or the text is blank
     */
    public JournalEntry(String tripId, LocalDate date, String text) {
        if (tripId == null || tripId.isBlank()) {
            throw new IllegalArgumentException("A journal entry must belong to a trip.");
        }
        if (date == null) {
            throw new IllegalArgumentException("A journal entry needs a date.");
        }
        this.tripId = tripId;
        this.date = date;
        setText(text);
    }

    public String getTripId() {
        return tripId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    /**
     * Replaces the entry's text.
     * @param text the new text
     * @throws IllegalArgumentException if the text is null or blank
     */
    public void setText(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("A journal entry cannot be empty.");
        }
        this.text = text;
    }

    /**
     * Returns the attached photos in the order they were added.
     * @return a read-only view of the photos
     */
    public List<Photo> getPhotos() {
        return Collections.unmodifiableList(photos);
    }

    /**
     * Returns whether another photo can be attached.
     * @return true if the entry has fewer than {@value #MAX_PHOTOS} photos
     */
    public boolean canAddPhoto() {
        return photos.size() < MAX_PHOTOS;
    }

    /**
     * Attaches a photo to this entry.
     * @param photo the photo to attach
     * @throws IllegalStateException if the entry already has {@value #MAX_PHOTOS} photos
     */
    public void addPhoto(Photo photo) {
        if (!canAddPhoto()) {
            throw new IllegalStateException("A journal entry can have at most " + MAX_PHOTOS + " photos.");
        }
        photos.add(photo);
    }

    /**
     * Removes a photo from this entry, if it is attached.
     * @param photo the photo to remove
     */
    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }
}
