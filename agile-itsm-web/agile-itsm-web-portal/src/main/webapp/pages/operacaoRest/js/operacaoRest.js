
		var properties = {
			context: ctx
		};

		var popup;
		addEvent(window, "load", load, false);

		function validaDiv(valor) {
			if(valor === 'JavaScript') {
				$("#javaDiv").hide();
				$("#javaScriptDiv").show();
			} else {
				$("#javaDiv").show();
				$("#javaScriptDiv").hide();
			}
		}

		function load() {
			popup = new PopupManager(1000, 900, ctx+"/pages/");
			document.form.afterRestore = function () {
				$('.tabs').tabs('select', 0);
				if (document.form.classType[1].checked) {
					validaDiv("Java");
				} else {
					validaDiv("JavaScript");
				}
			}
		}
	
