package com.hlz.interf;

import com.hlz.entity.Vip;
import com.hlz.webModel.VipModel;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface VipRepository {
    boolean addVip(VipModel model);
    boolean updateVip(Vip vip);
    boolean deleteVip(int id);
    int countVip();
    List<Vip> queryVipOnPage(int page);
    Vip querySingle(int id);
}
