package com.calculator.webapp.db.dao;

import java.util.List;

public interface Dao<K, I> {
     List<I> getAllItems();
     I getItem(final K key);
     void saveItem(final I item);
     void deleteItem(final I item);
}
