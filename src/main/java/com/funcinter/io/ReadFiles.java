package com.funcinter.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadFiles {

	public static void main(String[] args) {
		ReadFiles obj = new ReadFiles();
		obj.readFiles1();
		obj.readAndWalk();
	}

	private void readFiles1() {
		Path path = Paths.get("/Users", "rkhaskin", "onedata4");

		// stream will be automatically closed
		try (Stream<Path> stream = Files.list(path)) {
			stream.filter(mypath -> path.toFile().isDirectory()).forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * do not run. Memory leak example
	 */
	private void readFiles2() {
		Path path = Paths.get("/Users", "rkhaskin", "onedata4");

		try {
			// stream must be manuall closed
			Stream<Path> stream = Files.list(path);
			stream.filter(mypath -> path.toFile().isDirectory()).forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void readAndWalk() {
		Path path = Paths.get("/Users", "rkhaskin", "onedata4");

		// stream will be automatically closed
		try (Stream<Path> stream = Files.walk(path, 2)) {  // shows level up to which to read. 
			stream.filter(mypath -> path.toFile().isDirectory()).forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
