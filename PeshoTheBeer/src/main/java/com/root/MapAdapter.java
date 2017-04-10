/*
 * MapAdapter.java
 *
 * created at Mar 20, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.root;

import java.util.IdentityHashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MapAdapter extends XmlAdapter<AdaptedMap, Map<String, String>>
{

    private Node childNode;

    @Override
    public Map<String, String> unmarshal(AdaptedMap adapted) throws Exception
    {
        Map<String, String> map = new IdentityHashMap<String, String>();
        Element rootElement = (Element) adapted.getValue();
        NodeList childNodes = rootElement.getChildNodes();
        for (int x = 0, size = childNodes.getLength(); x < size; x++) {
            childNode = childNodes.item(x);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                map.put(childNode.getLocalName() + "", childNode.getTextContent());
            }
        }
        return map;

    }

    @Override
    public AdaptedMap marshal(Map<String, String> map) throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        Element rootElement = document.createElement("Pesho");
        document.appendChild(rootElement);

        for (Map.Entry<String, String> entry : map.entrySet()) {

             Element mapElement = document.createElement(entry.getKey());
             mapElement.setTextContent(entry.getValue());
             rootElement.appendChild(mapElement);

        }
        AdaptedMap adaptedMap = new AdaptedMap();
        adaptedMap.setValue(document.getDocumentElement());
        return adaptedMap;
    }

}



