package ua.foxminded.springbootjdbcapi.viewprovider;

import java.util.Scanner;

public class ViewProvider {
    private Scanner scanner;

    public ViewProvider(Scanner scanner) {
	this.scanner = scanner;
    }

    public void printMessage(String message) {
	System.out.println(message);
    }

    public String read() {
	return scanner.next();
    }

    public int readInt() {
	return scanner.nextInt();
    }
    
    public boolean readBoolean() {
	return scanner.hasNext();
    }
}
