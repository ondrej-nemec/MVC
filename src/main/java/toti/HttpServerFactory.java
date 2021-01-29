package toti;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import acl.Action;
import acl.RulesDao;
import acl.structures.AclDestination;
import acl.structures.AclRole;
import acl.structures.AclUser;
import acl.structures.Rules;
import common.Logger;
import socketCommunication.ServerSecuredCredentials;
import toti.authentication.UserSecurity;
import translator.Translator;

public class HttpServerFactory {
	
	// todo maybe regist here too
	private ResponseHeaders headers = new ResponseHeaders(Arrays.asList(
			"Access-Control-Allow-Origin: *"
	));
	
	private UserSecurity security;
	
	private Translator translator;
	private Logger logger;
	
	private int port = 80;
	private int threadPool = 5;
	private long readTimeout = 60000;
	private Optional<ServerSecuredCredentials> certs = Optional.empty();
	private String tempPath = "temp";
	private String charset = "UTF-8";
	private String defLang = Locale.getDefault().toString();
	private String resourcesPath = "www";
	private boolean deleteTempJavaFiles = true;
	private boolean dirResponseAllowed = true;
	private boolean minimalize = true;
	private int maxUploadFileSize = 0;
	private Optional<List<String>> allowedUploadFileTypes = Optional.of(new LinkedList<>());
	
	public HttpServer get(List<Module> modules) throws Exception {
		if (security == null) {
			this.security = new UserSecurity(
				null, // no redirect, on 4xx
				(identity)->new AclUser() {// FULL control
						@Override public Object getId() { return ""; }
						@Override public int getRank() { return 0; }
						@Override public List<AclRole> getRoles() { return new LinkedList<>(); }
				},
				new RulesDao() {
					@Override public Rules getRulesForUserAndGroups(AclUser user, AclDestination domain) {
						return Rules.forUserWithOwner(Action.ADMIN, null);
					}
				},
				1000 * 60 * 10, // 10 min
				"", // salt
				logger
			);
		}
		return new HttpServer(
				port, threadPool, readTimeout, headers,
				certs, tempPath, modules, resourcesPath,
				translator, security,
				maxUploadFileSize, allowedUploadFileTypes,
				charset, defLang,
				logger, deleteTempJavaFiles, dirResponseAllowed, minimalize
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

	public HttpServerFactory setAllowedUploadFileTypes(Optional<List<String>> allowedUploadFileTypes) {
		this.allowedUploadFileTypes = allowedUploadFileTypes;
		return this;
	}

	public HttpServerFactory setHeaders(ResponseHeaders headers) {
		this.headers = headers;
		return this;
	}

	public HttpServerFactory setUserSecurity(UserSecurity security) {
		this.security = security;
		return this;
	}

	public HttpServerFactory setTranslator(Translator translator) {
		this.translator = translator;
		return this;
	}

	public HttpServerFactory setLogger(Logger logger) {
		this.logger = logger;
		return this;
	}

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

	public HttpServerFactory setCerts(Optional<ServerSecuredCredentials> certs) {
		this.certs = certs;
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

	public HttpServerFactory setDefLang(String defLang) {
		this.defLang = defLang;
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

}
