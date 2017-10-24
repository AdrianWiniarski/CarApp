
import java.util.Arrays;
/* 
 *  Program: Aplikacja dzia³aj¹ca w oknie konsoli, która umo¿liwia testowanie 
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
			"Data:  paŸdziernik 2017 r.        \n";

	private static final String MENU = 
			"    M E N U   G £ Ó W N E         \n" +
			"1 - Wprowadz nowy samochod       \n" +
			"2 - Usuñ samochod z bazy danych   \n" +
			"3 - Modyfikuj dane samochodu      \n" +
			"4 - Wczytaj dane samochodu z pliku\n" +
			"5 - Zapisz dane samochodu do pliku\n" +
			"0 - Zakoñcz program\n";	
	
	private static final String CHANGE_MENU = 
			"   Co zmieniæ?             \n" + 
	        "1 - Marka samochodu        \n" + 
			"2 - model samochodu        \n" + 
	        "3 - Rok produkcji          \n" + 
			"4 - Pojemnosc silnika      \n" +
	        "5 - Kolor samochodu        \n" +
	        "0 - Powrót do menu g³ównego\n";
/*   
 *            ^   ^   ^   ^   ^   ^
 *           /|\ /|\ /|\ /|\ /|\ /|\
 *            |   |   |   |   |   |
 * Stworzenie greating massage, menu i change_menu 
 * aby w konsoli wypisywa³o nam podpowiedzi do 
 * czynnoœci ,które mo¿emy wykonaæ.
 * 
 * */
	

	private static ConsoleUserDialog UI = new ConsoleUserDialog();
/* 
 * ConsoleUserDialog to pomocnicza klasa zawieraj¹ca zestaw
 * prostych metod do realizacji dialogu z u¿ytkownikiem
 * w oknie konsoli tekstowej.
 * */
	
	public static void main(String[] args) {

		// Utworzenie obiektu aplikacji konsolowej
		CarConsole application = new CarConsole();
		// Uruchomienie g³ównej pêtli aplikacji.
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
					// usuniêcie danych aktualnego auta.
					currentCar = null;
					UI.printInfoMessage("Dane aktualnego samochodu zosta³y usuniête");
					break;
				case 3:
					// zmiana danych dla aktualnego auta
					if (currentCar == null) throw new CarException("W bazie danych nie zostal utworzony zaden samochod.");
					changeCarData(currentCar);
					break;
				case 4: {
					// odczyt danych z pliku tekstowego.
					String file_name = UI.enterString("Podaj nazwê pliku: ");
					currentCar = Car.readFromFile(file_name);
					UI.printInfoMessage("Dane samochodu zosta³y wczytane z pliku " + file_name);
				}
					break;
				case 5: {
					// zapis danych aktualnego auta do pliku tekstowego 
					String file_name = UI.enterString("Podaj nazwê pliku: ");
					Car.printToFile(file_name, currentCar);
					UI.printInfoMessage("Dane samochodu zosta³y zapisane do pliku " + file_name);
				}

					break; 
				case 0:
					// zakoñczenie dzia³ania programu
					UI.printInfoMessage("\nProgram zakoñczy³ dzia³anie!");
					System.exit(0);
				} // koniec instrukcji switch
			} catch (CarException e) { 
				// Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Car
				// gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci 
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o b³êdzie zg³oszonym za pomoc¹ wyj¹tku CarException.
				UI.printErrorMessage(e.getMessage());
			}
		} // koniec pêtli while
	
		
	}

	/*
	 *  Metoda wyœwietla w oknie konsoli dane aktualnej osoby 
	 *  pamiêtanej w zmiennej currentPerson.
	 */
	void showCurrentCar() {
		showCar(currentCar);
	} 

	
	/* 
	 * Metoda wyœwietla w oknie konsoli dane auta reprezentowanego
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
	 * klasy Car i wype³nia atrybuty wczytanymi danymi.
	 * Walidacja poprawnoœci danych odbywa siê w konstruktorze i setterach
	 * klasy Car. Jeœli zostan¹ wykryte niepoprawne dane 
	 * to zostanie zg³oszony wyj¹tek, który zawiera komunikat o b³êdzie.
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
			// ustawienie wartoœci wszystkich atrybutów.
			car = new Car(brand_,model_);
			car.setYear(year_);
			car.setCapacity(capacity_);
			car.setColour(colour_);
		} catch (CarException e) {    
			// Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Car
			// gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci 
			// poszczególnych atrybutów.
			// Drukowanie komunikatu o b³êdzie zg³oszonym za pomoc¹ wyj¹tku CarException.
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return car;
	}
	
	
	/* 
	 * Metoda pozwala wczytaæ nowe dane dla poszczególnych atrybutów 
	 * obiekty Car i zmienia je poprzez wywo³anie odpowiednich setterów z klasy Car.
	 * Walidacja poprawnoœci wczyranych danych odbywa siê w setterach
	 * klasy Car. Jeœli zostan¹ wykryte niepoprawne dane 
	 * to zostanie zg³oszony wyj¹tek, który zawiera komunikat o b³êdzie.
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
				// Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Car
				// gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci 
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o b³êdzie zg³oszonym za pomoc¹ wyj¹tku CarException.
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	
}  // end




