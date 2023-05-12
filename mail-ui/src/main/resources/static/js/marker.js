const submitButton = document.querySelector('#submit-button');
const messageInput = document.querySelector('#marker');

submitButton.addEventListener('click', (e) => {
    e.preventDefault();
    $.ajax({
        url: '/marker',
        type: 'GET',
        contentType: 'application/json',
        data: {search: messageInput.value.trim()},

        success: function (customer) {
            $('#marker').val(customer);

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
