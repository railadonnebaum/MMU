package com.mby.driver;

import com.mby.client.CacheUnitClientObserver;
import com.mby.view.CacheUnitView;

public class CacheUnitClientDriver {
    /**
     * constructor
     */
    public CacheUnitClientDriver() {
    }

    /**
     * main function, prepares the program to run the client
     * @param args the program args
     */
    public static void main(String[] args) {
        CacheUnitClientObserver cacheUnitClientObserver = new CacheUnitClientObserver();
        CacheUnitView view = new CacheUnitView();
        view.addPropertyChangeListener(cacheUnitClientObserver);
        view.start();
    }
}

