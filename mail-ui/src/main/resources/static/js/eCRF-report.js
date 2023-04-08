
$(document).ready(function(){

    $.fn.dataTable.tables({
        paging: true,
        lengthChange: false,
        searching: true,
        info: true,
        autoWidth: true,
        responsive: true,
        serverSide: true,
        language: {
            processing: `<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i>
            <span class="sr-only">Loading...</span>`
        }
    });

    let eCRFDataTable = $('#ecrf-grid').DataTable({
          "sDom":"tipr",
          "order": [[ 1, 'asc' ]],
          ajax:{
            url: "/eCRF-report/ajax",
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
                {
                     "targets": 0,
                     "className": "text-center",
                },
                {
                    "searchable": true, "targets": [2,4]
                }
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
            { data: 'id', name: 'id' },
            { data: 'patID', name: 'patID' },
            {
                data: 'fillingStatus',
                name: 'fillingStatus',
                render: (data,type,row) => {
                    if(data == undefined || data == ''){
                        return 0;
                    }
                    return data;
                }
            },
            { data: 'createdAt', name:'createdAt' },
            { data: 'userWithAddress', name:'userWithAddress', "orderable" : false },
            {
                data: 'action',
                name: 'action',
                searchable: false,
                orderable : false,
                render: (data,type,row) => {
                    return `<a href='/eCRF/${data}/edit' data-id=${data} class='btn btn-sm btn-primary mr-2'>Edit</a>
                     <a
                       href='#'
                        data-id=${data} class='btn btn-sm btn-danger mr-2'
                        data-title="Delete eCRF" data-toggle="modal"
                        data-target="#deleteModaleCRF"
                        data-message="Are you sure you want to delete the eCRF ?"
                        data-method="delete"
                        data-href="/eCRF-report/${data}/delete"
                     >Delete</a>
                     <a href="#" class='btn btn-sm btn-success ' data-href="/eCRF-report/${data}/assign" data-toggle="modal" data-target="#assignModal"> Assign </a>`
                     ;
               }

            }
          ],
    });

    $("#search-eCRF").keyup(function(e){
        let searchValue = $(this).val();
        if(e.keyCode == 13 || !searchValue) {
            eCRFDataTable.search(searchValue).draw();
        }
    });

    let patID = $('#multiple-pat-id').select2({
        theme: 'bootstrap4',
        placeholder: "Select Pat ID",
        closeOnSelect: false,
        width: 'resolve',
    });

    patID.on("change", function(e){
        eCRFDataTable.columns(2).search($('#multiple-pat-id').val()).draw();
    });

     let filterUserId = $('#userIds').select2({
        theme: 'bootstrap4',
        placeholder: "Select User",
        closeOnSelect: false,
        allowClear: true,
        width: 'resolve',
    });

    filterUserId.on("change", function(e){
        eCRFDataTable.columns(5).search($('#userIds').val()).draw();
    })

    $('#assignModal').on('show.bs.modal', function(e) {

        $(this).find('.modal-footer .btn-ok').on('click', function () {

            e.preventDefault();
            let assignedTo = $("#assignTo").val();
            if(assignedTo == '' || assignedTo == undefined){
                $("#invalid-assigned-to").text("Please select the user.");
            }else{
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

                let eCRFIds = [];
                $(".eCRF-id").each(function() {
                    if($(this).is(":checked")){
                        eCRFIds.push($(this).val());
                    }
                });

                var deletedIds =$('<input>', {
                    'type': 'hidden',
                    'name': "surveyTwoId",
                    'value':  eCRFIds.join(",")
                });

                var assignTo = $('<input>', {
                    'type':'hidden',
                    'name':'assignTo',
                    'value': assignedTo
                });

                form.append(token, assignTo, deletedIds).appendTo('body');
                form.submit();
            }
        });
    });

});