/* In?cio do C?digo do Menu.
*/
var isDOM = (document.getElementById ? true : false);
var isIE4 = ((document.all && !isDOM) ? true : false);
var isNS4 = (document.layers ? true : false);
function getRef(id) {
	if (isDOM) return document.getElementById(id);
	if (isIE4) return document.all[id];
	if (isNS4) return document.layers[id];
}
function getSty(id) {
	return (isNS4 ? getRef(id) : getRef(id).style);
} 
var popTimer = 0;
var litNow = new Array();
function popOver(menuNum, itemNum) {
		menuOver(); //Fun??o respons?vel por retirar visibilidade dos campos select (fun??o fica na p?gina)
		clearTimeout(popTimer);
		hideAllBut(menuNum);
		litNow = getTree(menuNum, itemNum);
		changeCol(litNow, true);
		targetNum = menu[menuNum][itemNum].target;
		if (targetNum > 0) {
			thisX = parseInt(menu[menuNum][0].ref.left) + parseInt(menu[menuNum][itemNum].ref.left);
			thisY = parseInt(menu[menuNum][0].ref.top) + parseInt(menu[menuNum][itemNum].ref.top);
			with (menu[targetNum][0].ref) {
				left = parseInt(thisX + menu[targetNum][0].x);
				top = parseInt(thisY + menu[targetNum][0].y);
				visibility = 'visible';
			}
		}
}
function popOut(menuNum, itemNum) {
	menuOut(); //Fun??o respons?vel por setar visibilidade dos campos select (fun??o fica na p?gina)
	if ((menuNum == 0) && !menu[menuNum][itemNum].target)
		hideAllBut(0)
	else
		popTimer = setTimeout('hideAllBut(0)', 500);
}
function getTree(menuNum, itemNum) {
	itemArray = new Array(menu.length);
	while(1) {
		itemArray[menuNum] = itemNum;
		if (menuNum == 0) return itemArray;
			itemNum = menu[menuNum][0].parentItem;
		menuNum = menu[menuNum][0].parentMenu;
   }
}
function changeCol(changeArray, isOver) {
	for (menuCount = 0; menuCount < changeArray.length; menuCount++) {
		if (changeArray[menuCount]) {
			newCol = isOver ? menu[menuCount][0].overCol : menu[menuCount][0].backCol;
			with (menu[menuCount][changeArray[menuCount]].ref) {
				if (isNS4) bgColor = newCol;
				else backgroundColor = newCol;
         }
      }
   }
}
function hideAllBut(menuNum) {
	var keepMenus = getTree(menuNum, 1);
	for (count = 0; count < menu.length; count++)
		if (!keepMenus[count])
			menu[count][0].ref.visibility = 'hidden';
	changeCol(litNow, false);
}
function Menu(isVert, popInd, x, y, width, overCol, backCol, borderClass, textClass) {
	this.isVert = isVert;
	this.popInd = popInd
	this.x = x;
	this.y = y;
	this.width = width;
	this.overCol = overCol;
	this.backCol = backCol;
	this.borderClass = borderClass;
	this.textClass = textClass;
	this.parentMenu = null;
	this.parentItem = null;
	this.ref = null;
}
/* Esta fun??o define os par?metros recebidos pelos itens do menu.
*/
function Item(text, href, frame, length, spacing, target, alinhamento, img, itemX, itemY) {
	this.text = text;
	this.href = href;
	this.frame = frame;
	this.length = length;
	this.spacing = spacing;
	this.target = target;
	this.ref = null;
	this.alinhamento = alinhamento;
	this.img = img;
	this.itemX = itemX;
	this.itemY = itemY;
}
function writeMenus() {
	if (!isDOM && !isIE4 && !isNS4) return;
	for (currMenu = 0; currMenu < menu.length; currMenu++) with (menu[currMenu][0]) {
		/* Posicionamento do menu ? setado nos itens abaixo:
		   itemX e itemY
		   Obs: Defina primeiramente o local em que se posicionar? seu menu
		   para depois arrumar o posicionamento dos subitens.
		*/
		var str = '', itemX = 0, itemY = 0;
		
		str = '<div id="dvMenuRoot">';  //#######
		
		for (currItem = 1; currItem < menu[currMenu].length; currItem++) with (menu[currMenu][currItem]) {
			var itemID = 'menu' + currMenu + 'item' + currItem;
			var w = (isVert ? width : length);
			var h = (isVert ? length : width);
			if (isDOM || isIE4) {
				if(document.all) itemX += 6;
				str += '<div id="' + itemID + '" style="position: absolute; left: ' + itemX + '; top: ' + itemY + '; width: ' + w + '; height: ' + h + '; visibility: inherit; z-index:1001; ';
				if (backCol) str += 'background: ' + backCol + '; ';
				str += '" ';
			}
			if (isNS4) {
				str += '<layer id="' + itemID + '" left="' + itemX + '" top="' + itemY + '" width="' +  w + '" height="' + h + '" visibility="inherit" z-index="1001" ';
				if (backCol) str += 'bgcolor="' + backCol + '" ';
			}
			if (borderClass) str += 'class="' + borderClass + '" ';
				str += 'onMouseOver="popOver(' + currMenu + ',' + currItem + ')" onMouseOut="popOut(' + currMenu + ',' + currItem + ')">';
			str += '<table width="' + (w - 8) + '" border="0" cellspacing="0" cellpadding="' + (!isNS4 && borderClass ? 3 : 0) + '"><tr><td align="'+alinhamento+'" height="' + (h - 7) + '">' + '<a class="' + textClass + '" href="javascript:loadPage(\'' + href + '\');"' + (frame ? ' target="' + frame + '">' : '>') + img + text + '</a></td>';
			if (target > 0) {
				menu[target][0].parentMenu = currMenu;
				menu[target][0].parentItem = currItem;
				if (popInd) str += '<td class="' + textClass + '" align="right">' + popInd + '</td>';
			}
			str += '</tr></table>' + (isNS4 ? '</layer>' : '</div>');
			if (isVert) itemY += length + spacing;
			else itemX += length + spacing;
		}
		
		str += '</div>'; //#######
		
		if (isDOM) {
			var newDiv = document.createElement('div');
			document.getElementsByTagName('body').item(0).appendChild(newDiv);
			newDiv.innerHTML = str;
			ref = newDiv.style;
			ref.position = 'absolute';
			ref.visibility = 'hidden';
		}
		/* Testando e incluindo fun??es para Internet Explorer 4.0
		*/
		if (isIE4) {
			document.body.insertAdjacentHTML('beforeEnd', '<div id="menu' + currMenu + 'div" ' + 'style="position: absolute; visibility: hidden">' + str + '</div>');
			ref = getSty('menu' + currMenu + 'div');
		}
		/* Testando e incluindo fun??es para o Nestcape 4.6.
		*/
		if (isNS4) {
			ref = new Layer(0);
			ref.document.write(str);
			ref.document.close();
		}
		for (currItem = 1; currItem < menu[currMenu].length; currItem++) {
			itemName = 'menu' + currMenu + 'item' + currItem;
			if (isDOM || isIE4) menu[currMenu][currItem].ref = getSty(itemName);
			if (isNS4) menu[currMenu][currItem].ref = ref.document[itemName];
		}
	}
	with(menu[0][0]) {
		ref.left = x;
		ref.top = y;
		ref.visibility = 'visible';
	}

	for (currMenu = 0; currMenu < menu.length; currMenu++) {
		for (currItem = 1; currItem < menu[currMenu].length; currItem++) {
			popOver(currMenu, currItem);
			popOut(currMenu, currItem);
		}
	}
	//Fim do fragmento
	
}

/* Fun??es indispens?veis para o funcionamento no Netscape 4.6
*/ 
var popOldWidth = window.innerWidth;
nsResizeHandler = new Function('if (popOldWidth != window.innerWidth) location.reload()');
if (isNS4) document.captureEvents(Event.CLICK);
	document.onclick = clickHandle;
function clickHandle(evt) {
	if (isNS4) document.routeEvent(evt);
		hideAllBut(0);
}
function moveRoot() {
	with(menu[0][0].ref) left = ((parseInt(left) < 100) ? 100 : 5);
}

function setFormVisibility(visible){
	var visibility = visible ? "" : "hidden";
	var allSelects = document.getElementsByTagName("select");
	for (var i = 0; i < allSelects.length; i++) {
		allSelects[i].style.visibility = visibility;
	}
}
	
var overId = 0;

function menuOver(){
	setFormVisibility(false);
	if(overId != 0){clearTimeout(overId);}
}
function menuOut(){
	overId = setTimeout("doMenuOut()", 150);
}

function doMenuOut(){
	setFormVisibility(true);
}
var MENU_requisicao = null;
function loadPage(page){
	$('areaUtilAplicacao').innerHTML = 'Aguarde... carregando a pagina...';
	JANELA_AGUARDE_MENU.show();
	window.location = page;
	/*
	MENU_requisicao = DEFINEALLPAGES_getObjectAJAX();
	DEFINEALLPAGES_submitObject(MENU_requisicao,null,page,MENU_fCallBackLoad);
	*/
}
function MENU_fCallBackLoad(){
	if (MENU_requisicao.readyState == 4){
		if (MENU_requisicao.status == 200){
			$('areaUtilAplicacao').innerHTML = MENU_requisicao.responseText;
			DEFINEALLPAGES_trataScripts(MENU_requisicao.responseText);
			DEFINEALLPAGES_setaIds();
			MENU_requisicao = DEFINEALLPAGES_getObjectAJAX();
			var urlGetValuesProcessed = 'http://localhost:2020/CitCorporeWeb/pages/empregado/empregado.get';
			DEFINEALLPAGES_submitObject(MENU_requisicao, null, urlGetValuesProcessed, MENU_fCallBackValuesLoad);
		}
	}
}
function MENU_fCallBackValuesLoad(){
	if (MENU_requisicao.readyState == 4){
		if (MENU_requisicao.status == 200){
			DEFINEALLPAGES_fCallBackLoadDirect(MENU_requisicao.responseText);
			var tabberArgs = {};
			tabberAutomatic(tabberArgs);
			JANELA_AGUARDE_MENU.hide();
		}	
	}
}