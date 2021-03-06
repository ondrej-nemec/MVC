package toti;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import common.Logger;
import common.structures.ThrowingFunction;
import socketCommunication.ServerSecuredCredentials;
import toti.security.User;
import translator.LanguageSettings;
import translator.Translator;

public class HttpServerFactory {
	
	// todo maybe regist here too OR only list - new header in creating response
	private ResponseHeaders headers = new ResponseHeaders(Arrays.asList(
			"Access-Control-Allow-Origin: *"
	));
	
	private Translator translator;
	private final Logger logger;
	
	private int port = 80;
	private int threadPool = 5;
	private long readTimeout = 60000;
	private Optional<ServerSecuredCredentials> certs = Optional.empty();
	private String tempPath = "temp";
	private String charset = "UTF-8";
	private LanguageSettings settings = new LanguageSettings(Locale.getDefault().toString(), Arrays.asList());
	private String resourcesPath = "www";
	private boolean deleteTempJavaFiles = true;
	private boolean dirResponseAllowed = true;
	private boolean minimalize = true;
	private List<String> developIps = Arrays.asList("127.0.0.1", "/0:0:0:0:0:0:0:1");
	private int maxUploadFileSize = 0;
	private Optional<List<String>> allowedUploadFileTypes = Optional.of(new LinkedList<>());
	private long tokenExpirationTime = 1000 * 60 * 10;
	private String tokenCustomSalt = "";
	private String redirectNoLoggerdUser = null;
	
	public HttpServerFactory(Logger logger) {
		this.logger = logger;
	}
	
	public <T extends Module> HttpServer get(List<T> modules, ThrowingFunction<String, User, Exception> userFactory) throws Exception {
		return new HttpServer(
				port, threadPool, readTimeout, headers,
				certs, tempPath, modules, userFactory, resourcesPath,
				translator,
				maxUploadFileSize, allowedUploadFileTypes,
				charset, settings, tokenCustomSalt, tokenExpirationTime,
				logger, deleteTempJavaFiles, dirResponseAllowed, minimalize, developIps, redirectNoLoggerdUser
		);
	}

	public HttpServerFactory setDirResponseAllowed(boolean dirResponseAllowed) {
		this.dirResponseAllowed = dirResponseAllowed;
		return this;
	}

	public HttpServerFactory setMaxUploadFileSize(int maxUploadFileSize) {
		this.maxUploadFileSize = maxUploadFileSize;
		return this;
	}

	/**
	 * 
	 * @param allowedUploadFileTypes - empty Optional means all types, Optional with empty list means no types
	 * @return
	 */
	public HttpServerFactory setAllowedUploadFileTypes(Optional<List<String>> allowedUploadFileTypes) {
		this.allowedUploadFileTypes = allowedUploadFileTypes;
		return this;
	}

	public HttpServerFactory setHeaders(ResponseHeaders headers) {
		this.headers = headers;
		return this;
	}

	public HttpServerFactory setTranslator(Translator translator) {
		this.translator = translator;
		return this;
	}
/*
	public HttpServerFactory setLogger(Logger logger) {
		this.logger = logger;
		return this;
	}
*/
	public HttpServerFactory setPort(int port) {
		this.port = port;
		return this;
	}

	public HttpServerFactory setThreadPool(int threadPool) {
		this.threadPool = threadPool;
		return this;
	}

	public HttpServerFactory setReadTimeout(long readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}

	public HttpServerFactory setCerts(ServerSecuredCredentials certs) {
		this.certs = Optional.of(certs);
		return this;
	}

	public HttpServerFactory setTempPath(String tempPath) {
		this.tempPath = tempPath;
		return this;
	}

	public HttpServerFactory setCharset(String charset) {
		this.charset = charset;
		return this;
	}

	public HttpServerFactory setLanguageSettings(LanguageSettings settings) {
		this.settings = settings;
		return this;
	}
	
	public HttpServerFactory setTokenExpirationTime(long tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
		return this;
	}
	
	public HttpServerFactory setTokenCustomSalt(String tokenCustomSalt) {
		this.tokenCustomSalt = tokenCustomSalt;
		return this;
	}

	public HttpServerFactory setResourcesPath(String resourcesPath) {
		this.resourcesPath = resourcesPath;
		return this;
	}

	public HttpServerFactory setDeleteTempJavaFiles(boolean deleteTempJavaFiles) {
		this.deleteTempJavaFiles = deleteTempJavaFiles;
		return this;
	}

	public HttpServerFactory setMinimalize(boolean minimalize) {
		this.minimalize = minimalize;
		return this;
	}

	public HttpServerFactory setDevelopIpAdresses(List<String> developIps) {
		this.developIps = developIps;
		return this;
	}

	public HttpServerFactory setRedirectNoLoggerdUser(String redirectNoLoggerdUser) {
		this.redirectNoLoggerdUser = redirectNoLoggerdUser;
		return this;
	}
	
}
