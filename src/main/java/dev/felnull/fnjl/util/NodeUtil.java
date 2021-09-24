package dev.felnull.fnjl.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeUtil {
    public static Node getNode(NodeList nodeList, String name) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            if (name.equals(n.getNodeName()))
                return n;
        }
        return null;
    }
}
