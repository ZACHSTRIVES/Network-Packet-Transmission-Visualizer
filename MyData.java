//Name: Zach Wang UPI: zwan270 ID: 240914081  This class use to settle data.
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.JFileChooser;

public class MyData {
	HashSet<String> souresHostIP= new HashSet();
	HashSet<String> destinationHostIP= new HashSet();
	ArrayList<Packet> packets=new ArrayList<Packet>();
    
	// Open file chooser and return packets array list.
	public ArrayList<Packet> getIpData(){
		
		File userchoose = null;
		JFileChooser fc= new JFileChooser();
		
		fc.setCurrentDirectory(new File("."));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		int i = fc.showOpenDialog(null);
		if(i==JFileChooser.APPROVE_OPTION) {
			
			userchoose = fc.getSelectedFile();
			BufferedReader br=null;
			try {
				br = new BufferedReader(new FileReader(userchoose));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String str = null;
		    try {
				while((str = br.readLine()) != null) {
					   Packet newpacket= new Packet(str);
					   if(newpacket.getSourceHost()!=null&&newpacket.getSourceHost().matches("^192\\.168\\.0\\.\\d{1,3}$")) {
						   packets.add(newpacket);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
    }
		return packets;
		
		
	}
	

	
    //Get sorted and unique destination hosts list.
	public static Object[] getUniqueSortedDestHosts(ArrayList<Packet> packets) {
	    HashSet<String> destHostIP= new HashSet<String>();
	    ArrayList<Host> destHostList= new ArrayList<Host>();
	    for(int i = 0;i < packets.size(); i ++){
	        destHostIP.add(packets.get(i).getDestinationHost());
	    }
	    for (String s:destHostIP){
	        destHostList.add(new Host(s));
	    }
	    int size= destHostList.size();
	    Object[] result =destHostList.toArray(new Object[size]);
	    Arrays.sort(result);
	    return result;
	    
	}
	//Get sorted and unique source hosts list.
	public static Object[] getUniqueSortedSourceHosts(ArrayList<Packet> packets) {
	    HashSet<String> souresHostIP= new HashSet<String>();
	    ArrayList<Host> srcHostList= new ArrayList<Host>();
	    for(int i = 0;i < packets.size(); i ++){
	        souresHostIP.add(packets.get(i).getSourceHost());
	    }
	    for (String s:souresHostIP){
	        srcHostList.add(new Host(s));
	    }
	    int size= srcHostList.size();
	    Object[] result =srcHostList.toArray(new Object[size]);
	    Arrays.sort(result);
	    return result;
	    
	}
	

}	

