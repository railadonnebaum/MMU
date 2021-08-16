package com.mby.dm;

import java.util.Objects;

/**
 * A class designed to define the memory page
 *
 * @param <T> define the page content type
 */
public class DataModel<T> {
    T content;// the page type
    Long DataModelId;// the page ID

    /**
     * Constructor
     *
     * @param id      the page ID
     * @param content the page type
     */
    public DataModel(Long id, T content) {
        this.content = content;
        this.DataModelId = id;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Long getDataModelId() {
        return DataModelId;
    }

    public void setDataModelId(Long id) {
        this.DataModelId = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, DataModelId);
    }

    @Override
    public boolean equals(Object obj) {
        DataModel<T> element = (DataModel<T>) obj;
        if (obj == null)
            return false;
        return this.content.equals(element.content) && this.DataModelId.equals(element.DataModelId);
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "content=" + content +
                ", DataModelId=" + DataModelId +
                '}';
    }
}
