package com.calculator.webapp.db;

import javax.persistence.EntityManager;
import java.util.List;

public class CalculatorDaoImpl implements Dao<Long,CalculatorResponseDTO> {

    private EntityManager manager;

    public CalculatorDaoImpl(final EntityManager manager) {
        this.manager = manager;
    }


    @Override
    public List<CalculatorResponseDTO> getAllItems() {
        return null;
    }

    @Override
    public CalculatorResponseDTO getItem(Long key) {
        return null;
    }

    @Override
    public void saveItem(CalculatorResponseDTO item) {

    }

    @Override
    public void deleteItem(CalculatorResponseDTO item) {

    }
}
