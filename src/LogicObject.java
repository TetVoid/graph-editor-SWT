public class LogicObject implements java.io.Serializable
{
    protected Boolean selected=false;
    protected Boolean choose=false;
    protected String nodeName="";
    private Node workNode=null;
    private Arc workArc=null;

    private int[]color=new int[3];

    void setColor(int red,int green,int blue)
    {
        this.color[0]=red;
        this.color[1]=green;
        this.color[2]=blue;
    }
    int[]getColor()
    {
        return color;
    }
    void setNode(Node workNode)
    {
        this.workNode=workNode;
    }

    Node getNode()
    {
        return workNode;
    }

    void setArc(Arc workArc)
    {
        this.workArc=workArc;
    }

    Arc getArc()
    {
        return workArc;
    }

    void setSelected(Boolean selected)
    {
        this.selected=selected;
    }

    Boolean isSelected()
    {
        return selected;
    }

    void setChoose(Boolean choose)
    {
        this.choose=choose;
    }

    Boolean isChoose()
    {
        return choose;
    }

    void setName(String name)
    {
        this.nodeName=name;
    }

    String getName()
    {
        return this.nodeName;
    }
}

