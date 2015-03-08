/**
 * Sobrescreve o comportamento do alert exibindo um alert do BootStrap
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 24/11/2014
 */
(function( $ ) {

	var proxied = window.alert;

	window.alert = function(message) {
		if (message.indexOf("Google has disabled use of the Maps API") != -1) {
			$().handleWrongMapsInitialize();
			var warning = '<div class="alert no-key">{0}<a href="{1}" target="_blank"><strong>{2}</strong></a></div>';

			var ixHttps = message.indexOf("http");
			var url = message.substring(ixHttps, message.length);
			var newMessage = message.substring(0, ixHttps);

			$("#map-box-div").html(StringUtils.format(warning, newMessage, url, url));
		} else {
			var alertLen = $("#alerts").length;
			if (alertLen > 0) {
				var warning = '<div id="alert" class="alert alert-info"><button type="button" class="close" data-dismiss="alert">&times;</button>{0}</div>';
				$("#alerts").html(StringUtils.format(warning, message));
				window.setTimeout(function() {
					$("#alert").alert("close");
				}, 5000);
			} else {
				return proxied.apply(this, arguments);
			}
		}
	};

}( jQuery ));
