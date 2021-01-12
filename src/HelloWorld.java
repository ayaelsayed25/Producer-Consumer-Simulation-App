import javax.swing.JFrame;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class HelloWorld extends JFrame
{
    private static final long serialVersionUID = -2707712944901661771L;

    public HelloWorld()
    {
        super("Hello, World!");

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.setCellsSelectable(false);

        graph.getModel().beginUpdate();

        try
        {
            mxCell v1 = (mxCell) graph.insertVertex(parent, null, "source", 20, 20,80, 30,"strokeColor=#66FF00;fillColor=#FFFFFF;shape=ellipse");
            v1.setId("5");
            v1.setEdge(false);
            v1.setConnectable(false);

            v1.setStyle("fillColor=#FFFFFF");
            v1.setAttribute("strokeColor","#66FF00");
            Object v2 = graph.insertVertex(parent, null, "destination", 200, 20,80, 30,"image");
//            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
//                    30);
//            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
//                    80, 30);
            Object v3 = graph.insertVertex(parent, null, "World!", 180, 100,
                80, 30);
            graph.insertEdge(parent, null, "", v1,v2,"startArrow=none;endArrow=diamond;strokeWidth=4;strokeColor=#66FF00");

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
        getContentPane().add(graphComponent);
    }

    public static void main(String[] args)
    {
        HelloWorld frame = new HelloWorld();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }

}