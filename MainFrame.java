import java.awt.BorderLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        
        super("Student Program Tester");
        
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,700);
        
        MainPanel mainPanel = new MainPanel();
        getContentPane().add(mainPanel, "Center");
        
        setVisible(true);
    }
    
}