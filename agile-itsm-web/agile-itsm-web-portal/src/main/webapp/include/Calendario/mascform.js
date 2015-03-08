window.onload = applyMasks;

function applyMasks(){
	var Tags = ['input','textarea'];
    	for(z=0;z<Tags.length;z++){
        	Inputs=document.getElementsByTagName(Tags[z]);
			for(i=0;i<Inputs.length;i++)
				if(('button,image,hidden,submit,reset').indexOf(Inputs[i].type.toLowerCase())==-1)
                	ApplyMask(Inputs[i]);
      }
}

AZ = /[A-Z]/i;
Acentos = /[?-?]/i;
Num = /[0-9]/;
IsNull = function(Str){return (Str==null || Str=='' || Str.split('').length-1==Str.length);}

CheckChar = function(Char, Rule){
      switch(Rule){
            case 'A': return Char.search(AZ)!=-1||Char.search(Num)!=-1||Char.search(Acentos)!=-1;
            case 'a': return Char.search(AZ)!=-1||Char.search(Num)!=-1;
            case '9': return Char.search(Num)!=-1;
            case 'C': return Char.search(AZ)!=-1||Char.search(Acentos)!=-1;
            case 'c': return Char.search(AZ)!=-1;
            case '*': return true;
            case Char: return true;
            default : return false;
      }
}

CheckMask = function(e){
      function Result(Status){
            if(Status && Field.OnMaskSuccess) return Field.OnMaskSuccess(e, Field, KeyCode);
            if(!Status && Field.OnMaskError) return Field.OnMaskError(e, Field, KeyCode);
            return Status;
      }
      var      IsIE = navigator.appName.toLowerCase().indexOf('microsoft')!=-1,
            Field =  IsIE ? event.srcElement : e.target,
            KeyCode = IsIE ? event.keyCode : e.which,
            Mask = Field.getAttribute('Mask'),
            Char = String.fromCharCode(KeyCode);
      if(KeyCode==8 || KeyCode==0) return Result(true);
      if(M=Mask.match(/^(.)\^(.*)$/)) {
            if(M[1]=='L') return Result(M[2].indexOf(Char)!=-1);
            else if(M[1]=='E') return Result(M[2].indexOf(Char)==-1);
            else return Result(CheckChar(Char,M[1]) || M[2].indexOf(Char)!=-1);
      }
      if(Field.value.length>Mask.length-1) return Result(false);
      var MaskChar = Mask.charAt(Field.value.length);
      if(MaskChar.match(/[A|9|C|L|\*]/i)) return Result(CheckChar(Char, MaskChar));
      if (CheckChar(Char, Mask.charAt(Field.value.length+1))) {
            Field.value+=MaskChar;
            return Result(true);
      }
      if(CheckChar(Char, MaskChar)) return Result(true);
      return Result(false);
}

ApplyMask = function(Field, DefMask, DefSubType){
      var SubType=DefSubType||String(Field.getAttribute('SubType')||''),
            Mask=DefMask||String(Field.getAttribute('Mask')||''),
            Limit = false;
      switch(SubType.toUpperCase()) {
            case 'NUMBER': Limit=false; Mask='9^'+Mask; break;
            case 'ALPHA+': Limit=false; Mask='A^'+Mask; break;
            case 'ALPHA': Limit=false; Mask='a^'+Mask; break;
            case 'CHAR+': Limit=false; Mask='C^'+Mask; break;
            case 'CHAR': Limit=false; Mask='c^'+Mask; break;
            case 'LIMIT': Limit=false; Mask='L^'+Mask; break;
            case 'EXCEPT': Limit=false; Mask='E^'+Mask; break;
            case 'DATE': Limit=true; Mask='99/99/9999'; break;
            case 'TIME': Limit=true; Mask='99:99:99'; break;
            case 'PHONE': Limit=true; Mask='(99)^999-9999'; break;
            case 'CEP': Limit=true; Mask='99999-999'; break;
            case 'CPF': Limit=true; Mask='999.999.999-99'; break;
            case 'CNPJ': Limit=true; Mask='99.999.999/9999-99'; break;
            case 'MONEY': Limit=false; Field.OnMaskSuccess = CheckMoney; ApplyMask(Field,'.,','Number'); return; break;
      }
      if(!Mask && !SubType) return false;
      if(Limit) Field.maxLength = Mask.length;
      Field.setAttribute('Mask',Mask);
      Field.setAttribute('SubType',SubType);
      if(Field.getAttribute && Field.getAttribute('Mask'))
            Field.onkeypress = function(e){ return CheckMask(e?e:event); };
}


CheckMoney = function(e, Field, KeyCode){
      var      IsIE = navigator.appName.toLowerCase().indexOf('microsoft')!=-1,
            FloatPoint = Field.getAttribute('FloatPoint') || 2;
      if(KeyCode!=44 && KeyCode!=46) {
            if((FracPart=Field.value.match(/\.(.*)/)) && (FloatPoint <= FracPart[1].length)) {
                  Field.value = Field.value.match(/(.*)\./)[1] + String.fromCharCode(KeyCode) + FracPart[0];
                  return false;
            }
            return true;
      }
      if(KeyCode==44)
            if(IsIE) event.keyCode=46;
            else { alert('Utilize o ponto (".") como separador decimal'); return false; }
      if(Field.value.indexOf('.')!=-1 || IsNull(Field.value)) return
false;
      return true;
}


var dtCh	= "/";
var minYear = 1900;
var maxYear = 2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function daysInFebruary(year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1		= dtStr.indexOf(dtCh)
	var pos2		= dtStr.indexOf(dtCh,pos1+1)
	var strDay		= dtStr.substring(0,pos1)
	var strMonth	= dtStr.substring(pos1+1,pos2)
	var strYear		= dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0) == "0" && strDay.length > 1) strDay = strDay.substring(1)
	if (strMonth.charAt(0) == "0" && strMonth.length > 1) strMonth = strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0) == "0" && strYr.length > 1) strYr = strYr.substring(1)
	}
	month = parseInt(strMonth)
	day = parseInt(strDay)
	year = parseInt(strYr)
	if (pos1 == -1 || pos2 == -1){
		alert("O formato da data deve ser : dd/mm/aaaa")
		return false
	}
	if (strMonth.length < 1 || month < 1 || month > 12){
		alert("Por favor, informe um M?s v?lido!")
		return false
	}
	if (strDay.length < 1 || day < 1 || day > 31 || (month == 2 && day > daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Por favor, informe um Dia v?lido!")
		return false
	}
	if (strYear.length != 4 || year == 0 || year < minYear || year > maxYear){
		alert("Por favor, informe um Ano de 4 d?gitos entre " + minYear + " e " + maxYear)
		return false 
	}
	if (dtStr.indexOf(dtCh, pos2 + 1) != -1 || isInteger(stripCharsInBag(dtStr, dtCh)) == false){
		alert("Por favor, informe uma Data v?lida!")
		return false
	}
return true
}

function ValidateForm(dt){
	if(dt.value == ""){
		return false
	}
	var x = dt.value.substring(0, 10);
	if (isDate(x) == false){
		dt.select()
		return false
	}
	return true
}

function pula(atual, proximo){
	var tecla = event.keyCode;
	if(	atual.value.length == 10 && 
		tecla != 8 &&	// backspace
		tecla != 9 &&	// tab
		tecla != 16 &&	// shift
		tecla != 37 &&  // seta p/ esquerda  <-
		tecla != 39 &&  // seta p/ direita   ->
		tecla != 46 )	// delete

		proximo.select();
}