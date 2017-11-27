package com.lushprojects.circuitjs1.client;

import java.lang.Math;
public class Complemento1 extends ChipElm {
 
    final int FLAG_ROTATE = 4096;
    int angulo=0;
    public Complemento1(int xx, int yy) {
            super(xx, yy);
    }
    public Complemento1(int xa, int ya, int xb, int yb, int f,StringTokenizer st) {
        super(xa, ya, xb, yb, f, st);
    }
 
    String getChipName() { return "Complemento de 1 para 8 bits"; }

    void setupPins() {
        if ((flags & FLAG_ROTATE) != 0) angulo = 1;

        sizeX = (angulo==0)?8:3;
        sizeY = (angulo==0)?3:8;
        pins = new Pin[getPostCount()];
    
        String s=" ";
        for (int i=0;i < 8;i++){
            s = "I".concat(Integer.toString(7-i));
            pins[i] = new Pin(i,(angulo==0)? SIDE_S:SIDE_E, s);

            s = "O".concat(Integer.toString(7-i));
            pins[8+i] = new Pin(i, (angulo==0)?SIDE_N:SIDE_W, s);
            pins[8+i].output=true;	

        }

    }

    int getPostCount() {
	    return 16;
    }

    int getVoltageSourceCount() {return 8;}


    void execute() {
        for (int i=0;i<8;i++)
            pins[8+i].value = !pins[i].value;
	}
	
    int getDumpType() { return 1008; }
    public EditInfo getEditInfo(int n) {
	    if (n<2) return super.getEditInfo(n);
	    if (n == 2){
        	EditInfo ei = new EditInfo("", 0, -1, -1);
		    ei.checkbox = new Checkbox("Rotacione", (flags & FLAG_ROTATE) != 0);
            return ei;
        }

	 
	    return null;
	}
	public void setEditValue(int n, EditInfo ei) {
	    if (n < 2) super.setEditValue(n, ei);
	    if (n == 2)
		{
		if (ei.checkbox.getState()){
		    flags |= FLAG_ROTATE;
            angulo=1;
		}else{
		    flags &= ~FLAG_ROTATE;
            angulo=0;
        }	
	
		setupPins();
		allocNodes();
		setPoints();
		}
	    	
	}
}
