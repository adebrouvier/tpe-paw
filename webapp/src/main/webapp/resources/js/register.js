$(document).ready(function() {

    $('form').submit(function(){
        $('button[type=submit]').prop('disabled', true);
    });
});
