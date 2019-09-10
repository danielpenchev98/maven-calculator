package com.calculator.webapp.db;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class CalculatorDaoImpl implements Dao<Long,CalculatorResponseDTO> {

    private EntityManager manager;

    @Inject
    public CalculatorDaoImpl(final EntityManager manager) {
        this.manager = manager;
    }

    @SuppressWarnings("JpaQueryApiInspection")
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
        //EntityTransaction transaction = manager.getTransaction();
        //transaction.begin();
        manager.getTransaction().begin();
        manager.persist(item);
        manager.getTransaction().commit();
        //transaction.commit();
    }

    @Override
    public void deleteItem(CalculatorResponseDTO item) {

    }
}
