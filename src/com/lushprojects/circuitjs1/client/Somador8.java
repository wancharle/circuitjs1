package com.lushprojects.circuitjs1.client;

import java.lang.Math;
public class Somador8 extends ChipElm {
 
    final int FLAG_ROTATE = 4096;
    int angulo=0;
    public Somador8(int xx, int yy) {
            super(xx, yy);
    }
    public Somador8(int xa, int ya, int xb, int yb, int f,StringTokenizer st) {
        super(xa, ya, xb, yb, f, st);
    }
 
    String getChipName() { return "Somador de 8 bits ("+Byte.toString(va)+" + "+Byte.toString(vb)+" = "+Byte.toString(vc)+")"; }

    void setupPins() {
        if ((flags & FLAG_ROTATE) != 0) angulo = 1;

        sizeX = (angulo==0)?17:3;
        sizeY = (angulo==0)?3:17;
        pins = new Pin[getPostCount()];
    
        String s=" ";
        for (int i=0;i < 8;i++){
            s = "A".concat(Integer.toString(7-i));
            pins[i] = new Pin(i,(angulo==0)? SIDE_N:SIDE_E, s);
            s = "B".concat(Integer.toString(7-i));
            pins[8+i] = new Pin(i+9,(angulo==0)? SIDE_N:SIDE_E, s);


            s = "O".concat(Integer.toString(7-i));
            pins[16+i] = new Pin(i+4, (angulo==0)?SIDE_S:SIDE_W, s);
            pins[16+i].output=true;	

        }
        pins[24] = new Pin(1, (angulo==0)?SIDE_E:SIDE_N, "OV");
        pins[24].output=true;	



    }

    int getPostCount() {
	    return 25;
    }

    int getVoltageSourceCount() {return 9;}

    byte va=0;
    byte vb=0;
    byte vc=0;
    void execute() {
        int valorA = 0;
        int valorB = 0;
        int valor = 0;
        vc= 0;
        boolean[] v = new boolean[8];

       // calcula valor em complemento de 1 
        for (int i=0;i<8;i++){
            valorA = valorA + (pins[7-i].value ? (int)Math.pow(2,i):0) ;
            valorB = valorB + (pins[7-i+8].value ? (int)Math.pow(2,i):0) ;
        }
        va=(byte)valorA;
        vb=(byte)valorB;


        valor =(valorA + valorB);
        if ((valorA >0 && valorB >0 && valor>127) || valor<-128){
            pins[24].value=true; //overflow;
         }else {
            pins[24].value=false; //overflow;
         }
         vc=(byte)valor;
        pins[16+0].value = (vc&128)!=0;
        pins[16+1].value = (vc&64)!=0;
        pins[16+2].value = (vc&32)!=0;
        pins[16+3].value = (vc&16)!=0;
        pins[16+4].value = (vc&8)!=0;
        pins[16+5].value = (vc&4)!=0;
        pins[16+6].value = (vc&2)!=0;
        pins[16+7].value = (vc&1)!=0;

	}
	
    int getDumpType() { return 1010; }
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
