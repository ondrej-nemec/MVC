package module.controllers.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import common.Logger;
import module.AuditTrail;
import socketCommunication.http.HttpMethod;
import socketCommunication.http.StatusCode;
import toti.annotations.inject.ClientIdentity;
import toti.annotations.inject.Translate;
import toti.annotations.url.Action;
import toti.annotations.url.Controller;
import toti.annotations.url.Domain;
import toti.annotations.url.Method;
import toti.annotations.url.Param;
import toti.annotations.url.ParamUrl;
import toti.annotations.url.Params;
import toti.annotations.url.Secured;
import toti.authentication.Identity;
import toti.response.Response;
import translator.Translator;

@Controller("entity")
public class EntityApiController {
	
	private final static String SECURITY_DOMAIN = "entity";
	private final static String UNIQUE = "id";
	
	static {
		EntityValidator.init();
	}
	
	@Translate
	private Translator translator;
	
	@ClientIdentity
	private Identity identity;
	
	public void setTranslator(Translator translator) {
		this.translator = translator;
	}
	
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	
	private final EntityDao dao;
	private final Logger logger;
	private final AuditTrail auditTrail;
	
	public EntityApiController(EntityDao dao, Logger logger, AuditTrail auditTrail) {
		this.dao = dao;
		this.logger = logger;
		this.auditTrail = auditTrail;
	}

	@Action(value = "all", validator = EntityValidator.NAME_GRID)
	@Method({HttpMethod.GET})
	//@Secured({@Domain(name=SECURITY_DOMAIN, action=helper.Action.READ)})
	public Response getAll(
			@Param("pageIndex") Integer pageIndex,
			@Param("pageSize") Integer pageSize,
			@Param("filters") Map<String, Object> filters,
			@Param("sorting") Map<String, Object> sorting,
			@Params Properties prop
		) {
		try {
			if (filters.get("is_main") != null && filters.get("is_main").equals("true")) {
				filters.put("is_main",  "t");
			} else if (filters.get("is_main") != null && filters.get("is_main").equals("false")) {
				filters.put("is_main",  "f");
			}
			List<Map<String, Object>> items = dao.getAll(pageIndex, pageSize, filters, sorting);
			
			Map<String, Object> json = new HashMap<>();
			json.put("data", items);
			json.put("itemsCount", dao.getTotalCount());
			json.put("pageIndex", pageIndex);
			return Response.getJson(json);
		} catch (Exception e) {
			logger.error("Entity GetAll", e);
			return Response.getJson(StatusCode.INTERNAL_SERVER_ERROR, new HashMap<>());
		}
	}

	@Action("get")
	@Method({HttpMethod.GET})
	//@Secured({@Domain(name=SECURITY_DOMAIN, action=helper.Action.READ)})
	public Response get(@ParamUrl("id") Integer id) {
		try {
			Map<String, Object> item = dao.get(id);
			/***/
			// TODO edit item
			/***/
			return Response.getJson(item);
		} catch (Exception e) {
			logger.error("Entity Get", e);
			return Response.getJson(StatusCode.INTERNAL_SERVER_ERROR, new HashMap<>());
		}
	}

	@Action("delete")
	@Method({HttpMethod.DELETE})
	//@Secured({@Domain(name=SECURITY_DOMAIN, action=helper.Action.DELETE)})
	public Response delete(@ParamUrl("id") Integer id) {
		try {
			Map<String, Object> deleted = dao.delete(id);
			auditTrail.delete(identity.getUser().getId(), deleted);
			return Response.getText(translator.translate("common.item-deleted"));
		} catch (Exception e) {
			logger.error("Entity Delete", e);
			return Response.getText(StatusCode.INTERNAL_SERVER_ERROR, translator.translate("common.deleting-problem"));
		}
	}

	@Action(value = "update", validator = EntityValidator.NAME_FORM)
	@Method({HttpMethod.PUT})
	//@Secured({@Domain(name=SECURITY_DOMAIN, action=helper.Action.UPDATE)})
	public Response update(@ParamUrl("id") Integer id, @Params Properties prop) {
		try {
			Map<String, Object> origin = dao.get(id);
			Map<String, Object> updated = new HashMap<>();
			prop.forEach((key, value)->{
				updated.put(key.toString(), value);
			});
			
			editValues(updated, false);
			
			dao.update(id, updated);
			auditTrail.update(identity.getUser().getId(), origin, updated);
			
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);
			params.put("message", translator.translate("common.item-updated"));
			return Response.getJson(params);
		} catch (Exception e) {
			logger.error("Entity Update", e);
			return Response.getText(StatusCode.INTERNAL_SERVER_ERROR, translator.translate("common.saving-problem"));
		}
	}

	@Action(value = "insert", validator = EntityValidator.NAME_FORM)
	@Method({HttpMethod.PUT})
	//@Secured({@Domain(name=SECURITY_DOMAIN, action=helper.Action.CREATE)})
	public Response insert(@Params Properties prop) {
		try {
			Map<String, Object> inserted = new HashMap<>();
			prop.forEach((key, value)->{
				inserted.put(key.toString(), value);
			});
			inserted.remove(UNIQUE);
			editValues(inserted, true);
			
			editValues(inserted, true);
			
			int id = dao.insert(inserted);
			inserted.put(UNIQUE, id);
			auditTrail.insert(identity.getUser().getId(), inserted);
			
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);
			params.put("message", translator.translate("common.item-inserted"));
			return Response.getJson(params);
		} catch (Exception e) {
			logger.error("Entity Insert", e);
			return Response.getText(StatusCode.INTERNAL_SERVER_ERROR, translator.translate("common.saving-problem"));
		}
	}

	private void editValues(Map<String, Object> values, boolean insert) {
		if (values.get("is_main") == null) {
			values.put("is_main", false);
		}
	}
	
}
