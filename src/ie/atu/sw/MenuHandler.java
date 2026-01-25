package ie.atu.sw;

import java.io.*;
import java.util.*;

//Handles menu-related functionalities for the program
public class MenuHandler {

	//Declare Private Member Variables
	private int option;
	private String[] words;
	private double[][] embeddings;
	private int index;
	private String embeddingFilePath;
	private String outputPath;
	private String searchWord;
	private Scanner scanner;
	private SearchFeatures searchFeatures;
	
	//Constructor to intialise the scanner object and searchFeatures object
	public MenuHandler() {
		// Initialize the scanner object
		scanner = new Scanner(System.in);
		searchFeatures = new SearchFeatures();
		

	}

	// prints menu user interface, colour, font etc comes from
	// ConsoleColour enum
	public static void printMenu() {
		System.out.println(ConsoleColour.PURPLE);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Similarity Search with Word Embeddings          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Embedding File");
		System.out.println("(2) Specify an Output File (default: ./out.txt)");
		System.out.println("(3) Enter a Word or Text");
		System.out.println("(4) Run Search");
		System.out.println("(5) Quit");
	} 

	// Prompts the user to select an option and handles the user input
	public void specifyOption() {
		// Prompt the user to select an option
		System.out.println("Please Select an Option: ");
		// Check if the next input is an integer
		if (scanner.hasNextInt()) {
			option = scanner.nextInt();
			// Consume the newline character to avoid issues with the next input
			scanner.nextLine();
			// Check if the option is within the valid range
			if (option >= 1 && option <= 5) {
				handleOption(option);
			} else {
				System.out.println("Invalid option. Please select a valid option.");
				specifyOption();
			}
		} else {
			System.out.println("Invalid input. Please enter a valid option number.");
			// Consume the invalid input to avoid issues with the next input
			scanner.nextLine();
			specifyOption();
		}
	}

	// Validates the inputs for options 1 - 3 before running the search
	public boolean validateInputs() {
		// Check if the necessary inputs are provided
		if (outputPath == null || outputPath.isEmpty()
				|| embeddingFilePath == null || embeddingFilePath.isEmpty()
				|| searchWord == null || searchWord.isEmpty()
				|| words == null || embeddings == null) {
			System.out.println("All appropriate inputs for options 1 - 3 must be inputted before selecting Option 4. Please select a new option.");
			// Prompt the user to select a new option
			specifyOption();
			return false;
		}
		return true;
	}
	
	// Handles users menu option choice based on user actions
	// @param option the user-selected menu option
	public void handleOption(int option) {

		switch (option) {
		case 1 -> specifyEmbeddingFile();
		case 2 -> specifyOutputFile();
		case 3 -> enterWordOrText();
		// Run the search if the necessary inputs are provided
		case 4 -> {
			// Check if the necessary inputs are provided
			if (!validateInputs()) {
				System.out.println("Please provide the necessary inputs before selecting Option 4.");
				break;
			} else{
				if (words != null) {
					searchFeatures.searchWord(words, searchWord);
				} else {
					System.out.println("Please load the embedding file before running the search.");
				}
			}
		}

		case 5 ->  System.exit(0);
		default -> System.out.println("Invalid option. Please try again.");
		}
	}

	// Prompts the user to specify the embedding file path
	public String specifyEmbeddingFile() {
		System.out.println("Please input the Embedding text file path: ");
		embeddingFilePath = scanner.nextLine();
		// Remove any leading and trailing whitespace and quotes
		embeddingFilePath = embeddingFilePath.trim().replaceAll("^\"(.*?)\"$", "$1");

		// check if file exists
		if (isValidFilePath(embeddingFilePath)) {
			// confirm the file path
			System.out.println("Embedding file path: " + embeddingFilePath);
			// load the file
			loadEmbeddingFile(embeddingFilePath);
		} else {
			System.out.println("Invalid file path. Please try again.");
		}
		
		specifyOption();
		return embeddingFilePath;
		
	}

	// Validates the file path provided by the user
	private boolean isValidFilePath(String embeddingFilePath) {
		File file = new File(embeddingFilePath);

		// Check if the file exists and is a file (not a directory)
		return file.exists() && file.isFile();
	}

	// Loads the embedding file and parses the data
	public void loadEmbeddingFile(String filePath) {
	    // create a File object based on the provided file path
	    File file = new File(filePath);

	    try {
	        Scanner scanner = new Scanner(file);
	        // getting the size of the arrays that are needed
	        int size = countLines(file);
	        words = new String[size];
	        embeddings = new double[size][];
	        index = 0; // Initialize index within the method scope to avoid potential issues
	        // Read Each line of the file
	        while (scanner.hasNextLine()) {
	            // Split the line into parts based on the whitespace
	            String line = scanner.nextLine();
	            String[] parts = line.split(", ");
				
	            // check if the line contains at least two parts
	            if (parts.length >= 2) {
	                // extract the word and embedding values
	                words[index] = parts[0];
	                // intialise the array to store the parsed doubles in
	                double[] embedding = new double[parts.length - 1];
	                // Parse each value in the embedding String as a double
	                for (int i = 1; i < parts.length; i++) {
	                    try {
	                        embedding[i - 1] = Double.parseDouble(parts[i]);
	                    } catch (NumberFormatException ex) {
	                        System.err.println("Error parsing double value at line " + (index + 1) + " for value: " + parts[i]);
	                        // Handle the error as needed
	                        // Set the problematic value to 0.0 or skip this embedding
	                        // embedding[i - 1] = 0.0;
	                    }
	                }
	                // Storing the embedding values in the appropriate array
	                embeddings[index] = embedding;
	                index++;
	            }
	        }
	        System.out.println("Embedding data loaded successfully.");
	    } catch (FileNotFoundException e) {
	        // Handle file not found exception
	        System.err.println("File not found: " + filePath);
	    }
	}
	
	// Count the number of lines in the file
	private int countLines(File file) throws FileNotFoundException {
	    int lines = 0;
	    Scanner scanner = new Scanner(file);
	    while (scanner.hasNextLine()) {
	        lines++;
	        scanner.nextLine();
	    }
	    return lines;
	}

	// Prompts the user to specify the output file path
	public void specifyOutputFile() {
		System.out.println("Please enter the output file path:");
		outputPath = scanner.nextLine();
		// Remove any leading and trailing whitespace and quotes
		outputPath = outputPath.trim().replaceAll("^\"(.*?)\"$", "$1");
		// Check if the output path is empty
		if (outputPath.isEmpty()) {
			System.err.println("Error: Output file path cannot be empty.");
			//prompt the user to make a new selection
			specifyOutputFile();
			return; 
		}
		 try {
			// Create the output file if it does not exist
	            File file = new File(outputPath);
				//check if the file is created
	            if (file.createNewFile()) {
	                System.out.println("Output file created: " + file.getName());
	            } else {
	                System.out.println("Output file already exists.");
	            }
			//Handle ay IO exceptions during file creation
	        } catch (IOException e) {
	            System.err.println("An error occurred while creating the output file: " + e.getMessage());
	        }
		 
	    System.out.println("Output path set to: " + outputPath);
		specifyOption();
	}

	// Prompts the user to enter a word for the similarity search
	public String enterWordOrText() {
		System.out.println("Please enter the word you wish to use for the Similarity Search");
		searchWord = scanner.nextLine();
		//return whether the search word is valid or invalid
		if (isValid(searchWord)) {
			System.out.println("The word entered is " + searchWord + " and is a valid selection");
		}else {
			System.out.println("The word entered is " + searchWord + " and is not a valid selection");
		}
		specifyOption();
		return searchWord;
		}
	// Check if the word is in the loaded words
	public boolean isValid(String word) {
		for (String loadedWord : words) {
	        if (loadedWord.equalsIgnoreCase(word)) {
	            return true; // Word found in the loaded words
	        }
	    }
	    return false; // Word not found in the loaded words
	}

	// Getters for the private member variables
	public String getSearchWord() {
		return searchWord;
	}

	public String[] getWords() {
		return words;
	}

	public double[][] getEmbeddings() {
		return embeddings;
	}

	public String getOutputPath() {
		return outputPath;
	}

	//close the scanner object
	public void closeScanner() {
		scanner.close();
	}
}
