package com.softserveinc.xmlhomework;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class XMLHomeworkApp {
    public static void main(String[] args) {
        ArrayList<String> xPathsList = new ArrayList<String>(){{
            add("./author");
            add("author");
            add("first.name");
            add("/bookstore");
            add("//author");
            add("book[/bookstore/@specialty=@style]");
            add("author/first-name");
            add("bookstore//title");
            add("bookstore/*/title");
            add("bookstore//book/excerpt//emph");
            add(".//title");
            add("author/*");
            add("book/*/last-name");
            add("*/*");
            add("*[@specialty]");
            add("@style");
            add("price/@exchange");
            add("price/@exchange/total");
            add("book[@style]");
            add("book/@style");
            add("@*");
            add("./first-name");
            add("first-name");
            add("author[1]");
            add("author[first-name][3]");
            add("my:book");
            add("my:*");
            add("@my:*");
        }};

        XMLHomeworkApp xmlHomeworkApp = new XMLHomeworkApp();
        Document doc = xmlHomeworkApp.readXML("inventory.xml");
        xmlHomeworkApp.printElementByXPath(xPathsList, doc);
    }

    private Document readXML(String fileLocation){
        Document doc = null;

        try {
            FileInputStream inputStream = new FileInputStream(getClass().getClassLoader().getResource(fileLocation).getFile());

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(inputStream);

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private void printElementByXPath (List<String> xPaths, Document doc){
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = null;
        NodeList nodeList;
        try {
            for (String xPath:xPaths){
                expr = xpath.compile(xPath);
                nodeList =  (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int i = 0; i < nodeList.getLength(); i++){
                    printNode(nodeList.item(i), xPath);
                }
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private void printNode(Node item, String xPath) {
        System.out.println("Result for " + "\""  + xPath + "\"" + " query");
        System.out.println("Node name - " + item.getNodeName());
        System.out.println("Node attributes quantity: " + item.getAttributes().getLength());
        System.out.println("Node children quantity: " + item.getChildNodes().getLength());
        for (int i = 0; i < item.getChildNodes().getLength(); i++){
            System.out.println("Child name: " + item.getChildNodes().item(i).getNodeName());
        }
        System.out.println("----------------------------------------------------------------");

        }
}
