package toti.control;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import toti.control.inputs.Input;

public class Form implements Jsonable, Control {

	private String bindUrl = null;
	private String bindMethod = "get";
	private final boolean editable;
	
	private final String formId;
	private final String formAction;
	private String formMethod = "get";

	private final List<Map<String, Object>> fields;
	
	public Form(String formId, String action, boolean editable) {
		this.formId = formId;
		this.formAction = action;
		this.fields = new LinkedList<>();
		this.editable = editable;
	}
	
	public Form addInput(Input input) {
		fields.add(input.getInputSettings());
		return this;
	}

	public Form setBindUrl(String bindUrl) {
		this.bindUrl = bindUrl;
		return this;
	}

	public Form setBindMethod(String bindMethod) {
		this.bindMethod = bindMethod;
		return this;
	}
	
	public Form setFormMethod(String formMethod) {
		this.formMethod = formMethod;
		return this;
	}
	
	@Override
	public String toString() {
		Map<String, Object> json = new HashMap<>();
		json.put("formId", formId);
		json.put("action", formAction);
		json.put("method", formMethod);
		json.put("fields", fields);
		json.put("editable", editable);
		if (bindUrl != null) {
			Map<String, Object> bind = new HashMap<>();
			json.put("bind", bind);
			bind.put("url", bindUrl);
			bind.put("method", bindMethod);
		}
		return toJson(json);
	}

	@Override
	public String getType() {
		return "Form";
	}
	
}