import java.util.*;
import java.io.*;

public class ConfigurationItems {
	
	public void setTestInputs(String inputs) {
		inputs = inputs.replace(" ", "");
		if(inputs.equals("")) {
			inputs = "None,None,None,None,None";
		}
		try{
			List<String> inputsList = Arrays.asList(inputs.split(","));
			File inputsFile = 
				new File("C:\\java\\src\\program-test-242-1\\src\\TestInput.txt");
			PrintStream fileStream = new PrintStream(inputsFile);
			for(Iterator<String> i = inputsList.iterator(); i.hasNext(); ) {
				String input = i.next();
				fileStream.println(input);
			}
			fileStream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setArgs(String argText){
        
		String arguments = argText;
        String[] separatedString = new String[100];
        
		if(!argText.equals("")) {
			try{
				File argFile;
				PrintStream writer;
				argFile = new File("src\\args.txt");
				writer = new PrintStream(argFile);
				
				int separationStart = 0;
				int[] separationEnd = new int[100];
				int argNum = 0;
				int commaCheck;
				do{
					separationEnd[argNum] = arguments.indexOf(", ", separationStart);
					
					separationStart = separationEnd[argNum] + 1;
					
					argNum++;
					commaCheck = argNum - 1;
				}while(separationEnd[commaCheck] != -1);
				
				for(int i = 0; i < argNum; i++){
					if(separationEnd[i] == -1){
						separationEnd[i] = arguments.length();
					}
				
					separatedString[i] = arguments.substring(separationStart, separationEnd[i]);
					writer.println(separatedString[i]);
					separationStart = separationEnd[i] + 2;
				}
				
				writer.close();
			}catch(IOException e){
				
			}
		}
    }
}