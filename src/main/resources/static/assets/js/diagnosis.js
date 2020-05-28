function output(form) {
    var sum = 0 * 1;
    var checkvalue = $('input:radio[name="diagnosis1"]:checked').val() * 1;
    sum += checkvalue;
    checkvalue = $('input:radio[name="diagnosis2"]:checked').val() * 1;
    sum += checkvalue;
    checkvalue = $('input:radio[name="diagnosis3"]:checked').val() * 1;
    sum += checkvalue;
    checkvalue = $('input:radio[name="diagnosis4"]:checked').val() * 1;
    sum += checkvalue;
    checkvalue = $('input:radio[name="diagnosis5"]:checked').val() * 1;
    sum += checkvalue;
    checkvalue = $('input:radio[name="diagnosis6"]:checked').val() * 1;
    sum += checkvalue;
    checkvalue = $('input:radio[name="diagnosis7"]:checked').val() * 1;
    sum += checkvalue;
    checkvalue = $('input:radio[name="diagnosis8"]:checked').val() * 1;
    sum += checkvalue;
    checkvalue = $('input:radio[name="diagnosis9"]:checked').val() * 1;
    sum += checkvalue;
    checkvalue = $('input:radio[name="diagnosis10"]:checked').val() * 1;
    sum += checkvalue;
    
    document.getElementById("diagnosis-result").innerHTML = sum;
}

