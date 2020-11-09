$(function(){
    $('#register_button').click(function(){
        let data = {
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            role: $('#role').children("option:selected").val()
        }
        console.log(JSON.stringify(data));

        api.POST('register',
        data,
        function success(res){
            console.log(res);
            window.location.href = 'login.html';
        },function fail(res){
            console.log(res);
        });
    });
});