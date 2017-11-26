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

// contributed by Edward Calver

    class DeMultiplexerElm8 extends ChipElm {
	boolean hasReset() {return false;}
	public DeMultiplexerElm8(int xx, int yy) { super(xx, yy); }
	public DeMultiplexerElm8(int xa, int ya, int xb, int yb, int f,
			    StringTokenizer st) {
	    super(xa, ya, xb, yb, f, st);
	}
	String getChipName() { return "DeMultiplexador de 8 saídas de 8 bits"; }

	void setupPins() {
	    sizeX = 4;
	    sizeY = 9;
	    pins = new Pin[getPostCount()];

	    pins[7] = new Pin(0, SIDE_E, "O7");
	    pins[7].output=true;
	    pins[6] = new Pin(1, SIDE_E, "O6");
	    pins[6].output=true;
	    pins[5] = new Pin(2, SIDE_E, "O5");
	    pins[5].output=true;
	    pins[4] = new Pin(3, SIDE_E, "O4");
	    pins[4].output=true;
		pins[3] = new Pin(4, SIDE_E, "O3");
	    pins[3].output=true;
	    pins[2] = new Pin(5, SIDE_E, "O2");
	    pins[2].output=true;
	    pins[1] = new Pin(6, SIDE_E, "O1");
	    pins[1].output=true;
	    pins[0] = new Pin(7, SIDE_E, "O0");
	    pins[0].output=true;
		
	    pins[8] = new Pin(0, SIDE_S, "E0");
	    pins[9] = new Pin(1, SIDE_S, "E1");
	    pins[10] = new Pin(2, SIDE_S, "E2");

	    pins[11] = new Pin(0, SIDE_W, "I");


	}
	int getPostCount() {
	    return 12;
	}
	int getVoltageSourceCount() {return 8;}

    void doStep() {
	    int i;
	    for (i = 0; i != getPostCount(); i++) {
		Pin p = pins[i];
		if (!p.output)
		    p.value = volts[i] > 2.5;
	    }
	    execute();
	    for (i = 0; i != getPostCount(); i++) {
		Pin p = pins[i];
		if (p.output)
            sim.updateVoltageSource(0, nodes[i], p.voltSource, (p.value)?volts[11]:0 ); // alterei aqui
	    }
    
    }


	 int selectedvalue=0;
	void execute() {

	 selectedvalue=0;
	 if(pins[8].value)selectedvalue++;
	 if(pins[9].value)selectedvalue+=2;
	 if(pins[10].value)selectedvalue+=4;
	 for(int i=0;i<8;i++)pins[i].value=false;
	 pins[selectedvalue].value=true;

	}
	int getDumpType() { return 1007; }

    }
