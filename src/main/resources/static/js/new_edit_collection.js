
Dropzone.options.myDropzone = {
    url: urlform,
    autoProcessQueue: false,
    uploadMultiple: true,
    parallelUploads: 1,
    maxFiles: 1,
    maxFilesize: 1,
    acceptedFiles: 'image/*',
    addRemoveLinks: true,

    init: function () {
        dzClosure = this;
        form = document.getElementById("form-valid");
        document.addEventListener('submit', function (e) {
            form.classList.add('was-validated');
            e.preventDefault();
            e.stopPropagation();
            if (form.checkValidity()) {
                if (dzClosure.getQueuedFiles().length > 0) {
                    dzClosure.processQueue();
                } else {
                    var blob = new Blob();
                    blob.upload = {'chunked': dzClosure.defaultOptions.chunking};
                    dzClosure.uploadFile(blob);
                }
                window.location.href = '/collection?user_id='+userId;
            }
        });

        this.on("sendingmultiple", function (data, xhr, formData) {
            formData.append("_csrf", jQuery("#token").val());
            if (document.getElementById('user_id'))
                formData.append("user_id", jQuery("#user_id").val());
            formData.append("name", jQuery("#name").val());
            formData.append("topic", jQuery("#topic").val());
            formData.append("markdown", jQuery("#markdown").val());
            if (document.getElementById('updateCheck'))
                formData.append("updateCheck", jQuery("#updateCheck").is(":checked"));
            formData.append("list", selected_fields());
            formData = set_field_names(formData);
        });
    }

};


function valid_fields(id){
    checkbox = document.getElementById(id);
    input = document.getElementById(id + "Field");
    if(checkbox.checked){
        input.removeAttribute("disabled");
    } else {
        input.setAttribute("disabled",true);
        input.value = "";
    }
}


function selected_fields(){
    inp = document.getElementsByName('list');
    mas = [];
    j=0;
    for (var i = 0; i < inp.length; i++) {
        if(inp[i].checked){
            mas[j] = inp[i].value;
            j++;
        }
    }
    return mas;
}



function set_field_names(formData){
    formData.append("nameFirstNumericField", jQuery("#firstNumericField").val());
    formData.append("nameSecondNumericField", jQuery("#secondNumericField").val());
    formData.append("nameThirdNumericField", jQuery("#thirdNumericField").val());
    formData.append("nameFirstStringField", jQuery("#firstStringField").val());
    formData.append("nameSecondStringField", jQuery("#secondStringField").val());
    formData.append("nameThirdStringField", jQuery("#thirdStringField").val());
    formData.append("nameFirstTextField", jQuery("#firstTextField").val());
    formData.append("nameSecondTextField", jQuery("#secondTextField").val());
    formData.append("nameThirdTextField", jQuery("#thirdTextField").val());
    formData.append("nameFirstBooleanField", jQuery("#firstBooleanField").val());
    formData.append("nameSecondBooleanField", jQuery("#secondBooleanField").val());
    formData.append("nameThirdBooleanField", jQuery("#thirdBooleanField").val());
    formData.append("nameFirstDateField", jQuery("#firstDateField").val());
    formData.append("nameSecondDateField", jQuery("#secondDateField").val());
    formData.append("nameThirdDateField", jQuery("#thirdDateField").val());
    return formData;
}

function update_photo(id) {
    checkbox = document.getElementById(id);
    currentPhoto = document.getElementById('currentPhoto');
    newPhoto = document.getElementById('myDropzone');

    if (checkbox.checked) {
        newPhoto.removeAttribute("hidden");
        currentPhoto.setAttribute("hidden", true);
    } else {
        currentPhoto.removeAttribute("hidden");
        newPhoto.setAttribute("hidden", true);
    }
}

function activate_fields(){
    inp = document.getElementsByName('list');
    for (var i = 0; i < inp.length; i++) {
        if(fields[i]!=0){
            inp[i].checked = true;
            inp[i].onclick() ;
        }
    }
}

