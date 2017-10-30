package com.lushprojects.circuitjs1.client;

public class LogicOutputElm2 extends LogicOutputElm {
	String text;
	    
    public LogicOutputElm2(int xx, int yy) {
            super(xx, yy);
	    text = "out";
    }
    public LogicOutputElm2(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
	super(xa, ya, xb, yb, f, st);
	    text = st.nextToken();

    }
	
	int getDumpType() { return 1001; }

	String dump() {
	    return super.dump() + " " + CustomLogicModel.escape(text);
	}
	void draw(Graphics g){
	    super.draw(g);
	    String s = text;
	    drawValues(g, s, 4);    
	}
	
	public EditInfo getEditInfo(int n) {
	    if (n >= 0 && n<=2) {
		return super.getEditInfo(n);
	    }
	    if (n == 3)
	    {
		 EditInfo ei = new EditInfo("Text", 0, -1, -1);
		 ei.text = text;
		 return ei;
	    }
	    return null;
	}
	public void setEditValue(int n, EditInfo ei) {
	    if (n >=0 && n <=2){
		super.setEditValue(n,ei);
	    }
	    if (n==3){
		text = ei.textf.getText();
	    }
	}
	int getShortcut() { return 'o'; }
	
	
}