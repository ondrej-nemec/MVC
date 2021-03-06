package toti.control.inputs;

import java.util.HashMap;
import java.util.Map;

import toti.control.columns.Filter;

public class Text implements Input, Filter {
	
	private final String name;
	private final String id;
	private final String type;
	private String title;	
	private final boolean required;
	private boolean disabled = false;
	private Boolean exclude = null;
	private Boolean editable = null;
	
	private Integer size = null;
	private Integer maxLength = null;
	private Integer minLength = null;
	private String value = null;
	private String placeholder = null;
	private final Map<String, String> params = new HashMap<>();
	
	public static Text input(String name, boolean required) {
		return new Text(name, required);
	}
	
	public static Text filter() {
		String name = "";
		return new Text(name, false);
	}
	
	private Text(String name, boolean required) {
		this.name = name;
		this.id = "id-" + name;
		this.type = "text";
		this.required = required;
	}

	public Text addParam(String name, String value) {
		params.put(name, value);
		return this;
	}
	
	public Text setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public String getType() {
		return type;
	}
	
	public Text setSize(Integer size) {
		this.size = size;
		return this;
	}

	public Text setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
		return this;
	}

	public Text setMinLength(Integer minLength) {
		this.minLength = minLength;
		return this;
	}
	
	public Text setDefaultValue(Object value) {
		if (value != null) {
			this.value = value.toString();
		} else {
			this.value = null;
		}
		return this;
	}
	
	public Text setDisabled(boolean disabled) {
		this.disabled = disabled;
		if (exclude == null) {
			exclude = disabled;
		}
		return this;
	}

	public Text setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		return this;
	}
	
	public Text setExclude(boolean exclude) {
		this.exclude = exclude;
		return this;
	}
	
	public Text setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}

	@Override
	public Map<String, Object> getFilterSettings() {
		Map<String, Object> set = new HashMap<>();
		if (size != null) {
			set.put("size", size);
		}
		if (maxLength != null) {
			set.put("maxlength", maxLength);
		}
		if (minLength != null) {
			set.put("minlength", minLength);
		}
		set.putAll(params);
		if (value != null) {
			set.put("value", value);
		}
		if (placeholder != null) {
			set.put("placeholder", placeholder);
		}
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
