package com.hlz.controller;

import com.hlz.entity.Bill;
import com.hlz.service.BillService;
import com.hlz.webModel.BillModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator 2017-3-3
 */
@RestController
public class BillController {
    @Autowired
    private BillService service;
    @RequestMapping(value="/bill/add",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String addBill(BillModel model){
        if(service.addBill(model)){
            return "success";
        }
        return "defeat";
    }
    @RequestMapping(value="/bill/update",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String updateBill(Bill bill){
        if(service.updateBill(bill)){
            return "success";
        }
        return "defeat";
    }
    @RequestMapping(value="/bill/delete/{id}",produces="text/plain;charset=UTF-8")
    public String deleteBill(@PathVariable String id){
        if(service.deleteBill(Integer.valueOf(id))){
            return "success";
        }
        return "defeat";
    }
    @RequestMapping(value="/bills",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public ArrayList<Bill> getBills(){
        ArrayList<Bill> bills=new ArrayList<>();
        List<Bill> result=service.findAllBill();
        if(result==null||result.isEmpty()){
            Bill bill=new Bill();
            bill.setName("404");
            bills.add(bill);
            return bills;
        }
        bills.addAll(result);
        return bills;
    }
    @RequestMapping(value="/bill/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public Bill getBill(@PathVariable String id){
        Bill bill=service.findOneBill(Integer.valueOf(id));
        if(bill==null){
            bill=new Bill();
            bill.setName("404");
            return bill;
        }else{
            return bill;
        }
    }
}
