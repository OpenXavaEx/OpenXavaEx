(function(global){
	if (! global.TestApp){
		global.TestApp = {};
	}

	/* Private members*/
    var open_win = function (url, name, features){
        try{
            var tmp;
            if (features){
                tmp = global.open(url, name, features);
            }else{
                tmp = global.open(url, name);
            }
            if (null==tmp){
                throw "Seems the popup window was blocked by browser";
            }
            tmp.focus();
        }catch(ex){
            alert("Popup window fail: " + ex + "\n\n" + "Please change the browser's setting, to allow window popup on current page");
        }
    }
	
	/* Public members */
	global.TestApp.openJspAction = function(ui, fieldName, url){
    	var fVal = ui.dataModel.data[ui.rowIndx][fieldName];
    	var url = url + fVal;
    	open_win(url);
    }
})(this);