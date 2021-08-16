package com.mby.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;

import com.mby.view.CacheUnitView;

/**
 *class designed to connect between the server and the client
 */
public class CacheUnitClientObserver implements PropertyChangeListener, EventListener {

    private final CacheUnitClient cacheUnitClient;

    /**
     *constructor
     */
    public CacheUnitClientObserver() {
        cacheUnitClient = new CacheUnitClient();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String response = cacheUnitClient.send((String) evt.getNewValue());
        CacheUnitView cacheUnitView = (CacheUnitView) evt.getSource();
        cacheUnitView.updateUIData(response);
    }
}
