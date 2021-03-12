/* TOTI Control version 0.0.3 */
var totiControl = {
	label: function (forInput, title, params = {}) {
		var label = document.createElement("label");
		label.innerText = title;
		label.setAttribute("for", forInput);
		for ([key, name] of Object.entries(params)) {
			label.setAttribute(key, name);
		}
		return label;
	},
	button: function(attributes) {
		var button = document.createElement('button');
		for ([key, name] of Object.entries(attributes)) {
			if (key === "value") {
				button.innerHTML = name;
			} else if (key === "action") {
				if (typeof attributes.action === "object") {
					button.onclick = totiControl.getAction(attributes.action);
				} else {
					button.onclick = function(event) {
						window[attributes.action](event);
					};
				}
			} else if (key === "style") {
				button.className += " toti-button-" + name;
			} else if (key === "class") {
				button.className += " " + name;
			} else {
				button.setAttribute(key, name);
			}
		}
		return button;
	},
	input: function (attributes) {
		if (!attributes.hasOwnProperty('type')) {
			console.error("Missing attribute type", attributes);
			return;
		}
		var type = attributes.type;
		attributes.originType = type;
		delete attributes.type;

		if (type === "checkbox" && attributes.value) {
			attributes.checked = "checked";
		}

		if (type === 'datetime-local') {
			if (attributes.hasOwnProperty("value")) {
				attributes.value = attributes.value.replace(" ", "T");
			}
			if (!attributes.strict) {
				return totiControl.inputs.datetime(attributes);
			}
			var browser = totiUtils.browser();
			if (browser !== "opera" && !browser.includes("chrom")) {
				return totiControl.inputs.datetime(attributes);
				/*console.error("Your browser probably not support datetime-local");*/
			}
			return totiControl.inputs._createInput("datetime-local", attributes);
		} else if (type === 'textarea') {
			return totiControl.inputs.textarea(attributes);
		} else if (type === 'select') {
			return totiControl.inputs.select(attributes);
		} else if (type === 'option') {
			return totiControl.inputs.option(attributes);
		} else if (type === 'radiolist') {
			return totiControl.inputs.radiolist(attributes);
		} else if (type === 'button') {
			return totiControl.inputs.button(attributes);
		} else {
			return totiControl.inputs._createInput(type, attributes);
		}
	},
	inputs: {
		_selectCache: {},
		_createInput: function (type, attributes) {
			var input = document.createElement("input");
			input.setAttribute("type", type);
			for ([key, name] of Object.entries(attributes)) {
				input.setAttribute(key, name);
			}
			return input;
		},
		button: function(attributes) {
			var button = document.createElement("input");
			button.setAttribute("type", "button");
			for ([key, name] of Object.entries(attributes)) {
				if (key === "action") {
					if (typeof attributes.action === "object") {
						button.onclick = totiControl.getAction(attributes.action);
					} else {
						button.onclick = function(event) {
							window[attributes.action](event);
						};
					}
				} else if (key === "style") {
					button.className += " toti-button-" + name;
				} else if (key === "class") {
					button.className += " " + name;
				} else {
					button.setAttribute(key, name);
				}
			}
			return button;
		},
		radiolist: function(params) {
			var input = document.createElement("div");
			params.radios.forEach(function(radio) {
				var item = document.createElement("div");
				var id = params.name + "-" + radio.id;
				item.setAttribute('id', id + "-block");
				if (radio.hasOwnProperty('title')) {
					item.appendChild(totiControl.label(params.id, radio.title, {
						id: id + "-label"
					}));
				}
				var settings = {
					id: id,
					name: params.name,
					form: params.formName,
					value: radio.value,
					type: "radio"
				};
				if (radio.value === params.value) {
					settings.checked = "checked";
				}
				if (params.hasOwnProperty('required')) {
					settings.required = params.required;
				}
				if (params.hasOwnProperty('disabled')) {
					settings.disabled = params.disabled;
				}
				item.appendChild(totiControl.input(settings));
				input.appendChild(item);
			});
			return input;
		},
		textarea: function(params) {
			var textarea = document.createElement('textarea');
			for ([key, name] of Object.entries(params)) {
				if (key === "value") {
					textarea.innerHTML = name;
				} else {
					textarea.setAttribute(key, name);
				}
			}
			return textarea;
		},
		select: function (params) {
			var select = document.createElement('select');
			for ([key, value] of Object.entries(params)) {
				if (key === "options" || key === "load" || key === "value") {
					/* ignored now, done soon */
				} else {
					select.setAttribute(key, value);
				}
			}
			var addOption = function(option, parent) {
				if (option instanceof HTMLOptionElement) {
		            parent.appendChild(option);
		        } else {
	            	option.type = "option";
	                if (option.hasOwnProperty("optgroup")) {
	                  	var optGroup = parent.querySelector('[label="' + option.optgroup + '"]');
		                if (optGroup === null) {
		                    optGroup = document.createElement("optgroup");
		                    optGroup.setAttribute("label", option.optgroup);
		                    parent.appendChild(optGroup);
		                }
		                optGroup.appendChild(totiControl.input(option));
		            } else {
		             	parent.appendChild(totiControl.input(option));
		            }
		            /*if (option.type === "optGroup") {
		                var optGroup = document.createElement("optgroup");
		                optGroup.setAttribute("label", option.title);
		                option.options.forEach(function(item, index) {
		                    addOption(item, optGroup);
		                });
		                parent.appendChild(optGroup);
		            }*/
	        	}
     		};
     		/* options */
			totiUtils.forEach(params.options, function(v, option) {
				addOption(option, select);
			});
			/* load */
			if (params.hasOwnProperty("load")) {
				var cacheKey = JSON.stringify({
					"url": params.load.url,
					"method": params.load.method,
					"params": params.load.params
				});
				var onSuccess = function(loaded) {
					totiUtils.forEach(loaded, function(value, opt) {
                		var option = { "value": value };
                		var use = true;
                  		if (typeof opt === "object") {
                       		option.title = opt.title;
                       		if (opt.disabled) {
	                            option.disabled = "disabled";
	                        }
                       		if (opt.optgroup) {
								if (params.hasOwnProperty("optionGroup")) {
									use = opt.optgroup === params.optionGroup;
								} else {
									option.optgroup = opt.optgroup;
								}
                       		}
	                    } else {
	                       option.title = opt;
	                    }
	                    if (use) {
	                    	params.options[value] = option; /* for value renderer*/
                  			addOption(option, select);
	                    }
                  		
               		});
				};
				if (totiControl.inputs._selectCache.hasOwnProperty(cacheKey)) {
					onSuccess(totiControl.inputs._selectCache[cacheKey]);
				} else {
					totiLoad.async(params.load.url, params.load.method, params.load.params, totiLoad.getHeaders(), function(loaded) {
						onSuccess(loaded);
						totiControl.inputs._selectCache[cacheKey] = loaded;
					}, function(xhr) {
						console.log(xhr);
					}, false);
				}
			}
			/* value */
			if (params.hasOwnProperty("value")) {
				select.value = params.value;
			}
			return select;
		},
		option: function(params) {
			var option = document.createElement('option');
			for ([key, value] of Object.entries(params)) {
				if (key === "title") {
					option.innerHTML = value;
				} else {
					option.setAttribute(key, value);
				}
				
			}
			return option;
		},
		datetime: function(attributes) {
			var dateAttr = {};
			dateAttr.id = attributes.id + "-date";
			var date = totiControl.inputs._createInput("date", dateAttr);

			var timeAttr = {};
			timeAttr.id = attributes.id + "-time"
			if (attributes.hasOwnProperty("step")) {
				timeAttr.step = attributes.step;
			}
			var time = totiControl.inputs._createInput("time", timeAttr);
			
			var div = document.createElement("button");
			for ([key, name] of Object.entries(attributes)) {
				div.setAttribute(key, name);
			}
			var datetime = div;
			/*datetime.setAttribute("type", "datetime-local");
			datetime.setAttribute("origintype", "datetime-local");*/
			div.appendChild(date);
			div.appendChild(time);

			var setValue = function(value) {
				var values = attributes.value.split("T");
				console.log(value, values);
				if (values.length !== 2) {
					return;
				}
				date.value = values[0];
				time.value = values[1];
			};

			if (attributes.hasOwnProperty("value")) {
				setValue(attributes.value);
			}

			datetime.onchange = function(event) {
				if (attributes.strict) {
					if (date.value === '' || time.value === '') {
						event.preventDefault();
					} else {
						datetime.value = date.value + "T" + time.value;
					}
				} else {
					if (date.value === '' && time.value === '') {
						event.preventDefault();
					} else if (date.value === '') {
						datetime.value = time.value;
					} else if (time.value === '') {
						datetime.value = date.value;
					} else {
						datetime.value = date.value + "T" + time.value;
					}
				}
			};
			datetime.onclick = function(event) {
				event.preventDefault();
			};
			return div;
		}
	},
	parseValue: function(type, value) {
		/* TODO week*/
		if (totiTranslations.timestamp.dateString.hasOwnProperty(type) && value !== '' && value !== null) {
			return new Date(value).toLocaleDateString(
            	totiLang.getLang().replace("_", "-"),
				totiTranslations.timestamp.dateString[type]
			);
		}
		if (totiTranslations.timestamp.timeString.hasOwnProperty(type) && value !== '' && value !== null) {
			value = "1970-01-01 " + value;
			return new Date(value).toLocaleTimeString(
            	totiLang.getLang().replace("_", "-"),
				totiTranslations.timestamp.timeString[type]
			);
		}
		return value;
	},
	getAction: function(clickSettings) {
		return function(event) {
			event.preventDefault();
			if (clickSettings.submitConfirmation !== null
				 && clickSettings.submitConfirmation !== undefined 
				 && !clickSettings.submitConfirmation()) {
				return false;
			}
			if (clickSettings.async) {
				totiLoad.async(clickSettings.href, clickSettings.method, {}, totiLoad.getHeaders(), function(res) {
					if (clickSettings.hasOwnProperty('onSuccess')) {
						window[clickSettings.onSuccess](res);
					} else {
						totiDisplay.flash("success", res);
					}
				}, function(xhr) {
					if (clickSettings.hasOwnProperty('onError')) {
						window[clickSettings.onError](xhr);
					} else {
						totiDisplay.flash("error", xhr);
					}
				});
			} else {
				/* totiControl.load.link(href, method, {}, totiControl.getHeaders()); */
				window.location = clickSettings.href;
			}
		};
	}
};
