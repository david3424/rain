package org.david.rain.games.pay.client.controller;

import org.david.rain.games.pay.client.dao.entity.OpayDTO;
import org.david.rain.games.pay.client.dao.entity.OpayOrder;
import org.david.rain.games.pay.client.dao.entity.OpayQuery;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by david on 2015/3/6.
 * 支付接口
 */
public interface BasePayInterface {


     String payout(OpayOrder opayOrder, String signature,Model model);

     String returned(String referenceId);

     String callback(OpayDTO opayDTO, String signature);

     Map query(OpayOrder opayOrder, String signature);

     Map search(OpayQuery opayQuery,String applicationCode,String signature, HttpServletRequest request);


}
