package org.david.rain.monitor.monitor.controller.model;

import org.david.rain.monitor.monitor.persistence.UserMapper;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.david.rain.monitor.monitor.util.JsonUtil;
import org.david.rain.monitor.monitor.util.PaginationJsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangji
 * Date: 14-3-5
 * Time: 下午3:49
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/model", method = RequestMethod.GET)
public class ModelController {
    Logger logger = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public List<Node> getModel() throws Exception {
        List<Node> nodeList = userMapper.getAllModelNode();

        Map<Integer, List<Node>> childrenMap = new HashMap<>();
        List<Node> result = new ArrayList<>();
        for (Node node : nodeList) {
            if (node.getParentId() == 0) {
                result.add(node); //存入根节点
            }
            List<Node> sibblings = childrenMap.get(node.getParentId());
            if (sibblings == null) {
                sibblings = new ArrayList<>();
                childrenMap.put(node.getParentId(), sibblings);
            }
            sibblings.add(node);
        }
        //因为必须等childrenMap构建完才能set到node children中，需要再次遍历
        for (Node node : nodeList) {
//            buildNode(root, childrenMap);
            node.setChildren(childrenMap.get(node.getId()));
        }
        buildNode(result, 5); //打印测试
        return result;
    }

    /**
     * 递归的作用是遍历输出*
     */
    private void buildNode(List<Node> result, int count) {
        for (Node n : result) {
            n.setText(n.getText() + "@" + n.getUrl());
            for (int i = 0; i < count; i++) {
                System.out.print("-");
            }
            System.out.println(n.getText());
            List<Node> children = n.getChildren();
            if (children != null && children.size() > 0) {
                buildNode(children, 2 * count);
            } else {
//                logger.info("root【{}】子节点为null，跳出递归", n.getText());
            }
        }
//        logger.info("list 迭代结束");
    }

    @RequestMapping(value = "url", method = RequestMethod.POST)
    @ResponseBody
    public String getUrl(@RequestParam Integer id) throws Exception {
        Node node = userMapper.getNodeUrl(id);
        return node.getUrl();
    }


    @RequestMapping("tree")
    public String goIndex() {
        return "model";
    }

    //获取功能tree 列表信息
    @RequestMapping(value = "list/tree", method = RequestMethod.POST)
    @ResponseBody
    public PaginationJsonObject<Node> pageList(EasyPageInfo pageInfo) {
        List<Node> list = userMapper.getAllTreeListPage(pageInfo);
        return new PaginationJsonObject<>(list, pageInfo);
    }

    @RequestMapping(value = "selectType", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectRoles() {
        Map<String, Object> result = new HashMap<>();
        result.put("allparent", userMapper.getAllListModelId());
        result.put("success", true);
        return result;

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addModel(Node node) {
        try {
            userMapper.saveModel(node);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "添加失败");
        }
        return JsonUtil.commonResponse(true, "添加成功");
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(Node node) {
        try {
            userMapper.updateMode(node);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "修改失败");
        }
        return JsonUtil.commonResponse(true, "修改成功");

    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteModel(Integer id) {

        int re = userMapper.deleteModel(id);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "删除成功。");
        } else {
            return JsonUtil.commonResponse(false, "删除失败。");
        }
    }

}
