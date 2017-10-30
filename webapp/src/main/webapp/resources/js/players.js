$(document).ready(function(){

    $("#sortable").sortable({
            start: function(e, ui) {
                $(this).attr('data-previndex', ui.item.index());
            },
            update: function(e, ui) {
                var newIndex = ui.item.index()+1;
                var oldIndex = parseInt($(this).attr('data-previndex')) + 1;
                var tournamentId = $("body").attr('data-tournamentId');
                $(this).removeAttr('data-previndex');
                $.ajax({
                    type: 'POST',//falta el tournamentId
                    url: contextPath + '/swap/player/' + tournamentId + '/' + oldIndex + '/' + newIndex,
                    success: function () {
                        url = window.location.href;
                        window.location.replace(url);
                    }

                });
            },
            cursor: "move",
            axis: "y"
    });

    $('form').submit(function(){
        $('button[type=submit]').prop('disabled', true);
    });

});