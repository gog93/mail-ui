
$(document).ready(function(){

    $('#ecrf-grid').DataTable({
          paging: true,
          lengthChange: true,
          searching: true,
          info: true,
          autoWidth: true,
          responsive: true,
          serverSide: true,
           "order": [[ 1, 'asc' ]],
          ajax:{
            url: "/eCRF/ajax",

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
                   "className": "text-center"
              }
           ],
          columns:[
            {
                name: "checkbox",
                data: "checkbox",
                searchable: false,
                orderable : false,
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
                        data-href="/eCRF/${data}/delete"
                     >Delete</a> ` ;
               }

            }
          ],

    });
});