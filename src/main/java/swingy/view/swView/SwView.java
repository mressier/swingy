package swingy.view.swView;

import lombok.Setter;

public abstract class SwView {

    @Setter protected SwViewObserver observers[] = {};

    /*
     * Public Methods
     */
    abstract public void  success(String message);
    abstract public void  error(String message);

    /*
     * Protected Methods
     */
    protected void triggerActionPerformed(String action)
    {
        for (int i = 0; i < observers.length; i++) {
            observers[i].actionPerformed(action);
        }
    }
}
