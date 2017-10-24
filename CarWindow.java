import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CarWindow extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private static final String GREETING_MESSAGE = 
			"Program: Car - wersja okienkowa   \n" + 
			"Autor: Adrian Winiarski           \n" + 
			"Indeks: 225948                    \n" +		
			"Data:  pa�dziernik 2017 r.        \n";
	
	public static void main(String[] args) {
		
		new CarWindow();//obiekt kt�ry reprezentuje g�owne okno obiektu

		 //new CarWindow();
		 //new CarWindow();
		 //powy�ej dodatkowe okna jak w Pa�skim programie przyk�adowym
	}

	
	
	/*
	 *  Referencja do obiektu, kt�ry zawiera dane aktualnego auta.
	 */
	private Car currentCar;
	
	
	// Font dla etykiet o sta�ej szeroko�ci znak�w
	Font font = new Font("MonoSpaced", Font.BOLD, 12);

	// Etykiety wy�wietlane na panelu w g��wnym oknie aplikacji
	JLabel brandLabel = new JLabel("     Marka: ");
	JLabel modelLabel  = new JLabel("    Model: ");
	JLabel yearLabel      = new JLabel("   Rok ur.: ");
	JLabel capacityLabel   = new JLabel("    poj. silnika: ");
	JLabel colourLabel       = new JLabel("   Kolor: ");

	// Pola tekstowe wy�wietlane na panelu w g��wnym oknie aplikacji
	JTextField brandField = new JTextField(10);
	JTextField modelField    = new JTextField(10);
	JTextField yearField    = new JTextField(10);
	JTextField capacityField  = new JTextField(10);
	JTextField colourField     = new JTextField(10);

	// Przyciski wy�wietlane na panelu w g��wnym oknie aplikacji
	JButton newButton    = new JButton("Nowe auto");
	JButton editButton   = new JButton("Zmie� dane");
	JButton saveButton   = new JButton("Zapisz do pliku");
	JButton loadButton   = new JButton("Wczytaj z pliku");
	JButton deleteButton = new JButton("Usu� auto");
	JButton infoButton   = new JButton("O programie");
	JButton exitButton   = new JButton("Zako�cz aplikacj�");

	
	/*
	 * Utworzenie i konfiguracja g��wnego okna apkikacji
	 */
	public CarWindow(){
		// Konfiguracja parametr�w g��wnego okna aplikacji
		setTitle("CarWindow");  
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(270, 300);
		setResizable(false);
		setLocationRelativeTo(null);

		// Zmiana domy�lnego fontu dla wszystkich etykiet
		// U�yto fontu o sta�ej szeroko�ci znak�w, by wyr�wna�
		// szeroko�� wszystkich etykiet.
		brandLabel.setFont(font);
		modelLabel.setFont(font);
		yearLabel.setFont(font);
		capacityLabel.setFont(font);
		colourLabel.setFont(font);

		// Zablokowanie mo�liwo�ci edycji tekst�w we wszystkich 
		// polach tekstowych.  (pola nieedytowalne)
		brandField.setEditable(false);
		modelField.setEditable(false);
		yearField.setEditable(false);
		capacityField.setEditable(false);
		colourField.setEditable(false);

		
		// Dodanie s�uchaczy zdarze� do wszystkich przycisk�w.
		// UWAGA: s�uchaczem zdarze� b�dzie metoda actionPerformed
		//        zaimplementowana w tej klasie i wywo�ana dla
		//        bie��cej instancji okna aplikacji - referencja this
		newButton.addActionListener(this);
		editButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		deleteButton.addActionListener(this);
		infoButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		// Utworzenie g��wnego panelu okna aplikacji.
		// Domy�lnym mened�erem rozd�adu dla panelu b�dzie
		// FlowLayout, kt�ry uk�ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		
		// Dodanie i rozmieszczenie na panelu wszystkich
		// komponent�w GUI.
		panel.add(brandLabel);
		panel.add(brandField);
		
		panel.add(modelLabel);
		panel.add(modelField);
		
		panel.add(yearLabel);
		panel.add(yearField);
		
		panel.add(capacityLabel);
		panel.add(capacityField);
		
		panel.add(colourLabel);
		panel.add(colourField);

		panel.add(newButton);
		panel.add(deleteButton);
		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(editButton);
		panel.add(infoButton);
		panel.add(exitButton);
		
		// Umieszczenie Panelu w g��wnym oknie aplikacji.
		setContentPane(panel);
		
		// Wype�nienie p�l tekstowych danymi aktualnej auta.
		showCurrentCar();
		
		// Pokazanie na ekranie g��wnego okna aplikacji
      setVisible(true);
	}

	
	/*
	 * Metoda wype�nia do wype�niania danych auta
	 */
	void showCurrentCar() {
		if (currentCar == null) {
			brandField.setText("");
			modelField.setText("");
			yearField.setText("");
			capacityField.setText("");
			colourField.setText("");
		} else {
			brandField.setText(currentCar.getBrand());
			modelField.setText(currentCar.getModel());
			yearField.setText("" + currentCar.getYear());
			capacityField.setText(""+currentCar.getCapacity());
			colourField.setText("" + currentCar.getColour());
		}
	}

	
	/*
	 *Implementacja interfejsu ActionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Odczytanie referencji do obiektu, kt�ry wygenerowa� zdarzenie.
		Object eventSource = event.getSource();

		try {
			if (eventSource == newButton) { 
				currentCar = WindowCarDialog.createNewCar(this);
			}
			if (eventSource == deleteButton) {
				currentCar = null;
			}
			if (eventSource == saveButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazw� pliku");
				if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
				Car.printToFile(fileName, currentCar);
			}
			if (eventSource == loadButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazw� pliku");
				if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku. 
				currentCar = Car.readFromFile(fileName);
			}
			if (eventSource == editButton) {
				if (currentCar == null) throw new CarException("�adne auto nie zosta� utworzona."); 
				WindowCarDialog.changeCarData(this, currentCar);
			}
			if (eventSource == infoButton) {
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			if (eventSource == exitButton) {
				System.exit(0);
			}
		} catch (CarException e) {//wychwytuje i zg�asza wyj�tki
			JOptionPane.showMessageDialog(this, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
		}
		
		// Aktualizacja zawarto�ci wszystkich p�l tekstowych.
		showCurrentCar();
	}	
	
	
} // koniec klasy CarWindow
