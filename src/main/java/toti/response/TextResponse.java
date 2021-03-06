package toti.response;

import socketCommunication.http.StatusCode;
import socketCommunication.http.server.RestApiResponse;
import toti.ResponseHeaders;
import toti.security.Authorizator;
import toti.security.Identity;
import toti.templating.TemplateFactory;
import translator.Translator;

public class TextResponse implements Response {

	private final String text;
	private final StatusCode code;
	
	public TextResponse(StatusCode code, String text) {
		this.text = text;
		this.code = code;
	}

	@Override
	public void addParam(String name, Object value) {}
	
	@Override
	public RestApiResponse getResponse(
			ResponseHeaders header,
			TemplateFactory templateFactory, 
			Translator translator, 
			Authorizator authorizator,
			Identity identity,
			String charset) {
		return RestApiResponse.textResponse(code, header.getHeaders(), (bw)->{
			bw.write(text);
		});
	}

}
