import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import java.util.LinkedList;

public class Queue implements Observer {

    private LinkedList<Machine> machines;
    private LinkedList<Product> products;
    mxCell vertex;
    String id;

    public Queue( String id,mxGraph graph,Object parent,int x,int y) {
        machines = new LinkedList<>();
        products = new LinkedList<>();
        setId(id);
       drawQueue(graph,parent,x,y);
    }

    public void drawQueue(mxGraph graph,Object parent,int x,int y){
        vertex = (mxCell) graph.insertVertex(parent, null, "Q"+id, x, y,30, 30,"strokeColor=#66FF00;fillColor=#FFFFFF;shape=rectangle");
        vertex.setId("5");
        vertex.setEdge(false);
        vertex.setConnectable(false);

        vertex.setStyle("fillColor=#FFFFFF");
        vertex.setAttribute("strokeColor","#66FF00");

    }
    public void addMachine(Machine machine){
      machines.add(machine);
    }
    public LinkedList<Machine> getMachines() {
        return machines;
    }
    public void setMachines(LinkedList<Machine> machines) {
        this.machines = machines;
    }
    public void addProduct(Product product){
        products.add(product);
    }

    public LinkedList<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Product> products) {
        this.products = products;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public void sendProduct() throws InterruptedException {
        for (Machine machine : machines) {
            if (machine.empty) {
                if (products.isEmpty())return;
                Product product = products.removeLast();
                machine.setEmpty(false);
                machine.setCurrentProduct(product);
            }
        }
    }


    @Override
    public void update() throws InterruptedException {
        sendProduct();
    }
}
