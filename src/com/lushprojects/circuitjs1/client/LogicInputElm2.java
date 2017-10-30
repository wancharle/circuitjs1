/*    
    Copyright (C) Paul Falstad and Iain Sharp
    
    This file is part of CircuitJS1.

    CircuitJS1 is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 2 of the License, or
    (at your option) any later version.

    CircuitJS1 is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CircuitJS1.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.lushprojects.circuitjs1.client;

//import java.awt.*;
//import java.util.StringTokenizer;

    class LogicInputElm2 extends LogicInputElm {
	String text;
	public LogicInputElm2(int xx, int yy) {
            super(xx, yy);
	    text = "in";
	}
	public LogicInputElm2(int xa, int ya, int xb, int yb, int f,  StringTokenizer st) {
	    super(xa, ya, xb, yb, f, st);
	    text = st.nextToken();

	}
	int getDumpType() { return 1000; }
	String dump() {
	    return super.dump() + " " + CustomLogicModel.escape(text);
	}
	
	int getPostCount() { return 1; }
	void draw(Graphics g) {
    	    super.draw(g);
	    String s = text;
	    drawValues(g, s, 4);
	}
	
	 
	public EditInfo getEditInfo(int n) {
	    if (n >= 0 && n<=3) {
		return super.getEditInfo(n);
	    }
	    if (n == 4) {
		    EditInfo ei = new EditInfo("Text", 0, -1, -1);
		    ei.text = text;
		    return ei;
	    }
	    return null;
	}
	public void setEditValue(int n, EditInfo ei) {
	   super.setEditValue(n, ei);
	   
	    if (n == 4) {
		    text = ei.textf.getText();
	    }
	}
	int getShortcut() { return 'i'; }
	
	
    }
