package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ParserTests {
	public static void main(String[] args) {
		ResumeParser rp = new ResumeParser("src/SampleResume1.txt");
		//System.out.println(rp.getResumeString());
		System.out.println(rp.parseForZip());
		System.out.println(rp.getParsedZip());
		System.out.println(rp.parseForPhone());
		System.out.println(rp.getParsedPhone());
		System.out.println(rp.parseForEmail());
		System.out.println(rp.getParsedEmail());
		System.out.println(rp.parseForState());
		System.out.println(rp.getParsedState());
	}
}
