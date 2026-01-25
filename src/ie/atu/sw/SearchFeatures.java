package ie.atu.sw;

import java.io.*;
import java.text.*;

//Handles search-related functionalities for the program
public class SearchFeatures {

    //Declare Private Member Variables
    String[] words;
    double[][] embeddings;
    int position;
    String searchWord;

    //Constructors to initialise the member variables
    public String accessSearchWord(MenuHandler menuHandler) {
        searchWord = menuHandler.getSearchWord();
        return searchWord;    
    }

    public String[] accessWords(MenuHandler menuHandler) {
        words = menuHandler.getWords();
        return words;
    }

    public double[][] accessEmbeddings(MenuHandler menuHandler) {
        embeddings = menuHandler.getEmbeddings();
        return embeddings;
    }
    
    //Searches for the user input word in the list of words and returns its position
    public int searchWord(String[] words, String searchWord) {
        position = -1; // Set the initial position to -1
        // Loop through the list of words to find the search word
        for (int i = 0; i < words.length; i++) {
            if (words[i].equalsIgnoreCase(searchWord)) {
                position = i; // Set the position if the word be found
                break; // Exit the loop once the word be found
            }
        }
        //exception handling in case word is not found in list
        if (position == -1) {
            System.out.println("The search word '" + searchWord + "' was not found in the list.");
        }       
        return position;
    }

    //Returns the word embeddings of the user input word at the specified position
    public double[] getWordEmbeddings(double[][] embeddings, int position) {
        //code that takes in the int i value of the word and finds the double[] of the word
        double[] wordEmbeddings = embeddings[position];
        return wordEmbeddings;
    }
    
    //Calculates the dot product of two vectors
    public double calculateDotProduct(double[] array1, double[] array2) {
       //exception handling in case the arrays are not the same length
        if (array1.length != array2.length) {
            throw new IllegalArgumentException("Arrays must have the same length for dot product calculation.");
        }
        double dotProduct = 0.0;
        // Loop through the arrays to calculate the dot product
        for (int i = 0; i < array1.length; i++) {
            dotProduct += array1[i] * array2[i];
        }
        
        return dotProduct;
    }
    
    //Calculates the norm of a vector
    public double calculateNorm(double[] vector) {
        double norm = 0.0;
        // Loop through the vector to calculate the norm
        for (double num : vector) {
            norm += num * num;
        }
        
        return Math.sqrt(norm);
    }

    //Calculates the cosine similarity between two vectors
    public double calculateCosineSimilarities(double[] vector1, double[] vector2) {
        //declare local variables
        double dotProduct = calculateDotProduct(vector1, vector2);
        double normVector1 = calculateNorm(vector1);
        double normVector2 = calculateNorm(vector2);
        //exception handling in case the norm of the vectors is 0
        if (normVector1 == 0 || normVector2 == 0) {
            return 0.0; // Return 0 if any vector has zero norm
        }

        return dotProduct / (normVector1 * normVector2);
    }
    //Searches for words similar to the user input word and writes the results to a file
     public void searchSimilarWords(String[] strings, MenuHandler menuHandler) {
        //Declare local variables
        StringBuilder resultBuilder = new StringBuilder();
        int searchWordPosition = searchWord(words, searchWord);
        String outputPath = menuHandler.getOutputPath();
        double[] topSimilarities = new double[10];
        String[] topSimilarWords = new String[10];
        //exception handling in case the search word is not found
        if (searchWordPosition != -1) {
            double[] searchWordEmbeddings = getWordEmbeddings(embeddings, searchWordPosition);
            // Loop through the list of words to find similar words
            for (int i = 0; i < words.length; i++) {
                // Skip the exact search word
                if (i != searchWordPosition) {
                    double[] wordEmbeddings = getWordEmbeddings(embeddings, i);
                    double cosineSimilarity = calculateCosineSimilarities(searchWordEmbeddings, wordEmbeddings);
                    // Round the cosine similarity to 4 decimal places
                    DecimalFormat df = new DecimalFormat("#.####");
                    double roundedCosineSimilarity = Double.parseDouble(df.format(cosineSimilarity));

                    // Check if the cosine similarity is above 0.6
                    if (roundedCosineSimilarity > 0.6) { 
                        // Update the top 10 similar words and their cosine similarities    
                        for (int j = 0; j < 10; j++) {
                            if (topSimilarities[j] < roundedCosineSimilarity) {
                                for (int k = 9; k > j; k--) {
                                    topSimilarities[k] = topSimilarities[k - 1];
                                    topSimilarWords[k] = topSimilarWords[k - 1];
                                }
                                topSimilarities[j] = roundedCosineSimilarity;
                                topSimilarWords[j] = words[i];
                                break;
                            }
                        }
                    }
                }
            }
            // Append the results to the result builder        
            for (int m = 0; m < 10; m++) {
                String result = "Word similar to '" + searchWord + "' =  " + topSimilarWords[m] + " (Cosine Similarity: " + topSimilarities[m] + ")";
                resultBuilder.append(result).append("\n");
            }
        }
        // Write the results to the output file
        writeResultsToFile(outputPath, resultBuilder.toString());
		System.out.println("Search complete. Please open the " + outputPath + " to view the results.");
    }

    //Writes the search results to a file
	public void writeResultsToFile(String outputPath, String results) {
        //declares a new file object
        File file = new File(outputPath);

        // Check if the file exists and is not empty
       if (file.exists() && file.length() > 0) {
        // Clear the contents of the file if not empty
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath, false))) {
            writer.print(""); // Clear the contents of the file
            System.out.println("Previous results deleted. File is now empty.");
        } //exception handling in case the file cannot be deleted
        catch (IOException e) {
            System.err.println("An error occurred while deleting the previous results: " + e.getMessage());
        }
    }
    
    // Write the new results to the file
    try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath, true))) {
        writer.println(results);
        System.out.println("Results written to the output file successfully.");
    }//exception handling in case the file cannot be written to 
    catch (IOException e) {
        System.err.println("An error occurred while writing to the output file: " + e.getMessage());
    }
}

}
