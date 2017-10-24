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
 *           definicja klasy CarException(wyj¹tki)
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
	WHITE("Bia³y"), 
	GREEN("Zielony"),
	BOWN("Br¹zowy"),
	GOLD("Z³oty"),
	VIOLET("Fioletowy"),
	SILVER("Srebrny"),
	YELLOW("¯ó³ty");
	

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
  Klasa CarException jest klas¹ wyj¹tków dedykowan¹ do zg³aszania b³êdów wystêpuj¹cych przy operacjach na obiektach klasy Car.
 */
class CarException extends Exception {

	private static final long serialVersionUID = 1L;

	public CarException(String message) {
		super(message);
	}
}//end
	
/* Klasa Car reprezentuje samochody, które s¹ opisane za pomoc¹
 * piêciu atrybutow: color auta, marka auta, model auta, rok produkcji, pojemnosc silnika.
 * W klasie przyjêto ograniczenia:
 *    pola brand i model musz¹ zawieraæ niepusty ci¹g znaków
 *    pole year musi byæ z przedia³u <1800,2020> lub jak w pana programie 0 oznacza brak danych-
 *        -na temat roku produkcji
 *    pole carColour jest typem wyliczeniowym i kolor mozna wybrac z listy albo unknown co-
 *        -oznacza ¿e brak informacji na temat koloru
 *        
 *        #Powy¿sze ograniczenia s¹ kontrolowane i w przypadku próby nadania
 *         niedozwolonej wartoœci, któremuœ z atrybutów jest zg³aszany wyj¹tek
 *         zawieraj¹cy stosowny komunikat.#
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
	 * "get'y" Zwracaj¹ poszczególne wartoœci zale¿nie od atrybutu.
	 * */
	
	/*---------------------------------------------------------------------------------*/
	
	public void setModel(String model_) throws CarException{
		if((model_==null)|| model_.equals(""))// "==" - porównuje nam referencje nie porownuje zawartosci obiektow tylko sprawdza czy obiekt istnieje
	throw new CarException("Pole <Model> nie mo¿e byæ polem pustym.");//".equals()" - porównuje ³añcuchy
		this.model=model_;
	}

	public void setBrand(String brand_) throws CarException {
		if((brand_==null)||brand_.equals(""))
		throw new CarException("Pole <Marka> nie mo¿e byæ polem pustym.");
	this.brand=brand_;
		
	}
	
	public void setYear(int year_) throws CarException {
		if((year_!=0)&&(year_<1800||year_>2020))
			throw new CarException("Rok samochodu musi byæ pomiêdzy 1800 rokiem a 2020.");
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
		throw new CarException("Rok samochodu musi byæ ca³kowity");
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
		throw new CarException("U¿yj kropki '.' a nie przecinka ',' lub nie u¿ywaj liter.");
	}
	}
	public void setColour(String txt) throws CarException {
		if (txt == null || txt.equals("")) {  // pusty ³añcuch znaków oznacza stanowisko niezdefiniowane
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
	 * "set'y" pobieraj¹ wartoœci i sprawdzaj¹ ich poprawnoœæ w razie "problemów" 
	 * wyrzucaj¹ wyj¹tek CarException zmodyfikowany odpowiednio do poszcególnych 
	 * "set'ów"   
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
			throw new CarException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}	
	}
	
	
	public static Car readFromFile(String file_name) throws CarException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name + ".txt")))) {
			return Car.readFromFile(reader);
		} catch (FileNotFoundException e){
			throw new CarException("Nie odnaleziono pliku " + file_name);
		} catch(IOException e){
			throw new CarException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}	
	}
	
}
