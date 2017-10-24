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

	// Utworzenie i inicjalizacja komponent�w do do budowy
	// okienkowego interfejsu u�ytkownika
	// Font dla etykiet o sta�ej szeroko�ci znak�w
	
	
	
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
	//Pola tekstowe wy�wietlane na panelu

	JButton OKButton = new JButton("  OK  ");
	JButton CancelButton = new JButton("Anuluj");
	//Przyciski wy�wietlane na panelu
	
	
	
	private WindowCarDialog(Window parent, Car car) {

		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// Konfiguracja parametr�w tworzonego okna dialogowego
		setSize(220, 200);
		setLocationRelativeTo(parent);
		
		this.car = car;
		
		// Ustawienie tytu�u okna oraz wype�nienie zawarto�ci p�l tekstowych
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
		
		// Dodanie s�uchaczy zdarze� do przycisk�w.
		// UWAGA: s�uchaczem zdarze� b�dzie metoda actionPerformed
		//        zaimplementowana w tej klasie i wywo�ana dla
		//        bie��cej instancji okna dialogowego - referencja this
		OKButton.addActionListener( this );
		CancelButton.addActionListener( this );
		
		// Utworzenie g��wnego panelu okna dialogowego.
		// Domy�lnym mened�erem rozd�adu dla panelu b�dzie
		// FlowLayout, kt�ry uk�ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		
		// Zmiana koloru t�a g��wnego panelu okna dialogowego
		panel.setBackground(Color.white);

		// Dodanie i rozmieszczenie na panelu wszystkich komponent�w GUI.
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
		
		
		setVisible(true);		// Pokazanie na ekranie okna dialogowego **Wa�ne**
	
	}
	

	@Override
	public void actionPerformed(ActionEvent ev) {

		// Odczytanie referencji do obiektu, kt�ry wygenerowa� zdarzenie.
		Object source = ev.getSource();
		
		if (source == OKButton) {
			try {
				if (car == null) { 
					car = new Car(brandField.getText(), modelField.getText());// Utworzenie nowej fury
				} else { 
					car.setBrand(brandField.getText());// Aktualizacji marki istniej�cego auta
					car.setModel(modelField.getText());// Aktualizacji modelu istniej�cego auta
				}
				car.setYear(yearField.getText());// Aktualizacji roku produkcji istniej�cego auta
				car.setCapacity(capacityField.getText());// Aktualizacji pojemnosci silnika istniej�cego auta
				car.setColour((CarColour) colourBox.getSelectedItem());// Aktualizacji koloru istniej�cego auta
				
				dispose();// Zamkni�cie okna i zwolnienie wszystkich zasob�w.
			} catch (CarException e) {//�apie wyj�tki i i zglasza komunikat o b�edzi� wraz z CareXCEPTION
				JOptionPane.showMessageDialog(this, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (source == CancelButton) {
			// Zamkni�cie okna i zwolnienie wszystkich zasob�w.
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
