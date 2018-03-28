package com.el.canno.ds;

import java.util.List;

/**
 * User Canno
 * Date 2018/3/22
 * Time 10:40
 */

public class TreeChildTest {

    public static void main(String[] args) {

        TreeChild<String> tp = new TreeChild<String>("root");
        TreeChild.Node root = tp.root();
        System.out.println(root);
        tp.addNode("节点1", root);
        tp.addNode("节点2", root);
        tp.addNode("节点3", root);
        System.out.println("添加子节点后的根结点：" + root);
        System.out.println("此树的深度：" + tp.deep());
        // 获取根节点的所有子节点
        List<TreeChild.Node<String>> nodes = tp.children(root);
        System.out.println("根节点的第一个子节点：" + nodes.get(0));
        // 为根节点的第一个子节点新增一个子节点
        tp.addNode("节点4", nodes.get(0));
        System.out.println("此树第一个子节点：" + nodes.get(0));
        System.out.println("此树的深度：" + tp.deep());
    }
}
