$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();

    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
        jqXHR.setRequestHeader('X-CSRF-Token', $("meta[name='_csrf']").attr("content"));
    });

    $(document).on("click", ".favorite-email", function(e){
        let hello = this;
        $.ajax({
            type: "POST",
            url: $(this).data("action"),
            processData: false,
            contentType: false,
            cache:false,
            success: function(response){
                $(hello).html(response.data);
            }
        });

    });

    var mailDataTable = $('#mail-list').DataTable({
        ordering: false,
        paging: true,
        lengthChange: true,
        ordering: false,
        searching: true,
        info: false,
        autoWidth: true,
        responsive: true,
        serverSide: true,
        language: {
            processing: `<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i>
            <span class="sr-only">Loading...</span>`
        },
        dom: "<'row'<'col-sm-3'l><'#mydiv.col-sm-6 text-right'><'col-sm-3'f>>" +
             			"<'row'<'col-sm-12'tr>>" +
             			"<'row'<'col-sm-5'i><'col-sm-7'p>>",
        ajax:{
            url: "/mail/ajax",
            type:"POST",
            headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
            contentType:"application/json",
            data:function(d){
                let key = $("meta[name='_csrf_header']").attr("content");
                let value = $("meta[name='_csrf']").attr("content");
                Object.assign(d, {[key]:value });
                return JSON.stringify(d)
            }
        },
        columnDefs:[
            { "targets":0, "className": "text-center"},
            { "searchable": true, "targets": [2,4]  },
            { width: 150, targets: 6 }
        ],
        columns:[
            {
                name: "checkbox",
                data: "checkbox",
                searchable: false,
                render: (data, type, row) => {
                    return `<input type="checkbox" class="eCRF-id" value=${data} />`
                }
            },

            {
                data: 'favorite',
                name: 'favorite',
                searchable: false,
                render: (data, type, row) => {

                    return `<div class="favorite-email" data-action="${data.url}">
                        <i class="${data.icon}" aria-hidden="true" ></i>
                    </div>`;
                }
            },
            {
                data: 'status',
                name: 'status',
                render: (data, type, row) => {
                    let icon_name = "badge-success";
                    if(data.localeCompare("new")){
                        text = "badge-primary";
                    }else if(data.localeCompare("submitted")){
                        text = "badge-info";
                    }
                    return `<span class="badge ${icon_name}">${data}</span>`;
                }
            },
            { data: 'subject', name:'subject' },
            {
                data: null,
                name:'customer',
                render: (data, type, row) => {

                    if(data.customer != null && data.customer != ""){
                        let customerElement = '<div>';
                        customerElement += `<a>${data.customer.name}</a>`;
                        customerElement += "</div>";
                        return customerElement;
                    }
                    return `<a href="/mail/${data.id}/add-customer">Add Customer</a>`;
                }
            },
            { data: 'createdAt', name:'createdAt' },
            {
                data: 'actions',
                name: 'actions',
                searchable: false,
                 render: (data, type, row) => {
                    let finalData = '';
                    data.forEach((action)=>{
                        if(action.action == "create-mail"){
                            finalData += `<a
                                href='#'
                                data-id=${action.id} class='btn btn-sm btn-success mr-2'
                                data-toggle="modal"
                                data-target="#create-email"
                            >${action.name}</a>`
                        }else if(action.action == "delete"){
                            finalData += `<a
                                href='#'
                                data-id=${data} class='text text-danger mr-2 '
                                data-title="Delete Mail" data-toggle="modal"
                                data-target="#deleteModal"
                                data-message="Are you sure you want to move mail to trash ?"
                                data-method="delete"
                                data-href="${action.url}"
                                data-toggle="tooltip" data-placement="top" title="Move to trash"
                            ><i class="nav-icon fas fa-trash"></i></a>`
                        } else{
                            finalData += `<a class="btn btn-sm btn-primary mr-2"  href="${action.url}" >${action.name}</a>`
                        }

                    });

                    return finalData ;
                }
            }
        ],

        "createdRow": function( row, data, dataIndex ) {
            console.log(row);
            console.log(data);
            console.log(dataIndex);
        }
    });

    $("#select-status").on("change", function(){
        mailDataTable.columns(2).search($(this).val()).draw();
    });

    mailDataTable.on("draw", function(){
        let selectStatus = $("#select-status");
        selectStatus.prependTo('#mydiv');
        selectStatus.css({"display":"block;float:right;"});
    });

    $('#create-email').on('show.bs.modal', function(e) {
        var id = $(e.relatedTarget).data('id');
        $(this).find(".modal-footer #create-workflow").attr("href", "mail/"+id+"/workflow");
        $(this).find(".modal-footer #use-existing").attr("href", "mail/"+id+"/email-templates");

    });
});