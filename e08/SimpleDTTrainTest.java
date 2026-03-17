package e8;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleDTTrainTest {

    public static void testGetAttrValueFreqMapList(SimpleDT dt) {
        AttrList attrList = dt.getAttrList();
        DataList dataList = dt.getTrainDataList();
        List<HashMap<String, Integer>> attrValueFreqMapList = dt.getAttrValueFreqMapList(dataList, attrList);
        for (int i = 0; i < attrValueFreqMapList.size(); i++) {
            String attrName = attrList.get(i);
            Map<String, Integer> attrValueFreqMap = attrValueFreqMapList.get(i);
            for (Map.Entry<String, Integer> attrValueFreqEntry : attrValueFreqMap.entrySet()) {
                String attrValue = attrValueFreqEntry.getKey();
                int freq = attrValueFreqEntry.getValue();
                System.out.printf("%s %s %d\n", attrName, attrValue, freq);
            }
        }
    }

    public static void testPreInfo(SimpleDT dt) {
        AttrList attrList = dt.getAttrList();
        DataList dataList = dt.getTrainDataList();
        List<HashMap<String, Integer>> attrValueFreqMapList = dt.getAttrValueFreqMapList(dataList, attrList);
        double preInfo = dt.preInfo(dataList.size(), attrValueFreqMapList.get(attrList.size() - 1));
        System.out.printf("Root preInfo %.3f\n", preInfo);
    }

    public static void testSubInfo(SimpleDT dt) {
        AttrList attrList = dt.getAttrList();
        DataList dataList = dt.getTrainDataList();
        List<HashMap<String, Integer>> attrValueFreqMapList = dt.getAttrValueFreqMapList(dataList, attrList);
        Map<String, Integer> attrValueFreqMap = attrValueFreqMapList.get(0);
        for (String attrValue : attrValueFreqMap.keySet()) {
            double subInfo = dt.subInfo(dataList, 0, attrValue);
            System.out.printf("%s %s subInfo %.3f\n", attrList.get(0), attrValue, subInfo);
        }
    }

    public static void testPostInfo(SimpleDT dt) {
        AttrList attrList = dt.getAttrList();
        DataList dataList = dt.getTrainDataList();
        List<HashMap<String, Integer>> attrValueFreqMapList = dt.getAttrValueFreqMapList(dataList, attrList);
        for (int i = 0; i < attrValueFreqMapList.size() - 1; i++) {
            double postInfo = dt.postInfo(dataList, i, attrValueFreqMapList.get(i));
            System.out.printf("%s postInfo %.3f\n", attrList.get(i), postInfo);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("java e8.SimpleDTTrainTest [testGetAttrValueFreqMapList|testPreInfo|testSubInfo|testPostInfo|testPrintTree] [train file path]");
            System.exit(-1);
        }
        String testType = args[0];
        Path trainFilePath = Path.of(args[1]);
        SimpleDT dt = new SimpleDT(trainFilePath);
        dt.readTrainFile();

        switch (testType) {
            case "testGetAttrValueFreqMapList":
                testGetAttrValueFreqMapList(dt);
                break;
            case "testPreInfo":
                testPreInfo(dt);
                break;
            case "testSubInfo":
                testSubInfo(dt);
                break;
            case "testPostInfo":
                testPostInfo(dt);
                break;
            case "testPrintTree":
                dt.buildTree();
                dt.printTree();
                break;
        }
    }
}
