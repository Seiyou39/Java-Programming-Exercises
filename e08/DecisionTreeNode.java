package e8;

import javax.swing.tree.DefaultMutableTreeNode;

public class DecisionTreeNode extends DefaultMutableTreeNode{
	private String nodeLabel;
    private String edgeLabel;
    
    public DecisionTreeNode(String nodeLabel) {
        this.nodeLabel = nodeLabel;
    }
    
    public String getNodeLabel() {
    	return nodeLabel;
    }
    
    public void setEdgeLabel(String edgeLabel) {
    	this.edgeLabel = edgeLabel;
    }
    
    public String getEdgeLabel() {
    	return edgeLabel;
    }
    
    public DecisionTreeNode getChildAt(int i) {
        return (DecisionTreeNode) super.getChildAt(i);
    }

}
