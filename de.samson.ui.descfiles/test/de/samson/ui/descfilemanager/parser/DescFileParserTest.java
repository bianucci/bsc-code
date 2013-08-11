package de.samson.ui.descfilemanager.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.samson.service.database.entities.description.GeraeteDesc;
import de.samson.ui.descfilemanager.exceptions.DescDirectoryNotFoundEception;
import de.samson.ui.descfilemanager.exceptions.DescFileParsingException;

public class DescFileParserTest {
	
	DescFileParser dfp;
	File f = new File("resources\\DescGeraete");
	
	@Before
	public void prepareTests(){
		dfp = new DescFileParser();
	}

	@Test
	public void testInit() {
		try {
			DescFileParser.init(f.getAbsolutePath());
		} catch (DescDirectoryNotFoundEception e) {
//			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
		} catch (DescFileParsingException e) {
//			e.printStackTrace();
		}
		ArrayList<GeraeteDesc> controller = DescFileParser.getController();
		System.out.println(controller);
		
	}

	@Test
	public void testParseJSONFile() {
	}

	@Test
	public void testGetController() {
	}

}
