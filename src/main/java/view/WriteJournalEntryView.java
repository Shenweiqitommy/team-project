package view;

import entity.JournalEntry;
import interface_adapter.write_journal_entry.WriteJournalEntryController;
import interface_adapter.write_journal_entry.WriteJournalEntryState;
import interface_adapter.write_journal_entry.WriteJournalEntryViewModel;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Lets the traveller write a journal entry for one day of a trip and attach up to three photos.
 */
public class WriteJournalEntryView extends JPanel implements PropertyChangeListener {
    private static final int THUMBNAIL_SIZE = 120;
    private static final Color ERROR_COLOR = new Color(176, 0, 32);
    private static final Color STATUS_COLOR = new Color(0, 110, 50);

    private final WriteJournalEntryViewModel viewModel;
    private final WriteJournalEntryController controller;

    private final JSpinner dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
    private final JTextArea textArea = new JTextArea(10, 40);
    private final JLabel photoCountLabel = new JLabel();
    private final JPanel thumbnailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
    private final JButton addPhotoButton = new JButton("Add photo…");
    private final JButton saveButton = new JButton("Save entry");
    private final JLabel messageLabel = new JLabel(" ");

    /**
     * Creates the view.
     * @param viewModel the view model this view displays
     * @param controller the controller the Save button calls
     */
    public WriteJournalEntryView(WriteJournalEntryViewModel viewModel, WriteJournalEntryController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        add(buildHeader(), BorderLayout.NORTH);
        add(buildTextArea(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);

        wireListeners();
        refreshPhotos();
    }

    private JPanel buildHeader() {
        JLabel title = new JLabel("Travel journal");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        JPanel dateRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        dateRow.add(new JLabel("Date: "));
        dateRow.add(dateSpinner);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        dateRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(dateRow);
        return header;
    }

    private JScrollPane buildTextArea() {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("What happened today?"));
        return scrollPane;
    }

    private JPanel buildFooter() {
        JPanel photoHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        photoHeader.add(photoCountLabel);
        photoHeader.add(addPhotoButton);

        thumbnailPanel.setPreferredSize(new Dimension(0, THUMBNAIL_SIZE + 40));

        JPanel actions = new JPanel(new BorderLayout());
        actions.add(messageLabel, BorderLayout.CENTER);
        actions.add(saveButton, BorderLayout.EAST);

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        for (JPanel row : new JPanel[] {photoHeader, thumbnailPanel, actions}) {
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            footer.add(row);
        }
        return footer;
    }

    private void wireListeners() {
        dateSpinner.addChangeListener(e -> viewModel.getState().setDate(toLocalDate((Date) dateSpinner.getValue())));

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                viewModel.getState().setText(textArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                viewModel.getState().setText(textArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                viewModel.getState().setText(textArea.getText());
            }
        });

        addPhotoButton.addActionListener(e -> choosePhoto());

        saveButton.addActionListener(e -> {
            WriteJournalEntryState state = viewModel.getState();
            controller.execute(state.getTripId(), state.getDate(), state.getText(), state.getPhotoPaths());
        });
    }

    private void choosePhoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Images (jpg, png, gif)", "jpg", "jpeg", "png", "gif"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            viewModel.getState().addPhotoPath(chooser.getSelectedFile().getAbsolutePath());
            refreshPhotos();
        }
    }

    /**
     * Redraws the thumbnails from the state and enables "Add photo" only while there is room.
     */
    private void refreshPhotos() {
        thumbnailPanel.removeAll();
        for (String path : viewModel.getState().getPhotoPaths()) {
            thumbnailPanel.add(buildThumbnail(path));
        }
        int count = viewModel.getState().getPhotoPaths().size();
        photoCountLabel.setText("Photos (" + count + "/" + JournalEntry.MAX_PHOTOS + ")");
        addPhotoButton.setEnabled(count < JournalEntry.MAX_PHOTOS);
        thumbnailPanel.revalidate();
        thumbnailPanel.repaint();
    }

    private JPanel buildThumbnail(String path) {
        JLabel image = new JLabel();
        image.setHorizontalAlignment(SwingConstants.CENTER);
        image.setPreferredSize(new Dimension(THUMBNAIL_SIZE, THUMBNAIL_SIZE));
        ImageIcon icon = new ImageIcon(path);
        if (icon.getIconWidth() > 0) {
            image.setIcon(new ImageIcon(scaleToFit(icon.getImage(), icon.getIconWidth(), icon.getIconHeight())));
        }
        else {
            image.setText(new File(path).getName());
        }
        image.setToolTipText(path);

        JButton remove = new JButton("Remove");
        remove.addActionListener(e -> {
            viewModel.getState().removePhotoPath(path);
            refreshPhotos();
        });

        JPanel thumbnail = new JPanel(new BorderLayout(0, 4));
        thumbnail.add(image, BorderLayout.CENTER);
        thumbnail.add(remove, BorderLayout.SOUTH);
        return thumbnail;
    }

    private static Image scaleToFit(Image image, int width, int height) {
        double scale = Math.min((double) THUMBNAIL_SIZE / width, (double) THUMBNAIL_SIZE / height);
        int scaledWidth = Math.max(1, (int) (width * scale));
        int scaledHeight = Math.max(1, (int) (height * scale));
        return image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
    }

    private static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        WriteJournalEntryState state = (WriteJournalEntryState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            messageLabel.setForeground(ERROR_COLOR);
            messageLabel.setText(state.getErrorMessage());
        }
        else if (state.getStatusMessage() != null) {
            messageLabel.setForeground(STATUS_COLOR);
            messageLabel.setText(state.getStatusMessage());
        }
        else {
            messageLabel.setText(" ");
        }
        refreshPhotos();
    }

    public String getViewName() {
        return viewModel.getViewName();
    }
}
