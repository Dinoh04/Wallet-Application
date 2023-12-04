package org.example.Repository;

import java.sql.SQLException;
import java.util.List;

public interface CrudOperations <T> {
    public List<T> findAll() throws SQLException;
    public List<T> saveAll(List<T>toSave)throws SQLException;
    public T save(T toSave)throws SQLException;
    public T update(T ToUpdate) throws SQLException;
    public T delete (T toDelete)throws SQLException;
}
