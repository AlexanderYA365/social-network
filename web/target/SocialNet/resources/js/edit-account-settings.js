$("button[name='button-delete']").click(function () {
    console.log('delete')
    $(this).parent().remove();
});

function createNewPhone() {
    console.log('создается телефон')
    var div = document.createElement('div');
    div.className = 'form-floating';
    var mySelect = document.createElement('select');
    mySelect.add(new Option('домашний', 'HOME'));
    mySelect.add(new Option('Рабочий', 'WORK'));
    mySelect.add(new Option('Личный', 'ADDITIONAL'));
    mySelect.name = 'typePhone';
    mySelect.id = 'typePhone';
    mySelect.class = 'form-select';
    var myInput = document.createElement('input');
    myInput.type = 'text';
    myInput.id = 'phone';
    myInput.name = 'phone';
    myInput.class = 'phone';
    div.appendChild(myInput)
    div.appendChild(mySelect)
    $("#button-add-phone").before(div);
}

$(document).ready(function () {
    $body = $('body');
    $body.find('#phone').each(function () {
        $(this).mask("+7 (999) 99-99-999", {autoclear: false});
    });
    $body.find('.phone').each(function () {
        $(this).mask("+7 (999) 99-99-999", {autoclear: false});
    });
});

function addMask() {
    $('#phone').mask("+7 (999) 99-99-999", {autoclear: false});
}

function sendPost() {
    clearError();
    return checkedField();
}

function checkedField() {
    var fields = document.querySelectorAll('.field')
    let valid = true;
    for (var i = 0; i < fields.length; i++) {
        if (!fields[i].value) {
            console.log('поле не заполнено', fields[i])
            var error = generateError('заполните поле')
            $(fields[i]).after(error)
            valid = false;
        }
    }
    return valid;
}

var generateError = function (text) {
    var error = document.createElement('div')
    error.className = 'error'
    error.style.color = 'red'
    error.innerHTML = text
    return error
}

function clearError() {
    var form = document.querySelector('.modal')
    var errors = form.querySelectorAll('.error')
    for (var i = 0; i < errors.length; i++) {
        errors[i].remove()
    }
}