import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimulatorFrame extends JFrame {

    private JFrame frame;
    private int nr_servers,timeLimit,maxProcessTime,minProcessTime,minService,maxService;
    private JTextField simulationTime,nr_queues,minServ,maxServ,minProc,maxProc;
    private JLabel number_of_servers,simulation_time,min_serv,max_serv,min_proc,max_proc,currentTime,peakhour,average,clientTime;
    private JButton startSimulation;
    private ArrayList<JTextArea> servers;
    private ArrayList<String> servers_to_print;
    //private SimulatorFrame simulatorFrame;
    private JPanel panelrealtime,panelServers,panelData,mainPanel;
    private Simulator simulator;

    public void initializeFrame()
    {

        // System.out.println("NICI NU INTRA AICI ?");
        nr_servers=Integer.parseInt(nr_queues.getText());
        timeLimit=Integer.parseInt(simulationTime.getText());
        this.maxProcessTime=Integer.parseInt(maxProc.getText());
        this.minProcessTime=Integer.parseInt(minProc.getText());
        this.minService=Integer.parseInt(minServ.getText());
        this.maxService=Integer.parseInt(maxServ.getText());

     /* this.nr_servers=4;
        this.timeLimit=40;
        this.maxProcessTime=3;
        this.minProcessTime=1;
        this.minService=7;
        this.maxService=15;*/

    }

    public void updateFrame(ArrayList<String> queues,int peakHour,double averageWait,int currentTime,int clientTime)
    {   servers_to_print=queues;
        for(int i=0;i<nr_servers;i++)
        {   if(servers_to_print.get(i).equals(""))
        {
            servers.get(i).setText("queue number "+i+"       ");
        }else {
            servers.get(i).setText(servers_to_print.get(i));
        }

        }
        this.peakhour.setText("the peak hour is "+peakHour);
        this.average.setText("The average wait time is "+averageWait);
        this.currentTime.setText("The current time is "+currentTime);
        this.clientTime.setText("The next client is coming in "+clientTime+"seconds");

    }

    public SimulatorFrame()
    {
        frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,500);
        frame.setLayout(new BorderLayout());
        simulationTime=new JTextField();
        nr_queues=new JTextField(5);
        minServ=new JTextField();
        maxServ=new JTextField();
        minProc=new JTextField();
        maxProc=new JTextField();
        number_of_servers=new JLabel("Number of queues");
        simulation_time=new JLabel("Simulation time");
        min_serv=new JLabel("Minimum service time ");
        max_serv=new JLabel("Maximum service time ");
        min_proc=new JLabel("Minimum time for arriving customers");
        max_proc=new JLabel("Maximum time for arriving customers");
        number_of_servers=new JLabel("Number of queues");
        // number_of_servers=new JLabel("Number of queues");
        startSimulation=new JButton("Start Simulation ");
        servers=new ArrayList<JTextArea>();
        servers_to_print=new ArrayList<String>();

        currentTime=new JLabel("The current time is 0");
        peakhour=new JLabel("the peak hour is 0");
        average=new JLabel("the average time is 0");
        clientTime=new JLabel("the client time is 0");
        SimulatorFrame simulatorFrame=this;
        panelData=new JPanel();
        panelServers =new JPanel();
        panelrealtime=new JPanel();
        mainPanel=new JPanel();

        /* start with some default values*/



        panelrealtime.setLayout(new BoxLayout(panelrealtime,BoxLayout.Y_AXIS));
        panelrealtime.add(currentTime);
        panelrealtime.add(peakhour);
        panelrealtime.add(average);
        panelrealtime.add(clientTime);
        mainPanel.add(panelrealtime,BorderLayout.WEST);

        panelData.setLayout(new BoxLayout(panelData,BoxLayout.Y_AXIS));
        minProc.setPreferredSize(new Dimension(30,30));
        panelData.add(min_proc);
        panelData.add(minProc);
        maxProc.setPreferredSize(new Dimension(30,30));
        panelData.add(max_proc);
        panelData.add(maxProc);
        minServ.setPreferredSize(new Dimension(30,30));
        panelData.add(min_serv);
        panelData.add(minServ);
        maxServ.setPreferredSize(new Dimension(30,30));
        panelData.add(max_serv);
        panelData.add(maxServ);
        nr_queues.setPreferredSize(new Dimension(30,30));
        panelData.add(number_of_servers);
        panelData.add(nr_queues);
        simulationTime.setPreferredSize(new Dimension(30,30));
        panelData.add(simulation_time);
        panelData.add(simulationTime);
        panelData.add(startSimulation);

        mainPanel.add(panelData,BorderLayout.LINE_START);
        nr_queues.setText("3");
        simulationTime.setText("50");
        minServ.setText("9");
        maxServ.setText("15");
        minProc.setText("3");
        maxProc.setText("5");

        panelServers.setLayout(new BoxLayout(panelServers,BoxLayout.X_AXIS));



        startSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                initializeFrame();

                // frame.setVisible(false);
                panelData.setVisible(false);
                panelData.revalidate();


                for(int i=0;i<nr_servers;i++)
                {
                    JTextArea text=new JTextArea("server number"+i);
                    servers.add(text);
                    text.setMaximumSize(new Dimension(1000,800));
                    panelServers.add(text);

                    panelServers.revalidate();
                }
                initializeFrame();
                //mainPanel.revalidate();

                simulator=new Simulator(timeLimit,maxProcessTime,minProcessTime,nr_servers,minService,maxService,simulatorFrame);
                Thread t=new Thread(simulator);
                t.start();


            }
        });


        mainPanel.add(panelServers,BorderLayout.WEST);
        mainPanel.setPreferredSize(new Dimension(1000,500));
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);



    }

    public static void main(String[] args)
    {

        new SimulatorFrame();


    }


}