//15824071 ZHANG JINGYANG
package e8;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleDT {
    private Path trainFilePath;
    private AttrList attrList;
    private DataList trainDataList;
    private DataList testDataList;
    private DecisionTreeNode rootNode;

    public SimpleDT(Path trainFilePath) {
        //必要なリストの初期化
        this.trainFilePath = trainFilePath;
        this.attrList = new AttrList();
        this.trainDataList = new DataList();
    }

    public AttrList getAttrList() {
        return attrList;
    }

    public DataList getTrainDataList() {
        return trainDataList;
    }

    public void readTrainFile() {
    	try {
			List<String> lines = Files.readAllLines(trainFilePath, StandardCharsets.UTF_8);
			
			attrList.setAttributes(lines.get(0));// 1行目は属性名なので AttrList に登録
			
			for (int i = 1; i < lines.size(); i++) { // 2行目以降がデータ本体
	            trainDataList.add(lines.get(i));
	        }
			
			System.out.printf("read %d records\n", trainDataList.size());
			
		}catch(Exception e) {
         e.printStackTrace();
		}
    }

    public void readTestFile(Path testFilePath) {
        // complete this method
    }

    private DataList[] divideData(DataList dlist, int col, int numOfDivisions) {
        DataList[] dividedDataLists = new DataList[numOfDivisions];
        for (int i = 0; i < numOfDivisions; i++) 
            dividedDataLists[i] = new DataList();
        Map<String, Integer> attrValueIndexMap = new HashMap<>();
        int index;

        for (ArrayList<String> data : dlist) {
            String value = data.get(col);
            if (attrValueIndexMap.containsKey(value)) index =
                attrValueIndexMap.get(value);
            else {
                index = attrValueIndexMap.size();
                attrValueIndexMap.put(value, index);
            }
            dividedDataLists[index].add(data);
        }
        return dividedDataLists;
    }

    private AttrList deleteAttribute(AttrList orgAttrList, int col) {
        AttrList newAttrList = new AttrList();
        for (int i = 0; i < orgAttrList.size(); i++) {
            if (i == col) continue;
            newAttrList.add(orgAttrList.get(i));
        }
        return newAttrList;
    }

    private double log2(double d) {
        return (double) Math.log(d)/Math.log(2.0);//log2計算式
    }

    public double subInfo(DataList dlist, int col, String attrValue) {
        double info = 0.0;
        HashMap<String, Integer> cDist = new HashMap<>();
        int count = 0;

        for (ArrayList<String> row : dlist) {//attrValue の中のクラス分布を数える
        	if(row.get(col).equals(attrValue)){
                String clas = row.get(row.size() - 1);
                cDist.put(clas, cDist.getOrDefault(clas, 0) + 1);//最後の列が Class
                count++;
            }
        }

        for (String key : cDist.keySet()) {//エントロピー計算
            double p = (double) cDist.get(key) / count;
            info += -p * log2(p);
        }
        
        return info;
    }

    public double postInfo(DataList dlist, int col, HashMap<String, Integer> attrValueFreqMap) {//Entropy after division
        double info = 0.0;
        int total = dlist.size();

        for (String Att : attrValueFreqMap.keySet()) {
            int n = attrValueFreqMap.get(Att);

            info += ((double) n / total) * (subInfo(dlist, col, Att));
        }

        return info;
    }

    public double preInfo(int numOfData, HashMap<String, Integer> cDist) {//Entropy before division
        double info = 0.0;
        for (String clas : cDist.keySet()) {
            double p = (double) cDist.get(clas) / numOfData;	
            info += -p * log2(p);
        }
        return info;
    }

    private String findMajority(HashMap<String, Integer> attrValueFreqMap) {
        int maxFreq = 0;
        String maxKey = null;
        for (String key : attrValueFreqMap.keySet()) {
            if (maxFreq < attrValueFreqMap.get(key)) {
                maxFreq = attrValueFreqMap.get(key);
                maxKey = key;
            }
        }
        return maxKey;
    }

    public ArrayList<HashMap<String, Integer>> getAttrValueFreqMapList(DataList dlist, AttrList alist) {
        ArrayList<HashMap<String, Integer>> attrValueFreqMap = new ArrayList<>();
        for (int i = 0; i < alist.size(); i++) {
            HashMap<String, Integer> map = new HashMap<>();
            
            
            int col = attrList.indexOf(alist.get(i));

            for (ArrayList<String> row : dlist) {
                String value = row.get(col);
                map.put(value, map.getOrDefault(value, 0) + 1);
            }
            attrValueFreqMap.add(map);
        }

        return attrValueFreqMap;
    }

    public DecisionTreeNode makeTree(DataList dlist, AttrList alist) {
        ArrayList<HashMap<String, Integer>> attrValueFreqMapList = getAttrValueFreqMapList(dlist, alist);
        DataList[] dividedDataLists;
        AttrList newAttrList;
        String clsName = null;
        double maxGain = 0.0;
        int maxId = -1;
        int col;

        if (alist.size() == 1) {
            clsName = findMajority(attrValueFreqMapList.get(0));
            return new DecisionTreeNode(clsName);
        }

        if (attrValueFreqMapList.get(alist.size() - 1).size() == 1) {
            for (String key : attrValueFreqMapList
                .get(alist.size() - 1)
                .keySet())
                clsName = key;
            return new DecisionTreeNode(clsName);
        }

        double preInfo = preInfo(
            dlist.size(),
            attrValueFreqMapList.get(alist.size() - 1)
        );
        System.out.printf("  preInfo: %.3f\n", preInfo);

        for (int i = 0; i < alist.size() - 1; i++) {
            double postInfo = postInfo(
                dlist,
                attrList.indexOf(alist.get(i)),
                attrValueFreqMapList.get(i)
            );
            double gain = preInfo - postInfo;
            System.out.printf("    gain for %s: %.3f\n", alist.get(i), gain);
            if (maxGain < gain) {
                maxGain = gain;
                maxId = i;
            }
        }

        if (maxGain == 0.0) {
            clsName = findMajority(attrValueFreqMapList.get(alist.size() - 1));
            return new DecisionTreeNode(clsName);
        }

        col = attrList.indexOf(alist.get(maxId));
        dividedDataLists = divideData(
            dlist,
            col,
            attrValueFreqMapList.get(maxId).size()
        );

        newAttrList = deleteAttribute(alist, maxId);

        DecisionTreeNode newNode = new DecisionTreeNode(alist.get(maxId));
        for (DataList dividedDataList : dividedDataLists) {
            if (!dividedDataList.isEmpty()) {
                DecisionTreeNode tmp = makeTree(dividedDataList, newAttrList);
                tmp.setEdgeLabel(dividedDataList.get(0).get(col));
                newNode.insert(tmp, newNode.getChildCount());
            }
        }
        return newNode;
    }

    public void buildTree() {
        rootNode = makeTree(trainDataList, attrList);
    }

    private void printNode(DecisionTreeNode node, int level) {
    	
        for (int i = 0; i < node.getChildCount(); i++) {
            DecisionTreeNode child = node.getChildAt(i);

            for (int j = 0; j < level; j++) {
                System.out.print("|   ");
            }

            System.out.print(node.getNodeLabel() + " = " + child.getEdgeLabel());

            if (child.getChildCount() == 0) {
                System.out.println(": " + child.getNodeLabel());
            } else {
                System.out.println();
                printNode(child, level + 1);
            }
        }
    }

    public void printTree() {
        printNode(rootNode, 0);
    }

    public void predictTestDataList() {
         // complete this method
    }

   /* private String predict(DecisionTreeNode node, List<String> data) {
         // complete this method
    }*/
   
    public void savePredictionsToFile(Path predictedFilePath) {
         // complete this method
     }

}
