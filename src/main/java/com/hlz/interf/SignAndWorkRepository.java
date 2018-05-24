package com.hlz.interf;

import com.hlz.entity.SignAnalysis;
import com.hlz.webModel.WorkModel;

import java.util.List;

/**
 *没有查询，查询user时，会级联查出签到和工作的时间
 * @author Administrator
 */
public interface SignAndWorkRepository {
    boolean addSign(int id);
    boolean addWork(WorkModel model);
    boolean updateWork(WorkModel model);
    boolean signOut(int id);
    List<SignAnalysis> getSignAnalysis(int id);
}
