package interface_adapter.write_journal_entry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * What the journal entry view shows: the entry being edited and any message for the user.
 */
public class WriteJournalEntryState {
    private String tripId = "";
    private LocalDate date = LocalDate.now();
    private String text = "";
    private List<String> photoPaths = new ArrayList<>();
    private String errorMessage;
    private String statusMessage;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getPhotoPaths() {
        return new ArrayList<>(photoPaths);
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = new ArrayList<>(photoPaths);
    }

    public void addPhotoPath(String path) {
        photoPaths.add(path);
    }

    public void removePhotoPath(String path) {
        photoPaths.remove(path);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
