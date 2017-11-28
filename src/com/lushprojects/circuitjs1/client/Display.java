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

class Display extends ChipElm {
	public Display(int xx, int yy) { super(xx, yy); }
	public Display(int xa, int ya, int xb, int yb, int f,
			   StringTokenizer st) {
	    super(xa, ya, xb, yb, f, st);
	}
	String getChipName() { return "Display"; }
	Color darkred;
	void setupPins() {
	    darkred = new Color(30, 0, 0);
	    sizeX = 5;
	    sizeY = 4;
	    pins = new Pin[8];
	    pins[7] = new Pin(0, SIDE_W, "7");
	    pins[6] = new Pin(1, SIDE_W, "6");
	    pins[5] = new Pin(2, SIDE_W, "5");
	    pins[4] = new Pin(3, SIDE_W, "4");
	    pins[3] = new Pin(1, SIDE_S, "3");
	    pins[2] = new Pin(2, SIDE_S, "2");
	    pins[1] = new Pin(3, SIDE_S, "1");
	    pins[0] = new Pin(4, SIDE_S, "0");
	}
	void draw(Graphics g) {
	    drawChip(g);
	    g.setColor(Color.red);
		Font oldf=g.getFont();
	    Font f = new Font("SansSerif", Font.BOLD, 42);
	    g.setFont(f);
	    drawCenteredText(g, Byte.toString(va), x+100, y+50, true);
	    g.setFont(oldf);
	}
    byte va =0;
    void execute(){
        int valorA =0; 
        for (int i=0;i<8;i++)
            valorA = valorA + (pins[i].value ? (int)Math.pow(2,i):0) ;
        va = (byte)valorA;
 
    } 

	int getPostCount() { return 8; }
	int getVoltageSourceCount() { return 0; }
	int getDumpType() { return 1011; }
    }
