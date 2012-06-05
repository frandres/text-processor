import java.util.List;

public class TextProcessor {
	 
	public static void main(String[] args) {
		 if(args.length < 1) {
	        	System.out.println("usage: java TextProcessor <files>");
	        	System.exit(0);
	        }
		 
		 XMLReader xmlReader = new XMLReader(args[0]);		 
		 List<FileSource> fSources = xmlReader.getFSources();
		 
		 Extractor.processFiles(fSources);
		 
	
	}
	

	/*
	private static List<String> obtainFilenames2(String filename){
		
		ArrayList<String> files = new ArrayList<String>();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		String file;
		
		try{ 
			file = br.readLine();
			while (file!= null) {
				files.add(file);
				file = br.readLine();
			}
		} 
		catch (IOException ioe){ 
			 ioe.printStackTrace();
		}
			
		return files;
		
	}*/
	
}
