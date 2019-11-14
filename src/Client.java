public class Client {
    private int arrivalTime;
    private int serverTime;

    public Client (int serverTime)
    {
        this.arrivalTime=0;
        this.serverTime=serverTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setServerTime(int serverTime) {
        this.serverTime = serverTime;
    }

    public int getArrivalTime() {

        return arrivalTime;
    }

    public int getServerTime() {
        return serverTime;
    }

    @Override
    public String toString() {
        return "Client{" +
                "arrivalTime=" + arrivalTime +
                ", serverTime=" + serverTime +
                '}';
    }
}
