//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//public class MatcherWithTimeout extends Thread {
//
//	private String oFileName;
//	private String input;
//	private String regExp;
//	private boolean foundMatch;
//	
//	public MatcherWithTimeout(String input, String oFileName, String regExp) {
//		super();
//		this.input = input;
//		this.oFileName = oFileName;
//		this.regExp = regExp;
//		foundMatch = true;
//	}
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		super.run();
//		
//		Pattern pattern = Pattern.compile(
//	               regExp,
//	                Pattern.DOTALL
//	            );
//		
//		String result = null;
//		
//		Matcher matcher = pattern.matcher(input);
//		if(!matcher.find()){
//			return;
//		} else {
//			try{
//				result = matcher.group(1);
//			}catch (IllegalStateException e) {
//			}
//			
//		}
//		
//		if (result == null){
//			foundMatch = false;
//			result = input;
//		}
//		
//		try{
//			  // Create file 
//			  FileWriter fstream = new FileWriter(oFileName);
//			  BufferedWriter out = new BufferedWriter(fstream);
//			  out.write(result);
//			  //Close the output stream
//			  out.close();
//		}catch (Exception e){//Catch exception if any
//			  return;
//		}
//		
//	}
//
//	public void foundMatch() {
//		if (foundMatch){
//			return;}
//		else{
//			System.out.println("File: " + oFileName + " has no match." );
//		}
//			
//		
//	}
//	
//}
