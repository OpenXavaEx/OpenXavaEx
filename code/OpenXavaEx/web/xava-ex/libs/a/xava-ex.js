/**
 * Some utility function
 */
(function(global){
    //Get size of window, from http://www.howtocreate.co.uk/tutorials/javascript/browserwindow
    var getWindowSize = function () {
        var myWidth = 0, myHeight = 0;
        if( typeof( window.innerWidth ) == 'number' ) {
            //Non-IE
            myWidth = window.innerWidth;
            myHeight = window.innerHeight;
        } else if( document.documentElement &&
                   ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
            //IE 6+ in 'standards compliant mode'
            myWidth = document.documentElement.clientWidth;
            myHeight = document.documentElement.clientHeight;
        } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
            //IE 4 compatible
            myWidth = document.body.clientWidth;
            myHeight = document.body.clientHeight;
        }
        return {
            width: myWidth,
            height: myHeight
        };
    }

    //Dynamic css file loader
    var loadCSS = function(url){
		var fileref = document.createElement("link");
        fileref.setAttribute("rel", "stylesheet");
        fileref.setAttribute("type", "text/css");
        fileref.setAttribute("href", url);
        if (typeof fileref != "undefined"){
            document.getElementsByTagName("head")[0].appendChild(fileref)
        }
    }
    
    //Wrtite html into an IFrame, with init-parameters
    var fillIFrame = function(iframeId, html, initParams){
        if (null==initParams) initParams = {};
        var theIframe = document.getElementById(iframeId);
        var iFrameWindow = theIframe.contentWindow || theIframe.documentWindow;
        var iframeDoc = iFrameWindow.document;
        iframeDoc.open();
        for (var o in initParams){
            iFrameWindow[o] = initParams[o];
        }
        iframeDoc.write(html);
        iframeDoc.close();
    }

    /** IFrame height auto-fit */
    var setIFrameAutoHeight = function(iframe){
        if (iframe.location){
            //It's a window(of the iframe content)
        	setIFrameAutoHeight(iframe.frameElement);
        }else{
            /**
            alert(
                 navigator.userAgent + ":\n" + 
                "\t documentWindow: "+iframe.documentWindow+"\n"+
                "\t contentWindow: "+iframe.contentWindow+"\n"+
                "\t Document: "+iframe.Document+"\n"+
                "\t contentDocument.body.offsetHeight: "
                    +((iframe.contentDocument)?(iframe.contentDocument.body?iframe.contentDocument.body.offsetHeight:"N/A"):"N/A")+"\n"+
                "\t Document.body.scrollHeight: "
                    +((iframe.Document)?(iframe.Document.body?iframe.Document.body.scrollHeight:"N/A"):"N/A")
            );
            */
            if (iframe.Document){
                //IE8
                iframe.height = iframe.Document.body.scrollHeight + 20;
            }else if (iframe.contentDocument){
                //IE9, Firefox, Chrome
                iframe.height = iframe.contentDocument.body.offsetHeight + 35;
            }else{
                throw "argument ["+iframe+"] is not a window or iframe"
            }
        }
    }

    var dependenceCache = {};
    var isDependenceExists = function(depObjName){
    	if (dependenceCache[depObjName]) return true;
    	var tmp = eval("global." + depObjName);
    	if (tmp){
    		dependenceCache[depObjName] = true;
    		return true;
    	}else{
    		return false;
    	}
    }
    var checkDependence = function(depObjName, refer, desc){
    	if (! isDependenceExists(depObjName)){
			var err = "'" + refer + "' depends on '"+depObjName+"'("+desc+"), please fix the javascript importing!";
			alert(err); throw(err);
    	}
    }
    
    var insertHtmlWithScript = function(id, html) {
		var ele = document.getElementById(id);
		ele.innerHTML = html;
		var codes = ele.getElementsByTagName("script");
		for ( var i = 0; i < codes.length; i++) {
			eval(codes[i].text);
		}
    }
    
	global.xavaEx = {
			getWindowSize: getWindowSize,
			loadCSS: loadCSS,
			fillIFrame: fillIFrame,
			setIFrameAutoHeight: setIFrameAutoHeight,
			isDependenceExists: isDependenceExists,
			checkDependence: checkDependence,
			insertHtmlWithScript: insertHtmlWithScript
	};
	
})(this);

/**
 * The JSON Formatter
 */
(function(global){
	/*
	 * From : http://joncom.be/code/javascript-json-formatter/
	 */

	/**
	 * Wrap the original "FormatJSON", Handle the exception
	 */
	var FormatJSON = function(oData, sIndent) {
		try{
			return json_formatter__FormatJSON(oData, sIndent || "");
		}catch(ex){
			var s = oData + "";
			var e = ex + "";
			return json_formatter__FormatJSON({"Error Data": s, "Error Type": e}, sIndent || "")
		}
	}

	/** It's the original "FormatJSON" */
	var json_formatter__FormatJSON = function(oData, sIndent) {
	    if (arguments.length < 2) {
	        var sIndent = "";
	    }
	    var sIndentStyle = "    ";
	    var sDataType = json_formatter__RealTypeOf(oData);

	    // open object
	    if (sDataType == "array") {
	        if (oData.length == 0) {
	            return "[]";
	        }
	        var sHTML = "[";
	    } else {
	        var iCount = 0;
	        $.each(oData, function() {
	            iCount++;
	            return;
	        });
	        if (iCount == 0) { // object is empty
	            return "{}";
	        }
	        var sHTML = "{";
	    }

	    // loop through items
	    var iCount = 0;
	    $.each(oData, function(sKey, vValue) {
	        if (iCount > 0) {
	            sHTML += ",";
	        }
	        if (sDataType == "array") {
	            sHTML += ("\n" + sIndent + sIndentStyle);
	        } else {
	            sHTML += ("\n" + sIndent + sIndentStyle + "\"" + sKey + "\"" + ": ");
	        }

	        // display relevant data type
	        switch (json_formatter__RealTypeOf(vValue)) {
	            case "array":
	            case "object":
	                sHTML += FormatJSON(vValue, (sIndent + sIndentStyle));
	                break;
	            case "boolean":
	            case "number":
	                sHTML += vValue.toString();
	                break;
	            case "null":
	                sHTML += "null";
	                break;
	            case "string":
	                sHTML += ("\"" + vValue + "\"");
	                break;
	            case "date":
	            	if (FormatJSON.dateFormatter){
		                sHTML += FormatJSON.dateFormatter(vValue);
	            	}else {
		                sHTML += ("new Date(" + vValue.getTime() + ")");
	            	}
	                break;
	            default:
	                sHTML += ("TYPEOF: " + typeof(vValue));
	        }

	        // loop
	        iCount++;
	    });

	    // close object
	    if (sDataType == "array") {
	        sHTML += ("\n" + sIndent + "]");
	    } else {
	        sHTML += ("\n" + sIndent + "}");
	    }

	    // return
	    return sHTML;
	}

	/*
	 * From : http://joncom.be/code/realtypeof/
	 */
	var json_formatter__RealTypeOf = function(v) {
	  if (typeof(v) == "object") {
	    if (v === null) return "null";
	    if (v.constructor == (new Array).constructor) return "array";
	    if (v.constructor == (new Date).constructor) return "date";
	    if (v.constructor == (new RegExp).constructor) return "regex";
	    return "object";
	  }
	  return typeof(v);
	}
	
	//Export ...
	global.xavaEx.formatJSON = FormatJSON;
})(this);
