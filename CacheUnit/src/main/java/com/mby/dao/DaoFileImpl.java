package com.mby.dao;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mby.dm.DataModel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * An implementation of the IDAO interface, while the ID param is of a Long type and the and the second param (T) is a DataModel<T> type
 *
 * @param <T> Determines the type of the DataModel <T>
 */
public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
    private int capacity;// capacity of the help map stores the file content during the program
    private final String filePath;//the path of the file where  the pages are being written to
    private HashMap<Long, DataModel<T>> map;//the help map stores the file content during the program

    /**
     * Constructor
     *
     * @param filePath the path of the file where we want the pages to be written to
     */
    public DaoFileImpl(String filePath) {
        this.filePath = new File(filePath).getAbsolutePath();
        this.map = new HashMap<Long, DataModel<T>>();
    }

    /**
     * Constructor
     *
     * @param filePath the path of the file where we want the pages to be written to
     * @param capacity the capacity of the help map stores the file content during the program
     */
    public DaoFileImpl(String filePath, int capacity) {
        this.capacity = capacity;
        this.filePath = new File(filePath).getAbsolutePath();
        this.map = new HashMap<Long, DataModel<T>>(this.capacity);

    }

    @Override
    public void delete(DataModel<T> entity) throws Exception {
        read();
        map.remove(entity.getDataModelId());
        write();
    }

    @Override
    public DataModel<T> find(Long id) throws Exception {
        read();
        return map.get(id);
    }

    @Override
    public void save(DataModel<T> entity) throws Exception {
        read();
        if (entity != null) {
            if (!(entity.equals(find(entity.getDataModelId())))) {
                this.map.put(entity.getDataModelId(), new DataModel<T>(entity.getDataModelId(), entity.getContent()));
                write();
            }
        }
    }

    /**
     * A function that is designed to read the file content into the help map, Throws IOException in case of failure
     */
    private void read() throws FileNotFoundException {
        Type types = new TypeToken<HashMap<Long, DataModel<T>>>() {
        }.getType();
        File file = new File(filePath);
        try (FileReader fileReader = new FileReader(file)) {
            Gson gson = new Gson();
            HashMap<Long, DataModel<T>> content = gson.fromJson(fileReader, types);
            if (content != null) {
                map = content;
            }
        } catch (FileNotFoundException exception) {
            throw exception;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A function that is designed to write the help map content to the file, Throws IOException in case of failure
     */
    private void write() throws Exception {
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new Gson();
            String entity = gson.toJson(map);
            fileWriter.write(entity);
        } catch (FileNotFoundException exception) {
            throw exception;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}