import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CountriesApp {
	private static Path filePath = Paths.get("C:\\Users\\Michael\\eclipse-workspace\\Lab17\\src\\Countries");

	
//********* MAIN	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean c = true;
		System.out.println("Welcome to the Countries Maintenance Application!");
		do {
			System.out.println("1-See the list of countries\n2-Add a country\n3-Exit");
			int choice = input.nextInt();
			
			if (choice == 3) {
				break;
			} else if (choice == 1) {
				List<Country> countries = readFile();
				int i = 1; 
				for (Country country : countries) {
					System.out.println(i++ + ". " + country);
				}
			} else if (choice == 2) {
				Country country = getThingFromUser(input);
				System.out.println("Adding " + country);
				appendLineToFile(country);
			} else {
				System.out.println("Invalid choice.");
			}
			System.out.println("\n");
		}while(c);	
		System.out.println("Goodbye.");
		input.close();
	}

//FILE READER	
	public static List<Country> readFile() {
			List<String> lines;
			try {
				lines = Files.readAllLines(filePath);
				List<Country> countries = new ArrayList<>();
				for(String line : lines) {
					String[] parts = line.split("-");
					String name = parts[0];
					int population = Integer.parseInt(parts[1]);
					countries.add(new Country(name,population));		
				}
				return countries;
			} catch (IOException e) {
				System.out.println("Incorrect file location");
				e.printStackTrace();
			}
			return null;
	}

//FILE APPENDER	
	public static void appendLineToFile(Country country) {
		
		String line = country.getName() + "-" +  country.getPopulation();
		
		List<String> lines = Collections.singletonList(line);
		try {
			Files.write(filePath, lines, StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}

//PREMADE VALIDATOR 	
	private static Country getThingFromUser(Scanner input) {
		String name = getString(input, "Enter Country Name: ");
		
		int population = getInt(input, "Enter the population:  ");
		return new Country(name, population);
	}
	
	public static String getString(Scanner input, String prompt) {
		System.out.print(prompt);
		String word = input.next();
		return word;
	}
	
	public static int getInt(Scanner input, String prompt) {
		boolean inputIsInvalid = true;
		int ind = 0;
		do {
			System.out.print(prompt);
			if (input.hasNextInt()) {
				ind = input.nextInt();
				input.nextLine(); // <-- clear remainder of line.
				inputIsInvalid = false;
			} else {
				input.nextLine(); // <-- clear out bad input
				System.out.println("You must enter a valid integer. Try again");
			}
		} while (inputIsInvalid);
		return ind;
	}
}

