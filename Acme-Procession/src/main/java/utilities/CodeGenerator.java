package utilities;

import java.util.UUID;


public class CodeGenerator {
	public static String generate(){
		String result;
		result = UUID.randomUUID().toString().replace("-", "");
		return result;
	}
	
}