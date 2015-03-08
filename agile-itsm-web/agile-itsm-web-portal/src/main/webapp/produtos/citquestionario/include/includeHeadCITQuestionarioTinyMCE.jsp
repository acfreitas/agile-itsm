<!-- TinyMCE -->
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
	<script type="text/javascript" src="${ctx}/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
	<script type="text/javascript">
		<%
		Collection lst = (Collection) request.getAttribute("LST_CAMPOS_EDITOR");
		if (lst == null)
			lst = new ArrayList();
		
		for(Iterator it = lst.iterator(); it.hasNext();){
			String fieldName = (String)it.next();
		%>
		tinyMCE.init({
			mode : "exact",
			elements : "<%=fieldName%>",
			theme : "advanced",
			plugins : "safari,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups",
	
			// Theme options
			theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
			theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
			theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
			theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_statusbar_location : "bottom",
			theme_advanced_resizing : true,		
	
			// Example word content CSS (should be your site CSS) this one removes paragraph margins
			content_css : "css/word.css",
	
			// Drop lists for link/image/media/template dialogs
			template_external_list_url : "${ctx}/tinymce/examples/lists/template_list.js",
			external_link_list_url : "${ctx}/tinymce/examples/lists/link_list.js",
			external_image_list_url : "${ctx}/tinymce/examples/lists/image_list.js",
			media_external_list_url : "${ctx}/tinymce/examples/lists/media_list.js",
	
			// Replace values for the template plugin
			template_replace_values : {
				username : "Some User",
				staffid : "991234"
			}		
		});
		
		<%
			}
		%>
	</script>	
<!-- /TinyMCE -->
