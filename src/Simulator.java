import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulator implements Runnable    {

    private int timeLimit;
    private int maxProcessTime;
    private int minProcessTime;
    private int nr_servers;
    private Scheduler[] schedulers;
    //private String[] queues;
    private int minwait=10000;
    private int minservice;
    private int maxservice;
    private double averageWait=0;
    private int currentTime=0;
    private int peakHour=0;
    private double  maxWait=0;
    private Random r=new Random();
    private double total_wait=0;
    private int client_time=0;
    private int  service_time=0;
    private SimulatorFrame simulatorFrame=new SimulatorFrame();
    private ArrayList<String> queues=new ArrayList<String>();

    public  Simulator (int timeLimit,int maxProcessTime,int minProcessTime,int nr_servers,int minservice,int maxservice,SimulatorFrame simulatorFrame)
    {
        this.simulatorFrame=simulatorFrame;
        this.timeLimit=timeLimit;
        this.maxProcessTime=maxProcessTime;
        this.minProcessTime=minProcessTime;
        this.nr_servers=nr_servers;
        this.minservice=minservice;
        this.maxservice=maxservice;
        schedulers=new Scheduler[nr_servers];
        for(int i=0;i<nr_servers;i++)
        {
            schedulers[i]=new Scheduler(i);
        }


    }

    public void  average()
    {
        double average=0;
        for(int k=0;k<nr_servers;k++)
        {
            average+=schedulers[k].getServer().getwaitPeriod();

        }
        this.averageWait=(double)average/nr_servers;
    }

    public  void test ()
    {
        this.timeLimit=10;
        this.maxProcessTime=3;
        this.minProcessTime=2;
        this.nr_servers=2;
        schedulers=new Scheduler[2];
        schedulers[0]= new Scheduler(0);
        schedulers[1]=new Scheduler(1);
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                while(currentTime<timeLimit)
                {
                    System.out.println(" nu merge");
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    currentTime++;
                }

            }
        });
        t.start();
    }


    public  void run() {
        int aux=0;
        String s;
        while (currentTime < timeLimit) {
            s="";

            if (client_time == 0) {

                client_time = r.nextInt(maxProcessTime - minProcessTime + 1) + minProcessTime;
                service_time = r.nextInt(maxservice - minservice + 1 )+ minservice;
                Client c = new Client(service_time);

                schedulers[findMin()].addTask(c);




            } else {
                client_time--;
            }
            queues.clear();
            for(int k=0;k<nr_servers;k++)
            {
                s=schedulers[k].toString1();
                queues.add(s);


            }

            average();
            // we calculate the peak hour
            total_wait=0;
            for(Scheduler x:schedulers)
            {
                total_wait+=x.getServer().getwaitPeriod();
            }
            if(total_wait>maxWait)
            {
                maxWait=total_wait;
                peakHour=currentTime;
            }
            send_to_SimulatorFrame(queues,peakHour,(int)averageWait,currentTime);



            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentTime++;


        }


        for(int i=0;i<nr_servers;i++)
        {
            schedulers[i].stopThread();
        }


    }


    public  synchronized void send_to_SimulatorFrame(ArrayList<String> queues,int peakHour,int   averageWait,int currentTime)
    {
        simulatorFrame.updateFrame(queues,peakHour,averageWait,currentTime,this.client_time);
    }

    public int findMin()
    {   minwait=1000;

        for(Scheduler s:schedulers)
        {
            if (s.getServer().getwaitPeriod()<minwait)
            {
                minwait=s.getServer().getwaitPeriod();
            }

        }
        for(int i=0;i<nr_servers;i++)

        {
            if(schedulers[i].getServer().getwaitPeriod()==minwait)
            {

                return i;

            }
        }
        return 0;
    }

}