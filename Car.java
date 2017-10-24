import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
/* 
 *  Program: Operacje na obiektach klasy Car
 *     Plik: Car.java
 *           definicja typu wyliczeniowego CarColour
 *           definicja klasy CarException(wyj�tki)
 *           definicja publicznej klasy Car(set'y i get'y)
 *           
 *    Autor: Adrian Winiarski
 *    Indeks:225948
 *    Data:  pazdziernik 2017 r.
 *     
 */

enum CarColour {             //typ wyliczeniowy
	UNKNOWN("-------"), 
	RED("Czerwony"), 
	BLACK("Czarny"), 
	GRAY("Szary"), 
	WHITE("Bia�y"), 
	GREEN("Zielony"),
	BOWN("Br�zowy"),
	GOLD("Z�oty"),
	VIOLET("Fioletowy"),
	SILVER("Srebrny"),
	YELLOW("��ty");
	

	String carColour;

	private CarColour(String car_colour) {
		carColour = car_colour;
	}

	
	@Override
	public String toString() {
		return carColour;
	}
	
}//end 

/*
  Klasa CarException jest klas� wyj�tk�w dedykowan� do zg�aszania b��d�w wyst�puj�cych przy operacjach na obiektach klasy Car.
 */
class CarException extends Exception {

	private static final long serialVersionUID = 1L;

	public CarException(String message) {
		super(message);
	}
}//end
	
/* Klasa Car reprezentuje samochody, kt�re s� opisane za pomoc�
 * pi�ciu atrybutow: color auta, marka auta, model auta, rok produkcji, pojemnosc silnika.
 * W klasie przyj�to ograniczenia:
 *    pola brand i model musz� zawiera� niepusty ci�g znak�w
 *    pole year musi by� z przedia�u <1800,2020> lub jak w pana programie 0 oznacza brak danych-
 *        -na temat roku produkcji
 *    pole carColour jest typem wyliczeniowym i kolor mozna wybrac z listy albo unknown co-
 *        -oznacza �e brak informacji na temat koloru
 *        
 *        #Powy�sze ograniczenia s� kontrolowane i w przypadku pr�by nadania
 *         niedozwolonej warto�ci, kt�remu� z atrybut�w jest zg�aszany wyj�tek
 *         zawieraj�cy stosowny komunikat.#
 * */
public class Car {
	private CarColour colour;
	private String brand;
	private String model;
	private int year;
	private double capacity;

	public Car(String brand_, String model_) throws CarException {
		setBrand(brand_);
		setModel(model_);
		colour = CarColour.UNKNOWN;
	}//konstruktor klasy Car
	
	/*---------------------------------------------------------------------------------*/
	public String getModel()
	{return model;}
	
	public String getBrand()
	{return brand;}
	
	public int getYear()
	{return year;}
	
	public CarColour getColour()
	{return colour;}
	
	public double getCapacity()
	{return capacity;}
	/*     ^   ^   ^
	 *    /|\ /|\ /|\
	 *     |   |   |
	 * "get'y" Zwracaj� poszczeg�lne warto�ci zale�nie od atrybutu.
	 * */
	
	/*---------------------------------------------------------------------------------*/
	
	public void setModel(String model_) throws CarException{
		if((model_==null)|| model_.equals(""))// "==" - por�wnuje nam referencje nie porownuje zawartosci obiektow tylko sprawdza czy obiekt istnieje
	throw new CarException("Pole <Model> nie mo�e by� polem pustym.");//".equals()" - por�wnuje �a�cuchy
		this.model=model_;
	}

	public void setBrand(String brand_) throws CarException {
		if((brand_==null)||brand_.equals(""))
		throw new CarException("Pole <Marka> nie mo�e by� polem pustym.");
	this.brand=brand_;
		
	}
	
	public void setYear(int year_) throws CarException {
		if((year_!=0)&&(year_<1800||year_>2020))
			throw new CarException("Rok samochodu musi by� pomi�dzy 1800 rokiem a 2020.");
		this.year=year_;
	}
	
	public void setYear(String year_)throws CarException {
	if((year_==null)||(year_.equals("")))
	{
		setYear(0);
		return;
	}
	try {
		setYear(Integer.parseInt(year_));
	}
	catch(NumberFormatException e){
		throw new CarException("Rok samochodu musi by� ca�kowity");
	}
	}

	public void setCapacity(double capacity_) throws CarException {
		if((capacity_!=0)&&(capacity_<0.1||capacity_>10.5))
			throw new CarException("Pojemnosc silnika musi znajdowac sie w pradziale od 0.1 m^3 do 10.5 m^3");
		this.capacity=capacity_;
		
	}
	public void setCapacity(String capacity_)throws CarException {
	if((capacity_==null)||(capacity_.equals("")))
	{
		setCapacity(0);
		return;
	}
	try {
		setCapacity(Double.parseDouble(capacity_));
	}
	catch(NumberFormatException e){
		throw new CarException("U�yj kropki '.' a nie przecinka ',' lub nie u�ywaj liter.");
	}
	}
	public void setColour(String txt) throws CarException {
		if (txt == null || txt.equals("")) {  // pusty �a�cuch znak�w oznacza stanowisko niezdefiniowane
			this.colour = CarColour.UNKNOWN;
			return;
		}
		for(CarColour coloure : CarColour.values()){
			if (coloure.carColour.equals(txt)) {
				this.colour = coloure;
				return;
			}
		}
		throw new CarException("Nie ma takiego koloru.");
}
	
	public void setColour(CarColour coloure){
		this.colour = coloure;
	}
	
	/*     ^   ^   ^
	 *    /|\ /|\ /|\
	 *     |   |   |
	 * "set'y" pobieraj� warto�ci i sprawdzaj� ich poprawno�� w razie "problem�w" 
	 * wyrzucaj� wyj�tek CarException zmodyfikowany odpowiednio do poszceg�lnych 
	 * "set'�w"   
	 * */
	
	/*---------------------------------------------------------------------------------*/
	
	
	public static void printToFile(PrintWriter writer, Car car){
		writer.println(car.brand + "#" + car.model + 
				"#" + car.year + "#" + car.capacity+ "#"+car.colour);
	}
		
	
	public static void printToFile(String file_name, Car car) throws CarException {
		try (PrintWriter writer = new PrintWriter(file_name+ ".txt")) {
			printToFile(writer, car);
		} catch (FileNotFoundException e){
			throw new CarException("Nie odnaleziono pliku " + file_name);
		}
	}
	
	
	public static Car readFromFile(BufferedReader reader) throws CarException{
		try {
			String line = reader.readLine();
			String[] txt = line.split("#");
			Car car = new Car(txt[0], txt[1]);
			car.setYear(txt[2]);
			car.setCapacity(txt[3]);
			car.setColour(txt[4]);	
			return car;
		} catch(IOException e){
			throw new CarException("Wyst�pi� b��d podczas odczytu danych z pliku.");
		}	
	}
	
	
	public static Car readFromFile(String file_name) throws CarException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name + ".txt")))) {
			return Car.readFromFile(reader);
		} catch (FileNotFoundException e){
			throw new CarException("Nie odnaleziono pliku " + file_name);
		} catch(IOException e){
			throw new CarException("Wyst�pi� b��d podczas odczytu danych z pliku.");
		}	
	}
	
}
