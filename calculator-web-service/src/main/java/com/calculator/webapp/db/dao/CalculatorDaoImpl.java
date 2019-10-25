package com.calculator.webapp.db.dao;


import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculationRequestDTO;

import javax.persistence.*;
import java.util.List;
import java.util.function.BiConsumer;

public class CalculatorDaoImpl implements Dao<Long, CalculationRequestDTO> {

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
    public List<CalculationRequestDTO> getAllItems()  {
        Query query = manager.createNamedQuery("CalculatorResponses.findAll", CalculationRequestDTO.class);
        return query.getResultList();
    }

    public List<CalculationRequestDTO> getAllPendingCalculations() {
        Query query = manager.createNamedQuery("CalculatorResponses.findAllNotCalculated", CalculationRequestDTO.class);
        query.setParameter("statusCode",0);
        return query.getResultList();
    }

    @Override
    public CalculationRequestDTO getItem(final Long key) throws ItemDoesNotExistException {
        CalculationRequestDTO result = manager.find(CalculationRequestDTO.class,key);

        if(checkIfItemNotFound(result)){
            throw new ItemDoesNotExistException("Item not found");
        }

        manager.detach(result);
        return result;
    }

    private boolean checkIfItemNotFound(final CalculationRequestDTO result){
        return result==null;
    }


    @Override
    public void saveItem(final CalculationRequestDTO item) {
        executeTransaction(EntityManager::persist,item);
    }

    @Override
    public void deleteItem(final CalculationRequestDTO item) {
        executeTransaction((entityManager,toDelete)-> {
            if(!entityManager.contains(toDelete)){
                toDelete=manager.merge(toDelete);
            }
            entityManager.remove(toDelete);
        },item);
    }

    @Override
    public void update(CalculationRequestDTO item) {
        executeTransaction(EntityManager::merge,item);
    }

    private void executeTransaction(final BiConsumer<EntityManager, CalculationRequestDTO> action, final CalculationRequestDTO item)
    {
        EntityTransaction transaction =  manager.getTransaction();
        transaction.begin();
        action.accept(manager,item);
        transaction.commit();
    }
}
