$(document).ready(function () {
    let totalPages = 1;

    function fetchNotes(startPage, searchTerm) {
        console.log(searchTerm);
        /**
         * get data from Backend's REST API
         */
        $.ajax({
            type: "GET",
            url: "http://localhost:8084/page",
            data: {
                page: startPage,
                size: 10,
                search: searchTerm
            },
            success: function (response) {
                $('#provider tbody').empty();
                // add table rows
                $.each(response.content, (i, note) => {
                    let noteRow = '<tr>' +
                        '<td>' + ++i + '</td>' +
                        '<td>' + note.profileName + '</td>' +
                        '<td style="padding-left:650px">' +
                        '<button type="button" id="edit" class="btn customer-btn" data-id="' + note.id + '">Edit</button>' +
                        '<a href="/api/v1/mail/view/' + encodeURIComponent(note.profileName) + '" class="btn btn-primary-view" data-id="' + note.profileName + '">View</a>' +
                        '<button type="button" id="delete" class="btn add-customers-btn" data-id="' + note.profileName + '">Delete</button>' +
                        '</td>' +
                        '</tr>';
                    $('#provider tbody').append(noteRow);
                });
                $('#edit').off('click').on('click', function() {
                    var id = $(this).data('id');
                    window.location.href = '/api/v1/mail/profile/edit/' + id;

                });


                if ($('ul.pagination li').length - 2 != response.totalPages) {
                    // build pagination list at the first time loading
                    $('ul.pagination').empty();
                    buildPagination(response);
                }
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    function buildPagination(response) {
        totalPages = response.totalPages;

        var pageNumber = response.pageable.pageNumber;

        var numLinks = 10;

        // print 'previous' link only if not on page one
        var first = '';
        var prev = '';
        if (pageNumber > 0) {
            if (pageNumber !== 0) {
                first = '<li class="page-item"><a class="page-link">« First</a></li>';
            }
            prev = '<li class="page-item"><a class="page-link">‹ Prev</a></li>';
        } else {
            prev = ''; // on the page one, don't show 'previous' link
            first = ''; // nor 'first page' link
        }

        // print 'next' link only if not on the last page
        var next = '';
        var last = '';
        if (pageNumber < totalPages) {
            if (pageNumber !== totalPages - 1) {
                next = '<li class="page-item"><a class="page-link">Next ›</a></li>';
                last = '<li class="page-item"><a class="page-link">Last »</a></li>';
            }
        } else {
            next = ''; // on the last page, don't show 'next' link
            last = ''; // nor 'last page' link
        }

        var start = pageNumber - (pageNumber % numLinks) + 1;
        var end = start + numLinks - 1;
        end = Math.min(totalPages, end);
        var pagingLink = '';

        for (var i = start; i <= end; i++) {
            if (i == pageNumber + 1) {
                pagingLink += '<li class="page-item active"><a class="page-link"> ' + i + ' </a></li>'; // no need to create a link to current page
            } else {
                pagingLink += '<li class="page-item"><a class="page-link"> ' + i + ' </a></li>';
            }
        }

        // return the page navigation link
        pagingLink = first + prev + pagingLink + next + last;

        $("ul.pagination").append(pagingLink);
    }

    $(document).on("click", "ul.pagination li a", function () {
        var data = $(this).attr('data');
        let val = $(this).text();
        console.log('val: ' + val);

        // click on the NEXT tag
        if (val.toUpperCase() === "« FIRST") {
            let currentActive = $("li.active");
            fetchNotes(0, $('#search_input3').val());
            $("li.active").removeClass("active");
            // add .active to next-pagination li
            currentActive.next().addClass("active");
        } else if (val.toUpperCase() === "LAST »") {
            fetchNotes(totalPages - 1, $('#search_input3').val());
            $("li.active").removeClass("active");
            // add .active to next-pagination li
            currentActive.next().addClass("active");
        } else if (val.toUpperCase() === "NEXT ›") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue < totalPages) {
                let currentActive = $("li.active");
                startPage = activeValue;
                fetchNotes(startPage, $('#search_input3').val());
                // remove .active class for the old li tag
                $("li.active").removeClass("active");
                // add .active to next-pagination li
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "‹ PREV") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue > 1) {
                // get the previous page
                startPage = activeValue - 2;
                fetchNotes(startPage, $('#search_input3').val());
                let currentActive = $("li.active");
                currentActive.removeClass("active");
                // add .active to previous-pagination li
                currentActive.prev().addClass("active");
            }
        } else {
            startPage = parseInt(val - 1);
            fetchNotes(startPage, $('#search_input3').val());
            // add focus to the li tag
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
            //$(this).addClass("active");
        }
    });
    $(document).on("click", "#delete", function () {
        var providerName = $(this).data('id');

        $.ajax({
            url: 'http://localhost:8084/profile/delete?profileName=' + providerName,
            type: 'DELETE',
            contentType: 'application/json',
            dataType: 'json',
            success: function (customer) {
                fetchNotes(0, '');
            },
            error: function () {
            }
        });

    });

    $('#form-control-sm').on('keyup', function () {
        fetchNotes(0, $(this).val());
    });
    $("#deleted").click(function () {
        // Call your function
        fetchNotes(0, '');
    });

    (function () {
        // get first-page at initial time
        fetchNotes(0, '');
    })();
});
