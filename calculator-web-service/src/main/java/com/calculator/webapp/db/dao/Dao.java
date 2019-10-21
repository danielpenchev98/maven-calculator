package com.calculator.webapp.db.dao;

import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;

import java.util.List;

public interface Dao<K, I> {
     List<I> getAllItems();
     I getItem(final K key) throws ItemDoesNotExistException;
     void saveItem(final I item);
     void deleteItem(final I item);
     void update(final I item);
}
