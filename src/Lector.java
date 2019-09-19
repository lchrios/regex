import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Lector {
	
	public Automata auto;
	public File[] filePaths;
	public LinkedList<File> acceptedPaths = new LinkedList<File>();
	public LinkedList<String> fileNames = new LinkedList<String>();
	
	public File path;
	
	public Lector (String path, String regex) {
		 this.path = new File(path);
		 this.auto = new Automata(regex);
	}
	
	public void printAllAcceptedPaths() {
		System.out.println("Paths aceptados:");
		for (int i = 0; i < acceptedPaths.size(); i++) {
			System.out.println(acceptedPaths.get(i).toString());
		}
	}
	
	public void printNames() {
		System.out.println("Paths aceptados:");
		for (int i = 0; i < fileNames.size(); i++) {
			System.out.println(fileNames.get(i).toString());
		}
	}
	
	public void busca(File dir) throws IOException {
		System.out.println("buscando en " + dir.toString());
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				busca(f);
			}
			else if (f.isFile()) {
				if (auto.checkInRegex(f.getName().toLowerCase())) {
					BufferedReader br = new BufferedReader(new FileReader(f));
					String line = br.readLine().toLowerCase();
					while (line != null) {
						if (auto.checkInRegex(line)) {
							fileNames.add(f.getName());
							acceptedPaths.add(f);
							break;
						}
					}
					br.close();
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		Scanner	in = new Scanner(System.in);
		System.out.print("Introduce la regex: ");
		String reg = in.nextLine();
		System.out.print("Introduce el path: ");
		String pa = in.nextLine();
		in.close();
		Lector l = new Lector(pa, reg);
		try {
			l.busca(l.path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		l.printNames();
		System.out.println(l.acceptedPaths.size()+ " tamaño");

	}

}
