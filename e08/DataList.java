package e8;

import java.util.ArrayList;
import java.util.LinkedList;

public class DataList extends LinkedList<ArrayList<String>>{
	void add(String dataLine) {
		if(!dataLine.isEmpty()) {
			String[] tokens = dataLine.split("\\s+");
			ArrayList<String> record = new ArrayList<>();
			for(String x : tokens) {
				record.add(x);
			}
		
		super.add(record);
		
		}
	}
}
