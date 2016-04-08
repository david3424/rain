
function startRequest(){
	
	Ext.Ajax.request({url:'/wmeovg/service/serviceManager_startRequest.action',
    	success:function(result){
		 	document.getElementById("request_status").innerHTML = result.responseText;
		}
	});
}

function stopRequest(){
	
	Ext.Ajax.request({url:'/wmeovg/service/serviceManager_stopRequest.action',
    	success:function(result){
		 	document.getElementById("request_status").innerHTML = result.responseText;
		}
	});
}

function startCallback(){
	
	Ext.Ajax.request({url:'/wmeovg/service/serviceManager_startCallback.action',
    	success:function(result){
		 	document.getElementById("callback_status").innerHTML = result.responseText;
		}
	});
}

function stopCallback(){
	
	Ext.Ajax.request({url:'/wmeovg/service/serviceManager_stopCallback.action',
    	success:function(result){
		 	document.getElementById("callback_status").innerHTML = result.responseText;
		}
	});
}
function startGoods(){
	
	Ext.Ajax.request({url:'/wmeovg/service/serviceManager_startGoods.action',
		success:function(result){
		document.getElementById("goods_status").innerHTML = result.responseText;
	}
	});
}

function stopGoods(){
	
	Ext.Ajax.request({url:'/wmeovg/service/serviceManager_stopGoods.action',
		success:function(result){
		document.getElementById("goods_status").innerHTML = result.responseText;
	}
	});
}

Ext.onReady(function(){

	Ext.Ajax.request({url:'/wmeovg/service/serviceManager_getRequestStatus.action',
    	success:function(result){
		 	document.getElementById("request_status").innerHTML = result.responseText;
		}
	});
	
	Ext.Ajax.request({url:'/wmeovg/service/serviceManager_getCallbackStatus.action',
    	success:function(result){
		 	document.getElementById("callback_status").innerHTML = result.responseText;
		}
	});
	Ext.Ajax.request({url:'/wmeovg/service/serviceManager_getGoodsStatus.action',
		success:function(result){
		document.getElementById("goods_status").innerHTML = result.responseText;
	}
	});
});