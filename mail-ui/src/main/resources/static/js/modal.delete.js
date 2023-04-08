$(document).ready(function(){
    $(".alert-dismissible").fadeTo(2000, 500).slideUp(500, function(){
        $(".alert-dismissible").alert('close');
    });

    $('#deleteModal').on('show.bs.modal', function(e) {
        var title = $(e.relatedTarget).data('title');
        if (title != undefined)
            $(this).find('.modal-title').html(title);

        var message = $(e.relatedTarget).data('message');
        if (message != undefined)
            $(this).find('.modal-body #message').html(message);

        $(this).find('.modal-footer .btn-ok').on('click', function () {
            e.preventDefault();
            var token = $(e.relatedTarget).data('delete-token');
            if(token == undefined){
                token = $("meta[name='_csrf']").attr("content");
            }
            var method = $(e.relatedTarget).data('method');
            var parameterName = $(e.relatedTarget).data('delete-parameter-name')
            if(parameterName == undefined){
                parameterName = "_csrf";
            }
            var form =$('<form>', {
                        'method': 'POST',
                        'action': $(e.relatedTarget).data('href')
                    });

            var token =$('<input>', {
                        'type': 'hidden',
                        'name': parameterName,
                        'value': token
                    });

            form.append(token).appendTo('body');
            form.submit();
        });
    });

    $('#restoreModal').on('show.bs.modal', function(e) {
        var title = $(e.relatedTarget).data('title');
        if (title != undefined)
            $(this).find('.modal-title').html(title);

        var message = $(e.relatedTarget).data('message');
        if (message != undefined)
            $(this).find('.modal-body #restore-message').html(message);

        $(this).find('.modal-footer .btn-ok').on('click', function () {
            e.preventDefault();
            var token = $(e.relatedTarget).data('delete-token');
            if(token == undefined){
                token = $("meta[name='_csrf']").attr("content");
            }
            var method = $(e.relatedTarget).data('method');
            var parameterName = $(e.relatedTarget).data('delete-parameter-name')
            if(parameterName == undefined){
                parameterName = "_csrf";
            }
            var form =$('<form>', {
                        'method': 'POST',
                        'action': $(e.relatedTarget).data('href')
                    });

            var token =$('<input>', {
                        'type': 'hidden',
                        'name': parameterName,
                        'value': token
                    });

            form.append(token).appendTo('body');
            form.submit();
        });
    });

    $('#assignModal').on('show.bs.modal', function(e) {
        var title = $(e.relatedTarget).data('title');
        if (title != undefined)
            $(this).find('.modal-title').html(title);

        var message = $(e.relatedTarget).data('message');
        if (message != undefined)
            $(this).find('.modal-body #message').html(message);

        $(this).find('.modal-footer .btn-ok').on('click', function () {
            e.preventDefault();

            let fileType = $(e.relatedTarget).data('file-type');
            let assignedTo = $("#assignTo").val();
            let isSampleManifest = fileType == 1;
            let assignToRequire = false;
            if(isSampleManifest){
                if(assignedTo == '' || assignedTo == undefined){
                    $("#invalid-assigned-to").text("Please select the user.");
                    assignToRequire = true;
                }
            }

            if(!assignToRequire){
                let metaToken = $("meta[name='_csrf']").attr("content");
                let parameterName = $(e.relatedTarget).data('delete-parameter-name')
                if(parameterName == undefined){
                    parameterName = "_csrf";
                }
                var form =$('<form>', {
                    'method': 'POST',
                    'action': $(e.relatedTarget).data('href')
                });

                var token =$('<input>', {
                    'type': 'hidden',
                    'name': parameterName,
                    'value': metaToken
                });

                var assignTo = $('<input>', {
                    'type':'hidden',
                    'name':'assignTo',
                    'value': assignedTo
                });

                form.append(token, assignTo).appendTo('body');
                form.submit();
            }
        });
    });
});