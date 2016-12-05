package phonebook;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

public class MapPhoneBook implements PhoneBook, Serializable{
	
	private static final long serialVersionUID = 1L;
	private Map<String,Set<String>> phoneBook;
	
	public MapPhoneBook() {
		phoneBook = new HashMap<String, Set<String>>();
	}

	@Override
	public boolean put(String name, String number) 
	{
		if (phoneBook.containsKey(name)) {
			Set<String> set = phoneBook.get(name);
			if (set.add(number)) {
				phoneBook.put(name, set);
				return true;
			}

			return false;
		} else {
			Set<String> set = new HashSet<String>();
			set.add(number);
			phoneBook.put(name, set);
			return true;
		}
	}

	@Override
	public boolean remove(String name) {
		
		if (phoneBook.containsKey(name)) {
			phoneBook.remove(name);
			return true;
		} 
		
		return false;

	}

	@Override
	public boolean removeNumber(String name, String number) {
				if (phoneBook.containsKey(name)) {
					Set<String> set = phoneBook.get(name);
					
					if (set.remove(number)) {
//						if (set.isEmpty()) {
//							return remove(name);
//						}
						phoneBook.put(name, set);
						return true;
					}
				}
				
				return false;

	}

	@Override
	public Set<String> findNumbers(String name) {
		
		//det går inte att göra size på null så därför returnar jag en tom ny set istället
		if (phoneBook.get(name)==null){
			return new HashSet<String>();
		}
		
		return phoneBook.get(name);
	}

	@Override
	public Set<String> findNames(String number) {
		
		//FATTA DENNA
		
		Set<Map.Entry<String,Set<String>>> set = phoneBook.entrySet();
		Set<String> temp = new TreeSet<String>();
		for (Map.Entry<String, Set<String>> e : set) {
			Set<String> numbers = e.getValue();
			for (String n : numbers) {
				if (n.equals(number)) {
					temp.add(e.getKey());
					break;
				}
			}
		}
		if (temp.isEmpty()) {
			return new HashSet<String>();
		}
		return temp;
	}

	@Override
	public Set<String> names() {
		return new TreeSet<String>(phoneBook.keySet());
	}

	@Override
	public int size() {

		return phoneBook.size();
	}

}