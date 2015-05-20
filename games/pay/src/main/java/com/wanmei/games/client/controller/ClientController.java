package com.wanmei.games.client.controller;

import com.google.common.collect.Maps;
import com.wanmei.games.client.dao.dbutils.CommonList;
import com.wanmei.games.client.dao.dbutils.search.Search;
import com.wanmei.games.client.dao.entity.OpayDic;
import com.wanmei.games.client.service.ClientService;
import com.wanmei.games.utils.DateUtils;
import com.wanmei.games.utils.RandomUtil;
import com.wanmei.games.utils.Signature;
import com.wanmei.games.validate.IpValidate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.Map;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * <p/>
 * List page     : GET /client/
 * List top100   : GET /client/list
 * Create page   : GET /client/create
 * Create action : POST /client/create
 * update page   : GET /client/update{id}
 * update action : POST /client/update
 * Delete action : get delete/{id}
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController {

    private static final int PAGE_SIZE = 10;

    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

    static {
        sortTypes.put("id", "自动");
        sortTypes.put("appid", "标识");
    }

    @Autowired
    private ClientService clientService;
    @IpValidate
    @RequestMapping(value = "")
    public String list(@RequestParam(value = "sortType", defaultValue = "id") String sortType,
                       @RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "search_LIKE_title", required = false) String search_LIKE_title,
                       Model model) throws Exception {

        Search search = new Search();
        search.setPageNo(pageNumber);
        search.setPageSize(PAGE_SIZE);
        search.addOrder(sortType, Search.SEARCH_DESC);

        if (StringUtils.isNotBlank(search_LIKE_title)) {
            search.addWhere(Search.SEARCH_AND, " name like ? ", "%" + search_LIKE_title + "%");
        }
        search.addSelectSql("select * from o_pay_dic ");
        search.addSelectCountSql("select count(1) from o_pay_dic");
        CommonList clients = clientService.pagination(search, OpayDic.class);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("clients", clients);
        return "client/clientList";
    }


    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("client", new OpayDic());
        model.addAttribute("action", "create");

        return "client/clientForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(OpayDic client, RedirectAttributes redirectAttributes) throws SQLException {
        client.setUserid(1);
        client.setStatus(-1);
        client.setCreatetime(DateUtils.getCurrentFormatDateTime());
        client.setAppid(clientService.genAppid());
        try {
            client.setPrivatekey(Signature.encryptBASE64(RandomUtil.getRandomString(12).getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        clientService.saveClient(client);
        redirectAttributes.addFlashAttribute("message", "创建成功");
        return "redirect:/client";
    }


    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) throws SQLException {
        clientService.deleteClient(id);
        redirectAttributes.addFlashAttribute("message", "删除任务成功");
        return "redirect:/client/";
    }


    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") int id, Model model) throws SQLException {
        model.addAttribute("client", clientService.getClient(id));
        model.addAttribute("action", "update");
        return "client/clientForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@ModelAttribute("preloadClient") OpayDic client, RedirectAttributes redirectAttributes) throws SQLException {
        clientService.saveClient(client);
        redirectAttributes.addFlashAttribute("message", "更新任务成功");
        return "redirect:/client/";
    }



    @RequestMapping(value = "permit", method = RequestMethod.GET)
    public String permit(@ModelAttribute("preloadClient") OpayDic client, RedirectAttributes redirectAttributes) throws SQLException {
        client.setStatus(client.getStatus()==0?-1:0);
        clientService.saveClient(client);
        redirectAttributes.addFlashAttribute("message", "更新任务成功");
        return "redirect:/client/";
    }

    /**
     * 使用@ModelAttribute, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出DsysDic对象,再把Form提交的内容绑定到该对象上。
     * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
     */
    @ModelAttribute("preloadClient")
    public OpayDic getClient(@RequestParam(value = "id", required = false) Integer id) throws SQLException {
        if (id != null) {
            return clientService.getClient(id);
        }
        return null;
    }



}
