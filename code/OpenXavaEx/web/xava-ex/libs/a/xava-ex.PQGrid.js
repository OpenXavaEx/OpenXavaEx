/**
 * The PQGrid related javascript client code, mostly the customized renderer and editor.
 * depends on xava-ex.js
 */
(function(global){
    if (! global.xavaEx){
        var err = "xavaEx.PQGrid depends on xava-ex.js, please fix the javascript importing!";
        alert(err); throw(err);
    }
    
    /** Mapping of prototype name and it's handler */
    var prototypeMapping = {};
    
    /**
     * Register prototype handler;
     * A handler has following properties:
     *   1. render: render(ui, ctx) of PQGrid colModel
     *     - ctx: {rawValue: ...}
     *   2. editor: editor(ui, ctx) of PQGrid colModel
     *     - ctx: {rawValue: ...}
     *   3. getEditCellData: getEditCellData(ui, ctx) of PQGrid colModel
     *     - ctx: {//BLANK}
     *   4. basePrototype: the base prototype which this handler override
     */
    var registerPrototype = function(name, handler){
        prototypeMapping[name] = handler;
    }
    var _getByPrototype = function(prototype, field, defValue){
        if (! prototype){
            return defValue;
        }
        
        var hdl = prototypeMapping[prototype];
        if (! hdl){
            return defValue;
        }
        var fld = hdl[field];
        if (fld){
            return fld;
        }
        
        var baseName = hdl.basePrototype;
        if (baseName){
        	var bHdl = prototypeMapping[baseName];
        	if (bHdl){
                var bFld = bHdl[field];
                if (bFld){
                    return bFld;
                }
        	}
        }
        
        return defValue;
    }
    
    var _doRealRender = function(ui){
        var cm = ui.column;
        
        var render = _getByPrototype(cm.prototype, "render", theDefaultRender);
        var val = render.call(this, ui, {rawValue: _getUiData(ui)});

        if (cm.action){
            var actStyle = cm.actionStyle?cm.actionStyle:"";
            val = '<a href="javascript:void(0)" class="xava-ex-pqgrid-action"'+actStyle+'>' + val + '</a>';
        }
        return val;
    }
    var _doRealEditor = function(ui){
        var cm = this;
        var editor = _getByPrototype(cm.prototype, "editor", theDefaultEditor);
        editor.call(this, ui, {rawValue: _getUiData(ui)});
    }
    var _doRealGetEditCellData =function(ui){
        var cm = this;
        var getEditCellData = _getByPrototype(cm.prototype, "getEditCellData", theDefaultGetEditCellData);
        var text = getEditCellData.call(this, ui, {});
        return text;
    }
    
    var _getUiData = function(ui){
        var data = ui.data, rowIndx = ui.rowIndxPage, dataIndx = ui.dataIndx||ui.colIndx;
        var d = data[rowIndx][dataIndx];
        return d;
    }
    var theDefaultRender = function(ui){
        return _getUiData(ui);
    }
    var theDefaultEditor = function(ui){
        var $cell = ui.$cell, data = ui.data, rowIndx = ui.rowIndxPage, colIndx = ui.colIndx;
        var val = _getUiData(ui);
        var input = _createEditInput($cell, val);
        input.select();
    }
    var theDefaultGetEditCellData =function(ui){
        var $cell = ui.$cell;
        var text = $cell.children().val();
        return text;
    }
    
    var _createEditInput = function($cell, textValue){
        $cell.css('padding', '0');
        var cellH = $cell.css("height");
        var cellW = $cell.css("width");
        var $ = jQuery;
        var $inp = $("<input type='text' style='padding:0px;border:0;vertical-align:middle;width:"+cellW+";height:"+cellH+"'/>");
        $inp.appendTo($cell).val(textValue);
        return $inp;
    }
    var _doDateTimeRender = function(ui, format){
        xavaEx.checkDependence("moment", "dateRender", "Moment.js, http://momentjs.com/");
        var d = _getUiData(ui);
        return (d)?moment(d).format(format):"";
    }
    var _doDateTimeEditor = function(ui, format, callback){
        xavaEx.checkDependence("moment", "dateRender", "Moment.js, http://momentjs.com/");
        xavaEx.checkDependence("jQuery", "dateRender", "jquery");
        
        var $cell = ui.$cell, data = ui.data, rowIndx = ui.rowIndxPage, colIndx = ui.colIndx;
        var dc = _getUiData(ui);
        var dcText = (dc)?moment(data[rowIndx][colIndx]).format(format):"";

        $inp = _createEditInput($cell, dcText);

        callback($inp, dc);
    }
    var _doDateTimeGetEditCellData = function(ui, format){
        var $cell = ui.$cell;
        var text = $cell.children().val();
        var date = moment(text, format).toDate();
        return date;
    }

    /**
     * Prepare editor, renderer, and other properties for gridModel
     */
    var prepare = function(gridModel){
        var colModel = gridModel.colModel;
        for(var i=0; i<colModel.length; i++){
            var cm = colModel[i];
            cm.render = autoRender;
            cm.editor = autoEditor;
            cm.getEditCellData = autoGetEditCellData;
        }
    }
    
    /**
     * Do action:
     *   - server: The open-xava action, run at server side
     *   - client: run client side javascript function name, with argument "ui" object
     */
    var doAction = function(event, ui){
        var cm = ui.column;
        var act = cm.action;
        if (act.indexOf("server:") == 0){
            var actionName = act.substring(7);
            alert(actionName);
        }else if (act.indexOf("client:") == 0){
            var js = act.substring(7);
            var scope={};
            if (js.indexOf("(")>0 || js.indexOf(" ")>0 || js.indexOf("\"")>0 || js.indexOf("\'")>0 || js.indexOf("=")>0){
                //script is not a function name
                js = "scope.tmpfunc = function(ui){ \n" +
                     "    var cancelEdit = false; \n" + 
                     "    "+js+" \n" +
                     "    return !cancelEdit; \n" +
                     "}"
            }
            var tmpfunc = eval(js);
            var result = tmpfunc.call(global, ui, event);
            return result;
        }else{
            var err = "Unknown action type:["+act+"]";
            alert(err); throw(err);
        }
    }
    
    var autoRender = function(ui){
        return _doRealRender.call(this, ui);
    }
    var autoEditor = function(ui){
        _doRealEditor.call(this, ui)
    }
    var autoGetEditCellData =function(ui){
        return _doRealGetEditCellData.call(this, ui);
    }
    
    var dateRender = function(ui){
        return _doDateTimeRender(ui, "YYYY/MM/DD");
    }
    var dateEditor = function(ui){
        _doDateTimeEditor(ui, "YYYY/MM/DD", function(input, defaultValue){
            input.datepicker({
                defaultDate: defaultValue,
                dateFormat: "yy/mm/dd",
                changeMonth: true,
                changeYear:true,
                onClose: function () {
                    input.focus();
                }
            });
            input.select();
        });
    }
    var dateGetEditCellData =function(ui){
        return _doDateTimeGetEditCellData(ui, "YYYY/MM/DD");
    }
    var datetimeRender = function(ui){
        return _doDateTimeRender(ui, "YYYY/MM/DD HH:mm:ss");
    }
    var datetimeEditor = function(ui){
        _doDateTimeEditor(ui, "YYYY/MM/DD HH:mm:ss", function(input, defaultValue){
            input.datetimepicker({
                dateFormat: "yy/mm/dd",
                timeFormat: "HH:mm:ss",
                changeMonth: true,
                changeYear:true,
                hourGrid: 3,
                minuteGrid: 10,
                secondGrid: 10,
                onClose: function () {
                    input.focus();
                }
            });
            input.datetimepicker("setDate", defaultValue);
            input.select();
        });
    }
    var datetimeGetEditCellData =function(ui){
        return _doDateTimeGetEditCellData(ui, "YYYY/MM/DD HH:mm:ss");
    }
    var _isNumber = function(n) {
        //From: //http://stackoverflow.com/questions/18082/validate-numbers-in-javascript-isnumeric/1830844#1830844
        return !isNaN(parseFloat(n)) && isFinite(n);
    }
    var _formatNumber = function(num, numberPrecision){
        if (! _isNumber(numberPrecision)) numberPrecision = 3;    //Default numberPrecision is 3
        if (_isNumber(num)){
            return accounting.formatNumber(num, numberPrecision, ",");
        }else{
            return "";
        }
    }
    var numberRender = function(ui){
        var d = _getUiData(ui);
        return _formatNumber(d, ui.column.numberPrecision);
    }
    var numberEditor = function(ui){
        var $cell = ui.$cell, data = ui.data, rowIndx = ui.rowIndxPage, colIndx = ui.colIndx;
        var num = _getUiData(ui);
        var fmtedText = _formatNumber(num, this.numberPrecision);
        var input = _createEditInput($cell, fmtedText);
        input.autoNumeric({mDec: this.numberPrecision});
        input.select();
    }
    var numberGetEditCellData =function(ui){
        var $cell = ui.$cell;
        var text = $cell.children().val();
        var number = $cell.children().autoNumeric("get");
        return number;
    }
    
    //Register default handlers
    registerPrototype("date", {
        render: dateRender,
        editor: dateEditor,
        getEditCellData: dateGetEditCellData,
        baseHandler: null
    });
    registerPrototype("datetime", {
        render: datetimeRender,
        editor: datetimeEditor,
        getEditCellData: datetimeGetEditCellData,
        baseHandler: null
    });
    registerPrototype("number", {
        render: numberRender,
        editor: numberEditor,
        getEditCellData: numberGetEditCellData,
        baseHandler: null
    });
    
    //Export ...
    global.xavaEx.PQGrid = {
            prepare: prepare,
            doAction: doAction,
            registerPrototype: registerPrototype,
            "":""    //The END
    };
})(this);