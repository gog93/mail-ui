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

    let eCRFDataTable = $('#emailTemplates').DataTable({
          ajax:{
            url: "/email-templates/ajax",
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
            { data: 'description', name: 'description' },
            { data: 'createdAt', name:'createdAt' },
            { data: 'createdBy', name:'createdBy' },
            {
                data: 'action',
                name: 'action',
                searchable: false,
                orderable : false,
                render: (data,type,row) => {
                    return `<a href='/email-templates/${data}/edit' data-id=${data} class='btn btn-sm btn-primary mr-2'>Edit</a>

                     <a
                       href='#'
                        data-id=${data} class='btn btn-sm btn-danger mr-2'
                        data-title="Delete Template" data-toggle="modal"
                        data-target="#deleteModal"
                        data-message="Are you sure you want to delete email templates ?"
                        data-method="delete"
                        data-href="/email-templates/${data}/delete"
                     >Delete</a> `
                     ;
               }

            }
          ],
    });
});