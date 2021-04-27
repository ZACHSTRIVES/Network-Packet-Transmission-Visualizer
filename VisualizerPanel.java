//Name: Zach Wang UPI: zwan270 ID: 240914081  This class use to paint coordinate.
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.DoubleStream;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class VisualizerPanel extends JPanel {
	int slots=12;
	double[] xAxis=null;
	double[] yAxis =null;
	ArrayList<Packet> packetss= new ArrayList<Packet>() ;
	double endtime=600;
	int maxvolume=0;
	
	//paint and repaint coordinate 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 1000, 325);
		
		//draw lines
		g2.setColor(Color.BLACK);
		g2.drawLine(65, 30, 65, 280);
		g2.drawLine(60, 280, 965, 280);
		g2.drawString("Volume[bytes]", 10, 25);
		g2.drawString("Time[s]", 500, 320);
		int x=65;
		int y1=280;
		int y2=285;
		
		//draw X-axis
		for (int i=0; i<=this.endtime/xInfo(this.endtime); i++){
			   g.drawLine(x, y1, x, y2);
			   g.drawString(""+i*xInfo(endtime), x-10, y2+15);
			   x+=900*xInfo(endtime)/endtime;
			}
		//draw Y-axis
		if (!packetss.isEmpty()) {
	    	OptionalDouble maxVolume = DoubleStream.of(this.yAxis).max();
	    	maxvolume= (int)maxVolume.getAsDouble();
			
	    	x=65;
	    	int stringx=25;
	    	int y=280;
	    	int yInfo = yInfo(maxvolume);
	    	ArrayList<String> yStr= new ArrayList<String>();
	    	yStr.add("0");
	    	ArrayList<Integer> yNum= new ArrayList<Integer>();
	        for(int i=0; i<yInfo;i++) {
	        	int num=i*maxvolume/yInfo;
	        	yNum.add(num);
	        }
	        int firstNum=yNum.get(1);
	        if(firstNum>=1000000){ //Rounding to M
	        	double roundToM=firstNum/1000000;
	        	String afterRoundstr=String.format("%.1f", (roundToM));
	        	double afterRoundnum=Double.parseDouble(afterRoundstr);
	        	double num=afterRoundnum;
	        	for(int i=0; i<yInfo-1;i++) {
	        		yStr.add(num+"M");
	        		num+=afterRoundnum;
	        	}
	        
	        }else if(firstNum>=1000 && firstNum<1000000) {
	        	int roundToK=firstNum/1000;
	        	int num=roundToK;
	        	for(int i=0;i<yInfo-1;i++) {
	        		if (num<1000) {
	        			yStr.add(num+"k");
	        		}else {
	        			double roundToM=(double)num/1000.0;
	        			String after=String.format("%.1f", (roundToM));
	        			yStr.add(after+"M");
	        		}
	        	    num+=roundToK;
	        	}
	        }
	        
	        
	      
	    	for(int i=0; i<yInfo; i++) {
	    	   g.drawLine(x-5, y, x, y);
	    	   g.drawString(yStr.get(i), stringx, y+5);
	    	   y-=250/yInfo;
	    	}
	    }
		if (yAxis!=null) {
			OptionalDouble max1 = DoubleStream.of(this.yAxis).max();
	    	double max=max1.getAsDouble();
			double xsize=900/endtime;
			double ysize=250/max;
			double firstX1=65;
			
			for(int i=0; i<yAxis.length; i++) {
				if (yAxis[i] != 0) {
					Rectangle2D r= new Rectangle2D.Double(firstX1+(xAxis[i]*xsize),279-(yAxis[i]*ysize),xsize*2,yAxis[i]*ysize);
					g2.setColor(Color.blue);
					g2.draw(r);
				}
			}
		}else{
		     g2.setColor(Color.black);
		     g2.drawString("0",48, 284);
		}
		
	}
	
	//Get and provide graph data.
	public void getGraphData(ArrayList<Packet> packets, String ipFilter, boolean isSrcHost, double endTime, int interval) {
	      int slot= (int)Math.ceil(endTime/interval);
	      double[] x= new double[slot];
	      double[] y= new double[slot];
	      double[] z = new double[slot+1];
	      packetss=packets;
	      z[0] = 0.0;
	      for(int i=0; i<slot;i++) {
	    	  x[i] = new Double(interval)+interval*i;
			  z[i+1] = x[i];
	      }
	      
	     for(int i=0; i<slot;i++) {
	    	 int packetSize = 0;
	     
	    	 for(int j=0; j<packets.size();j++) {
	    		 Packet packet = packets.get(j);
	    		 String sourceHost = packet.getSourceHost();
	    		 String destHost=packet.getDestinationHost();
	    		 double timeStamp = packet.getTimeStamp();
	    		 
		    	  if(isSrcHost==true) {
		    		  if(timeStamp>=z[i] && i<=slot-1 && timeStamp<z[i+1] && sourceHost!=null && sourceHost.equals(ipFilter)) {
		    			  packetSize += packet.getIpPacketSize();
		    		  }
		    		  
		    	  }else {
		    		  if(timeStamp>=z[i] && i<=slot-1 && timeStamp<z[i+1] && destHost!=null && destHost.equals(ipFilter)) {
		    			  packetSize += packet.getIpPacketSize();
		    	  }
		      }
	    	 y[i]=new Double(packetSize);
	     }
	    
}
	     this.xAxis=z;
	     this.yAxis=y;
	     slots=13;
	     endtime=endTime;
	}
    //get number of interval of X-axis.
    public int xInfo(double endTime) {
		   if (endTime>=1000){
		      return 100;
		   }else if(endTime<1000 && endTime>=400) {
		      return 50;
		   }else if(endTime<400 && endTime>=230) {
		      return 20;
		   }else if(endTime<230 && endTime>=100) {
		      return 10;
		   }else if(endTime<100 && endTime>=40) {
		      return 5;
		   }else if(endTime<40 && endTime>=10) {
		      return 2;
		   }else if(endTime<10) {
		      return 1;
		   }
		   return 50;
		}
  //get number of interval of Y-axis.
    public int yInfo(int maxPackSize) {
    	   int m1 = maxPackSize/4;
    	   //1000   1K
    	   //1000000  1M
    	   //1000000000 1G
    	   if(m1<1000) {
    	      return 5;
    	   }
    	   if(m1>=1000 && m1<10000) {//10K
    	      return 6;
    	   }
    	   if(m1>=10000 && m1<100000) {//100K
    	      return 7;
    	   }
    	   if(m1>=100000 && m1<1000000) {//1M
    	      return 8;
    	   }
    	   if(m1>=1000000 && m1<10000000) {//10M
    	      return 9;
    	   }
    	   if(m1>=10000000 && m1<100000000) {//100M
    	      return 10;
    	   }
    	   if(m1>=100000000 && m1<1000000000) {//1G
    	      return 11;
    	   }
    	   return 0;
    	}

}



