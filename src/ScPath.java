import java.util.ArrayList;
import java.util.List;

public class ScPath {
    private List<Node> path=new ArrayList<>(0);

    void add(Node newNode)
    {
        path.add(newNode);
    }

    Node get(int i)
    {
        return path.get(i);
    }

    void set(Node node,int i)
    {
        path.set(i,node);
    }

    void clear()
    {
        path.clear();
    }
    ScPath getCopy()
    {
        ScPath newScPath =new ScPath();
        for(Node tempNode: path)
            newScPath.add(tempNode);

        return newScPath;
    }

    boolean isEqal(ScPath workScPath)
    {
        ScPath scPath = workScPath.getCopy();


        if(scPath.size()!=this.size())
            return false;
        else
        {
            for(int i = 0; i< scPath.size(); i++)
            {
                boolean flag=true;
                for (int j = 0; j < scPath.size(); j++) {
                    if (scPath.get(j).isEqual(this.get(j)) ==false )
                        flag = false;
                }

                if(flag==true)
                    return true;
                Node tempLast,temp=null;
                tempLast= scPath.get(scPath.size()-1);
                for(int j = 0; j< scPath.size()-1; j++)
                {
                    temp= scPath.get(scPath.size()-j-2);
                    scPath.set(temp,scPath.size()-j-1);
                }
                scPath.set(tempLast,0);
            }
        }
        return false;
    }

    int size()
    {
        return path.size();
    }

    void delete(Node node)
    {
        path.remove(node);
    }
}
