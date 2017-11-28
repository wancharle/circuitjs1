package com.lushprojects.circuitjs1.client;

import java.lang.Math;
public class Subtrator8 extends Somador8{
 
    public Subtrator8(int xx, int yy) {
            super(xx, yy);
    }
    public Subtrator8(int xa, int ya, int xb, int yb, int f,StringTokenizer st) {
        super(xa, ya, xb, yb, f, st);
    }
 
    String getChipName() { return "Subtrator de 8 bits ("+Byte.toString(va)+" - "+Byte.toString(vb)+" = "+Byte.toString(vc)+")"; }

    int valor =0;
    void execute() {
        int valorA = 0;
        int valorB = 0;
        valor = 0;
        vc= 0;
        boolean[] v = new boolean[8];

       // calcula valor em complemento de 1 
        for (int i=0;i<8;i++){
            valorA = valorA + (pins[7-i].value ? (int)Math.pow(2,i):0) ;
            valorB = valorB + (pins[7-i+8].value ? (int)Math.pow(2,i):0) ;
        }
        va=(byte)valorA;
        vb=(byte)valorB;


        valor =(va - vb);
        if (valor>127 || valor<-128){
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
	
    int getDumpType() { return 1013; }
}
