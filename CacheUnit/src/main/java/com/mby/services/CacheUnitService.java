package com.mby.services;

import com.mby.algorithm.IAlgoCache;
import com.mby.algorithm.LRUAlgoCacheImpl;
import com.mby.dao.DaoFileImpl;
import com.mby.dm.DataModel;
import com.mby.memory.CacheUnit;

/**
 * A class designed to manage the MMU - the storage of data on hardDisk and cache
 * and the exchange of data between them according to the request and cacheUnit settings
 *
 * @param <T> the type of the DataModel in the CacheUnit
 */
public class CacheUnitService<T> {
    private final DaoFileImpl<T> hardDisk;
    public CacheUnit<T> cache;
    private static Long DATA_MODEL;
    private static Long CAPACITY;
    private static Long DATA_MODEL_SWAPS = (long) 0;
    private static final String LRU = "LRU";
    private final IAlgoCache<Long, DataModel<T>> algo;

    /**
     * Constructor
     */
    public CacheUnitService() {
        hardDisk = new DaoFileImpl<>("src\\main\\resources\\dataSource.txt");
        CAPACITY = (long) 10;
        algo = new LRUAlgoCacheImpl<>(CAPACITY);
        cache = new CacheUnit<>(algo);
        DATA_MODEL = (long) 0;
    }

    /**
     * @return the Algo type
     */
    static String getAlgo() {
        return LRU;
    }

    /**
     * @return the capacity of the CacheUnit
     */
    static Long getCapacity() {
        return CAPACITY;
    }

    /**
     * @param dataModels an array of dataModels the user desires to delete
     * @return true in case of success, false otherwise
     */
    public boolean delete(DataModel<T>[] dataModels) {
        Long[] ids;
        DATA_MODEL += dataModels.length;
        ids = new Long[dataModels.length];
        try {
            for (int i = 0; i < dataModels.length; i++) {
                ids[i] = dataModels[i].getDataModelId();
                if (hardDisk.find(dataModels[i].getDataModelId()) != null) {
                    hardDisk.delete(dataModels[i]);
                }
            }
            cache.removeDataModels(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param dataModels an array of dataModels the user desires to receive
     * @return the array of the requested dataModels
     */
    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        Long[] ids;
        DATA_MODEL += dataModels.length;
        DataModel<T>[] returnedArray;
        returnedArray = new DataModel[dataModels.length];
        ids = new Long[dataModels.length];
        try {
            for (int i = 0; i < dataModels.length; i++) {
                ids[i] = dataModels[i].getDataModelId();
            }
            returnedArray = cache.getDataModels(ids);
            for (int i = 0; i < dataModels.length; i++) {
                if (returnedArray[i] == null) {
                    returnedArray[i] = hardDisk.find(ids[i]);
                    if (returnedArray[i] != null) {
                        DataModel[] helpArray = new DataModel[1];
                        helpArray[0] = returnedArray[i];
                        helpArray = cache.putDataModels(helpArray);
                        if (helpArray[0] != null) {
                            hardDisk.save(helpArray[0]);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnedArray;
    }

    /**
     * @param dataModels an array of dataModels the user desires to update
     * @return true in case of success, false otherwise
     */
    public boolean update(DataModel<T>[] dataModels) {
        DataModel<T>[] returnedArray;
        DATA_MODEL += dataModels.length;
        returnedArray = cache.putDataModels(dataModels);
        try {
            for (int i = 0; i < dataModels.length; i++) {
                if (returnedArray[i] != null) {
                    DATA_MODEL_SWAPS++;
                    hardDisk.save(returnedArray[i]);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * A function that copies all the dataModels from the cache to the hard disk and clears the cache.
     *
     * @throws Exception if gets exception from the save function
     */
    public void shotDown() throws Exception {

        if (cache != null) {
            DataModel<T>[] map = cache.getAllDataModels();
            for (DataModel<T> element : map) {
                if (element != null) {
                    hardDisk.save(element);
                }
            }
        }

        cache =  new CacheUnit<>(algo);;
    }

    /**
     * @return the number of DataModels that were deleted/ updated/got during the program flow.
     */
    static Long getDataModels() {
        return DATA_MODEL;
    }

    /**
     * @return the number of DataModels that were swapped from the cacheUnit to IDao
     */
    static Long getDataModelsSwaps() {
        return DATA_MODEL_SWAPS;
    }
}
