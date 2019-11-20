package com.calculator.webapp.db.dao;

import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.CalculationRequestDTO;
import com.calculator.webapp.db.dto.CalculatorResponseDTO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EquationDaoImpl extends Dao<String, CalculatorResponseDTO> {

    public EquationDaoImpl(final EntityManager manager) {
        super(manager);
    }

    public EquationDaoImpl() { super(); }

    @Override
    public List<CalculatorResponseDTO> getAllItems() {
        Query query = manager.createNamedQuery("CalculatorResponses.findAll", CalculatorResponseDTO.class);
        return query.getResultList();
    }


    @Override
    public CalculatorResponseDTO getItem(final String key) throws ItemDoesNotExistException {
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
}
