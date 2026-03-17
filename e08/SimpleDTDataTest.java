package e8;

import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class SimpleDTDataTest {

    public static boolean isArrayListOfString(Class<?> clazz) {
        Type superType = clazz.getGenericSuperclass();
        if (!(superType instanceof ParameterizedType)) return false;
        ParameterizedType pType = (ParameterizedType) superType;
        Type raw = pType.getRawType();
        if (!raw.equals(ArrayList.class)) return false;
        Type[] typeArgs = pType.getActualTypeArguments();
        return typeArgs.length == 1 && typeArgs[0].equals(String.class);
    }

    public static boolean isSubClassOfLinkedListOfArrayListString(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) return false;
        ParameterizedType pType = (ParameterizedType) type;
        if (!pType.getRawType().equals(LinkedList.class)) return false;
        Type[] typeArgs = pType.getActualTypeArguments();
        if (typeArgs.length != 1) return false;
        Type t = typeArgs[0];
        if (!(t instanceof ParameterizedType)) return false;
        ParameterizedType tParam = (ParameterizedType) t;
        if (!tParam.getRawType().equals(ArrayList.class)) return false;
        Type[] args2 = tParam.getActualTypeArguments();
        return args2.length == 1 && args2[0].equals(String.class);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("java e8.SimpleDTDataTest [ex8-1-1|ex8-1-2|ex8-1-3|ex8-1-4|ex8-1-5]");
            System.exit(-1);
        }
        String testType = args[0];

        switch (testType) {
            case "ex8-1-1":
                AttrList attrList = new AttrList();
                attrList.setAttributes("Outlook Temperature Humidity Windy Class");
                if (isArrayListOfString(AttrList.class) && attrList.equals(Arrays.asList("Outlook", "Temperature", "Humidity", "Windy", "Class"))) {
                    System.out.println("pass");
                } else {
                    System.out.println("fail");
                }
                break;
            case "ex8-1-2":
                DataList dataList = new DataList();
                dataList.add("sunny hot high false not_play");
                if (isSubClassOfLinkedListOfArrayListString(DataList.class) && dataList.get(0).equals(Arrays.asList("sunny", "hot", "high", "false", "not_play"))) {
                    System.out.println("pass");
                } else {
                    System.out.println("fail");
                }
                break;
            case "ex8-1-3":
                DecisionTreeNode node = new DecisionTreeNode("Outlook");
                node.setEdgeLabel("sunny");
                if (node instanceof DefaultMutableTreeNode && node.getNodeLabel().equals("Outlook") && node.getEdgeLabel().equals("sunny")) {
                    System.out.println("pass");
                } else {
                    System.out.println("fail");
                }
                break;
            case "ex8-1-4":
                node = new DecisionTreeNode("Outlook");
                DecisionTreeNode childNode = new DecisionTreeNode("Humidity");
                node.add(childNode);
                if (node.getChildAt(0) instanceof DecisionTreeNode) {
                    System.out.println("pass");
                } else {
                    System.out.println("fail");
                }
                break;
            case "ex8-1-5":
                node = new DecisionTreeNode("Outlook");
                childNode = new DecisionTreeNode("Humidity");
                DecisionTreeNode childNode2 = new DecisionTreeNode("Temperature");
                node.add(childNode);
                node.add(childNode2);
                if (node.getChildCount() == 2) {
                    System.out.println("pass");
                } else {
                    System.out.println("fail");
                }
                break;
        }
    }
}
