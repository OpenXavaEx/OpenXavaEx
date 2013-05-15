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

	global.xavaEx = {
			getWindowSize: getWindowSize,
			loadCSS: loadCSS,
			fillIFrame: fillIFrame
	};
	
})(this);