public static HashMap<String,String> getJsonFromRequest(HttpServletRequest req){
		
		HashMap<String,String> params = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = req.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		JsonParser parser = Json.createParser(new StringReader(sb.toString()));
		String currentKey = "";
		while (parser.hasNext()) {		
			
			final Event event = parser.next();
			switch (event) {
			case KEY_NAME:				
				params.put(parser.getString(), "");
				currentKey = parser.getString();
				break;
			case VALUE_STRING:				
				params.put(currentKey, parser.getString());
				break;
			}	
		}
		parser.close(); 
		return params;
		
	}
