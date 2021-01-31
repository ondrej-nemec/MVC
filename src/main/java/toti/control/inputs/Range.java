package toti.control.inputs;

import java.util.HashMap;
import java.util.Map;

import toti.control.columns.Filter;

public class Range implements Input, Filter {
	
	private final String name;
	private final String id;
	private final String type;
	private final boolean required;
	private boolean disabled = false;
	
	private Integer step = null;
	private Integer min = null;
	private Integer max = null;
	private String value = null;
	private String title = null;
	private final Map<String, String> params = new HashMap<>();
	
	public static Range filter() {
		String name = "";
		return new Range(name, false);
	}
	
	public static Range input(String name, boolean required) {
		return new Range(name, required);
	}

	public Range addParam(String name, String value) {
		params.put(name, value);
		return this;
	}
	
	private Range(String name, boolean required) {
		this.name = name;
		this.id = "id-" + name;
		this.type = "range";
		this.required = required;
	}
	
	public Range setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public Range setStep(Integer step) {
		this.step = step;
		return this;
	}

	public Range setMin(Integer min) {
		this.min = min;
		return this;
	}

	public Range setMax(Integer max) {
		this.max = max;
		return this;
	}
	
	public Range setDefaultValue(String value) {
		this.value = value;
		return this;
	}
	
	public Range setDisabled(boolean disabled) {
		this.disabled = disabled;
		return this;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public Map<String, Object> getFilterSettings() {
		Map<String, Object> set = new HashMap<>();
		if (step != null) {
			set.put("step", step);
		}
		if (max != null) {
			set.put("max", max);
		}
		if (min != null) {
			set.put("min", min);
		}
		return set;
	}
	
	@Override
	public Map<String, Object> getInputSettings() {
		Map<String, Object> json = new HashMap<>(getFilterSettings());
		json.put("name", name);
		json.put("id", id);
		json.put("type", type);
		if (required) {
			json.put("required", required);
		}
		if (disabled) {
			json.put("disabled", disabled);
		}
		params.forEach((key, param)->{
			json.put(key, param);
		});
		if (title != null) {
			json.put("title", title);
		}
		if (value != null) {
			json.put("value", value);
		}
		return json;
	}

}