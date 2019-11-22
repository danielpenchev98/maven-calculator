package com.calculator.webapp.db.dao;


import com.calculator.webapp.db.dao.exceptions.ItemDoesNotExistException;
import com.calculator.webapp.db.dto.RequestDTO;

import javax.persistence.*;
import java.util.List;

public class RequestDaoImpl extends Dao<Long, RequestDTO> {

    public RequestDaoImpl(final EntityManager manager) {
        super(manager);
    }

    public RequestDaoImpl() { super(); }

    @Override
    public List<RequestDTO> getAllItems() {
        Query query = manager.createNamedQuery("Requests.findAll", RequestDTO.class);
        return query.getResultList();
    }


    public List<RequestDTO> getAllPendingRequests() {
        Query query = manager.createNamedQuery("Requests.findAllPending", RequestDTO.class);
        query.setParameter("statusCode",0);
        return query.getResultList();
    }

    @Override
    public RequestDTO getItem(final Long key) throws ItemDoesNotExistException {
        RequestDTO result = manager.find(RequestDTO.class,key);

        if(checkIfItemNotFound(result)){
            throw new ItemDoesNotExistException("Request with id :"+key+" hasn't been not found");
        }

        manager.detach(result);
        return result;
    }

    private boolean checkIfItemNotFound(final RequestDTO result){
        return result==null;
    }
}
