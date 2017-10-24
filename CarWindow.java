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
			"Data:  paŸdziernik 2017 r.        \n";
	
	public static void main(String[] args) {
		
		new CarWindow();//obiekt który reprezentuje g³owne okno obiektu

		 //new CarWindow();
		 //new CarWindow();
		 //powy¿ej dodatkowe okna jak w Pañskim programie przyk³adowym
	}

	
	
	/*
	 *  Referencja do obiektu, który zawiera dane aktualnego auta.
	 */
	private Car currentCar;
	
	
	// Font dla etykiet o sta³ej szerokoœci znaków
	Font font = new Font("MonoSpaced", Font.BOLD, 12);

	// Etykiety wyœwietlane na panelu w g³ównym oknie aplikacji
	JLabel brandLabel = new JLabel("     Marka: ");
	JLabel modelLabel  = new JLabel("    Model: ");
	JLabel yearLabel      = new JLabel("   Rok ur.: ");
	JLabel capacityLabel   = new JLabel("    poj. silnika: ");
	JLabel colourLabel       = new JLabel("   Kolor: ");

	// Pola tekstowe wyœwietlane na panelu w g³ównym oknie aplikacji
	JTextField brandField = new JTextField(10);
	JTextField modelField    = new JTextField(10);
	JTextField yearField    = new JTextField(10);
	JTextField capacityField  = new JTextField(10);
	JTextField colourField     = new JTextField(10);

	// Przyciski wyœwietlane na panelu w g³ównym oknie aplikacji
	JButton newButton    = new JButton("Nowe auto");
	JButton editButton   = new JButton("Zmieñ dane");
	JButton saveButton   = new JButton("Zapisz do pliku");
	JButton loadButton   = new JButton("Wczytaj z pliku");
	JButton deleteButton = new JButton("Usuñ auto");
	JButton infoButton   = new JButton("O programie");
	JButton exitButton   = new JButton("Zakoñcz aplikacjê");

	
	/*
	 * Utworzenie i konfiguracja g³ównego okna apkikacji
	 */
	public CarWindow(){
		// Konfiguracja parametrów g³ównego okna aplikacji
		setTitle("CarWindow");  
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(270, 300);
		setResizable(false);
		setLocationRelativeTo(null);

		// Zmiana domyœlnego fontu dla wszystkich etykiet
		// U¿yto fontu o sta³ej szerokoœci znaków, by wyrównaæ
		// szerokoœæ wszystkich etykiet.
		brandLabel.setFont(font);
		modelLabel.setFont(font);
		yearLabel.setFont(font);
		capacityLabel.setFont(font);
		colourLabel.setFont(font);

		// Zablokowanie mo¿liwoœci edycji tekstów we wszystkich 
		// polach tekstowych.  (pola nieedytowalne)
		brandField.setEditable(false);
		modelField.setEditable(false);
		yearField.setEditable(false);
		capacityField.setEditable(false);
		colourField.setEditable(false);

		
		// Dodanie s³uchaczy zdarzeñ do wszystkich przycisków.
		// UWAGA: s³uchaczem zdarzeñ bêdzie metoda actionPerformed
		//        zaimplementowana w tej klasie i wywo³ana dla
		//        bie¿¹cej instancji okna aplikacji - referencja this
		newButton.addActionListener(this);
		editButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		deleteButton.addActionListener(this);
		infoButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		// Utworzenie g³ównego panelu okna aplikacji.
		// Domyœlnym mened¿erem rozd³adu dla panelu bêdzie
		// FlowLayout, który uk³ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		
		// Dodanie i rozmieszczenie na panelu wszystkich
		// komponentów GUI.
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
		
		// Umieszczenie Panelu w g³ównym oknie aplikacji.
		setContentPane(panel);
		
		// Wype³nienie pól tekstowych danymi aktualnej auta.
		showCurrentCar();
		
		// Pokazanie na ekranie g³ównego okna aplikacji
      setVisible(true);
	}

	
	/*
	 * Metoda wype³nia do wype³niania danych auta
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
		// Odczytanie referencji do obiektu, który wygenerowa³ zdarzenie.
		Object eventSource = event.getSource();

		try {
			if (eventSource == newButton) { 
				currentCar = WindowCarDialog.createNewCar(this);
			}
			if (eventSource == deleteButton) {
				currentCar = null;
			}
			if (eventSource == saveButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
				Car.printToFile(fileName, currentCar);
			}
			if (eventSource == loadButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku. 
				currentCar = Car.readFromFile(fileName);
			}
			if (eventSource == editButton) {
				if (currentCar == null) throw new CarException("¯adne auto nie zosta³ utworzona."); 
				WindowCarDialog.changeCarData(this, currentCar);
			}
			if (eventSource == infoButton) {
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			if (eventSource == exitButton) {
				System.exit(0);
			}
		} catch (CarException e) {//wychwytuje i zg³asza wyj¹tki
			JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		
		// Aktualizacja zawartoœci wszystkich pól tekstowych.
		showCurrentCar();
	}	
	
	
} // koniec klasy CarWindow
