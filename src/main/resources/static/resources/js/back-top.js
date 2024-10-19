/* funcion-regresar-arriba */
$(document).ready(function(){

	// hide #back-top first
	$("#volver-arriba").hide();
	
	// fade in #back-top
	$(function () {
		$(window).scroll(function () {
			if ($(this).scrollTop() > 300) {
				$('#volver-arriba').fadeIn();
			} else {
				$('#volver-arriba').fadeOut();
			}
		});

		// scroll body to 0px on click
		$('#volver-arriba a').click(function () {
			$('body,html').animate({
				scrollTop: 0
			}, 100);
			return false;
		});
	});
});