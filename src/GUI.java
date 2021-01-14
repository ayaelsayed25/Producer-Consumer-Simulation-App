import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.*;
import java.text.ParseException;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class GUI extends JFrame {

    ArrayList<Machine> machines = new ArrayList<>();
    ArrayList<Queue> queues = new ArrayList<>();
    mxGraph graph;
    Object parent;
    Queue start;
    Simulation simulation;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    public GUI() {
        super("Hello, World!");
        initComponents();

        graph = new mxGraph(){
            @Override
            public boolean isCellSelectable(Object cell)
            {
                if (model.isEdge(cell))
                {
                    return false;
                }

                return super.isCellSelectable(cell);
            }
        };
        parent = graph.getDefaultParent();
        graph.setAllowDanglingEdges(false);
        graph.getModel().beginUpdate();

        try
        {
            String id = String.valueOf(queues.size());
            start = new Queue(id,graph,parent,800,50);
            fromQueueCombo.addItem("Q"+id);
            toQueueCombo.addItem("Q"+id);
            queues.add(start);

            id = String.valueOf(queues.size());
            Queue after = new Queue(id,graph,parent,200,50);
            queues.add(after);
            fromQueueCombo.addItem("Q"+id);
            toQueueCombo.addItem("Q"+id);
            machines.add( new Machine(String.valueOf(machines.size()),graph,parent,400,50,start,after));
//            machines.add(new Machine(String.valueOf(machines.size()),graph,parent,150,50,after,new Queue(String.valueOf(queues.size())
//                    ,graph,parent,10,50)));
//            addMachine();

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
        addQueueBtn = new JButton();
        toQueueCombo = new JComboBox<>();
        fromQueueCombo = new JComboBox<>();


        startBtn = new JButton();
        replayBtn = new JButton();
        SpinnerNumberModel model1 = new SpinnerNumberModel(5.0, 1.0, 100.0, 1.0);
        l = new JLabel();

        // add text to label
        l.setText("label text");
        JSpinner spin = new JSpinner(model1);

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
                addMachineBtn.addActionListener(e -> addMachine());
                buttonBar.add(addMachineBtn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- fromQueueCombo ----
                toQueueCombo.setModel(new DefaultComboBoxModel<>(new String[] {
                    "before queue"
                }));
                buttonBar.add(toQueueCombo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- toQueueCombo ----
                fromQueueCombo.setModel(new DefaultComboBoxModel<>(new String[] {
                        "after queue"
                }));
                buttonBar.add(fromQueueCombo, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- addQueueBtn ----
                addQueueBtn.setText("Add Queue");
                addQueueBtn.addActionListener(e -> addQueue());
                buttonBar.add(addQueueBtn, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- startBtn ----
                startBtn.setText("Start");
                startBtn.addActionListener(e -> {
                    try {
                        spin.commitEdit();
                        double numberOfProducts =  (Double) spin.getValue();
                        simulation = new Simulation(start,numberOfProducts);
                        for (Queue queue : queues) {
                            mxCell cell = (mxCell) ((mxGraphModel) (graph.getModel())).getCell(queue.id);
                            queue.resetProducts();
                            cell.setValue(queue.id + "\n" + 0 + " Products");
                        }

                        Object[] cells = graph.getChildVertices(graph.getDefaultParent());
                        for (Object c : cells)
                        {
                            mxCell cell = (mxCell) c;
                            System.out.println("id: " + cell.getId() + ", value: " + cell.getValue() );
                        }
                        graph.refresh();
                        simulation.play(false);
                    } catch (InterruptedException | ParseException ex) {
                        ex.printStackTrace();
                    }
                    startBtn.setBackground(Color.PINK);

                });
                buttonBar.add(startBtn, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
                //---- spinLabel ----
                l.setText("Number of products :");
                buttonBar.add(l, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));


                //---- productsSpinner ----

                buttonBar.add(spin, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));



                //---- replayBtn ----
                replayBtn.setText("Replay");
                buttonBar.add(replayBtn, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
                }
                dialogPane.add(buttonBar, BorderLayout.SOUTH);
                replayBtn.addActionListener(e -> {
                    try {
                        simulation.play(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    startBtn.setBackground(Color.PINK);

                });
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
    private JComboBox<String> fromQueueCombo;
    private JComboBox<String> toQueueCombo;
    private JButton addQueueBtn;
    private JButton startBtn;
    private JButton replayBtn;
    private JLabel l;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void addMachine(){

        String before = Objects.requireNonNull(fromQueueCombo.getSelectedItem()).toString();
        String to = Objects.requireNonNull(toQueueCombo.getSelectedItem()).toString();
        machines.add( new Machine(String.valueOf(machines.size()),graph,parent,400,200,queues.get(Integer.parseInt(before.substring(1))),
                queues.get(Integer.parseInt(to.substring(1)))));

        String id = String.valueOf(machines.size()-1);
    }
    public void addQueue(){
//        String before = Objects.requireNonNull(fromMachineCombo.getSelectedItem()).toString();
//        String to = Objects.requireNonNull(toMachineCombo.getSelectedItem()).toString();
        String id = String.valueOf(queues.size());
        queues.add(new Queue(id,graph,parent,500,100));

        fromQueueCombo.addItem("Q"+id);
        toQueueCombo.addItem("Q"+id);

    }
    public static void main(String[] args) throws InterruptedException {
        GUI frame = new GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

}
