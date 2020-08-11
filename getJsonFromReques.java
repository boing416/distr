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

		String key2 = null;
		String value = null;

		String needKey1 = "accountId";
		String needKey2 = "amount";
		String needVal1 = "";
		String needVal2 = "";
		boolean isNext1 = false;
		boolean isNext2 = false;
		
		String currentKey = "";
		while (parser.hasNext()) {
			key2 = "";
			value = "";
			final Event event = parser.next();
			switch (event) {
			case KEY_NAME:
				key2 = parser.getString();
				params.put(key2, "");
				currentKey = key2;
				break;
			case VALUE_STRING:
				value = parser.getString();
				params.put(currentKey, value);
				break;
			}

			if (isNext1) {
				needVal1 = parser.getString();
				isNext1 = false;
			} else if (isNext2) {
				needVal2 = parser.getString();
				isNext2 = false;
			}

			if (needKey1.equals(key2)) {
				isNext1 = true;
			} else if (needKey2.equals(key2)) {
				isNext2 = true;
			}

		}
		parser.close(); 
		return params;
		
	}
