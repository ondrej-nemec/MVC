package toti.templating;

import java.io.File;
import java.util.Map;

import toti.security.Authorizator;
import translator.Translator;

public class DirectoryTemplate implements Template {
	
	private final File[] files;
	
	private final String path;
	
	public DirectoryTemplate(File[] files, String path) {
		this.files = files;
		this.path = path;
	}

	@Override
	public long getLastModification() {
		return 0;
	}

	@Override
	public String create(TemplateFactory templateFactory, Map<String, Object> variables, Translator translator, Authorizator authorizator)
			throws Exception {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Folder: <br>");
		
		String filePath = path;
		if (!path.endsWith("/")) {
			filePath += "/";
		}
		if (!path.equals("/")) { // root
			builder.append(String.format("<a href='%s..'>..</a>", filePath));			
			builder.append("<br>");
		}
		
		for(File file : files) {
			builder.append(String.format("<a href='%s'>%s</a>", filePath + file.getName(), file.getName()));			
			builder.append("<br>");
		}
		
		return builder.toString();
	}

}
