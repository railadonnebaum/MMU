package com.mby.memory;

import com.mby.algorithm.IAlgoCache;
import com.mby.dm.DataModel;

import java.util.HashMap;
import java.util.Map;

/**
 * A class designed to manage the Cache Unit Memory
 *
 * @param <T> Determines the type of the DataModel <T>
 */
public class CacheUnit<T> {

    private final IAlgoCache<java.lang.Long, DataModel<T>> algoCache;//The algorithm that will determine how to manage the cache

    /**
     * Constructor
     *
     * @param algo The algorithm that will determine how to manage the cache
     */
    public CacheUnit(IAlgoCache<java.lang.Long, DataModel<T>> algo) {
        this.algoCache = algo;
    }

    /**
     * A function that is designed to get from the cache memory an array of specific pages
     *
     * @param ids Array of ids of the pages we would like to get
     * @return an Array of the pages
     */
    public DataModel<T>[] getDataModels(Long[] ids) {
        DataModel<T>[] values = new DataModel[ids.length];
        for (int i = 0; i < ids.length; i++) {
            values[i] = this.algoCache.getElement(ids[i]);
        }
        return values;
    }

    /**
     * A function that is designed to put in the cache memory an array of  pages
     *
     * @param dataModels Array of  pages(DataModels) we would like to get
     * @return an Array of the return values from the putElement function
     */
    public DataModel<T>[] putDataModels(DataModel<T>[] dataModels) {
        DataModel<T>[] values = new DataModel[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) {
            values[i] = this.algoCache.putElement(dataModels[i].getDataModelId(), dataModels[i]);
        }
        return values;
    }


    /**
     * A function that is designed to remove from the cache memory an array of specific pages
     *
     * @param ids Array of ids of the pages we would like to remove
     */
    public void removeDataModels(Long[] ids) {
        for (Long id : ids) {
            this.algoCache.removeElement(id);
        }
    }

    /**
     *
     * @return all dataModels in the cache
     */
    public DataModel<T>[] getAllDataModels(){
        Map<Long, DataModel<T>> map = new HashMap<>();
        map=this.algoCache.getPages();
        DataModel<T>[] data = new DataModel[map.size()];
        int counter=0;
        for (DataModel<T> element: map.values()) {
            data[counter]=element;
            counter++;
        }
        return data;
    }
}
