package ie.atu.sw;

public class Runner {


    //Main method to run the program
    public static void main(String[] args) {
        //Create a new MenuHandler object
    	MenuHandler menuHandler = new MenuHandler();
        //call the various methods from the MenuHandler Class
        MenuHandler.printMenu();
        menuHandler.specifyOption();
        menuHandler.getSearchWord();
        menuHandler.closeScanner();
        //Create a new SearchFeatures object
        SearchFeatures searchFeatures = new SearchFeatures();
        //call the various methods from the SearchFeatures Class
        searchFeatures.getWordEmbeddings(searchFeatures.accessEmbeddings(menuHandler), searchFeatures.searchWord(menuHandler.getWords(), searchFeatures.accessSearchWord(menuHandler)));
        searchFeatures.searchSimilarWords(searchFeatures.accessWords(menuHandler), menuHandler);
    }   

}

