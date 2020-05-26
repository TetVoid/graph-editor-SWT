
public class Arc extends LogicObject{
    int mode=0;

    Node outputNode;
    Node inputNode;

    Arc(Node outputNode, Node inputNode)
    {
        this.inputNode=inputNode;
        this.outputNode=outputNode;
    }

    public Node getOutputNode() {
        return outputNode;
    }

    public Node getInputNode() {
        return inputNode;
    }
}
