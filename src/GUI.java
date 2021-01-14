import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    LinkedList<Queue> queuesBefore = new LinkedList<>();
    final JButton fromQueue = new JButton("After");
    final JPopupMenu menu = new JPopupMenu();
    public GUI() {
        super("Producer Consumer");
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
            start = new Queue(id,graph,parent,1300,50);
            start.setEdge(false);
//            fromQueueCombo.addItem("Q"+id);
            addMenuItem(id);
            toQueueCombo.addItem("Q"+id);
            queues.add(start);

            id = String.valueOf(queues.size());
            Queue after = new Queue(id,graph,parent,900,50);
            queues.add(after);
//            fromQueueCombo.addItem("Q"+id);
            addMenuItem(id);

            LinkedList<Queue> queuesBefore = new LinkedList<>();
            queuesBefore.add(start);
            toQueueCombo.addItem("Q"+id);
            machines.add( new Machine(String.valueOf(machines.size()),graph,parent,1100,50,queuesBefore,after));

        }
        finally
        {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);
        dialogPane.add(graphComponent);
    }

    private void initComponents() {
        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JPanel buttonBar = new JPanel();
        JButton addMachineBtn = new JButton();
        JButton addQueueBtn = new JButton();
        toQueueCombo = new JComboBox<>();
//        fromQueueCombo = new JComboBox<>();


        JButton startBtn = new JButton();
        JButton replayBtn = new JButton();
        SpinnerNumberModel model = new SpinnerNumberModel(5.0, 1.0, 100.0, 1.0);
        JLabel productsLabel = new JLabel();
        JSpinner spin = new JSpinner(model);
        JLabel before = new JLabel();
        JLabel after = new JLabel();
        JLabel addMachineLabel = new JLabel();
        JLabel addQueueLabel = new JLabel();
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

                //---- addMachineLabel ----
                addMachineLabel.setText("Add Machine:");
                buttonBar.add(addMachineLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                //---- addMachineBtn ----
                addMachineBtn.setText("Add !");
                addMachineBtn.addActionListener(e -> addMachine());
                buttonBar.add(addMachineBtn, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- beforeLabel ----
                before.setText("Before queue:");
                buttonBar.add(before, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- afterLabel ----
                after.setText("After queue:");
                buttonBar.add(after, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                //---- fromQueueCombo ----
//                toQueueCombo.setModel(new DefaultComboBoxModel<>(new String[] {
//                    "before queue"
//                }));
                buttonBar.add(toQueueCombo, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- toQueueCombo ----
//                fromQueueCombo.setModel(new DefaultComboBoxModel<>(new String[] {
//                        "after queue"
//                }));

                buttonBar.add(fromQueue, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                fromQueue.addActionListener(e -> {
                    if (!menu.isVisible()) {
                        Point p = fromQueue.getLocationOnScreen();
                        menu.setInvoker(fromQueue);
                        menu.setLocation((int) p.getX(),
                                (int) p.getY() + fromQueue.getHeight());
                        menu.setVisible(true);
                    } else {
                        menu.setVisible(false);
                    }

                });
                //---- addQueueBtn ----
                addQueueBtn.setText("Add Queue");
                addQueueBtn.addActionListener(e -> addQueue());
                buttonBar.add(addQueueBtn, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- startBtn ----
//                startBtn.setText("▶");
                startBtn.setText("Start");

                startBtn.addActionListener(e -> {
                    try {
                        spin.commitEdit();
                        double numberOfProducts =  (Double) spin.getValue();
                        simulation = new Simulation(start,numberOfProducts);
                        resetGraph();
                        simulation.play(false);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                });
                buttonBar.add(startBtn, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                JLabel space = new JLabel();
                space.setText("");
                space.add(productsLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- spinLabel ----
                productsLabel.setText("Number of products :");
                buttonBar.add(productsLabel, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));




                //---- productsSpinner ----

                buttonBar.add(spin, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));


                //---- replayBtn ----
                replayBtn.setText("Replay");
                buttonBar.add(replayBtn, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
                }
                dialogPane.add(buttonBar, BorderLayout.SOUTH);
                replayBtn.addActionListener(e -> {
                    try {
                        resetGraph();
                        simulation.play(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
//        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents


    }

    private JPanel dialogPane;
//    private JComboBox<String> fromQueueCombo;
    private JComboBox<String> toQueueCombo;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public void addMachine(){

//        String before = Objects.requireNonNull(fromQueueCombo.getSelectedItem()).toString();
        String to = Objects.requireNonNull(toQueueCombo.getSelectedItem()).toString();
        Queue toQueue = queues.get(Integer.parseInt(to.substring(1)));
        LinkedList<Queue> machineQueues = new LinkedList<>();
        while (!queuesBefore.isEmpty()){
            machineQueues.add(queuesBefore.pop());
        }
        if(Machine.checkValidity(queues,machineQueues,toQueue))
        machines.add(new Machine(String.valueOf(machines.size()), graph, parent, 400, 200, machineQueues,
                toQueue ));
        else {
            System.out.println("ANA MAMA");
        }

    }
    public void addQueue(){
        String id = String.valueOf(queues.size());
        queues.add(new Queue(id,graph,parent,500,100));
        addMenuItem(id);
//        fromQueueCombo.addItem("Q"+id);
        toQueueCombo.addItem("Q"+id);
        Object[] cells = graph.getChildVertices(graph.getDefaultParent());
        for (Object c : cells)
        {
            mxCell cell = (mxCell) c;
            System.out.println("id: " + cell.getId() + ", value: " + cell.getValue() );
        }

    }
    public void resetGraph(){
        for (Queue queue : queues) {
            mxCell cell = (mxCell) ((mxGraphModel) (graph.getModel())).getCell("Q"+queue.id);
            queue.resetProducts();
            cell.setValue("Q"+queue.id + "\n" + 0 + " Products");
        }

        graph.refresh();
    }
    private void addMenuItem(String id){
        JMenuItem item = new JCheckBoxMenuItem("Q"+id);
        menu.add(item);
        item.addActionListener(new OpenAction(menu, fromQueue));
        item.addActionListener(e->{
            if(item.isSelected())
                queuesBefore.add(queues.get(Integer.parseInt(item.getText().substring(1))));
            else
                queuesBefore.remove(queues.get(Integer.parseInt(item.getText().substring(1))));
        });
    }
    private static class OpenAction implements ActionListener {

        private JPopupMenu menu;
        private JButton button;

        private OpenAction(JPopupMenu menu, JButton button) {
            this.menu = menu;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            menu.show(button, 0, button.getHeight());
        }
    }

    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 600);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}
