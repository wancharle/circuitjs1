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

//
//import java.awt.*;
//import java.util.StringTokenizer;

// contributed by Edward Calver

    class MultiplexerElm8 extends ChipElm {
	boolean hasReset() {return false;}
	public MultiplexerElm8(int xx, int yy) { super(xx, yy); }
	public MultiplexerElm8(int xa, int ya, int xb, int yb, int f,
			    StringTokenizer st) {
	    super(xa, ya, xb, yb, f, st);
	}
	String getChipName() { return "Multiplexador de 8 Bits"; }

	void setupPins() {
	    sizeX = 4;
	    sizeY = 9;
	    pins = new Pin[getPostCount()];

	    pins[7] = new Pin(0, SIDE_W, "I7");
	    pins[6] = new Pin(1, SIDE_W, "I6");
	    pins[5] = new Pin(2, SIDE_W, "I5");
	    pins[4] = new Pin(3, SIDE_W, "I4");
	    pins[3] = new Pin(4, SIDE_W, "I3");
	    pins[2] = new Pin(5, SIDE_W, "I2");
	    pins[1] = new Pin(6, SIDE_W, "I1");
	    pins[0] = new Pin(7, SIDE_W, "I0");
		
	    pins[8] = new Pin(1, SIDE_S, "E0");
	    pins[9] = new Pin(2, SIDE_S, "E1");
	    pins[10] = new Pin(3, SIDE_S, "E2");

	    pins[11] = new Pin(0, SIDE_E, "O");
	    pins[11].output=true;

	}
	int getPostCount() {
	    return 12;
	}
	int getVoltageSourceCount() {return 1;}

    void doStep() {
	    int i;
	    for (i = 0; i != getPostCount(); i++) {
		Pin p = pins[i];
		if (!p.output)
		    p.value = volts[i] > 2.5;
	    }
	    execute();
		Pin p = pins[11];
		sim.updateVoltageSource(0, nodes[i], p.voltSource, volts[selectedvalue] ); // alterei aqui
    }


	int selectedvalue=0;
	void execute() {
     selectedvalue = 0;
	 if(pins[8].value)selectedvalue++;
	 if(pins[9].value)selectedvalue+=2;
	 if(pins[10].value)selectedvalue+=4;
	 pins[11].value=pins[selectedvalue].value;

	}
	int getDumpType() { return 1006; }

    }
