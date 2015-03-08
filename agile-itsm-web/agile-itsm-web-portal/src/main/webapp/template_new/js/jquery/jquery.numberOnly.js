  //somente numeros
      jQuery.fn.numbersOnly = function(){
        var $teclas = {8:'backspace',9:'tab',13:'enter',48:0,49:1,50:2,51:3,52:4,53:5,54:6,55:7,56:8,57:9};    
        $(this).keypress(function(e){
          var keyCode = e.keyCode?e.keyCode:e.which?e.which:e.charCode;
          if(keyCode in $teclas){
            return true;
          }else{
            return false;
          }
        });
        return $(this);
      }