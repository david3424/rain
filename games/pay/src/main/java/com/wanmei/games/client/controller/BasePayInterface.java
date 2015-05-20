package com.wanmei.games.client.controller;

import com.wanmei.games.client.dao.entity.OpayDTO;
import com.wanmei.games.client.dao.entity.OpayOrder;
import com.wanmei.games.client.dao.entity.OpayQuery;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by david on 2015/3/6.
 * 支付接口
 */
public interface BasePayInterface {


    public String payout(OpayOrder opayOrder, String signature,Model model);

    public String returned(String referenceId);

    public String callback(OpayDTO opayDTO, String signature);

    public Map query(OpayOrder opayOrder, String signature);

    public Map search(OpayQuery opayQuery,String applicationCode,String signature, HttpServletRequest request);


}
