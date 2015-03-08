<%@page import="java.io.FileInputStream"%>
<%@page import="br.com.centralit.citcorpore.util.CITCorporeUtil"%>
<%@page import="java.io.File"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%
    String path = request.getParameter("path");
path = UtilStrings.nullToVazio(path);

File f = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload");
if (!f.exists()){
	f.mkdirs();
}	

f = new File(path);
byte[] byteFile = null;
try{
	FileInputStream fis = new FileInputStream(path);
	byteFile = new byte[(int) f.length()];
	fis.read(byteFile);
}catch(Exception e){
}

if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("XLS")) {
	response.setContentType("application/x-msdownload");
} else if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("PPS")
		|| CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("PPT")) {
	response.setContentType("application/powerpoint");
} else if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("DOC")) {
	response.setContentType("application/x-msdownload");
} else if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("PDF")) {
	response.setContentType("application/pdf");
} else if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("TXT")) {
	response.setContentType("application/x-msdownload");
} else if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("ZIP")) {
	response.setContentType("application/zip");
} else if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("RTF")) {
	response.setContentType("application/rtf");
} else if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("JPG")
		|| CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("JPEG")
		|| CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("JPE")) {
	response.setContentType("image/jpeg");
} else if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("GIF")) {
	response.setContentType("image/gif");
} else if (CITCorporeUtil.getExtensao(path).trim().equalsIgnoreCase("PNG")) {
	response.setContentType("image/png");
}
response.setHeader("Content-Disposition", "attachment; filename="
		+ CITCorporeUtil.getNameFile(path));

if (byteFile != null){
	ServletOutputStream outputStream = response.getOutputStream();
	response.setContentLength(byteFile.length);
	outputStream.write(byteFile);
	outputStream.flush();
	outputStream.close();
}
%>