
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
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
	public static void processFiles(List<FileSource> fileSources){
		for (Iterator <FileSource> iterator = fileSources.iterator(); iterator.hasNext();) {
			FileSource fileSource = iterator.next();
			List<FileInformation> files = fileSource.getFiles();
			List<FileInformation> filesWithoutMatch = new ArrayList<FileInformation>();
			
			try{
				  // Create file for the report.
				  FileWriter fstream = new FileWriter(fileSource.getOutputFilepath()+"report");
				  BufferedWriter out = new BufferedWriter(fstream);
				  
				  out.write("Extraction report" + System.getProperty("line.separator"));
				  
				  List<RegExpDescritor> regExps = fileSource.getRegExp();
				  
				  
			for (Iterator <RegExpDescritor> regExpIterator = regExps.iterator(); regExpIterator
				.hasNext();) {
				
				String regularExpression = regExpIterator.next().getRegExp();
				
				out.write(" Using regexp: " + regularExpression  + System.getProperty("line.separator") );
				
				for (Iterator<FileInformation> fileInformationIterator = files.iterator(); fileInformationIterator.hasNext();) {
					FileInformation file = fileInformationIterator.next();
					
					String text = PDFReader.getPDFContent(fileSource.getInputFilepath().concat(file.getInputFilename()));
					
					
					if (processFile(text, 
							fileSource.getOutputFilepath().concat(file.getOutputFilename()),
							regularExpression)){
						out.write ("  " + file.getInputFilename() + ": " + System.getProperty("line.separator"));
					}else{
						out.write ("  " + file.getInputFilename() + ": no match found." + System.getProperty("line.separator") );
						filesWithoutMatch.add(file);
					}
				}
				
				files = filesWithoutMatch;
				filesWithoutMatch = new ArrayList<FileInformation>();
				
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


