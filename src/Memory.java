import java.util.ArrayList;
import java.util.List;

public class Memory implements java.io.Serializable {

    List<Node> listOfNodes=new ArrayList<>(0);
    List<Arc> listOfArcs=new ArrayList<>(0);
    String grafName=null;

    Memory getEquivalent()
    {
        Memory equivalent=new Memory();
        for(Node node : listOfNodes)
        {
            equivalent.getListOfNodes().add(node);
        }

        for(Arc arc : listOfArcs)
        {
            equivalent.getArcs().add(arc);
        }
        return equivalent;
    }

    List <Node> getAdjacentNodesOf(Node node)
    {
        List<Node> adjacentNodes=new ArrayList<>(0);
        for(int i=0;i<node.getOutputArc().size();i++)
        {
            adjacentNodes.add(node.getOutputArc().get(i).outputNode);
        }

        for(int i=0;i<node.getInputArc().size();i++)
        {
            adjacentNodes.add(node.getInputArc().get(i).outputNode);
        }
        return adjacentNodes;
    }

    void setGrafName(String grafName)
    {
        this.grafName=grafName;
    }
    String getGrafName()
    {
            return grafName;
    }

    Arc getArc(int x, int y)
    {
        for(int i=0;i<listOfArcs.size();i++)
        {
            int x1=listOfArcs.get(i).getOutputNode().getXCords();
            int y1=listOfArcs.get(i).getOutputNode().getYCords();
            int x2=listOfArcs.get(i).getInputNode().getXCords();
            int y2=listOfArcs.get(i).getInputNode().getYCords();


            int checkY=(x-x1)*(y2-y1)/(x2-x1)+y1;
            if(checkY-5<y&&checkY+5>y)
                return listOfArcs.get(i);
        }
        return null;
    }

    Arc getArcByNodes(Node output, Node input)
    {
        for(Arc arc:listOfArcs)
        {
            if(output.isEqual(arc.outputNode)&&input.isEqual(arc.inputNode))
                return arc;

            if(output.isEqual(arc.inputNode) && input.isEqual(arc.outputNode ) && arc.mode==1)
                return arc;
        }
        return null;
    }

    boolean checkArc(int x,int y)
    {
        for(int i=0;i<listOfArcs.size();i++)
        {
            int x1=listOfArcs.get(i).getOutputNode().getXCords();
            int y1=listOfArcs.get(i).getOutputNode().getYCords();
            int x2=listOfArcs.get(i).getInputNode().getXCords();
            int y2=listOfArcs.get(i).getInputNode().getYCords();

            int checkY=0;
            if(x2-x1!=0)
                checkY=(x-x1)*(y2-y1)/(x2-x1)+y1;

            boolean xFlag=false;
            if(x1>x2) {
                if (x < x1+5 && x > x2-5)
                    xFlag = true;
            }
            else
            {
                if (x < x2+5 && x > x1-5)
                    xFlag = true;
            }

            if(x1==x2)
            {
                if (x < x2+5 && x > x1-5)
                    xFlag = true;
            }

            if(checkY-5<y&&checkY+5>y&&xFlag)
            {
                return true;
            }
        }
        return false;
    }

    void addArc(Node outputNode, Node inputNode)
    {
        Arc temp=new Arc(outputNode,inputNode);
        listOfArcs.add(temp);
        temp.setColor(0,0,0);
        inputNode.addInputArc(temp);
        outputNode.addOutputArc(temp);
    }

   List<Arc> getArcs()
    {
        return listOfArcs;
    }

     Node findNodeByCords(int x, int y)
    {
        Node tempNode=new Node(-1,-1);
        for(int i=0;i<listOfNodes.size();i++)
        {
            if(listOfNodes.get(i).getXCords()<x+10&&listOfNodes.get(i).getXCords()>x-10&&listOfNodes.get(i).getYCords()<y+10&&listOfNodes.get(i).getYCords()>y-10)
                tempNode=listOfNodes.get(i);
        }
        if(tempNode.getXCords()!=-1)
            return tempNode;
        else
            return null;
    }



    Boolean cheakNode(int x,int y)
    {
        for(int i=0;i<listOfNodes.size();i++)
        {
            if(listOfNodes.get(i).getXCords()<x+10&&listOfNodes.get(i).getXCords()>x-10&&listOfNodes.get(i).getYCords()<y+10&&listOfNodes.get(i).getYCords()>y-10)
                return true;
        }
        return false;
    }

    void InitNewNode(int x,int y)
    {
        Node node=new Node(x,y);
        node.setColor(255,255,255);
        listOfNodes.add(node);
    }

     List<Node> getListOfNodes()
     {
         return this.listOfNodes;
     }

     List<ArrayList<Integer>> getMatrix()
     {
         List<ArrayList<Integer>> matrix= new ArrayList(0);
         for(int i=0; i<getListOfNodes().size();i++)
         {
             ArrayList<Integer> elementOfMatrix=new ArrayList<>(0);
             for(int j=0;j<getArcs().size();j++)
             {
                 boolean check=false;
                 for(int k=0;k<getListOfNodes().get(i).getOutputArc().size();k++)
                    if (getListOfNodes().get(i).getOutputArc().get(k)==getArcs().get(j))
                        check=true;

                 for(int k=0;k<getListOfNodes().get(i).getInputArc().size();k++)
                     if (getListOfNodes().get(i).getInputArc().get(k)==getArcs().get(j)&&getListOfNodes().get(i).getInputArc().get(k).mode==1)
                         check=true;

                 if(check)
                     elementOfMatrix.add(1);
                 else
                     elementOfMatrix.add(0);
             }

             matrix.add(elementOfMatrix) ;
         }
         return matrix;
     }

     void lineMultiplication(Memory memoryGraf1 ,Memory memoryGraf2)
     {
         int xMaxCord,xMinCord,deltaX,yMaxCord,yMinCord,deltaY;
         xMaxCord=memoryGraf1.getListOfNodes().get(0).getXCords();
         xMinCord=memoryGraf1.getListOfNodes().get(0).getXCords();

         yMaxCord=memoryGraf1.getListOfNodes().get(0).getYCords();
         yMinCord=memoryGraf1.getListOfNodes().get(0).getYCords();
         for(Node node:memoryGraf1.getListOfNodes())
         {
             if(xMaxCord<node.getXCords())
                 xMaxCord=node.getXCords();

             if(xMinCord>node.getXCords())
                 xMinCord=node.getXCords();

             if(yMaxCord<node.getYCords())
                 yMaxCord=node.getYCords();

             if(yMinCord>node.getYCords())
                 yMinCord=node.getYCords();
         }

         deltaX=xMaxCord-xMinCord+20;
         deltaY=yMaxCord-yMinCord+20;

         for(int i=0;i<memoryGraf2.getListOfNodes().size();i++)
         {
             for(Node node:memoryGraf1.getListOfNodes())
             {
                 this.InitNewNode(node.getXCords()+deltaX*i,node.getYCords()+deltaY*i);
             }
         }

         for(int i=0;i<memoryGraf2.getListOfNodes().size();i++)
         {
             for(int j=0;j<memoryGraf1.getArcs().size();j++)
             {
                 Arc copy=memoryGraf1.getArcs().get(j);
                 int inputIndex=memoryGraf1.getListOfNodes().indexOf(copy.getInputNode());
                 int outputIndex=memoryGraf1.getListOfNodes().indexOf(copy.getOutputNode());

                 Node input,output;

                 input = this.getListOfNodes().get(inputIndex + (memoryGraf1.getListOfNodes().size()) * i);
                 output = this.getListOfNodes().get(outputIndex + (memoryGraf1.getListOfNodes().size()) * i);

                 this.addArc(output,input);
                 if(copy.mode==1)
                     this.getArcs().get(this.getArcs().size()-1).mode=1;
             }
         }


         for(int i=0;i<memoryGraf2.getArcs().size();i++)
         {
             Arc copy=memoryGraf2.getArcs().get(i);
             int inputIndex=memoryGraf2.getListOfNodes().indexOf(copy.getInputNode());
             int outputIndex=memoryGraf2.getListOfNodes().indexOf(copy.getOutputNode());
             for(int j=0;j<memoryGraf1.getListOfNodes().size();j++)
             {
                 Node input,output;

                 input = this.getListOfNodes().get(j+(memoryGraf1.getListOfNodes().size()) * inputIndex);
                 output = this.getListOfNodes().get(j+(memoryGraf1.getListOfNodes().size()) * outputIndex);

                 this.addArc(output,input);
                 if(copy.mode==1)
                     this.getArcs().get(this.getArcs().size()-1).mode=1;
             }
         }
     }
    void vectorMultiplication(Memory memoryGraf1 ,Memory memoryGraf2)
    {
        int xMaxCord,xMinCord,deltaX,yMaxCord,yMinCord,deltaY;
        xMaxCord=memoryGraf1.getListOfNodes().get(0).getXCords();
        xMinCord=memoryGraf1.getListOfNodes().get(0).getXCords();

        yMaxCord=memoryGraf1.getListOfNodes().get(0).getYCords();
        yMinCord=memoryGraf1.getListOfNodes().get(0).getYCords();
        for(Node node:memoryGraf1.getListOfNodes())
        {
            if(xMaxCord<node.getXCords())
                xMaxCord=node.getXCords();

            if(xMinCord>node.getXCords())
                xMinCord=node.getXCords();

            if(yMaxCord<node.getYCords())
                yMaxCord=node.getYCords();

            if(yMinCord>node.getYCords())
                yMinCord=node.getYCords();
        }

        deltaX=xMaxCord-xMinCord+20;
        deltaY=yMaxCord-yMinCord+20;

        for(int i=0;i<memoryGraf2.getListOfNodes().size();i++)
        {
            for(Node node:memoryGraf1.getListOfNodes())
            {
                this.InitNewNode(node.getXCords()+deltaX*i,node.getYCords()+deltaY*i);
            }
        }

        for(int i=0;i<this.getListOfNodes().size();i++)
        {
            int firstGrafIndex=i%memoryGraf1.getListOfNodes().size();
            int secondGrafIndex=i/memoryGraf1.getListOfNodes().size();

            int countOfoutputArcsGraf1=memoryGraf1.getListOfNodes().get(firstGrafIndex).getOutputArc().size();
            int countOfoutputArcsGraf2=memoryGraf2.getListOfNodes().get(secondGrafIndex).getOutputArc().size();

            List<Integer> firsGrafOutputInexs=new ArrayList<>(0);
            List<Integer> secondGrafOutputInexs=new ArrayList<>(0);


            for(int j=0;j<countOfoutputArcsGraf1;j++)
            {
                int indexOfinputNode=memoryGraf1.getListOfNodes().indexOf(memoryGraf1.getListOfNodes().get(firstGrafIndex).getOutputArc().get(j).getInputNode());
                firsGrafOutputInexs.add(indexOfinputNode);
            }

            for(int j=0;j<countOfoutputArcsGraf2;j++)
            {
                int indexOfinputNode=memoryGraf2.getListOfNodes().indexOf(memoryGraf2.getListOfNodes().get(secondGrafIndex).getOutputArc().get(j).getInputNode());
                secondGrafOutputInexs.add(indexOfinputNode);
            }

            for(int j=0;j<firsGrafOutputInexs.size();j++)
                for(int k=0;k<secondGrafOutputInexs.size();k++)
                {
                    this.addArc(this.getListOfNodes().get(i),this.getListOfNodes().get(firsGrafOutputInexs.get(j)+secondGrafOutputInexs.get(k)*memoryGraf1.getListOfNodes().size()));
                    this.getArcs().get(this.getArcs().size()-1).mode=1;
                }

            if(firsGrafOutputInexs.size()==0)
            {
                for(int k=0;k<secondGrafOutputInexs.size();k++)
                {
                    this.addArc(this.getListOfNodes().get(i),this.getListOfNodes().get(secondGrafOutputInexs.get(k)*memoryGraf1.getListOfNodes().size()));
                    this.getArcs().get(this.getArcs().size()-1).mode=1;
                }
            }

            if(secondGrafOutputInexs.size()==0)
            {
                for(int k=0;k<firsGrafOutputInexs.size();k++)
                {
                    this.addArc(this.getListOfNodes().get(i),this.getListOfNodes().get(firsGrafOutputInexs.get(k)));
                    this.getArcs().get(this.getArcs().size()-1).mode=1;
                }
            }
        }


    }
}

