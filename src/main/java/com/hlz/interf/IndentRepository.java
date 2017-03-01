package com.hlz.interf;

import com.hlz.entity.Indent;
import com.hlz.webModel.IndentModel;
import com.hlz.webModel.IndentStyle;
import java.util.List;

/**
 *餐桌信息的DAO
 * @author Administrator
 */
public interface IndentRepository {
    boolean addIndent(IndentModel model);
    boolean updateIndent(IndentModel model);
    boolean updateIndent(IndentStyle style);//用于专门更新餐桌信息状态
    List<Indent> queryUnderwayIndent(int page);//分页查询正在进行的
    List<Indent> queryFinishedIndent(int page);//分页查询完成的
    List<Indent> queryCanceledIndent(int page);//分页查询取消的
    Indent findOneIndent(int id);
    long countUnderway();
    long countFinished();
    long countCanceled();
}
