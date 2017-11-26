package com.lushprojects.circuitjs1.client;

import java.lang.Math;
public class Jointer extends ChipElm {
    int totalPinos;
    int voltagem=0;
    final int FLAG_ROTATE=4096;

    static int START_MEN_PIN = 1; //pino que comeca a armazenar bits  
    int angulo=0;
  
    public Jointer(int xx, int yy) {super(xx, yy);totalPinos=8;}
    public Jointer(int xa, int ya, int xb, int yb, int f,
		    StringTokenizer st) {
	super(xa, ya, xb, yb, f, st);
    }

 
    String getChipName() { return "Jointer (junta 8 fios em 1)"; }

    void setupPins() {
        if ((flags & FLAG_ROTATE) != 0) angulo = 1;
	    if(totalPinos==0)//forca inicializacao padrao
		totalPinos=8;
		    
            sizeX = (angulo==0)?2:9;
            sizeY = (angulo==0)?9:2;
            pins = new Pin[getPostCount()];
        
            pins[0] = new Pin(4, ((angulo==0)? SIDE_W : SIDE_N), "O");
            pins[0].output=true;	
	    
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

           }

    }

    int getPostCount() {
	    return 1+1*totalPinos;
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
	    for (i = 0; i != getPostCount(); i++) {
		Pin p = pins[i];
		if (p.output)
		    sim.updateVoltageSource(0, nodes[i], p.voltSource, voltagem ); // alterei aqui
	    }
    }

    void execute() {
        boolean setBit = false;
        double v = volts[0]; // pin0;
        int resto = (int)v;
        int comparador = 128;

        voltagem = 0;

        for (int i=0;i<8;i++)
            voltagem = voltagem + (pins[7-i+1].value ? (int)Math.pow(2,i):0) ;
        super.log(Integer.toString(voltagem));
        
	}
	
    int getDumpType() { return 1005; }
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
