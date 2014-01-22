import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hohankitivan on 1/22/14.
 */
public class SSEFView extends JPanel implements ActionListener {
    private SSEFInterface si;

    private JPanel SSEFPanel;
    private JPanel WeatherPanel;
    private JPanel StocksPanel;

    private JButton uploadButton;
    private JTextField codeField;

    public SSEFView(SSEFInterface si){
        this.si = si;
        constructGUI();
    }

    public void constructGUI(){
        SSEFPanel = new JPanel();

        uploadButton = new JButton("Upload");
        uploadButton.addActionListener(this);
        codeField = new JTextField("SSEF Code");

        //size setting
        SSEFPanel.setLayout(new FlowLayout());
        codeField.setPreferredSize(new Dimension(200, 30));

        SSEFPanel.add(codeField);
        SSEFPanel.add(uploadButton);

        this.add(SSEFPanel);
    }

    public SSEFView(){
        this(new SSEFInterface());
    }

    public void actionPerformed(ActionEvent ae){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF only", "pdf");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            si.uploadFile(codeField.getText(), chooser.getSelectedFile().getName());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SSEF");
        frame.add(new SSEFView());
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
