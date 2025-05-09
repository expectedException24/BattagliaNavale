
window.onload = function() {
    createField("campoUtente");
    createField("campoComp");

};

function createField(field){
    for(let i=0;i<100;i++){
        cell =document.createElement("div");
        cell.classList.add("cell");
        cell.setAttribute('data-index', i);
        document.getElementById(field).appendChild(cell);

    }
}

