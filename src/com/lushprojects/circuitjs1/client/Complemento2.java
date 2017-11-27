package com.lushprojects.circuitjs1.client;

import java.lang.Math;
public class Complemento2 extends ChipElm {
 
    final int FLAG_ROTATE = 4096;
    int angulo=0;
    public Complemento2(int xx, int yy) {
            super(xx, yy);
    }
    public Complemento2(int xa, int ya, int xb, int yb, int f,StringTokenizer st) {
        super(xa, ya, xb, yb, f, st);
    }
 
    String getChipName() { return "Complemento de 2 para 8 bits -> C2("+Byte.toString(va)+")="+Byte.toString(vb); }

    void setupPins() {
        if ((flags & FLAG_ROTATE) != 0) angulo = 1;

        sizeX = (angulo==0)?8:3;
        sizeY = (angulo==0)?3:8;
        pins = new Pin[getPostCount()];
    
        String s=" ";
        for (int i=0;i < 8;i++){
            s = "I".concat(Integer.toString(7-i));
            pins[i] = new Pin(i,(angulo==0)? SIDE_N:SIDE_E, s);

            s = "O".concat(Integer.toString(7-i));
            pins[8+i] = new Pin(i, (angulo==0)?SIDE_S:SIDE_W, s);
            pins[8+i].output=true;	

        }

    }

    int getPostCount() {
	    return 16;
    }

    int getVoltageSourceCount() {return 8;}


    byte vb = 0;
    byte va = 0;
    void execute() {
        int valor = 0;

        va=0;
        // calcula valor em complemento de 1 
        for (int i=0;i<8;i++)
            valor = valor + (pins[7-i].value ? (int)Math.pow(2,i):0) ;
        va=(byte)valor;

        if (valor>255)valor=(int)(byte)valor;
        vb = (byte)(va*(-1));
        pins[8+0].value = (vb&128)!=0;
        pins[8+1].value = (vb&64)!=0;
        pins[8+2].value = (vb&32)!=0;
        pins[8+3].value = (vb&16)!=0;
        pins[8+4].value = (vb&8)!=0;
        pins[8+5].value = (vb&4)!=0;
        pins[8+6].value = (vb&2)!=0;
        pins[8+7].value = (vb&1)!=0;


       
	}
	
    int getDumpType() { return 1009; }
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
