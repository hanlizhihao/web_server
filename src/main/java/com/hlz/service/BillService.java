package com.hlz.service;

import com.hlz.dao.BillDAO;
import com.hlz.entity.Bill;
import com.hlz.webModel.BillModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator 2017-3-3
 */
@Service
public class BillService {
    @Autowired
    private BillDAO dao;
    public boolean addBill(BillModel model){
        return dao.addBill(model);
    }
    public boolean updateBill(Bill bill){
        Bill b=dao.updateBill(bill);
        return b != null;
    }
    public boolean deleteBill(int id){
        Bill b=dao.deleteBill(id);
        return b != null;
    }
    public List<Bill> findAllBill(){
        return dao.queryAll();
    }
    public Bill findOneBill(int id){
        return dao.queryOne(id);
    }
}
