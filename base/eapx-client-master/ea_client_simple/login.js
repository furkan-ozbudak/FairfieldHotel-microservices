$(function(){
    $('#login_button').click(function(){
        let data = {
            email: $('#email').val(),
            password: $('#password').val()
        }
        console.log(JSON.stringify(data));

        api.POST('login',
        data,
        function success(res){
            console.log(res);
            storage.save(res.data);
            window.location.href = 'index.html';
        },function fail(res){
            console.log(res);
        });
    });


});