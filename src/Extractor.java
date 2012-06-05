
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Extractor {

	private static final int timeout = 10000;
	
	/*
	 * Looks in file for the regular expression specified in pattern and writes
	 * the output in outputFilePath+pattern.
	 * 
	 * iFile: the contents of the file to be processed.
	 * oFileName: the name (including filepath) of the output. 
	 * regExp: the regular expression to process the file. 
	 * 
	 * Return a boolean that specifies if a match was found or not. 
	 */
	public static boolean processFile(String iFile, String oFileName,String regExp){
		
		boolean foundMatch = true;
		
		Pattern pattern = Pattern.compile(
	               regExp,
	                Pattern.DOTALL
	            );
		
		MatcherWithTimeout match = new MatcherWithTimeout(iFile,pattern);
		
		String result = null;
		
		try {
			TimeoutController.execute(match, timeout);
			result = match.getResult();
		} catch (TimeoutException e1) {
			result = null;
			
		}
		
		
		if (result == null){
			System.out.println("File: " + oFileName + " has no match." );
			result = iFile;
			foundMatch = false;
		}
		
		try{
			  // Create file 
			  FileWriter fstream = new FileWriter(oFileName);
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(result);
			  //Close the output stream
			  out.close();
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error writing to file: " + e.getMessage());
			  
		}
		return foundMatch;
	}
	
	/*
	 * Iterates through a list of file to process each file.
	 * 
	 * files: List of files 
	 */
	public static void processFiles(List<FileSource> files){
		for (Iterator <FileSource> iterator = files.iterator(); iterator.hasNext();) {
			FileSource fileSource = iterator.next();
			List<FileInformation> fileNames = fileSource.getFiles();
		
			try{
				  // Create file for the report.
				  FileWriter fstream = new FileWriter(fileSource.getOutputFilepath()+"report");
				  BufferedWriter out = new BufferedWriter(fstream);
				  
				  out.write("Extraction report" + System.getProperty("line.separator"));
				  out.write(" Using regexp: " + fileSource.getRegExp() + System.getProperty("line.separator") );
				  
			
			for (Iterator<FileInformation> iterator2 = fileNames.iterator(); iterator2.hasNext();) {
				FileInformation file = iterator2.next();
				
				String text = PDFReader.getPDFContent(fileSource.getInputFilepath().concat(file.getInputFilename()));
				
				if (processFile(text, 
						fileSource.getOutputFilepath().concat(file.getOutputFilename()),
						fileSource.getRegExp())){
					out.write ("  " + file.getInputFilename() + ": " + System.getProperty("line.separator"));
				}else{
					out.write ("  " + file.getInputFilename() + ": no match found." + System.getProperty("line.separator") );
				}
			}
			
			//Close the output stream
				out.close();
				
			}catch (Exception e){//Catch exception if any
				  System.err.println("Error writing to file: " + e.getMessage());
				  return;
			}
			
			
		}
	}
}


