import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

public class GUI extends JFrame {

    ArrayList<Machine> machines = new ArrayList<>();
    ArrayList<Queue> queues = new ArrayList<>();
    mxGraph graph;
    Object parent;
    Queue start;
    public GUI() {
        super("Hello, World!");
        initComponents();

        graph = new mxGraph();
        parent = graph.getDefaultParent();
        graph.setCellsSelectable(false);

        graph.getModel().beginUpdate();

        try
        {
            start = new Queue(String.valueOf(queues.size()),graph,parent,500,50);
            queues.add(start);
            Queue after = new Queue(String.valueOf(queues.size()),graph,parent,200,50);
            queues.add(after);

            machines.add( new Machine(String.valueOf(machines.size()),graph,parent,400,50,start,after));
            machines.add(new Machine(String.valueOf(machines.size()),graph,parent,150,50,after,new Queue(String.valueOf(queues.size()),graph,parent,10,50)));

            Object[] cells = graph.getChildVertices(graph.getDefaultParent());
            for (Object c : cells)
            {
                mxCell cell = (mxCell) c;
                System.out.println("id: " + cell.getId() + ", value: " + cell.getValue() );
            }

        }
        finally
        {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
//        ((mxGraphModel)(graph.getModel())).getC
        contentPanel.add(graphComponent);
    }

    private void initComponents() {
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        addMachineBtn = new JButton();
        queuesCombo = new JComboBox<>();
        addQueueBtn = new JButton();
        machinesQueue = new JComboBox<>();
        startBtn = new JButton();
        replayBtn = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
//                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 85, 0};
//                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0};

                //---- addMachineBtn ----
                addMachineBtn.setText("Add Machine");
                buttonBar.add(addMachineBtn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- queuesCombo ----
                queuesCombo.setModel(new DefaultComboBoxModel<>(new String[] {
                    "insert after queue"
                }));
                buttonBar.add(queuesCombo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- addQueueBtn ----
                addQueueBtn.setText("Add Queue");
                buttonBar.add(addQueueBtn, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- machinesQueue ----
                machinesQueue.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Insert after machine"
                }));
                buttonBar.add(machinesQueue, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- startBtn ----
                startBtn.setText("Start");
                startBtn.addActionListener(e -> {

                    try {
                        Simulation simulation = new Simulation(start);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    startBtn.setBackground(Color.PINK);

                });
                buttonBar.add(startBtn, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- replayBtn ----
                replayBtn.setText("Replay");
                buttonBar.add(replayBtn, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
//        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents


    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel buttonBar;
    private JButton addMachineBtn;
    private JComboBox<String> queuesCombo;
    private JButton addQueueBtn;
    private JComboBox<String> machinesQueue;
    private JButton startBtn;
    private JButton replayBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void addMachine(){

    }
    public static void main(String[] args) throws InterruptedException {
        GUI frame = new GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setVisible(true);
        Simulation simulation = new Simulation(frame.start);
    }

}
