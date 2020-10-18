var index = {
    init : function () {
        var _this = this;
        $('#btn-event-save').on('click', function () {
            _this.save();
        });
        $('#btn-signup').on('click', function () {
            _this.signup();
        })
    },
    save : function () {

        var data = {
            drinkDate: $('#drinkDate').val(),
            drinkType: $('#drinkType').val(),
            cup: $('#cup').val(),
            film: $('#film').val(),
            memo: $('#memo').val()
        };

        $.ajax({
            type: 'POST',
            url: '/service/event/put',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('주량이 정상적으로 등록되었습니다.');
            window.location.href = '/service/main';
        }).fail(function (error) {
            alert("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근) \n error message : \n" + JSON.stringify(error, null, 2));
        });
    },
    signup : function () {
        var data = {
            name: $('#name').val(),
            password: $('#password').val(),
            email: $('#email').val(),
            birthDate: $('#birthDate').val(),
            gender: $('#gender').val(),
            height: $('#height').val(),
            weight: $('#weight').val()
        };

        $.ajax({
            type: 'POST',
            url: '/signup/new',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('정상적으로 회원가입 되었습니다.');
            window.location.href = '/';
        })
        //     .fail(function (error) {
        //     alert("이미 있는 이메일입니다. \n error message : \n" + JSON.stringify(error, null, 2));
        // })
        ;
    }

};

index.init();