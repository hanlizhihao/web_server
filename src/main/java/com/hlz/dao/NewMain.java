package com.hlz.dao;

import com.hlz.entity.Indent;
import com.hlz.webModel.IndentModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Administrator
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IndentModel model=new IndentModel();
        IndentDAO dao=new IndentDAO();
        List<Indent> result=dao.queryAllUnderwayIndent();
    }
    
}
