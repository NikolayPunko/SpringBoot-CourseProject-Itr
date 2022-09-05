
var fieldTagCounter = 0;

function duplicate_tag() {
    var original = document.getElementById('dupl_0');
    var clone = original.cloneNode(true);

    clone.id = "dupl_" + ++fieldTagCounter;

    var inputNodes = clone.getElementsByTagName('INPUT');
    inputNodes[0].id = "inp_" + fieldTagCounter;

    original.parentNode.appendChild(clone);
    if (fieldTagCounter>0){
        document.getElementById('btn-remove').style.display="block";
    }
}

function delete_tag() {
    document.getElementById('dupl_'+fieldTagCounter).remove();
    --fieldTagCounter;
    if (fieldTagCounter<1){
        document.getElementById('btn-remove').style.display="none";
    }
}
