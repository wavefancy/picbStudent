
/**
 * BlockUI with user set message and with a nice css.
 * @param {Object} message
 */
function blockUI(message){
	$.blockUI({
			 message: message,
			 
			 //showOverlay: false,
			 
			 css: { 
			 border: 'none', 
			 padding: '15px', 
			 backgroundColor: '#000', 
			 '-webkit-border-radius': '10px', 
			 '-moz-border-radius': '10px', 
			 'border-radius': '10px',
			// opacity: .5, 
			 opacity: .5,
			 color: '#fff' 
			} 
			});
}

function blockUIWithCSS(message,css){
	    $.blockUI({
			 message: message,
			 
			 //showOverlay: false,
			 
			 css:css
			});
}

function blockUIWithCSSNoOverlay(message,css){
	    $.blockUI({
			 message: message,
			 
			 showOverlay: false,
			 
			 css:css
			});
}

/**
 * Show blockUI withOut overlay.
 * @param {Object} message
 */
function blockUINoOverlay(message){
	
	$.blockUI({
			 message: message,
			 
			 showOverlay: false,
			 
			 css: { 
			 border: 'none', 
			 padding: '15px', 
			 backgroundColor: '#000', 
			 '-webkit-border-radius': '10px', 
			 '-moz-border-radius': '10px', 
			 'border-radius': '10px',
			// opacity: .5, 
			 opacity: .5,
			 color: '#fff' 
			} 
			});
}

/**
 * unblockUI whit delay time.
 * @param {Object} delayTime
 */
function unBlockUI(delayTime){
	setTimeout($.unblockUI,delayTime);	
}

/**
 * unblockUI and run a function, with delayTime.
 * @param {Object} delayTime
 * @param {Object} func
 */
function unBlockUI(delayTime, func){
	setTimeout(function() { 
            $.unblockUI({ 
                onUnblock: func
            }); 
        }, delayTime);
}