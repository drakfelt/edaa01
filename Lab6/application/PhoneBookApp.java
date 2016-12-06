package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import phonebook.MapPhoneBook;
import phonebook.PhoneBook;

public class PhoneBookApp extends Application{
	private PhoneBook phoneBook;
	private NameListView nameListView;
	private Dialogs dialoger;
	private PhoneBook gamlakatalogen;
	private File fille;

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
			fille = laddakatalog();
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
		
		if(load){
			try (BufferedReader br = new BufferedReader(new FileReader(fille))) {
			    String line;
			    
			    
			    while ((line = br.readLine()) != null) {
			    			    	
			    	String[] parts = line.split("-");
			    	String namndel = parts[0];
			    	String nummerdel = parts[1];
			    	
			    	phoneBook.put(namndel, nummerdel);
			    }
			}
		}
		
		
	}

	private File laddakatalog() {
		
//		Optional<String> result = dialoger.oneInputDialog("Find file", "asd", "filename");
//
//		if (result.isPresent()) {
//		String filename = result.get();
//		file = new File(filename);
		
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		
		return selectedFile;

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
				
				BufferedWriter writer = null;
		        try {
		        	
		            // This will output the full path where the file will be written to...
		            System.out.println(file.getCanonicalPath());

		            writer = new BufferedWriter(new FileWriter(file));
		            
		            Set<String> namn = phoneBook.names();
		            
		            Iterator iter = namn.iterator();
		            
		            StringBuilder sb = new StringBuilder();

		            
		            //itteratror över alla namn och alla nummer på varje namn
		            while (iter.hasNext()) {
		            	
		            	
		            	String currentname = iter.next().toString();

		            	Set<String> nummer = phoneBook.findNumbers(currentname);

		            	Iterator iter2 = nummer.iterator();
		            	while (iter2.hasNext()) {
		            		String currentnumber= iter2.next().toString();
		            		
//		            		System.out.println(currentname + " " + currentnumber);
		            		sb.append(currentname + "-" + currentnumber + "\n");

		            	}



		            }


		            writer.write(sb.toString());

		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                // Close the writer regardless of what happens...
		                writer.close();
		            } catch (Exception e) {
		            }
		        }

			}

		}

	}

}
