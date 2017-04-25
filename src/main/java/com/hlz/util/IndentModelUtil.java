package com.hlz.util;

import com.hlz.entity.Indent;
import com.hlz.entity.Menu;
import com.hlz.webModel.IndentModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *用于将给定的IndentModel转化为Indent
 * @author Administrator 2017-2-27
 */
public class IndentModelUtil {

    public static Indent TransformModel(IndentModel model, Indent indent) {
        Map<String, String> reserves = model.getReserve();
        String reserve = "";
        int reserveNumber=0;
        for (Entry<String, String> entry : reserves.entrySet()) {
            reserve = reserve + entry.getKey() + "a" + entry.getValue() + "e";
            reserveNumber+=Integer.valueOf(entry.getValue());
        }
        indent.setReserveNumber(reserveNumber);
        indent.setReserve(reserve);
        reserves.clear();
        reserves.putAll(model.getFulfill());
        reserve = "";
        int fulfillNumber = 0;
        for (Entry<String, String> entry : reserves.entrySet()) {
            reserve = reserve + entry.getKey() + "a" + entry.getValue() + "e";
            fulfillNumber = Integer.valueOf(entry.getValue()) + fulfillNumber;
        }
        indent.setFulfillNumber(fulfillNumber);
        indent.setFulfill(reserve);
        indent.setPrice(model.getPrice());
        indent.setReminderNumber(model.getRemiderNumber());
        indent.setTableId(model.getTable());
        //如果为0表示不存在firstTime，还没上菜
        if (model.getTime() == 0) {
            indent.setFirstTime(null);
        } else {
            indent.setFirstTime(new java.sql.Timestamp(model.getTime()));
        }
        return indent;
    }
}
