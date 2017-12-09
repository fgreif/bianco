package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriter {

	/**
	 * USAGE EXAMPLE
	 */
	public static void main(String[] args) {
		try {
			writeToFile("./", "output.txt", Files.readAllBytes(Paths.get("okcool.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean writeToFile(String path, String filename, byte[] data) {
		Path p = Paths.get(path, filename);
		try {
			Files.createFile(p);
			Files.write(p, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
