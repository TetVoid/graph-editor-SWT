import java.util.*;


public class PlanarityVerifier {
    private static final int COUNT_OF_NODES_K5 = 5;
    private static final int COUNT_OF_ARCS_IN_UNDIRECTED_K5 = 10;
    private static final int COUNT_OF_NODES_K33 = 6;
    private static final int COUNT_OF_ARCS_IN_UNDIRECTED_K33 = 9;

    private Memory graph;
    private Memory memory;

    PlanarityVerifier(Memory graph) {
        this.graph = graph.getEquivalent();
        this.memory=graph;
    }

    public boolean verify() {
        if (graph.getListOfNodes().size() >= COUNT_OF_NODES_K5
                && graph.getArcs().size() == graph.getListOfNodes().size() * (graph.listOfNodes.size() - 1)) {
            return false;
        }

        List<Node> someKuratowskiGraph = permute();

        if (someKuratowskiGraph.size() == COUNT_OF_NODES_K5
                && graph.getArcs().size() >= COUNT_OF_ARCS_IN_UNDIRECTED_K5) {
            return false;
        } else if (someKuratowskiGraph.size() == COUNT_OF_NODES_K33
                && graph.getArcs().size() >= COUNT_OF_ARCS_IN_UNDIRECTED_K33) {
            return false;
        }

        return true;
    }

    public void createPlanar() {
            List<Node> someKuratowskiGraph = permute();

            if (someKuratowskiGraph.size() == COUNT_OF_NODES_K5
                    && graph.getArcs().size() >= COUNT_OF_ARCS_IN_UNDIRECTED_K5) {
                {
                    for (int i = 0; i < someKuratowskiGraph.size(); i++) {
                        if (someKuratowskiGraph.get(i).getInputArc() != null) {
                            Node input;
                            Arc arc;
                            input = someKuratowskiGraph.get(i).getInputArc().get(0).getInputNode();

                            arc = memory.getArc(input.getXCords(), input.getYCords());

                            memory.getArcs().remove(arc);
                            break;
                        } else {
                            Node output;
                            Arc arc;
                            output = someKuratowskiGraph.get(i).getOutputArc().get(0).getOutputNode();

                            arc = memory.getArc(output.getXCords(), output.getYCords());

                            memory.getArcs().remove(arc);
                            break;
                        }
                    }
                }
            } else if (someKuratowskiGraph.size() == COUNT_OF_NODES_K33
                    && graph.getArcs().size() >= COUNT_OF_ARCS_IN_UNDIRECTED_K33) {
                {
                    for (int i = 0; i < someKuratowskiGraph.size(); i++) {

                        if (someKuratowskiGraph.get(i).getInputArc() != null) {
                            Node input;
                            Arc arc;
                            input = someKuratowskiGraph.get(i).getInputArc().get(0).getOutputNode();

                            arc = memory.getArc(input.getXCords(), input.getYCords());

                            memory.getArcs().remove(arc);
                            break;
                        } else {
                            Node output;
                            Arc arc;
                            output = someKuratowskiGraph.get(i).getOutputArc().get(0).getInputNode();

                            arc = memory.getArc(output.getXCords(), output.getYCords());

                            memory.getArcs().remove(arc);
                            break;
                        }
                    }
                }
            }
        }


    private List<Node> permute() {
        List<Node> permutation = new ArrayList<>();

        for (Node one : graph.getListOfNodes()) {
            permutation.clear();
            permutation.add(one);

            for (Node two : graph.getListOfNodes()) {
                if (permutation.contains(two)) {
                    continue;
                }
                permutation.add(two);

                for (Node three : graph.getListOfNodes()) {
                    if (permutation.contains(three)) {
                        continue;
                    }
                    permutation.add(three);

                    for (Node four : graph.getListOfNodes()) {
                        if (permutation.contains(four)) {
                            continue;
                        }
                        permutation.add(four);

                        for (Node five : graph.getListOfNodes()) {
                            if (permutation.contains(five)) {
                                continue;
                            }
                            permutation.add(five);

                            for (Node six : graph.getListOfNodes()) {
                                if (permutation.contains(six)) {
                                    continue;
                                }
                                permutation.add(six);

                                for (int i = 0; i < permutation.size(); i++) {
                                    for (int j = 0; j < permutation.size(); j++) {
                                        Collections.swap(permutation, i, j);

                                        if (isK33(permutation)) {
                                            return permutation;
                                        }
                                    }
                                }

                                permutation.remove(six);
                            }

                            if (isK5(permutation)) {
                                return permutation;
                            }

                            permutation.remove(five);
                        }
                        permutation.remove(four);
                    }
                    permutation.remove(three);
                }
                permutation.remove(two);
            }
        }

        return permutation;
    }

    private boolean isK5(List<Node> permutation) {
        boolean isK5 = true;

        for (Node begin : permutation) {
            for (Node end : permutation) {
                if (begin.equals(end)) {
                    continue;
                }

                isK5 &= isPathExist(begin, end);
            }
        }

        return isK5;
    }

    private boolean isK33(List<Node> permutation) {
        boolean isK33 = true;

        List<Node> homes = permutation.subList(0, COUNT_OF_NODES_K33 / 2);
        List<Node> wells = permutation.subList(COUNT_OF_NODES_K33 / 2, COUNT_OF_NODES_K33);

        for (Node home : homes) {
            for (Node well : wells) {
                isK33 &= isPathExist(home, well);
            }
        }

        return isK33;
    }

    private boolean isPathExist(Node source, Node destination) {
        Map<Node, Boolean> visitedNodes = new HashMap<>();

        for (Node node : graph.getListOfNodes()) {
            visitedNodes.put(node, false);
        }

        LinkedList<Node> queue = new LinkedList<>();

        visitedNodes.replace(source, true);
        queue.add(source);

        while (queue.size() != 0) {
            source = queue.poll();

            for (Node adjacent : graph.getAdjacentNodesOf(source)) {
                if (adjacent.equals(destination)) {
                    return true;
                }

                if (!visitedNodes.get(adjacent)) {
                    visitedNodes.replace(adjacent, true);
                    queue.add(adjacent);
                }
            }
        }

        return false;
    }
}
