import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {

    private ArrayList<Client> clients;
    private int waitPeriod;
    int server_nr;
    private int flag=1;
    private ArrayList<String >output =new ArrayList<String>();

    public void stopThread()
    {

        this.flag=0;
    }
    public Server( int server_nr)
    {
        clients=new ArrayList<Client>();
        waitPeriod=0;
        this.server_nr=server_nr;
    }
    public int  getwaitPeriod()
    {
        return waitPeriod;
    }

    public void  setwaitPeriod(int waitPeriod)
    {
        this.waitPeriod=waitPeriod;
    }

    public void addClient(Client c)
    {
        clients.add(c);
        if(clients.size()>0)
        {
            c.setArrivalTime(this.waitPeriod);
            this.waitPeriod+=c.getServerTime();
        }else
        {
            this.waitPeriod=c.getServerTime();

        }
    }
    public void run()
    {
        while(flag==1)
        {
            for(int i=0;i<clients.size();i++)
            {
                if(i==0)
                {
                    clients.get(0).setServerTime(clients.get(0).getServerTime()-1);
                }else
                {
                    clients.get(i).setArrivalTime(clients.get(i).getArrivalTime()-1);
                }
            }
            if(clients.size()>0)
            {
                if(clients.get(0).getServerTime()==0)
                {
                    clients.remove(0);
                    System.out.println("the first client left he queue number"+(server_nr)+"\n");

                }
                this.waitPeriod--;
            }

            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e)
            {
                e.printStackTrace();
                System.out.println("am intrat in exceptie");
            }
        }
    }

    public ArrayList<String> toString1()
    {  int i=0;

        output.clear();

        if(clients.size()>0)
        {   output.add("Queue number"+server_nr);

            for( i=0;i<clients.size();i++)
            {
                if(i==0)
                {

                    output.add("the first client has "+ clients.get(0).getServerTime()+"seconds of serving time ");
                }else
                {
                    output.add("the client number "+(i+1)+"has the waiting time "+clients.get(i).getArrivalTime()+"");
                }

            }

            output.add("the total waiting time for the queue is "+waitPeriod+"\n");

        }

        return output;
    }




}