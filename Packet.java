//Name: Zach Wang UPI: zwan270 ID: 240914081  This class use to get data from a string line.
public class Packet {
	String[] info;
    double timestamp;
    String srcHost,destHost;
    int ipPacketSize;
    public Packet(String str){
        this.info=split(str);
    }
    
    public String getSourceHost(){
		if(info.length>=7) {
			return info[2];
		}
		return srcHost;
    }

    public String getDestinationHost(){
		if(info.length==7) {
			return info[3];
		}
		if(info.length>7) {
			return info[4];
		}
    	return destHost;
    }

    public double getTimeStamp() {
		return new Double(info[1]).doubleValue();
    }

    public int getIpPacketSize() {
		if(info.length==7) {
			return new Integer(info[5]).intValue();
		}
		if(info.length>7) {
			return new Integer(info[7]).intValue();
		}
    	return ipPacketSize;
   }
    

    private String[] split(String line){
    	return line.split("\\s+");
	}
    
}
