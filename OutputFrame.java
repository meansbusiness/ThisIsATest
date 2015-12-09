import java.awt.BorderLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class OutputFrame extends JFrame {
    
	BatchPanel batchPanel;
	SinglePanel singlePanel;
	
	public OutputFrame(){
    }
	
    public OutputFrame(boolean batchRun){
        super("Student Program Tester");
        
		getContentPane().setLayout(new BorderLayout());
        setSize(700,700);
        
		if(batchRun == true) {
			batchPanel = new BatchPanel();
			getContentPane().add(batchPanel, "Center");
		} else if(batchRun == false) {
			singlePanel = new SinglePanel();
			getContentPane().add(singlePanel, "Center");
		} else {
			System.out.println("Error deciding whether batch or single.");
		}
        
        setVisible(true);
    }
}
