package com.calculator.webapp.db.dao;

import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Dao<K,V>{

     protected final EntityManager manager;

     private static final String productionPersistenceUnitName = "production-unit";

     Dao(final EntityManager manager){
          this.manager=manager;
     }

     Dao(){
          EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory(productionPersistenceUnitName);
          this.manager=managerFactory.createEntityManager(); }

     public abstract List<V> getAllItems();

     public abstract V getItem(final K key) throws ItemDoesNotExistException;

     public  void saveItem(final V item) {
          executeTransaction(EntityManager::persist,item);
     }

     public void deleteItem(final V item) {
          executeTransaction((entityManager,toDelete)-> {
               if(!entityManager.contains(toDelete)){
                    toDelete=manager.merge(toDelete);
               }
               entityManager.remove(toDelete);
          },item);
     }

     public void update(final V item) {
          executeTransaction(EntityManager::merge,item);
     }

     private void executeTransaction(final BiConsumer<EntityManager, Object> action, final V item)
     {
          EntityTransaction transaction =  manager.getTransaction();
          transaction.begin();
          action.accept(manager,item);
          transaction.commit();
     }
}
