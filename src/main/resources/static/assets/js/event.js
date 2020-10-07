var index = {
    init : function () {
        var _this = this;
        $('#btn-event-save').on('click', function () {
            _this.save();
        });
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
            alert(JSON.stringify(error))
        });
    }

};

index.init();