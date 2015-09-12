import java.util.*;

public class morseToEnglish {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] testString = { "....", ".", ".-..", ".-..", "---", "_", ".--", "---", ".-.", ".-..", "-.."};
		
		String testEnglish = convert(testString);
		
		System.out.print(testEnglish);
	}

	static String convert(String[] morseArray) {
		String englishString ="";
		
		for(int i = 0; i < morseArray.length; i++) {
			if(morseArray[i] != "_") {
				englishString = englishString + morseKey(morseArray[i]);
			}
			else if(morseArray[i] == "_") {
				englishString = englishString + " ";
			}
		}
		
		return englishString;
	}
	
	static char morseKey(String morse) {
		char eng = ' ';
		
		switch(morse) {
			case ".-": eng = 'a';
				break;
			case "-...": eng = 'b';
				break;
			case "-.-.": eng = 'c';
				break;
			case "-..": eng = 'd';
				break;
			case ".": eng = 'e';
				break;
			case "..-.": eng = 'f';
				break;
			case "--.": eng = 'g';
				break;
			case "....": eng = 'h';
				break;
			case "..": eng = 'i';
				break;
			case ".---": eng = 'j';
				break;
			case "-.-": eng = 'k';
				break;
			case ".-..": eng = 'l';
				break;
			case "--": eng = 'm';
				break;
			case "-.": eng = 'n';
				break;
			case "---": eng = 'o';
				break;
			case ".--.": eng = 'p';
				break;
			case "--.-": eng = 'q';
				break;
			case ".-.": eng = 'r';
				break;
			case "...": eng = 's';
				break;
			case "-": eng = 't';
				break;
			case "..-": eng = 'u';
				break;
			case "...-": eng = 'v';
				break;
			case ".--": eng = 'w';
				break;
			case "-..-": eng = 'x';
				break;
			case "-.--": eng = 'y';
				break;
			case "--..": eng = 'z';
				break;
			case ".----": eng = '1';
				break;
			case "..---": eng = '2';
				break;
			case "...--": eng = '3';
				break;
			case "....-": eng = '4';
				break;
			case ".....": eng = '5';
				break;
			case "-....": eng = '6';
				break;
			case "--...": eng = '7';
				break;
			case "---..": eng = '8';
				break;
			case "----.": eng = '9';
				break;
			case "-----": eng = '0';
				break;
		}
		
		return eng;
	}
}
