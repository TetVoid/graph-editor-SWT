import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class MainWindow {

    public static void main(String[] args)
    {
        Display display=new Display();
        Shell shell=new Shell(display);

        shell.setLayout(new RowLayout(SWT.HORIZONTAL));

        Composite window=new Composite(shell,SWT.NONE);
        window.setLayout(new RowLayout(SWT.VERTICAL));

        Button newGraf=new Button(window,SWT.NONE);
        Button lineMultiplication=new Button(window,SWT.NONE);
        Button vectorMultiplication=new Button(window,SWT.NONE);

        Image image1 = new Image(shell.getDisplay(),"newGraff.png");
        Image image2 = new Image(shell.getDisplay(),"lineMultipliction.png");
        Image image3 = new Image(shell.getDisplay(),"vector.png");
        newGraf.setImage(image1);
        lineMultiplication.setImage(image3);
        vectorMultiplication.setImage(image2);

        List<DrawingScreen> allGrafWindow=new ArrayList<>(0);

        TabFolder folder = new TabFolder(shell, SWT.NONE);
        TabItem tab = new TabItem(folder, SWT.NONE);
        tab.setText("graf");

        Composite screen =new Composite(folder,SWT.NONE);
        screen.setLayout(new RowLayout(SWT.HORIZONTAL));

        DrawingScreen drawingScreen=new DrawingScreen(screen,tab);
        drawingScreen.drawGraf();
        allGrafWindow.add(drawingScreen);

        tab.setControl(screen);

        final int[] countOfgrafs = {1};
        newGraf.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                TabItem tab1 = new TabItem(folder, SWT.NONE);
                tab1.setText("graf("+ countOfgrafs[0] +")");
                countOfgrafs[0]++;

                Composite screen =new Composite(folder,SWT.NONE);
                screen.setLayout(new RowLayout(SWT.HORIZONTAL));

                DrawingScreen drawingScreen=new DrawingScreen(screen,tab1);
                drawingScreen.drawGraf();
                allGrafWindow.add(drawingScreen);

                tab1.setControl(screen);
            }
        });

        lineMultiplication.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Shell newShell=new Shell();
                newShell.setLayout(new RowLayout(SWT.HORIZONTAL));
                newShell.setLocation(1000,300);

                Combo graf1=new Combo(newShell,SWT.DROP_DOWN);
                Combo graf2=new Combo(newShell,SWT.DROP_DOWN);
                Button start=new Button(newShell,SWT.NONE);
                start.setText("ok");

                List<Memory> allGrafMemory=new ArrayList<>(0);
                for(DrawingScreen grafWindow: allGrafWindow)
                {
                    allGrafMemory.add(grafWindow.getMemory());
                    graf1.add(grafWindow.getMemory().getGrafName());
                    graf2.add(grafWindow.getMemory().getGrafName());
                }

                start.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {

                        TabItem tab1 = new TabItem(folder, SWT.NONE);
                        tab1.setText("graf("+ countOfgrafs[0] +")");
                        countOfgrafs[0]++;

                        Memory memoryGraf1,memoryGraf2;


                        Composite screen =new Composite(folder,SWT.NONE);
                        screen.setLayout(new RowLayout(SWT.HORIZONTAL));

                        DrawingScreen drawingScreen=new DrawingScreen(screen,tab1);
                        allGrafWindow.add(drawingScreen);

                        tab1.setControl(screen);

                        memoryGraf1=allGrafMemory.get(graf1.getSelectionIndex());
                        memoryGraf2=allGrafMemory.get(graf2.getSelectionIndex());

                        drawingScreen.getMemory().lineMultiplication(memoryGraf1,memoryGraf2);

                        drawingScreen.drawGraf();
                        newShell.close();
                    }
                    });

                newShell.pack();
                newShell.open();
            }
            });

        vectorMultiplication.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Shell newShell=new Shell();
                newShell.setLayout(new RowLayout(SWT.HORIZONTAL));
                newShell.setLocation(1000,300);

                Combo graf1=new Combo(newShell,SWT.DROP_DOWN);
                Combo graf2=new Combo(newShell,SWT.DROP_DOWN);
                Button start=new Button(newShell,SWT.NONE);
                start.setText("ok");

                List<Memory> allGrafMemory=new ArrayList<>(0);
                for(DrawingScreen grafWindow: allGrafWindow)
                {
                    allGrafMemory.add(grafWindow.getMemory());
                    graf1.add(grafWindow.getMemory().getGrafName());
                    graf2.add(grafWindow.getMemory().getGrafName());
                }

                start.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {

                        TabItem tab1 = new TabItem(folder, SWT.NONE);
                        tab1.setText("graf("+ countOfgrafs[0] +")");
                        countOfgrafs[0]++;

                        Memory memoryGraf1,memoryGraf2;


                        Composite screen =new Composite(folder,SWT.NONE);
                        screen.setLayout(new RowLayout(SWT.HORIZONTAL));

                        DrawingScreen drawingScreen=new DrawingScreen(screen,tab1);
                        allGrafWindow.add(drawingScreen);

                        tab1.setControl(screen);

                        memoryGraf1=allGrafMemory.get(graf1.getSelectionIndex());
                        memoryGraf2=allGrafMemory.get(graf2.getSelectionIndex());

                        drawingScreen.getMemory().vectorMultiplication(memoryGraf1,memoryGraf2);

                        drawingScreen.drawGraf();
                        newShell.close();
                    }
                });

                newShell.pack();
                newShell.open();
            }
        });

        shell.pack();
        shell.open();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }
}
