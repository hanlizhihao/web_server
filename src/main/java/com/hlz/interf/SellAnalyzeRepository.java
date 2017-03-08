package com.hlz.interf;

import com.hlz.entity.Indent;
import com.hlz.webModel.IndentStyle;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface SellAnalyzeRepository {
    Indent updateSellAnalyze(IndentStyle style,Indent indent);
    List<Indent> queryAllSellAnalyze();
}
