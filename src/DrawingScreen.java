import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class DrawingScreen
{
    Canvas screen;
    Composite shell;
    Boolean mouseFlag=false;
    Memory memory;
    Button save;
    Button load;
    Button nameGraf;
    Button nodeMode;
    Button arcMode;
    Button neorent;
    Button setColor;
    Button info;
    Button createPlanar;
    Button findWays;
    Button center;

    LogicObject selectedObject =null;
    LogicObject chooseObject=null;
    int drawMode=0;
    TabItem tab;

    int push=0;

    int Xcord;
    int Ycord;
    DrawingScreen(Composite shell,TabItem tab)
    {
        Composite buttons=new Composite(shell,SWT.NONE);
        buttons.setLayout(new RowLayout(SWT.VERTICAL));
        Composite canvasComposite=new Composite(shell,SWT.NONE);
        canvasComposite.setLayout(new RowLayout(SWT.HORIZONTAL));

        this.tab=tab;
        this.shell=shell;
        screen=new Canvas(canvasComposite, SWT.NONE);
        memory =new Memory();
        screen.setLayoutData(new RowData(1000,600));

        memory.setGrafName(tab.getText());

        Image image1 = new Image(shell.getDisplay(),"NameGraf.png");
        Image image2 = new Image(shell.getDisplay(),"Node.png");
        Image image3 = new Image(shell.getDisplay(),"Arc.png");
        Image image4 = new Image(shell.getDisplay(),"load.png");
        Image image5 = new Image(shell.getDisplay(),"save.png");
        Image image6 = new Image(shell.getDisplay(),"setColor.png");
        Image image7 = new Image(shell.getDisplay(),"info.png");
        Image image8 = new Image(shell.getDisplay(),"neorent.png");
        Image image9 = new Image(shell.getDisplay(),"createPlanar.png");
        Image image10 = new Image(shell.getDisplay(),"findWays.png");
        Image image11 = new Image(shell.getDisplay(),"centers.png");

        load=new Button(buttons,SWT.NONE);
        save=new Button(buttons,SWT.NONE);
        nameGraf=new Button(buttons,SWT.NONE);
        nodeMode=new Button(buttons,SWT.NONE);
        arcMode=new Button(buttons,SWT.NONE);
        neorent=new Button(buttons,SWT.NONE);
        setColor=new Button(buttons, SWT.NONE);
        info=new Button(buttons, SWT.NONE);
        createPlanar=new Button(buttons, SWT.NONE);
        findWays=new Button(buttons, SWT.NONE);
        center=new Button(buttons, SWT.NONE);

        nameGraf.setImage(image1);
        nodeMode.setImage(image2);
        arcMode.setImage(image3);
        load.setImage(image4);
        save.setImage(image5);
        setColor.setImage(image6);
        info.setImage(image7);
        neorent.setImage(image8);
        createPlanar.setImage(image9);
        findWays.setImage(image10);
        center.setImage(image11);

        initEventListeners();
    }

    void initEventListeners()
    {
        center.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                if(push==0)
                    push=1;
                else
                    push=0;

                Finder finder=new Finder(memory);
                List<Node> centers=finder.center();

                if(push==1) {
                    for (Node node:centers)
                        node.setColor(123, 104, 238);
                    screen.redraw();
                }
                else
                {
                    for(Node node:memory.getListOfNodes())
                        node.setColor(255,255,255);
                    screen.redraw();
                }
            }
            });

        findWays.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                int [][]colors=new int[8][3];
                colors[0]= new int[]{255, 255, 0};
                colors[1]= new int[]{255, 105, 108};
                colors[2]= new int[]{0, 100, 0};
                colors[3]= new int[]{123, 104, 238};
                colors[4]= new int[]{0, 255, 255};
                colors[5]= new int[]{119, 136, 153};
                colors[6]= new int[]{0, 0, 255};

                Shell newShell=new Shell(SWT.NONE);
                newShell.setLayout(new RowLayout(SWT.HORIZONTAL));
                newShell.setLocation(500,500);

                Button prev=new Button( newShell,SWT.NONE);
                Button next=new Button( newShell,SWT.NONE);
                Button close=new Button( newShell,SWT.NONE);

                close.setText("OK");

                Image imageNext = new Image(shell.getDisplay(),"button1.png");
                Image imagePrev = new Image(shell.getDisplay(),"button2.png");

                next.setImage(imagePrev);
                prev.setImage(imageNext);

                if(drawMode!=3 && drawMode!=4)
                {
                    drawMode = 3;
                    screen.redraw();
                }


                Finder finder=new Finder(memory);
                List<ScPath> allPath =finder.findGameltonovCikl();

                final int[] i = {0};
               screen.addPaintListener(new PaintListener() {
                    public void paintControl(PaintEvent e) {
                        if(drawMode==3&&allPath.size()!=0) {
                            ScPath path = allPath.get(i[0]);

                            for (int j = 0; j < path.size(); j++) {
                                path.get(j).setColor(colors[i[0]][0], colors[i[0]][1], colors[i[0]][2]);
                                if (j < path.size() - 1)
                                    memory.getArcByNodes(path.get(j), path.get(j + 1)).setColor(colors[i[0]][0], colors[i[0]][1], colors[i[0]][2]);
                                else
                                    memory.getArcByNodes(path.get(path.size() - 1), path.get(0)).setColor(colors[i[0]][0], colors[i[0]][1], colors[i[0]][2]);
                            }

                            screen.redraw();
                            drawMode=4;
                        }
                    }
                });

                next.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                        if(i[0]<allPath.size()-1) {
                            i[0]++;
                            for(Node node : memory.getListOfNodes())
                                node.setColor(255,255,255);

                            for(Arc arc: memory.getArcs())
                                arc.setColor(0,0,0);
                            drawMode=3;
                            screen.redraw();
                        }
                    }
                    });

                close.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                        drawMode = 0;

                        for(Node node : memory.getListOfNodes())
                            node.setColor(255,255,255);

                        for(Arc arc: memory.getArcs())
                            arc.setColor(0,0,0);

                        screen.redraw();
                        newShell.close();
                    }
                });


                prev.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                        if(i[0]>0) {
                            i[0]--;
                            for(Node node : memory.getListOfNodes())
                                node.setColor(255,255,255);

                            for(Arc arc: memory.getArcs())
                                arc.setColor(0,0,0);
                            drawMode=3;
                            screen.redraw();
                        }
                    }
                });

                newShell.pack();
                newShell.open();
            }
            });
        createPlanar.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                PlanarityVerifier verifier=new PlanarityVerifier(memory);
                verifier.createPlanar();
                screen.redraw();
            }
            });

        neorent.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                if(selectedObject.getArc()!=null)
                    selectedObject.getArc().mode=1;
                screen.redraw();
            }
        });

        info.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Shell newShell=new Shell(SWT.NONE);
                newShell.setLayout(new RowLayout(SWT.VERTICAL));
                newShell.setLocation(1000,300);

                Finder finder=new Finder(memory);

                Label countOfNodes=new Label(newShell,SWT.NONE);
                Label countOfArcs=new Label(newShell,SWT.NONE);
                Label chouseNodeLvL=new Label(newShell,SWT.NONE);
                Label grafLvL=new Label(newShell,SWT.NONE);
                Label planarity=new Label(newShell,SWT.NONE);
                Label diametr=new Label(newShell,SWT.NONE);
                Label radius=new Label(newShell,SWT.NONE);

                diametr.setText("Диаметр графа "+finder.diametr());
                radius.setText("Радиус графа "+finder.radius() );

                PlanarityVerifier verifier=new PlanarityVerifier(memory);
                if(verifier.verify()==true)
                    planarity.setText("Граф планарен");
                else
                    planarity.setText("Граф не планарен");

                int LvL=0;
                for(int i=0;i<memory.getListOfNodes().size();i++)
                {
                    LvL+=memory.getListOfNodes().get(i).getOutputArc().size();
                }

                countOfNodes.setText("Количество вершин "+memory.getListOfNodes().size());
                countOfArcs.setText("Количество дуг "+memory.getArcs().size());
                if(selectedObject!=null && selectedObject.getArc()==null)
                    chouseNodeLvL.setText("Степень выделеной вершины "+selectedObject.getNode().getOutputArc().size());
                else
                    chouseNodeLvL.setText("Вершина не выбрана");
                grafLvL.setText("Степень графа "+LvL);

                List<ArrayList<Integer>> matrix= memory.getMatrix();
                for(int i=0;i<matrix.size();i++)
                {
                    String tempString=new String();
                    for(int j=0;j<matrix.get(i).size();j++)
                    {
                        tempString +=matrix.get(i).get(j);
                        tempString +="  ";
                    }
                    Label tempLabel=new Label(newShell,SWT.NONE);
                    tempLabel.setText(tempString);
                }

                Button button=new Button(newShell,SWT.NONE);
                button.setText("OK");

                button.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                        newShell.close();
                        screen.redraw();
                    }
                });
                newShell.pack();
                newShell.open();
            }
            });

        setColor.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Shell newShell=new Shell();
                newShell.setLayout(new RowLayout(SWT.VERTICAL));
                newShell.setLocation(800,400);
                Composite colorButtons=new Composite(newShell,SWT.NONE);
                colorButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

                int [][]colors=new int[9][3];
                colors[0]= new int[]{255, 255, 255};
                colors[1]= new int[]{255, 255, 0};
                colors[2]= new int[]{255, 105, 108};
                colors[3]= new int[]{0, 100, 0};
                colors[4]= new int[]{123, 104, 238};
                colors[5]= new int[]{0, 255, 255};
                colors[6]= new int[]{119, 136, 153};
                colors[7]= new int[]{0, 0, 255};
                colors[8]= new int[]{0, 0, 0};
                int i=0;
                for(i=0;i<9;i++)
                {
                    Button button=new Button(colorButtons,SWT.NONE);
                    int finalI = i;

                    button.addPaintListener(new PaintListener() {
                    public void paintControl(PaintEvent e) {
                        Color color = new Color(e.gc.getDevice(), colors[finalI][0], colors[finalI][1], colors[finalI][2]);
                        button.setBackground(color);
                    }
                    });

                    button.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent event) {
                            if(selectedObject!=null) {
                                selectedObject.setColor(colors[finalI][0], colors[finalI][1], colors[finalI][2]);
                                newShell.close();
                                screen.redraw();
                            }
                        }
                    });
                }

                newShell.pack();
                newShell.open();
                newShell.setSize(140,90);
            }
        });


        save.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                SaveMemory saveMemory=new SaveMemory(shell.getShell(),memory);
            }
        });

        load.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event)
            {
                LoadMemory loadMemory=new LoadMemory(shell.getShell());
                memory= loadMemory.getMemory();
                tab.setText(memory.getGrafName());
                screen.redraw();
            }
        });

        nodeMode.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {

                drawMode=0;
            }
            });

        arcMode.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                drawMode=1;
            }
        });

        nameGraf.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Shell newShell=new Shell();
                newShell.setLayout(new RowLayout(SWT.VERTICAL));
                newShell.setLocation(800,400);
                Text text=new Text(newShell,SWT.NONE);
                text.setLayoutData(new RowData(180,20));
                Button button=new Button(newShell,SWT.NONE);
                button.setText("OK");

                button.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                        tab.setText(text.getText());
                        memory.setGrafName(text.getText());
                        newShell.close();
                        screen.redraw();
                    }
                });
                newShell.pack();
                newShell.open();
                newShell.setSize(220,110);
            }
        });

        screen.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                switch (e.keyCode)
                {
                    case 105:
                        if(selectedObject !=null && selectedObject.getNode()!=null) {
                            Shell newShell=new Shell();
                            newShell.setLayout(new RowLayout(SWT.VERTICAL));
                            newShell.setLocation(800,400);
                            Text text=new Text(newShell,SWT.NONE);
                            text.setLayoutData(new RowData(180,20));
                            Button button=new Button(newShell,SWT.NONE);
                            button.setText("OK");

                            button.addSelectionListener(new SelectionAdapter() {
                                public void widgetSelected(SelectionEvent event) {
                                    selectedObject.setName(text.getText());
                                    newShell.close();
                                    screen.redraw();
                                }
                                });
                            newShell.pack();
                            newShell.open();
                            newShell.setSize(220,110);
                        }
                        break;

                    case 127:
                        if(selectedObject !=null&&selectedObject.getNode()!=null)
                        {
                            List<Arc> temp= selectedObject.getNode().getInputArc();
                            for(int i=0;i<temp.size();i++)
                            {
                                memory.getArcs().remove(temp.get(i));
                            }

                             temp= selectedObject.getNode().getOutputArc();
                            for(int i=0;i<temp.size();i++)
                            {
                                memory.getArcs().remove(temp.get(i));
                            }

                            memory.getListOfNodes().remove(selectedObject.getNode());
                            screen.redraw();
                        }

                        if(selectedObject !=null&&selectedObject.getArc()!=null)
                        {
                             memory.getArcs().remove(selectedObject.getArc());
                            screen.redraw();
                        }
                        break;
                }

            }
        });

        screen.addMouseListener(new MouseListener() {
            public void mouseDown(MouseEvent e) {
                if(memory.cheakNode(e.x,e.y))
                {
                    if(selectedObject !=null)
                        selectedObject.setSelected(false);
                    mouseFlag = true;
                    Node temp=memory.findNodeByCords(e.x,e.y);

                    selectedObject = temp;
                    selectedObject.setNode(temp);
                    selectedObject.setSelected(true);
                }
                else
                {
                    if(memory.checkArc(e.x,e.y))
                    {
                        if(selectedObject !=null)
                            selectedObject.setSelected(false);
                        Arc temp=memory.getArc(e.x,e.y);

                        selectedObject = temp;
                        selectedObject.setArc(temp);
                        selectedObject.setSelected(true);
                    }
                    else
                        if(selectedObject !=null)
                        {
                            selectedObject.setSelected(false);
                            selectedObject = null;
                        }
                }
                screen.redraw();
            }


            public void mouseUp(MouseEvent e)
            {
                mouseFlag = false;

                if(drawMode==1)
                {
                    if(memory.cheakNode(e.x,e.y))
                        if(memory.findNodeByCords(e.x,e.y)!=selectedObject.getNode())
                        memory.addArc(selectedObject.getNode(), memory.findNodeByCords(e.x, e.y));

                }
                screen.redraw();
            }

            public void mouseDoubleClick(MouseEvent e)
            {
                if(drawMode==0)
                {
                    memory.InitNewNode(e.x, e.y);
                    screen.redraw();
                }
            }



        });

        screen.addMouseMoveListener(new MouseMoveListener() {
            public void mouseMove(MouseEvent e) {
                Xcord=e.x;
                Ycord=e.y;
                   if(mouseFlag&&drawMode==0)
                   {
                       Node temp=selectedObject.getNode();
                       int x=temp.getXCords();
                       int y=temp.getYCords();

                       if(Math.sqrt(Math.abs((e.x*e.x+e.y*e.y)-(x*x+y*y)))>80)
                       {
                           temp.setXCords(e.x);
                           temp.setYCords(e.y);
                           screen.redraw();
                       }
                   }

                       screen.addPaintListener(new PaintListener() {
                           public void paintControl(PaintEvent e) {
                               if(mouseFlag&drawMode==1)
                               {
                                   Color color1 = new Color(e.gc.getDevice(), 0, 0, 0);
                                   e.gc.setForeground(color1);
                                   e.gc.setLineWidth(2);
                                   Node temp=selectedObject.getNode();

                                   e.gc.drawLine(temp.getXCords(), temp.getYCords(), Xcord, Ycord);
                                   screen.redraw();
                               }
                           }
                           });

                   if(memory.cheakNode(e.x,e.y))
                   {
                       if(chooseObject!=memory.findNodeByCords(e.x,e.y))
                       {
                           Node temp=memory.findNodeByCords(e.x, e.y);

                           temp.setChoose(true);
                           chooseObject = temp;
                           chooseObject.setNode(temp);
                           screen.redraw();
                       }
                   }
                   else
                   {
                       if(memory.checkArc(e.x,e.y) && chooseObject ==null)
                       {
                           chooseObject= memory.getArc(e.x,e.y);
                           chooseObject.setChoose(true);
                           screen.redraw();
                       }
                       else
                       {
                           if(chooseObject!=null)
                           {
                               chooseObject.setChoose(false);
                               chooseObject=null;
                               screen.redraw();
                           }
                       }
                   }
                }
            });
    }

    private void drawArrow(GC gc, int x1, int y1, int x2, int y2, double arrowLength, double arrowAngle) {
        double theta = Math.atan2(y2 - y1, x2 - x1);
        double offset = (arrowLength - 2) * Math.cos(arrowAngle);

        gc.drawLine(x1, y1, (int)(x2 - offset * Math.cos(theta)), (int)(y2 - offset * Math.sin(theta)));

        Path path = new Path(gc.getDevice());
        path.moveTo((float)(x2 - arrowLength * Math.cos(theta - arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta - arrowAngle)));
        path.lineTo((float)x2, (float)y2);
        path.lineTo((float)(x2 - arrowLength * Math.cos(theta + arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta + arrowAngle)));
        path.close();

        gc.fillPath(path);

        path.dispose();
    }

    void drawGraf()
    {
        screen.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {

                List<Node> nodes= memory.getListOfNodes();
                List<Arc> arcs= memory.getArcs();

                for(int i=0;i<arcs.size();i++)
                {
                    Arc temp=arcs.get(i);

                    Color color1=new Color(e.gc.getDevice(),0,0,0);
                    e.gc.setForeground(color1);
                    e.gc.setLineWidth(2);

                    int x1 =temp.getOutputNode().getXCords();
                    int y1=temp.getOutputNode().getYCords();
                    int x2=temp.getInputNode().getXCords();
                    int y2 =temp.getInputNode().getYCords();

                    Color color0=new Color(e.gc.getDevice(),temp.getColor()[0],temp.getColor()[1],temp.getColor()[2]);
                    e.gc.setForeground(color0);
                    e.gc.setBackground(color0);

                    if(temp.isChoose())
                    {
                        Color color=new Color(e.gc.getDevice(),255,165,0);
                        e.gc.setForeground(color);
                        e.gc.setBackground(color);
                    }

                    if(temp.isSelected())
                    {
                        Color color=new Color(e.gc.getDevice(),124,207,97);
                        e.gc.setForeground(color);
                        e.gc.setBackground(color);
                    }

                    int nx1,ny1;

                    if(x1<x2)
                        nx1=x2-5;
                    else
                        nx1=x2+5;

                    if(x1==x2)
                        nx1=x2;


                    if((x2-x1)!=0)
                        ny1=((nx1-x2)*(y2-y1))/(x2-x1)+y2;
                    else
                        if(y2>y1)
                            ny1=y2-5;
                        else
                            ny1=y2+5;

                        if(ny1>y2+10||ny1<y2-10)
                            if(y2>y1)
                                ny1=y2-5;
                            else
                                ny1=y2+5;
                    if(temp.mode==0)
                    drawArrow(e.gc,x1,y1,nx1,ny1,10, Math.toRadians(30));
                    else
                        e.gc.drawLine(x1,y1,nx1,ny1);
                }

                for(int i=0;i<nodes.size();i++)
                {
                    Node temp=nodes.get(i);
                    Color color0=new Color(e.gc.getDevice(),temp.getColor()[0],temp.getColor()[1],temp.getColor()[2]);
                    e.gc.setBackground(color0);
                   e.gc.fillOval(temp.getXCords()-10,temp.getYCords()-10,15,15);

                    Color color1=new Color(e.gc.getDevice(),0,0,0);

                    if(memory.getListOfNodes().get(i).isChoose()) {
                        color1 = new Color(e.gc.getDevice(), 255, 165, 0);
                    }

                    if(memory.getListOfNodes().get(i).isSelected()) {
                        color1 = new Color(e.gc.getDevice(), 124, 207, 97);

                    }

                    e.gc.setForeground(color1);
                    e.gc.setLineWidth(3);

                    e.gc.drawOval(temp.getXCords()-10,temp.getYCords()-10,15,15);

                    color1=new Color(e.gc.getDevice(),0,0,0);
                    e.gc.setForeground(color1);
                    int x1=temp.getXCords()-10-temp.getName().length()*8;
                    e.gc.drawText(temp.getName(),x1,temp.getYCords()-35);
                }
            }
        });
    }

    public Memory getMemory()
    {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }
}


