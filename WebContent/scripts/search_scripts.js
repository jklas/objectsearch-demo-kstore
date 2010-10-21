var cart = document.getElementById('cart');

function paintItemsInCart() {

	var results = document.getElementsByName('result');
	var itemList = getCartItems();
	
	
	if(itemList!=null && itemList.length > 0) {
		enableCartButtons();
		buildCart(itemList);
	}
	
	for ( var i in itemList) {
		for ( var j in results) {
			if(new String(results[j].id) == ("item_"+itemList[i])) {
				results[j].className='selectedResult';
				document.getElementById('cart_img_'+itemList[i]).src='images/remove_cart.png';
			}
		}
	}	
}

function getCartItems() {
	checkCartCookie();
	var itemString = getCookie('cart');
	var itemList ;
	
	if(itemString.length>0) itemList = itemString.split(';');
	else itemList = new Array();
	
	return itemList;
}

function checkout() {
	checkCartCookie();
	var itemString = getCookie('cart');
	var itemList ;
	
	if(itemString.length>0) itemList = itemString.split(';');
	else itemList = new Array();
	
	if(itemList.length==0) return;
	else window.location.href='checkout.htm';
}


function cartToggle(siteId,resultId) {
	
	var itemList = getCartItems(); 
	
	var elementById=document.getElementById('cart_img_'+resultId);
		
	
	for ( var i = 0; i < itemList.length; i++) {
		var currentItem = itemList[i];
		if(currentItem==resultId) {
			removeFromCart(siteId, resultId);
			if(elementById!=null) elementById.src='images/add_cart.jpg';
			return;
		}
	}
	
	addToCart(siteId, resultId);
	elementById.src='images/remove_cart.png';
}

function addToCart(siteId, resultId) {
	var itemList = getCartItems(); 
	
	enableCartButtons();
		
	for ( var i = 0; i < itemList.length; i++) {
		var currentItem = itemList[i];
		if(currentItem==resultId) return;
	}
	
	itemList.push(resultId);
	
	saveItemListToCookie(itemList);
	
	// bold in main list
	document.getElementById('item_'+resultId).className='selectedResult';
	
	buildCart(itemList);
}

function buildCart(itemList) {
	cart.innerHTML = '';
	for ( var i = 0; i < itemList.length; i++) {
		var id = itemList[i];
		var cartItem=document.getElementById('item_'+id);
		
		if(cartItem == null) {
			cart.innerHTML += "<li>"+
			titles[id] +
			"</li><br/>";
		} else {
			cart.innerHTML += "<li>"+
			cartItem.innerHTML +
			"</li><br/>";
		}		
	}
}

function disableCartButtons() {
	document.getElementById('img_checkout').src='images/checkout_disabled.png';
	document.getElementById('img_clearcart').display='none';
}

function enableCartButtons() {
	document.getElementById('img_checkout').src='images/checkout.png';		
	document.getElementById('img_clearcart').display='';
}

function clearCart() {
	var itemList = getCartItems(); 	
	disableCartButtons();	
	
	 for ( var i = 0; i < itemList.length; i++) {
		var id = itemList[i]; 
		cartToggle('',id);
	}	
}

function saveItemListToCookie(itemList) {
	var itemString = "";
	
	for ( var i = 0; i < itemList.length ; i++) {
		var currentItem = itemList[i];
		if(i!=0) itemString+=";";		
		itemString+=currentItem;
	}
		
	setCookie('cart',itemString,365);
	
	//alert('Items in cart: '+itemString);
}

function removeFromCart(siteId, resultId) {
	var itemList = getCartItems();
		
	for ( var i = itemList.length-1; i >= 0 ; i--) {
		var currentItem = itemList[i];
		if(currentItem==resultId) itemList.splice(i,1);
	}
	
	saveItemListToCookie(itemList);
	var elementById=document.getElementById('item_'+resultId);
	
	if(elementById !=null) elementById.className='';
	
	if(itemList!=null && itemList.length == 0) {
		disableCartButtons();
	}
	
	buildCart(itemList);
}

function checkCartCookie()
{
	items=getCookie('cart');
	if (items==null || items=="") {
		setCookie('cart','',365);
	}
}

function getCookie(c_name)
{
	if (document.cookie.length>0)
	{
		c_start=document.cookie.indexOf(c_name + "=");
		if (c_start!=-1)
		{
			c_start=c_start + c_name.length+1;
			c_end=document.cookie.indexOf(";",c_start);
			if (c_end==-1) c_end=document.cookie.length;
			return unescape(document.cookie.substring(c_start,c_end));
		}
	}
	return "";
}

function setCookie(c_name,value,expiredays) {
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toUTCString());
}