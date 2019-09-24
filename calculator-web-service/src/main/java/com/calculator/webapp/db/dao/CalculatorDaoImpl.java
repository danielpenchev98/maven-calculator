package com.calculator.webapp.db.dao;


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
        Query query=manager.createNamedQuery("CalculatorResponses.findAll",CalculatorResponseDTO.class);
        return query.getResultList();
    }

    @Override
    public CalculatorResponseDTO getItem(Long key) {
        CalculatorResponseDTO result = manager.find(CalculatorResponseDTO.class,key);
        manager.detach(result);
        return result;
    }

    @Override
    public void saveItem(CalculatorResponseDTO item) {
        executeTransaction(EntityManager::persist,item);
    }

    @Override
    public void deleteItem(CalculatorResponseDTO item) {
        executeTransaction((entityManager,toDelete)-> {
            if(!entityManager.contains(toDelete)){
                toDelete=manager.merge(toDelete);
            }
            entityManager.remove(toDelete);
        },item);
    }

    private void executeTransaction(final BiConsumer<EntityManager,CalculatorResponseDTO> action, final CalculatorResponseDTO item)
    {
        manager.getTransaction().begin();
        action.accept(manager,item);
        manager.getTransaction().commit();
    }
}
