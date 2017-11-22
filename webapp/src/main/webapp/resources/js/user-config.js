$(document).ready(function() {
    Materialize.updateTextFields();

    var dataTextarea = $('#description-text-area').attr('data-textarea');
    document.getElementById("description-textarea").defaultValue = dataTextarea.toString();
    $('#description-text-area').removeAttr('data-textarea');

    $('form').submit(function(){
        $('button[type=submit]').prop('disabled', true);
    });
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#user-preview').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}
