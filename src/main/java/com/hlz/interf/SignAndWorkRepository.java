package com.hlz.interf;

import com.hlz.webModel.WorkModel;
/**
 *没有查询，查询user时，会级联查出签到和工作的时间
 * @author Administrator
 */
public interface SignAndWorkRepository {
    boolean addSign(int id);
    boolean addWork(WorkModel model);
    boolean deleteSign(int id);
    boolean deleteWork(int id);
    boolean updateWork(WorkModel model);
}
