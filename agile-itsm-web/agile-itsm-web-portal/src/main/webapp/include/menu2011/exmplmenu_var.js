
var bwr = new cm_bwcheck()
//if (bwr.ns4) alert ("Netscape 4")
//if (bwr.ns6) alert ("Netscape 6")
//if (bwr.ie4) alert ("Internet Explorer 4")
//if (bwr.ie5) alert ("Internet Explorer 5")
//if (bwr.ie6) alert ("Internet Explorer 6")

var oM = new makeCM("oM")

//oCMenu=new makeCM("oCMenu")
oM.pxBetween =10
//Posiciona o menu em rela??o a altura da tela
oM.fromTop=53
oM.fromLeft=500
//Define a posi??o inicial do menu na lina ou na coluna
oM.menuPlacement=new Array(2,70,135,190,255,360,473,543)
//oM.menuPlacement="left"
oM.wait=500
// ESSA IMAGEM RESOLVE O PROBLEMA DAS BORDAS QUE APARECEM NO NETSCAPE
oM.fillImg="../../imagens/cm_fill.gif"

oM.zIndex=100
oM.resizeCheck=1
oM.zIndex=100

//oM.onlineRoot=""
oM.offlineRoot=""

//Define se o menu ? horizontal ou vertical, 1 para horizontal e 0 para vertical
oM.rows=1

//Background bar properties
//propriedades da barra que se encontra atr?s do menu, essas propriedades s?o setadas no css
///////////////////////////////////////////////////////barra atr?s do menu/////////////////////////////////////////////////////////////////////////////////

//set 0 ou 1 em oM.useBar para abilitar essas propriedades
oM.useBar=1

//comprimento da barra
oM.barWidth="700"

oM.barHeight="menu"
oM.barX=0
oM.barY="menu"

//informa qual componente do css ira ser usado
oM.barClass="clBar"

//seta o tamanho das bordas
oM.barBorderX=0
oM.barBorderY=0

//informa qual componente do css ira ser usado
oM.barBorderClass="clB3"
///////////////////////////////////////////////////////barra atr?s do menu/////////////////////////////////////////////////////////////////////////////////

//Level properties
//Syntax for fast creation (advanced users only)
//oM.level[1]=new cm_makeLevel(width,height,regClass,overClass,borderX,borderY,borderClass,rows,align,offsetX,offsetY,arrow,arrowWidth,arrowHeight)
///////////////////////////////////////////////////////menu principal/////////////////////////////////////////////////////////////////////////////////

oM.level[0]=new cm_makeLevel()


oM.level[0].width=11
//altura do menu
oM.level[0].height=15

oM.level[0].align="bottom"

//informa qual componente do css ira ser usado
oM.level[0].regClass="clT"
oM.level[0].overClass="clTover"
oM.level[0].borderClass="clB2"

//seta o tamanho das bordas
oM.level[0].borderX=1
oM.level[0].borderY=1

//Altera os sub-menus para horizontal ou vertical
oM.level[0].rows=0

oM.level[0].align="bottom"

//posiciona os sub-menus em rela??o a lateral esquerda da tela
oM.level[0].offsetX=0
oM.level[0].offsetY=0

oM.level[0].arrow=1000
oM.level[0].arrowWidth=1
oM.level[0].arrowHeight=1

///////////////////////////////////////////////////////menu principal/////////////////////////////////////////////////////////////////////////////////


oM.level[1]=new cm_makeLevel()
oM.level[1].width=300
oM.level[1].height=19

bw.ns4||bw.ns6?oM.level[1].regClass="dlS":oM.level[1].regClass="clS"
bw.ns4||bw.ns6?oM.level[1].overClass="dlSover":oM.level[1].overClass="clSover"
bw.ns4||bw.ns6?oM.level[1].borderClass="dlB1":oM.level[1].borderClass="clB1"

oM.level[1].regClass="clT"
oM.level[1].overClass="clTover"
oM.level[1].borderClass="clB2"

oM.level[1].borderX=0
oM.level[1].borderY=0

oM.level[1].align="right"
oM.level[1].rows=0
oM.level[1].arrow="../../imagens/im_seta.gif"
oM.level[1].arrowWidth=3
oM.level[1].arrowHeight=8
oM.level[1].offsetX=0
oM.level[1].offsetX=-1
oM.level[1].bordercolor="#0099CC"

oM.level[2]=new cm_makeLevel()
oM.level[2].width=300
oM.level[2].height=19

bw.ns4||bw.ns6?oM.level[2].regClass="dlS2":oM.level[2].regClass="clS2"
bw.ns4||bw.ns6?oM.level[2].overClass="dlS2over":oM.level[2].overClass="clS2over"
bw.ns4||bw.ns6?oM.level[2].borderClass="dlB3":oM.level[2].borderClass="clB3"
oM.level[2].borderX=1
oM.level[2].borderY=1
oM.level[2].align="right"
oM.level[2].rows=0
oM.level[2].arrowWidth=5
oM.level[2].arrowHeight=5

oM.level[2].regClass="clT"
oM.level[2].overClass="clTover"
oM.level[2].borderClass="clB2"

//oM.level[2].arrow="../../imagens/im_seta.gif"  // DEFINIR IMAGEM DA SETA DO SUBNIVEL 2

oM.level[3]=new cm_makeLevel()
oM.level[3].width=300
oM.level[3].height=19

bw.ns4||bw.ns6?oM.level[3].regClass="dlS3":oM.level[3].regClass="clS3"
bw.ns4||bw.ns6?oM.level[3].overClass="dlS3over":oM.level[3].overClass="clS3over"
bw.ns4||bw.ns6?oM.level[3].borderClass="dlB3":oM.level[3].borderClass="clB3"
oM.level[3].borderX=1
oM.level[3].borderY=1
oM.level[3].align="right"
oM.level[3].rows=0
oM.level[3].arrowWidth=3
oM.level[3].arrowHeight=8

oM.level[3].regClass="clT"
oM.level[3].overClass="clTover"
oM.level[3].borderClass="clB2"

//oM.level[1]=new cm_makeLevel(width,height,regClass,overClass,borderX,borderY,borderClass,rows,align,offsetX,offsetY,arrow,arrowWidth,arrowHeight)
oM.level[4]=new cm_makeLevel(0,0,"","",3,3,"clB2",0,"right")
oM.level[4].filter=""
oM.level[4].slidepx=10

oM.level[5]=new cm_makeLevel(0,0,"","",3,3,"clB2",0,"right")
oM.level[5].slidepx=0
oM.level[5].clippx=10

oM.level[6]=new cm_makeLevel(0,0,"","",3,3,"clB2",0,"right")
oM.level[6].clippx=10
oM.level[6].regClass="clS"
oM.level[6].overClass="clSover"
oM.level[6].border=null
oM.level[6].borderClass="clB1"

oM.level[7]=new cm_makeLevel(0,0,"","",3,3,"clB2",0,"right")
oM.level[7].clippx=10

oM.level[8]=new cm_makeLevel(0,0,"","",3,3,"clB2",0,"right")
oM.level[6].clippx=10
oM.level[6].regClass="clS"
oM.level[6].overClass="clSover"
oM.level[6].border=null
oM.level[6].borderClass="clB1"


/******************************************
Menu item creation:  1        2          3     4     5       6		7		  8			9			10		  11		12	  13     14     15           16			17
myCoolMenu.makeMenu(name, parent_name, text, link, target, width, height, regImage, overImage, regClass, overClass , align, rows, nolink, onclick, onmouseover, onmouseout)
*************************************/


oM.makeMenu('top0','','Cadastros', '', '',66, 200)
    oM.makeMenu('sub0_1','top0','CBO', '','', 170,'','','','','','','right')
        oM.makeMenu('sub0_1_1','sub0_1','Incluir', raiz + '/pages/cBO/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub0_1_2','sub0_1','Consultar', raiz + '/pages/cBO/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub0_2','top0','Cargos', '','', 170,'','','','','','','right')
    oM.makeMenu('sub0_3','top0','Departamentos', '','', 170,'','','','','','','right')
        oM.makeMenu('sub0_3_1','sub0_3','Incluir', raiz + '/pages/dEPARTAMENTO/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub0_3_2','sub0_3','Consultar', raiz + '/pages/dEPARTAMENTO/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub0_4','top0','Treinamentos', '','', 170,'','','','','','','right')
        oM.makeMenu('sub0_4_1','sub0_4','Incluir', raiz + '/pages/tREINAMENTOS/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub0_4_2','sub0_4','Consultar', raiz + '/pages/tREINAMENTOS/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub0_5','top0','Grupos Treinamentos', '','', 170,'','','','','','','right')
        oM.makeMenu('sub0_5_1','sub0_5','Incluir', raiz + '/pages/gRUPOTREINAMENTO/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub0_5_2','sub0_5','Consultar', raiz + '/pages/gRUPOTREINAMENTO/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub0_6','top0','Sub-Grupos Treinamentos', '','', 170,'','','','','','','right')
        oM.makeMenu('sub0_6_1','sub0_6','Incluir', raiz + '/pages/sUBGRUPOTREINAMENTO/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub0_6_2','sub0_6','Consultar', raiz + '/pages/sUBGRUPOTREINAMENTO/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub0_7','top0','Riscos', '','', 170,'','','','','','','right')
        oM.makeMenu('sub0_7_1','sub0_7','Incluir', raiz + '/pages/rISCOS/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub0_7_2','sub0_7','Consultar', raiz + '/pages/rISCOS/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub0_8','top0','Grupos de Riscos', '','', 170,'','','','','','','right')
        oM.makeMenu('sub0_8_1','sub0_8','Incluir', raiz + '/pages/gRUPORISCO/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub0_8_2','sub0_8','Consultar', raiz + '/pages/gRUPORISCO/exibirConsulta.do','', 170,'','','','','','','right')

oM.makeMenu('top1','','PCMSO', '', '',66, 200)
    oM.makeMenu('sub1_1','top1','Configuracao', '','', 170,'','','','','','','right')
        oM.makeMenu('sub1_1_1','sub1_1','Incluir', raiz + '/pages/pCMSO/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub1_1_2','sub1_1','Consultar', raiz + '/pages/pCMSO/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub1_2','top1','ASO', '','', 170,'','','','','','','right')
        oM.makeMenu('sub1_2_1','sub1_2','ASO', '','', 170,'','','','','','','right')
            oM.makeMenu('sub1_2_1_1','sub1_2_1','Incluir', raiz + '/pages/aSO/exibirInsercao.do','', 170,'','','','','','','right')
            oM.makeMenu('sub1_2_1_2','sub1_2_1','Consultar', raiz + '/pages/aSO/exibirConsulta.do','', 170,'','','','','','','right')
        oM.makeMenu('sub1_2_2','sub1_2','Exames Complementares', '','', 170,'','','','','','','right')
            oM.makeMenu('sub1_2_2_1','sub1_2_2','Incluir', raiz + '/pages/eXAMESRECOMENDADOS/exibirInsercao.do','', 170,'','','','','','','right')
            oM.makeMenu('sub1_2_2_2','sub1_2_2','Consultar', raiz + '/pages/eXAMESRECOMENDADOS/exibirConsulta.do','', 170,'','','','','','','right')
        oM.makeMenu('sub1_2_3','sub1_2','Riscos', '','', 170,'','','','','','','right')
            oM.makeMenu('sub1_2_3_1','sub1_2_3','Incluir', raiz + '/pages/rISCOSASO/exibirInsercao.do','', 170,'','','','','','','right')
            oM.makeMenu('sub1_2_3_2','sub1_2_3','Consultar', raiz + '/pages/rISCOSASO/exibirConsulta.do','', 170,'','','','','','','right')

oM.makeMenu('top2','','PPRA', '', '',66, 200)
    oM.makeMenu('sub2_1','top2','Configuracao', '','', 170,'','','','','','','right')
        oM.makeMenu('sub2_1_1','sub2_1','Incluir', raiz + '/pages/pPRA/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub2_1_2','sub2_1','Consultar', raiz + '/pages/pPRA/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub2_2','top2','Cronogramas de Acoes', '','', 170,'','','','','','','right')
        oM.makeMenu('sub2_2_1','sub2_2','Incluir', raiz + '/pages/cRONOGRAMAACOES/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub2_2_2','sub2_2','Consultar', raiz + '/pages/cRONOGRAMAACOES/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub2_3','top2','Riscos do Departamento', '','', 170,'','','','','','','right')
        oM.makeMenu('sub2_3_1','sub2_3','Incluir', raiz + '/pages/rISCOSDEPARTAMENTO/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub2_3_2','sub2_3','Consultar', raiz + '/pages/rISCOSDEPARTAMENTO/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub2_4','top2','Riscos do Cargo', '','', 170,'','','','','','','right')
        oM.makeMenu('sub2_4_1','sub2_4','Incluir', raiz + '/pages/rISCOSCargos/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub2_4_2','sub2_4','Consultar', raiz + '/pages/rISCOSCargos/exibirConsulta.do','', 170,'','','','','','','right')
    oM.makeMenu('sub2_5','top2','Treinamentos', '','', 170,'','','','','','','right')
        oM.makeMenu('sub2_5_1','sub2_5','Incluir', raiz + '/pages/tREINAMENTOSPPRA/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub2_5_2','sub2_5','Consultar', raiz + '/pages/tREINAMENTOSPPRA/exibirConsulta.do','', 170,'','','','','','','right')

oM.makeMenu('top3','','LTCAT', '', '',66, 200)
    oM.makeMenu('sub3_1','top3','Configuracao', '','', 170,'','','','','','','right')
        oM.makeMenu('sub3_1_1','sub3_1','Incluir', raiz + '/pages/lTCAT/exibirInsercao.do','', 170,'','','','','','','right')
        oM.makeMenu('sub3_1_2','sub3_1','Consultar', raiz + '/pages/lTCAT/exibirConsulta.do','', 170,'','','','','','','right')

oM.makeMenu('top4','',' ', '', '',510, 200)

oM.construct()