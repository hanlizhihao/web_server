package com.hlz.util;

import com.hlz.entity.Indent;
import com.hlz.webModel.IndentModel;
import com.hlz.webModel.IndentStyle;
import java.util.Map;

/**
 *用于将给定的IndentModel转化为Indent
 * @author Administrator 2017-2-27
 */
public class IndentModelUtil {
    public static Indent TransformModel(IndentModel model,Indent indent){
        String fulfills = new String();
        Map<String,Integer> reserves = model.getReserve();
        String reserve = "";
       for(Map.Entry<String,Integer> entry:reserves.entrySet()){
           reserve=reserve+entry.getKey()+"a"+entry.getValue()+"e";
       }
        indent.setReserveNumber(reserves.size());
        indent.setReserve(reserve);
        reserves.clear();
        reserves.putAll(model.getFulfill());
        reserve="";
       for(Map.Entry<String,Integer> entry:reserves.entrySet()){
           reserve=reserve+entry.getKey()+"a"+entry.getValue()+"e";
       }
        indent.setFulfillNumber(reserves.size());
        indent.setFulfill(reserve);
        indent.setPrice(model.getPrice());
        indent.setReminderNumber(model.getRemiderNumber());
        indent.setTableId(model.getTable());
        indent.setFirstTime(new java.sql.Timestamp(model.getTime()));
        return indent;
    }
}
