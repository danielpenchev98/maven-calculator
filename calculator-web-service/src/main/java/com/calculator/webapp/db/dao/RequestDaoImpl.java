package com.calculator.webapp.db.dao;


import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;

import javax.persistence.*;
import java.util.List;
import java.util.function.BiConsumer;

public class RequestDaoImpl extends Dao<Long, CalculationRequestDTO> {

    public RequestDaoImpl(final EntityManager manager) {
        super(manager);
    }

    public RequestDaoImpl() { super(); }

    @Override
    public List<CalculationRequestDTO> getAllItems() {
        Query query = manager.createNamedQuery("CalculationRequests.findAll", CalculationRequestDTO.class);
        return query.getResultList();
    }


    public List<CalculationRequestDTO> getAllPendingRequests() {
        Query query = manager.createNamedQuery("CalculationRequests.findAllNotCalculated", CalculationRequestDTO.class);
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
}
