import java.util.ArrayList;

public class    Scheduler {
    private Server server;
    private String s="";
    private ArrayList<String >output=new ArrayList<String>();

    public Scheduler(int i )
    {
        server=new Server(i);
        Thread thread=new Thread(server);
        thread.start();
    }
    public void addTask(Client c)
    {
        server.addClient(c);
    }

    public void stopThread()
    {
        server.stopThread();
    }

    public Server getServer()
    {
        return server;
    }

    public void setServer(Server s)
    {
        this.server=s;
    }
    public String  toString1()
    {   s="";
        output.clear();
        output=server.toString1();

        for(String k:output)
        {
            s+=k+"\n";
        }

        return s ;

    }

}