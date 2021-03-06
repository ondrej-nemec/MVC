package toti.control.inputs;

import java.util.HashMap;
import java.util.Map;

import toti.control.columns.Filter;

public class Number implements Input, Filter {
	
	private final String name;
	private final String id;
	private final String type;
	private final boolean required;
	private boolean disabled = false;
	private Boolean exclude = null;
	private Boolean editable = null;
	private String placeholder = null;
	
	private Integer step = null;
	private Integer min = null;
	private Integer max = null;
	private String value = null;
	private String title = null;
	private final Map<String, String> params = new HashMap<>();
	
	public static Number filter() {
		String name = "";
		return new Number(name, false);
	}
	
	public static Number input(String name, boolean required) {
		return new Number(name, required);
	}

	public Number addParam(String name, String value) {
		params.put(name, value);
		return this;
	}
	
	private Number(String name, boolean required) {
		this.name = name;
		this.id = "id-" + name;
		this.type = "number";
		this.required = required;
	}
	
	public Number setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public Number setStep(Integer step) {
		this.step = step;
		return this;
	}

	public Number setMin(Integer min) {
		this.min = min;
		return this;
	}

	public Number setMax(Integer max) {
		this.max = max;
		return this;
	}
	
	public Number setDefaultValue(Object value) {
		if (value != null) {
			this.value = value.toString();
		} else {
			this.value = null;
		}
		return this;
	}
	
	public Number setDisabled(boolean disabled) {
		this.disabled = disabled;
		if (exclude == null) {
			exclude = disabled;
		}
		return this;
	}

	public Number setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		return this;
	}
	
	public Number setExclude(boolean exclude) {
		this.exclude = exclude;
		return this;
	}
	
	public Number setEditable(boolean editable) {
		this.editable = editable;
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
		if (value != null) {
			set.put("value", value);
		}
		if (placeholder != null) {
			set.put("placeholder", placeholder);
		}
		set.putAll(params);
		return set;
	}
	
	@Override
	public Map<String, Object> getInputSettings() {
		Map<String, Object> json = getFilterSettings();
		json.put("name", name);
		json.put("id", id);
		json.put("type", type);
		if (required) {
			json.put("required", required);
		}
		if (disabled) {
			json.put("disabled", disabled);
		}
		if (exclude != null) {
			json.put("exclude", exclude);
		}
		if (editable != null) {
			json.put("editable", editable);
		}
		if (title != null) {
			json.put("title", title);
		}
		return json;
	}

}
