<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
    response.setCharacterEncoding("ISO-8859-1");
%>
<HTML>
<HEAD>
    <%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

    <script>var URL_INITIAL = '${ctx}/';</script>

    <TITLE>SlickGrid with Layout</TITLE>

    <LINK type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css">
    <LINK type="text/css" rel="stylesheet" href="${ctx}/css/slick.grid.css">
    <LINK type="text/css" rel="stylesheet" href="${ctx}/css/slick-examples.css">
    <!-- theme is last so will override defaults --->
    <LINK type="text/css" rel="stylesheet" href="${ctx}/css/jquery.ui.all.css">

    <STYLE title="" type="text/css">

    .ui-layout-center ,
    .ui-layout-east ,
    .ui-layout-east .ui-layout-content {
        padding:        0;
        overflow:       hidden;
    }
    .hidden {
        display:        none;
    }
    .ui-widget-header {
        padding:        7px 15px 9px;
    }
    H2.loading {
        border:         0;
        font-size:      24px;
        font-weight:    normal;
        margin:         30% 0 0 40%;
    }
    .cell-title {
        font-weight: bold;
    }
    .cell-effort-driven {
        text-align: center;
    }
    </STYLE>

    <SCRIPT type="text/javascript" src="${ctx}/js/jquery-latest.js"></SCRIPT>
    <SCRIPT type="text/javascript" src="${ctx}/js/jquery-ui-latest.js"></SCRIPT>
    <SCRIPT type="text/javascript" src="${ctx}/js/jquery.layout-latest.js"></SCRIPT>
    <SCRIPT type="text/javascript" src="${ctx}/js/debug.js"></SCRIPT>

    <!-- SlickGrid and its dependancies (not sure what they're for?) --->
    <SCRIPT type="text/javascript" src="${ctx}/js/jquery.rule-1.0.1.1-min.js"></SCRIPT>
    <SCRIPT type="text/javascript" src="${ctx}/js/jquery.event.drag.custom.js"></SCRIPT>

    <SCRIPT type="text/javascript" src="${ctx}/js/slick.editors.js"></SCRIPT>
    <SCRIPT type="text/javascript" src="${ctx}/js/slick.grid.js"></SCRIPT>

    <SCRIPT type="text/javascript">

    function requiredFieldValidator(value) {
        if (value == null || value == undefined || !value.length)
            return {valid:false, msg:"This is a required field"};
        else
            return {valid:true, msg:null};
    };


    var
        myGrid      = {}
    ,   gridData    = []
    ,   gridColumns = [
            { id: "title"           , name: "Title"         , field: "title"            , width: 120    , editor: TextCellEditor , validator: requiredFieldValidator }
        ,   { id: "duration"        , name: "Duration"      , field: "duration"                         , editor: TextCellEditor }
        ,   { id: "%"               , name: "% Complete"    , field: "percentComplete"  , width: 80     , editor: PercentCompleteCellEditor, formatter: GraphicalPercentCompleteCellFormatter, resizable:false }
        ,   { id: "start"           , name: "Start"         , field: "start"            , width: 100    , editor: DateCellEditor }
        ,   { id: "finish"          , name: "Finish"        , field: "finish"           , width: 100    , editor: DateCellEditor }
        ,   { id: "effort-driven"   , name: "Effort Driven" , field: "effortDriven"     , width: 80 , minWidth:20, maxWidth:80, cssClass: "cell-effort-driven", formatter: BoolCellFormatter, editor: YesNoCheckboxCellEditor }
        ,   { id: "c7"              , name: "Description"   , field: "c7"               , width: 200    , editor: TextCellEditor }
        ,   { id: "c8"              , name: "C8"            , field: "c8"               , width: 120    , editor: TextCellEditor }
        ,   { id: "c9"              , name: "C9"            , field: "c9"               , width: 120    , editor: TextCellEditor }
        ,   { id: "c10"             , name: "C10"           , field: "c10"              , width: 120    , editor: TextCellEditor }
        ,   { id: "c11"             , name: "C11"           , field: "c11"              , width: 120    , editor: TextCellEditor }
        ,   { id: "c12"             , name: "C12"           , field: "c12"              , width: 120    , editor: TextCellEditor }
        ,   { id: "c13"             , name: "C13"           , field: "c13"              , width: 120    , editor: TextCellEditor }
        ,   { id: "c14"             , name: "C14"           , field: "c14"              , width: 120    , editor: TextCellEditor }
        ,   { id: "c15"             , name: "C15"           , field: "c15"              , width: 120    , editor: TextCellEditor }
        ,   { id: "c16"             , name: "C16"           , field: "c16"              , width: 120    , editor: TextCellEditor }
        ,   { id: "c17"             , name: "C17"           , field: "c17"              , width: 120    , editor: TextCellEditor }
        ]
    ,   gridOptions = {
            editable:               true
        ,   asyncEditorLoading:     false
        ,   enableAddRow:           true
        ,   enableCellNavigation:   true
        ,   enableColumnReorder:    true
        }
    ;

    for (var i = 10000; i-- > 0;) {
        gridData[i] = {
            title:              "Task " + i
        ,   duration:           "5 days"
        ,   percentComplete:    Math.round(Math.random() * 100)
        ,   start:              "01/01/2009"
        ,   finish:             "01/05/2009"
        ,   effortDriven:       (i % 5 == 0)
        ,   c7:                 "C7-"  + i
        ,   c8:                 "C8-"  + i
        ,   c9:                 "C9-"  + i
        ,   c10:                "C10-" + i
        ,   c11:                "C11-" + i
        ,   c12:                "C12-" + i
        ,   c13:                "C13-" + i
        ,   c14:                "C14-" + i
        ,   c15:                "C15-" + i
        ,   c16:                "C16-" + i
        ,   c17:                "C17-" + i
        };
    };

    var myLayout;

    $(document).ready(function () {

        // create the layout - with data-table wrapper as the layout-content element
        myLayout = $('body').layout({
            west__initClosed:   true
        ,   east__size:         .50
        ,   center__onresize:   function (pane, $pane, state, options) {
                                    var gridHdrH    = $pane.children('.slick-header').outerHeight()
                                    ,   $gridList   = $pane.children('.slick-viewport') ;
                                    $gridList.height( state.innerHeight - gridHdrH );
                                }
        ,   east__onresize:     function (pane, $pane, state, options) {
                                    var $content    = $pane.children('.ui-layout-content')
                                    ,   gridHdrH    = $content.children('.slick-header').outerHeight()
                                    ,   paneHdrH    = $pane.children(':first').outerHeight()
                                    ,   paneFtrH    = $pane.children(':last').outerHeight()
                                    ,   $gridList   = $content.children('.slick-viewport') ;
                                    $gridList.height( state.innerHeight - paneHdrH - paneFtrH - gridHdrH );
                                }
        });

        myGrid  = new Slick.Grid( myLayout.panes.center,  gridData, gridColumns, gridOptions );
        myGrid2 = new Slick.Grid( myLayout.contents.east, gridData, gridColumns, gridOptions );

        $('body > H2.loading').hide(); // hide Loading msg

    });

    </SCRIPT>

</HEAD>
<BODY>
<H2 class="loading">Loading...</H2>
<DIV class="ui-layout-center hidden"></DIV>
<DIV class="ui-layout-north hidden">North</DIV>
<DIV class="ui-layout-south hidden">South</DIV>
<DIV class="ui-layout-west hidden">West</DIV>
<DIV class="ui-layout-east hidden">
    <DIV class="ui-widget-header ui-state-active">East Header</DIV>
    <DIV class="ui-layout-content"></DIV>
    <DIV class="ui-widget-header">East Footer</DIV>
</DIV>
</BODY>
</HTML>

