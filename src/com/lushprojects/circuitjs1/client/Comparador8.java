package com.lushprojects.circuitjs1.client;

import java.lang.Math;
public class Comparador8 extends Spliter{
 
    public Comparador8(int xx, int yy) {
            super(xx, yy);
    }
    public Comparador8(int xa, int ya, int xb, int yb, int f,StringTokenizer st) {
        super(xa, ya, xb, yb, f, st);
    }
 
    String getChipName() { return "Comparador de 8 bits (A "+comparador+" B) ("+Byte.toString(va)+" "+comparador+" "+Byte.toString(vb)+")"; }

    void setupPins() {
        if ((flags & FLAG_ROTATE) != 0) angulo = 1;

        sizeX = (angulo==0)?3:9;
        sizeY = (angulo==0)?9:3;
        pins = new Pin[getPostCount()];
    
        String s=" ";
        for (int i=0;i < 8;i++){
            s = "A".concat(Integer.toString(7-i));
            pins[i] = new Pin(i,(angulo==0)? SIDE_W:SIDE_N, s);
            s = "B".concat(Integer.toString(7-i));
            pins[8+i] = new Pin(i,(angulo==0)? SIDE_E:SIDE_S, s);
        }
        pins[16] = new Pin(0, (angulo==0)?SIDE_S:SIDE_E, "<");
        pins[16].output=true;	
        pins[17] = new Pin(1, (angulo==0)?SIDE_S:SIDE_E, "=");
        pins[17].output=true;	
        pins[18] = new Pin(2, (angulo==0)?SIDE_S:SIDE_E, ">");
        pins[18].output=true;	




    }

    int getPostCount() {
	    return 19;
    }

    int getVoltageSourceCount() {return 3;}

    byte va=0;
    byte vb=0;
    String comparador = "";
    void execute() {
        int valorA = 0;
        int valorB = 0;
        int valor = 0;
        boolean[] v = new boolean[8];

       // calcula valor em complemento de 1 
        for (int i=0;i<8;i++){
            valorA = valorA + (pins[7-i].value ? (int)Math.pow(2,i):0) ;
            valorB = valorB + (pins[7-i+8].value ? (int)Math.pow(2,i):0) ;
        }
        va=(byte)valorA;
        vb=(byte)valorB;
        
        pins[16].value=false;
        pins[17].value=false;
        pins[18].value=false;
        if (va == vb){
            comparador = "=";
            pins[17].value=true;
        }else if (va < vb){
            comparador = "<";
            pins[16].value=true;
        }else{
            comparador = ">";
            pins[18].value=true;
        }
	}
	
    int getDumpType() { return 1014; }
}
