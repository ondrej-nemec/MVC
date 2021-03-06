package toti.control.inputs;

import java.util.HashMap;
import java.util.Map;

import toti.control.columns.Filter;

public class Month implements Input, Filter {
	
	private final String name;
	private final String id;
	private final String type;
	private String title;	
	private final boolean required;
	private boolean disabled = false;
	private Boolean exclude = null;
	private Boolean editable = null;
	private String value = null;
	private final Map<String, String> params = new HashMap<>();
	
	public static Month input(String name, boolean required) {
		return new Month(name, required);
	}

	public static Month filter() {
		String name = "";
		return new Month(name, false);
	}
	
	private Month(String name, boolean required) {
		this.name = name;
		this.id = "id-" + name;
		this.type = "month";
		this.required = required;
	}

	public Month addParam(String name, String value) {
		params.put(name, value);
		return this;
	}
	
	public Month setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public Month setDefaultValue(String value) {
		this.value = value;
		return this;
	}
	
	public Month setDisabled(boolean disabled) {
		this.disabled = disabled;
		if (exclude == null) {
			exclude = disabled;
		}
		return this;
	}
	
	public Month setExclude(boolean exclude) {
		this.exclude = exclude;
		return this;
	}
	
	public Month setEditable(boolean editable) {
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
		set.putAll(params);
		if (value != null) {
			set.put("value", value);
		}
		return set;
	}

	@Override
	public Map<String, Object> getInputSettings() {
		Map<String, Object> json = new HashMap<>();
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
