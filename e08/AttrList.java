package e8;

import java.util.ArrayList;

public class AttrList extends ArrayList<String>{
	void setAttributes(String attrLine) {
		String[] attrs = attrLine.split("\\s+");
		
		for (String w : attrs) {
            this.add(w);
		}
	}

}
