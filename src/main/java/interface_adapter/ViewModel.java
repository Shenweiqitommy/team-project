package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Holds the state a view displays and tells the view when it changes.
 * Shared by all use cases' view models.
 * @param <T> the type of state this view model holds
 */
public class ViewModel<T> {
    private final String viewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private T state;

    /**
     * Creates a view model.
     * @param viewName the name of the view this model backs
     */
    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }

    /**
     * Tells listeners that the state changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    /**
     * Tells listeners that one named property changed.
     * @param propertyName the property that changed
     */
    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, state);
    }

    /**
     * Registers a listener, usually the view.
     * @param listener the listener to notify
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
