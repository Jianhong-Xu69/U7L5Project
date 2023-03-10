
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName) {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        sortResults(movies); // little redundancy never hurt anyone
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void menu() {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q")) {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option) {
        if (option.equals("t")) {
            searchTitles();
        } else if (option.equals("c")) {
            searchCast();
        } else if (option.equals("k")) {
            searchKeywords();
        } else if (option.equals("g")) {
            listGenres();
        } else if (option.equals("r")) {
            listHighestRated();
        } else if (option.equals("h")) {
            listHighestRevenue();
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles() {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String movieTitle = movie.getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movie);
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortString(ArrayList<String> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            String temp = listToSort.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie) {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {
        System.out.print("Enter a cast search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> actors = new ArrayList<String>();

        // search through ALL movies in collection
        for (Movie value : movies) {
            String movieCast = value.getCast();
            movieCast = movieCast.toLowerCase();

            if (movieCast.contains(searchTerm)) {
                //add the Casts to the results list
                String[] temp = movieCast.split("\\|");
                String[] temp2 = value.getCast().split("\\|");
                for (int j = 0; j < temp.length; j++) {
                    if (temp[j].contains(searchTerm) && !actors.contains(temp2[j])) {
                        actors.add(temp2[j]);
                    }
                }
            }
        }

        // sort the results
        sortString(actors);

        //display to user
        for (int i = 0; i < actors.size(); i++) {
            String actor = actors.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + actor);
        }

        System.out.println("Which actor would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        searchTerm = actors.get(choice - 1).toLowerCase();

        for (Movie movie : movies) {
            String movieCast = movie.getCast();
            movieCast = movieCast.toLowerCase();

            if (movieCast.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movie);
            }
        }

        // sort the results by title
        sortResults(results);

        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords() {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String movieKeyword = movie.getKeywords();
            movieKeyword = movieKeyword.toLowerCase();

            if (movieKeyword.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movie);
            }
        }
        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres() {
        // search through ALL movies in collection
        ArrayList<String> genres = new ArrayList<String>();
        for (Movie movie : movies) {
            //add the Casts to the results list
            String[] temp = movie.getGenres().split("\\|");
            for (String s : temp) {
                if (!genres.contains(s)) {
                    genres.add(s);
                }
            }
        }

        // sort the results
        sortString(genres);

        for (int i = 0; i < genres.size(); i++) {
            String genre = genres.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + genre);
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String searchTerm = genres.get(choice - 1).toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (Movie movie : movies) {
            String movieGenres = movie.getGenres();
            movieGenres = movieGenres.toLowerCase();

            if (movieGenres.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movie);
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated() {
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int j = 0; j < 50; j++) {
            Movie highest = new Movie("placeholder", "placeholder", "placeholder", "placeholder", "placeholder", "placeholder", 0, "genre", 0.0, 0, 0);
            for (Movie temp : movies) {
                if (temp.getUserRating() > highest.getUserRating() && !results.contains(temp)) {
                    highest = temp;
                }
            }
            results.add(highest);
        }

        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            double rating = results.get(i).getUserRating();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + " (Rating: " + rating + ")");
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue() {
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int j = 0; j < 50; j++) {
            Movie highest = new Movie("placeholder", "placeholder", "placeholder", "placeholder", "placeholder", "placeholder", 0, "genre", 0.0, 0, 0);
            for (Movie temp : movies) {
                if (temp.getRevenue() > highest.getRevenue() && !results.contains(temp)) {
                    highest = temp;
                }
            }
            results.add(highest);
        }

        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            int revenue = results.get(i).getRevenue();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + " (Revenue: $" + revenue + ")");
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null) {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        } catch (IOException exception) {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}