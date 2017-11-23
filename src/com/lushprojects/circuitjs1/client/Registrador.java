package com.lushprojects.circuitjs1.client;

import java.lang.Math;
public class Registrador extends ChipElm {
    byte data=126;// 8 bits sinalizado em complemento de 2 vai de -128 para 127 
    int totalPinos;
    boolean clockstate=false;
    static int START_MEN_PIN = 3; //pino que comeca a armazenar bits  
   
    boolean hasReset() {return false;}
    public Registrador(int xx, int yy) {super(xx, yy);totalPinos=8;}
    public Registrador(int xa, int ya, int xb, int yb, int f,
		    StringTokenizer st) {
	super(xa, ya, xb, yb, f, st);
    }

 
    String getChipName() { return "Registrador de N bits"; }

    void setupPins() {
	    if(totalPinos==0)//forca inicializacao padrao
		totalPinos=8;
		    
            sizeX = totalPinos+3;
            sizeY = 3;
            pins = new Pin[getPostCount()];
        
            pins[0] = new Pin(0, SIDE_W, "WR");
            pins[1] = new Pin(1, SIDE_W, "");
            pins[1].clock=true;
            pins[2] = new Pin(2, SIDE_W, "EN");
	    
            String s=" ";
            int iside = 0;
            int iq= 0;
            int id= 0;
            for (int i=START_MEN_PIN;i < (START_MEN_PIN+totalPinos);i++){
                iside = i - START_MEN_PIN + 2;
                iq = i;
                id = i+totalPinos;

                s = "Q".concat(Integer.toString(totalPinos  - iside +1));
                pins[iq] = new Pin(iside, SIDE_S, s);
                pins[iq].output=true;	

                s = "D".concat(Integer.toString(totalPinos  - iside +1));
                pins[id] = new Pin(iside, SIDE_N, s);

            }

    }

    int getPostCount() {
	    return 3+2*totalPinos;
    }

    int getVoltageSourceCount() {return totalPinos;}


    void execute() {
        boolean setBit = false;
		if (pins[1].value && !lastClock){
           if (pins[0].value == true) // write enable
                for (int i=0;i< (totalPinos);i++){
                    setBit = pins[START_MEN_PIN+totalPinos +i].value;
                    data = setBit ? (byte)(data | (1 << i))
                                  : (byte)(data & ~(1 << i));
                }
                
        }     

        if (pins[2].value == true)  // enable on
            for (int i=0;i< (totalPinos);i++) pins[START_MEN_PIN+i].value = ((data & (byte)Math.pow(2,i)) != 0);
        else
            for (int i=0;i< (totalPinos);i++) pins[START_MEN_PIN+i].value = false;
	    lastClock = pins[1].value;
	}
	
    int getDumpType() { return 1002; }
    public EditInfo getEditInfo(int n) {
	    if (n<2) return super.getEditInfo(n);
	    if (n == 2){
            double choice = Math.log(totalPinos)/Math.log(2);
	        EditInfo ei = new EditInfo("Bits", choice, -1, -1);
            ei.choice = new Choice();
            ei.choice.add("4 Bits");
            ei.choice.add("8 Bits");
            ei.choice.select((int)(choice-2)); 
            return ei;
        }

	 
	    return null;
	}
	public void setEditValue(int n, EditInfo ei) {
	    if (n < 2) super.setEditValue(n, ei);
	    if (n == 2)
		{
		double choice = ei.choice.getSelectedIndex();
        totalPinos = (int)Math.pow(2, (choice+1)+1);
		setupPins();
		allocNodes();
		setPoints();
		}
	    	
	}
}
