<script type="text/javascript">
	// inicio ajuste altura
	var ini = new Date().getTime();
	if(!document.all){
		var divs = document.getElementsByTagName('div');
		for(var i = 0; i < divs.length; i++){
			if(divs[i].id.indexOf('menu') >= 0){
				var child = null;
				for(var n = 0; n < divs[i].childNodes.length; n++){
					if(divs[i].childNodes[n].nodeName == 'TABLE'){
						child = divs[i].childNodes[n];
						break;
					}
				}
				if(child){
					var h = child.rows[0].cells[0].offsetHeight;
					//if(divs[i].offsetHeight < h)
						divs[i].style.height = h + 9;
				}
			}
		}
	}
	//alert(new Date().getTime() - ini);
	// fim ajuste altura

	if(document.all){
		var inputs = document.getElementsByTagName('input');
		for(var i = 0; i < inputs.length; i++){
			if(inputs[i].type.toLowerCase() == 'button'){
				if(inputs[i].className == ''){
					inputs[i].className = 'btBB';
				}
			}
		}
	}

</script>
