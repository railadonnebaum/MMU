package com.mby.dao;

/**
 * This is an Interface that's designed to implement the file system memory
 *
 * @param <ID> The key of the page
 * @param <T>  The page
 */
public interface IDao<ID extends java.io.Serializable, T> {
    /**
     * A function that is designed to delete a specific page from a file
     *
     * @param entity the page we want to delete
     * @throws Exception Throws IOException in case of failure to read or write to file
     */
    void delete(T entity) throws Exception;

    /**
     * A function that is designed to find a specific page from a file
     *
     * @param id the id of the specific page we want to find
     * @return Returns the page with the given id, in case the page is not found returns NULL
     * @throws Exception Throws IOException in case of file read failure
     */
    T find(ID id) throws Exception;

    /**
     * A function that is designed to save a specific page to a file
     *
     * @param entity the page we want to save
     * @throws Exception Throws IOException in case of failure to read or write to file
     */
    void save(T entity) throws Exception;
}
