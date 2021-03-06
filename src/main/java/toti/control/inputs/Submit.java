package toti.control.inputs;

import java.util.HashMap;
import java.util.Map;

import toti.control.Control;

public class Submit implements Input {
	
	private final String type;
	private final String name;
	private final String title;
	private String redirect;
	private String confirmation;
	private boolean ajax = true;
	private String onFailure = null;
	private String onSuccess = null;
	private final Map<String, String> params = new HashMap<>();
	
	public static Submit create(String title, String name) {
		return new Submit(title, name);
	}
	
	private Submit(String title, String name) {
		this.type = "submit";
		this.title = title;
		this.name = name;
	}

	// after success ajax request, redirect
	public Submit setRedirect(String redirect) {
		this.redirect = redirect;
		return this;
	}

	public Submit setAsync(boolean async) {
		this.ajax = async;
		return this;
	}

	public Submit setOnSuccess(String onSuccess) {
		this.onSuccess = onSuccess;
		return this;
	}

	public Submit setOnFailure(String onFailure) {
		this.onFailure = onFailure;
		return this;
	}

	public Submit setConfirmation(String confirmation) {
		this.confirmation = Control.escapeJs(confirmation);
		return this;
	}

	public Submit addParam(String name, String value) {
		params.put(name, value);
		return this;
	}
	
	@Override
	public Map<String, Object> getInputSettings() {
		Map<String, Object> json = new HashMap<>();
		json.put("type", type);
		json.put("value", title);
		json.put("ajax", ajax);
		json.put("id", name);
		json.put("name", name);
		params.forEach((key, param)->{
			json.put(key, param);
		});
		if (redirect != null) {
			json.put("redirect", redirect);
		}
		if (confirmation != null) {
			json.put("confirmation", confirmation);
		}
		if (onSuccess != null) {
			json.put("onSuccess", onSuccess);
		}
		if (onFailure != null) {
			json.put("onFailure", onFailure);
		}
		return json;
	}
	
}
