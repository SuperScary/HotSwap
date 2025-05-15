package net.superscary.hotswap.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Loader‑agnostic JSON5 config manager with per‑field comments and custom names.
 * Uses Gson in lenient mode to parse comments and manual reflection to write.
 */
public class JsonConfig<T> {
	private final Path file;
	private final Class<T> type;
	private final Gson gson;
	private final String headerComment;
	private T config;

	private JsonConfig (Builder<T> b) {
		this.file = b.file;
		this.type = b.type;
		this.headerComment = b.headerComment == null ? "" : b.headerComment;
		this.gson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.create();
		loadOrCreate(b.defaultConfig);
	}

	/**
	 * Builder for JsonConfig
	 */
	public static class Builder<T> {
		private Path file;
		private Class<T> type;
		private T defaultConfig;
		private String headerComment;

		public Builder<T> file (Path file) {
			this.file = file;
			return this;
		}

		public Builder<T> type (Class<T> type) {
			this.type = type;
			return this;
		}

		public Builder<T> defaultConfig (T defaultConfig) {
			this.defaultConfig = defaultConfig;
			return this;
		}

		public Builder<T> headerComment (String headerComment) {
			this.headerComment = headerComment;
			return this;
		}

		public JsonConfig<T> build () {
			return new JsonConfig<>(this);
		}
	}

	/**
	 * @return the live config instance
	 */
	public T get () {
		return config;
	}

	private void loadOrCreate (T defaults) {
		try {
			Files.createDirectories(file.getParent());
			if (Files.notExists(file)) {
				this.config = defaults;
				save();
			} else {
				try (Reader reader = Files.newBufferedReader(file)) {
					JsonReader jr = new JsonReader(reader);
					jr.setStrictness(Strictness.LENIENT);
					JsonElement root = JsonParser.parseReader(jr);
					JsonObject obj = root != null && root.isJsonObject()
							? root.getAsJsonObject()
							: new JsonObject();
					// Map custom names to Java field names for deserialization
					for (Field f : type.getDeclaredFields()) {
						f.setAccessible(true);
						if (f.isAnnotationPresent(Name.class)) {
							String jsonKey = f.getAnnotation(Name.class).value();
							if (obj.has(jsonKey)) {
								JsonElement value = obj.remove(jsonKey);
								obj.add(f.getName(), value);
							}
						}
					}
					this.config = gson.fromJson(obj, type);
				}
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Failed to load/create config: " + file, e);
		}
	}

	/**
	 * Writes header + each field with its @Comment above the JSON5 property.
	 * Honors @Name for custom JSON keys.
	 */
	public void save () {
		try (BufferedWriter w = Files.newBufferedWriter(file,
				StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			// write header comment
			if (!headerComment.isEmpty()) {
				for (String line : headerComment.split("\r?\n")) {
					w.write("// " + line);
					w.newLine();
				}
			}
			w.write("{");
			w.newLine();
			Field[] fields = type.getDeclaredFields();
			int total = 0;
			for (Field f : fields) {
				f.setAccessible(true);
				try {
					// count only non-null fields
					if (f.get(config) != null) total++;
				} catch (IllegalAccessException ignored) {
				}
			}
			int count = 0;
			for (Field f : fields) {
				f.setAccessible(true);
				Object value;
				try {
					value = f.get(config);
				} catch (IllegalAccessException e) {
					continue;
				}
				if (value == null) continue;
				// write comment
				if (f.isAnnotationPresent(Comment.class)) {
					for (String line : f.getAnnotation(Comment.class).value().split("\r?\n")) {
						w.write("  // " + line);
						w.newLine();
					}
				}
				// determine JSON key
				String jsonKey = f.isAnnotationPresent(Name.class)
						? f.getAnnotation(Name.class).value()
						: f.getName();
				// serialize value
				String valueJson = gson.toJson(value, f.getType());
				count++;
				String comma = count < total ? "," : "";
				w.write("  \"" + jsonKey + "\": " + valueJson + comma);
				w.newLine();
			}
			w.write("}");
		} catch (IOException e) {
			throw new UncheckedIOException("Failed to save config: " + file, e);
		}
	}
}
