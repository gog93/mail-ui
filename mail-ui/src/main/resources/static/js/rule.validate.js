
function ruleTypeAction(val){
    if (val == 1) {
        $("#row-type").show();
        $("#column-type").hide();
    } else {
        $("#row-type").hide();
        $("#column-type").show();
    }
}

function hideDiv(columnRuleType){

    if (columnRuleType == 2) {
        $("#second-column-container").hide();
        $("#column-parameter-container").show();
    } else {
        $("#second-column-container").show();
        $("#column-parameter-container").hide();
    }
}

function setColumnOption(fileTypeValue){
    const firstColumn = $("#first-column");
    const secondColumn = $("#second-column");
    firstColumn.val(null).trigger("change");
    secondColumn.val(null).trigger("change");
    if(fileTypeValue == 0){
        clinicalDataColumn.forEach((element, key)=>{
            firstColumn.append(new Option(element, element)).trigger("change");
            secondColumn.append(new Option(element, element)).trigger("change");
        });
    }else if(fileTypeValue == 1){
         sampleManifestColumn.forEach((element, key)=>{
            firstColumn.append(new Option(element, element)).trigger("change");
            secondColumn.append(new Option(element, element)).trigger("change");
        });
    }else if(fileTypeValue == 2){
          factorsPriorColumn.forEach((element, key)=>{
             firstColumn.append(new Option(element, element)).trigger("change");
             secondColumn.append(new Option(element, element)).trigger("change");
         });
     }
}

function compareBetweenToggle(columnCompare){
    if(columnCompare == 1){
        $("#expression-with-parameter").show();
        $("#column-parameter-container").hide();
        $("#column-validation-method").hide();
        $("#second-column-container").show();
    }else if(columnCompare == 2){
        $("#expression-with-parameter").show();
        $("#column-validation-method").hide();
        $("#second-column-container").hide();
        $("#column-parameter-container").show();
    }else if(columnCompare == 3){
        $("#column-validation-method").show();
        $("#second-column-container").hide();
        $("#column-parameter-container").hide();
        $("#expression-with-parameter").hide();
    }
}

$(document).ready(function(){

    // rule type
    let ruleType = $("#rule-type");
    ruleTypeAction(ruleType.val());
    ruleType.on("change", function (e) {
        ruleTypeAction($(this).val());
    });

     // select option
    const defaultSelect = {
          theme: 'bootstrap4',
          closeOnSelect: true,
          width: 'resolve',
          placeholder:"Select column"
    };
    $("#first-column").select2(defaultSelect);
    $("#second-column").select2(defaultSelect);

    // file type
    let fileType = $("#file-type");
    setColumnOption(fileType.val());
    fileType.on("change", function(){
        setColumnOption($(this).val());
    });

    // column rule type
    let columnCompareValue = $('input[name="compareBetween"]:checked').val();
    if(columnCompareValue == undefined || columnCompareValue == ""){
        $('#1-between-column').prop("checked", true);
    }
    compareBetweenToggle($('input[name="compareBetween"]:checked').val())

    const columnRuleType = $(".column-rule-type");
    columnRuleType.on("change", function () {
        compareBetweenToggle($(this).val());
    });

    if($("#first-column").data("value") != ""){
        $("#first-column").val($("#first-column").data("value"));
    }

    if($("#second-column").data("value") != ""){
        $("#second-column").val($("#second-column").data("value"));
    }

    $.validator.setDefaults({
        highlight: function(element) {
            $(element).addClass("is-invalid").removeClass("is-valid");
        },
        unhighlight: function(element) {
              $(element).addClass("is-valid").removeClass("is-invalid");
        },

        errorElement: 'span',
        errorClass: 'text-danger',
        errorPlacement: function(error, element) {
            if(element.parent('.form-control').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
        // end add
    });

    $("#create-rule").validate({
        rules: {
            ruleType: "required",
            fileType: "required",
            name: 'required',
            columnBetween: {
                required: function () {
                    return $("#rule-type").val() == 1;
                }
            },
            errorMessage: {
                required: function () {
                    return $("#rule-type").val() == 1;
                }
            },
            parameterValue: {
                required: function(){
                    return $('input[name="columnBetween"]:checked').val() == 2;
                }
            },

            parameters:{
                required: function(){
                    return $("#rule-type").val() == 1;
                }
            },
            methodName:{
                required: function(){
                    return $("#rule-type").val() == 1;
                }
            },
        }
    });
});