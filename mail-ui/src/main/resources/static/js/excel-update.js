function getTotalErrors(values){
    let totalErrors = [];
    for(let i=0; i<values.length; i++){
        let row = values[i];
        for(let j=0; j<row.length; j++){
            if(row[j]['message'] ){
                 totalErrors.push(row[j]['message'] + " on "+ row[j]['cellReference'] );
            }
        }
    }

    return totalErrors;
}

function findTotalErrors(values){
    let totalErrors = getTotalErrors(values);
    $("#total-error").html(`Errors <span class=" ml-2 badge badge-danger">${totalErrors.length}</span>`);
}
$(document).ready(function(){

    $('#errorMessageModal').on('show.bs.modal', function(e) {
        var title = $(e.relatedTarget).data('title');
        if (title != undefined)
            $(this).find('.modal-title').html(title);

        var message = $(e.relatedTarget).data('message');
        if (message != undefined)
            $(this).find('.modal-body #overWriteModalMessage').html(message);

        let totalErrors = getTotalErrors(values);
        let messageContainer = "";
        for(let i=0; i<totalErrors.length; i++){
            messageContainer += `<p class="m-0">${totalErrors[i]}</p>` ;
        }

        $("#message-list").html(messageContainer);
    });


});