import java.util.ArrayList;
import java.util.List;

public class Node extends LogicObject{

    private int xCords;
    private int yCords;


    List<Arc> outputArc=new ArrayList<>(0);
    List<Arc> inputArc=new ArrayList<>(0);

    Node(int x, int y)
    {
        this.xCords=x;
        this.yCords=y;
    }

    void setXCords(int x)
    {
        this.xCords=x;
    }

    void setYCords(int y)
    {
        this.yCords=y;
    }

    boolean isEqual(Node node)
    {
        if(this.getXCords()==node.getXCords()&&this.getYCords()==node.getYCords())
            return true;
        else
            return false;
    }


    int getXCords()
    {
        return this.xCords;
    }

    int getYCords()
    {
        return this.yCords;
    }

    List<Arc> getOutputArc()
    {
        return outputArc;
    }

    List<Arc> getInputArc()
    {
        return inputArc;
    }
    void addInputArc(Arc temp)
    {
        inputArc.add(temp);
    }

    void addOutputArc(Arc temp)
    {
        outputArc.add(temp);
    }


}
