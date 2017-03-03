package com.hlz.interf;

import com.hlz.entity.Bill;
import com.hlz.webModel.BillModel;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface BillRepository {
    List<Bill> queryAll();

    boolean addBill(BillModel model);

    boolean deleteBill(int id);

    boolean updateBill(Bill bill);
}
