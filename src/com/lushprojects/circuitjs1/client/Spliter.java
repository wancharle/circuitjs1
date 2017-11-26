package com.lushprojects.circuitjs1.client;

import java.lang.Math;
public class Spliter  extends ChipElm {
    int totalPinos;
    static int START_MEN_PIN = 1; //pino que comeca a armazenar bits  
    int angulo=0;
  
    public Spliter(int xx, int yy) {super(xx, yy);totalPinos=8;}
    public Spliter(int xa, int ya, int xb, int yb, int f,
		    StringTokenizer st) {
	super(xa, ya, xb, yb, f, st);
    }

 
    String getChipName() { return "Spliter"; }

    void setupPins() {
	    if(totalPinos==0)//forca inicializacao padrao
		totalPinos=8;
		    
            sizeX = (angulo==0)?2:9;
            sizeY = (angulo==0)?9:2;
            pins = new Pin[getPostCount()];
        
            pins[0] = new Pin(4, ((angulo==0)? SIDE_W : SIDE_N), "I");
	    
            String s=" ";
            int iside = 0;
            int iq= 0;
            int id= 0;
            for (int i=START_MEN_PIN;i < (START_MEN_PIN+totalPinos);i++){
                iside = i - START_MEN_PIN + 0;
                iq = i;
                id = i+totalPinos;


                if (iside > 3)
                {                 
                    iside+=1; // shift
                    s = "I".concat(Integer.toString(totalPinos  - iside));
                }else
                    s = "I".concat(Integer.toString(totalPinos  - iside-1));
                pins[iq] = new Pin(iside, ((angulo==0)?SIDE_E:SIDE_S), s);
                pins[iq].output=true;	

           }

    }

    int getPostCount() {
	    return 1+1*totalPinos;
    }

    int getVoltageSourceCount() {return totalPinos;}


    void execute() {
        boolean setBit = false;
        double v = volts[0]; // pin0;
        int resto = (int)v;
        int comparador = 128;
        for (int i=1;i<= (totalPinos);i++){
           if (resto >= comparador){
              pins[i].value = true;
              resto = resto - comparador;
            }
            else{
              pins[i].value = false;
            }
           comparador = comparador /2;
        }
        
	}
	
    int getDumpType() { return 1004; }
    public EditInfo getEditInfo(int n) {
	    if (n<2) return super.getEditInfo(n);
	    if (n == 2){
	        EditInfo ei = new EditInfo("Rotação", angulo, -1, -1);
            ei.choice = new Choice();
            ei.choice.add("0 graus");
            ei.choice.add("90 graus");
            ei.choice.select(angulo); 
            return ei;
        }

	 
	    return null;
	}
	public void setEditValue(int n, EditInfo ei) {
	    if (n < 2) super.setEditValue(n, ei);
	    if (n == 2)
		{
		angulo = ei.choice.getSelectedIndex();
		setupPins();
		allocNodes();
		setPoints();
		}
	    	
	}
}
