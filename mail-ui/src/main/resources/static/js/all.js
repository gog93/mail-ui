$(document).ready(function(){
    $("#select-all-eCRF").change(function(e){
        let isChecked = $(this).is(":checked");
        $('.eCRF-id').prop('checked', isChecked);
        $("#delete-eCRF").prop( "disabled", !isChecked );
        $("#assign-eCRF").prop("disabled", !isChecked);
    });

    $(document).on("change", ".eCRF-id",function(e){
        let isChecked = $(this).is(":checked");

        $("#delete-eCRF").prop( "disabled", !isChecked );
        $("#assign-eCRF").prop("disabled", !isChecked);
    });

    $('#deleteModaleCRF').on('show.bs.modal', function(e) {
        var title = $(e.relatedTarget).data('title');
        if (title != undefined)
            $(this).find('.modal-title').html(title);

        var message = $(e.relatedTarget).data('message');
        if (message != undefined)
            $(this).find('.modal-body #message').html(message);

        $(this).find('.modal-footer .btn-ok').on('click', function () {
            e.preventDefault();
            var form =$('<form>', {
                'method': 'POST',
                'action': $(e.relatedTarget).data('href')
            });

            let eCRFId = [];
            $(".eCRF-id").each(function() {
                if($(this).is(":checked")){
                    eCRFId.push($(this).val());
                }
            });

            var deletedIds =$('<input>', {
                'type': 'hidden',
                'name': "ids",
                'value':  eCRFId.join(",")
            });

            var token =$('<input>', {
                'type': 'hidden',
                'name': "_csrf",
                'value': $("meta[name='_csrf']").attr("content")
            });

            form.append(deletedIds, token).appendTo('body');
            form.submit();
        });
    });
});