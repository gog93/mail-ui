$(document).ready(function(){


    let eCRFDataTable = $('#customer').DataTable({
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
        },
        ajax:{
            url: $('#customer').data("href"),
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
        columns:[
            {  name: "sn",  data: "sn", searchable: false },
            { data: 'name', name: 'name' },
            { data: 'email', name: 'email' },
            { data: 'phoneNumber', name: 'phoneNumber', orderable:false },
            { data: 'createdAt', name:'createdAt' },
            {
                data: 'action',
                name: 'action',
                searchable: false,
                orderable : false,
                render: (data,type,row) => {
                    return `<a href='/customer/${data}/edit' data-id=${data} class='btn btn-sm btn-primary mr-2'>Edit</a>`;
               }

            }
        ],
    });
});