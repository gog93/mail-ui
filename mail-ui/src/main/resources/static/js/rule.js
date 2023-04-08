$(document).ready(function(){

    $(document).on("click", ".view-details", function(e){
         e.preventDefault();
         $.ajax({
            type: "GET",
            url: $(this).data('href'),
            success: function(response){
                $("#rule-details").html(response);
                $('#viewDetails').modal('show');
            }
        });
    });


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

    let eCRFDataTable = $('#rules').DataTable({
          ajax:{
            url: "/rules/ajax",
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
                name: "sn",
                data: "sn",
                searchable: false
            },
            { data: 'name', name: 'name' },
            { data: 'fileType', name: 'fileType' },
            { data: 'ruleType', name: 'ruleType' },
            { data: 'createdAt', name:'createdAt' },
            { data: 'createdBy', name:'createdBy' },
            {
                data: 'action',
                name: 'action',
                searchable: false,
                orderable : false,
                render: (data,type,row) => {
                    return `<a href='/rules/${data}/edit' data-id=${data} class='btn btn-sm btn-primary mr-2'>Edit</a>
                    <a data-href='/rules/${data}'
                        data-id=${data}
                        data-toggle="modal"
                        data-target="#viewDetails"
                        class='view-details btn btn-sm btn-success mr-2'>View</a>
                     <a
                       href='#'
                        data-id=${data} class='btn btn-sm btn-danger mr-2'
                        data-title="Delete Rule" data-toggle="modal"
                        data-target="#deleteModal"
                        data-message="Are you sure you want to delete rule ?"
                        data-method="delete"
                        data-href="/rules/${data}/delete"
                     >Delete</a> `
                     ;
               }

            }
          ],
    });
});