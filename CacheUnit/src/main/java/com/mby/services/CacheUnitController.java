package com.mby.services;

import com.mby.dm.DataModel;

/**
 * A class designed to serve as a separation layer
 * between CacheUnitService and Networking
 *
 * @param <T> the type of the DataModel in the CacheUnitService
 */
public class CacheUnitController<T> {
    private final CacheUnitService<T> cacheUnitService;

    /**
     * constructor
     */
    public CacheUnitController() {
        cacheUnitService = new CacheUnitService<>();
    }

    /**
     * A function that is designed to remove from the cacheUnit  an array of specific dataModels
     *
     * @param dataModels an array of dataModels
     * @return the value received from the delete function in the cacheUnitService
     */
    public boolean delete(DataModel<T>[] dataModels) {
        return cacheUnitService.delete(dataModels);
    }

    /**
     * A function that is designed to get from the cacheUnit  an array of specific dataModels
     *
     * @param dataModels an array of dataModels
     * @return an array of  dataModels received from the get function in the cacheUnitService
     * (if the cacheUnit doesn't contain a specific dataModel, it will return in its place null)
     */
    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        return cacheUnitService.get(dataModels);
    }

    /**
     * A function that is designed to update  an array of specific dataModels in the cacheUnit
     *
     * @param dataModels an array of dataModels
     * @return the value received from the update function in the cacheUnitService
     */
    public boolean update(DataModel<T>[] dataModels) {
        return cacheUnitService.update(dataModels);
    }

    /**
     * A function that is designed to shut down the server
     *
     * @throws Exception if gets exception from the cacheUnitService shutDown function
     */
    public void shutDown() throws Exception {
        cacheUnitService.shotDown();
    }

    /**
     * @return the value received from getAlgo function in the cacheUnitService.
     */
    public String getAlgo() {
        return CacheUnitService.getAlgo();
    }

    /**
     * @return the value received from getCapacity function in the cacheUnitService.
     */
    public Long getCapacity() {
        return CacheUnitService.getCapacity();
    }

    /**
     * @return the value received from getDataModels function in the cacheUnitService.
     */
    public Long getDataModels() {
        return CacheUnitService.getDataModels();
    }

    /**
     * @return the value received from getDataModelsSwaps function in the cacheUnitService
     */
    public Long getDataModelsSwaps() {
        return CacheUnitService.getDataModelsSwaps();
    }
}
