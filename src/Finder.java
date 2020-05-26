import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Finder {
    Memory memory;
    List<ScPath> allScPath = new ArrayList<>(0);
    ScPath tempScPath = new ScPath();

    Finder(Memory memory) {
        this.memory = memory;
    }

    ScPath eccentricity(Node head)
    {
        List<ScPath> allShortPathes = new ArrayList<>(0);

        Map<Node, Boolean> map = new HashMap<Node, Boolean>();
        for(Node node:memory.getListOfNodes())
            map.put(node,false);

            map.put(head, true);
            tempScPath.add(head);
            for (int j = 0; j < memory.getListOfNodes().size(); j++) {
                if (head.isEqual(memory.getListOfNodes().get(j)))
                    continue;

                findCikl(head, map, memory.getListOfNodes().get(j));

                int min=0;
                if(allScPath.size()>0)
                    min = allScPath.get(0).size();

                if(allScPath.size()>1)
                {
                    for (int k = 1; k < allScPath.size(); k++) {
                        if (allScPath.get(k).size() < min)
                            min = allScPath.get(k).size();
                        else
                            allScPath.remove(allScPath.get(k));
                    }
                }

                if (allScPath.size() != 0) {
                    allShortPathes.add(allScPath.get(0));
                    allScPath.remove(allScPath.get(0));
                }

            }

            ScPath maxPath;
            if(allShortPathes.size()!=0)
                maxPath=allShortPathes.get(0);
            else
                return null;

            if(allShortPathes.size()>1)
            {
                for (int j = 1; j < allShortPathes.size(); j++) {
                    if (maxPath.size() < allShortPathes.get(j).size()) {
                        ScPath temp=maxPath;
                        maxPath = allShortPathes.get(j);
                        allShortPathes.remove(temp);
                        j--;
                    } else {
                        allShortPathes.remove(allShortPathes.get(j));
                        j--;
                    }

                }
            }
            map.put(head, false);
            tempScPath.clear();

        return allShortPathes.get(0);
    }

    int diametr() {

        int diametr = 0;
        List<ScPath> allShortPathes = new ArrayList<>(0);

        Map<Node, Boolean> map = new HashMap<Node, Boolean>();
        for(Node node:memory.getListOfNodes())
            map.put(node,false);

        for (int i = 0; i < memory.getListOfNodes().size(); i++) {
            Node head = memory.getListOfNodes().get(i);
            map.put(head, true);
            tempScPath.add(head);
            for (int j = 0; j < memory.getListOfNodes().size(); j++) {
                if (head.isEqual(memory.getListOfNodes().get(j)))
                    continue;

                findCikl(head, map, memory.getListOfNodes().get(j));

                int min=0;
                if(allScPath.size()>0)
                    min = allScPath.get(0).size();

                if(allScPath.size()>1)
                {
                    for (int k = 1; k < allScPath.size(); k++) {
                        if (allScPath.get(k).size() < min)
                            min = allScPath.get(k).size();
                        else
                            allScPath.remove(allScPath.get(k));
                    }
                }

                if (allScPath.size() != 0) {
                    allShortPathes.add(allScPath.get(0));
                    allScPath.remove(allScPath.get(0));
                }

            }
            map.put(head, false);
            tempScPath.clear();
        }

        diametr = allShortPathes.get(0).size();
        for (int i = 0; i < allShortPathes.size(); i++) {
            if (allShortPathes.get(i).size() > diametr)
                diametr = allShortPathes.get(i).size();
        }

        return diametr;
    }

    int radius()
    {
        int radius = 0;
        List<ScPath>allEccentricity=new ArrayList<>(0);
        for(Node node:memory.getListOfNodes())
            if(eccentricity(node)!=null)
                allEccentricity.add(eccentricity(node));


        radius = allEccentricity.get(0).size();

        for (int i = 0; i < allEccentricity.size(); i++) {
            if (allEccentricity.get(i).size() < radius)
                radius = allEccentricity.get(i).size();
        }

        return radius;
    }

    List<Node> center()
    {
        int radius=radius();
        List<Node> centers=new ArrayList<>(0);
        for(Node node :memory.getListOfNodes())
        {
            if(eccentricity(node)!=null && eccentricity(node).size()==radius)
                centers.add(node);
        }
        return centers;
    }

    private void findCikl(Node head, Map<Node, Boolean> map, Node finish) {
        if (head == finish) {
            Node save = tempScPath.get(tempScPath.size() - 1);
            tempScPath.delete(tempScPath.get(tempScPath.size() - 1));
            allScPath.add(tempScPath.getCopy());
            tempScPath.add(save);
        }

        for (int i = 0; i < head.getOutputArc().size(); i++) {

            if (!map.containsKey(head.getOutputArc().get(i).getInputNode()) || map.get(head.getOutputArc().get(i).getInputNode()) == false) {
                tempScPath.add(head.getOutputArc().get(i).getInputNode());

                map.put(head.getOutputArc().get(i).getInputNode(), true);
                findCikl(head.getOutputArc().get(i).getInputNode(), map, finish);
                map.put(head.getOutputArc().get(i).getInputNode(), false);

                tempScPath.delete(head.getOutputArc().get(i).getInputNode());
            }
        }

        for (int i = 0; i < head.getInputArc().size(); i++) {
            if (!map.containsKey(head.getInputArc().get(i).getInputNode()) || map.get(head.getInputArc().get(i).getOutputNode()) == false) {
                if (head.getInputArc().get(i).mode == 1) {
                    tempScPath.add(head.getInputArc().get(i).getOutputNode());

                    map.put(head.getInputArc().get(i).getOutputNode(), true);
                    findCikl(head.getInputArc().get(i).getOutputNode(), map, head);
                    map.put(head.getInputArc().get(i).getOutputNode(), false);

                    tempScPath.delete(head.getInputArc().get(i).getOutputNode());
                }
            }
        }
    }

    List<ScPath> findGameltonovCikl() {
        Map<Node, Boolean> map = new HashMap<Node, Boolean>();

        for(Node node:memory.getListOfNodes())
            map.put(node,false);

        for (Node head : memory.getListOfNodes()) {
            tempScPath.add(head);

            for (int i = 0; i < head.getOutputArc().size(); i++) {

                if (!map.containsKey(head.getOutputArc().get(i).getInputNode()) || map.get(head.getOutputArc().get(i).getInputNode()) == false) {
                    tempScPath.add(head.getOutputArc().get(i).getInputNode());

                    map.put(head.getOutputArc().get(i).getInputNode(), true);
                    findCikl(head.getOutputArc().get(i).getInputNode(), map, head);
                    map.put(head.getOutputArc().get(i).getInputNode(), false);

                    tempScPath.delete(head.getOutputArc().get(i).getInputNode());
                }
            }

            for (int i = 0; i < head.getInputArc().size(); i++) {
                if (!map.containsKey(head.getInputArc().get(i).getInputNode()) || map.get(head.getInputArc().get(i).getOutputNode()) == false) {
                    if (head.getInputArc().get(i).mode == 1) {
                        tempScPath.add(head.getInputArc().get(i).getOutputNode());

                        map.put(head.getInputArc().get(i).getOutputNode(), true);
                        findCikl(head.getInputArc().get(i).getOutputNode(), map, head);
                        map.put(head.getInputArc().get(i).getOutputNode(), false);

                        tempScPath.delete(head.getInputArc().get(i).getOutputNode());
                    }
                }
            }
            tempScPath.delete(head);
        }

        for (int i = 0; i < allScPath.size(); i++) {
            if (allScPath.get(i).size() != memory.getListOfNodes().size()) {
                allScPath.remove(allScPath.get(i));
                i--;
            }
        }

        for (int i = 0; i < allScPath.size(); i++) {
            for (int j = i + 1; j < allScPath.size(); j++) {
                if (allScPath.get(i).isEqal(allScPath.get(j))) {
                    allScPath.remove(allScPath.get(j));
                    j--;
                }
            }
        }


        return (allScPath);
    }


}