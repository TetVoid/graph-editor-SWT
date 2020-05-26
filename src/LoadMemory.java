import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadMemory
{
    Memory memory;
    LoadMemory(Shell shell)
    {
        FileDialog dialog=new FileDialog(shell, SWT.OPEN);
        dialog.setFilterNames(new String[]{"SER files"});
        dialog.setFilterExtensions(new String[]{"*.ser"});
        dialog.open();

        try
        {
            FileInputStream file = new FileInputStream(dialog.getFileName());
            ObjectInputStream in = new ObjectInputStream(file);

            memory = (Memory)in.readObject();

            in.close();
            file.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
    }
    Memory getMemory()
    {
        return memory;
    }

}
