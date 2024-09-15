$(function () {
    $("#search").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: '<c:url value = "/search" />',
                data: {
                    filter: request.term
                },
                success: function (data) {
                    response($.map(data, function (account, i) {
                        return {value: account.name, label: account.name + " " + account.surname}
                    }));
                }
            });
        },
        minLength: 1,
    });
});