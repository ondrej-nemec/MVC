package mvc.templating.tags;

import java.util.Map;

import mvc.templating.Tag;

public class FinallyTag implements Tag {

	@Override
	public String getName() {
		return "finally";
	}

	@Override
	public String getPairStartCode(Map<String, String> params) {
		return getNotPairCode(params);
	}

	@Override
	public String getPairEndCode(Map<String, String> params) {
		return "";
	}

	@Override
	public String getNotPairCode(Map<String, String> params) {
		return "}finally{";
	}

}
