$( document ).ready(function() {
	
    $( document ).on( "click", ".expand", function() {
    	$(this).html("&ndash;");
		$(this).removeClass("expand").addClass("collapse");
		
		var resultBox = $(this).parent().next();
    	$(resultBox).css('height', 'auto');
		$(resultBox).css('display', "block");
    	
    	var autoHeight = $(resultBox).outerHeight();
    	$(resultBox).css('height', '0');
    	
    	
    	$(resultBox).animate({height: autoHeight}, 500);
	});
    
    $( document ).on( "click", ".collapse", function() {
    	$(this).html("+");
    	$(this).removeClass("collapse").addClass("expand");
    	
    	var resultBox = $(this).parent().next();
    	$.when($(resultBox).slideUp()).then(function() {
    		$(resultBox).css("height", '0');
    	});
    	
	});

});