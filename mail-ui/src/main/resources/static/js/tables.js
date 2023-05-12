
function removeCustomer(customerId) {
    $.ajax({
        url:'/workflow/remove/' + customerId,
        type: 'post',
        contentType: 'application/json',
        dataType: 'json',
        success: function (customer) {
            // Handle success
        },
        error: function () {
            loadCustomers();
            loadWorkflowCustomers();
        }
    });
}

function loadWorkflowCustomers() {
    var id = $('#workflowId').val();
    $.ajax({
        url: '/workflow/create/',
        type: 'GET',
        success: function (data) {
            var $workflowCustomersTable = $('#workflowCustomers tbody');
            $workflowCustomersTable.empty(); // Clear existing rows
            $.each(data, function (index, customer) {
                var row = '<tr  th:field="*{customer}">' +
                    '<td id="id">' + customer.id + '</td>' +
                    '<td id="name">' + customer.name + '</td>' +
                    '<td id="email">' + customer.email + '</td>' +
                    '<td>' +
                    '<button type="button" id="remove" class="btn btn-primary add-customers-btn" data-id="' + customer.id + '">Remove</button>' +
                    '</td>' +
                    '</tr>';
                $workflowCustomersTable.append(row);
            });
            // Unbind the click event before binding it again
            $('.add-customers-btn').off('click').on('click', function() {
                var customerId = $(this).data('id');
                removeCustomer(customerId);
            });
        },
        error: function () {
        }
    });
}
function loadCustomers() {

    $.ajax({
        url: '/customers',
        type: 'GET',
        success: function (data) {
            var $customerTable = $('#customerTable tbody');
            $customerTable.empty(); // Clear existing rows
            $.each(data, function (index, customer) {
                var row = '<tr>' +
                    '<td>' + customer.id + '</td>' +
                    '<td>' + customer.name + '</td>' +
                    '<td>' + customer.email + '</td>' +
                    '<td>' +
                    '<button type="button"  class="btn btn-primary customer-btn" data-id="' + customer.id + '">Add Customer</button>' +
                    '</td>' +
                    '</tr>';
                $customerTable.append(row);
            });
            // Unbind the click event before binding it again
            $('.customer-btn').off('click').on('click', function() {
                var customerId = $(this).data('id');
                addCustomer(customerId);
            });
        },
        error: function () {
        }
    });
}

function addCustomer(customerId) {

    $.ajax({
        url:'/workflow/create/addCustomer?customerId=' + customerId,
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        success: function (customer) {
            // Handle success
        },
        error: function () {
            loadCustomers();
            loadWorkflowCustomers();
        }
    });
}

$(document).ready(function () {
    loadCustomers();
    loadWorkflowCustomers();
});
$(document).on("click", ".btn-delete", function () {
    var providerName = $(this).data('name');

        $.ajax({
            url: '/api/v1/mail/profile?profileName=' + providerName,
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
const submitButton = document.querySelector('#edit-template');
const messageInput = document.querySelector('#edit-marker');

submitButton.addEventListener('click', (e) => {
    e.preventDefault();
    $.ajax({
        url: '/marker/edit-template',
        type: 'GET',
        contentType: 'application/json',
        data: {search: messageInput.value.trim()},

        success: function (customer) {
            $('#edit-marker').val(customer);

        },
        error: function () {
            console.log('Error occurred while fetching chat data');
        }
    });
    if (messageInput.value.trim() !== '') {
        // addUserMessage(messageInput.value.trim());
        messageInput.value = '';
    }
});