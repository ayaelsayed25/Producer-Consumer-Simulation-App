import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

public class GUI extends JFrame {
    public GUI() {
        super("Hello, World!");
        initComponents();
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
//        graph.setCellsSelectable(false);

        graph.getModel().beginUpdate();

        try
        {
            mxCell v1 = (mxCell) graph.insertVertex(parent, null, "source", 20, 20,80, 30,"strokeColor=#66FF00;fillColor=#FFFFFF;shape=ellipse");
            v1.setId("5");
            v1.setEdge(false);
            v1.setConnectable(false);

//            v1.setStyle("fillColor=#FFFFFF");
            v1.setAttribute("strokeColor","#66FF00");
            Object v2 = graph.insertVertex(parent, null, "destination", 200, 20,80, 30,"image");
//            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
//                    30);
//            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
//                    80, 30);
            Object v3 = graph.insertVertex(parent, null, "World!", 180, 100,
                    80, 30);
            Object v4 = graph.insertVertex(parent, null, "World!", 280, 100,
                    80, 30);
            Object v5 = graph.insertVertex(parent, null, "World!", 380, 500,
                    80, 30);
            graph.insertEdge(parent, null, "", v1,v2,"startArrow=none;strokeWidth=2;strokeColor=#66FF00");

            Object[] cells = graph.getChildVertices(graph.getDefaultParent());
            for (Object c : cells)
            {
                mxCell cell = (mxCell) c;
                System.out.println("id: " + cell.getId() + ", value: " + cell.getValue() );
            }

//            graph.insertEdge(parent, null, "Edge", v1, v2);
//            graph.insertEdge(parent, null, "Edge", v1, v3);

        }
        finally
        {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
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
        setLocationRelativeTo(getOwner());
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
    public static void main(String[] args)
    {
        GUI frame = new GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }
}
