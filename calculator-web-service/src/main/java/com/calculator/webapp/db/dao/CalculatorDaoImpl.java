package com.calculator.webapp.db.dao;


import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;

import javax.persistence.*;
import java.util.List;
import java.util.function.BiConsumer;

public class CalculatorDaoImpl implements Dao<Long, CalculatorResponseDTO> {

    private EntityManager manager;

    private static final String productionPersistenceUnitName = "production-unit";

    public CalculatorDaoImpl(final EntityManager manager) {
        this.manager = manager;
    }

    public CalculatorDaoImpl()
    {
        setProductionEntityManager();
    }

    private void setProductionEntityManager() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory(productionPersistenceUnitName);
        this.manager=managerFactory.createEntityManager();
    }

    @Override
    public List<CalculatorResponseDTO> getAllItems()  {
        Query query = manager.createNamedQuery("CalculatorResponses.findAll",CalculatorResponseDTO.class);
        return query.getResultList();
    }

    public List<CalculatorResponseDTO> getAllPendingCalculations() {
        Query query = manager.createNamedQuery("CalculatorResponses.findAllNotCalculated",CalculatorResponseDTO.class);
        return query.getResultList();
    }

    @Override
    public CalculatorResponseDTO getItem(final Long key) throws ItemDoesNotExistException {
        CalculatorResponseDTO result = manager.find(CalculatorResponseDTO.class,key);

        if(checkIfItemNotFound(result)){
            throw new ItemDoesNotExistException("Item not found");
        }

        manager.detach(result);
        return result;
    }

    private boolean checkIfItemNotFound(final CalculatorResponseDTO result){
        return result==null;
    }


    @Override
    public void saveItem(final CalculatorResponseDTO item) {
        executeTransaction(EntityManager::persist,item);
    }

    @Override
    public void deleteItem(final CalculatorResponseDTO item) {
        executeTransaction((entityManager,toDelete)-> {
            if(!entityManager.contains(toDelete)){
                toDelete=manager.merge(toDelete);
            }
            entityManager.remove(toDelete);
        },item);
    }

    @Override
    public void update(CalculatorResponseDTO item) {
        executeTransaction(EntityManager::merge,item);
    }

    private void executeTransaction(final BiConsumer<EntityManager,CalculatorResponseDTO> action, final CalculatorResponseDTO item)
    {
        manager.getTransaction().begin();
        action.accept(manager,item);
        manager.getTransaction().commit();
    }
}
