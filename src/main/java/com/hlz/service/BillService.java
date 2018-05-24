package com.hlz.service;

import com.hlz.dao.BillDAO;
import com.hlz.entity.Bill;
import com.hlz.webModel.BillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Administrator 2017-3-3
 */
@Service
public class BillService {
    @Autowired
    private BillDAO dao;
    @Autowired
    private SimpMessageSendingOperations messaging;
    public boolean addBill(BillModel model){
        return dao.addBill(model);
    }
    public boolean updateBill(Bill bill){
        Bill b=dao.updateBill(bill);
        messaging.convertAndSend("topic/bill","1");
        return b != null;
    }
    public boolean deleteBill(int id){
        Bill b=dao.deleteBill(id);
        return b != null;
    }
    public List<Bill> findAllBill(String id){
        return dao.queryAll(id);
    }
    public Bill findOneBill(int id){
        return dao.queryOne(id);
    }
}
