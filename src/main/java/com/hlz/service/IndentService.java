package com.hlz.service;

import com.hlz.dao.IndentDAO;
import com.hlz.entity.Indent;
import com.hlz.webModel.IndentModel;
import com.hlz.webModel.IndentStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator 2017-3-7
 */
@Service
public class IndentService {
    @Autowired
    private IndentDAO dao;
    /**
     * 增，删，改均涉及消息推送
     * @param model 客户端传递过来最新的数据
     * @return 
     */
    public boolean createIndent(IndentModel model){
        return dao.addIndent(model);
    }
    public boolean updateIndent(IndentModel model){
        Indent indent=dao.updateIndent(model);
        return indent != null;
    }
    /**
     * 结算与取消订单，均涉及推送消息
     * @param model
     * @return 
     */
    public boolean updateIndentStyle(IndentStyle model){
        Indent indent=dao.updateIndent(model);
        return indent!=null;
    }
    
}
