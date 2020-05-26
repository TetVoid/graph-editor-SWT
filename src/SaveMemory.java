import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveMemory {

    SaveMemory(Shell shell,Memory memory) {
        FileDialog dialog = new FileDialog(shell, SWT.SAVE);
        dialog.setFilterNames(new String[]{"SER file"});
        dialog.setFilterExtensions(new String[]{"*.ser"});
        dialog.open();

        try
        {
            FileOutputStream file = new FileOutputStream(dialog.getFileName());
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(memory);

            out.close();
            file.close();

            System.out.println("Object has been serialized");

        }

        catch(IOException ex)
        {

            System.out.println(ex.getMessage());
        }

    }
}
