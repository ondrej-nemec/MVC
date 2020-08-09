package mvc.templating.tags;

import java.util.Map;

import mvc.templating.Tag;

public class ForTag implements Tag {

	@Override
	public String getName() {
		return "for";
	}

	@Override
	public String getStartingCode(Map<String, String> params) {
		return String.format("for(%s;%s;%s){", params.get("from"), params.get("to"), params.get("change"));
	}

	@Override
	public String getClosingCode(Map<String, String> params) {
		return "}";
	}

}
