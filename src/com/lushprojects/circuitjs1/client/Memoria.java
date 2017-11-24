package com.lushprojects.circuitjs1.client;

import java.lang.Math;
public class Memoria extends ChipElm {
    static byte[] buffer = new byte[256];
    static int totalPinos=8;

    int endereco=0;
    int angulo=0;
    static int[][] POS = {
    {0,3,1,2},
    {2,0,3,1},
    {1,2,0,3},
    {3,1,2,0}};
    static int START_MEN_PIN = 3; //pino que comeca a armazenar bits  
   
    public Memoria(int xx, int yy) {super(xx, yy);totalPinos=8;}
    public Memoria(int xa, int ya, int xb, int yb, int f,
		    StringTokenizer st) {
	super(xa, ya, xb, yb, f, st);
    }

 
    String getChipName() { return "Memória de 256 endereços"; }

    void draw(Graphics g){
        super.draw(g);
	    Font f = new Font("SansSerif", 0, 14*csize);
        g.setFont(f);
		g.drawString("Memória",rectPointsX[0]+(int)((sizeX*cspc2/10)*3.3),rectPointsY[0]+sizeY*cspc2/2);
	
    }
    void setupPins() {
	    if(totalPinos==0)//forca inicializacao padrao
            totalPinos=8;
		    
        sizeX = totalPinos+2;
        sizeY = totalPinos+2;
        pins = new Pin[getPostCount()];
        pins[0] = new Pin(0, POS[angulo][2], "W");
        pins[1] = new Pin(4, POS[angulo][2], "");
        pins[1].clock=true;
        pins[2] = new Pin(9, POS[angulo][2], "R");
    
       String s=" ";
        int iside = 0;
        int iq = 0;
        int id = 0;
        int im = 0;
        int ie = 0;
        for (int i=START_MEN_PIN;i < (START_MEN_PIN+totalPinos);i++){
            iside = i - START_MEN_PIN + 1;
            iq = i;
            im = i+totalPinos;
            ie = im+totalPinos;

            s = "Q".concat(Integer.toString(totalPinos  - iside));
            pins[iq] = new Pin(iside, POS[angulo][1], s);
            pins[iq].output=true;	

            s = "M".concat(Integer.toString(totalPinos  - iside ));
            pins[im] = new Pin(iside, POS[angulo][3], s);

            s = "E".concat(Integer.toString(totalPinos  - iside ));
            pins[ie] = new Pin(iside, POS[angulo][0], s);



        }

    }

    int getPostCount() {
	    return 3+24;//+3*totalPinos;
    }

    int getVoltageSourceCount() {return 8;}//totalPinos;}


    void execute() {
        boolean setBit = false;
        // calcula o endereço baseado no input E
       
        endereco = 0;
        for (int i=0;i<8;i++)
            endereco = endereco + (pins[START_MEN_PIN+16 +7-i].value ? (int)Math.pow(2,i):0) ;

        // seleciona o byte que sera mostrado na saida;
        byte data = buffer[endereco];
        
        // escreve na memoria
		if (pins[1].value && !lastClock){
           if (pins[0].value == true){ 
                // write enable
                for (int i=0;i< (totalPinos);i++){
                    setBit = pins[START_MEN_PIN+totalPinos +i].value;
                    data = setBit ? (byte)(data | (1 << i))
                                  : (byte)(data & ~(1 << i));
                }
                buffer[endereco]=data;

           } 
        }     

        if (pins[2].value == true)  // enable on
            for (int i=0;i< (totalPinos);i++) pins[START_MEN_PIN+i].value = ((data & (byte)Math.pow(2,i)) != 0);
        else
            for (int i=0;i< (totalPinos);i++) pins[START_MEN_PIN+i].value = false;
	    lastClock = pins[1].value;
	}
	
    int getDumpType() { return 1003; }
    public EditInfo getEditInfo(int n) {
	    if (n<2) return 	   super.getEditInfo(n);
	  if (n == 2){
	        EditInfo ei = new EditInfo("Rotação",angulo , -1, -1);
            ei.choice = new Choice();
            ei.choice.add("0 ");
            ei.choice.add("90");
            ei.choice.add("180");
            ei.choice.add("240");
            ei.choice.select(0); 
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
