
import java.util.Arrays;
/* 
 *  Program: Aplikacja dzia�aj�ca w oknie konsoli, kt�ra umo�liwia testowanie 
 *          operacji wykonywanych na obiektach klasy Car.
 *     Plik: CarConsole.java
 *           
 *           
 *    Autor: Adrian Winiarski
 *    Indeks:225948
 *    Data:  pazdziernik 2017 r.
 *     
 */
public class CarConsole {
	
	private static final String GREETING_MESSAGE = 
			"Program: Car - wersja konsolowa    \n" + 
	        "Autor: Adrian Winiarski           \n" + 
			"Indeks: 225948                    \n" +		
			"Data:  pa�dziernik 2017 r.        \n";

	private static final String MENU = 
			"    M E N U   G � � W N E         \n" +
			"1 - Wprowadz nowy samochod       \n" +
			"2 - Usu� samochod z bazy danych   \n" +
			"3 - Modyfikuj dane samochodu      \n" +
			"4 - Wczytaj dane samochodu z pliku\n" +
			"5 - Zapisz dane samochodu do pliku\n" +
			"0 - Zako�cz program\n";	
	
	private static final String CHANGE_MENU = 
			"   Co zmieni�?             \n" + 
	        "1 - Marka samochodu        \n" + 
			"2 - model samochodu        \n" + 
	        "3 - Rok produkcji          \n" + 
			"4 - Pojemnosc silnika      \n" +
	        "5 - Kolor samochodu        \n" +
	        "0 - Powr�t do menu g��wnego\n";
/*   
 *            ^   ^   ^   ^   ^   ^
 *           /|\ /|\ /|\ /|\ /|\ /|\
 *            |   |   |   |   |   |
 * Stworzenie greating massage, menu i change_menu 
 * aby w konsoli wypisywa�o nam podpowiedzi do 
 * czynno�ci ,kt�re mo�emy wykona�.
 * 
 * */
	

	private static ConsoleUserDialog UI = new ConsoleUserDialog();
/* 
 * ConsoleUserDialog to pomocnicza klasa zawieraj�ca zestaw
 * prostych metod do realizacji dialogu z u�ytkownikiem
 * w oknie konsoli tekstowej.
 * */
	
	public static void main(String[] args) {

		// Utworzenie obiektu aplikacji konsolowej
		CarConsole application = new CarConsole();
		// Uruchomienie g��wnej p�tli aplikacji.
		application.runMainLoop();
	
	}
	private Car currentCar = null;


	private void runMainLoop() {

		UI.printMessage(GREETING_MESSAGE);

		while (true) {
			UI.clearConsole();
			showCurrentCar();

			try {
				switch (UI.enterInt(MENU + "==>> ")) {
				case 1:
					// utworzenie nowego auta
					currentCar = createNewCar();
					break;
				case 2:
					// usuni�cie danych aktualnego auta.
					currentCar = null;
					UI.printInfoMessage("Dane aktualnego samochodu zosta�y usuni�te");
					break;
				case 3:
					// zmiana danych dla aktualnego auta
					if (currentCar == null) throw new CarException("W bazie danych nie zostal utworzony zaden samochod.");
					changeCarData(currentCar);
					break;
				case 4: {
					// odczyt danych z pliku tekstowego.
					String file_name = UI.enterString("Podaj nazw� pliku: ");
					currentCar = Car.readFromFile(file_name);
					UI.printInfoMessage("Dane samochodu zosta�y wczytane z pliku " + file_name);
				}
					break;
				case 5: {
					// zapis danych aktualnego auta do pliku tekstowego 
					String file_name = UI.enterString("Podaj nazw� pliku: ");
					Car.printToFile(file_name, currentCar);
					UI.printInfoMessage("Dane samochodu zosta�y zapisane do pliku " + file_name);
				}

					break; 
				case 0:
					// zako�czenie dzia�ania programu
					UI.printInfoMessage("\nProgram zako�czy� dzia�anie!");
					System.exit(0);
				} // koniec instrukcji switch
			} catch (CarException e) { 
				// Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy Car
				// gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci 
				// poszczeg�lnych atrybut�w.
				// Drukowanie komunikatu o b��dzie zg�oszonym za pomoc� wyj�tku CarException.
				UI.printErrorMessage(e.getMessage());
			}
		} // koniec p�tli while
	
		
	}

	/*
	 *  Metoda wy�wietla w oknie konsoli dane aktualnej osoby 
	 *  pami�tanej w zmiennej currentPerson.
	 */
	void showCurrentCar() {
		showCar(currentCar);
	} 

	
	/* 
	 * Metoda wy�wietla w oknie konsoli dane auta reprezentowanego
	 * przez obiekt klasy Car
	 */ 
	static void showCar(Car car) {
		StringBuilder sb = new StringBuilder();
		
		if (car != null) {
			sb.append("Aktualny samochod: \n");
			sb.append( "       Marka: " + car.getBrand() + "\n" );
			sb.append( "       Model: " + car.getModel() + "\n" );
			sb.append( "   Rok prod.: " + car.getYear() + "\n" );
			sb.append( "Poj. silnika: " + car.getCapacity() + "\n" );
			sb.append( "       Kolor: " + car.getColour() + "\n");
		} else
			sb.append( "Brak danych dotyczacych samochodu." + "\n" );
		UI.printMessage( sb.toString() );
	}

	
	/* 
	 * Meoda wczytuje w konsoli dane nowego auta, tworzy nowy obiekt
	 * klasy Car i wype�nia atrybuty wczytanymi danymi.
	 * Walidacja poprawno�ci danych odbywa si� w konstruktorze i setterach
	 * klasy Car. Je�li zostan� wykryte niepoprawne dane 
	 * to zostanie zg�oszony wyj�tek, kt�ry zawiera komunikat o b��dzie.
	 */
	static Car createNewCar(){
		String brand_ = UI.enterString("Podaj Marke: ");
		String model_ = UI.enterString("Podaj Model: ");
		String year_ = UI.enterString("Podaj rok prod.: ");
		String capacity_ = UI.enterString("Podaj pojemnosc silnika.: ");
		UI.printMessage("Mozliwe kolory:" + Arrays.deepToString(CarColour.values()));
		String colour_ = UI.enterString("Podaj kolor: ");
		Car car;
		try { 
			// Utworzenie nowego obiektu klasy Car oraz
			// ustawienie warto�ci wszystkich atrybut�w.
			car = new Car(brand_,model_);
			car.setYear(year_);
			car.setCapacity(capacity_);
			car.setColour(colour_);
		} catch (CarException e) {    
			// Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy Car
			// gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci 
			// poszczeg�lnych atrybut�w.
			// Drukowanie komunikatu o b��dzie zg�oszonym za pomoc� wyj�tku CarException.
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return car;
	}
	
	
	/* 
	 * Metoda pozwala wczyta� nowe dane dla poszczeg�lnych atrybut�w 
	 * obiekty Car i zmienia je poprzez wywo�anie odpowiednich setter�w z klasy Car.
	 * Walidacja poprawno�ci wczyranych danych odbywa si� w setterach
	 * klasy Car. Je�li zostan� wykryte niepoprawne dane 
	 * to zostanie zg�oszony wyj�tek, kt�ry zawiera komunikat o b��dzie.
	 */
	static void changeCarData(Car car)
	{
		while (true) {
			UI.clearConsole();
			showCar(car);

			try {		
				switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
				case 1:
					car.setBrand(UI.enterString("Podaj marka: "));
					break;
				case 2:
					car.setModel(UI.enterString("Podaj model: "));
					break;
				case 3:
					car.setYear(UI.enterString("Podaj rok prod.: "));
					break;
				case 4:
					car.setCapacity(UI.enterString("Podaj poj.: "));
					break;
				case 5:
					UI.printMessage("Mozliwe kolory:" + Arrays.deepToString(CarColour.values()));
					car.setColour(UI.enterString("Podaj kolor: "));
					break;
				case 0: return;
				}  // koniec instrukcji switch
			} catch (CarException e) {     
				// Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy Car
				// gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci 
				// poszczeg�lnych atrybut�w.
				// Drukowanie komunikatu o b��dzie zg�oszonym za pomoc� wyj�tku CarException.
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	
}  // end




