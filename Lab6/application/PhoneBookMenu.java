package application;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import phonebook.MapPhoneBook;
import phonebook.PhoneBook;

public class PhoneBookMenu extends MenuBar {
	private PhoneBook phoneBook;
	private NameListView nameListView;
	private Dialogs dia;
	
	/** Creates the menu for the phone book application.
	 * @param phoneBook the phone book with names and numbers
	 * @param nameListView handles the list view for the names
	 */
	public PhoneBookMenu(PhoneBook phoneBook, NameListView nameListView) {
		this.phoneBook = phoneBook;
		this.nameListView = nameListView;
		dia = new Dialogs();

		final Menu menuPhoneBook = new Menu("PhoneBook");
		final MenuItem menuQuit = new MenuItem("Quit");
		menuQuit.setOnAction(e -> Platform.exit());
		menuPhoneBook.getItems().addAll(menuQuit);
	
		final Menu menuFind = new Menu("Find");
		
		final MenuItem menuShowAll = new MenuItem("Show All");
		final MenuItem menuFindNbr = new MenuItem("Find Numbers");
		final MenuItem menuFindName = new MenuItem("Find Names");
		final MenuItem menuFindPers = new MenuItem("Find Persons");
		menuShowAll.setOnAction(e -> showAll());
		menuFindNbr.setOnAction(e -> FindNbr());
		menuFindName.setOnAction(e -> FindName());
		menuFindPers.setOnAction(e -> FindPers());
		menuFind.getItems().addAll(menuShowAll, menuFindNbr, menuFindName, menuFindPers);

	    getMenus().addAll(menuPhoneBook, menuFind);
  //    setUseSystemMenuBar(true);  // if you want operating system rendered menus, uncomment this line
	}

	
	private void showAll() {
		nameListView.fillList(phoneBook.names());
		nameListView.clearSelection();
	}

	private void FindNbr() {
		Optional<String> res = dia.oneInputDialog("Find Numbers", "asd", "Namn att hitta nummer till:");

		if (res.isPresent()) {
			String name = res.get();
			MapPhoneBook temp = new MapPhoneBook();

			Set<String> nummer = phoneBook.findNumbers(name);

			if (nummer.size()!=0){
				Iterator itr = nummer.iterator();
				while (itr.hasNext()) {
					temp.put(name, itr.next().toString());
				}

				nameListView.fillList(temp.names());
				nameListView.select(0);
			}
			
			else {
				dia.alert("Error", "Error", "Could not find any numbers with the name " + name);
			}
		}

	}

	private void FindName() {
		Optional<String> res = dia.oneInputDialog("Find Names", "asd", "Number:");

		if (res.isPresent()) {
			String number = res.get();
			MapPhoneBook temp = new MapPhoneBook();

			Set<String> namn = phoneBook.findNames(number);

			if (namn.size()!=0){
				Iterator itr = namn.iterator();
				while (itr.hasNext()) {
					temp.put(itr.next().toString(), number);
				}

				nameListView.fillList(temp.names());
				nameListView.clearSelection();

			}
			
			else {
				dia.alert("Error", "Error", "Could not find any names with the number " + number);
			}
		}
	}


	private void FindPers() {
		Optional<String> res = dia.oneInputDialog("Find part of name", "asd", "Keyword:");

		if (res.isPresent()) {
			String name = res.get();
			MapPhoneBook temp = new MapPhoneBook();

			Set<String> namn = phoneBook.findNames(number);

			if (namn.size()!=0){
	}



}
