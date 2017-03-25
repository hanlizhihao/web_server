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
    Vip updateVip(Vip vip);
    Vip deleteVip(int id);
    int countVip();
    List<Vip> queryVipOnPage(int page);
    Vip querySingle(int id);
    Vip querySingle(String telephone);
}
