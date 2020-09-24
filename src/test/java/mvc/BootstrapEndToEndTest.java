package mvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;

import controllers.LoginController;
import controllers.TestController;
import logging.LoggerFactory;
import mvc.authentication.Authenticator;
import mvc.authentication.TokenType;
import mvc.authentication.storage.NullStorage;
import mvc.registr.Registr;
import translator.PropertiesTranslator;

public class BootstrapEndToEndTest {

	public static void main(String[] args) {
		try {
			Registr.get().addFactory(TestController.class, ()->{
				return new TestController();
			});
			Registr.get().addFactory(controllers2.TestController.class, ()->{
				return new controllers2.TestController();
			});
			Registr.get().addFactory(LoginController.class, ()->{
				return new LoginController();
			});

			Authenticator authenticator = new Authenticator(
					120000,
					TokenType.COOKIE(),
					new NullStorage(),
					"secretSalt",
					LoggerFactory.getLogger("auth")
			);		
			Router router = new Router();
			router.addUrl("/jsgrid", "/base/grid");
			router.addUrl("", "/index.html");
			router.addUrl("/grid/index.html", "/grid");
			
			Map<String, String> folders = new HashMap<>();
			folders.put("controllers", "templates");
			folders.put("controllers2", "templates2");
			
			Bootstrap b = new Bootstrap(
					80, 10, 60000, 3600000, // 1 h
					"temp", folders, "www",
					new ResponseHeaders(
						RandomStringUtils.randomAlphanumeric(50),
						Arrays.asList(
							"Access-Control-Allow-Origin: *"
						)
					),
					Optional.empty(),
					"utf-8",
					(loc)->new PropertiesTranslator(LoggerFactory.getLogger("translator"), "messages"),
					authenticator,
					router,
					LoggerFactory.getLogger("server"), false
			);
			b.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
