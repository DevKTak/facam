package kdt_y_be_toy_project1.common.util;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateSerializer implements JsonSerializer<LocalDate> {

	@Override
	public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.toString());
	}
}
