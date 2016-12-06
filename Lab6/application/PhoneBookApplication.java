package application;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import phonebook.MapPhoneBook;
import phonebook.PhoneBook;

public class PhoneBookApplication extends Application{
	private PhoneBook phoneBook;
	private NameListView nameListView;
	private Dialogs dialoger;
	private PhoneBook gamlakatalogen;
	private File file;

	/**
	 * The entry point for the Java program.
	 * @param args
	 */
	public static void main(String[] args) {	
		// launch() do the following:
		// - creates an instance of the Main class
		// - calls Main.init()
		// - create and start the javaFX application thread
		// - waits for the javaFX application to finish (close all windows)
		// the javaFX application thread do:
		// - calls Main.start(Stage s)
		// - runs the event handling loop
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		phoneBook = new MapPhoneBook();
		dialoger = new Dialogs();
		
//		
//		boolean start = dialoger.confirmDialog("Startup", "blabla", "Vill du starta programet?");
//		
//		if (!start){
//			System.exit(1);
//		}
		
		boolean load = dialoger.confirmDialog("Ladda befintlig telefonkatalog", "blabla", "Vill du ladda in en befintlig telefonkatalog?");
		
		if(load){
			laddakatalog();
		}
		// set default locale english 
		Locale.setDefault(Locale.ENGLISH);



		nameListView = new NameListView(phoneBook);


		BorderPane root = new BorderPane();




		root.setTop(new PhoneBookMenu(phoneBook, nameListView));

		root.setCenter(nameListView);

		Scene scene = new Scene(root);
		primaryStage.setTitle("PhoneBook");
		primaryStage.setScene(scene);
		primaryStage.show();

		
	}

	private void laddakatalog() {
		
//		Optional<String> result = dialoger.oneInputDialog("Find file", "asd", "filename");
//
//		if (result.isPresent()) {
//		String filename = result.get();
//		file = new File(filename);
		
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);

		
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(selectedFile));
			phoneBook = (PhoneBook) in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		}


		
//	}


	@Override
	public void stop(){
		boolean save;
		save = Dialogs.confirmDialog("Spara katalogen", "asd", "Spara katalogen?");

		if(save){
			Optional<String> result = dialoger.oneInputDialog("Save to file", "asd", "filename");

			if (result.isPresent()) {
				String filename = result.get();
				File file = new File(filename);
				
				try {
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
					out.writeObject(phoneBook);
					out.close();
					} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
					}

			}

		}

	}

}
