package com.lushprojects.circuitjs1.client;

public class Registrador extends ChipElm {
    short data=0;//This has to be a short because there's no unsigned byte and it's screwing with my code
    int totalPinos;
    boolean clockstate=false;
    
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
		    
	    super.log(Integer.toString(totalPinos));
            sizeX = totalPinos+1;
            super.log(Integer.toString(sizeX));
            sizeY = 3;
            pins = new Pin[getPostCount()];
        
            pins[0] = new Pin(1, SIDE_W, "D");
            pins[1] = new Pin(2, SIDE_W, "");
            pins[1].clock=true;
            super.log("testando");
            String s=" ";
	    
            for (int i=1;i<=(totalPinos+1);i++){
        	s = "I".concat(Integer.toString(totalPinos + 1 - i));
        	super.log(s);
        	 pins[i+1] = new Pin(i, SIDE_N, s);
        	 pins[i+1].output=true;	
            }

    }

    int getPostCount() {
	    return 2+totalPinos;
    }

    int getVoltageSourceCount() {return totalPinos;}


    void execute() {

		if(pins[1].value&&!clockstate)
		{
		clockstate=true;
		//for (int i=2;i<= (totalPinos+1);i++)
		//    pins[i].value=false;
		
		//
		}
		if(!pins[1].value)clockstate=false;
	}
	
    int getDumpType() { return 1002; }
    public EditInfo getEditInfo(int n) {
	    if (n<2) return super.getEditInfo(n);
	    if (n == 2)
		return new EditInfo("Bits", totalPinos, 1, 128);
	 
	    return null;
	}
	public void setEditValue(int n, EditInfo ei) {
	    if (n < 2) super.setEditValue(n, ei);
	    if (n == 2)
		{
		totalPinos = (int)ei.value;
		setupPins();
		allocNodes();
		setPoints();
		}
	    	
	}
}
