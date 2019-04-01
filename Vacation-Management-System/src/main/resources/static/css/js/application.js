$(function() {
    // timeout alert
    var $alert = $('.alert');

    if($alert.length){
        setTimeout(function() {
            $alert.fadeOut('slow');
        }, 3000)
    }
});