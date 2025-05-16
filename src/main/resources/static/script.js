$(document).ready(function() {
    createField("campoUtente");
    createField("campoComp");
    getships();
});

function createField(field){
    for(let i=0;i<100;i++){
        cell =document.createElement("div");
        cell.classList.add("cell");
        cell.setAttribute('data-index', i);
        document.getElementById(field).appendChild(cell);

    }
}

function getships() {
    $.ajax({
        url: 'getships',
        method: 'GET',
        success: function(response) {
            console.log(response);
            let num = 1;
            response.playerShips.forEach(ship => {
                console.log('ship', ship);
                ship.cordinates.forEach(coord => {
                    let index = coord.x*10 + coord.y;
                    $('#campoUtente .cell').eq(index).addClass('ship-'+num);
                });
                num++;
            });
            num = 1;
            response.pcShips.forEach(ship => {
                console.log('ship', ship);
                ship.cordinates.forEach(coord => {
                    let index = coord.x*10 + coord.y;
                    $('#campoComp .cell').eq(index).addClass('ship-'+num);
                });
                num++;
            });
            /*
            response.pcShips.forEach(index => {
                $('#campoUtente .cell').eq(index).addClass('ship');
            });
            response.playerShips.forEach(index => {
                $('#campoComp .cell').eq(index).addClass('ship');
            });
            */
        },
        error: function() {
            alert('Errore nel caricamento delle griglie!');
        }
    });
}