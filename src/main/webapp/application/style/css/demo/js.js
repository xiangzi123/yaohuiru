// JavaScript Document
$(function () {
	$('#top_nav li').mouseover(function() {
		$(this).addClass('sfhover');
	}).mouseout(function() {
		$(this).removeClass('sfhover');
	});
	$('#bottom_nav li').mouseover(function() {
		$(this).addClass('sfhover');
	}).mouseout(function() {
		$(this).removeClass('sfhover');
	});
	
});

function AddRand(url) { 
	if (url.indexOf('?') == -1) { 
		url += '?'; 
	} else { 
		url += '&'; 
	}   
	return url + 'random=' + Math.random(); 
} 

function refreshCaptcha() {
	var $img = $('#captchaimg');
	var src = $img.attr('src');
	var index = src.indexOf('?');
	if (index != -1) {
		src = src.substring(0, index);
	}
	src += '?rnd='+Math.random();
	$img.attr('src', src);
}

$(function () {
	$('#submitbutton').click(function () {
		var $form = $('#submitform');
		var data = $form.serialize();
		var url = $form.attr('action');
		$.post(AddRand(url), data, function(result) {
			eval(result);
		});
	});
	$('#submitform').submit(function () { return false; });
});
