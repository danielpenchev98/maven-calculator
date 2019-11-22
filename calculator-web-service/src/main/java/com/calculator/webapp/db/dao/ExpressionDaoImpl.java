package com.calculator.webapp.db.dao;

import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.ExpressionDTO;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ExpressionDaoImpl extends Dao<String, ExpressionDTO> {

    public ExpressionDaoImpl(final EntityManager manager) {
        super(manager);
    }

    public ExpressionDaoImpl() { super(); }

    @Override
    public List<ExpressionDTO> getAllItems() {
        Query query = manager.createNamedQuery("Expressions.findAll", ExpressionDTO.class);
        return query.getResultList();
    }


    @Override
    public ExpressionDTO getItem(final String key) throws ItemDoesNotExistException {
        ExpressionDTO result = manager.find(ExpressionDTO.class,key);

        if(checkIfItemNotFound(result)){
            throw new ItemDoesNotExistException("Expression with id:"+key+" hasn't been not found");
        }

        manager.detach(result);
        return result;
    }

    private boolean checkIfItemNotFound(final ExpressionDTO result){
        return result==null;
    }
}
