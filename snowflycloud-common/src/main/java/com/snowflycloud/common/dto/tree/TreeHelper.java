package com.snowflycloud.common.dto.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @file: TreeHelper
 * @description: 树形结构工具类.
 * @author: lixuefei
 * @create: 2019-06-18 19:54
 * @version: v1.0.0
 */
public class TreeHelper {

    private static final TreeHelper instance = null;

    private TreeHelper(){

    }

    public static TreeHelper getInstance(){
        return new TreeHelper();
    }

    public <T extends TreeSourceNode> List<TreeDestNode<T>> convert(List<T> sourceNodeList){
        List<TreeDestNode<T>> destNodeList = new ArrayList<TreeDestNode<T>>();
        //第一步，找出第一级的节点
        //1.1 统计所有节点的id
        List<String> allIds = new ArrayList<String>();
        for (T sourceNode : sourceNodeList) {
            allIds.add(sourceNode.getId());
        }
        //所有父节点找不到对应的都是一级id
        for (T sourceNode : sourceNodeList) {
            if (!allIds.contains(sourceNode.getParentId())) {
                //从每个一级节点，递归查找children
                TreeDestNode<T> destNode = new TreeDestNode<T>();
                destNode.setId(sourceNode.getId());
                destNode.setLabel(sourceNode.getLabel());
                destNode.setParentId(sourceNode.getParentId());
                destNode.setLevel(1);
                destNode.setMeta(sourceNode);
                List<TreeDestNode<T>> myChilds = getChilderen(sourceNodeList, destNode);
                destNode.setChildren(myChilds.isEmpty() ? null : myChilds);
                destNodeList.add(destNode);
            }
        }
        return destNodeList;

    }

    //    递归获取子节点
    private static <T extends TreeSourceNode> List<TreeDestNode<T>> getChilderen(List<T> sourceNodeList, TreeDestNode<T> parentNode) {
        List<TreeDestNode<T>> childrenList = new ArrayList<TreeDestNode<T>>();
        for (T sourceNode : sourceNodeList) {
            if (parentNode.getId().equals(sourceNode.getParentId())) {
                TreeDestNode<T> children = new TreeDestNode<T>();
                children.setId(sourceNode.getId());
                children.setLabel(sourceNode.getLabel());
                children.setParentId(sourceNode.getParentId());
                children.setLevel(parentNode.getLevel() + 1);
                children.setMeta(sourceNode);
                List<TreeDestNode<T>> myChilds = getChilderen(sourceNodeList, children);
                children.setChildren(myChilds.isEmpty() ? null : myChilds);
                childrenList.add(children);
            }
        }
        return childrenList;
    }

}
