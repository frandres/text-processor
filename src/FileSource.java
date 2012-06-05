import java.util.List;


public class FileSource {

	private String inputFilePath;
	private List <FileInformation> files;
	private String regExp;
	private String outputFilepath;
	
	public FileSource(String inputFilePath, List<FileInformation> files,
			String outputFilepath, String regExp) {
		super();
		this.inputFilePath = inputFilePath;
		this.files = files;
		this.outputFilepath = outputFilepath;
		this.regExp = regExp;
	}

	public String getRegExp() {
		return regExp;
	}

	public String getOutputFilepath() {
		return outputFilepath;
	}
	
	public String getInputFilepath() {
		return inputFilePath;
	}

	public List<FileInformation> getFiles() {
		return files;
	}
	
}
