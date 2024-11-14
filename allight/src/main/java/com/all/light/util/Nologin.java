package com.all.light.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Nologin {
	public static String[] auto() {
		String[] arr=null;
		try {
			Runtime rt=Runtime.getRuntime();
			String ex="d:\\study\\pj5ML\\dist\\test.exe";
			Process pro=rt.exec(ex);
			pro.waitFor();
			File file = new File("d:\\study\\pj5ML\\logintest.txt");

            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = bufReader.readLine();
            arr=line.split(",");//예측,정답
            bufReader.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		return arr;
	}

}
