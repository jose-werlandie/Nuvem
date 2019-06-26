package nuvem_de_bolso.client;

/**
 *
 * @author lucas
 */
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class Renderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        //ASSIGN TO VALUE THAT IS PASSED
        ImgsNText is = (ImgsNText) value;

        setText(is.getName());
        setIcon(is.getImg());

        //SET BACKGROUND AND FOREGROUND COLORS TO CUSTOM LIST ROW
        if(isSelected)
        {
            setBackground(list.getSelectionBackground());
         setForeground(list.getSelectionForeground());
        }else
        {

            setBackground(list.getBackground());
         setForeground(list.getForeground());
        }

        setEnabled(true);
        setFont(list.getFont());

         return this;
    }
}