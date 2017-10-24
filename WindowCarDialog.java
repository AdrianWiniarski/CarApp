import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class WindowCarDialog extends JDialog implements ActionListener {


	private static final long serialVersionUID = 1L;
	
	private Car car;//referencja
	
	Font font = new Font("MonoSpaced", Font.BOLD, 12);

	// Utworzenie i inicjalizacja komponentów do do budowy
	// okienkowego interfejsu u¿ytkownika
	// Font dla etykiet o sta³ej szerokoœci znaków
	
	
	
	JLabel brandLabel = new JLabel("   Marka: ");
	JLabel modelLabel  = new JLabel("  Model: ");
	JLabel yearLabel      = new JLabel("   Rok prod.: ");
	JLabel capacityLabel = new JLabel(" Poj. silnika.: ");
	JLabel colourLabel       = new JLabel("   Kolor: ");
	//Etykietowanie
	

	JTextField brandField = new JTextField(10);
	JTextField modelField = new JTextField(10);
	JTextField yearField = new JTextField(10);
	JTextField capacityField = new JTextField(10);
	JComboBox<CarColour> colourBox = new JComboBox<CarColour>(CarColour.values());
	//Pola tekstowe wyœwietlane na panelu

	JButton OKButton = new JButton("  OK  ");
	JButton CancelButton = new JButton("Anuluj");
	//Przyciski wyœwietlane na panelu
	
	
	
	private WindowCarDialog(Window parent, Car car) {

		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// Konfiguracja parametrów tworzonego okna dialogowego
		setSize(220, 200);
		setLocationRelativeTo(parent);
		
		this.car = car;
		
		// Ustawienie tytu³u okna oraz wype³nienie zawartoœci pól tekstowych
		if (car==null){
			setTitle("Nowe auto");
		} else{
			setTitle(car.toString());
			brandField.setText(car.getBrand());
			modelField.setText(car.getModel());
			yearField.setText(""+car.getYear());
			capacityField.setText(""+car.getCapacity());
			colourBox.setSelectedItem(car.getColour());
		}
		
		// Dodanie s³uchaczy zdarzeñ do przycisków.
		// UWAGA: s³uchaczem zdarzeñ bêdzie metoda actionPerformed
		//        zaimplementowana w tej klasie i wywo³ana dla
		//        bie¿¹cej instancji okna dialogowego - referencja this
		OKButton.addActionListener( this );
		CancelButton.addActionListener( this );
		
		// Utworzenie g³ównego panelu okna dialogowego.
		// Domyœlnym mened¿erem rozd³adu dla panelu bêdzie
		// FlowLayout, który uk³ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		
		// Zmiana koloru t³a g³ównego panelu okna dialogowego
		panel.setBackground(Color.white);

		// Dodanie i rozmieszczenie na panelu wszystkich komponentów GUI.
		panel.add(brandLabel);
		panel.add(brandField);
		
		panel.add(modelLabel);
		panel.add(modelField);
		
		panel.add(yearLabel);
		panel.add(yearField);
		
		panel.add(capacityLabel);
		panel.add(capacityField);
		
		panel.add(colourLabel);
		panel.add(colourBox);
		
		panel.add(OKButton);
		panel.add(CancelButton);
		
		setContentPane(panel);// Umieszczenie Panelu w oknie dialogowym.
		
		
		setVisible(true);		// Pokazanie na ekranie okna dialogowego **Wa¿ne**
	
	}
	

	@Override
	public void actionPerformed(ActionEvent ev) {

		// Odczytanie referencji do obiektu, który wygenerowa³ zdarzenie.
		Object source = ev.getSource();
		
		if (source == OKButton) {
			try {
				if (car == null) { 
					car = new Car(brandField.getText(), modelField.getText());// Utworzenie nowej fury
				} else { 
					car.setBrand(brandField.getText());// Aktualizacji marki istniej¹cego auta
					car.setModel(modelField.getText());// Aktualizacji modelu istniej¹cego auta
				}
				car.setYear(yearField.getText());// Aktualizacji roku produkcji istniej¹cego auta
				car.setCapacity(capacityField.getText());// Aktualizacji pojemnosci silnika istniej¹cego auta
				car.setColour((CarColour) colourBox.getSelectedItem());// Aktualizacji koloru istniej¹cego auta
				
				dispose();// Zamkniêcie okna i zwolnienie wszystkich zasobów.
			} catch (CarException e) {//£apie wyj¹tki i i zglasza komunikat o b³edziê wraz z CareXCEPTION
				JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (source == CancelButton) {
			// Zamkniêcie okna i zwolnienie wszystkich zasobów.
			dispose();
		}
			
	}
	public static Car createNewCar(Window parent) {
		WindowCarDialog dialog = new WindowCarDialog(parent, null);
		return dialog.car;
	}
	
	public static void changeCarData(Window parent, Car car) {
		new WindowCarDialog(parent, car);
	}
}
