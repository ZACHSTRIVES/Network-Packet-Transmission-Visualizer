//Name: Zach Wang UPI: zwan270 ID: 240914081  This class use to make hosts comparable.
class Host implements Comparable<Host>{
    String ip;
    public Host(String ip){
        this.ip=ip;
    }
    public String toString(){
        return ip;
    }
    public int compareTo(Host other){
        String[] numbers = ip.split("\\.");
        String[] othernum = other.ip.split("\\.");
        int num3 = Integer.parseInt(numbers[2]);
        int num4 = Integer.parseInt(othernum[2]);
        if(num3 == num4) {
        int num1 = Integer.parseInt(numbers[3]);
        int num2 = Integer.parseInt(othernum[3]);
        return num1 - num2;}
        else {
         return num3 - num4;
        }

}
}