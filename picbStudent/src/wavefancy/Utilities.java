package wavefancy;

import com.google.gson.Gson;

public class Utilities {
	static Gson gson = new Gson();
	
	/**
	 * return the raw Gson.
	 * @return new Gson();
	 */
	public static Gson getRawGson(){
		return gson;
	}

}
