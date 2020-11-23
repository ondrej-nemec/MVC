package adminer;

import java.util.List;

import common.Logger;
import toti.Module;
import toti.Router;
import toti.Task;
import toti.registr.Registr;
import utils.Env;

public class AdminerModule implements Module {

	@Override
	public Module initInstances(Registr registr) {
		registr.addFactory(Adminer.class, ()->{
			return new Adminer();
		});
		return this;
	}

	@Override
	public void addRoutes(Router router) {
		
	}

	@Override
	public String getTemplatesPath() {
		return "templates";
	}

	@Override
	public String getControllersPath() {
		return "adminer";
	}

	@Override
	public String getName() {
		return "adminer";
	}

	@Override
	public String getTranslationPath() {
		return "translations/adminer";
	}

	@Override
	public List<Task> getTasks(Env env, Logger logger) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMigrationsPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
