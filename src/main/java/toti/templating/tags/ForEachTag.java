package toti.templating.tags;

import java.util.Map;

import toti.templating.Tag;

public class ForEachTag implements Tag {

	@Override
	public String getName() {
		return "foreach";
	}

	@Override
	public String getPairStartCode(Map<String, String> params) {
		if (params.get("map") != null) {
			String[] keyP = parseItem(params.get("key"));
			String[] valueP = parseItem(params.get("value"));
			return String.format(
				"for(%s %s:Template.toMap(%s,%s.class,%s.class).keySet()){"
				+ "%s %s=Template.toMap(%s,%s.class,%s.class).get(%s);",
				keyP[0], keyP[1], params.get("map"), keyP[0], valueP[0],
				valueP[0], valueP[1], params.get("map"), keyP[0], valueP[0], keyP[1]
			);
		}
		String[] item = parseItem(params.get("item"));
		return String.format(
				"for(%s %s:Template.toIterable(%s,%s.class)){",
				item[0], item[1], params.get("collection"), item[0]
		);
	}
	
	private String[] parseItem(String item) {
		String[] items = item.trim().split(" +");
		if (items.length != 2) {
			throw new RuntimeException("Incorrect item");
		}
		return items;
	}

	@Override
	public String getPairEndCode(Map<String, String> params) {
		return "}";
	}

	@Override
	public String getNotPairCode(Map<String, String> params) {
		return "";
	}

}
