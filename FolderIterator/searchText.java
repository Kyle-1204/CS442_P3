package assignment03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class SearchText {
	public static void listFiles(File file) {
		Scanner myObj = new Scanner(System.in);
	    String text;
	    // Enter text and press Enter
	    System.out.println("Enter text: "); 
	    text = myObj.nextLine().toUpperCase();
	    myObj.close();
		try (PrintWriter pw = new PrintWriter("Output3.txt")) {
			System.out.println(file);
			var iter = new FileCompositeIterator(file);
			while(iter.hasNext()) {
				File f = iter.next();
				pw.println(f);
				if(f.getName().toUpperCase().contains(".TXT")
				|| (f.getName().toUpperCase().contains(".HTM"))
				|| (f.getName().toUpperCase().contains(".TEXT"))
				|| (f.getName().toUpperCase().contains(".JAVA"))
				|| (f.getName().toUpperCase().contains(".C"))
				|| (f.getName().toUpperCase().contains(".CPP"))
				|| (f.getName().toUpperCase().contains(".H"))
				|| (f.getName().toUpperCase().contains(".HTML"))
				|| (f.getName().toUpperCase().contains(".JS"))
				|| (f.getName().toUpperCase().contains(".HS"))
				|| (f.getName().toUpperCase().contains(".XML"))
				|| (f.getName().toUpperCase().contains(".LOG"))){
					if(checkWord(f, pw, text))
						System.out.println(f);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						// See https://stackoverflow.com/questions/10083447/selecting-folder-destination-in-java
						var chooser = new JFileChooser(); 
						chooser.setCurrentDirectory(new java.io.File("~"));
						chooser.setDialogTitle("Pick a starting directory");
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						// disable the "All files" option.
						chooser.setAcceptAllFileFilterUsed(false);
						if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
							System.out.println("getCurrentDirectory(): " 
									+  chooser.getCurrentDirectory());
							System.out.println("getSelectedFile() : " 
									+  chooser.getSelectedFile());
							listFiles(chooser.getSelectedFile());
						} else {
							System.out.println("No Selection ");
						}
						System.out.println("DONE!");
					}
				});		
	}

	public static boolean checkWord(File f, PrintWriter pw, String text) {
		boolean ct = false;
		if (f.getName().toUpperCase().contains(text)){
			ct = true;
		}
		else {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f));
				String line;
				while((line = reader.readLine()) != null) {
					String[] temp = line.split("\\s+");
					for(String s : temp)
						if(s.toUpperCase().contains(text)) {
							ct = true;
							break;
						}
					}
				reader.close();
			} catch (Exception e) {
				pw.println("PROBLEM WITH " + f);
				e.printStackTrace(pw);
			}	
		}
		return ct;
	}
}
