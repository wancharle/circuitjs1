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

class Display8 extends Display{
	public Display8(int xx, int yy) { super(xx, yy); }
	public Display8(int xa, int ya, int xb, int yb, int f,
			   StringTokenizer st) {
	    super(xa, ya, xb, yb, f, st);
	}
	String getChipName() { return "Display com spliter embutido"; }
	void setupPins() {
	    darkred = new Color(30, 0, 0);
	    sizeX = 5;
	    sizeY = 3;
	    pins = new Pin[1];
	    pins[0] = new Pin(1, SIDE_W, "I/8");
	}
    void execute(){
        double valorA = volts[0];
        va = (byte)valorA;
    } 
	void draw(Graphics g) {
	    drawChip(g);
	    g.setColor(Color.red);
		Font oldf=g.getFont();
	    Font f = new Font("SansSerif", Font.BOLD, 42);
	    g.setFont(f);
	    drawCenteredText(g, Byte.toString(va), x+100, y+30, true);
	    g.setFont(oldf);
	}


	int getPostCount() { return 1; }
	int getDumpType() { return 1012; }
    }
