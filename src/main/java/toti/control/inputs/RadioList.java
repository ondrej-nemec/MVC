package toti.control.inputs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RadioList implements Input {
	
	private final String name;
	private final List<Map<String, String>> radios;
	private final String type;
	private final boolean required;
	private boolean disabled = false;
	private Boolean exclude = null;
	private Boolean editable = null;
	private String value = null;
	private String title = null;
	private final Map<String, String> params = new HashMap<>();
	
	public static RadioList input(String name, boolean required, Map<String, String> radios) {
		return new RadioList(name, required, radios);
	}
	
	private RadioList(String name, boolean required, Map<String, String> radios) {
		this.name = name;
		this.radios = new LinkedList<>();
		radios.forEach((value, title)->{
			Map<String, String> radio = new HashMap<>();
			radio.put("id", "id-" + value);
			radio.put("value", value);
			radio.put("title", title);
			this.radios.add(radio);
		});
		this.type = "radiolist";
		this.required = required;
	}
	
	public RadioList setTitle(String title) {
		this.title = title;
		return this;
	}

	public RadioList addParam(String name, String value) {
		params.put(name, value);
		return this;
	}
	
	public RadioList setDefaultValue(String value) {
		this.value = value;
		return this;
	}
	
	public RadioList setDisabled(boolean disabled) {
		this.disabled = disabled;
		if (exclude == null) {
			exclude = disabled;
		}
		return this;
	}
	
	public RadioList setExclude(boolean exclude) {
		this.exclude = exclude;
		return this;
	}
	
	public RadioList setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}
	
	@Override
	public Map<String, Object> getInputSettings() {
		Map<String, Object> json = new HashMap<>();
		json.put("name", name);
		json.put("radios", radios);
		json.put("type", type);
		params.forEach((key, param)->{
			json.put(key, param);
		});
		if (title != null) {
			json.put("title", title);
		}
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
		if (value != null) {
			json.put("value", value);
		}
		return json;
	}

}
